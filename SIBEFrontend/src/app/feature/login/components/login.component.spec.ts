import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { of, throwError } from 'rxjs';
import { Router } from '@angular/router';

import { LoginComponent } from './login.component';
import { LoginService } from '../service/login.service';
import { StateService } from 'src/app/shared/service/state.service';
import { HttpService } from 'src/app/core/service/http.service';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let mockLoginService: jasmine.SpyObj<LoginService>;
  let mockStateService: jasmine.SpyObj<StateService>;
  let router: Router;

  beforeEach(() => {
    mockLoginService = jasmine.createSpyObj('LoginService', ['validarLogin']);
    mockStateService = jasmine.createSpyObj('StateService', ['updateState']);

    TestBed.configureTestingModule({
      imports: [FormsModule, ReactiveFormsModule, HttpClientTestingModule, RouterTestingModule],
      declarations: [LoginComponent],
      providers: [
        { provide: LoginService, useValue: mockLoginService },
        { provide: StateService, useValue: mockStateService },
        HttpService
      ],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    it('should initialize the login form', () => {
      expect(component.formularioLogin).toBeTruthy();
      expect(component.formularioLogin.get('userInput')).toBeTruthy();
      expect(component.formularioLogin.get('passwordInput')).toBeTruthy();
    });

    it('should have required validators on userInput', () => {
      const control = component.formularioLogin.get('userInput');
      control!.setValue('');
      expect(control!.valid).toBeFalse();
    });

    it('should have email validator on userInput', () => {
      const control = component.formularioLogin.get('userInput');
      control!.setValue('not-email');
      expect(control!.hasError('email')).toBeTrue();
    });

    it('should accept valid email', () => {
      const control = component.formularioLogin.get('userInput');
      control!.setValue('test@example.com');
      expect(control!.hasError('email')).toBeFalse();
    });

    it('should have required validator on passwordInput', () => {
      const control = component.formularioLogin.get('passwordInput');
      control!.setValue('');
      expect(control!.valid).toBeFalse();
    });
  });

  describe('clear error on value changes', () => {
    it('should clear error when userInput changes', () => {
      component.mensajeError = 'Some error';
      component.formularioLogin.get('userInput')!.setValue('new@email.com');
      expect(component.mensajeError).toBe('');
    });

    it('should clear error when passwordInput changes', () => {
      component.mensajeError = 'Some error';
      component.formularioLogin.get('passwordInput')!.setValue('newpass');
      expect(component.mensajeError).toBe('');
    });
  });

  describe('login', () => {
    it('should not call service when form is invalid', () => {
      component.formularioLogin.get('userInput')!.setValue('');
      component.formularioLogin.get('passwordInput')!.setValue('');
      component.login();
      expect(mockLoginService.validarLogin).not.toHaveBeenCalled();
    });

    it('should call loginService with credentials when form is valid', () => {
      // Create a fake JWT token
      const payload = btoa(JSON.stringify({
        email: 'test@example.com',
        id: 'user-1',
        authorities: 'ROLE_ADMIN',
        rol: 'admin',
        direccionId: 'dir-1',
        areaId: 'area-1',
        subareaId: 'sub-1'
      }));
      const fakeToken = 'header.' + payload + '.signature';
      spyOn(window.sessionStorage, 'getItem').and.returnValue(fakeToken);
      spyOn(router, 'navigate');

      mockLoginService.validarLogin.and.returnValue(of({ valor: 'ok' } as any));

      component.formularioLogin.get('userInput')!.setValue('test@example.com');
      component.formularioLogin.get('passwordInput')!.setValue('password123');
      component.login();

      expect(mockLoginService.validarLogin).toHaveBeenCalledWith({
        correo: 'test@example.com',
        contrasena: 'password123'
      });
      expect(mockStateService.updateState).toHaveBeenCalled();
      expect(router.navigate).toHaveBeenCalledWith(['/home']);
    });

    it('should set error message on login failure', () => {
      mockLoginService.validarLogin.and.returnValue(throwError(() => new Error('Unauthorized')));

      component.formularioLogin.get('userInput')!.setValue('test@example.com');
      component.formularioLogin.get('passwordInput')!.setValue('wrongpassword');
      component.login();

      expect(component.mensajeError).toContain('incorrectos');
    });
  });
});
