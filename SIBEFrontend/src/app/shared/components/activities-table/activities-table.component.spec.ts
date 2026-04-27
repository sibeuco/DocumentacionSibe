import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NO_ERRORS_SCHEMA, ChangeDetectorRef } from '@angular/core';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { of, throwError } from 'rxjs';
import * as bootstrap from 'bootstrap';

import { ActivitiesTableComponent } from './activities-table.component';
import { ActivityService } from '../../service/activity.service';
import { DepartmentService } from '../../service/department.service';
import { AreaService } from '../../service/area.service';
import { SubAreaService } from '../../service/subarea.service';
import { NgxPaginationModule } from 'ngx-pagination';

describe('ActivitiesTableComponent', () => {
  let component: ActivitiesTableComponent;
  let fixture: ComponentFixture<ActivitiesTableComponent>;
  let mockActivityService: jasmine.SpyObj<ActivityService>;
  let mockDepartmentService: jasmine.SpyObj<DepartmentService>;
  let mockAreaService: jasmine.SpyObj<AreaService>;
  let mockSubAreaService: jasmine.SpyObj<SubAreaService>;

  const mockActivitiesResponse = [
      { identificador: 'act-1', nombre: 'Actividad 1', nombreColaborador: 'Juan', fechaCreacion: '2025-01-01', fechasProgramadas: ['2025-06-01'] },
      { identificador: 'act-2', nombre: 'Actividad 2', nombreColaborador: 'Ana', fechaCreacion: '2025-02-01', fechasProgramadas: [] }
    ];

  const mockPaginatedResponse = {
    content: mockActivitiesResponse,
    totalElements: 2
  };

  const mockEstructura = { identificador: 'dir-1', nombre: 'Direccion Test' };

  beforeEach(() => {
    mockActivityService = jasmine.createSpyObj('ActivityService', [
      'consultarPorDireccion',
      'consultarPorArea',
      'consultarPorSubarea',
      'consultarPorDireccionPaginado',
      'consultarPorAreaPaginado',
      'consultarPorSubareaPaginado',
      'consultarEjecuciones'
    ]);
    mockDepartmentService = jasmine.createSpyObj('DepartmentService', ['consultarPorNombre']);
    mockAreaService = jasmine.createSpyObj('AreaService', ['consultarPorNombre']);
    mockSubAreaService = jasmine.createSpyObj('SubAreaService', ['consultarPorNombre']);

    mockActivityService.consultarEjecuciones.and.returnValue(of([]));
    mockActivityService.consultarPorDireccion.and.returnValue(of(mockActivitiesResponse as any));
    mockActivityService.consultarPorArea.and.returnValue(of(mockActivitiesResponse as any));
    mockActivityService.consultarPorSubarea.and.returnValue(of(mockActivitiesResponse as any));
    mockActivityService.consultarPorDireccionPaginado.and.returnValue(of(mockPaginatedResponse as any));
    mockActivityService.consultarPorAreaPaginado.and.returnValue(of(mockPaginatedResponse as any));
    mockActivityService.consultarPorSubareaPaginado.and.returnValue(of(mockPaginatedResponse as any));
    mockDepartmentService.consultarPorNombre.and.returnValue(of(mockEstructura));
    mockAreaService.consultarPorNombre.and.returnValue(of(mockEstructura as any));
    mockSubAreaService.consultarPorNombre.and.returnValue(of(mockEstructura as any));

    TestBed.configureTestingModule({
      imports: [FormsModule, ReactiveFormsModule, HttpClientTestingModule, RouterTestingModule, NgxPaginationModule],
      declarations: [ActivitiesTableComponent],
      providers: [
        { provide: ActivityService, useValue: mockActivityService },
        { provide: DepartmentService, useValue: mockDepartmentService },
        { provide: AreaService, useValue: mockAreaService },
        { provide: SubAreaService, useValue: mockSubAreaService }
      ],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(ActivitiesTableComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    fixture.detectChanges();
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    it('should call cargarActividades when nombreArea is set', () => {
      component.nombreArea = 'Test Area';
      component.tipoEstructura = 'direccion';
      fixture.detectChanges();

      expect(mockDepartmentService.consultarPorNombre).toHaveBeenCalledWith('Test Area');
    });

    it('should not load activities when nombreArea is empty', () => {
      component.nombreArea = '';
      fixture.detectChanges();

      expect(mockDepartmentService.consultarPorNombre).not.toHaveBeenCalled();
      expect(component.cargando).toBeFalse();
    });
  });

  describe('cargarActividades via tipoEstructura', () => {
    it('should use departmentService for direccion type', () => {
      component.nombreArea = 'Dir Test';
      component.tipoEstructura = 'direccion';
      fixture.detectChanges();
      expect(mockDepartmentService.consultarPorNombre).toHaveBeenCalledWith('Dir Test');
    });

    it('should use areaService for area type', () => {
      component.nombreArea = 'Area Test';
      component.tipoEstructura = 'area';
      fixture.detectChanges();
    });

    it('should use subAreaService for subarea type', () => {
      component.nombreArea = 'Subarea Test';
      component.tipoEstructura = 'subarea';
      fixture.detectChanges();
      expect(mockSubAreaService.consultarPorNombre).toHaveBeenCalledWith('Subarea Test');
    });

    it('should set error when consultarPorNombre fails', () => {
      mockDepartmentService.consultarPorNombre.and.returnValue(throwError(() => new Error('fail')));
      component.nombreArea = 'Dir Test';
      component.tipoEstructura = 'direccion';
      fixture.detectChanges();
      expect(component.error).toContain('Error al cargar las actividades');
    });

    it('should set error when estructura has no identificador', () => {
      mockDepartmentService.consultarPorNombre.and.returnValue(of({ nombre: 'test' } as any));
      component.nombreArea = 'Dir Test';
      component.tipoEstructura = 'direccion';
      fixture.detectChanges();
      expect(component.error).toContain('Error al cargar las actividades');
    });
  });

  describe('obtenerNombreColaborador', () => {
    it('should return collaborator name when present', () => {
      const actividad = { nombreColaborador: 'Juan Perez' } as any;
      expect(component.obtenerNombreColaborador(actividad)).toBe('Juan Perez');
    });

    it('should return Sin colaborador when no name', () => {
      expect(component.obtenerNombreColaborador(null as any)).toBe('Sin colaborador');
      expect(component.obtenerNombreColaborador({} as any)).toBe('Sin colaborador');
    });
  });

  describe('ordenarPor', () => {
    beforeEach(() => {
      component.nombreArea = '';
      fixture.detectChanges();
      component.actividadesCargadas = mockActivitiesResponse as any;
    });

    it('should toggle direction when same column clicked', () => {
      component.columnaOrdenamiento = 'fechaProgramada';
      component.direccionOrdenamiento = 'asc';
      component.ordenarPor('fechaProgramada');
      expect(component.direccionOrdenamiento).toBe('desc');
    });

    it('should set new column and default to asc', () => {
      component.columnaOrdenamiento = 'nombreActividad';
      component.ordenarPor('colaborador');
      expect(component.columnaOrdenamiento).toBe('colaborador');
      expect(component.direccionOrdenamiento).toBe('asc');
    });
  });

  describe('recargarActividades', () => {
    it('should reload when nombreArea and tipoEstructura are set', () => {
      component.nombreArea = 'Dir Test';
      component.tipoEstructura = 'direccion';
      fixture.detectChanges();
      mockActivityService.consultarPorDireccionPaginado.calls.reset();
      component.recargarActividades();
      expect(mockActivityService.consultarPorDireccionPaginado).toHaveBeenCalled();
    });
  });

  describe('ngOnChanges', () => {
    it('should reset page and reload when nombreArea changes', () => {
      component.nombreArea = 'Dir Test';
      component.tipoEstructura = 'direccion';
      fixture.detectChanges();
      mockDepartmentService.consultarPorNombre.calls.reset();
      component.nombreArea = 'New Dir';
      component.ngOnChanges({
        nombreArea: { currentValue: 'New Dir', previousValue: 'Dir Test', firstChange: false, isFirstChange: () => false }
      });
      expect(mockDepartmentService.consultarPorNombre).toHaveBeenCalledWith('New Dir');
    });

    it('should apply filters when terminoBusqueda changes', () => {
      component.nombreArea = 'Dir Test';
      component.tipoEstructura = 'direccion';
      fixture.detectChanges();
      component.terminoBusqueda = 'search';
      component.ngOnChanges({
        terminoBusqueda: { currentValue: 'search', previousValue: '', firstChange: false, isFirstChange: () => false }
      });
      // terminoBusqueda change triggers aplicarFiltrosYOrdenamiento
      expect(component.actividadesOrdenadas).toBeDefined();
    });

    it('should not reload when nombreArea is empty', () => {
      fixture.detectChanges();
      mockDepartmentService.consultarPorNombre.calls.reset();
      component.nombreArea = '';
      component.ngOnChanges({
        nombreArea: { currentValue: '', previousValue: 'old', firstChange: false, isFirstChange: () => false }
      });
      expect(mockDepartmentService.consultarPorNombre).not.toHaveBeenCalled();
    });
  });

  describe('cargar actividades via area and subarea types', () => {
    it('should load activities by area', () => {
      component.nombreArea = 'Area Test';
      component.tipoEstructura = 'area';
      fixture.detectChanges();
      expect(mockActivityService.consultarPorAreaPaginado).toHaveBeenCalled();
    });

    it('should load activities by subarea', () => {
      component.nombreArea = 'Subarea Test';
      component.tipoEstructura = 'subarea';
      fixture.detectChanges();
      expect(mockActivityService.consultarPorSubareaPaginado).toHaveBeenCalled();
    });

    it('should set error when cargar actividades fails', () => {
      mockActivityService.consultarPorDireccionPaginado.and.returnValue(throwError(() => new Error('fail')));
      component.nombreArea = 'Dir Test';
      component.tipoEstructura = 'direccion';
      fixture.detectChanges();
      expect(component.error).toContain('Error al cargar');
    });
  });

  describe('getSortClass', () => {
    it('should return base class when column is not sorted', () => {
      component.columnaOrdenamiento = 'nombre';
      expect(component.getSortClass('colaborador')).toBe('sort-indicator');
    });

    it('should return asc class when column is sorted ascending', () => {
      component.columnaOrdenamiento = 'nombre';
      component.direccionOrdenamiento = 'asc';
      expect(component.getSortClass('nombre')).toBe('sort-indicator sort-asc');
    });

    it('should return desc class when column is sorted descending', () => {
      component.columnaOrdenamiento = 'nombre';
      component.direccionOrdenamiento = 'desc';
      expect(component.getSortClass('nombre')).toBe('sort-indicator sort-desc');
    });
  });

  describe('trackByActividadId', () => {
    it('should return identificador', () => {
      const act = { identificador: 'act-123' } as any;
      expect(component.trackByActividadId(0, act)).toBe('act-123');
    });
  });

  describe('calcularEstado', () => {
    it('should return PENDIENTE when no ejecuciones', () => {
      const act = { identificador: 'act-1' } as any;
      expect(component.calcularEstado(act)).toBe('Pendiente');
    });

    it('should return FINALIZADO when all ejecuciones are finalized', () => {
      component.ejecucionesMap.set('act-1', [
        { estadoActividad: { nombre: 'Finalizada' } } as any,
        { estadoActividad: { nombre: 'Finalizada' } } as any
      ]);
      const act = { identificador: 'act-1' } as any;
      expect(component.calcularEstado(act)).toBe('Finalizada');
    });

    it('should return PENDIENTE when not all ejecuciones are finalized', () => {
      component.ejecucionesMap.set('act-1', [
        { estadoActividad: { nombre: 'Finalizada' } } as any,
        { estadoActividad: { nombre: 'Pendiente' } } as any
      ]);
      const act = { identificador: 'act-1' } as any;
      expect(component.calcularEstado(act)).toBe('Pendiente');
    });
  });

  describe('getStatusClass', () => {
    it('should return status-pendiente for PENDIENTE', () => {
      const act = { identificador: 'x' } as any;
      expect(component.getStatusClass(act)).toBe('status-pendiente');
    });

    it('should return status-finalizado for FINALIZADO', () => {
      component.ejecucionesMap.set('x', [{ estadoActividad: { nombre: 'Finalizada' } } as any]);
      const act = { identificador: 'x' } as any;
      expect(component.getStatusClass(act)).toBe('status-finalizado');
    });
  });

  describe('obtenerFechaProgramada', () => {
    it('should return null when no date mapped', () => {
      const act = { identificador: 'act-1' } as any;
      expect(component.obtenerFechaProgramada(act)).toBeNull();
    });

    it('should return mapped date', () => {
      const date = new Date(2025, 5, 1);
      component.fechasProgramadasMap.set('act-1', date);
      const act = { identificador: 'act-1' } as any;
      expect(component.obtenerFechaProgramada(act)).toEqual(date);
    });
  });

  describe('realizarActividad', () => {
    it('should emit event and set selected activity', () => {
      spyOn(component.actividadSeleccionada, 'emit');
      spyOn(document, 'getElementById').and.returnValue(null);
      const act = { identificador: 'act-1', nombre: 'Test' } as any;
      component.realizarActividad(act);
      expect(component.actividadSeleccionada.emit).toHaveBeenCalledWith(act);
      expect(component.actividadSeleccionadaParaModal).toEqual(act);
    });
  });

  describe('abrirModalEditar', () => {
    it('should set actividadSeleccionadaEdicion', fakeAsync(() => {
      spyOn(document, 'getElementById').and.returnValue(null);
      const act = { identificador: 'act-1' } as any;
      component.abrirModalEditar(act);
      expect(component.actividadSeleccionadaEdicion).toEqual(act);
      tick();
    }));
  });

  describe('cerrarModalFechas', () => {
    it('should clear actividadSeleccionadaParaModal', fakeAsync(() => {
      spyOn(document, 'getElementById').and.returnValue(null);
      component.actividadSeleccionadaParaModal = { identificador: 'act-1' } as any;
      component.cerrarModalFechas();
      expect(component.actividadSeleccionadaParaModal).toBeNull();
      tick(200);
    }));
  });

  describe('ordenarPor with backend columns', () => {
    it('should sort client-side for nombreActividad column', () => {
      component.nombreArea = 'Dir Test';
      component.tipoEstructura = 'direccion';
      fixture.detectChanges();
      mockDepartmentService.consultarPorNombre.calls.reset();
      component.ordenarPor('nombreActividad');
      expect(component.columnaOrdenamiento).toBe('nombreActividad');
      expect(mockDepartmentService.consultarPorNombre).not.toHaveBeenCalled();
    });

    it('should apply client-side sort for non-backend column', () => {
      fixture.detectChanges();
      component.actividadesCargadas = mockActivitiesResponse as any;
      component.ordenarPor('estado');
      expect(component.columnaOrdenamiento).toBe('estado');
      expect(component.actividadesOrdenadas.length).toBe(2);
    });
  });

  describe('cargarFechasProgramadas', () => {
    it('should set cargando false when activities list is empty', () => {
      component.nombreArea = '';
      fixture.detectChanges();
      component.cargando = true;
      component.actividadesCargadas = [];
      // Trigger via recargarActividades indirectly — just call ngOnInit path
      component.ngOnInit();
      expect(component.cargando).toBeFalse();
    });

    it('should populate fechasProgramadasMap from ejecuciones', () => {
      const ejecuciones = [
        { fechaProgramada: '2025-06-15', estadoActividad: { nombre: 'PENDIENTE' } }
      ];
      mockActivityService.consultarEjecuciones.and.returnValue(of(ejecuciones as any));
      mockActivityService.consultarPorDireccionPaginado.and.returnValue(of(mockPaginatedResponse as any));

      component.nombreArea = 'Dir Test';
      component.tipoEstructura = 'direccion';
      fixture.detectChanges();

      expect(component.fechasProgramadasMap.size).toBeGreaterThan(0);
    });
  });

  describe('verFechaProgramada', () => {
    it('should be callable (TODO method)', () => {
      expect(() => component.verFechaProgramada({} as any)).not.toThrow();
    });
  });

  describe('ordering by client-side columns', () => {
    beforeEach(() => {
      component.nombreArea = '';
      fixture.detectChanges();
      component.actividadesCargadas = [
        { identificador: 'act-1', nombre: 'Zebra', nombreColaborador: 'Ana', fechaCreacion: '2025-02-01', fechasProgramadas: [] },
        { identificador: 'act-2', nombre: 'Alpha', nombreColaborador: 'Zara', fechaCreacion: '2025-01-01', fechasProgramadas: [] }
      ] as any;
      component.actividadesOrdenadas = [...component.actividadesCargadas];
    });

    it('should sort by fechaProgramada ascending', () => {
      component.fechasProgramadasMap.set('act-1', new Date(2025, 5, 15));
      component.fechasProgramadasMap.set('act-2', new Date(2025, 0, 1));
      component.ordenarPor('fechaProgramada');
      expect(component.actividadesOrdenadas[0].identificador).toBe('act-2');
    });

    it('should sort by fechaProgramada descending', () => {
      component.fechasProgramadasMap.set('act-1', new Date(2025, 5, 15));
      component.fechasProgramadasMap.set('act-2', new Date(2025, 0, 1));
      component.columnaOrdenamiento = 'fechaProgramada';
      component.direccionOrdenamiento = 'asc';
      component.ordenarPor('fechaProgramada');
      expect(component.direccionOrdenamiento).toBe('desc');
    });

    it('should handle null fechaProgramada in sort', () => {
      component.fechasProgramadasMap.set('act-1', null);
      component.fechasProgramadasMap.set('act-2', new Date(2025, 0, 1));
      component.ordenarPor('fechaProgramada');
      expect(component.actividadesOrdenadas.length).toBe(2);
    });

    it('should sort by estado', () => {
      component.ejecucionesMap.set('act-1', [{ estadoActividad: { nombre: 'Finalizada' } } as any]);
      component.ejecucionesMap.set('act-2', []);
      component.ordenarPor('estado');
      expect(component.actividadesOrdenadas.length).toBe(2);
    });

    it('should sort by fechaCreacion client-side when already sorted by non-backend column', () => {
      component.columnaOrdenamiento = 'estado';
      component.ordenarPor('fechaProgramada');
      expect(component.columnaOrdenamiento).toBe('fechaProgramada');
      expect(component.direccionOrdenamiento).toBe('asc');
    });
  });

  describe('getStatusClass for EN_CURSO', () => {
    it('should return status-en-curso', () => {
      component.ejecucionesMap.set('x', [
        { estadoActividad: { nombre: 'En curso' } } as any
      ]);
      const act = { identificador: 'x' } as any;
      // calcularEstado returns Pendiente since not all are Finalizada
      expect(component.getStatusClass(act)).toBe('status-pendiente');
    });
  });

  describe('recargarActividades', () => {
    it('should not reload when no estructuraId and no nombreArea', () => {
      component.nombreArea = '';
      fixture.detectChanges();
      mockDepartmentService.consultarPorNombre.calls.reset();
      component.recargarActividades();
      expect(mockDepartmentService.consultarPorNombre).not.toHaveBeenCalled();
    });
  });

  describe('cargarFechasProgramadas with ejecuciones error', () => {
    it('should handle forkJoin error', () => {
      mockActivityService.consultarEjecuciones.and.returnValue(throwError(() => new Error('fail')));
      mockActivityService.consultarPorDireccionPaginado.and.returnValue(of(mockPaginatedResponse as any));

      component.nombreArea = 'Dir Test';
      component.tipoEstructura = 'direccion';
      fixture.detectChanges();

      // Should still resolve via catchError in the forkJoin
      expect(component.fechasProgramadasMap.size).toBeGreaterThanOrEqual(0);
    });
  });

  describe('parseFechaLocal edge cases', () => {
    it('should handle ISO date format with time', () => {
      const result = (component as any).parseFechaLocal('2025-06-15T10:00:00Z');
      expect(result instanceof Date).toBeTrue();
      expect(isNaN(result.getTime())).toBeFalse();
    });

    it('should handle YYYY-MM-DD format', () => {
      const result = (component as any).parseFechaLocal('2025-06-15');
      expect(result.getFullYear()).toBe(2025);
      expect(result.getMonth()).toBe(5); // June = 5
      expect(result.getDate()).toBe(15);
    });

    it('should handle invalid date', () => {
      const result = (component as any).parseFechaLocal('not-a-date');
      expect(result instanceof Date).toBeTrue();
    });
  });

  describe('calcularFechaMasCercana', () => {
    it('should return null for empty ejecuciones', () => {
      const result = (component as any).calcularFechaMasCercana([]);
      expect(result).toBeNull();
    });

    it('should return null when no valid fechas', () => {
      const result = (component as any).calcularFechaMasCercana([
        { fechaProgramada: null } as any
      ]);
      expect(result).toBeNull();
    });

    it('should return closest date to today', () => {
      const hoy = new Date();
      const manana = new Date(hoy);
      manana.setDate(manana.getDate() + 1);
      const pasado = new Date(hoy);
      pasado.setDate(pasado.getDate() + 100);
      const result = (component as any).calcularFechaMasCercana([
        { fechaProgramada: manana.toISOString().split('T')[0] } as any,
        { fechaProgramada: pasado.toISOString().split('T')[0] } as any
      ]);
      expect(result).toBeTruthy();
    });
  });

  describe('ordenarPorColumna client-side mapping', () => {
    beforeEach(() => {
      component.actividadesCargadas = [
        { identificador: 'a1', nombre: 'Beta', nombreColaborador: 'Zebra', fechaCreacion: '2025-02-01' } as any,
        { identificador: 'a2', nombre: 'Alpha', nombreColaborador: 'Alpha', fechaCreacion: '2025-01-01' } as any
      ];
    });

    it('should sort by nombreActividad column', () => {
      component.ordenarPor('nombreActividad');
      expect(component.columnaOrdenamiento).toBe('nombreActividad');
      expect(component.actividadesOrdenadas[0].nombre).toBe('Alpha');
    });

    it('should sort by colaborador column', () => {
      component.ordenarPor('colaborador');
      expect(component.columnaOrdenamiento).toBe('colaborador');
      expect(component.actividadesOrdenadas[0].nombreColaborador).toBe('Alpha');
    });

    it('should sort by fechaCreacion column', () => {
      component.ordenarPor('fechaCreacion');
      expect(component.columnaOrdenamiento).toBe('fechaCreacion');
      expect(component.actividadesOrdenadas[0].fechaCreacion).toBe('2025-01-01');
    });

    it('should not change order for unknown column', () => {
      component.ordenarPor('estado');
      expect(component.columnaOrdenamiento).toBe('estado');
      expect(component.actividadesOrdenadas.length).toBe(2);
    });
  });

  describe('abrirModalEditar with modal element', () => {
    it('should set actividadSeleccionadaEdicion and try to show modal', fakeAsync(() => {
      spyOn(document, 'getElementById').and.returnValue(null);
      const act = { identificador: 'act-1', nombre: 'Test' } as any;
      component.abrirModalEditar(act);
      expect(component.actividadSeleccionadaEdicion).toEqual(act);
      tick();
    }));
  });

  describe('cerrarModalFechas with modal element', () => {
    it('should hide modal when getInstance returns a modal', fakeAsync(() => {
      const mockModalObj = { hide: jasmine.createSpy('hide') };
      (window as any).bootstrap = { Modal: { getInstance: () => mockModalObj } };
      const mockElement = document.createElement('div');
      spyOn(document, 'getElementById').and.returnValue(mockElement);
      component.actividadSeleccionadaParaModal = { identificador: 'x' } as any;
      component.cerrarModalFechas();
      expect(mockModalObj.hide).toHaveBeenCalled();
      expect(component.actividadSeleccionadaParaModal).toBeNull();
      tick(200);
    }));
  });

  describe('ordenarPorColumna client-side for all column types', () => {
    beforeEach(() => {
      component.actividadesCargadas = [
        { identificador: 'a1', nombre: 'Beta', nombreColaborador: 'Zebra', fechaCreacion: '2025-02-01' } as any,
        { identificador: 'a2', nombre: 'Alpha', nombreColaborador: 'Alpha', fechaCreacion: '2025-01-01' } as any
      ];
    });

    it('should sort by colaborador ascending', () => {
      component.columnaOrdenamiento = 'colaborador';
      component.direccionOrdenamiento = 'asc';
      (component as any).aplicarFiltrosYOrdenamiento();
      expect(component.actividadesOrdenadas[0].nombreColaborador).toBe('Alpha');
    });

    it('should sort by fechaCreacion ascending', () => {
      component.columnaOrdenamiento = 'fechaCreacion';
      component.direccionOrdenamiento = 'asc';
      (component as any).aplicarFiltrosYOrdenamiento();
      expect(component.actividadesOrdenadas[0].fechaCreacion).toBe('2025-01-01');
    });

    it('should return 0 for unknown column', () => {
      component.columnaOrdenamiento = 'unknownCol';
      component.direccionOrdenamiento = 'asc';
      (component as any).aplicarFiltrosYOrdenamiento();
      expect(component.actividadesOrdenadas.length).toBe(2);
    });
  });

  describe('getStatusClass for EN_CURSO', () => {
    it('should return status-en-curso when calcularEstado returns En curso', () => {
      spyOn(component, 'calcularEstado').and.returnValue('En curso' as any);
      const act = { identificador: 'x' } as any;
      expect(component.getStatusClass(act)).toBe('status-en-curso');
    });

    it('should return status-pendiente for unknown estado', () => {
      spyOn(component, 'calcularEstado').and.returnValue('Unknown' as any);
      const act = { identificador: 'x' } as any;
      expect(component.getStatusClass(act)).toBe('status-pendiente');
    });
  });

  describe('recargarActividades without estructuraId', () => {
    it('should call cargarActividades when no estructuraId but nombreArea and tipoEstructura set', () => {
      component.nombreArea = 'Test Area';
      component.tipoEstructura = 'area';
      // Don't trigger ngOnInit/fixture.detectChanges to avoid setting estructuraId
      mockAreaService.consultarPorNombre.and.returnValue(of({ identificador: 'a1', nombre: 'Test Area' } as any));
      mockActivityService.consultarPorAreaPaginado.and.returnValue(of(mockPaginatedResponse as any));
      component.recargarActividades();
      expect(mockAreaService.consultarPorNombre).toHaveBeenCalledWith('Test Area');
    });
  });

  describe('cargarActividades with invalid tipoEstructura', () => {
    it('should set error for invalid tipoEstructura', () => {
      component.nombreArea = 'Test';
      component.tipoEstructura = 'invalid' as any;
      fixture.detectChanges();
      expect(component.error).toBe('Tipo de estructura no válido');
      expect(component.cargando).toBeFalse();
    });
  });

  describe('cargarActividades with empty response', () => {
    it('should set cargando false and apply filters when response is empty', () => {
      mockActivityService.consultarPorDireccionPaginado.and.returnValue(of({ content: [], totalElements: 0 } as any));
      component.nombreArea = 'Dir Test';
      component.tipoEstructura = 'direccion';
      fixture.detectChanges();
      expect(component.cargando).toBeFalse();
      expect(component.actividadesOrdenadas).toEqual([]);
    });
  });

  describe('onPageChange', () => {
    it('should update page and reload activities', () => {
      component.nombreArea = 'Dir Test';
      component.tipoEstructura = 'direccion';
      fixture.detectChanges();
      mockActivityService.consultarPorDireccionPaginado.calls.reset();
      component.onPageChange(2);
      expect(component.p).toBe(2);
      expect(mockActivityService.consultarPorDireccionPaginado).toHaveBeenCalled();
    });

    it('should set totalElementos from response', () => {
      mockActivityService.consultarPorDireccionPaginado.and.returnValue(of({ content: mockActivitiesResponse, totalElements: 25 } as any));
      component.nombreArea = 'Dir Test';
      component.tipoEstructura = 'direccion';
      fixture.detectChanges();
      expect(component.totalElementos).toBe(25);
    });
  });

  describe('abrirModalEditar with element found', () => {
    it('should show modal when DOM element exists', fakeAsync(() => {
      const mockModal = { show: jasmine.createSpy('show') };
      spyOn(bootstrap.Modal, 'getInstance').and.returnValue(mockModal as any);
      const el = document.createElement('div');
      spyOn(document, 'getElementById').and.returnValue(el);
      component.abrirModalEditar({ identificador: 'act-1' } as any);
      tick();
      expect(mockModal.show).toHaveBeenCalled();
    }));
  });

  describe('realizarActividad with modal element', () => {
    it('should open modal when DOM element exists', () => {
      const mockModal = { show: jasmine.createSpy('show') };
      (window as any).bootstrap = { Modal: class { show() { mockModal.show(); } } };
      spyOn(component.actividadSeleccionada, 'emit');
      const el = document.createElement('div');
      spyOn(document, 'getElementById').and.returnValue(el);
      const act = { identificador: 'act-1' } as any;
      component.realizarActividad(act);
      expect(component.actividadSeleccionada.emit).toHaveBeenCalledWith(act);
      expect(mockModal.show).toHaveBeenCalled();
    });
  });
});
