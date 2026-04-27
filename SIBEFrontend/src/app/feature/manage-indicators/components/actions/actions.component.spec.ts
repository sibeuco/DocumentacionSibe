import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { of, throwError } from 'rxjs';
import * as bootstrap from 'bootstrap';

import { ActionsComponent } from './actions.component';
import { ActionService } from '../../service/action.service';
import { ActionResponse } from '../../model/action.model';
import { NgxPaginationModule } from 'ngx-pagination';

describe('ActionsComponent', () => {
  let component: ActionsComponent;
  let fixture: ComponentFixture<ActionsComponent>;
  let mockActionService: jasmine.SpyObj<ActionService>;

  const mockAcciones: ActionResponse[] = [
    { identificador: 'a1', detalle: 'Detalle 1', objetivo: 'Objetivo 1' },
    { identificador: 'a2', detalle: 'Detalle 2', objetivo: 'Objetivo 2' }
  ];

  const mockPaginatedResponse = {
    content: mockAcciones,
    totalElements: 2
  };

  beforeEach(() => {
    mockActionService = jasmine.createSpyObj('ActionService', ['consultarAcciones']);
    mockActionService.consultarAcciones.and.returnValue(of(mockPaginatedResponse));

    TestBed.configureTestingModule({
      imports: [FormsModule, NgxPaginationModule],
      declarations: [ActionsComponent],
      providers: [
        { provide: ActionService, useValue: mockActionService }
      ],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(ActionsComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    fixture.detectChanges();
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    it('should load actions', () => {
      fixture.detectChanges();
      expect(mockActionService.consultarAcciones).toHaveBeenCalled();
      expect(component.acciones).toEqual(mockAcciones);
    });
  });

  describe('obtenerAcciones', () => {
    it('should populate acciones from paginated response on success', () => {
      component.obtenerAcciones();
      expect(component.acciones).toEqual(mockAcciones);
      expect(component.accionesFiltradas).toEqual(mockAcciones);
      expect(component.totalElementos).toBe(2);
      expect(component.cargando).toBeFalse();
    });

    it('should handle error', () => {
      mockActionService.consultarAcciones.and.returnValue(throwError(() => new Error('fail')));
      component.obtenerAcciones();
      expect(component.error).toBe('No se pudieron cargar las acciones.');
      expect(component.cargando).toBeFalse();
    });
  });

  describe('onPageChange', () => {
    it('should update page and reload actions', () => {
      mockActionService.consultarAcciones.calls.reset();
      component.onPageChange(3);
      expect(component.p).toBe(3);
      expect(mockActionService.consultarAcciones).toHaveBeenCalledWith(2);
    });
  });

  describe('filterActions', () => {
    it('should reload actions', () => {
      component.filterActions();
      expect(component.accionesFiltradas).toBeDefined();
    });
  });

  describe('abrirModalEdicion', () => {
    it('should set accionSeleccionada', () => {
      component.abrirModalEdicion(mockAcciones[0]);
      expect(component.accionSeleccionada).toEqual(mockAcciones[0]);
    });
  });

  describe('onAccionModificada', () => {
    it('should reload actions and clear selection', () => {
      mockActionService.consultarAcciones.calls.reset();
      component.accionSeleccionada = mockAcciones[0];
      component.onAccionModificada(mockAcciones[0]);
      expect(mockActionService.consultarAcciones).toHaveBeenCalled();
      expect(component.accionSeleccionada).toBeNull();
    });
  });

  describe('onAccionCancelada', () => {
    it('should clear selection', () => {
      component.accionSeleccionada = mockAcciones[0];
      component.onAccionCancelada();
      expect(component.accionSeleccionada).toBeNull();
    });
  });

  describe('abrirModalEdicion with DOM element', () => {
    it('should show modal when element exists', () => {
      const mockModal = { show: jasmine.createSpy('show') };
      spyOn(bootstrap.Modal, 'getInstance').and.returnValue(mockModal as any);
      spyOn(document, 'getElementById').and.returnValue(document.createElement('div'));
      component.abrirModalEdicion(mockAcciones[0]);
      expect(mockModal.show).toHaveBeenCalled();
    });
  });

  describe('cerrarModal with DOM element', () => {
    it('should hide modal when element and instance exist', () => {
      const mockModal = { hide: jasmine.createSpy('hide') };
      spyOn(bootstrap.Modal, 'getInstance').and.returnValue(mockModal as any);
      spyOn(document, 'getElementById').and.returnValue(document.createElement('div'));
      component.onAccionCancelada();
      expect(mockModal.hide).toHaveBeenCalled();
    });
  });
});
