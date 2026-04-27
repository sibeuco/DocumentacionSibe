import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA, SimpleChange } from '@angular/core';
import { of, throwError } from 'rxjs';

import { AreaStatisticsComponent } from './area-statistics.component';
import { ActivityService } from 'src/app/shared/service/activity.service';
import { DepartmentService } from 'src/app/shared/service/department.service';
import { AreaService } from 'src/app/shared/service/area.service';
import { SubAreaService } from 'src/app/shared/service/subarea.service';

describe('AreaStatisticsComponent', () => {
  let component: AreaStatisticsComponent;
  let fixture: ComponentFixture<AreaStatisticsComponent>;
  let mockActivityService: jasmine.SpyObj<ActivityService>;
  let mockDepartmentService: jasmine.SpyObj<DepartmentService>;
  let mockAreaService: jasmine.SpyObj<AreaService>;
  let mockSubAreaService: jasmine.SpyObj<SubAreaService>;

  beforeEach(() => {
    mockActivityService = jasmine.createSpyObj('ActivityService', [
      'contarEjecucionesTotales', 'contarParticipantesTotales', 'contarAsistenciasTotales'
    ]);
    mockActivityService.contarEjecucionesTotales.and.returnValue(of(42));
    mockActivityService.contarParticipantesTotales.and.returnValue(of(150));
    mockActivityService.contarAsistenciasTotales.and.returnValue(of(300));

    mockDepartmentService = jasmine.createSpyObj('DepartmentService', ['consultarPorNombre']);
    mockDepartmentService.consultarPorNombre.and.returnValue(of({ identificador: 'dep1', nombre: 'Dir' }));

    mockAreaService = jasmine.createSpyObj('AreaService', ['consultarPorNombre']);
    mockAreaService.consultarPorNombre.and.returnValue(of({ identificador: 'a1', nombre: 'Area', tipoArea: 'AREA' }));

    mockSubAreaService = jasmine.createSpyObj('SubAreaService', ['consultarPorNombre']);
    mockSubAreaService.consultarPorNombre.and.returnValue(of({ identificador: 's1', nombre: 'Sub' }));

    TestBed.configureTestingModule({
      declarations: [AreaStatisticsComponent],
      providers: [
        { provide: ActivityService, useValue: mockActivityService },
        { provide: DepartmentService, useValue: mockDepartmentService },
        { provide: AreaService, useValue: mockAreaService },
        { provide: SubAreaService, useValue: mockSubAreaService }
      ],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(AreaStatisticsComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have default values', () => {
    expect(component.totalActividades).toBe(0);
    expect(component.totalParticipantes).toBe(0);
    expect(component.totalAsistencias).toBe(0);
    expect(component.tipoEstructura).toBe('AREA');
  });

  describe('ngOnInit', () => {
    it('should not call services when nombreArea is empty', () => {
      component.nombreArea = '';
      fixture.detectChanges();
      expect(component.totalActividades).toBe(0);
      expect(component.totalParticipantes).toBe(0);
      expect(component.totalAsistencias).toBe(0);
    });

    it('should load all statistics when inputs are set', () => {
      component.nombreArea = 'Bienestar';
      component.tipoEstructura = 'AREA';
      fixture.detectChanges();
      expect(mockAreaService.consultarPorNombre).toHaveBeenCalledWith('Bienestar');
      expect(component.totalActividades).toBe(42);
      expect(component.totalParticipantes).toBe(150);
      expect(component.totalAsistencias).toBe(300);
    });
  });

  describe('ngOnChanges', () => {
    it('should reload when tipoEstructura changes', () => {
      component.nombreArea = 'Dir';
      component.tipoEstructura = 'DIRECCION';
      component.ngOnChanges({ tipoEstructura: new SimpleChange('AREA', 'DIRECCION', false) });
      expect(mockDepartmentService.consultarPorNombre).toHaveBeenCalled();
    });

    it('should reload when nombreArea changes', () => {
      component.nombreArea = 'New Area';
      component.tipoEstructura = 'AREA';
      component.ngOnChanges({ nombreArea: new SimpleChange('Old', 'New Area', false) });
      expect(mockAreaService.consultarPorNombre).toHaveBeenCalledWith('New Area');
    });
  });

  describe('loadTotalActivities', () => {
    it('should set 0 when nombreArea is empty', () => {
      component.nombreArea = '';
      (component as any).loadTotalActivities();
      expect(component.totalActividades).toBe(0);
    });

    it('should load for DIRECCION', () => {
      component.nombreArea = 'Dir';
      component.tipoEstructura = 'DIRECCION';
      (component as any).loadTotalActivities();
      expect(component.totalActividades).toBe(42);
    });

    it('should load for SUBAREA', () => {
      component.nombreArea = 'Sub';
      component.tipoEstructura = 'SUBAREA';
      (component as any).loadTotalActivities();
      expect(mockSubAreaService.consultarPorNombre).toHaveBeenCalledWith('Sub');
      expect(component.totalActividades).toBe(42);
    });

    it('should handle error gracefully', () => {
      mockAreaService.consultarPorNombre.and.returnValue(throwError(() => new Error('fail')));
      component.nombreArea = 'Area';
      component.tipoEstructura = 'AREA';
      (component as any).loadTotalActivities();
      expect(component.totalActividades).toBe(0);
    });

    it('should set 0 when area not found', () => {
      mockAreaService.consultarPorNombre.and.returnValue(of(null as any));
      component.nombreArea = 'Unknown';
      component.tipoEstructura = 'AREA';
      (component as any).loadTotalActivities();
      expect(component.totalActividades).toBe(0);
    });
  });

  describe('loadTotalParticipants', () => {
    it('should set 0 when nombreArea is empty', () => {
      component.nombreArea = '';
      (component as any).loadTotalParticipants();
      expect(component.totalParticipantes).toBe(0);
    });

    it('should load for AREA', () => {
      component.nombreArea = 'Area';
      component.tipoEstructura = 'AREA';
      (component as any).loadTotalParticipants();
      expect(component.totalParticipantes).toBe(150);
    });

    it('should handle error gracefully', () => {
      mockAreaService.consultarPorNombre.and.returnValue(throwError(() => new Error('fail')));
      component.nombreArea = 'Area';
      component.tipoEstructura = 'AREA';
      (component as any).loadTotalParticipants();
      expect(component.totalParticipantes).toBe(0);
    });
  });

  describe('loadTotalAsistencias', () => {
    it('should set 0 when nombreArea is empty', () => {
      component.nombreArea = '';
      (component as any).loadTotalAsistencias();
      expect(component.totalAsistencias).toBe(0);
    });

    it('should load for AREA', () => {
      component.nombreArea = 'Area';
      component.tipoEstructura = 'AREA';
      (component as any).loadTotalAsistencias();
      expect(component.totalAsistencias).toBe(300);
    });

    it('should handle error gracefully', () => {
      mockAreaService.consultarPorNombre.and.returnValue(throwError(() => new Error('fail')));
      component.nombreArea = 'Area';
      component.tipoEstructura = 'AREA';
      (component as any).loadTotalAsistencias();
      expect(component.totalAsistencias).toBe(0);
    });
  });

  describe('getAreaIdentifier', () => {
    it('should return null for empty nombreArea', (done) => {
      component.nombreArea = '';
      (component as any).getAreaIdentifier().subscribe((r: any) => { expect(r).toBeNull(); done(); });
    });

    it('should use departmentService for DIRECCION', (done) => {
      component.tipoEstructura = 'DIRECCION';
      component.nombreArea = 'Test';
      (component as any).getAreaIdentifier().subscribe((r: any) => { expect(r).toBe('dep1'); done(); });
    });

    it('should use areaService for AREA', (done) => {
      component.tipoEstructura = 'AREA';
      component.nombreArea = 'Test';
      (component as any).getAreaIdentifier().subscribe((r: any) => { expect(r).toBe('a1'); done(); });
    });

    it('should use subAreaService for SUBAREA', (done) => {
      component.tipoEstructura = 'SUBAREA';
      component.nombreArea = 'Test';
      (component as any).getAreaIdentifier().subscribe((r: any) => { expect(r).toBe('s1'); done(); });
    });

    it('should return null for unknown type', (done) => {
      component.tipoEstructura = 'UNKNOWN' as any;
      component.nombreArea = 'Test';
      (component as any).getAreaIdentifier().subscribe((r: any) => { expect(r).toBeNull(); done(); });
    });

    it('should handle department service error', (done) => {
      mockDepartmentService.consultarPorNombre.and.returnValue(throwError(() => new Error('fail')));
      component.tipoEstructura = 'DIRECCION';
      component.nombreArea = 'Test';
      (component as any).getAreaIdentifier().subscribe((r: any) => { expect(r).toBeNull(); done(); });
    });

    it('should handle area service error', (done) => {
      mockAreaService.consultarPorNombre.and.returnValue(throwError(() => new Error('fail')));
      component.tipoEstructura = 'AREA';
      component.nombreArea = 'Test';
      (component as any).getAreaIdentifier().subscribe((r: any) => { expect(r).toBeNull(); done(); });
    });

    it('should handle subarea service error', (done) => {
      mockSubAreaService.consultarPorNombre.and.returnValue(throwError(() => new Error('fail')));
      component.tipoEstructura = 'SUBAREA';
      component.nombreArea = 'Test';
      (component as any).getAreaIdentifier().subscribe((r: any) => { expect(r).toBeNull(); done(); });
    });
  });

  describe('loadTotalParticipants with null idArea', () => {
    it('should set 0 when area not found', () => {
      mockDepartmentService.consultarPorNombre.and.returnValue(of(null as any));
      component.nombreArea = 'Dir';
      component.tipoEstructura = 'DIRECCION';
      (component as any).loadTotalParticipants();
      expect(component.totalParticipantes).toBe(0);
    });
  });

  describe('loadTotalAsistencias with null idArea', () => {
    it('should set 0 when area not found', () => {
      mockDepartmentService.consultarPorNombre.and.returnValue(of(null as any));
      component.nombreArea = 'Dir';
      component.tipoEstructura = 'DIRECCION';
      (component as any).loadTotalAsistencias();
      expect(component.totalAsistencias).toBe(0);
    });
  });

  describe('catchError handlers', () => {
    it('should handle contarEjecucionesTotales error', () => {
      mockActivityService.contarEjecucionesTotales.and.returnValue(throwError(() => new Error('fail')));
      component.nombreArea = 'Dir';
      component.tipoEstructura = 'DIRECCION';
      (component as any).loadTotalActivities();
      expect(component.totalActividades).toBe(0);
    });

    it('should handle contarParticipantesTotales error', () => {
      mockActivityService.contarParticipantesTotales.and.returnValue(throwError(() => new Error('fail')));
      component.nombreArea = 'Dir';
      component.tipoEstructura = 'DIRECCION';
      (component as any).loadTotalParticipants();
      expect(component.totalParticipantes).toBe(0);
    });

    it('should handle contarAsistenciasTotales error', () => {
      mockActivityService.contarAsistenciasTotales.and.returnValue(throwError(() => new Error('fail')));
      component.nombreArea = 'Dir';
      component.tipoEstructura = 'DIRECCION';
      (component as any).loadTotalAsistencias();
      expect(component.totalAsistencias).toBe(0);
    });
  });
});
