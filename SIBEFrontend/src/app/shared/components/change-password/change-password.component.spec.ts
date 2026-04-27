import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { ChangePasswordComponent } from './change-password.component';

describe('ChangePasswordComponent', () => {
  let component: ChangePasswordComponent;
  let fixture: ComponentFixture<ChangePasswordComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FormsModule],
      declarations: [ChangePasswordComponent],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(ChangePasswordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize passwords with empty strings', () => {
    expect(component.passwords.contrasenaActual).toBe('');
    expect(component.passwords.nuevaContrasena).toBe('');
    expect(component.passwords.confirmarContrasena).toBe('');
  });

  it('should set mensajeValidacion when new passwords do not match', () => {
    spyOn(component.contrasenaActualizada, 'emit');

    component.passwords = {
      contrasenaActual: 'OldPass1!',
      nuevaContrasena: 'NewPass1!',
      confirmarContrasena: 'NewPass2!'
    };
    component.cambiarContrasena();

    expect(component.mensajeValidacion).toBe('Las contraseñas nuevas no coinciden');
    expect(component.contrasenaActualizada.emit).not.toHaveBeenCalled();
  });

  it('should set mensajeValidacion when new password equals current password', () => {
    spyOn(component.contrasenaActualizada, 'emit');

    component.passwords = {
      contrasenaActual: 'SamePass1!',
      nuevaContrasena: 'SamePass1!',
      confirmarContrasena: 'SamePass1!'
    };
    component.cambiarContrasena();

    expect(component.mensajeValidacion).toBe('La nueva contraseña debe ser diferente a la actual');
    expect(component.contrasenaActualizada.emit).not.toHaveBeenCalled();
  });

  it('should emit contrasenaActualizada with correct payload on valid submit', () => {
    spyOn(component.contrasenaActualizada, 'emit');

    component.passwords = {
      contrasenaActual: 'OldPass1!',
      nuevaContrasena: 'NewPass1!',
      confirmarContrasena: 'NewPass1!'
    };
    component.cambiarContrasena();

    expect(component.contrasenaActualizada.emit).toHaveBeenCalledWith({
      contrasenaActual: 'OldPass1!',
      nuevaContrasena: 'NewPass1!'
    });
  });

  it('should set mensajeValidacion when password is too short', () => {
    component.passwords = {
      contrasenaActual: 'OldPass1!',
      nuevaContrasena: 'Ab1cd',
      confirmarContrasena: 'Ab1cd'
    };
    component.cambiarContrasena();
    expect(component.mensajeValidacion).toContain('al menos 8 caracteres');
  });

  it('should set mensajeValidacion when password has no uppercase', () => {
    component.passwords = {
      contrasenaActual: 'OldPass1!',
      nuevaContrasena: 'newpass123',
      confirmarContrasena: 'newpass123'
    };
    component.cambiarContrasena();
    expect(component.mensajeValidacion).toContain('letra mayúscula');
  });

  it('should set mensajeValidacion when password has no lowercase', () => {
    component.passwords = {
      contrasenaActual: 'OldPass1!',
      nuevaContrasena: 'NEWPASS123',
      confirmarContrasena: 'NEWPASS123'
    };
    component.cambiarContrasena();
    expect(component.mensajeValidacion).toContain('letra minúscula');
  });

  it('should set mensajeValidacion when password has no number', () => {
    component.passwords = {
      contrasenaActual: 'OldPass1!',
      nuevaContrasena: 'NewPassword!',
      confirmarContrasena: 'NewPassword!'
    };
    component.cambiarContrasena();
    expect(component.mensajeValidacion).toContain('un número');
  });

  it('should reset form fields on limpiarFormulario', () => {
    component.passwords = {
      contrasenaActual: 'old',
      nuevaContrasena: 'new',
      confirmarContrasena: 'new'
    };

    component.limpiarFormulario();

    expect(component.passwords.contrasenaActual).toBe('');
    expect(component.passwords.nuevaContrasena).toBe('');
    expect(component.passwords.confirmarContrasena).toBe('');
  });

  it('should accept input bindings', () => {
    component.cambiandoContrasena = true;
    component.mensajeError = 'Error test';
    component.mensajeExito = 'Success test';

    expect(component.cambiandoContrasena).toBeTrue();
    expect(component.mensajeError).toBe('Error test');
    expect(component.mensajeExito).toBe('Success test');
  });

  describe('cerrarModal', () => {
    it('should not throw when modal element not found', () => {
      spyOn(document, 'getElementById').and.returnValue(null);
      expect(() => component.cerrarModal()).not.toThrow();
    });
  });

  it('should try to close modal on cerrarModal', () => {
    spyOn(document, 'getElementById').and.returnValue(null);
    component.cerrarModal();
    expect(document.getElementById).toHaveBeenCalledWith('change-password-modal');
  });
});
