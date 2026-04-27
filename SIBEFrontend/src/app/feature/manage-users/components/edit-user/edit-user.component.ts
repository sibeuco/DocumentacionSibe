import { Component, EventEmitter, Output, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { Modal } from 'bootstrap';
import { UserService } from 'src/app/shared/service/user.service';
import { UserTypeService } from 'src/app/shared/service/user-type.service';
import { IdentificationTypeService } from 'src/app/shared/service/identification-type.service';
import { EditUserRequest } from 'src/app/shared/model/user.model';
import { UserTypeResponse } from 'src/app/shared/model/user-type.model';
import { IdentificationTypeResponse } from 'src/app/shared/model/identification-type.model';
import { UserResponse } from 'src/app/shared/model/user.model';
import { UserNotificationService } from '../../service/user-notification.service';
import { DepartmentService } from 'src/app/shared/service/department.service';
import { AreaService } from 'src/app/shared/service/area.service';
import { SubAreaService } from 'src/app/shared/service/subarea.service';
import { DepartmentResponse } from 'src/app/shared/model/department.model';
import { AreaResponse } from 'src/app/shared/model/area.model';
import { SubAreaResponse } from 'src/app/shared/model/subarea.model';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.scss']
})
export class EditUserComponent implements OnInit, OnChanges {

  @Input() usuarioAEditar: UserResponse | null = null;
  @Output() usuarioActualizado = new EventEmitter<any>();

  usuario = {
    nombres: '',
    apellidos: '',
    tipoIdentificacion: '',
    numeroIdentificacion: '',
    correo: '',
    tipoUsuario: '',
    estructuraOrganizacional: '',
    tipoEstructura: ''
  };


  cargando = false;
  error = '';
  mensajeExito = '';

  tiposIdentificacion: IdentificationTypeResponse[] = [];
  cargandoTiposIdentificacion = false;
  errorTiposIdentificacion = '';

  tiposUsuario: UserTypeResponse[] = [];
  cargandoTiposUsuario = false;
  errorTiposUsuario = '';

  // Propiedades para manejo de listas condicionales
  listaFiltrada: { identificador: string; nombre: string }[] = [];
  listaDepartamentos: { identificador: string; nombre: string }[] = [];
  listaAreas: { identificador: string; nombre: string }[] = [];
  listaSubareas: { identificador: string; nombre: string }[] = [];
  cargandoEstructuras = false;
  opcionesTipoEstructura = [
    { valor: 'area', etiqueta: 'Área' },
    { valor: 'subarea', etiqueta: 'Subárea' }
  ];

  constructor(
    private userService: UserService,
    private userTypeService: UserTypeService,
    private identificationTypeService: IdentificationTypeService,
    private userNotificationService: UserNotificationService,
    private departmentService: DepartmentService,
    private areaService: AreaService,
    private subAreaService: SubAreaService
  ) {}

  ngOnInit(): void {
    this.cargarTiposUsuario();
    this.cargarTiposIdentificacion();
    this.cargarEstructurasOrganizacionales();
  }

  cargarEstructurasOrganizacionales(): void {
    this.cargandoEstructuras = true;
    forkJoin({
      departamentos: this.departmentService.consultarDirecciones(),
      areas: this.areaService.consultarAreas(),
      subareas: this.subAreaService.consultarSubareas()
    }).subscribe({
      next: ({ departamentos, areas, subareas }) => {
        this.listaDepartamentos = departamentos.map(d => ({ identificador: d.identificador, nombre: d.nombre }));
        this.listaAreas = areas.map(a => ({ identificador: a.identificador, nombre: a.nombre }));
        this.listaSubareas = subareas.map(s => ({ identificador: s.identificador, nombre: s.nombre }));
        this.cargandoEstructuras = false;
        // Si ya hay un usuario cargado, recargar los datos para configurar correctamente
        if (this.usuarioAEditar) {
          this.cargarDatosUsuario();
        }
      },
      error: (err) => {
        console.error('Error al cargar estructuras organizacionales:', err);
        this.cargandoEstructuras = false;
      }
    });
  }

  onTipoUsuarioChange(): void {
    // Limpiar campos cuando cambia el tipo de usuario
    this.usuario.tipoEstructura = '';
    this.usuario.estructuraOrganizacional = '';
    this.listaFiltrada = [];
  }

  esAdministradorDireccion(): boolean {
    if (!this.usuario.tipoUsuario) {
      // Si no hay tipoUsuario seleccionado, verificar en usuarioAEditar
      if (this.usuarioAEditar?.tipoUsuario?.nombre) {
        return this.usuarioAEditar.tipoUsuario.nombre === 'Administrador de dirección';
      }
      return false;
    }
    const tipoUsuario = this.tiposUsuario.find(t => t.identificador === this.usuario.tipoUsuario);
    return tipoUsuario?.nombre === 'Administrador de dirección';
  }

  ngOnChanges(changes: SimpleChanges): void {
    console.log('ngOnChanges triggered:', changes); // Debug log
    if (changes['usuarioAEditar']) {
      if (changes['usuarioAEditar'].currentValue) {
        console.log('UsuarioAEditar cambió, cargando datos...'); // Debug log
        // Esperar a que las estructuras estén cargadas antes de cargar los datos del usuario
        if (!this.cargandoEstructuras && (this.listaDepartamentos.length > 0 || this.listaAreas.length > 0 || this.listaSubareas.length > 0)) {
          this.cargarDatosUsuario();
        }
        // Si las estructuras aún se están cargando, cargarDatosUsuario se llamará desde cargarEstructurasOrganizacionales
      } else {
        console.log('UsuarioAEditar se limpió, limpiando formulario...'); // Debug log
        this.limpiarFormulario();
      }
    }
  }

  cargarTiposIdentificacion(): void {
    this.cargandoTiposIdentificacion = true;
    this.errorTiposIdentificacion = '';

    this.identificationTypeService.consultarTipoIdentificacion().subscribe({
      next: (tipos: IdentificationTypeResponse[]) => {
        this.tiposIdentificacion = tipos;
        this.cargandoTiposIdentificacion = false;
      },
      error: (err) => {
        console.error('Error al cargar tipos de identificación:', err);
        this.errorTiposIdentificacion = 'Error al cargar los tipos de identificación';
        this.cargandoTiposIdentificacion = false;
      }
    });
  }

  cargarTiposUsuario(): void {
    this.cargandoTiposUsuario = true;
    this.errorTiposUsuario = '';

    this.userTypeService.consultarTipoUsuario().subscribe({
      next: (tipos: UserTypeResponse[]) => {
        this.tiposUsuario = tipos;
        this.cargandoTiposUsuario = false;
      },
      error: (err) => {
        console.error('Error al cargar tipos de usuario:', err);
        this.errorTiposUsuario = 'Error al cargar los tipos de usuario';
        this.cargandoTiposUsuario = false;
      }
    });
  }

  cargarDatosUsuario(): void {
    if (this.usuarioAEditar) {
      console.log('Usuario a editar:', this.usuarioAEditar); // Debug log
      this.usuario = {
        nombres: this.usuarioAEditar.nombres || '',
        apellidos: this.usuarioAEditar.apellidos || '',
        tipoIdentificacion: this.usuarioAEditar.identificacion?.tipoIdentificacion?.identificador || '',
        numeroIdentificacion: this.usuarioAEditar.identificacion?.numeroIdentificacion || '',
        correo: this.usuarioAEditar.correo || '',
        tipoUsuario: this.usuarioAEditar.tipoUsuario?.identificador || '',
        estructuraOrganizacional: this.usuarioAEditar.area?.identificador || '',
        tipoEstructura: this.determinarTipoEstructuraInicial()
      };
      
      // Configurar lista filtrada según el tipo de estructura
      this.configurarListaFiltrada();
      
      console.log('Datos cargados en el formulario:', this.usuario); // Debug log
    }
  }

  determinarTipoEstructuraInicial(): string {
    // Si el tipo de usuario es Administrador de dirección, no necesita tipoEstructura
    if (this.esAdministradorDireccion()) {
      return '';
    }
    
    // Si el usuario tiene un tipoArea, usarlo para determinar el tipoEstructura
    const tipoArea = this.usuarioAEditar?.area?.tipoArea;
    if (tipoArea === 'AREA') {
      return 'area';
    } else if (tipoArea === 'SUBAREA') {
      return 'subarea';
    }
    
    // Si no hay tipoArea, intentar determinar por el identificador del área
    if (this.usuarioAEditar?.area?.identificador) {
      // Verificar si el área actual está en la lista de áreas
      const esArea = this.listaAreas.some(area => area.identificador === this.usuarioAEditar?.area?.identificador);
      if (esArea) {
        return 'area';
      }
      
      // Verificar si está en la lista de subáreas
      const esSubarea = this.listaSubareas.some(subarea => subarea.identificador === this.usuarioAEditar?.area?.identificador);
      if (esSubarea) {
        return 'subarea';
      }
    }
    
    return '';
  }

  configurarListaFiltrada(): void {
    if (this.esAdministradorDireccion()) {
      this.listaFiltrada = this.listaDepartamentos;
    } else if (this.usuario.tipoEstructura === 'area') {
      this.listaFiltrada = this.listaAreas;
    } else if (this.usuario.tipoEstructura === 'subarea') {
      this.listaFiltrada = this.listaSubareas;
    } else {
      this.listaFiltrada = [];
    }
  }

  onTipoEstructuraChange(): void {
    // Limpiar la selección de estructura organizacional cuando cambia el tipo
    this.usuario.estructuraOrganizacional = '';
    
    // Filtrar la lista según el tipo seleccionado
    if (this.usuario.tipoEstructura === 'area') {
      this.listaFiltrada = this.listaAreas;
    } else if (this.usuario.tipoEstructura === 'subarea') {
      this.listaFiltrada = this.listaSubareas;
    } else {
      this.listaFiltrada = [];
    }
  }

  determinarTipoArea(): string {
    if (this.esAdministradorDireccion()) {
      return 'DIRECCION';
    } else {
      if (this.usuario.tipoEstructura === 'area') {
        return 'AREA';
      } else if (this.usuario.tipoEstructura === 'subarea') {
        return 'SUBAREA';
      }
    }
    
    return '';
  }

  actualizarUsuario() {
    if (!this.validarFormulario()) {
      this.error = 'Por favor, complete todos los campos requeridos';
      return;
    }

    if (!this.usuarioAEditar) {
      this.error = 'No se ha seleccionado un usuario para editar';
      return;
    }

    this.cargando = true;
    this.error = '';
    this.mensajeExito = '';

    const tipoArea = this.determinarTipoArea();
    
    const editUserRequest: EditUserRequest = {
      tipoIdentificacion: this.usuario.tipoIdentificacion,
      numeroIdentificacion: this.usuario.numeroIdentificacion,
      nombres: this.usuario.nombres,
      apellidos: this.usuario.apellidos,
      correo: this.usuario.correo,
      tipoUsuario: this.usuario.tipoUsuario,
      area: {
        area: this.usuario.estructuraOrganizacional,
        tipoArea: tipoArea
      }
    };

    this.userService.modificarUsuario(this.usuarioAEditar.identificador, editUserRequest).subscribe({
      next: (response) => {
        this.cargando = false;
        this.mensajeExito = 'Usuario actualizado exitosamente';
        
        // Emitir evento para actualizar la lista en el componente padre (mantener compatibilidad)
        this.usuarioActualizado.emit(response);
        
        // Notificar a través del servicio para que todos los componentes se actualicen
        this.userNotificationService.notificarUsuarioActualizado(response);
        
        setTimeout(() => {
          this.limpiarFormulario();
          this.cerrarModal();
        }, 1500);
      },
      error: (err) => {
        this.cargando = false;
        console.error('Error al actualizar usuario:', err);
        
        if (err.error && err.error.mensaje) {
          this.error = err.error.mensaje;
        } else if (err.error && err.error.message) {
          this.error = err.error.message;
        } else {
          this.error = 'Error al actualizar el usuario. Por favor, intente nuevamente.';
        }
      }
    });
  }

  validarFormulario(): boolean {
    const camposBasicos = !!(
      this.usuario.nombres &&
      this.usuario.apellidos &&
      this.usuario.tipoIdentificacion &&
      this.usuario.numeroIdentificacion &&
      this.usuario.correo &&
      this.usuario.tipoUsuario
    );

    // Si es Administrador de dirección, solo necesita estructuraOrganizacional (departamento)
    if (this.esAdministradorDireccion()) {
      return camposBasicos && !!this.usuario.estructuraOrganizacional;
    }

    // Para otros tipos de usuario, necesita tipoEstructura y estructuraOrganizacional
    return camposBasicos && 
           !!this.usuario.tipoEstructura && 
           !!this.usuario.estructuraOrganizacional;
  }

  abrirModal(): void {
    const modalElement = document.getElementById('edit-user-modal');
    if (modalElement) {
      const modal = Modal.getInstance(modalElement) || new Modal(modalElement);
      modal.show();
    }
  }

  cerrarModal() {
    const modalElement = document.getElementById('edit-user-modal');
    if (modalElement) {
      const modal = Modal.getInstance(modalElement) || new Modal(modalElement);
      modal.hide();
      
      // Asegurar que el backdrop se remueva completamente
      setTimeout(() => {
        // Remover cualquier backdrop que pueda quedar
        const backdrops = document.querySelectorAll('.modal-backdrop');
        backdrops.forEach(backdrop => backdrop.remove());
        
        // Remover la clase modal-open del body
        document.body.classList.remove('modal-open');
        
        // Restaurar el padding-right del body si fue modificado
        document.body.style.paddingRight = '';
        
        // Restaurar el overflow del body
        document.body.style.overflow = '';
      }, 300);
    }
  }

  limpiarFormulario() {
    this.usuario = {
      nombres: '',
      apellidos: '',
      tipoIdentificacion: '',
      numeroIdentificacion: '',
      correo: '',
      tipoUsuario: '',
      estructuraOrganizacional: '',
      tipoEstructura: ''
    };
    this.listaFiltrada = [];
    this.error = '';
    this.mensajeExito = '';
    this.usuarioAEditar = null; // Limpiar la referencia al usuario
  }

}
