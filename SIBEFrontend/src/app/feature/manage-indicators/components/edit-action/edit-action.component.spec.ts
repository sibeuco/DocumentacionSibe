import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { of, throwError } from 'rxjs';

import { EditActionComponent } from './edit-action.component';
import { ActionService } from '../../service/action.service';

describe('EditActionComponent', () => {
  let component: EditActionComponent;
  let fixture: ComponentFixture<EditActionComponent>;
  let mockActionService: jasmine.SpyObj<ActionService>;

  beforeEach(() => {
    mockActionService = jasmine.createSpyObj('ActionService', ['modificarAccion']);
    mockActionService.modificarAccion.and.returnValue(of({ valor: 'ok' }));

    TestBed.configureTestingModule({
      imports: [FormsModule, ReactiveFormsModule, HttpClientTestingModule, RouterTestingModule],
      declarations: [EditActionComponent],
      providers: [
        { provide: ActionService, useValue: mockActionService }
      ],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(EditActionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    it('should load action data if accionAEditar is set', () => {
      component.accionAEditar = { identificador: 'acc-1', detalle: 'Test', objetivo: 'Obj' };
      component.ngOnInit();
      expect(component.accion.detalle).toBe('Test');
      expect(component.accion.objetivo).toBe('Obj');
    });
  });

  describe('ngOnChanges', () => {
    it('should reload data when accionAEditar changes', () => {
      const accion = { identificador: 'acc-1', detalle: 'Changed', objetivo: 'New Obj' };
      component.accionAEditar = accion;
      component.ngOnChanges({
        accionAEditar: { currentValue: accion, previousValue: null, firstChange: false, isFirstChange: () => false }
      });
      expect(component.accion.detalle).toBe('Changed');
      expect(component.accion.objetivo).toBe('New Obj');
    });
  });

  describe('cargarDatosAccion', () => {
    it('should set form data from accionAEditar', () => {
      component.accionAEditar = { identificador: 'acc-1', detalle: 'D1', objetivo: 'O1' };
      component.cargarDatosAccion();
      expect(component.accion.detalle).toBe('D1');
      expect(component.accion.objetivo).toBe('O1');
      expect(component.error).toBe('');
      expect(component.exito).toBe('');
    });
  });

  describe('modificarAccion', () => {
    it('should not modify if loading', () => {
      component.cargando = true;
      component.modificarAccion();
      expect(mockActionService.modificarAccion).not.toHaveBeenCalled();
    });

    it('should not modify if accionAEditar is null', () => {
      component.accionAEditar = null;
      component.modificarAccion();
      expect(mockActionService.modificarAccion).not.toHaveBeenCalled();
    });

    it('should call service with correct data', () => {
      component.accionAEditar = { identificador: 'acc-1', detalle: 'Old', objetivo: 'Old' };
      component.accion = { detalle: 'Updated', objetivo: 'Updated Obj' };
      component.modificarAccion();
      expect(mockActionService.modificarAccion).toHaveBeenCalledWith('acc-1', {
        detalle: 'Updated',
        objetivo: 'Updated Obj'
      });
    });

    it('should set exito message on success', () => {
      component.accionAEditar = { identificador: 'acc-1', detalle: 'D', objetivo: 'O' };
      component.accion = { detalle: 'D', objetivo: 'O' };
      component.modificarAccion();
      expect(component.exito).toContain('exitosamente');
    });

    it('should emit accionModificada on success', () => {
      spyOn(component.accionModificada, 'emit');
      component.accionAEditar = { identificador: 'acc-1', detalle: 'D', objetivo: 'O' };
      component.accion = { detalle: 'D2', objetivo: 'O2' };
      jasmine.clock().install();
      component.modificarAccion();
      jasmine.clock().tick(1600);
      expect(component.accionModificada.emit).toHaveBeenCalledWith(jasmine.objectContaining({
        identificador: 'acc-1',
        detalle: 'D2',
        objetivo: 'O2'
      }));
      jasmine.clock().uninstall();
    });

    it('should handle error with mensaje', () => {
      mockActionService.modificarAccion.and.returnValue(throwError(() => ({
        error: { mensaje: 'Error de servidor' }
      })));
      component.accionAEditar = { identificador: 'acc-1', detalle: 'D', objetivo: 'O' };
      component.accion = { detalle: 'D', objetivo: 'O' };
      component.modificarAccion();
      expect(component.error).toBe('Error de servidor');
      expect(component.cargando).toBeFalse();
    });

    it('should handle error with string error', () => {
      mockActionService.modificarAccion.and.returnValue(throwError(() => ({
        error: 'String error'
      })));
      component.accionAEditar = { identificador: 'acc-1', detalle: 'D', objetivo: 'O' };
      component.accion = { detalle: 'D', objetivo: 'O' };
      component.modificarAccion();
      expect(component.error).toBe('String error');
    });

    it('should handle error with error.error.message', () => {
      mockActionService.modificarAccion.and.returnValue(throwError(() => ({
        error: { message: 'Message error' }
      })));
      component.accionAEditar = { identificador: 'acc-1', detalle: 'D', objetivo: 'O' };
      component.accion = { detalle: 'D', objetivo: 'O' };
      component.modificarAccion();
      expect(component.error).toBe('Message error');
    });

    it('should handle error with top-level message', () => {
      mockActionService.modificarAccion.and.returnValue(throwError(() => ({
        message: 'Top level msg'
      })));
      component.accionAEditar = { identificador: 'acc-1', detalle: 'D', objetivo: 'O' };
      component.accion = { detalle: 'D', objetivo: 'O' };
      component.modificarAccion();
      expect(component.error).toBe('Top level msg');
    });

    it('should handle generic error without error or message', () => {
      mockActionService.modificarAccion.and.returnValue(throwError(() => ({})));
      component.accionAEditar = { identificador: 'acc-1', detalle: 'D', objetivo: 'O' };
      component.accion = { detalle: 'D', objetivo: 'O' };
      component.modificarAccion();
      expect(component.error).toBe('Error al modificar la acción. Por favor, intente nuevamente.');
    });
  });

  describe('cancelar', () => {
    it('should emit accionCancelada', () => {
      spyOn(component.accionCancelada, 'emit');
      component.cancelar();
      expect(component.accionCancelada.emit).toHaveBeenCalled();
    });
  });

  describe('limpiarFormulario', () => {
    it('should reset all fields', () => {
      component.accion = { detalle: 'Test', objetivo: 'Obj' };
      component.error = 'err';
      component.exito = 'ok';
      component.cargando = true;
      component.limpiarFormulario();
      expect(component.accion.detalle).toBe('');
      expect(component.accion.objetivo).toBe('');
      expect(component.error).toBe('');
      expect(component.exito).toBe('');
      expect(component.cargando).toBeFalse();
    });
  });
});
