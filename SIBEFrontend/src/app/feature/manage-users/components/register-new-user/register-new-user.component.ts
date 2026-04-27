import { Component, EventEmitter, Output, OnInit, Input } from '@angular/core';
import { Modal } from 'bootstrap';
import { UserService } from 'src/app/shared/service/user.service';
import { UserTypeService } from 'src/app/shared/service/user-type.service';
import { UserRequest } from 'src/app/shared/model/user.model';
import { UserTypeResponse } from 'src/app/shared/model/user-type.model';
import { IdentificationTypeService } from 'src/app/shared/service/identification-type.service';
import { IdentificationTypeResponse } from 'src/app/shared/model/identification-type.model';
import { UserNotificationService } from '../../service/user-notification.service';
import { DepartmentService } from 'src/app/shared/service/department.service';
import { AreaService } from 'src/app/shared/service/area.service';
import { SubAreaService } from 'src/app/shared/service/subarea.service';
import { DepartmentResponse } from 'src/app/shared/model/department.model';
import { AreaResponse } from 'src/app/shared/model/area.model';
import { SubAreaResponse } from 'src/app/shared/model/subarea.model';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-register-new-user',
  templateUrl: './register-new-user.component.html',
  styleUrls: ['./register-new-user.component.scss']
})
export class RegisterNewUserComponent implements OnInit {
  @Output() usuarioCreado = new EventEmitter<any>();
  @Input() modalId: string = 'register-user-modal';

  usuario = {
    nombres: '',
    apellidos: '',
    tipoIdentificacion: '',
    numeroIdentificacion: '',
    correo: '',
    clave: '',
    confirmarClave: '',
    tipoUsuario: '',
    estructuraOrganizacional: '',
    tipoEstructura: ''
  };


  cargando = false;
  error = '';
  mensajeExito = '';

  // Propiedades para tipos de identificación
  tiposIdentificacion: IdentificationTypeResponse[] = [];
  cargandoTiposIdentificacion = false;
  errorTiposIdentificacion = '';
  
  // Propiedades para tipos de usuario
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
      return false;
    }
    const tipoUsuario = this.tiposUsuario.find(t => t.identificador === this.usuario.tipoUsuario);
    return tipoUsuario?.nombre === 'Administrador de dirección';
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

  registrarUsuario() {
    // Validar contraseñas
    if (this.usuario.clave !== this.usuario.confirmarClave) {
      this.error = 'Las contraseñas no coinciden';
      return;
    }

    // Validar que todos los campos estén completos
    if (!this.validarFormulario()) {
      this.error = 'Por favor, complete todos los campos requeridos';
      return;
    }


    this.cargando = true;
    this.error = '';
    this.mensajeExito = '';

      const tipoArea = this.determinarTipoArea();
      
      const userRequest: UserRequest = {
        tipoIdentificacion: this.usuario.tipoIdentificacion, // Solo el identificador
        numeroIdentificacion: this.usuario.numeroIdentificacion,
        nombres: this.usuario.nombres,
        apellidos: this.usuario.apellidos,
        correo: this.usuario.correo,
        clave: this.usuario.clave,
        tipoUsuario: this.usuario.tipoUsuario, // Solo el identificador
        area: {
          area: this.usuario.estructuraOrganizacional,
          tipoArea: tipoArea
        }
      };

    this.userService.agregarNuevoUsuario(userRequest).subscribe({
      next: (response) => {
        this.cargando = false;
        this.mensajeExito = 'Usuario creado exitosamente';
        
        // Emitir evento para actualizar la lista en el componente padre (mantener compatibilidad)
        this.usuarioCreado.emit(response);
        
        // Notificar a través del servicio para que todos los componentes se actualicen
        this.userNotificationService.notificarUsuarioCreado(response);
        
        // Limpiar formulario y cerrar modal después de un breve delay
        setTimeout(() => {
          this.limpiarFormulario();
          this.cerrarModal();
        }, 1500);
      },
      error: (err) => {
        this.cargando = false;
        console.error('Error al crear usuario:', err);
        
        if (err.error && err.error.mensaje) {
          this.error = err.error.mensaje;
        } else if (err.error && err.error.message) {
          this.error = err.error.message;
        } else {
          this.error = 'Error al crear el usuario. Por favor, intente nuevamente.';
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
      this.usuario.clave &&
      this.usuario.confirmarClave &&
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

  cerrarModal() {
    const modalElement = document.getElementById(this.modalId);
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
      clave: '',
      confirmarClave: '',
      tipoUsuario: '',
      estructuraOrganizacional: '',
      tipoEstructura: ''
    };
    this.listaFiltrada = [];
    this.error = '';
    this.mensajeExito = '';
  }

}
