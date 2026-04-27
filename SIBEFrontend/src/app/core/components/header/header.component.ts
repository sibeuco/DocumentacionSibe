import { Component, OnInit, OnDestroy, HostListener, ViewChild } from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { UserSession } from 'src/app/feature/login/model/user-session.model';
import { StateProps } from 'src/app/shared/model/state.enum';
import { StateService } from 'src/app/shared/service/state.service';
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';
import { Modal } from 'bootstrap';
import { UserService } from 'src/app/shared/service/user.service';
import { EditPasswordRequest } from 'src/app/shared/model/password.model';
import { ChangePasswordComponent } from 'src/app/shared/components/change-password/change-password.component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  @ViewChild('changePasswordComponent') changePasswordComponent!: ChangePasswordComponent;

  userSession$!: Observable<UserSession | undefined>;

  isLogged$!: Observable<boolean>;

  mostrarDropdown: boolean = false;

  // Propiedades para el cambio de contraseña
  cambiandoContrasena: boolean = false;
  mensajeError: string = '';
  mensajeExito: string = '';

  constructor(private stateService: StateService,
              private router: Router,
              private userService: UserService
  ) {}

  ngOnInit(): void {
    this.userSession$ = this.stateService.select<UserSession>(StateProps.USER_SESSION);

    this.isLogged$ = this.userSession$.pipe(
      map(session => !!session && session.logged)
    );
  }

  logout(): void {
    // 1. Limpia el estado global (esto hará que isLogged$ emita 'false')
    this.stateService.deleteProperty(StateProps.USER_SESSION);

    window.sessionStorage.removeItem('Authorization');

    this.router.navigate(['/login']);
  }

  abrirModalCambiarContrasena(): void {
    // Limpiar mensajes previos
    this.mensajeError = '';
    this.mensajeExito = '';
    this.cambiandoContrasena = false;

    const modalElement = document.getElementById('change-password-modal');
    if (modalElement) {
      const modal = Modal.getInstance(modalElement) || new Modal(modalElement);
      modal.show();
    }
  }

  onContrasenaActualizada(response: any): void {
    // Limpiar mensajes previos
    this.mensajeError = '';
    this.mensajeExito = '';
    this.cambiandoContrasena = true;

    // Timeout de seguridad para evitar carga infinita
    const timeoutId = setTimeout(() => {
      this.cambiandoContrasena = false;
      this.mensajeError = 'Tiempo de espera agotado. Por favor, intente nuevamente.';
    }, 30000);

    // Obtener la sesión de forma síncrona para evitar suscripciones que queden vivas
    const userSession = this.stateService.getState(StateProps.USER_SESSION) as UserSession | undefined;

    if (!userSession || !userSession.identificador) {
      clearTimeout(timeoutId);
      this.cambiandoContrasena = false;
      this.mensajeError = 'No se pudo obtener la información de sesión del usuario';
      return;
    }

    const editPasswordRequest: EditPasswordRequest = {
      identificador: userSession.identificador,
      claveAntigua: response.contrasenaActual,
      claveNueva: response.nuevaContrasena
    };

    this.userService.modificarClave(editPasswordRequest).subscribe({
      next: (result) => {
        clearTimeout(timeoutId);
        this.cambiandoContrasena = false;
        this.mensajeExito = 'Contraseña modificada exitosamente';

        // Cerrar el modal después de un breve delay
        setTimeout(() => {
          if (this.changePasswordComponent) {
            this.changePasswordComponent.cerrarModal();
            this.changePasswordComponent.limpiarFormulario();
          }
        }, 2000);
      },
      error: (error) => {
        clearTimeout(timeoutId);
        this.cambiandoContrasena = false;

        // Extraer el mensaje de error del backend
        let mensajeError = 'Error al modificar la contraseña. Por favor, intente nuevamente.';

        if (error.error) {
          if (typeof error.error === 'string') {
            mensajeError = error.error;
          } else if (error.error.mensaje) {
            mensajeError = error.error.mensaje;
          } else if (error.error.message) {
            mensajeError = error.error.message;
          } else if (error.error.error) {
            mensajeError = typeof error.error.error === 'string'
              ? error.error.error
              : mensajeError;
          } else if (error.error.valor) {
            mensajeError = error.error.valor;
          }
        } else if (error.message) {
          mensajeError = error.message;
        }

        this.mensajeError = mensajeError;
      }
    });
  }

  toggleDropdown(): void {
    this.mostrarDropdown = !this.mostrarDropdown;
  }

  cerrarDropdown(): void {
    this.mostrarDropdown = false;
  }

  /**
   * Listener para cerrar el dropdown cuando se hace clic fuera
   */
  @HostListener('document:click', ['$event'])
  onDocumentClick(event: Event): void {
    const target = event.target as HTMLElement;
    const dropdown = target.closest('.dropdown');

    if (!dropdown) {
      this.cerrarDropdown();
    }
  }
}
