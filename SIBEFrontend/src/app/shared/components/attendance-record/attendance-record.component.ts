import { Component, Input, Output, EventEmitter, OnInit, OnChanges, SimpleChanges, ViewChild } from '@angular/core';
import { Modal } from 'bootstrap';
import { UniversityMemberService } from '../../service/university-member.service';
import { UniversityMemberResponse } from '../../model/university-member.model';
import { catchError, of } from 'rxjs';
import { finalize } from 'rxjs/operators';
import { ActivityService } from '../../service/activity.service';
import { ActivityExecutionResponse } from '../../model/activity-execution.model';
import { EstadoActividad, ActivityResponse } from '../../model/activity.model';
import { ParticipantRequest, ParticipantResponse } from '../../model/participant.model';
import { ExternalParticipantComponent } from '../external-participant/external-participant.component';

@Component({
  selector: 'app-attendance-record',
  templateUrl: './attendance-record.component.html',
  styleUrls: ['./attendance-record.component.scss']
})
export class AttendanceRecordComponent implements OnInit, OnChanges {

  @ViewChild(ExternalParticipantComponent) private externalParticipantComp!: ExternalParticipantComponent;

  @Input() idActividad: number = 0;
  @Input() actividad: any = null;
  @Input() usuarioLoggeado: any = null;
  @Output() actividadFinalizada = new EventEmitter<any>();
  @Output() actividadCancelada = new EventEmitter<any>();

  // Variables del formulario
  rfidSearch: string = '';
  documentoSearch: string = '';

  // Variables de estado
  buscando: boolean = false;
  guardando: boolean = false;
  mensaje: string = '';
  tipoMensaje: 'success' | 'error' | 'warning' = 'success';
  actividadIniciada: boolean = false;
  ejecucionFinalizada: boolean = false;
  iniciandoActividad: boolean = false;
  cancelandoActividad: boolean = false;
  cargandoParticipantesFinalizados: boolean = false;

  // Lista de participantes
  miembrosAsistencia: UniversityMemberResponse[] = [];

  // Paginación (solo para actividades finalizadas)
  p = 1;
  totalElementos = 0;

  private readonly STORAGE_KEY = 'selectedActivityInfo';
  private ejecucionSeleccionada: ActivityExecutionResponse | null = null;
  ejecucionSeleccionadaId: string | null = null;
  actividadCargada: ActivityResponse | null = null;

  constructor(
    private universityMemberService: UniversityMemberService,
    private activityService: ActivityService
  ) {}

  ngOnInit(): void {
    this.limpiarMensaje();
    this.cargarEjecucionSeleccionada();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['actividad'] || changes['idActividad']) {
      this.cargarEjecucionSeleccionada();
    }
  }

  iniciarActividad(): void {
    if (this.iniciandoActividad) {
      return;
    }

    if (this.esEstadoPendienteOEnCurso() && !this.esUsuarioColaborador()) {
      this.mostrarMensaje('No tienes permisos para iniciar esta actividad. Solo el colaborador asignado puede realizar esta acción.', 'error');
      return;
    }

    if (!this.ejecucionSeleccionadaId) {
      this.mostrarMensaje('No hay una ejecución de actividad seleccionada. Por favor, selecciona una fecha programada antes de iniciar.', 'error');
      return;
    }

    this.iniciandoActividad = true;
    this.limpiarMensaje();

    this.activityService.iniciarActividad(this.ejecucionSeleccionadaId)
      .pipe(finalize(() => this.iniciandoActividad = false))
      .subscribe({
        next: () => {
          this.actualizarEstadoEjecucionLocal(EstadoActividad.EN_CURSO);
          this.actividadIniciada = true;
          this.mostrarMensaje('La actividad fue iniciada. Puedes registrar la asistencia.', 'success');
        },
        error: () => {
          this.mostrarMensaje('No fue posible iniciar la actividad. Inténtalo nuevamente.', 'error');
        }
      });
  }

  buscarParticipante(): void {
    if (!this.rfidSearch && !this.documentoSearch) {
      this.mostrarMensaje('Por favor ingrese un código RFID o número de documento', 'warning');
      return;
    }

    this.buscando = true;
    this.limpiarMensaje();

    const carnet = this.rfidSearch?.trim();
    const documento = this.documentoSearch?.trim();

    const consulta$ = carnet
      ? this.universityMemberService.consultarPorCarnet(carnet)
      : this.universityMemberService.consultarPorIdentificacion(documento);

    consulta$
      .pipe(
        catchError(() => {
          this.buscando = false;
          this.preguntarAgregarParticipanteExterno();
          return of(null);
        })
      )
      .subscribe(miembro => {
        this.buscando = false;
        if (miembro) {
          this.agregarMiembro(miembro);
          this.limpiarFormulario();
        } else {
          this.mostrarMensaje('No se encontró ningún miembro con los datos ingresados.', 'warning');
        }
      });
  }

  private preguntarAgregarParticipanteExterno(): void {
    const modalElement = document.getElementById('confirmExternalParticipantModal');
    if (modalElement) {
      const modal = Modal.getInstance(modalElement) || new Modal(modalElement);
      modal.show();
    }
  }

  confirmarAgregarParticipanteExterno(): void {
    const modalElement = document.getElementById('confirmExternalParticipantModal');
    if (modalElement) {
      const modal = Modal.getInstance(modalElement);
      if (modal) {
        modal.hide();
      }
    }
    this.abrirModalParticipanteExterno();
  }

  cancelarAgregarParticipanteExterno(): void {
    const modalElement = document.getElementById('confirmExternalParticipantModal');
    if (modalElement) {
      const modal = Modal.getInstance(modalElement);
      if (modal) {
        modal.hide();
      }
    }
    this.mostrarMensaje('No se encontró ningún participante con los datos ingresados', 'warning');
    this.limpiarFormulario();
  }

  private abrirModalParticipanteExterno(): void {
    if (this.externalParticipantComp) {
      this.externalParticipantComp.precargarDocumento(this.documentoSearch?.trim() ?? '');
    }
    const modalElement = document.getElementById('external-participant-modal');
    if (modalElement) {
      const modal = new Modal(modalElement);
      modal.show();
    }
  }

  manejarParticipanteExterno(datos: any): void {
    const miembroExterno: UniversityMemberResponse = {
      identificador: `externo-${Date.now()}`,
      nombreCompleto: datos.nombreCompleto,
      documentoIdentificacion: datos.documento,
      programaAcademico: 'N/A',
      correoInstitucional: 'N/A',
      tipo: 'Externo'
    };

    this.agregarMiembro(miembroExterno);
    this.limpiarFormulario();
    this.mostrarMensaje(`Participante externo ${datos.nombreCompleto} agregado exitosamente`, 'success');
  }

  private agregarMiembro(miembro: UniversityMemberResponse): void {
    const yaExiste = this.miembrosAsistencia.some(existing =>
      existing.documentoIdentificacion === miembro.documentoIdentificacion ||
      existing.identificador === miembro.identificador
    );

    if (yaExiste) {
      this.mostrarMensaje('Este participante ya está registrado en la lista de asistencia', 'warning');
      return;
    }

    this.miembrosAsistencia.push(miembro);
    this.mostrarMensaje(`Participante ${miembro.nombreCompleto} agregado exitosamente`, 'success');
  }

  removerParticipante(index: number): void {
    const participante = this.miembrosAsistencia[index];
    this.miembrosAsistencia.splice(index, 1);
    this.mostrarMensaje(`Participante ${participante.nombreCompleto} removido de la lista`, 'success');
  }

  finalizarActividad(): void {
    if (this.guardando) {
      return;
    }

    if (!this.esUsuarioColaborador()) {
      this.mostrarMensaje('No tienes permisos para finalizar esta actividad. Solo el colaborador asignado puede realizar esta acción.', 'error');
      return;
    }

    if (!this.ejecucionSeleccionadaId) {
      this.mostrarMensaje('No hay una ejecución de actividad seleccionada para finalizar.', 'error');
      return;
    }

    const ejecucionId = String(this.ejecucionSeleccionadaId).trim();

    if (!ejecucionId) {
      this.mostrarMensaje('El identificador de la ejecución seleccionada no es válido.', 'error');
      return;
    }

    if (this.miembrosAsistencia.length === 0) {
      this.mostrarMensaje('No hay participantes registrados para finalizar la actividad', 'warning');
      return;
    }

    const participantesRegistrados = [...this.miembrosAsistencia];
    const participantesRequest: ParticipantRequest[] = participantesRegistrados.map(miembro =>
      this.convertirAParticipantRequest(miembro)
    );

    this.guardando = true;
    this.limpiarMensaje();

    this.activityService.finalizarActividad(ejecucionId, participantesRequest)
      .pipe(finalize(() => this.guardando = false))
      .subscribe({
        next: () => {
          this.actualizarEstadoEjecucionLocal(EstadoActividad.FINALIZADO);
          this.actividadFinalizada.emit({
            idActividad: this.idActividad,
            participantes: participantesRegistrados,
            fechaFinalizacion: new Date()
          });

          this.actividadIniciada = false;
          this.limpiarTodo();
          this.mostrarMensaje('La actividad fue finalizada exitosamente.', 'success');
        },
        error: (errorResponse) => {
          const mensajeBackend = this.obtenerMensajeError(errorResponse);
          if (mensajeBackend) {
            this.mostrarMensaje(mensajeBackend, 'error');
          } else {
            this.mostrarMensaje('No fue posible finalizar la actividad. Inténtalo nuevamente.', 'error');
          }
        }
      });
  }

  solicitarCancelacionActividad(): void {
    const modalElement = document.getElementById('cancelActivityConfirmModal');
    if (modalElement) {
      const modal = Modal.getInstance(modalElement) || new Modal(modalElement);
      modal.show();
    }
  }

  cerrarModalCancelacion(): void {
    const modalElement = document.getElementById('cancelActivityConfirmModal');
    if (modalElement) {
      const modal = Modal.getInstance(modalElement);
      if (modal) {
        modal.hide();
      }
    }
  }

  cancelarActividad(): void {
    if (this.cancelandoActividad) {
      return;
    }

    if (!this.esUsuarioColaborador()) {
      this.mostrarMensaje('No tienes permisos para cancelar esta actividad. Solo el colaborador asignado puede realizar esta acción.', 'error');
      return;
    }

    if (!this.ejecucionSeleccionadaId) {
      this.mostrarMensaje('No hay una ejecución de actividad seleccionada para cancelar.', 'error');
      return;
    }

    this.cerrarModalCancelacion();
    this.cancelandoActividad = true;
    this.limpiarMensaje();
    const participantesAnteriormenteRegistrados = [...this.miembrosAsistencia];

    this.activityService.cancelarActividad(this.ejecucionSeleccionadaId)
      .pipe(finalize(() => this.cancelandoActividad = false))
      .subscribe({
        next: () => {
          this.actualizarEstadoEjecucionLocal(EstadoActividad.PENDIENTE);
          this.miembrosAsistencia = [];
          this.limpiarFormulario();
          this.actividadIniciada = false;
          this.actividadCancelada.emit({
            idActividad: this.idActividad,
            participantes: participantesAnteriormenteRegistrados
          });
          this.mostrarMensaje('La actividad fue cancelada exitosamente.', 'success');
        },
        error: () => {
          this.mostrarMensaje('No fue posible cancelar la actividad. Inténtalo nuevamente.', 'error');
        }
      });
  }

  private limpiarFormulario(): void {
    this.rfidSearch = '';
    this.documentoSearch = '';
  }

  private limpiarTodo(): void {
    this.limpiarFormulario();
    this.miembrosAsistencia = [];
    this.limpiarMensaje();
    this.cancelandoActividad = false;
    this.iniciandoActividad = false;
  }

  private mostrarMensaje(mensaje: string, tipo: 'success' | 'error' | 'warning'): void {
    this.mensaje = mensaje;
    this.tipoMensaje = tipo;
    setTimeout(() => {
      this.limpiarMensaje();
    }, 5000);
  }

  private limpiarMensaje(): void {
    this.mensaje = '';
  }

  esActividadFinalizada(): boolean {
    return this.ejecucionFinalizada;
  }

  /**
   * Convierte miembro universitario a ParticipantRequest. Los externos no tienen identificador.
   */
  private convertirAParticipantRequest(miembro: UniversityMemberResponse): ParticipantRequest {
    const identificadorNormalizado = miembro.identificador ? miembro.identificador.trim() : '';
    const tipoNormalizado = miembro.tipo ? miembro.tipo.trim().toLowerCase() : '';
    const esExterno = tipoNormalizado === 'externo' || identificadorNormalizado.startsWith('externo-') || identificadorNormalizado === '';

    return {
      identificador: esExterno ? null : identificadorNormalizado,
      nombreCompleto: miembro.nombreCompleto,
      documentoIdentificacion: miembro.documentoIdentificacion
    };
  }

  private obtenerMensajeError(error: any): string {
    if (!error) {
      return '';
    }

    const mensaje =
      error.error?.message ||
      error.error?.mensaje ||
      error.error?.detail ||
      error.message ||
      '';

    return typeof mensaje === 'string' ? mensaje : '';
  }

  /**
   * Carga actividad y ejecución desde sessionStorage (guardados por date-selector).
   * Si cambia la ejecución, limpia participantes para evitar inconsistencias.
   */
  private cargarEjecucionSeleccionada(): void {
    const storage = this.obtenerStorage();
    if (!storage) {
      return;
    }

    const raw = storage.getItem(this.STORAGE_KEY);
    if (!raw) {
      this.ejecucionSeleccionada = null;
      this.ejecucionSeleccionadaId = null;
      this.actividadCargada = null;
      this.actividadIniciada = false;
      this.ejecucionFinalizada = false;
      return;
    }

    try {
      const data = JSON.parse(raw) as { actividad?: ActivityResponse | null; ejecucion?: ActivityExecutionResponse | null };
      const ejecucionAnteriorId = this.ejecucionSeleccionadaId;

      this.actividadCargada = data?.actividad || null;

      if (!this.actividad && this.actividadCargada) {
        this.actividad = this.actividadCargada;
      }

      this.ejecucionSeleccionada = data?.ejecucion || null;
      this.ejecucionSeleccionadaId = this.ejecucionSeleccionada?.identificador || null;
      const estado = this.ejecucionSeleccionada?.estadoActividad?.nombre;
      this.actividadIniciada = this.esEstadoEnCurso(estado);
      this.ejecucionFinalizada = this.esEstadoFinalizado(estado);

      if (this.ejecucionSeleccionadaId !== ejecucionAnteriorId) {
        this.miembrosAsistencia = [];
        this.p = 1;
        this.totalElementos = 0;
      }

      if (this.ejecucionFinalizada) {
        this.cargarParticipantesDeActividadFinalizada();
      }
    } catch {
      storage.removeItem(this.STORAGE_KEY);
      this.ejecucionSeleccionada = null;
      this.ejecucionSeleccionadaId = null;
      this.actividadCargada = null;
      this.actividadIniciada = false;
      this.ejecucionFinalizada = false;
    }
  }

  /**
   * Actualiza el estado de la ejecución en memoria y sessionStorage para mantener sincronización.
   */
  private actualizarEstadoEjecucionLocal(estado: EstadoActividad): void {
    this.actividadIniciada = estado === EstadoActividad.EN_CURSO;
    this.ejecucionFinalizada = estado === EstadoActividad.FINALIZADO;

    if (this.ejecucionSeleccionada) {
      this.ejecucionSeleccionada = {
        ...this.ejecucionSeleccionada,
        estadoActividad: {
          ...(this.ejecucionSeleccionada.estadoActividad || {}),
          nombre: estado
        }
      };
    }

    const storage = this.obtenerStorage();
    if (!storage) {
      return;
    }

    const raw = storage.getItem(this.STORAGE_KEY);
    if (!raw) {
      return;
    }

    try {
      const data = JSON.parse(raw) as { actividad?: any; ejecucion?: ActivityExecutionResponse | null };
      if (data?.ejecucion) {
        data.ejecucion = {
          ...data.ejecucion,
          estadoActividad: {
            ...(data.ejecucion.estadoActividad || {}),
            nombre: estado
          }
        };
        storage.setItem(this.STORAGE_KEY, JSON.stringify(data));
      }
    } catch {
      storage.removeItem(this.STORAGE_KEY);
    }

    if (this.ejecucionFinalizada) {
      this.cargarParticipantesDeActividadFinalizada();
    } else if (estado === EstadoActividad.PENDIENTE) {
      this.miembrosAsistencia = [];
    }
  }

  private esEstadoEnCurso(estado?: string): boolean {
    if (!estado) {
      return false;
    }
    const estadoNormalizado = this.normalizarTexto(estado);
    return estadoNormalizado === this.normalizarTexto(EstadoActividad.EN_CURSO);
  }

  private esEstadoFinalizado(estado?: string): boolean {
    if (!estado) {
      return false;
    }
    const estadoNormalizado = this.normalizarTexto(estado);
    return estadoNormalizado === this.normalizarTexto(EstadoActividad.FINALIZADO) || estadoNormalizado === 'finalizada';
  }

  private cargarParticipantesDeActividadFinalizada(): void {
    if (!this.ejecucionFinalizada || !this.ejecucionSeleccionadaId) {
      return;
    }

    const participantesPrevios = [...this.miembrosAsistencia];
    this.cargandoParticipantesFinalizados = true;
    this.miembrosAsistencia = [];

    this.activityService.consultarParticipantesPorEjecucionPaginado(this.ejecucionSeleccionadaId, this.p - 1)
      .pipe(finalize(() => this.cargandoParticipantesFinalizados = false))
      .subscribe({
        next: (response: any) => {
          const participantes = response.content || [];
          this.totalElementos = response.totalElements || 0;
          this.miembrosAsistencia = participantes.map((participante: ParticipantResponse) =>
            this.convertirParticipanteResponseAUniversityMember(participante)
          );
        },
        error: () => {
          this.miembrosAsistencia = participantesPrevios;
          this.mostrarMensaje('No fue posible obtener los participantes de la actividad finalizada.', 'error');
        }
      });
  }

  onPageChange(page: number): void {
    this.p = page;
    this.cargarParticipantesDeActividadFinalizada();
  }

  private convertirParticipanteResponseAUniversityMember(participante: ParticipantResponse): UniversityMemberResponse {
    return {
      identificador: participante.identificador,
      nombreCompleto: participante.nombreCompleto,
      documentoIdentificacion: participante.numeroIdentificacion,
      programaAcademico: participante.programaAcademico || 'N/A',
      correoInstitucional: participante.correoInstitucional || 'N/A',
      tipo: participante.tipo || 'Interno'
    };
  }

  private obtenerStorage(): Storage | null {
    if (typeof window === 'undefined') {
      return null;
    }
    return window.sessionStorage;
  }

  /**
   * Normaliza texto usando descomposición Unicode (NFD) para comparaciones sin acentos.
   * Separa caracteres base de diacríticos: á -> a + ´, luego elimina los diacríticos.
   */
  private normalizarTexto(valor: string): string {
    return valor
      .toLowerCase()
      .normalize('NFD')
      .replace(/[\u0300-\u036f]/g, '')
      .replace(/\s+/g, ' ')
      .trim();
  }

  /**
   * Extrae el identificador del usuario desde el token JWT (formato: header.payload.signature).
   * Decodifica el payload (segunda parte) que contiene la información del usuario.
   */
  private obtenerIdentificadorUsuario(): string | null {
    try {
      const token = window.sessionStorage.getItem('Authorization');
      if (!token || !token.includes('.')) {
        return null;
      }

      const tokenPayload = JSON.parse(atob(token.split('.')[1]));
      return tokenPayload.identificador || tokenPayload.id || null;
    } catch {
      return null;
    }
  }

  private esEstadoPendienteOEnCurso(): boolean {
    if (!this.ejecucionSeleccionada || !this.ejecucionSeleccionada.estadoActividad) {
      return false;
    }
    const estado = this.ejecucionSeleccionada.estadoActividad.nombre;
    const estadoNormalizado = this.normalizarTexto(estado);
    const pendienteNormalizado = this.normalizarTexto(EstadoActividad.PENDIENTE);
    const enCursoNormalizado = this.normalizarTexto(EstadoActividad.EN_CURSO);
    return estadoNormalizado === pendienteNormalizado || estadoNormalizado === enCursoNormalizado;
  }

  esUsuarioColaborador(): boolean {
    const actividadParaValidar = this.actividad || this.actividadCargada;
    if (!actividadParaValidar || !actividadParaValidar.colaborador) {
      return false;
    }

    const usuarioId = this.obtenerIdentificadorUsuario();
    if (!usuarioId) {
      return false;
    }

    const colaboradorId = actividadParaValidar.colaborador;
    return String(usuarioId) === String(colaboradorId);
  }

  obtenerNombreColaborador(): string {
    const actividadParaValidar = this.actividad || this.actividadCargada;
    if (!actividadParaValidar) {
      return 'No disponible';
    }
    return actividadParaValidar.nombreColaborador || 'Sin colaborador asignado';
  }

  /**
   * Determina si mostrar error de permisos: solo para estados Pendiente/En curso cuando el usuario no es el colaborador.
   */
  debeMostrarErrorPermisos(): boolean {
    const actividadParaValidar = this.actividad || this.actividadCargada;
    if (!actividadParaValidar || !this.ejecucionSeleccionada) {
      return false;
    }

    if (!this.esEstadoPendienteOEnCurso()) {
      return false;
    }

    return !this.esUsuarioColaborador();
  }
}
