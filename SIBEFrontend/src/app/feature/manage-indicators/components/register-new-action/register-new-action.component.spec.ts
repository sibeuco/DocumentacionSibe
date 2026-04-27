import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { of, throwError } from 'rxjs';

import { RegisterNewActionComponent } from './register-new-action.component';
import { ActionService } from '../../service/action.service';

describe('RegisterNewActionComponent', () => {
  let component: RegisterNewActionComponent;
  let fixture: ComponentFixture<RegisterNewActionComponent>;
  let mockActionService: jasmine.SpyObj<ActionService>;

  beforeEach(() => {
    mockActionService = jasmine.createSpyObj('ActionService', ['agregarNuevaAccion']);
    mockActionService.agregarNuevaAccion.and.returnValue(of({ valor: 'ok' }));

    TestBed.configureTestingModule({
      imports: [FormsModule, ReactiveFormsModule, HttpClientTestingModule, RouterTestingModule],
      declarations: [RegisterNewActionComponent],
      providers: [
        { provide: ActionService, useValue: mockActionService }
      ],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(RegisterNewActionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('registrarAccion', () => {
    it('should not register if already loading', () => {
      component.cargando = true;
      component.registrarAccion();
      expect(mockActionService.agregarNuevaAccion).not.toHaveBeenCalled();
    });

    it('should call service with action data', () => {
      component.accion = { detalle: 'Test detalle', objetivo: 'Test objetivo' };
      component.registrarAccion();
      expect(mockActionService.agregarNuevaAccion).toHaveBeenCalledWith({
        detalle: 'Test detalle',
        objetivo: 'Test objetivo'
      });
    });

    it('should set exito message on success', () => {
      component.accion = { detalle: 'Test', objetivo: 'Obj' };
      component.registrarAccion();
      expect(component.exito).toContain('exitosamente');
    });

    it('should emit accionCreada on success', () => {
      spyOn(component.accionCreada, 'emit');
      component.accion = { detalle: 'Test', objetivo: 'Obj' };
      component.registrarAccion();
      expect(component.accionCreada.emit).toHaveBeenCalled();
    });

    it('should set error on failure with mensaje', () => {
      mockActionService.agregarNuevaAccion.and.returnValue(throwError(() => ({
        error: { mensaje: 'La accion ya existe' }
      })));
      component.accion = { detalle: 'Test', objetivo: 'Obj' };
      component.registrarAccion();
      expect(component.error).toBe('La accion ya existe');
      expect(component.cargando).toBeFalse();
    });

    it('should set error on failure with string error', () => {
      mockActionService.agregarNuevaAccion.and.returnValue(throwError(() => ({
        error: 'Error string'
      })));
      component.accion = { detalle: 'Test', objetivo: 'Obj' };
      component.registrarAccion();
      expect(component.error).toBe('Error string');
    });

    it('should set default error on unknown failure', () => {
      mockActionService.agregarNuevaAccion.and.returnValue(throwError(() => ({})));
      component.accion = { detalle: 'Test', objetivo: 'Obj' };
      component.registrarAccion();
      expect(component.error).toContain('Error al registrar');
    });
  });

  describe('limpiarFormulario', () => {
    it('should reset all fields', () => {
      component.accion = { detalle: 'Test', objetivo: 'Obj' };
      component.error = 'Some error';
      component.exito = 'Some success';
      component.limpiarFormulario();
      expect(component.accion.detalle).toBe('');
      expect(component.accion.objetivo).toBe('');
      expect(component.error).toBe('');
      expect(component.exito).toBe('');
    });
  });

  describe('registrarAccion error variants', () => {
    it('should handle error with message field', () => {
      mockActionService.agregarNuevaAccion.and.returnValue(throwError(() => ({
        error: { message: 'Message error' }
      })));
      component.accion = { detalle: 'Test', objetivo: 'Obj' };
      component.registrarAccion();
      expect(component.error).toBe('Message error');
    });

    it('should handle error with nested error string', () => {
      mockActionService.agregarNuevaAccion.and.returnValue(throwError(() => ({
        error: { error: 'Nested string error' }
      })));
      component.accion = { detalle: 'Test', objetivo: 'Obj' };
      component.registrarAccion();
      expect(component.error).toBe('Nested string error');
    });

    it('should handle error with valor field', () => {
      mockActionService.agregarNuevaAccion.and.returnValue(throwError(() => ({
        error: { valor: 'Valor error msg' }
      })));
      component.accion = { detalle: 'Test', objetivo: 'Obj' };
      component.registrarAccion();
      expect(component.error).toBe('Valor error msg');
    });

    it('should handle error with top-level message', () => {
      mockActionService.agregarNuevaAccion.and.returnValue(throwError(() => ({
        message: 'Top level message'
      })));
      component.accion = { detalle: 'Test', objetivo: 'Obj' };
      component.registrarAccion();
      expect(component.error).toBe('Top level message');
    });

    it('should handle error with non-string nested error', () => {
      mockActionService.agregarNuevaAccion.and.returnValue(throwError(() => ({
        error: { error: { nested: true } }
      })));
      component.accion = { detalle: 'Test', objetivo: 'Obj' };
      component.registrarAccion();
      expect(component.error).toContain('Error al registrar');
    });
  });

  describe('cerrarModal', () => {
    it('should not throw when modal element not found', () => {
      spyOn(document, 'getElementById').and.returnValue(null);
      expect(() => component.cerrarModal()).not.toThrow();
    });
  });
});
