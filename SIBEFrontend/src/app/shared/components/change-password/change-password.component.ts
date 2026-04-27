import { Component, EventEmitter, Output, Input } from '@angular/core';
import { Modal } from 'bootstrap';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent {
  @Output() contrasenaActualizada = new EventEmitter<any>();
  @Input() cambiandoContrasena: boolean = false;
  @Input() mensajeError: string = '';
  @Input() mensajeExito: string = '';

  mensajeValidacion: string = '';

  passwords = {
    contrasenaActual: '',
    nuevaContrasena: '',
    confirmarContrasena: ''
  };

  cambiarContrasena() {
    this.mensajeValidacion = '';

    if (this.passwords.nuevaContrasena !== this.passwords.confirmarContrasena) {
      this.mensajeValidacion = 'Las contraseñas nuevas no coinciden';
      return;
    }

    if (this.passwords.nuevaContrasena === this.passwords.contrasenaActual) {
      this.mensajeValidacion = 'La nueva contraseña debe ser diferente a la actual';
      return;
    }

    const errorComplejidad = this.validarComplejidadClave(this.passwords.nuevaContrasena);
    if (errorComplejidad) {
      this.mensajeValidacion = errorComplejidad;
      return;
    }

    // Emitir el evento con las contraseñas para que el componente padre maneje la lógica
    this.contrasenaActualizada.emit({
      contrasenaActual: this.passwords.contrasenaActual,
      nuevaContrasena: this.passwords.nuevaContrasena
    });

    // No cerrar el modal aquí, el padre lo manejará
  }

  validarComplejidadClave(clave: string): string | null {
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

  limpiarFormulario() {
    this.passwords = {
      contrasenaActual: '',
      nuevaContrasena: '',
      confirmarContrasena: ''
    };
  }

  cerrarModal() {
    const modalElement = document.getElementById('change-password-modal');
    if (modalElement) {
      const modal = Modal.getInstance(modalElement) || new Modal(modalElement);
      modal.hide();
    }
  }
}
