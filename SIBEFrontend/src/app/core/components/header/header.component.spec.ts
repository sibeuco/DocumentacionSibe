import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import * as bootstrap from 'bootstrap';

import { HeaderComponent } from './header.component';
import { StateService } from 'src/app/shared/service/state.service';
import { UserService } from 'src/app/shared/service/user.service';
import { StateProps } from 'src/app/shared/model/state.enum';
import { UserSession } from 'src/app/feature/login/model/user-session.model';

describe('HeaderComponent', () => {
  let component: HeaderComponent;
  let fixture: ComponentFixture<HeaderComponent>;
  let mockStateService: jasmine.SpyObj<StateService>;
  let mockUserService: jasmine.SpyObj<UserService>;
  let router: Router;

  const mockSession: UserSession = {
    correo: 'test@test.com',
    identificador: '1',
    authorities: ['ROLE_ADMIN'],
    logged: true,
    rol: 'ADMIN',
    direccionId: '1',
    areaId: '1',
    subareaId: '1'
  };

  beforeEach(() => {
    mockStateService = jasmine.createSpyObj('StateService', ['select', 'deleteProperty', 'getState']);
    mockStateService.select.and.returnValue(of(mockSession));
    mockStateService.getState.and.returnValue(mockSession);

    mockUserService = jasmine.createSpyObj('UserService', ['consultarUsuarioPorCorreo', 'modificarClave']);

    TestBed.configureTestingModule({
      imports: [FormsModule, ReactiveFormsModule, HttpClientTestingModule, RouterTestingModule],
      declarations: [HeaderComponent],
      providers: [
        { provide: StateService, useValue: mockStateService },
        { provide: UserService, useValue: mockUserService }
      ],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(HeaderComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    spyOn(router, 'navigate');
    fixture.detectChanges();
  });

  afterEach(() => {
    sessionStorage.clear();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize userSession$ and isLogged$ on ngOnInit', () => {
    expect(mockStateService.select).toHaveBeenCalledWith(StateProps.USER_SESSION);
    expect(component.userSession$).toBeDefined();
    expect(component.isLogged$).toBeDefined();
  });

  it('should emit true from isLogged$ when session is logged', (done) => {
    component.isLogged$.subscribe(isLogged => {
      expect(isLogged).toBeTrue();
      done();
    });
  });

  it('should emit false from isLogged$ when session is undefined', (done) => {
    mockStateService.select.and.returnValue(of(undefined));
    component.ngOnInit();

    component.isLogged$.subscribe(isLogged => {
      expect(isLogged).toBeFalse();
      done();
    });
  });

  it('should clear state and navigate to /login on logout', () => {
    sessionStorage.setItem('Authorization', 'test-token');

    component.logout();

    expect(mockStateService.deleteProperty).toHaveBeenCalledWith(StateProps.USER_SESSION);
    expect(sessionStorage.getItem('Authorization')).toBeNull();
    expect(router.navigate).toHaveBeenCalledWith(['/login']);
  });

  it('should toggle dropdown visibility', () => {
    expect(component.mostrarDropdown).toBeFalse();

    component.toggleDropdown();
    expect(component.mostrarDropdown).toBeTrue();

    component.toggleDropdown();
    expect(component.mostrarDropdown).toBeFalse();
  });

  it('should close dropdown on cerrarDropdown', () => {
    component.mostrarDropdown = true;
    component.cerrarDropdown();
    expect(component.mostrarDropdown).toBeFalse();
  });

  it('should close dropdown when clicking outside', () => {
    component.mostrarDropdown = true;
    const event = new MouseEvent('click');
    Object.defineProperty(event, 'target', {
      value: document.createElement('div'),
      writable: false
    });

    component.onDocumentClick(event);
    expect(component.mostrarDropdown).toBeFalse();
  });

  it('should handle password update success flow', fakeAsync(() => {
    mockUserService.modificarClave.and.returnValue(of({ valor: 'ok' } as any));

    // Provide mock changePasswordComponent so the setTimeout callback doesn't fail
    component.changePasswordComponent = jasmine.createSpyObj('ChangePasswordComponent', ['cerrarModal', 'limpiarFormulario']) as any;

    component.onContrasenaActualizada({ contrasenaActual: 'old', nuevaContrasena: 'new' });

    expect(mockStateService.getState).toHaveBeenCalledWith(StateProps.USER_SESSION);
    expect(mockUserService.modificarClave).toHaveBeenCalled();
    expect(component.mensajeExito).toBe('Contraseña modificada exitosamente');
    expect(component.cambiandoContrasena).toBeFalse();

    // Flush the setTimeout(2000) so it doesn't leak
    tick(2000);
  }));

  it('should set mensajeError when password update fails', () => {
    mockUserService.modificarClave.and.returnValue(throwError(() => ({ error: { mensaje: 'Clave incorrecta' } })));

    component.onContrasenaActualizada({ contrasenaActual: 'old', nuevaContrasena: 'new' });

    expect(component.mensajeError).toBe('Clave incorrecta');
    expect(component.cambiandoContrasena).toBeFalse();
  });

  it('should set mensajeError when session is undefined', () => {
    mockStateService.getState.and.returnValue(undefined);

    component.onContrasenaActualizada({ contrasenaActual: 'old', nuevaContrasena: 'new' });

    expect(component.mensajeError).toBe('No se pudo obtener la información de sesión del usuario');
    expect(component.cambiandoContrasena).toBeFalse();
  });

  it('should set mensajeError when session has no identificador', () => {
    mockStateService.getState.and.returnValue({ logged: true } as any);

    component.onContrasenaActualizada({ contrasenaActual: 'old', nuevaContrasena: 'new' });

    expect(component.mensajeError).toBe('No se pudo obtener la información de sesión del usuario');
    expect(component.cambiandoContrasena).toBeFalse();
  });

  it('should handle error.error as string', () => {
    mockUserService.modificarClave.and.returnValue(throwError(() => ({ error: 'Error string directo' })));

    component.onContrasenaActualizada({ contrasenaActual: 'old', nuevaContrasena: 'new' });

    expect(component.mensajeError).toBe('Error string directo');
  });

  it('should handle error.error.message', () => {
    mockUserService.modificarClave.and.returnValue(throwError(() => ({ error: { message: 'Msg error' } })));

    component.onContrasenaActualizada({ contrasenaActual: 'old', nuevaContrasena: 'new' });

    expect(component.mensajeError).toBe('Msg error');
  });

  it('should handle error.error.error as string', () => {
    mockUserService.modificarClave.and.returnValue(throwError(() => ({ error: { error: 'Sub-error str' } })));

    component.onContrasenaActualizada({ contrasenaActual: 'old', nuevaContrasena: 'new' });

    expect(component.mensajeError).toBe('Sub-error str');
  });

  it('should handle error.error.valor', () => {
    mockUserService.modificarClave.and.returnValue(throwError(() => ({ error: { valor: 'Valor error' } })));

    component.onContrasenaActualizada({ contrasenaActual: 'old', nuevaContrasena: 'new' });

    expect(component.mensajeError).toBe('Valor error');
  });

  it('should handle error.message fallback', () => {
    mockUserService.modificarClave.and.returnValue(throwError(() => ({ message: 'Top-level message' })));

    component.onContrasenaActualizada({ contrasenaActual: 'old', nuevaContrasena: 'new' });

    expect(component.mensajeError).toBe('Top-level message');
  });

  it('should try to open change password modal', () => {
    spyOn(document, 'getElementById').and.returnValue(null);
    component.abrirModalCambiarContrasena();
    expect(component.mensajeError).toBe('');
    expect(component.mensajeExito).toBe('');
    expect(component.cambiandoContrasena).toBeFalse();
    expect(document.getElementById).toHaveBeenCalledWith('change-password-modal');
  });

  it('should not close dropdown when clicking inside dropdown', () => {
    component.mostrarDropdown = true;
    const dropdownEl = document.createElement('div');
    dropdownEl.className = 'dropdown';
    const innerEl = document.createElement('span');
    dropdownEl.appendChild(innerEl);
    document.body.appendChild(dropdownEl);

    const event = new MouseEvent('click');
    Object.defineProperty(event, 'target', { value: innerEl, writable: false });

    component.onDocumentClick(event);
    expect(component.mostrarDropdown).toBeTrue();

    document.body.removeChild(dropdownEl);
  });

  describe('abrirModalCambiarContrasena with element', () => {
    it('should show modal when DOM element exists', () => {
      const mockModal = { show: jasmine.createSpy('show') };
      spyOn(bootstrap.Modal, 'getInstance').and.returnValue(mockModal as any);
      const el = document.createElement('div');
      spyOn(document, 'getElementById').and.returnValue(el);
      component.abrirModalCambiarContrasena();
      expect(mockModal.show).toHaveBeenCalled();
    });
  });

  describe('onContrasenaActualizada timeout handler', () => {
    it('should set error on timeout', fakeAsync(() => {
      // Make modificarClave never complete (simulate timeout)
      mockUserService.modificarClave.and.returnValue(new (require('rxjs').Observable)(() => {}));
      component.onContrasenaActualizada({ contrasenaActual: 'old', nuevaContrasena: 'new' });
      tick(30000);
      expect(component.mensajeError).toContain('Tiempo de espera agotado');
      expect(component.cambiandoContrasena).toBeFalse();
    }));
  });
});
