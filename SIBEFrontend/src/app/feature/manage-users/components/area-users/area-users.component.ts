import { Component, OnInit, OnDestroy, Output, EventEmitter } from '@angular/core';
import { UserService } from 'src/app/shared/service/user.service';
import { UserResponse } from 'src/app/shared/model/user.model';
import { UserNotificationService } from '../../service/user-notification.service';
import { Subscription } from 'rxjs';
import { Modal } from 'bootstrap';
import { AreaService } from 'src/app/shared/service/area.service';
import { SubAreaService } from 'src/app/shared/service/subarea.service';
import { AreaResponse } from 'src/app/shared/model/area.model';
import { SubAreaResponse } from 'src/app/shared/model/subarea.model';
import { UsuarioSeleccionadoParaEditar } from '../manage-users.component';

@Component({
  selector: 'app-area-users',
  templateUrl: './area-users.component.html',
  styleUrls: ['./area-users.component.scss']
})
export class AreaUsersComponent implements OnInit, OnDestroy{

  @Output() usuarioSeleccionadoParaEditar = new EventEmitter<UsuarioSeleccionadoParaEditar>();

  usuarios: UserResponse[] = [];
  usuariosFiltrados: UserResponse[] = [];
  cargando = false;
  error = '';
  searchTerm = '';
  private subscriptions: Subscription[] = [];
  p: number = 1;
  totalElementos: number = 0;

  // Propiedades para el modal de confirmación
  usuarioAEliminar: UserResponse | null = null;
  eliminando = false;
  mensajeExito = '';
  mostrarMensajeExito = false;
  errorEliminacion = '';

  // Propiedades para la lista desplegable
  listaAreas: { identificador: string; nombre: string }[] = [];
  listaSubareas: { identificador: string; nombre: string }[] = [];
  cargandoAreas = false;

  constructor(
    private userService: UserService,
    private userNotificationService: UserNotificationService,
    private areaService: AreaService,
    private subAreaService: SubAreaService
  ) {}

  ngOnInit(): void {
    this.obtenerUsuarios();
    this.suscribirseANotificaciones();
    this.cargarAreasYSubareas();
  }

  ngOnDestroy(): void {
    // Limpiar suscripciones para evitar memory leaks
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  suscribirseANotificaciones(): void {
    // Suscribirse a notificaciones de usuarios creados
    const subCreado = this.userNotificationService.usuarioCreado$.subscribe(() => {
      this.obtenerUsuarios();
    });
    this.subscriptions.push(subCreado);

    // Suscribirse a notificaciones de usuarios actualizados
    const subActualizado = this.userNotificationService.usuarioActualizado$.subscribe(() => {
      this.obtenerUsuarios();
    });
    this.subscriptions.push(subActualizado);

    // Suscribirse a notificaciones de usuarios eliminados
    const subEliminado = this.userNotificationService.usuarioEliminado$.subscribe(() => {
      this.obtenerUsuarios();
    });
    this.subscriptions.push(subEliminado);
  }

  cargarAreasYSubareas(): void {
    this.cargandoAreas = true;

    // Cargar áreas
    this.areaService.consultarAreas().subscribe({
      next: (areas: AreaResponse[]) => {
        // Cargar subáreas
        this.subAreaService.consultarSubareas().subscribe({
          next: (subareas: SubAreaResponse[]) => {
            // Separar áreas y subáreas
            this.listaAreas = areas.map(area => ({
              identificador: area.identificador,
              nombre: area.nombre
            }));

            this.listaSubareas = subareas.map(subarea => ({
              identificador: subarea.identificador,
              nombre: subarea.nombre
            }));

            // Las listas ya están separadas para el uso condicional
            this.cargandoAreas = false;
          },
          error: (err) => {
            console.error('Error al cargar subáreas:', err);
            this.cargandoAreas = false;
          }
        });
      },
      error: (err) => {
        console.error('Error al cargar áreas:', err);
        this.cargandoAreas = false;
      }
    });
  }

  obtenerUsuarios(): void {
    this.cargando = true;
    this.error = '';

    this.userService.consultarUsuariosPorTipo('Administrador de dirección', this.p - 1, 10, true).subscribe({
      next: (response: any) => {
        this.usuarios = response.content;
        this.usuariosFiltrados = [...response.content];
        this.totalElementos = response.totalElements;
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al consultar usuarios:', err);
        this.error = 'No se pudieron cargar los usuarios.';
        this.cargando = false;
      }
    });
  }

  onPageChange(event: number): void {
    this.p = event;
    this.obtenerUsuarios();
  }

  // para filtrar los usuarios por algún término de busqueda
  filtrarUsuarios(): void {
    if (!this.searchTerm.trim()) {
      this.usuariosFiltrados = [...this.usuarios];
    } else {
      const termino = this.searchTerm.toLowerCase().trim();
      this.usuariosFiltrados = this.usuarios.filter(usuario =>
        usuario.nombres?.toLowerCase().includes(termino) ||
        usuario.apellidos?.toLowerCase().includes(termino) ||
        usuario.correo?.toLowerCase().includes(termino) ||
        usuario.tipoUsuario?.nombre?.toLowerCase().includes(termino)
      );
    }
  }

  // Se ejecuta cuando se crea un nuevo usuario desde el modal
  onUsuarioCreado(response: any): void {
    console.log('Usuario creado:', response);
    // Recargar la lista de usuarios para mostrar el nuevo usuario
    this.obtenerUsuarios();
  }

  // Se ejecuta cuando se actualiza un usuario desde el modal
  onUsuarioActualizado(response: any): void {
    console.log('Usuario actualizado:', response);
    this.obtenerUsuarios();
  }

  // Selecciona el usuario para editar
  seleccionarUsuarioParaEditar(usuario: UserResponse): void {
    console.log('Usuario seleccionado para editar (area-users):', usuario); // Debug log
    console.log('Estructura de identificacion:', usuario.identificacion); // Debug log
    console.log('Estructura de tipoUsuario:', usuario.tipoUsuario); // Debug log
    this.usuarioSeleccionadoParaEditar.emit({
      usuario: usuario,
      tipoComponente: 'area'
    });
  }

  // Limpia el estado del modal
  private limpiarEstadoModal(): void {
    this.usuarioAEliminar = null;
    this.eliminando = false;
    this.mostrarMensajeExito = false;
    this.mensajeExito = '';
    this.errorEliminacion = '';
  }

  // Abre el modal de confirmación para eliminar un usuario
  eliminarUsuario(usuario: UserResponse): void {
    // Limpiar estado previo antes de abrir el modal
    this.limpiarEstadoModal();

    // Establecer el nuevo usuario a eliminar
    this.usuarioAEliminar = usuario;

    const modalElement = document.getElementById('confirmDeleteAreaModal');
    if (modalElement) {
      const modal = Modal.getInstance(modalElement) || new Modal(modalElement);
      modal.show();
    }
  }

  // Confirma la eliminación del usuario
  confirmarEliminacion(): void {
    if (!this.usuarioAEliminar) return;

    this.eliminando = true;
    this.error = '';

    this.userService.eliminarUsuario(this.usuarioAEliminar.identificador).subscribe({
      next: (response) => {
        console.log('Usuario eliminado exitosamente (area-users):', response); // Debug log
        this.eliminando = false;

        // Mostrar mensaje de éxito en el modal
        this.mensajeExito = 'Usuario eliminado exitosamente';
        this.mostrarMensajeExito = true;

        // Notificar a través del servicio para que todos los componentes se actualicen
        this.userNotificationService.notificarUsuarioEliminado(response);

        // Recargar la lista de usuarios
        this.obtenerUsuarios();

        // Cerrar el modal después de 2 segundos
        setTimeout(() => {
          const modalElement = document.getElementById('confirmDeleteAreaModal');
          if (modalElement) {
            const modal = Modal.getInstance(modalElement);
            if (modal) {
              modal.hide();
            }
          }

          // Limpiar el estado del modal
          this.limpiarEstadoModal();
        }, 2000);
      },
      error: (err) => {
        this.eliminando = false;

        if (err.error && err.error.mensaje) {
          this.errorEliminacion = err.error.mensaje;
        } else if (err.error && err.error.message) {
          this.errorEliminacion = err.error.message;
        } else {
          this.errorEliminacion = 'Error al eliminar el usuario. Por favor, intente nuevamente.';
        }
      }
    });
  }

}
