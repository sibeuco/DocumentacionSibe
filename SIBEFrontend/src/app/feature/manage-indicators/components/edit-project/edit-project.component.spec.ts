import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { NO_ERRORS_SCHEMA, SimpleChange } from '@angular/core';
import { of, throwError } from 'rxjs';

import { EditProjectComponent } from './edit-project.component';
import { ProjectService } from '../../service/project.service';
import { ActionService } from '../../service/action.service';

describe('EditProjectComponent', () => {
  let component: EditProjectComponent;
  let fixture: ComponentFixture<EditProjectComponent>;
  let mockProjectService: jasmine.SpyObj<ProjectService>;
  let mockActionService: jasmine.SpyObj<ActionService>;

  const mockAcciones = [
    { identificador: 'acc-1', detalle: 'Accion 1', objetivo: 'Obj 1' },
    { identificador: 'acc-2', detalle: 'Accion 2', objetivo: 'Obj 2' }
  ];

  const mockProyecto = {
    identificador: 'p1', numeroProyecto: '001', nombre: 'Proyecto 1', objetivo: 'Obj',
    acciones: [{ identificador: 'acc-1', detalle: 'Accion 1', objetivo: 'Obj 1' }]
  };

  beforeEach(() => {
    mockProjectService = jasmine.createSpyObj('ProjectService', ['modificarProyecto']);
    mockActionService = jasmine.createSpyObj('ActionService', ['consultarAcciones']);

    mockActionService.consultarAcciones.and.returnValue(of({ content: mockAcciones, totalElements: mockAcciones.length } as any));
    mockProjectService.modificarProyecto.and.returnValue(of({ valor: 'ok' }));

    TestBed.configureTestingModule({
      imports: [FormsModule],
      declarations: [EditProjectComponent],
      providers: [
        { provide: ProjectService, useValue: mockProjectService },
        { provide: ActionService, useValue: mockActionService }
      ],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(EditProjectComponent);
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
      expect(component.cargandoAcciones).toBeFalse();
    });
  });

  describe('cargarAcciones', () => {
    it('should map actions to value/label format', () => {
      expect(component.accionesDisponibles[0].value).toBe('acc-1');
      expect(component.accionesDisponibles[0].label).toBe('Accion 1');
      expect(component.accionesFiltradas.length).toBe(2);
    });

    it('should handle error', () => {
      mockActionService.consultarAcciones.and.returnValue(throwError(() => new Error('fail')));
      component.cargarAcciones();
      expect(component.cargandoAcciones).toBeFalse();
    });
  });

  describe('ngOnChanges', () => {
    it('should call cargarDatosProyecto when proyecto changes', () => {
      spyOn(component, 'cargarDatosProyecto');
      component.proyectoAEditar = mockProyecto as any;
      component.ngOnChanges({
        proyectoAEditar: new SimpleChange(null, mockProyecto, false)
      });
      expect(component.cargarDatosProyecto).toHaveBeenCalled();
    });
  });

  describe('cargarDatosProyecto', () => {
    it('should populate form from proyecto', () => {
      component.proyectoAEditar = mockProyecto as any;
      component.cargarDatosProyecto();
      expect(component.proyecto.numeroProyecto).toBe('001');
      expect(component.proyecto.nombre).toBe('Proyecto 1');
      expect(component.proyecto.objetivo).toBe('Obj');
      expect(component.proyecto.acciones).toEqual(['acc-1']);
      expect(component.error).toBe('');
    });

    it('should handle proyecto without acciones', () => {
      component.proyectoAEditar = { ...mockProyecto, acciones: undefined } as any;
      component.cargarDatosProyecto();
      expect(component.proyecto.acciones).toEqual([]);
    });
  });

  describe('modificarProyecto', () => {
    beforeEach(() => {
      component.proyectoAEditar = mockProyecto as any;
      component.proyecto = { numeroProyecto: 'P1', nombre: 'P1', objetivo: 'O1', acciones: ['acc-1'] };
    });

    it('should not call service when cargando', () => {
      component.cargando = true;
      component.modificarProyecto();
      expect(mockProjectService.modificarProyecto).not.toHaveBeenCalled();
    });

    it('should not call service when no proyecto to edit', () => {
      component.proyectoAEditar = null;
      component.modificarProyecto();
      expect(mockProjectService.modificarProyecto).not.toHaveBeenCalled();
    });

    it('should set error when no actions selected', () => {
      component.proyecto.acciones = [];
      component.modificarProyecto();
      expect(component.error).toBe('Debe seleccionar al menos una acción para el proyecto');
      expect(component.cargando).toBeFalse();
    });

    it('should call service and emit on success', fakeAsync(() => {
      spyOn(component.proyectoModificado, 'emit');
      component.modificarProyecto();
      expect(mockProjectService.modificarProyecto).toHaveBeenCalledWith('p1', jasmine.any(Object));
      expect(component.exito).toBe('Proyecto modificado exitosamente');
      tick(1500);
      expect(component.proyectoModificado.emit).toHaveBeenCalled();
    }));

    it('should handle error with string error body', () => {
      mockProjectService.modificarProyecto.and.returnValue(throwError(() => ({ error: 'String error' })));
      component.modificarProyecto();
      expect(component.error).toBe('String error');
      expect(component.cargando).toBeFalse();
    });

    it('should handle error with mensaje', () => {
      mockProjectService.modificarProyecto.and.returnValue(throwError(() => ({ error: { mensaje: 'Dup' } })));
      component.modificarProyecto();
      expect(component.error).toBe('Dup');
    });

    it('should handle error with message', () => {
      mockProjectService.modificarProyecto.and.returnValue(throwError(() => ({ error: { message: 'Bad' } })));
      component.modificarProyecto();
      expect(component.error).toBe('Bad');
    });

    it('should handle error with only error.message', () => {
      mockProjectService.modificarProyecto.and.returnValue(throwError(() => ({ message: 'Top level' })));
      component.modificarProyecto();
      expect(component.error).toBe('Top level');
    });

    it('should handle generic error', () => {
      mockProjectService.modificarProyecto.and.returnValue(throwError(() => ({})));
      component.modificarProyecto();
      expect(component.error).toBe('Error al modificar el proyecto. Por favor, intente nuevamente.');
    });
  });

  describe('cancelar', () => {
    it('should emit proyectoCancelado', () => {
      spyOn(component.proyectoCancelado, 'emit');
      component.cancelar();
      expect(component.proyectoCancelado.emit).toHaveBeenCalled();
    });
  });

  describe('limpiarFormulario', () => {
    it('should reset all fields', () => {
      component.proyecto = { numeroProyecto: 'X1', nombre: 'X', objetivo: 'Y', acciones: ['a'] };
      component.searchAcciones = 'search';
      component.error = 'e';
      component.exito = 's';
      component.limpiarFormulario();
      expect(component.proyecto.nombre).toBe('');
      expect(component.proyecto.objetivo).toBe('');
      expect(component.proyecto.acciones).toEqual([]);
      expect(component.searchAcciones).toBe('');
      expect(component.error).toBe('');
      expect(component.exito).toBe('');
    });
  });

  describe('filterAcciones', () => {
    it('should show all when search is empty', () => {
      component.searchAcciones = '';
      component.filterAcciones();
      expect(component.accionesFiltradas.length).toBe(2);
    });

    it('should filter by term', () => {
      component.searchAcciones = 'Accion 1';
      component.filterAcciones();
      expect(component.accionesFiltradas.length).toBe(1);
      expect(component.accionesFiltradas[0].label).toBe('Accion 1');
    });

    it('should return empty for no matches', () => {
      component.searchAcciones = 'xyz';
      component.filterAcciones();
      expect(component.accionesFiltradas.length).toBe(0);
    });
  });

  describe('isActionSelected', () => {
    it('should return true if action is selected', () => {
      component.proyecto.acciones = ['acc-1'];
      expect(component.isActionSelected('acc-1')).toBeTrue();
    });

    it('should return false if not selected', () => {
      component.proyecto.acciones = [];
      expect(component.isActionSelected('acc-1')).toBeFalse();
    });
  });

  describe('toggleAction', () => {
    it('should add action if not present', () => {
      component.proyecto.acciones = [];
      component.toggleAction('acc-1');
      expect(component.proyecto.acciones).toContain('acc-1');
    });

    it('should remove action if already present', () => {
      component.proyecto.acciones = ['acc-1'];
      component.toggleAction('acc-1');
      expect(component.proyecto.acciones).not.toContain('acc-1');
    });
  });

  describe('removeAction', () => {
    it('should remove action from list', () => {
      component.proyecto.acciones = ['acc-1', 'acc-2'];
      component.removeAction('acc-1');
      expect(component.proyecto.acciones).toEqual(['acc-2']);
    });
  });

  describe('getSelectedActionsLabels', () => {
    it('should return labels of selected actions', () => {
      component.proyecto.acciones = ['acc-1'];
      const labels = component.getSelectedActionsLabels();
      expect(labels).toEqual(['Accion 1']);
    });

    it('should return empty array when no acciones', () => {
      component.proyecto.acciones = [];
      expect(component.getSelectedActionsLabels()).toEqual([]);
    });
  });

  describe('getActionValue', () => {
    it('should return value for label', () => {
      expect(component.getActionValue('Accion 1')).toBe('acc-1');
    });

    it('should return empty for unknown label', () => {
      expect(component.getActionValue('Unknown')).toBe('');
    });
  });
});
