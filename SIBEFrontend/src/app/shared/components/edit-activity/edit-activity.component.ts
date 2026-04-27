import { Component, EventEmitter, Output, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
import { Modal } from 'bootstrap';
import { IndicatorService } from 'src/app/feature/manage-indicators/service/indicator.service';
import { IndicatorResponse } from 'src/app/feature/manage-indicators/model/indicator.model';
import { UserService } from 'src/app/shared/service/user.service';
import { UserResponse } from 'src/app/shared/model/user.model';
import { DepartmentService } from 'src/app/shared/service/department.service';
import { AreaService } from 'src/app/shared/service/area.service';
import { SubAreaService } from 'src/app/shared/service/subarea.service';
import { DepartmentResponse } from 'src/app/shared/model/department.model';
import { AreaResponse } from 'src/app/shared/model/area.model';
import { SubAreaResponse } from 'src/app/shared/model/subarea.model';
import { ActivityService } from 'src/app/shared/service/activity.service';
import { ActivityExecutionResponse, EditActivityExecutionRquest } from 'src/app/shared/model/activity-execution.model';
import { ActivityResponse, EditActivityRequest, EstadoActividad } from 'src/app/shared/model/activity.model';

@Component({
  selector: 'app-edit-activity',
  templateUrl: './edit-activity.component.html',
  styleUrls: ['./edit-activity.component.scss']
})
export class EditActivityComponent implements OnInit, OnChanges {

  @Input() actividad: ActivityResponse | null = null;
  @Input() nombreArea: string = '';
  @Input() tipoEstructura: 'direccion' | 'area' | 'subarea' | '' = '';
  @Output() actividadEditada = new EventEmitter<void>();

  actividadForm = {
    nombre: '',
    objetivo: '',
    semestre: '',
    rutaInsumos: '',
    indicador: '',
    colaborador: '',
    area: '',
    tipoArea: '',
    fechasProgramadas: [] as EditActivityExecutionRquest[],
  };

  indicadores: IndicatorResponse[] = [];
  indicadorSeleccionado: string = '';

  usuarios: UserResponse[] = [];
  colaboradorSeleccionado: string = '';

  direcciones: DepartmentResponse[] = [];
  areas: AreaResponse[] = [];
  subareas: SubAreaResponse[] = [];
  listaFiltrada: { identificador: string; nombre: string }[] = [];
  areaSeleccionada: string = '';
  tipoEstructuraSeleccionado: 'direccion' | 'area' | 'subarea' | '' = '';

  fechaTemporal: string = '';
  errorFecha: string = '';
  minFechaProgramada: string = this.obtenerFechaActualISO();

  cargando = false;
  cargandoDatosActividad = false;
  error = '';
  mensajeExito = '';

  soloColaboradorEditable = false;
  tieneEjecucionEnCurso = false;
  ejecuciones: ActivityExecutionResponse[] = [];

  constructor(
    private indicatorService: IndicatorService,
    private userService: UserService,
    private departmentService: DepartmentService,
    private areaService: AreaService,
    private subAreaService: SubAreaService,
    private activityService: ActivityService
  ) { }

  ngOnInit(): void {
    this.cargarIndicadores();
    this.cargarUsuarios();
    this.cargarEstructurasOrganizacionales();
  }

  private obtenerFechaActualISO(): string {
    const hoy = new Date();
    hoy.setHours(0, 0, 0, 0);
    const year = hoy.getFullYear();
    const month = String(hoy.getMonth() + 1).padStart(2, '0');
    const day = String(hoy.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['actividad'] && this.actividad) {
      this.cargarDatosActividad();
    }

    if ((changes['nombreArea'] || changes['tipoEstructura']) && this.nombreArea && this.tipoEstructura) {
      this.tipoEstructuraSeleccionado = this.tipoEstructura;
      this.buscarYPrecargarArea();
    }
  }

  cargarIndicadores(): void {
    this.indicatorService.consultarIndicadoresParaActividades().subscribe({
      next: (indicadores) => {
        this.indicadores = indicadores;
        this.asegurarIndicadorEnLista();
      },
      error: () => {}
    });
  }

  cargarUsuarios(): void {
    this.userService.consultarUsuarios().subscribe({
      next: (usuarios) => {
        this.usuarios = usuarios;
        this.asegurarColaboradorEnLista();
      },
      error: () => {}
    });
  }

  cargarEstructurasOrganizacionales(): void {
    this.departmentService.consultarDirecciones().subscribe({
      next: (direcciones) => {
        this.direcciones = direcciones;
        this.actualizarListaFiltrada();
      },
      error: () => {}
    });

    this.areaService.consultarAreas().subscribe({
      next: (areas) => {
        this.areas = areas;
        this.actualizarListaFiltrada();
      },
      error: () => {}
    });

    this.subAreaService.consultarSubareas().subscribe({
      next: (subareas) => {
        this.subareas = subareas;
        this.actualizarListaFiltrada();
      },
      error: () => {}
    });
  }

  private cargarDatosActividad(): void {
    if (!this.actividad) {
      this.resetFormulario();
      return;
    }

    this.cargandoDatosActividad = true;
    this.error = '';
    this.mensajeExito = '';

    this.tipoEstructuraSeleccionado = this.tipoEstructura || this.obtenerTipoEstructuraDesdeActividad();

    this.actividadForm = {
      nombre: this.actividad.nombre || '',
      objetivo: this.actividad.objetivo || '',
      semestre: this.actividad.semestre || '',
      rutaInsumos: this.actividad.rutaInsumos || '',
      indicador: this.actividad.indicador?.identificador || '',
      colaborador: this.actividad.colaborador || '',
      area: this.obtenerAreaDesdeActividad(this.actividad),
      tipoArea: this.obtenerTipoAreaDesdeActividad(this.actividad),
      fechasProgramadas: []
    };

    this.indicadorSeleccionado = this.actividadForm.indicador;
    this.colaboradorSeleccionado = this.actividadForm.colaborador;
    this.areaSeleccionada = this.actividadForm.area;

    this.asegurarIndicadorEnLista();
    this.asegurarColaboradorEnLista();

    this.buscarYPrecargarArea();
    this.cargarEjecuciones(this.actividad.identificador);
  }

  private cargarEjecuciones(identificador: string): void {
    if (!identificador) {
      this.cargandoDatosActividad = false;
      return;
    }

    this.activityService.consultarEjecuciones(identificador).subscribe({
      next: (ejecuciones) => {
        this.ejecuciones = ejecuciones || [];
        // Mapear las ejecuciones a EditActivityExecutionRquest con identificador y fechaProgramada
        this.actividadForm.fechasProgramadas = (this.ejecuciones || [])
          .map(ej => ({
            identificador: ej.identificador || '',
            fechaProgramada: ej.fechaProgramada || ''
          }))
          .filter(fecha => !!fecha.fechaProgramada);

        const hayEnCurso = (this.ejecuciones || []).some(ej => this.esEstadoEnCurso(ej.estadoActividad?.nombre));
        this.tieneEjecucionEnCurso = hayEnCurso;

        const hayFinalizadas = (this.ejecuciones || []).some(ej => this.esEstadoFinalizado(ej.estadoActividad?.nombre));
        this.soloColaboradorEditable = hayFinalizadas && !hayEnCurso;

        if (!hayFinalizadas && !this.actividadForm.fechasProgramadas.length) {
          // Si no hay ejecuciones, usar las fechas programadas de la actividad como nuevas (sin identificador)
          this.actividadForm.fechasProgramadas = (this.actividad?.fechasProgramadas ?? [])
            .map(fecha => ({
              identificador: null as any,
              fechaProgramada: fecha
            }));
        }

        this.cargandoDatosActividad = false;
      },
      error: () => {
        this.soloColaboradorEditable = false;
        this.tieneEjecucionEnCurso = false;
        // En caso de error, usar las fechas programadas de la actividad como nuevas (sin identificador)
        this.actividadForm.fechasProgramadas = (this.actividad?.fechasProgramadas ?? [])
          .map(fecha => ({
            identificador: null as any,
            fechaProgramada: fecha
          }));
        this.cargandoDatosActividad = false;
      }
    });
  }

  private esEstadoFinalizado(nombreEstado?: string | null): boolean {
    if (!nombreEstado) {
      return false;
    }

    const normalizado = nombreEstado.toLowerCase();
    return normalizado === EstadoActividad.FINALIZADO.toLowerCase();
  }

  private esEstadoEnCurso(nombreEstado?: string | null): boolean {
    if (!nombreEstado) {
      return false;
    }

    const normalizado = nombreEstado.toLowerCase();
    return normalizado === EstadoActividad.EN_CURSO.toLowerCase();
  }

  private obtenerTipoEstructuraDesdeActividad(): 'direccion' | 'area' | 'subarea' | '' {
    const tipo = this.actividad?.area?.tipoArea || this.actividad?.tipoArea || '';

    switch (tipo?.toUpperCase?.()) {
      case 'DIRECCION':
        return 'direccion';
      case 'AREA':
        return 'area';
      case 'SUBAREA':
        return 'subarea';
      default:
        return '';
    }
  }

  private obtenerAreaDesdeActividad(actividad: ActivityResponse | null): string {
    if (!actividad) {
      return '';
    }

    const areaDatos = actividad.area;
    if (areaDatos && typeof areaDatos === 'object' && 'area' in areaDatos) {
      return areaDatos.area;
    }

    const areaSimple = (actividad as unknown as { area?: string }).area;
    if (areaSimple && typeof areaSimple === 'string') {
      return areaSimple;
    }

    return actividad.areaIdentificador || '';
  }

  private obtenerTipoAreaDesdeActividad(actividad: ActivityResponse | null): string {
    if (!actividad) {
      return '';
    }

    const areaDatos = actividad.area;
    if (areaDatos && typeof areaDatos === 'object' && 'tipoArea' in areaDatos) {
      return areaDatos.tipoArea;
    }

    if (actividad.tipoArea) {
      return actividad.tipoArea;
    }

    return this.determinarTipoArea();
  }

  private actualizarListaFiltrada(): void {
    if (!this.tipoEstructuraSeleccionado) {
      this.listaFiltrada = [];
      return;
    }

    if (this.tipoEstructuraSeleccionado === 'direccion') {
      this.listaFiltrada = this.direcciones.map(direccion => ({
        identificador: direccion.identificador,
        nombre: direccion.nombre
      }));
    } else if (this.tipoEstructuraSeleccionado === 'area') {
      this.listaFiltrada = this.areas.map(area => ({
        identificador: area.identificador,
        nombre: area.nombre
      }));
    } else if (this.tipoEstructuraSeleccionado === 'subarea') {
      this.listaFiltrada = this.subareas.map(subarea => ({
        identificador: subarea.identificador,
        nombre: subarea.nombre
      }));
    } else {
      this.listaFiltrada = [];
    }
  }

  onTipoEstructuraChange(): void {
    if (this.nombreArea && this.tipoEstructura) {
      return;
    }

    this.actividadForm.area = '';
    this.actividadForm.tipoArea = '';
    this.areaSeleccionada = '';
    this.actualizarListaFiltrada();
  }

  private buscarYPrecargarArea(): void {
    if (!this.nombreArea || !this.tipoEstructuraSeleccionado) {
      this.actualizarListaFiltrada();
      return;
    }

    if (this.actividadForm.area) {
      this.actualizarListaFiltrada();
      return;
    }

    let consulta$: any;

    switch (this.tipoEstructuraSeleccionado) {
      case 'direccion':
        consulta$ = this.departmentService.consultarPorNombre(this.nombreArea);
        break;
      case 'area':
        consulta$ = this.areaService.consultarPorNombre(this.nombreArea);
        break;
      case 'subarea':
        consulta$ = this.subAreaService.consultarPorNombre(this.nombreArea);
        break;
      default:
        this.actualizarListaFiltrada();
        return;
    }

    consulta$.subscribe({
      next: (estructura: DepartmentResponse | AreaResponse | SubAreaResponse | null) => {
        if (estructura && estructura.identificador) {
          this.actividadForm.area = estructura.identificador;
          this.actividadForm.tipoArea = this.determinarTipoArea();
          this.areaSeleccionada = estructura.identificador;
        }
        this.actualizarListaFiltrada();
      },
      error: () => {
        this.actualizarListaFiltrada();
      }
    });
  }

  determinarTipoArea(): string {
    if (this.tipoEstructuraSeleccionado === 'direccion') {
      return 'DIRECCION';
    } else if (this.tipoEstructuraSeleccionado === 'area') {
      return 'AREA';
    } else if (this.tipoEstructuraSeleccionado === 'subarea') {
      return 'SUBAREA';
    }
    return this.actividadForm.tipoArea || '';
  }

  agregarFecha() {
    this.errorFecha = '';

    if (!this.fechaTemporal) {
      this.errorFecha = 'Por favor seleccione una fecha';
      return;
    }

    if (this.fechaTemporal < this.minFechaProgramada) {
      this.errorFecha = 'La fecha no puede ser anterior a la fecha actual';
      return;
    }

    // Verificar si la fecha ya está agregada
    const fechaYaExiste = this.actividadForm.fechasProgramadas.some(
      fecha => fecha.fechaProgramada === this.fechaTemporal
    );

    if (fechaYaExiste) {
      this.errorFecha = 'Esta fecha ya está agregada';
      return;
    }

    // Agregar nueva fecha con identificador null (fecha nueva)
    this.actividadForm.fechasProgramadas.push({
      identificador: null as any,
      fechaProgramada: this.fechaTemporal
    });
    this.fechaTemporal = '';
  }

  eliminarFecha(index: number) {
    const fecha = this.actividadForm.fechasProgramadas[index];
    // Solo permitir eliminar fechas nuevas (sin identificador) cuando hay ejecuciones finalizadas
    if (this.soloColaboradorEditable && fecha.identificador) {
      return; // No permitir eliminar fechas existentes
    }
    this.actividadForm.fechasProgramadas.splice(index, 1);
    this.errorFecha = '';
  }

  esFechaEditable(fecha: EditActivityExecutionRquest): boolean {
    // Si hay ejecuciones finalizadas, solo las fechas nuevas (sin identificador) son editables
    if (this.soloColaboradorEditable) {
      return !fecha.identificador; // Si no tiene identificador, es nueva y se puede eliminar
    }
    return true; // Si no hay ejecuciones finalizadas, todas son editables (incluso las existentes)
  }

  mostrarCandado(fecha: EditActivityExecutionRquest): boolean {
    // Solo mostrar candado si hay ejecuciones finalizadas Y la fecha es existente (tiene identificador)
    return this.soloColaboradorEditable && !!fecha.identificador;
  }

  trackByIndex(index: number): number {
    return index;
  }

  editarActividad() {
    if (!this.actividad || !this.actividad.identificador) {
      return;
    }

    if (this.tieneEjecucionEnCurso) {
      return;
    }

    if (!this.validarFormulario()) {
      this.error = 'Por favor, complete todos los campos requeridos';
      return;
    }

    if (!this.actividadForm.area || !this.actividadForm.tipoArea) {
      this.error = 'No se pudo determinar el área de la actividad.';
      return;
    }

    this.cargando = true;
    this.error = '';
    this.mensajeExito = '';

    const request: EditActivityRequest = {
      nombre: this.actividadForm.nombre.trim(),
      objetivo: this.actividadForm.objetivo.trim(),
      semestre: this.actividadForm.semestre.trim(),
      rutaInsumos: this.actividadForm.rutaInsumos.trim(),
      indicador: this.indicadorSeleccionado.trim(),
      colaborador: this.colaboradorSeleccionado.trim(),
      fechasProgramada: this.actividadForm.fechasProgramadas.map(f => ({
        identificador: f.identificador || null as any,
        fechaProgramada: f.fechaProgramada.trim()
      })).filter(f => f.fechaProgramada),
      area: {
        area: this.actividadForm.area.trim(),
        tipoArea: this.actividadForm.tipoArea || this.determinarTipoArea()
      }
    };

    this.activityService.modificarActividad(this.actividad.identificador, request).subscribe({
      next: () => {
        this.cargando = false;
        this.mensajeExito = 'Actividad actualizada exitosamente';
        this.actividadEditada.emit();

        setTimeout(() => {
          this.cerrarModal();
          this.resetFormulario();
        }, 1500);
      },
      error: (err) => {
        this.cargando = false;
        if (err?.error?.mensaje) {
          this.error = err.error.mensaje;
        } else if (err?.error?.message) {
          this.error = err.error.message;
        } else if (typeof err?.error === 'string') {
          this.error = err.error;
        } else {
          this.error = 'Error al actualizar la actividad. Por favor, intente nuevamente.';
        }
      }
    });
  }

  private validarFormulario(): boolean {
    if (this.tieneEjecucionEnCurso) {
      return false;
    }

    if (this.soloColaboradorEditable) {
      return !!this.colaboradorSeleccionado && this.colaboradorSeleccionado.trim() !== '';
    }

    return !!(
      this.actividadForm.nombre &&
      this.actividadForm.objetivo &&
      this.actividadForm.semestre &&
      this.actividadForm.rutaInsumos &&
      this.indicadorSeleccionado &&
      this.indicadorSeleccionado.trim() !== '' &&
      this.colaboradorSeleccionado &&
      this.actividadForm.area &&
      this.actividadForm.tipoArea &&
      this.actividadForm.fechasProgramadas.length > 0
    );
  }

  cerrarModal() {
    const modalElement = document.getElementById('edit-activity-modal');
    if (modalElement) {
      const modal = Modal.getInstance(modalElement) || new Modal(modalElement);
      modal.hide();
      setTimeout(() => {
        const backdrops = document.querySelectorAll('.modal-backdrop');
        backdrops.forEach(backdrop => backdrop.remove());
        document.body.classList.remove('modal-open');
        document.body.style.paddingRight = '';
        document.body.style.overflow = '';
      }, 300);
    }
  }

  limpiarFormulario() {
    this.resetFormulario();
  }

  private asegurarIndicadorEnLista(): void {
    if (!this.actividad?.indicador?.identificador || this.indicadores.length === 0) {
      return;
    }
    const existe = this.indicadores.some(i => i.identificador === this.actividad!.indicador.identificador);
    if (!existe) {
      this.indicadores = [...this.indicadores, this.actividad.indicador];
    }
  }

  private asegurarColaboradorEnLista(): void {
    if (!this.actividad?.colaborador || this.usuarios.length === 0) {
      return;
    }
    const existe = this.usuarios.some(u => u.identificador === this.actividad!.colaborador);
    if (!existe) {
      const nombreCompleto = this.actividad.nombreColaborador || '';
      const partes = nombreCompleto.split(' ');
      const nombres = partes.length > 1 ? partes.slice(0, -1).join(' ') : nombreCompleto;
      const apellidos = partes.length > 1 ? partes[partes.length - 1] : '';
      this.usuarios = [...this.usuarios, {
        identificador: this.actividad.colaborador,
        nombres: nombres,
        apellidos: apellidos,
        correo: '',
        identificacion: {} as any,
        tipoUsuario: {} as any,
        estaActivo: false
      }];
    }
  }

  private resetFormulario(): void {
    this.actividadForm = {
      nombre: '',
      objetivo: '',
      semestre: '',
      rutaInsumos: '',
      indicador: '',
      colaborador: '',
      area: '',
      tipoArea: '',
      fechasProgramadas: [],
    };
    this.indicadorSeleccionado = '';
    this.colaboradorSeleccionado = '';
    this.tipoEstructuraSeleccionado = this.tipoEstructura || '';
    this.areaSeleccionada = '';
    this.listaFiltrada = [];
    this.fechaTemporal = '';
    this.errorFecha = '';
    this.error = '';
    this.mensajeExito = '';
    this.soloColaboradorEditable = false;
    this.tieneEjecucionEnCurso = false;
    this.ejecuciones = [];
  }

}
