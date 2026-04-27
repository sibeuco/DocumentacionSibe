import { Component, Input, Output, EventEmitter, OnInit, OnChanges, SimpleChanges, ChangeDetectorRef } from '@angular/core';
import { ActivityResponse, EstadoActividad } from '../../model/activity.model';
import { FechaProgramada } from '../../model/fecha-programada.model';
import { ActivityExecutionResponse } from '../../model/activity-execution.model';
import { ActivityService } from '../../service/activity.service';
import { DepartmentService } from '../../service/department.service';
import { AreaService } from '../../service/area.service';
import { SubAreaService } from '../../service/subarea.service';
import { StateService } from '../../service/state.service';
import { StateProps } from '../../model/state.enum';
import { UserSession } from 'src/app/feature/login/model/user-session.model';
import { forkJoin, of } from 'rxjs';
import { catchError, switchMap, map } from 'rxjs/operators';
import { Modal } from 'bootstrap';

@Component({
  selector: 'app-activities-table',
  templateUrl: './activities-table.component.html',
  styleUrls: ['./activities-table.component.scss']
})
export class ActivitiesTableComponent implements OnInit, OnChanges {
  // Inputs para cargar desde backend
  @Input() nombreArea: string = '';
  @Input() tipoEstructura: 'direccion' | 'area' | 'subarea' = 'direccion';

  @Input() rutaRedireccion: string = '';
  @Input() terminoBusqueda: string = '';
  @Output() actividadSeleccionada = new EventEmitter<any>();

  // Propiedades para ordenamiento
  columnaOrdenamiento: string = '';
  direccionOrdenamiento: 'asc' | 'desc' = 'asc';
  actividadesOrdenadas: ActivityResponse[] = [];
  actividadesCargadas: ActivityResponse[] = []; // Actividades cargadas desde backend o recibidas como input

  // Propiedades para el modal de fechas programadas
  actividadSeleccionadaParaModal: ActivityResponse | null = null;
  actividadSeleccionadaEdicion: ActivityResponse | null = null;

  // Estados de carga
  cargando = false;
  error = '';

  // Paginación
  p = 1;
  totalElementos = 0;
  private identificadorEstructuraResuelto: string = '';

  // Mapa para almacenar fechas programadas más cercanas (identificador actividad -> fecha)
  fechasProgramadasMap: Map<string, Date | null> = new Map();

  // Mapa para almacenar ejecuciones de cada actividad (identificador actividad -> ejecuciones)
  ejecucionesMap: Map<string, ActivityExecutionResponse[]> = new Map();

  get esColaborador(): boolean {
    const session = this.stateService.getState(StateProps.USER_SESSION) as UserSession;
    const rolesPermitidos = ['ADMINISTRADOR_DIRECCION', 'ADMINISTRADOR_AREA'];
    return session ? !rolesPermitidos.includes(session.rol) : false;
  }

  constructor(
    private activityService: ActivityService,
    private departmentService: DepartmentService,
    private areaService: AreaService,
    private subAreaService: SubAreaService,
    private cdr: ChangeDetectorRef,
    private stateService: StateService
  ) {}

  ngOnInit() {
    if (this.nombreArea) {
      this.cargarActividades();
    } else {
      this.cargando = false;
      this.aplicarFiltrosYOrdenamiento();
    }
  }

  ngOnChanges(changes: SimpleChanges) {
    // Si cambian los parámetros para cargar desde backend
    if (changes['nombreArea'] || changes['tipoEstructura']) {
      if (this.nombreArea) {
        this.p = 1;
        this.identificadorEstructuraResuelto = '';
        this.cargarActividades();
      }
    }

    if (changes['terminoBusqueda']) {
      this.aplicarFiltrosYOrdenamiento();
    }
  }

  /**
   * Carga las actividades desde el backend según el tipo de estructura
   * Método público para poder recargar desde componentes padre
   */
  recargarActividades(): void {
    if (this.nombreArea && this.tipoEstructura) {
      this.cargarActividades();
    }
  }

  /**
   * Carga las actividades desde el backend según el tipo de estructura
   */
  private cargarActividades(): void {
    if (!this.nombreArea || !this.tipoEstructura) {
      return;
    }

    this.cargando = true;
    this.error = '';

    // Si ya tenemos el identificador resuelto, cargar directamente
    if (this.identificadorEstructuraResuelto) {
      this.cargarActividadesPaginadas(this.identificadorEstructuraResuelto);
      return;
    }

    // Primero consultar por nombre para obtener el identificador
    let consultaPorNombre$: any;

    switch (this.tipoEstructura) {
      case 'direccion':
        consultaPorNombre$ = this.departmentService.consultarPorNombre(this.nombreArea);
        break;
      case 'area':
        consultaPorNombre$ = this.areaService.consultarPorNombre(this.nombreArea);
        break;
      case 'subarea':
        consultaPorNombre$ = this.subAreaService.consultarPorNombre(this.nombreArea);
        break;
      default:
        this.error = 'Tipo de estructura no válido';
        this.cargando = false;
        return;
    }

    consultaPorNombre$
      .pipe(
        switchMap((estructura: any) => {
          if (!estructura || !estructura.identificador) {
            throw new Error('No se pudo obtener el identificador de la estructura');
          }

          this.identificadorEstructuraResuelto = estructura.identificador;
          return this.consultarActividadesPaginadas(estructura.identificador);
        }),
        catchError((err) => {
          if (err?.status === 403 && err?.error?.mensaje) {
            this.error = err.error.mensaje;
          } else {
            this.error = 'Error al cargar las actividades. Por favor, intente nuevamente.';
          }
          return of({ content: [], totalElements: 0 });
        })
      )
      .subscribe({
        next: (response: any) => {
          this.actividadesCargadas = response.content || [];
          this.totalElementos = response.totalElements || 0;
          this.cargarFechasProgramadas(this.actividadesCargadas);
        },
        error: () => {
          this.cargando = false;
        }
      });
  }

  private cargarActividadesPaginadas(identificador: string): void {
    this.consultarActividadesPaginadas(identificador)
      .pipe(
        catchError((err) => {
          if (err?.status === 403 && err?.error?.mensaje) {
            this.error = err.error.mensaje;
          } else {
            this.error = 'Error al cargar las actividades. Por favor, intente nuevamente.';
          }
          return of({ content: [], totalElements: 0 });
        })
      )
      .subscribe({
        next: (response: any) => {
          this.actividadesCargadas = response.content || [];
          this.totalElementos = response.totalElements || 0;
          this.cargarFechasProgramadas(this.actividadesCargadas);
        },
        error: () => {
          this.cargando = false;
        }
      });
  }

  private consultarActividadesPaginadas(identificador: string): any {
    const pagina = this.p - 1;
    switch (this.tipoEstructura) {
      case 'direccion':
        return this.activityService.consultarPorDireccionPaginado(identificador, pagina);
      case 'area':
        return this.activityService.consultarPorAreaPaginado(identificador, pagina);
      case 'subarea':
        return this.activityService.consultarPorSubareaPaginado(identificador, pagina);
      default:
        return of({ content: [], totalElements: 0 });
    }
  }

  onPageChange(page: number): void {
    this.p = page;
    if (this.identificadorEstructuraResuelto) {
      this.cargarActividadesPaginadas(this.identificadorEstructuraResuelto);
    }
  }

  /**
   * Obtiene el nombre del colaborador desde el modelo
   */
  obtenerNombreColaborador(actividad: ActivityResponse): string {
    if (!actividad || !actividad.nombreColaborador) {
      return 'Sin colaborador';
    }
    return actividad.nombreColaborador;
  }

  /**
   * Carga las fechas programadas más cercanas para todas las actividades
   */
  private cargarFechasProgramadas(actividades: ActivityResponse[]): void {
    if (!actividades || actividades.length === 0) {
      this.cargando = false;
      this.aplicarFiltrosYOrdenamiento();
      return;
    }

    // Consultar ejecuciones para todas las actividades en paralelo
    const consultas = actividades.map(actividad =>
      this.activityService.consultarEjecuciones(actividad.identificador).pipe(
        map((ejecuciones: ActivityExecutionResponse[]) => {
          const fechaMasCercana = this.calcularFechaMasCercana(ejecuciones);
          return {
            identificador: actividad.identificador,
            fecha: fechaMasCercana,
            ejecuciones: ejecuciones
          };
        }),
        catchError(() => of({
          identificador: actividad.identificador,
          fecha: null,
          ejecuciones: [] as ActivityExecutionResponse[]
        }))
      )
    );

    forkJoin(consultas).subscribe({
      next: (resultados) => {
        resultados.forEach(r => {
          this.fechasProgramadasMap.set(r.identificador, r.fecha);
          this.ejecucionesMap.set(r.identificador, r.ejecuciones);
        });
        this.cargando = false;
        this.aplicarFiltrosYOrdenamiento();
        this.cdr.detectChanges();
      },
      error: () => {
        this.cargando = false;
        this.aplicarFiltrosYOrdenamiento();
        this.cdr.detectChanges();
      }
    });
  }

  /**
   * Calcula la fecha programada más cercana a hoy desde las ejecuciones
   */
  private calcularFechaMasCercana(ejecuciones: ActivityExecutionResponse[]): Date | null {
    if (!ejecuciones || ejecuciones.length === 0) {
      return null;
    }

    const hoy = new Date();
    hoy.setHours(0, 0, 0, 0);

    // Convertir todas las fechas programadas a Date y filtrar las válidas
    const fechasValidas = ejecuciones
      .map(ej => {
        if (!ej.fechaProgramada) return null;
        const fecha = this.parseFechaLocal(ej.fechaProgramada);
        return isNaN(fecha.getTime()) ? null : fecha;
      })
      .filter(fecha => fecha !== null) as Date[];

    if (fechasValidas.length === 0) {
      return null;
    }

    // Encontrar la fecha más cercana a hoy
    let fechaMasCercana: Date | null = null;
    let diferenciaMinima = Infinity;

    fechasValidas.forEach(fecha => {
      const diferencia = Math.abs(fecha.getTime() - hoy.getTime());
      if (diferencia < diferenciaMinima) {
        diferenciaMinima = diferencia;
        fechaMasCercana = fecha;
      }
    });

    return fechaMasCercana;
  }

  /**
   * Parsea una fecha string del backend como fecha local (evita desfase por zona horaria)
   * Soporta formatos: 'YYYY-MM-DD' y ISO estándar.
   */
  private parseFechaLocal(fechaStr: string): Date {
    // Caso 'YYYY-MM-DD'
    const soloFecha = /^\d{4}-\d{2}-\d{2}$/;
    if (soloFecha.test(fechaStr)) {
      const [y, m, d] = fechaStr.split('-').map(Number);
      return new Date(y, m - 1, d);
    }
    // Fallback ISO: si no trae zona horaria y queda en 00:00Z, ajustar a local
    const d = new Date(fechaStr);
    if (!isNaN(d.getTime())) {
      return d;
    }
    // Último recurso: retornar fecha inválida para que el filtro la descarte
    return new Date('invalid');
  }

  /**
   * Aplica filtros de búsqueda y ordenamiento a las actividades
   */
  private aplicarFiltrosYOrdenamiento(): void {
    // Primero aplicar filtro de búsqueda
    let actividadesFiltradas = this.actividadesCargadas;

    if (this.terminoBusqueda && this.terminoBusqueda.trim() !== '') {
      const termino = this.terminoBusqueda.toLowerCase().trim();
      actividadesFiltradas = this.actividadesCargadas.filter(actividad =>
        actividad.nombre.toLowerCase().includes(termino) ||
        (actividad.nombreColaborador && actividad.nombreColaborador.toLowerCase().includes(termino)) ||
        this.calcularEstado(actividad).toLowerCase().includes(termino) ||
        (this.obtenerFechaProgramada(actividad) && this.obtenerFechaProgramada(actividad)!.toLocaleDateString().toLowerCase().includes(termino))
      );
    }

    // Luego aplicar ordenamiento
    this.actividadesOrdenadas = [...actividadesFiltradas];
    if (this.columnaOrdenamiento) {
      this.ordenarPorColumna(this.columnaOrdenamiento, this.direccionOrdenamiento);
    }
  }

  /**
   * Maneja el clic en el encabezado de una columna para ordenar
   */
  ordenarPor(columna: string): void {
    if (this.columnaOrdenamiento === columna) {
      // Si ya está ordenando por esta columna, cambiar la dirección
      this.direccionOrdenamiento = this.direccionOrdenamiento === 'asc' ? 'desc' : 'asc';
    } else {
      // Si es una nueva columna, empezar con ascendente
      this.columnaOrdenamiento = columna;
      this.direccionOrdenamiento = 'asc';
    }

    this.aplicarFiltrosYOrdenamiento();
  }

  /**
   * Ordena las actividades por la columna especificada
   */
  private ordenarPorColumna(columna: string, direccion: 'asc' | 'desc'): void {
    this.actividadesOrdenadas.sort((a, b) => {
      let valorA: any;
      let valorB: any;

      switch (columna) {
        case 'nombreActividad':
          valorA = a.nombre.toLowerCase();
          valorB = b.nombre.toLowerCase();
          break;
        case 'colaborador':
          valorA = (a.nombreColaborador || '').toLowerCase();
          valorB = (b.nombreColaborador || '').toLowerCase();
          break;
        case 'fechaCreacion':
          valorA = new Date(a.fechaCreacion).getTime();
          valorB = new Date(b.fechaCreacion).getTime();
          break;
        case 'fechaProgramada':
          const fechaA = this.obtenerFechaProgramada(a);
          const fechaB = this.obtenerFechaProgramada(b);
          valorA = fechaA ? new Date(fechaA).getTime() : 0;
          valorB = fechaB ? new Date(fechaB).getTime() : 0;
          break;
        case 'estado':
          valorA = this.calcularEstado(a).toLowerCase();
          valorB = this.calcularEstado(b).toLowerCase();
          break;
        default:
          return 0;
      }

      if (valorA < valorB) {
        return direccion === 'asc' ? -1 : 1;
      }
      if (valorA > valorB) {
        return direccion === 'asc' ? 1 : -1;
      }
      return 0;
    });
  }

  /**
   * Obtiene la clase CSS para el indicador de ordenamiento
   */
  getSortClass(columna: string): string {
    if (this.columnaOrdenamiento !== columna) {
      return 'sort-indicator';
    }
    return `sort-indicator sort-${this.direccionOrdenamiento}`;
  }

  /**
   * Función para realizar el tracking de las actividades por identificador
   */
  trackByActividadId(index: number, actividad: ActivityResponse): string {
    return actividad.identificador;
  }

  /**
   * Obtiene la clase CSS para el estado de la actividad
   */
  getStatusClass(actividad: ActivityResponse): string {
    const estado = this.calcularEstado(actividad);
    switch (estado) {
      case EstadoActividad.PENDIENTE:
        return 'status-pendiente';
      case EstadoActividad.EN_CURSO:
        return 'status-en-curso';
      case EstadoActividad.FINALIZADO:
        return 'status-finalizado';
      default:
        return 'status-pendiente';
    }
  }

  /**
   * Calcula el estado de la actividad basándose en los estados de las ejecuciones
   * Si al menos una ejecución tiene estado diferente de FINALIZADO → PENDIENTE
   * Si todas las ejecuciones tienen estado FINALIZADO → FINALIZADO
   */
  calcularEstado(actividad: ActivityResponse): EstadoActividad {
    const ejecuciones = this.ejecucionesMap.get(actividad.identificador);

    // Si no hay ejecuciones, retornar PENDIENTE por defecto
    if (!ejecuciones || ejecuciones.length === 0) {
      return EstadoActividad.PENDIENTE;
    }

    // Verificar si todas las ejecuciones están FINALIZADAS
    const todasFinalizadas = ejecuciones.every(ej =>
      ej.estadoActividad &&
      ej.estadoActividad.nombre === EstadoActividad.FINALIZADO
    );

    // Si todas están finalizadas, retornar FINALIZADO
    if (todasFinalizadas) {
      return EstadoActividad.FINALIZADO;
    }

    // Si al menos una no está finalizada, retornar PENDIENTE
    return EstadoActividad.PENDIENTE;
  }

  /**
   * Obtiene la fecha programada más cercana de la actividad
   */
  obtenerFechaProgramada(actividad: ActivityResponse): Date | null {
    return this.fechasProgramadasMap.get(actividad.identificador) || null;
  }

  /**
   * Maneja el clic en el botón "Gestionar Actividad"
   */
  realizarActividad(actividad: ActivityResponse): void {
    // Emitir evento para que el componente padre maneje la lógica
    this.actividadSeleccionada.emit(actividad);

    // Configurar datos para el modal
    this.actividadSeleccionadaParaModal = actividad;

    // Abrir el modal
    this.abrirModalFechas();
  }

  abrirModalEditar(actividad: ActivityResponse): void {
    this.actividadSeleccionadaEdicion = { ...actividad };

    setTimeout(() => {
      const modalElement = document.getElementById('edit-activity-modal');
      if (modalElement) {
        const modal = Modal.getInstance(modalElement) || new Modal(modalElement);
        modal.show();
      }
    });
  }

  /**
   * Abre el modal de fechas programadas
   */
  private abrirModalFechas(): void {
    // Usar Bootstrap modal API
    const modalElement = document.getElementById('date-selector-modal');
    if (modalElement) {
      const modal = new (window as any).bootstrap.Modal(modalElement);
      modal.show();
    }
  }

  /**
   * Cierra el modal de fechas programadas
   */
  cerrarModalFechas(): void {
    const actividadId = this.actividadSeleccionadaParaModal?.identificador;

    const modalElement = document.getElementById('date-selector-modal');
    if (modalElement) {
      const modal = (window as any).bootstrap.Modal.getInstance(modalElement);
      if (modal) {
        modal.hide();
        // Asegurar que el backdrop se elimine correctamente
        setTimeout(() => {
          const backdrop = document.querySelector('.modal-backdrop');
          if (backdrop) {
            backdrop.remove();
          }
          // Remover la clase modal-open del body
          document.body.classList.remove('modal-open');
          document.body.style.overflow = '';
          document.body.style.paddingRight = '';
        }, 150);
      }
    }
    this.actividadSeleccionadaParaModal = null;

    if (actividadId) {
      this.actualizarEjecucionesDeActividad(actividadId);
    }
  }

  /**
   * Reconsulta las ejecuciones de una actividad específica para reflejar cambios de estado
   */
  private actualizarEjecucionesDeActividad(actividadId: string): void {
    this.activityService.consultarEjecuciones(actividadId).pipe(
      catchError(() => of([] as ActivityExecutionResponse[]))
    ).subscribe(ejecuciones => {
      this.ejecucionesMap.set(actividadId, ejecuciones);
      const fechaMasCercana = this.calcularFechaMasCercana(ejecuciones);
      this.fechasProgramadasMap.set(actividadId, fechaMasCercana);
      this.aplicarFiltrosYOrdenamiento();
      this.cdr.detectChanges();
    });
  }

  /**
   * Maneja el clic en el botón "Ver" de una fecha programada
   * TODO: Implementar la lógica para ver los detalles de la fecha programada
   */
  verFechaProgramada(fechaProgramada: FechaProgramada): void {
    // TODO: Implementar la lógica para ver los detalles de la fecha programada
  }

}
