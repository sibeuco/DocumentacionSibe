import { Component, Input, Output, EventEmitter, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { Router } from '@angular/router';
import { FechaProgramada, EstadoFechaProgramada } from '../../model/fecha-programada.model';
import { ActivityResponse } from '../../model/activity.model';
import { ActivityService } from '../../service/activity.service';
import { ActivityExecutionResponse } from '../../model/activity-execution.model';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';

@Component({
  selector: 'app-date-selector',
  templateUrl: './date-selector.component.html',
  styleUrls: ['./date-selector.component.scss']
})
export class DateSelectorComponent implements OnInit, OnChanges {
  @Input() actividad: ActivityResponse | null = null;
  @Input() redirectLink: string = ''; // Link para redirección
  @Output() verFecha = new EventEmitter<FechaProgramada>();
  @Output() cerrarModal = new EventEmitter<void>();

  fechasOrdenadas: FechaProgramada[] = [];
  cargando = false;
  error = '';
  p = 1;
  totalElementos = 0;
  private readonly STORAGE_KEY = 'selectedActivityInfo';
  private ejecuciones: ActivityExecutionResponse[] = [];
  private ejecucionesPorId: Map<number, ActivityExecutionResponse> = new Map();

  constructor(
    private router: Router,
    private activityService: ActivityService
  ) { }

  ngOnInit(): void {
    if (this.actividad) {
      this.cargarEjecuciones();
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['actividad'] && this.actividad) {
      this.p = 1;
      this.cargarEjecuciones();
    }
  }

  /**
   * Carga las ejecuciones de la actividad desde el servicio
   */
  private cargarEjecuciones(): void {
    if (!this.actividad || !this.actividad.identificador) {
      this.fechasOrdenadas = [];
      return;
    }

    this.cargando = true;
    this.error = '';

    this.activityService.consultarEjecucionesPaginado(this.actividad.identificador, this.p - 1)
      .pipe(
        catchError((err) => {
          if (err?.status === 403 && err?.error?.mensaje) {
            this.error = err.error.mensaje;
          } else {
            this.error = 'Error al cargar las fechas programadas.';
          }
          this.cargando = false;
          this.ejecuciones = [];
          this.ejecucionesPorId.clear();
          return of({ content: [], totalElements: 0 });
        })
      )
      .subscribe({
        next: (response: any) => {
          const ejecuciones = response.content || [];
          this.totalElementos = response.totalElements || 0;
          this.ejecuciones = ejecuciones;
          this.ejecucionesPorId.clear();
          this.fechasOrdenadas = this.mapearEjecucionesAFechas(ejecuciones);
          this.cargando = false;
        },
        error: () => {
          this.ejecuciones = [];
          this.ejecucionesPorId.clear();
          this.cargando = false;
        }
      });
  }

  onPageChange(page: number): void {
    this.p = page;
    this.cargarEjecuciones();
  }

  /**
   * Mapea las ejecuciones a fechas programadas
   */
  private mapearEjecucionesAFechas(ejecuciones: ActivityExecutionResponse[]): FechaProgramada[] {
    return ejecuciones
      .filter(ej => ej.fechaProgramada)
      .map((ej, index) => {
        const id = this.generarIdEjecucion(ej, index);
        this.ejecucionesPorId.set(id, ej);
        return {
          id,
          fecha: this.parseFechaLocal(ej.fechaProgramada),
          estado: this.mapearEstado(ej.estadoActividad?.nombre),
          actividadId: parseInt(this.actividad?.identificador || '0') || 0
        };
      })
      .sort((a, b) => a.fecha.getTime() - b.fecha.getTime());
  }

  /**
   * Mapea el nombre del estado a EstadoFechaProgramada
   */
  private mapearEstado(nombreEstado: string | undefined): EstadoFechaProgramada {
    if (!nombreEstado) {
      return EstadoFechaProgramada.PENDIENTE;
    }

    const estadoNormalizado = this.normalizarTexto(nombreEstado);

    switch (estadoNormalizado) {
      case 'pendiente':
        return EstadoFechaProgramada.PENDIENTE;
      case 'en curso':
        return EstadoFechaProgramada.EN_CURSO;
      case 'finalizada':
        return EstadoFechaProgramada.FINALIZADO;
      default:
        return EstadoFechaProgramada.PENDIENTE;
    }
  }

  /**
   * Parsea una fecha string del backend como fecha local
   */
  private parseFechaLocal(fechaStr: string): Date {
    // Caso 'YYYY-MM-DD'
    const soloFecha = /^\d{4}-\d{2}-\d{2}$/;
    if (soloFecha.test(fechaStr)) {
      const [y, m, d] = fechaStr.split('-').map(Number);
      return new Date(y, m - 1, d);
    }
    // Fallback ISO
    const d = new Date(fechaStr);
    if (!isNaN(d.getTime())) {
      return d;
    }
    // Último recurso: retornar fecha actual
    return new Date();
  }

  /**
   * Obtiene la clase CSS para el estado de la fecha programada
   */
  getStatusClass(estado: EstadoFechaProgramada): string {
    switch (estado) {
      case EstadoFechaProgramada.PENDIENTE:
        return 'status-pendiente';
      case EstadoFechaProgramada.EN_CURSO:
        return 'status-en-curso';
      case EstadoFechaProgramada.FINALIZADO:
        return 'status-finalizada';
      default:
        return 'status-pendiente';
    }
  }

  /**
   * Maneja el clic en el botón "Ver"
   */
  verFechaProgramada(fechaProgramada: FechaProgramada): void {
    // Emitir el evento para mantener compatibilidad
    this.verFecha.emit(fechaProgramada);
    const ejecucion = this.obtenerEjecucionPorFecha(fechaProgramada);

    this.guardarSeleccion(this.actividad, ejecucion || null);

    // Cerrar el modal correctamente antes de navegar
    this.cerrarModalCompletamente();

    // Si hay un link de redirección, navegar a él
    if (this.redirectLink) {
      this.router.navigate([this.redirectLink], {
        queryParams: {
          actividadId: this.actividad?.identificador || null,
          ejecucionId: ejecucion?.identificador || null
        },
        state: {
          actividad: this.actividad,
          ejecucion: ejecucion || null
        }
      });
    }
  }

  /**
   * Cierra el modal
   */
  cerrar(): void {
    this.cerrarModal.emit();
  }

  /**
   * Cierra el modal completamente, eliminando el backdrop
   */
  private cerrarModalCompletamente(): void {
    const modalElement = document.getElementById('date-selector-modal');
    if (modalElement) {
      const modal = (window as any).bootstrap.Modal.getInstance(modalElement);
      if (modal) {
        modal.hide();
        // Forzar la eliminación del backdrop después de un pequeño delay
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
    // Emitir el evento de cerrar modal
    this.cerrarModal.emit();
  }

  /**
   * Función para realizar el tracking de las fechas por ID
   */
  trackByFechaId(index: number, fecha: FechaProgramada): number {
    return fecha.id;
  }

  private obtenerEjecucionPorFecha(fechaProgramada: FechaProgramada): ActivityExecutionResponse | undefined {
    if (!fechaProgramada) {
      return undefined;
    }

    const ejecucionPorId = this.ejecucionesPorId.get(fechaProgramada.id);
    if (ejecucionPorId) {
      return ejecucionPorId;
    }

    const timestampFecha = fechaProgramada.fecha?.getTime();
    if (timestampFecha) {
      return this.ejecuciones.find(ej => this.parseFechaLocal(ej.fechaProgramada).getTime() === timestampFecha);
    }

    return undefined;
  }

  private generarIdEjecucion(ejecucion: ActivityExecutionResponse, index: number): number {
    const idNumerico = Number(ejecucion.identificador);
    if (!isNaN(idNumerico) && isFinite(idNumerico) && idNumerico > 0) {
      return idNumerico;
    }
    return index + 1;
  }

  private guardarSeleccion(actividad: ActivityResponse | null, ejecucion: ActivityExecutionResponse | null): void {
    const storage = this.obtenerStorage();
    if (!storage) {
      return;
    }

    if (!actividad) {
      storage.removeItem(this.STORAGE_KEY);
      return;
    }

    try {
      storage.setItem(this.STORAGE_KEY, JSON.stringify({ actividad, ejecucion }));
    } catch {
      storage.removeItem(this.STORAGE_KEY);
    }
  }

  private obtenerStorage(): Storage | null {
    if (typeof window === 'undefined') {
      return null;
    }
    return window.sessionStorage;
  }

  private normalizarTexto(valor: string): string {
    return valor
      .toLowerCase()
      .normalize('NFD')
      .replace(/[\u0300-\u036f]/g, '')
      .replace(/\s+/g, ' ')
      .trim();
  }
}
