import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA, SimpleChange } from '@angular/core';
import { of, throwError } from 'rxjs';

import { CompletedActivitiesComponent } from './completed-activities.component';
import { ActivityService } from 'src/app/shared/service/activity.service';
import { DepartmentService } from 'src/app/shared/service/department.service';
import { AreaService } from 'src/app/shared/service/area.service';
import { SubAreaService } from 'src/app/shared/service/subarea.service';
import { FiltersRequestWithoutArea } from 'src/app/shared/model/filters.model';

describe('CompletedActivitiesComponent', () => {
  let component: CompletedActivitiesComponent;
  let fixture: ComponentFixture<CompletedActivitiesComponent>;
  let mockActivityService: jasmine.SpyObj<ActivityService>;
  let mockDepartmentService: jasmine.SpyObj<DepartmentService>;
  let mockAreaService: jasmine.SpyObj<AreaService>;
  let mockSubAreaService: jasmine.SpyObj<SubAreaService>;

  const mockFilters: FiltersRequestWithoutArea = {
    mes: 'Enero', anno: 2024, semestre: '2024-1',
    programaAcademico: '', tipoProgramaAcademico: '',
    centroCostos: '', tipoParticipante: '', indicador: ''
  };

  beforeEach(() => {
    mockActivityService = jasmine.createSpyObj('ActivityService', ['contarEjecucionesTotales']);
    mockActivityService.contarEjecucionesTotales.and.returnValue(of(42));

    mockDepartmentService = jasmine.createSpyObj('DepartmentService', ['consultarPorNombre']);
    mockDepartmentService.consultarPorNombre.and.returnValue(of({ identificador: 'dep1', nombre: 'Dir' }));

    mockAreaService = jasmine.createSpyObj('AreaService', ['consultarPorNombre']);
    mockAreaService.consultarPorNombre.and.returnValue(of({ identificador: 'a1', nombre: 'Area', tipoArea: 'AREA' }));

    mockSubAreaService = jasmine.createSpyObj('SubAreaService', ['consultarPorNombre']);
    mockSubAreaService.consultarPorNombre.and.returnValue(of({ identificador: 's1', nombre: 'Sub' }));

    TestBed.configureTestingModule({
      declarations: [CompletedActivitiesComponent],
      providers: [
        { provide: ActivityService, useValue: mockActivityService },
        { provide: DepartmentService, useValue: mockDepartmentService },
        { provide: AreaService, useValue: mockAreaService },
        { provide: SubAreaService, useValue: mockSubAreaService }
      ],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(CompletedActivitiesComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have default values', () => {
    expect(component.totalActividades).toBe(0);
    expect(component.tipoEstructura).toBe('DIRECCION');
  });

  describe('ngOnInit', () => {
    it('should not call service when filtersRequest is null', () => {
      component.filtersRequest = null;
      fixture.detectChanges();
      expect(component.totalActividades).toBe(0);
    });

    it('should load activities when all inputs are set', () => {
      component.filtersRequest = mockFilters;
      component.nombreArea = 'Dir';
      component.tipoEstructura = 'DIRECCION';
      fixture.detectChanges();
      expect(component.totalActividades).toBe(42);
    });
  });

  describe('ngOnChanges', () => {
    it('should reload when filtersRequest changes', () => {
      component.filtersRequest = mockFilters;
      component.nombreArea = 'Dir';
      component.ngOnChanges({
        filtersRequest: new SimpleChange(null, mockFilters, false)
      });
      expect(mockDepartmentService.consultarPorNombre).toHaveBeenCalled();
    });

    it('should reload when tipoEstructura changes', () => {
      component.filtersRequest = mockFilters;
      component.nombreArea = 'Dir';
      component.ngOnChanges({
        tipoEstructura: new SimpleChange('AREA', 'DIRECCION', false)
      });
      expect(mockDepartmentService.consultarPorNombre).toHaveBeenCalled();
    });
  });

  describe('loadCompletedActivities', () => {
    it('should set totalActividades to 0 when filters are null', () => {
      component.filtersRequest = null;
      (component as any).loadCompletedActivities();
      expect(component.totalActividades).toBe(0);
    });

    it('should set totalActividades to 0 when nombreArea is empty', () => {
      component.filtersRequest = mockFilters;
      component.nombreArea = '';
      (component as any).loadCompletedActivities();
      expect(component.totalActividades).toBe(0);
    });

    it('should load for DIRECCION', () => {
      component.filtersRequest = mockFilters;
      component.nombreArea = 'Dir';
      component.tipoEstructura = 'DIRECCION';
      (component as any).loadCompletedActivities();
      expect(mockDepartmentService.consultarPorNombre).toHaveBeenCalledWith('Dir');
      expect(component.totalActividades).toBe(42);
    });

    it('should load for AREA', () => {
      component.filtersRequest = mockFilters;
      component.nombreArea = 'Area Test';
      component.tipoEstructura = 'AREA';
      (component as any).loadCompletedActivities();
      expect(mockAreaService.consultarPorNombre).toHaveBeenCalledWith('Area Test');
      expect(component.totalActividades).toBe(42);
    });

    it('should load for SUBAREA', () => {
      component.filtersRequest = mockFilters;
      component.nombreArea = 'Sub Test';
      component.tipoEstructura = 'SUBAREA';
      (component as any).loadCompletedActivities();
      expect(mockSubAreaService.consultarPorNombre).toHaveBeenCalledWith('Sub Test');
      expect(component.totalActividades).toBe(42);
    });

    it('should handle error gracefully', () => {
      mockDepartmentService.consultarPorNombre.and.returnValue(throwError(() => new Error('fail')));
      component.filtersRequest = mockFilters;
      component.nombreArea = 'Dir';
      component.tipoEstructura = 'DIRECCION';
      (component as any).loadCompletedActivities();
      expect(component.totalActividades).toBe(0);
    });

    it('should set 0 when area identifier is null', () => {
      mockDepartmentService.consultarPorNombre.and.returnValue(of(null as any));
      component.filtersRequest = mockFilters;
      component.nombreArea = 'Dir';
      component.tipoEstructura = 'DIRECCION';
      (component as any).loadCompletedActivities();
      expect(component.totalActividades).toBe(0);
    });
  });

  describe('getAreaIdentifier', () => {
    it('should return null for empty nombreArea', (done) => {
      component.nombreArea = '';
      (component as any).getAreaIdentifier().subscribe((result: string | null) => {
        expect(result).toBeNull();
        done();
      });
    });

    it('should return null for unknown type', (done) => {
      component.tipoEstructura = 'UNKNOWN' as any;
      component.nombreArea = 'Test';
      (component as any).getAreaIdentifier().subscribe((result: string | null) => {
        expect(result).toBeNull();
        done();
      });
    });

    it('should handle department service error', (done) => {
      mockDepartmentService.consultarPorNombre.and.returnValue(throwError(() => new Error('fail')));
      component.tipoEstructura = 'DIRECCION';
      component.nombreArea = 'Test';
      (component as any).getAreaIdentifier().subscribe((result: string | null) => {
        expect(result).toBeNull();
        done();
      });
    });

    it('should handle area service error', (done) => {
      mockAreaService.consultarPorNombre.and.returnValue(throwError(() => new Error('fail')));
      component.tipoEstructura = 'AREA';
      component.nombreArea = 'Test';
      (component as any).getAreaIdentifier().subscribe((result: string | null) => {
        expect(result).toBeNull();
        done();
      });
    });

    it('should handle subarea service error', (done) => {
      mockSubAreaService.consultarPorNombre.and.returnValue(throwError(() => new Error('fail')));
      component.tipoEstructura = 'SUBAREA';
      component.nombreArea = 'Test';
      (component as any).getAreaIdentifier().subscribe((result: string | null) => {
        expect(result).toBeNull();
        done();
      });
    });
  });
});
