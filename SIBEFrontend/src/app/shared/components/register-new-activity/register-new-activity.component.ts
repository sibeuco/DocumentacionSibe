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
import { ActivityRequest } from 'src/app/shared/model/activity.model';
import { StateService } from 'src/app/shared/service/state.service';
import { StateProps } from 'src/app/shared/model/state.enum';
import { UserSession } from 'src/app/feature/login/model/user-session.model';

@Component({
  selector: 'app-register-new-activity',
  templateUrl: './register-new-activity.component.html',
  styleUrls: ['./register-new-activity.component.scss']
})
export class RegisterNewActivityComponent implements OnInit, OnChanges {

  @Input() nombreArea: string = '';
  @Input() tipoEstructura: 'direccion' | 'area' | 'subarea' | '' = '';
  @Output() actividadCreada = new EventEmitter<any>();

  actividad = {
    nombre: '',
    objetivo: '',
    semestre: '',
    rutaInsumos: '',
    indicador: '',
    colaborador: '',
    area: '',
    tipoArea: '',
    fechasProgramadas: [] as string[],
  };

  // Lista de indicadores para el select
  indicadores: IndicatorResponse[] = [];
  indicadorSeleccionado: string = '';

  // Lista de usuarios para el select de colaborador
  usuarios: UserResponse[] = [];
  colaboradorSeleccionado: string = '';

  // Propiedades para estructura organizacional
  direcciones: DepartmentResponse[] = [];
  areas: AreaResponse[] = [];
  subareas: SubAreaResponse[] = [];
  listaFiltrada: { identificador: string; nombre: string }[] = [];
  areaSeleccionada: string = '';

  opcionesTipoEstructura = [
    { valor: 'direccion', etiqueta: 'Dirección' },
    { valor: 'area', etiqueta: 'Área' },
    { valor: 'subarea', etiqueta: 'Subárea' }
  ];

  // Fecha temporal para agregar
  fechaTemporal: string = '';
  errorFecha: string = '';
  minFechaProgramada: string = this.obtenerFechaActualISO();

  // Estados para el registro
  cargando = false;
  error = '';
  mensajeExito = '';

  constructor(
    private indicatorService: IndicatorService,
    private userService: UserService,
    private departmentService: DepartmentService,
    private areaService: AreaService,
    private subAreaService: SubAreaService,
    private activityService: ActivityService,
    private stateService: StateService
  ) { }

  ngOnInit(): void {
    this.cargarIndicadores();
    this.cargarUsuarios();
    this.cargarEstructurasOrganizacionales();

    // Si ya tenemos los inputs al inicializar, precargar el área
    if (this.nombreArea && this.tipoEstructura) {
      this.buscarYPrecargarArea();
    }
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
    // Si cambian los inputs de área, buscar y precargar
    if ((changes['nombreArea'] || changes['tipoEstructura']) && this.nombreArea && this.tipoEstructura) {
      this.buscarYPrecargarArea();
    }
  }

  cargarIndicadores(): void {
    this.indicatorService.consultarIndicadoresParaActividades().subscribe({
      next: (indicadores) => {
        this.indicadores = indicadores;
      },
      error: () => {
        // Error silencioso al cargar indicadores
      }
    });
  }

  cargarUsuarios(): void {
    this.userService.consultarUsuarios().subscribe({
      next: (usuarios) => {
        this.usuarios = usuarios;
      },
      error: () => {
        // Error silencioso al cargar usuarios
      }
    });
  }

  cargarEstructurasOrganizacionales(): void {
    this.departmentService.consultarDirecciones().subscribe({
      next: (direcciones) => {
        this.direcciones = direcciones;
      },
      error: () => {
        // Error silencioso al cargar direcciones
      }
    });

    this.areaService.consultarAreas().subscribe({
      next: (areas) => {
        this.areas = areas;
      },
      error: () => {
        // Error silencioso al cargar áreas
      }
    });

    this.subAreaService.consultarSubareas().subscribe({
      next: (subareas) => {
        this.subareas = subareas;
      },
      error: () => {
        // Error silencioso al cargar subáreas
      }
    });
  }

  /**
   * Busca el identificador del área usando consultarPorNombre y lo precarga
   */
  buscarYPrecargarArea(): void {
    if (!this.nombreArea || !this.tipoEstructura) {
      return;
    }

    let consulta$: any;

    switch (this.tipoEstructura) {
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
        return;
    }

    consulta$.subscribe({
      next: (estructura: any) => {
        if (estructura && estructura.identificador) {
          this.actividad.area = estructura.identificador;
          this.actividad.tipoArea = this.determinarTipoArea();
          this.areaSeleccionada = estructura.identificador;
        }
      },
      error: () => {
        // Error silencioso al buscar el área
      }
    });
  }

  onTipoEstructuraChange(): void {
    // Solo permitir cambios si no se pasaron los inputs desde el padre
    if (this.nombreArea && this.tipoEstructura) {
      return;
    }

    this.areaSeleccionada = '';
    this.actividad.area = '';
    this.actividad.tipoArea = '';

    if (this.tipoEstructura === 'direccion') {
      this.listaFiltrada = this.direcciones.map(direccion => ({
        identificador: direccion.identificador,
        nombre: direccion.nombre
      }));
    } else if (this.tipoEstructura === 'area') {
      this.listaFiltrada = this.areas.map(area => ({
        identificador: area.identificador,
        nombre: area.nombre
      }));
    } else if (this.tipoEstructura === 'subarea') {
      this.listaFiltrada = this.subareas.map(subarea => ({
        identificador: subarea.identificador,
        nombre: subarea.nombre
      }));
    } else {
      this.listaFiltrada = [];
    }
  }

  determinarTipoArea(): string {
    if (this.tipoEstructura === 'direccion') {
      return 'DIRECCION';
    } else if (this.tipoEstructura === 'area') {
      return 'AREA';
    } else if (this.tipoEstructura === 'subarea') {
      return 'SUBAREA';
    }
    return '';
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

    if (this.actividad.fechasProgramadas.includes(this.fechaTemporal)) {
      this.errorFecha = 'Esta fecha ya está agregada';
      return;
    }

    this.actividad.fechasProgramadas.push(this.fechaTemporal);
    this.fechaTemporal = '';
  }
  eliminarFecha(index: number) {
    this.actividad.fechasProgramadas.splice(index, 1);
    this.errorFecha = '';
  }

  trackByIndex(index: number): number {
    return index;
  }

  registrarActividad() {
    const errorPermisos = this.validarPermisosCreacion();
    if (errorPermisos) {
      this.error = errorPermisos;
      return;
    }

    if (!this.validarFormulario()) {
      this.error = 'Por favor, complete todos los campos requeridos';
      return;
    }

    this.cargando = true;
    this.error = '';
    this.mensajeExito = '';

    let creador = '';
    const userSession = this.stateService.getState(StateProps.USER_SESSION) as UserSession;
    creador = userSession?.identificador || '';

    if (!creador) {
      try {
        const token = window.sessionStorage.getItem('Authorization');
        if (token) {
          const tokenPayload = JSON.parse(atob(token.split('.')[1]));
          creador = tokenPayload.identificador || tokenPayload.id || '';
        }
      } catch {
        // Error al decodificar el token
      }
    }

    if (!creador || creador.trim() === '') {
      this.error = 'No se pudo obtener la información del usuario. Por favor, inicie sesión nuevamente.';
      this.cargando = false;
      return;
    }

    if (!this.indicadorSeleccionado || this.indicadorSeleccionado.trim() === '') {
      this.error = 'Por favor, seleccione un indicador';
      this.cargando = false;
      return;
    }

    if (!this.actividad.area || this.actividad.area.trim() === '') {
      this.error = 'Por favor, seleccione un área';
      this.cargando = false;
      return;
    }

    const tipoArea = this.determinarTipoArea();
    if (!tipoArea) {
      this.error = 'No se pudo determinar el tipo de área. Por favor, seleccione el tipo de estructura organizacional.';
      this.cargando = false;
      return;
    }

    const activityRequest: ActivityRequest = {
      nombre: this.actividad.nombre.trim(),
      objetivo: this.actividad.objetivo.trim(),
      semestre: this.actividad.semestre.trim(),
      rutaInsumos: this.actividad.rutaInsumos.trim(),
      indicador: this.indicadorSeleccionado.trim(),
      colaborador: this.actividad.colaborador.trim(),
      creador: creador,
      fechasProgramadas: this.actividad.fechasProgramadas.map(f => f.trim()).filter(f => f),
      area: {
        area: this.actividad.area.trim(),
        tipoArea: tipoArea
      }
    };

    // Llamar al servicio
    this.activityService.agregarNuevaActividad(activityRequest).subscribe({
      next: (response) => {
        this.cargando = false;
        this.mensajeExito = 'Actividad creada exitosamente';

        this.actividadCreada.emit(response);

        setTimeout(() => {
          this.limpiarFormulario();
          this.cerrarModal();
        }, 1500);
      },
      error: (err) => {
        this.cargando = false;

        if (err?.status === 403) {
          if (err.error?.mensaje) {
            this.error = err.error.mensaje;
          } else {
            const etiqueta = this.opcionesTipoEstructura.find(o => o.valor === this.tipoEstructura)?.etiqueta || 'unidad organizacional';
            this.error = `No tienes permisos para agregar actividades en esta ${etiqueta}. Tu rol no permite ejecutar esta acción.`;
          }
        } else if (err.error) {
          if (err.error.mensaje) {
            this.error = err.error.mensaje;
          } else if (err.error.message) {
            this.error = err.error.message;
          } else if (typeof err.error === 'string') {
            this.error = err.error;
          } else {
            this.error = 'Error al crear la actividad. Por favor, intente nuevamente.';
          }
        } else {
          this.error = 'Error al crear la actividad. Por favor, intente nuevamente.';
        }
      }
    });
  }

  private validarPermisosCreacion(): string | null {
    const userSession = this.stateService.getState(StateProps.USER_SESSION) as UserSession;
    if (!userSession) {
      return 'No se pudo obtener la información del usuario. Por favor, inicie sesión nuevamente.';
    }

    const authorities = userSession.authorities || [];
    const tienePermisoCrear = authorities.some(a =>
      a === 'ADMINISTRADOR_DIRECCION_CREATE' || a === 'ADMINISTRADOR_AREA_CREATE'
    );

    if (!tienePermisoCrear) {
      return 'No tienes permisos para crear actividades. Tu rol no permite ejecutar esta acción.';
    }

    const rol = userSession.rol;
    const etiqueta = this.opcionesTipoEstructura.find(o => o.valor === this.tipoEstructura)?.etiqueta || 'unidad organizacional';
    const mensajeRestriccion = `No tienes permisos para crear actividades en esta ${etiqueta}. Tu rol no permite ejecutar esta acción.`;

    // Si el padre proporcionó nombreArea y tipoEstructura, pero la precarga del área falló
    // (actividad.area vacío), es porque el backend denegó el acceso al consultarPorNombre
    if (this.nombreArea && this.tipoEstructura && !this.actividad.area) {
      return mensajeRestriccion;
    }

    // ADMINISTRADOR_DIRECCION puede crear en cualquier nivel
    if (rol === 'ADMINISTRADOR_DIRECCION') {
      return null;
    }

    // ADMINISTRADOR_AREA: restricciones según contexto organizacional
    if (rol === 'ADMINISTRADOR_AREA') {
      // No puede crear a nivel de dirección
      if (this.tipoEstructura === 'direccion') {
        return mensajeRestriccion;
      }

      if (userSession.areaId) {
        // Admin de área propia: solo puede crear en su propia área
        if (this.tipoEstructura === 'area' && this.actividad.area !== userSession.areaId) {
          return mensajeRestriccion;
        }
        // Para subáreas bajo su área, se delega al backend la validación de jerarquía
      } else if (userSession.subareaId) {
        // Admin de subárea propia: no puede crear a nivel de área
        if (this.tipoEstructura === 'area') {
          return mensajeRestriccion;
        }
        // Solo puede crear en su propia subárea
        if (this.tipoEstructura === 'subarea' && this.actividad.area !== userSession.subareaId) {
          return mensajeRestriccion;
        }
      }
    }

    return null;
  }

  validarFormulario(): boolean {
    return !!(
      this.actividad.nombre &&
      this.actividad.objetivo &&
      this.actividad.semestre &&
      this.actividad.rutaInsumos &&
      this.indicadorSeleccionado &&
      this.indicadorSeleccionado.trim() !== '' &&
      this.actividad.colaborador &&
      this.actividad.area &&
      this.actividad.fechasProgramadas.length > 0
    );
  }

  cerrarModal() {
    const modalElement = document.getElementById('register-activity-modal');
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
    this.actividad = {
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
    this.areaSeleccionada = '';
    this.listaFiltrada = [];
    this.fechaTemporal = '';
    this.errorFecha = '';
    this.error = '';
    this.mensajeExito = '';

    // Restaurar el tipo de estructura y re-precargar el área desde los @Input() del padre
    // porque el modal Bootstrap reutiliza la misma instancia del componente (hide, no destroy)
    // y ngOnChanges no se dispara de nuevo si los inputs no cambian
    if (this.nombreArea && this.tipoEstructura) {
      this.buscarYPrecargarArea();
    }
  }

}
