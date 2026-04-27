import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { of, throwError } from 'rxjs';
import { Router } from '@angular/router';

import { PasswordRecoveryComponent } from './password-recovery.component';
import { PasswordRecoveryService } from '../service/password-reccovery.service';

describe('PasswordRecoveryComponent', () => {
  let component: PasswordRecoveryComponent;
  let fixture: ComponentFixture<PasswordRecoveryComponent>;
  let mockPasswordService: jasmine.SpyObj<PasswordRecoveryService>;
  let router: Router;

  beforeEach(() => {
    mockPasswordService = jasmine.createSpyObj('PasswordRecoveryService', [
      'solicitarCodigo', 'validarCodigo', 'recuperarClave'
    ]);
    mockPasswordService.solicitarCodigo.and.returnValue(of({ valor: 'ok' }));
    mockPasswordService.validarCodigo.and.returnValue(of({ valor: true }));
    mockPasswordService.recuperarClave.and.returnValue(of({ valor: 'ok' }));

    TestBed.configureTestingModule({
      imports: [FormsModule, ReactiveFormsModule, HttpClientTestingModule, RouterTestingModule],
      declarations: [PasswordRecoveryComponent],
      providers: [
        { provide: PasswordRecoveryService, useValue: mockPasswordService }
      ],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(PasswordRecoveryComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('initial state', () => {
    it('should start at step 1', () => {
      expect(component.currentStep).toBe(1);
    });

    it('should have empty fields', () => {
      expect(component.correo).toBe('');
      expect(component.codigo).toBe('');
      expect(component.nuevaContrasena).toBe('');
    });
  });

  describe('enviarCodigo (step 1)', () => {
    it('should show error for empty email', () => {
      component.correo = '';
      component.enviarCodigo();
      expect(component.mensajeError).toContain('correo');
    });

    it('should show error for invalid email', () => {
      component.correo = 'not-an-email';
      component.enviarCodigo();
      expect(component.mensajeError).toContain('correo');
    });

    it('should call service with valid email', () => {
      component.correo = 'test@example.com';
      component.enviarCodigo();
      expect(mockPasswordService.solicitarCodigo).toHaveBeenCalledWith('test@example.com');
    });

    it('should show success message on success', () => {
      component.correo = 'test@example.com';
      component.enviarCodigo();
      expect(component.mensajeExito).toContain('enviado');
      expect(component.enviandoCodigo).toBeFalse();
    });

    it('should handle 404 error', () => {
      mockPasswordService.solicitarCodigo.and.returnValue(throwError(() => ({
        status: 404
      })));
      component.correo = 'test@example.com';
      component.enviarCodigo();
      expect(component.mensajeError).toContain('no');
    });

    it('should handle connection error', () => {
      mockPasswordService.solicitarCodigo.and.returnValue(throwError(() => ({
        status: 0
      })));
      component.correo = 'test@example.com';
      component.enviarCodigo();
      expect(component.mensajeError).toContain('No se pudo conectar');
    });
  });

  describe('validarCodigo (step 2)', () => {
    it('should show error when code is empty', () => {
      component.codigo = '';
      component.validarCodigo();
      expect(component.mensajeError).toContain('ingresa el');
    });

    it('should call service with code and email', () => {
      component.correo = 'test@example.com';
      component.codigo = '123456';
      component.validarCodigo();
      expect(mockPasswordService.validarCodigo).toHaveBeenCalledWith({
        codigo: '123456',
        correo: 'test@example.com'
      });
    });

    it('should show success on valid code', () => {
      component.correo = 'test@example.com';
      component.codigo = '123456';
      component.validarCodigo();
      expect(component.mensajeExito).toContain('validado correctamente');
    });

    it('should show error on invalid code', () => {
      mockPasswordService.validarCodigo.and.returnValue(of({ valor: false }));
      component.correo = 'test@example.com';
      component.codigo = '000000';
      component.validarCodigo();
      expect(component.mensajeError).toContain('incorrecto');
    });

    it('should handle 400 error', () => {
      mockPasswordService.validarCodigo.and.returnValue(throwError(() => ({
        status: 400,
        error: { mensaje: 'Codigo expirado' }
      })));
      component.codigo = '123456';
      component.validarCodigo();
      expect(component.mensajeError).toBe('Codigo expirado');
    });
  });

  describe('cambiarContrasena (step 3)', () => {
    it('should show error when fields are empty', () => {
      component.nuevaContrasena = '';
      component.confirmarContrasena = '';
      component.cambiarContrasena();
      expect(component.mensajeError).toContain('completa todos los campos');
    });

    it('should show error when passwords do not match', () => {
      component.nuevaContrasena = 'password1';
      component.confirmarContrasena = 'password2';
      component.cambiarContrasena();
      expect(component.mensajeError).toContain('no coinciden');
    });

    it('should show error when password is too short', () => {
      component.nuevaContrasena = 'Ab1cd';
      component.confirmarContrasena = 'Ab1cd';
      component.cambiarContrasena();
      expect(component.mensajeError).toContain('al menos 8 caracteres');
    });

    it('should call service with valid passwords', () => {
      component.correo = 'test@example.com';
      component.nuevaContrasena = 'Newpass123';
      component.confirmarContrasena = 'Newpass123';
      component.cambiarContrasena();
      expect(mockPasswordService.recuperarClave).toHaveBeenCalledWith({
        correo: 'test@example.com',
        clave: 'Newpass123'
      });
    });

    it('should show success and redirect on success', () => {
      spyOn(router, 'navigate');
      component.correo = 'test@example.com';
      component.nuevaContrasena = 'Newpass123';
      component.confirmarContrasena = 'Newpass123';
      component.cambiarContrasena();
      expect(component.mensajeExito).toContain('exitosamente');
    });

    it('should handle connection error', () => {
      mockPasswordService.recuperarClave.and.returnValue(throwError(() => ({
        status: 0
      })));
      component.nuevaContrasena = 'Newpass123';
      component.confirmarContrasena = 'Newpass123';
      component.cambiarContrasena();
      expect(component.mensajeError).toContain('No se pudo conectar');
    });
  });

  describe('volverPaso', () => {
    it('should go back one step', () => {
      component.currentStep = 2;
      component.volverPaso();
      expect(component.currentStep).toBe(1);
    });

    it('should not go below step 1', () => {
      component.currentStep = 1;
      component.volverPaso();
      expect(component.currentStep).toBe(1);
    });

    it('should clear messages', () => {
      component.currentStep = 2;
      component.mensajeError = 'error';
      component.mensajeExito = 'success';
      component.volverPaso();
      expect(component.mensajeError).toBe('');
      expect(component.mensajeExito).toBe('');
    });
  });

  describe('volverLogin', () => {
    it('should navigate to login', () => {
      spyOn(router, 'navigate');
      component.volverLogin();
      expect(router.navigate).toHaveBeenCalledWith(['/login']);
    });
  });

  describe('additional error branches', () => {
    it('should handle 400 error on enviarCodigo', () => {
      mockPasswordService.solicitarCodigo.and.returnValue(throwError(() => ({ status: 400, error: { mensaje: 'Bad req' } })));
      component.correo = 'test@example.com';
      component.enviarCodigo();
      expect(component.mensajeError).toBe('Bad req');
    });

    it('should handle 500 error on enviarCodigo', () => {
      mockPasswordService.solicitarCodigo.and.returnValue(throwError(() => ({ status: 500 })));
      component.correo = 'test@example.com';
      component.enviarCodigo();
      expect(component.mensajeError).toContain('Error del servidor');
    });

    it('should handle generic error on enviarCodigo', () => {
      mockPasswordService.solicitarCodigo.and.returnValue(throwError(() => ({ status: 503 })));
      component.correo = 'test@example.com';
      component.enviarCodigo();
      expect(component.mensajeError).toContain('Error al solicitar');
    });

    it('should handle 0 error on validarCodigo', () => {
      mockPasswordService.validarCodigo.and.returnValue(throwError(() => ({ status: 0 })));
      component.codigo = '123';
      component.validarCodigo();
      expect(component.mensajeError).toContain('No se pudo conectar');
    });

    it('should handle 404 error on validarCodigo', () => {
      mockPasswordService.validarCodigo.and.returnValue(throwError(() => ({ status: 404 })));
      component.codigo = '123';
      component.validarCodigo();
      expect(component.mensajeError).toContain('expirado');
    });

    it('should handle 500 error on validarCodigo', () => {
      mockPasswordService.validarCodigo.and.returnValue(throwError(() => ({ status: 500 })));
      component.codigo = '123';
      component.validarCodigo();
      expect(component.mensajeError).toContain('Error del servidor');
    });

    it('should handle generic error on validarCodigo', () => {
      mockPasswordService.validarCodigo.and.returnValue(throwError(() => ({ status: 503 })));
      component.codigo = '123';
      component.validarCodigo();
      expect(component.mensajeError).toContain('Error al validar');
    });

    it('should handle 400 error on cambiarContrasena', () => {
      mockPasswordService.recuperarClave.and.returnValue(throwError(() => ({ status: 400, error: { mensaje: 'Bad clave' } })));
      component.nuevaContrasena = 'Newpass123';
      component.confirmarContrasena = 'Newpass123';
      component.cambiarContrasena();
      expect(component.mensajeError).toBe('Bad clave');
    });

    it('should handle 404 error on cambiarContrasena', () => {
      mockPasswordService.recuperarClave.and.returnValue(throwError(() => ({ status: 404 })));
      component.nuevaContrasena = 'Newpass123';
      component.confirmarContrasena = 'Newpass123';
      component.cambiarContrasena();
      expect(component.mensajeError).toContain('expirado');
    });

    it('should handle 500 error on cambiarContrasena', () => {
      mockPasswordService.recuperarClave.and.returnValue(throwError(() => ({ status: 500 })));
      component.nuevaContrasena = 'Newpass123';
      component.confirmarContrasena = 'Newpass123';
      component.cambiarContrasena();
      expect(component.mensajeError).toContain('Error del servidor');
    });

    it('should handle generic error on cambiarContrasena', () => {
      mockPasswordService.recuperarClave.and.returnValue(throwError(() => ({ status: 503 })));
      component.nuevaContrasena = 'Newpass123';
      component.confirmarContrasena = 'Newpass123';
      component.cambiarContrasena();
      expect(component.mensajeError).toContain('Error al cambiar');
    });
  });
});
