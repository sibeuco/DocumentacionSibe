import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { PasswordRecoveryService } from '../service/password-reccovery.service';
import { CodeRequest, RestorePasswordRequest } from '../model/password-recovery.model';

@Component({
  selector: 'app-password-recovery',
  templateUrl: './password-recovery.component.html',
  styleUrls: ['./password-recovery.component.scss']
})
export class PasswordRecoveryComponent {

  // Control de pasos
  currentStep: number = 1;

  // Datos del formulario
  correo: string = '';
  codigo: string = '';
  nuevaContrasena: string = '';
  confirmarContrasena: string = '';

  // Mensajes
  mensajeError: string = '';
  mensajeExito: string = '';

  // Estados de carga
  enviandoCodigo: boolean = false;
  validandoCodigo: boolean = false;
  cambiandoContrasena: boolean = false;

  constructor(
    private router: Router,
    private passwordRecoveryService: PasswordRecoveryService
  ) {}

  // Paso 1: Enviar código al correo
  enviarCodigo() {
    this.mensajeError = '';
    this.mensajeExito = '';

    if (!this.correo || !this.validarEmail(this.correo)) {
      this.mensajeError = 'Por favor ingresa un correo válido';
      return;
    }

    this.enviandoCodigo = true;

    this.passwordRecoveryService.solicitarCodigo(this.correo).subscribe({
      next: (response) => {
        this.enviandoCodigo = false;
        this.mensajeExito = 'Código enviado al correo.';

        setTimeout(() => {
          this.currentStep = 2;
        }, 1500);
      },
      error: (error) => {
        this.enviandoCodigo = false;

        // Manejar diferentes tipos de errores
        if (error.status === 0) {
          this.mensajeError = 'No se pudo conectar con el servidor';
        } else if (error.status === 400) {
          this.mensajeError = error.error?.mensaje || 'Error al solicitar el código';
        } else if (error.status === 404) {
          this.mensajeError = 'El correo no está registrado en el sistema';
        } else if (error.status === 500) {
          this.mensajeError = 'Error del servidor al enviar el código';
        } else {
          this.mensajeError = error.error?.mensaje || 'Error al solicitar el código de recuperación';
        }
      }
    });
  }

  // Paso 2: Validar código
  validarCodigo() {
    this.mensajeError = '';
    this.mensajeExito = '';

    if (!this.codigo) {
      this.mensajeError = 'Por favor ingresa el código';
      return;
    }

    this.validandoCodigo = true;

    const codeRequest: CodeRequest = {
      codigo: this.codigo,
      correo: this.correo
    };

    this.passwordRecoveryService.validarCodigo(codeRequest).subscribe({
      next: (response) => {
        this.validandoCodigo = false;

        if (response.valor === true) {
          this.mensajeExito = 'Código validado correctamente';
          setTimeout(() => {
            this.currentStep = 3;
          }, 1000);
        } else {
          this.mensajeError = 'Código incorrecto';
        }
      },
      error: (error) => {
        this.validandoCodigo = false;

        // Manejar diferentes tipos de errores
        if (error.status === 0) {
          this.mensajeError = 'No se pudo conectar con el servidor';
        } else if (error.status === 400) {
          this.mensajeError = error.error?.mensaje || 'Código incorrecto';
        } else if (error.status === 404) {
          this.mensajeError = 'El código ha expirado o no es válido';
        } else if (error.status === 500) {
          this.mensajeError = 'Error del servidor al validar el código';
        } else {
          this.mensajeError = error.error?.mensaje || 'Error al validar el código';
        }
      }
    });
  }

  // Paso 3: Cambiar contraseña
  cambiarContrasena() {
    this.mensajeError = '';
    this.mensajeExito = '';

    if (!this.nuevaContrasena || !this.confirmarContrasena) {
      this.mensajeError = 'Por favor completa todos los campos';
      return;
    }

    if (this.nuevaContrasena !== this.confirmarContrasena) {
      this.mensajeError = 'Las contraseñas no coinciden';
      return;
    }

    const errorComplejidad = this.validarComplejidadClave(this.nuevaContrasena);
    if (errorComplejidad) {
      this.mensajeError = errorComplejidad;
      return;
    }

    this.cambiandoContrasena = true;

    const restorePasswordRequest: RestorePasswordRequest = {
      correo: this.correo,
      clave: this.nuevaContrasena
    };

    this.passwordRecoveryService.recuperarClave(restorePasswordRequest).subscribe({
      next: (response) => {
        this.cambiandoContrasena = false;
        this.mensajeExito = 'Contraseña cambiada exitosamente. Redirigiendo...';

        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 2000);
      },
      error: (error) => {
        this.cambiandoContrasena = false;

        // Manejar diferentes tipos de errores
        if (error.status === 0) {
          this.mensajeError = 'No se pudo conectar con el servidor';
        } else if (error.status === 400) {
          this.mensajeError = error.error?.mensaje || 'Error al cambiar la contraseña';
        } else if (error.status === 404) {
          this.mensajeError = 'El código ha expirado, por favor solicita uno nuevo';
        } else if (error.status === 500) {
          this.mensajeError = 'Error del servidor al cambiar la contraseña';
        } else {
          this.mensajeError = error.error?.mensaje || 'Error al cambiar la contraseña';
        }
      }
    });
  }

  // Volver al paso anterior
  volverPaso() {
    if (this.currentStep > 1) {
      this.currentStep--;
      this.mensajeError = '';
      this.mensajeExito = '';
    }
  }

  // Volver al login
  volverLogin() {
    this.router.navigate(['/login']);
  }

  // Validar formato de email
  private validarEmail(email: string): boolean {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return regex.test(email);
  }

  private validarComplejidadClave(clave: string): string | null {
    if (clave.length < 8) {
      return 'La contraseña debe tener al menos 8 caracteres.';
    }
    if (!/[A-Z]/.test(clave)) {
      return 'La contraseña debe incluir al menos una letra mayúscula.';
    }
    if (!/[a-z]/.test(clave)) {
      return 'La contraseña debe incluir al menos una letra minúscula.';
    }
    if (!/[0-9]/.test(clave)) {
      return 'La contraseña debe incluir al menos un número.';
    }
    return null;
  }
}
