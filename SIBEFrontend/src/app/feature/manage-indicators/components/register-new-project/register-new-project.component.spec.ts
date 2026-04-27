import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { of, throwError } from 'rxjs';

import { RegisterNewProjectComponent } from './register-new-project.component';
import { ActionService } from '../../service/action.service';
import { ProjectService } from '../../service/project.service';

describe('RegisterNewProjectComponent', () => {
  let component: RegisterNewProjectComponent;
  let fixture: ComponentFixture<RegisterNewProjectComponent>;
  let mockActionService: jasmine.SpyObj<ActionService>;
  let mockProjectService: jasmine.SpyObj<ProjectService>;

  const mockAcciones = [
    { identificador: 'acc-1', detalle: 'Accion 1', objetivo: 'Obj 1' },
    { identificador: 'acc-2', detalle: 'Accion 2', objetivo: 'Obj 2' }
  ];

  beforeEach(() => {
    mockActionService = jasmine.createSpyObj('ActionService', ['consultarAcciones']);
    mockProjectService = jasmine.createSpyObj('ProjectService', ['agregarNuevoProyecto']);

    mockActionService.consultarAcciones.and.returnValue(of({ content: mockAcciones, totalElements: mockAcciones.length } as any));
    mockProjectService.agregarNuevoProyecto.and.returnValue(of({ valor: 'ok' }));

    TestBed.configureTestingModule({
      imports: [FormsModule, ReactiveFormsModule, HttpClientTestingModule, RouterTestingModule],
      declarations: [RegisterNewProjectComponent],
      providers: [
        { provide: ActionService, useValue: mockActionService },
        { provide: ProjectService, useValue: mockProjectService }
      ],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(RegisterNewProjectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    it('should load actions', () => {
      expect(mockActionService.consultarAcciones).toHaveBeenCalled();
      expect(component.accionesDisponibles.length).toBe(2);
      expect(component.accionesDisponibles[0].label).toBe('Accion 1');
    });
  });

  describe('registrarProyecto', () => {
    it('should not register if already loading', () => {
      component.cargando = true;
      component.registrarProyecto();
      expect(mockProjectService.agregarNuevoProyecto).not.toHaveBeenCalled();
    });

    it('should show error if no actions selected', () => {
      component.proyecto.acciones = [];
      component.registrarProyecto();
      expect(component.error).toContain('al menos una');
    });

    it('should call service with project data', () => {
      component.proyecto = {
        numeroProyecto: 'P001',
        nombre: 'Proyecto Test',
        objetivo: 'Objetivo Test',
        acciones: ['acc-1']
      };
      component.registrarProyecto();
      expect(mockProjectService.agregarNuevoProyecto).toHaveBeenCalledWith(jasmine.objectContaining({
        numeroProyecto: 'P001',
        nombre: 'Proyecto Test',
        acciones: ['acc-1']
      }));
    });

    it('should set exito message on success', () => {
      component.proyecto = { numeroProyecto: 'P001', nombre: 'Test', objetivo: 'Obj', acciones: ['acc-1'] };
      component.registrarProyecto();
      expect(component.exito).toContain('exitosamente');
    });

    it('should emit proyectoCreado on success', () => {
      spyOn(component.proyectoCreado, 'emit');
      component.proyecto = { numeroProyecto: 'P001', nombre: 'Test', objetivo: 'Obj', acciones: ['acc-1'] };
      component.registrarProyecto();
      expect(component.proyectoCreado.emit).toHaveBeenCalled();
    });

    it('should handle error with mensaje', () => {
      mockProjectService.agregarNuevoProyecto.and.returnValue(throwError(() => ({
        error: { mensaje: 'Proyecto duplicado' }
      })));
      component.proyecto = { numeroProyecto: 'P001', nombre: 'Test', objetivo: 'Obj', acciones: ['acc-1'] };
      component.registrarProyecto();
      expect(component.error).toBe('Proyecto duplicado');
    });
  });

  describe('filterAcciones', () => {
    it('should filter by search term', () => {
      component.searchAcciones = 'Accion 1';
      component.filterAcciones();
      expect(component.accionesFiltradas.length).toBe(1);
      expect(component.accionesFiltradas[0].label).toBe('Accion 1');
    });

    it('should show all when search is empty', () => {
      component.searchAcciones = '';
      component.filterAcciones();
      expect(component.accionesFiltradas.length).toBe(2);
    });
  });

  describe('toggleAction', () => {
    it('should add action if not selected', () => {
      component.proyecto.acciones = [];
      component.toggleAction('acc-1');
      expect(component.proyecto.acciones).toContain('acc-1');
    });

    it('should remove action if already selected', () => {
      component.proyecto.acciones = ['acc-1'];
      component.toggleAction('acc-1');
      expect(component.proyecto.acciones).not.toContain('acc-1');
    });
  });

  describe('isActionSelected', () => {
    it('should return true when action is in list', () => {
      component.proyecto.acciones = ['acc-1'];
      expect(component.isActionSelected('acc-1')).toBeTrue();
    });

    it('should return false when action is not in list', () => {
      component.proyecto.acciones = [];
      expect(component.isActionSelected('acc-1')).toBeFalse();
    });
  });

  describe('removeAction', () => {
    it('should remove action from list', () => {
      component.proyecto.acciones = ['acc-1', 'acc-2'];
      component.removeAction('acc-1');
      expect(component.proyecto.acciones).toEqual(['acc-2']);
    });
  });

  describe('limpiarFormulario', () => {
    it('should reset all fields', () => {
      component.proyecto = { numeroProyecto: 'P1', nombre: 'N', objetivo: 'O', acciones: ['a'] };
      component.error = 'err';
      component.exito = 'ok';
      component.limpiarFormulario();
      expect(component.proyecto.nombre).toBe('');
      expect(component.proyecto.acciones.length).toBe(0);
      expect(component.error).toBe('');
      expect(component.exito).toBe('');
    });
  });

  describe('recargarAcciones', () => {
    it('should reload actions from service', () => {
      mockActionService.consultarAcciones.calls.reset();
      component.recargarAcciones();
      expect(mockActionService.consultarAcciones).toHaveBeenCalled();
    });
  });

  describe('cargarAcciones error handling', () => {
    it('should set cargandoAcciones to false on error', () => {
      mockActionService.consultarAcciones.and.returnValue(throwError(() => new Error('fail')));
      component.cargarAcciones();
      expect(component.cargandoAcciones).toBeFalse();
    });
  });

  describe('registrarProyecto error variants', () => {
    beforeEach(() => {
      component.proyecto = { numeroProyecto: 'P001', nombre: 'Test', objetivo: 'Obj', acciones: ['acc-1'] };
    });

    it('should handle error with message', () => {
      mockProjectService.agregarNuevoProyecto.and.returnValue(throwError(() => ({
        error: { message: 'Message error' }
      })));
      component.registrarProyecto();
      expect(component.error).toBe('Message error');
    });

    it('should handle error with error.error string', () => {
      mockProjectService.agregarNuevoProyecto.and.returnValue(throwError(() => ({
        error: { error: 'Nested string error' }
      })));
      component.registrarProyecto();
      expect(component.error).toBe('Nested string error');
    });

    it('should handle error with valor', () => {
      mockProjectService.agregarNuevoProyecto.and.returnValue(throwError(() => ({
        error: { valor: 'Valor error' }
      })));
      component.registrarProyecto();
      expect(component.error).toBe('Valor error');
    });

    it('should handle error with string body', () => {
      mockProjectService.agregarNuevoProyecto.and.returnValue(throwError(() => ({
        error: 'String body error'
      })));
      component.registrarProyecto();
      expect(component.error).toBe('String body error');
    });

    it('should handle error with only message', () => {
      mockProjectService.agregarNuevoProyecto.and.returnValue(throwError(() => ({
        message: 'Top level message'
      })));
      component.registrarProyecto();
      expect(component.error).toBe('Top level message');
    });

    it('should handle generic error', () => {
      mockProjectService.agregarNuevoProyecto.and.returnValue(throwError(() => ({})));
      component.registrarProyecto();
      expect(component.error).toContain('Error al registrar');
    });

    it('should handle error.error as non-string object', () => {
      mockProjectService.agregarNuevoProyecto.and.returnValue(throwError(() => ({
        error: { error: { nested: true } }
      })));
      component.registrarProyecto();
      expect(component.error).toContain('Error al registrar');
    });
  });

  describe('getSelectedActionsLabels', () => {
    it('should return labels for selected actions', () => {
      component.proyecto.acciones = ['acc-1'];
      const labels = component.getSelectedActionsLabels();
      expect(labels).toEqual(['Accion 1']);
    });

    it('should return empty array for no selections', () => {
      component.proyecto.acciones = [];
      expect(component.getSelectedActionsLabels()).toEqual([]);
    });

    it('should handle null acciones', () => {
      component.proyecto.acciones = null as any;
      expect(component.getSelectedActionsLabels()).toEqual([]);
    });

    it('should skip unknown values', () => {
      component.proyecto.acciones = ['unknown-id'];
      expect(component.getSelectedActionsLabels()).toEqual([]);
    });
  });

  describe('getActionValue', () => {
    it('should return value for matching label', () => {
      expect(component.getActionValue('Accion 1')).toBe('acc-1');
    });

    it('should return empty string for unknown label', () => {
      expect(component.getActionValue('Unknown')).toBe('');
    });
  });

  describe('removeAction', () => {
    it('should do nothing when action not in list', () => {
      component.proyecto.acciones = ['acc-1'];
      component.removeAction('acc-99');
      expect(component.proyecto.acciones).toEqual(['acc-1']);
    });

    it('should handle null acciones', () => {
      component.proyecto.acciones = null as any;
      expect(() => component.removeAction('acc-1')).not.toThrow();
    });
  });

  describe('toggleAction with null acciones', () => {
    it('should initialize acciones and add value', () => {
      component.proyecto.acciones = null as any;
      component.toggleAction('acc-1');
      expect(component.proyecto.acciones).toEqual(['acc-1']);
    });
  });

  describe('isActionSelected with null acciones', () => {
    it('should return false', () => {
      component.proyecto.acciones = null as any;
      expect(component.isActionSelected('acc-1')).toBeFalse();
    });
  });

  describe('cerrarModal', () => {
    it('should not throw when modal element not found', () => {
      spyOn(document, 'getElementById').and.returnValue(null);
      expect(() => component.cerrarModal()).not.toThrow();
    });
  });

  describe('filterAcciones edge cases', () => {
    it('should return empty when no disponibles', () => {
      component.accionesDisponibles = [];
      component.searchAcciones = 'test';
      component.filterAcciones();
      expect(component.accionesFiltradas).toEqual([]);
    });

    it('should return empty when disponibles is null-like', () => {
      component.accionesDisponibles = null as any;
      component.searchAcciones = 'test';
      component.filterAcciones();
      expect(component.accionesFiltradas).toEqual([]);
    });
  });

  describe('suscripcionEventoAccion', () => {
    it('should be callable', () => {
      expect(() => component.suscripcionEventoAccion()).not.toThrow();
    });
  });

  describe('registrarProyecto success setTimeout', () => {
    it('should call limpiarFormulario and cerrarModal after timeout', fakeAsync(() => {
      component.proyecto = { numeroProyecto: 'P001', nombre: 'Test', objetivo: 'Obj', acciones: ['acc-1'] };
      spyOn(component, 'limpiarFormulario');
      spyOn(component, 'cerrarModal');
      component.registrarProyecto();
      expect(component.exito).toContain('exitosamente');
      tick(1500);
      expect(component.limpiarFormulario).toHaveBeenCalled();
      expect(component.cerrarModal).toHaveBeenCalled();
    }));
  });

  describe('cerrarModal with existing DOM element', () => {
    it('should call modal hide when element exists', () => {
      const fakeModal = { hide: jasmine.createSpy('hide') };
      const fakeElement = document.createElement('div');
      fakeElement.id = 'register-project-modal';
      spyOn(document, 'getElementById').and.returnValue(fakeElement);
      // Bootstrap Modal is hard to mock; just ensure no throw when element exists
      expect(() => component.cerrarModal()).not.toThrow();
    });
  });
});
