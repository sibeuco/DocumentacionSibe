import { Component, OnInit, OnDestroy, Output, EventEmitter } from '@angular/core';
import { UserService } from 'src/app/shared/service/user.service';
import { UserResponse } from 'src/app/shared/model/user.model';
import { UserNotificationService } from '../../service/user-notification.service';
import { Subscription } from 'rxjs';
import { Modal } from 'bootstrap';
import { DepartmentService } from 'src/app/shared/service/department.service';
import { DepartmentResponse } from 'src/app/shared/model/department.model';
import { UsuarioSeleccionadoParaEditar } from '../manage-users.component';

@Component({
  selector: 'app-department-users',
  templateUrl: './department-users.component.html',
  styleUrls: ['./department-users.component.scss']
})
export class DepartmentUsersComponent implements OnInit, OnDestroy{

  @Output() usuarioSeleccionadoParaEditar = new EventEmitter<UsuarioSeleccionadoParaEditar>();

  usuarios: UserResponse[] = [];
    usuariosFiltrados: UserResponse[] = [];
    cargando = false;
    error = '';
    searchTerm = '';
    private subscriptions: Subscription[] = [];

    // Propiedades para paginación
    p: number = 1;
    totalElementos: number = 0;

    // Propiedades para el modal de confirmación
    usuarioAEliminar: UserResponse | null = null;
    eliminando = false;
    mensajeExito = '';
    mostrarMensajeExito = false;
    errorEliminacion = '';

    // Propiedades para la lista desplegable
    listaDepartamentos: { identificador: string; nombre: string }[] = [];
    cargandoDepartamentos = false;

    constructor(
      private userService: UserService,
      private userNotificationService: UserNotificationService,
      private departmentService: DepartmentService
    ) {}

    ngOnInit(): void {
      this.obtenerUsuarios();
      this.suscribirseANotificaciones();
      this.cargarDepartamentos();
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

    cargarDepartamentos(): void {
      this.cargandoDepartamentos = true;
      this.departmentService.consultarDirecciones().subscribe({
        next: (departamentos: DepartmentResponse[]) => {
          this.listaDepartamentos = departamentos.map(dept => ({
            identificador: dept.identificador,
            nombre: dept.nombre
          }));
          this.cargandoDepartamentos = false;
        },
        error: (err) => {
          console.error('Error al cargar departamentos:', err);
          this.cargandoDepartamentos = false;
        }
      });
    }

    obtenerUsuarios(): void {
    this.cargando = true;
    this.error = '';

    this.userService.consultarUsuariosPorTipo('Administrador de dirección', this.p - 1).subscribe({
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
      // Recargar la lista de usuarios para mostrar los cambios
      this.obtenerUsuarios();
    }

    // Selecciona el usuario para editar
    seleccionarUsuarioParaEditar(usuario: UserResponse): void {
      console.log('Usuario seleccionado para editar (department-users):', usuario); // Debug log
      console.log('Estructura de identificacion (department):', usuario.identificacion); // Debug log
      console.log('Estructura de tipoUsuario (department):', usuario.tipoUsuario); // Debug log
      this.usuarioSeleccionadoParaEditar.emit({
        usuario: usuario,
        tipoComponente: 'department'
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

      const modalElement = document.getElementById('confirmDeleteDepartmentModal');
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
          console.log('Usuario eliminado exitosamente (department):', response); // Debug log
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
            const modalElement = document.getElementById('confirmDeleteDepartmentModal');
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
