import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { of, throwError } from 'rxjs';
import { Router } from '@angular/router';

import { DateSelectorComponent } from './date-selector.component';
import { ActivityService } from '../../service/activity.service';
import { NgxPaginationModule } from 'ngx-pagination';

describe('DateSelectorComponent', () => {
  let component: DateSelectorComponent;
  let fixture: ComponentFixture<DateSelectorComponent>;
  let mockActivityService: jasmine.SpyObj<ActivityService>;
  let router: Router;

  const mockEjecuciones = [
      { identificador: 'ej-1', fechaProgramada: '2025-06-15', estadoActividad: { nombre: 'Pendiente' } },
      { identificador: 'ej-2', fechaProgramada: '2025-07-20', estadoActividad: { nombre: 'En curso' } },
      { identificador: 'ej-3', fechaProgramada: '2025-05-01', estadoActividad: { nombre: 'Finalizada' } }
    ];

  const mockPaginatedResponse = {
    content: mockEjecuciones,
    totalElements: 3
  };

  beforeEach(() => {
    mockActivityService = jasmine.createSpyObj('ActivityService', ['consultarEjecuciones', 'consultarEjecucionesPaginado']);
    mockActivityService.consultarEjecuciones.and.returnValue(of(mockEjecuciones as any));
    mockActivityService.consultarEjecucionesPaginado.and.returnValue(of(mockPaginatedResponse as any));

    // Mock window.bootstrap for cerrarModalCompletamente()
    (window as any).bootstrap = { Modal: { getInstance: () => null } };

    TestBed.configureTestingModule({
      imports: [FormsModule, ReactiveFormsModule, HttpClientTestingModule, RouterTestingModule, NgxPaginationModule],
      declarations: [DateSelectorComponent],
      providers: [
        { provide: ActivityService, useValue: mockActivityService }
      ],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(DateSelectorComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
  });

  it('should create', () => {
    fixture.detectChanges();
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    it('should load executions when actividad is set', () => {
      component.actividad = { identificador: 'act-1' } as any;
      fixture.detectChanges();
      expect(mockActivityService.consultarEjecucionesPaginado).toHaveBeenCalledWith('act-1', 0);
    });

    it('should not load executions when actividad is null', () => {
      component.actividad = null;
      fixture.detectChanges();
      expect(mockActivityService.consultarEjecucionesPaginado).not.toHaveBeenCalled();
    });
  });

  describe('ngOnChanges', () => {
    it('should reload executions when actividad changes', () => {
      component.actividad = { identificador: 'act-1' } as any;
      fixture.detectChanges();
      mockActivityService.consultarEjecuciones.calls.reset();

      component.actividad = { identificador: 'act-2' } as any;
      component.ngOnChanges({
        actividad: { currentValue: { identificador: 'act-2' }, previousValue: { identificador: 'act-1' }, firstChange: false, isFirstChange: () => false }
      });

      expect(mockActivityService.consultarEjecucionesPaginado).toHaveBeenCalledWith('act-2', 0);
    });
  });

  describe('after loading executions', () => {
    beforeEach(() => {
      component.actividad = { identificador: 'act-1' } as any;
      fixture.detectChanges();
    });

    it('should map executions to sorted fechas', () => {
      expect(component.fechasOrdenadas.length).toBe(3);
      // sorted by date ascending; generarIdEjecucion uses index+1 for non-numeric ids
      expect(component.fechasOrdenadas[0].id).toBe(3); // May (ej-3, index 2)
      expect(component.fechasOrdenadas[1].id).toBe(1); // June (ej-1, index 0)
      expect(component.fechasOrdenadas[2].id).toBe(2); // July (ej-2, index 1)
    });

    it('should set cargando to false', () => {
      expect(component.cargando).toBeFalse();
    });
  });

  describe('error handling', () => {
    it('should set error when service fails', () => {
      mockActivityService.consultarEjecucionesPaginado.and.returnValue(throwError(() => new Error('fail')));
      component.actividad = { identificador: 'act-1' } as any;
      fixture.detectChanges();
      expect(component.error).toContain('Error al cargar');
    });

    it('should clear fechas on empty actividad', () => {
      component.actividad = { identificador: '' } as any;
      fixture.detectChanges();
      expect(component.fechasOrdenadas.length).toBe(0);
    });
  });

  describe('getStatusClass', () => {
    it('should return status-pendiente for PENDIENTE', () => {
      expect(component.getStatusClass('Pendiente' as any)).toBe('status-pendiente');
    });

    it('should return status-en-curso for EN_CURSO', () => {
      expect(component.getStatusClass('En curso' as any)).toBe('status-en-curso');
    });

    it('should return status-finalizada for FINALIZADO', () => {
      expect(component.getStatusClass('Finalizada' as any)).toBe('status-finalizada');
    });

    it('should return status-pendiente for unknown', () => {
      expect(component.getStatusClass('UNKNOWN' as any)).toBe('status-pendiente');
    });
  });

  describe('verFechaProgramada', () => {
    it('should emit verFecha event', () => {
      component.actividad = { identificador: 'act-1' } as any;
      fixture.detectChanges();
      spyOn(component.verFecha, 'emit');
      spyOn(router, 'navigate');

      const fecha = component.fechasOrdenadas[0];
      component.verFechaProgramada(fecha);

      expect(component.verFecha.emit).toHaveBeenCalledWith(fecha);
    });

    it('should navigate when redirectLink is set', () => {
      component.actividad = { identificador: 'act-1' } as any;
      component.redirectLink = '/test-route';
      fixture.detectChanges();
      spyOn(component.verFecha, 'emit');
      spyOn(router, 'navigate');

      const fecha = component.fechasOrdenadas[0];
      component.verFechaProgramada(fecha);

      expect(router.navigate).toHaveBeenCalledWith(
        ['/test-route'],
        jasmine.objectContaining({ queryParams: jasmine.any(Object) })
      );
    });

    it('should not navigate when no redirectLink', () => {
      component.actividad = { identificador: 'act-1' } as any;
      component.redirectLink = '';
      fixture.detectChanges();
      spyOn(component.verFecha, 'emit');
      spyOn(router, 'navigate');

      const fecha = component.fechasOrdenadas[0];
      component.verFechaProgramada(fecha);

      expect(router.navigate).not.toHaveBeenCalled();
    });
  });

  describe('cerrar', () => {
    it('should emit cerrarModal event', () => {
      spyOn(component.cerrarModal, 'emit');
      component.cerrar();
      expect(component.cerrarModal.emit).toHaveBeenCalled();
    });
  });

  describe('trackByFechaId', () => {
    it('should return id', () => {
      const fecha = { id: 99, fecha: new Date(), estado: 'Pendiente' as any, actividadId: 0 };
      expect(component.trackByFechaId(0, fecha)).toBe(99);
    });
  });

  describe('mapearEstado edge cases', () => {
    it('should return PENDIENTE for undefined estado', () => {
      const result = (component as any).mapearEstado(undefined);
      expect(result).toBe('Pendiente');
    });

    it('should return PENDIENTE for unknown estado', () => {
      const result = (component as any).mapearEstado('Desconocido');
      expect(result).toBe('Pendiente');
    });

    it('should map en curso', () => {
      const result = (component as any).mapearEstado('en curso');
      expect(result).toBe('En curso');
    });

    it('should map finalizada', () => {
      const result = (component as any).mapearEstado('finalizada');
      expect(result).toBe('Finalizada');
    });
  });

  describe('parseFechaLocal edge cases', () => {
    it('should parse YYYY-MM-DD format', () => {
      const result = (component as any).parseFechaLocal('2025-03-15');
      expect(result.getFullYear()).toBe(2025);
      expect(result.getMonth()).toBe(2);
      expect(result.getDate()).toBe(15);
    });

    it('should parse ISO format', () => {
      const result = (component as any).parseFechaLocal('2025-06-15T10:00:00Z');
      expect(result instanceof Date).toBeTrue();
      expect(isNaN(result.getTime())).toBeFalse();
    });

    it('should return current date for invalid format', () => {
      const result = (component as any).parseFechaLocal('not-a-date');
      expect(result instanceof Date).toBeTrue();
    });
  });

  describe('guardarSeleccion edge cases', () => {
    it('should remove storage when actividad is null', () => {
      spyOn(window.sessionStorage, 'removeItem');
      (component as any).guardarSeleccion(null, null);
      expect(window.sessionStorage.removeItem).toHaveBeenCalledWith('selectedActivityInfo');
    });

    it('should save actividad and ejecucion', () => {
      spyOn(window.sessionStorage, 'setItem');
      const act = { identificador: 'act-1' } as any;
      const ej = { identificador: 'ej-1' } as any;
      (component as any).guardarSeleccion(act, ej);
      expect(window.sessionStorage.setItem).toHaveBeenCalled();
    });
  });

  describe('normalizarTexto', () => {
    it('should normalize accented text', () => {
      expect((component as any).normalizarTexto('Dirección')).toBe('direccion');
    });

    it('should trim and collapse spaces', () => {
      expect((component as any).normalizarTexto('  a   b  ')).toBe('a b');
    });
  });

  describe('obtenerEjecucionPorFecha', () => {
    it('should return undefined for null fechaProgramada', () => {
      expect((component as any).obtenerEjecucionPorFecha(null)).toBeUndefined();
    });

    it('should find by id in map', () => {
      component.actividad = { identificador: 'act-1' } as any;
      fixture.detectChanges();
      // After loading, ejecucionesPorId should have entries
      const fecha = component.fechasOrdenadas[0];
      const result = (component as any).obtenerEjecucionPorFecha(fecha);
      expect(result).toBeTruthy();
      expect(result.identificador).toBeDefined();
    });

    it('should find by timestamp fallback', () => {
      component.actividad = { identificador: 'act-1' } as any;
      fixture.detectChanges();
      // Create a fake fecha with matching timestamp but unknown id
      const ejDate = (component as any).parseFechaLocal('2025-06-15');
      const customFecha = { id: 999, fecha: ejDate, estado: 'Pendiente' as any, actividadId: 0 };
      const result = (component as any).obtenerEjecucionPorFecha(customFecha);
      // Should find via timestamp match
      expect(result).toBeTruthy();
    });
  });

  describe('cerrarModalCompletamente', () => {
    it('should not throw when modal element not found', () => {
      spyOn(document, 'getElementById').and.returnValue(null);
      spyOn(component.cerrarModal, 'emit');
      (component as any).cerrarModalCompletamente();
      expect(component.cerrarModal.emit).toHaveBeenCalled();
    });
  });

  describe('cargarEjecuciones edge cases', () => {
    it('should clear fechas when actividad has no identificador', () => {
      component.actividad = { identificador: '' } as any;
      (component as any).cargarEjecuciones();
      expect(component.fechasOrdenadas).toEqual([]);
    });

    it('should filter out ejecuciones without fechaProgramada', () => {
      mockActivityService.consultarEjecucionesPaginado.and.returnValue(of({
          content: [
            { identificador: 'ej-1', fechaProgramada: '2025-06-15', estadoActividad: { nombre: 'Pendiente' } },
            { identificador: 'ej-2', fechaProgramada: null, estadoActividad: { nombre: 'Pendiente' } }
          ],
          totalElements: 2
        } as any));
      component.actividad = { identificador: 'act-1' } as any;
      fixture.detectChanges();
      expect(component.fechasOrdenadas.length).toBe(1);
    });
  });

  describe('cerrarModalCompletamente with element found', () => {
    it('should hide modal and clean up backdrop', fakeAsync(() => {
      const mockModal = { hide: jasmine.createSpy('hide') };
      (window as any).bootstrap = { Modal: { getInstance: () => mockModal } };
      const el = document.createElement('div');
      spyOn(document, 'getElementById').and.returnValue(el);

      const backdrop = document.createElement('div');
      backdrop.classList.add('modal-backdrop');
      document.body.appendChild(backdrop);
      document.body.classList.add('modal-open');

      spyOn(component.cerrarModal, 'emit');
      (component as any).cerrarModalCompletamente();
      expect(mockModal.hide).toHaveBeenCalled();
      tick(150);

      expect(document.body.classList.contains('modal-open')).toBeFalse();
      expect(component.cerrarModal.emit).toHaveBeenCalled();
    }));
  });

  describe('obtenerEjecucionPorFecha with null', () => {
    it('should return undefined when fechaProgramada is null', () => {
      const result = (component as any).obtenerEjecucionPorFecha(null);
      expect(result).toBeUndefined();
    });
  });

  describe('guardarSeleccion edge cases', () => {
    it('should remove storage key when actividad is null', () => {
      sessionStorage.setItem('selectedActivityInfo', 'old');
      (component as any).guardarSeleccion(null, null);
      expect(sessionStorage.getItem('selectedActivityInfo')).toBeNull();
    });

    it('should handle storage setItem error', () => {
      const originalSetItem = sessionStorage.setItem.bind(sessionStorage);
      spyOn(sessionStorage, 'setItem').and.throwError('quota exceeded');
      spyOn(sessionStorage, 'removeItem');
      (component as any).guardarSeleccion({ identificador: 'act-1' } as any, null);
      expect(sessionStorage.removeItem).toHaveBeenCalledWith('selectedActivityInfo');
    });
  });

  describe('cargarEjecuciones error handler', () => {
    it('should set error when consultarEjecucionesPaginado fails', () => {
      mockActivityService.consultarEjecucionesPaginado.and.returnValue(throwError(() => new Error('fail')));
      component.actividad = { identificador: 'act-1' } as any;
      fixture.detectChanges();
      expect(component.error).toBe('Error al cargar las fechas programadas.');
      expect(component.cargando).toBeFalse();
    });
  });

  describe('onPageChange', () => {
    it('should update page and reload ejecuciones', () => {
      component.actividad = { identificador: 'act-1' } as any;
      fixture.detectChanges();
      mockActivityService.consultarEjecucionesPaginado.calls.reset();
      component.onPageChange(2);
      expect(component.p).toBe(2);
      expect(mockActivityService.consultarEjecucionesPaginado).toHaveBeenCalledWith('act-1', 1);
    });

    it('should set totalElementos from response', () => {
      mockActivityService.consultarEjecucionesPaginado.and.returnValue(of({ content: mockEjecuciones, totalElements: 25 } as any));
      component.actividad = { identificador: 'act-1' } as any;
      fixture.detectChanges();
      expect(component.totalElementos).toBe(25);
    });
  });
});
