import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA, SimpleChange } from '@angular/core';
import { of, throwError } from 'rxjs';

import { TotalParticipantsComponent } from './total-participants.component';
import { ActivityService } from 'src/app/shared/service/activity.service';
import { DepartmentService } from 'src/app/shared/service/department.service';
import { AreaService } from 'src/app/shared/service/area.service';
import { SubAreaService } from 'src/app/shared/service/subarea.service';
import { FiltersRequestWithoutArea } from 'src/app/shared/model/filters.model';

describe('TotalParticipantsComponent', () => {
  let component: TotalParticipantsComponent;
  let fixture: ComponentFixture<TotalParticipantsComponent>;
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
    mockActivityService = jasmine.createSpyObj('ActivityService', [
      'contarParticipantesTotales', 'contarAsistenciasTotales', 'contarPoblacionTotal'
    ]);
    mockActivityService.contarParticipantesTotales.and.returnValue(of(150));
    mockActivityService.contarAsistenciasTotales.and.returnValue(of(300));
    mockActivityService.contarPoblacionTotal.and.returnValue(of(1000));

    mockDepartmentService = jasmine.createSpyObj('DepartmentService', ['consultarPorNombre']);
    mockDepartmentService.consultarPorNombre.and.returnValue(of({ identificador: 'dep1', nombre: 'Dir' }));

    mockAreaService = jasmine.createSpyObj('AreaService', ['consultarPorNombre']);
    mockAreaService.consultarPorNombre.and.returnValue(of({ identificador: 'a1', nombre: 'Area', tipoArea: 'AREA' }));

    mockSubAreaService = jasmine.createSpyObj('SubAreaService', ['consultarPorNombre']);
    mockSubAreaService.consultarPorNombre.and.returnValue(of({ identificador: 's1', nombre: 'Sub' }));

    TestBed.configureTestingModule({
      declarations: [TotalParticipantsComponent],
      providers: [
        { provide: ActivityService, useValue: mockActivityService },
        { provide: DepartmentService, useValue: mockDepartmentService },
        { provide: AreaService, useValue: mockAreaService },
        { provide: SubAreaService, useValue: mockSubAreaService }
      ],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(TotalParticipantsComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have default values', () => {
    expect(component.totalParticipantes).toBe(0);
    expect(component.totalAsistencias).toBe(0);
    expect(component.esCobertura).toBeFalse();
  });

  describe('ngOnInit', () => {
    it('should not call service when filtersRequest is null', () => {
      component.filtersRequest = null;
      fixture.detectChanges();
      expect(component.totalParticipantes).toBe(0);
    });

    it('should load statistics when filters and area are set', () => {
      component.filtersRequest = mockFilters;
      component.nombreArea = 'Dir Bienestar';
      component.tipoEstructura = 'DIRECCION';
      fixture.detectChanges();
      expect(mockDepartmentService.consultarPorNombre).toHaveBeenCalledWith('Dir Bienestar');
      expect(component.totalParticipantes).toBe(150);
      expect(component.totalAsistencias).toBe(300);
    });
  });

  describe('ngOnChanges', () => {
    it('should reload statistics when filtersRequest changes', () => {
      component.filtersRequest = mockFilters;
      component.nombreArea = 'Test';
      component.ngOnChanges({
        filtersRequest: new SimpleChange(null, mockFilters, false)
      });
      expect(mockDepartmentService.consultarPorNombre).toHaveBeenCalled();
    });
  });

  describe('loadTotalParticipants (via loadStatistics)', () => {
    it('should set totalParticipantes to 0 when filters are null', () => {
      component.filtersRequest = null;
      (component as any).loadStatistics();
      expect(component.totalParticipantes).toBe(0);
    });

    it('should set totalParticipantes to 0 when nombreArea is empty', () => {
      component.filtersRequest = mockFilters;
      component.nombreArea = '';
      (component as any).loadStatistics();
      expect(component.totalParticipantes).toBe(0);
    });

    it('should load participants for DIRECCION type', () => {
      component.filtersRequest = mockFilters;
      component.nombreArea = 'Dir';
      component.tipoEstructura = 'DIRECCION';
      (component as any).loadStatistics();
      expect(mockDepartmentService.consultarPorNombre).toHaveBeenCalled();
      expect(component.totalParticipantes).toBe(150);
    });

    it('should load participants for AREA type', () => {
      component.filtersRequest = mockFilters;
      component.nombreArea = 'Area Test';
      component.tipoEstructura = 'AREA';
      (component as any).loadStatistics();
      expect(mockAreaService.consultarPorNombre).toHaveBeenCalledWith('Area Test');
    });

    it('should load participants for SUBAREA type', () => {
      component.filtersRequest = mockFilters;
      component.nombreArea = 'Sub Test';
      component.tipoEstructura = 'SUBAREA';
      (component as any).loadStatistics();
      expect(mockSubAreaService.consultarPorNombre).toHaveBeenCalledWith('Sub Test');
    });

    it('should handle error gracefully', () => {
      mockDepartmentService.consultarPorNombre.and.returnValue(throwError(() => new Error('fail')));
      component.filtersRequest = mockFilters;
      component.nombreArea = 'Dir';
      component.tipoEstructura = 'DIRECCION';
      (component as any).loadStatistics();
      expect(component.totalParticipantes).toBe(0);
    });
  });

  describe('loadCobertura', () => {
    it('should calculate coverage percentage', () => {
      component.filtersRequest = { ...mockFilters, indicador: 'Cobertura' };
      component.nombreArea = 'Dir';
      component.tipoEstructura = 'DIRECCION';
      (component as any).loadStatistics();
      expect(component.esCobertura).toBeTrue();
      expect(mockActivityService.contarParticipantesTotales).toHaveBeenCalled();
      expect(mockActivityService.contarPoblacionTotal).toHaveBeenCalled();
      expect(component.porcentajeCobertura).toBe(15); // 150/1000 * 100
    });

    it('should set 0 when population is 0', () => {
      mockActivityService.contarPoblacionTotal.and.returnValue(of(0));
      mockActivityService.contarParticipantesTotales.and.returnValue(of(100));
      component.filtersRequest = { ...mockFilters, indicador: 'Cobertura' };
      component.nombreArea = 'Dir';
      component.tipoEstructura = 'DIRECCION';
      (component as any).loadStatistics();
      expect(component.porcentajeCobertura).toBe(0);
    });

    it('should set 0 when filters are null for cobertura', () => {
      component.filtersRequest = null;
      component.esCobertura = true;
      (component as any).loadCobertura();
      expect(component.porcentajeCobertura).toBe(0);
    });
  });

  describe('getAreaIdentifier', () => {
    it('should return null when nombreArea is empty', (done) => {
      component.nombreArea = '';
      (component as any).getAreaIdentifier().subscribe((result: string | null) => {
        expect(result).toBeNull();
        done();
      });
    });

    it('should use departmentService for DIRECCION', (done) => {
      component.tipoEstructura = 'DIRECCION';
      component.nombreArea = 'Test';
      (component as any).getAreaIdentifier().subscribe((result: string | null) => {
        expect(result).toBe('dep1');
        expect(mockDepartmentService.consultarPorNombre).toHaveBeenCalledWith('Test');
        done();
      });
    });

    it('should use areaService for AREA', (done) => {
      component.tipoEstructura = 'AREA';
      component.nombreArea = 'Test';
      (component as any).getAreaIdentifier().subscribe((result: string | null) => {
        expect(result).toBe('a1');
        expect(mockAreaService.consultarPorNombre).toHaveBeenCalledWith('Test');
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

  describe('loadTotalAsistencias', () => {
    it('should set totalAsistencias to 0 when filters are null', () => {
      component.filtersRequest = null;
      (component as any).loadTotalAsistencias();
      expect(component.totalAsistencias).toBe(0);
    });

    it('should set totalAsistencias to 0 when nombreArea is empty', () => {
      component.filtersRequest = mockFilters;
      component.nombreArea = '';
      (component as any).loadTotalAsistencias();
      expect(component.totalAsistencias).toBe(0);
    });

    it('should load asistencias for AREA type', () => {
      component.filtersRequest = mockFilters;
      component.nombreArea = 'Area Test';
      component.tipoEstructura = 'AREA';
      (component as any).loadTotalAsistencias();
      expect(mockActivityService.contarAsistenciasTotales).toHaveBeenCalled();
      expect(component.totalAsistencias).toBe(300);
    });

    it('should handle error gracefully', () => {
      mockAreaService.consultarPorNombre.and.returnValue(throwError(() => new Error('fail')));
      component.filtersRequest = mockFilters;
      component.nombreArea = 'Area';
      component.tipoEstructura = 'AREA';
      (component as any).loadTotalAsistencias();
      expect(component.totalAsistencias).toBe(0);
    });

    it('should set 0 when idArea is null', () => {
      mockDepartmentService.consultarPorNombre.and.returnValue(of(null as any));
      component.filtersRequest = mockFilters;
      component.nombreArea = 'Dir';
      component.tipoEstructura = 'DIRECCION';
      (component as any).loadTotalAsistencias();
      expect(component.totalAsistencias).toBe(0);
    });
  });

  describe('loadCobertura with null idArea', () => {
    it('should set defaults when area identifier is null', () => {
      mockDepartmentService.consultarPorNombre.and.returnValue(of(null as any));
      component.filtersRequest = { ...mockFilters, indicador: 'Cobertura' };
      component.nombreArea = 'Dir';
      component.tipoEstructura = 'DIRECCION';
      (component as any).loadCobertura();
      expect(component.totalParticipantes).toBe(0);
      expect(component.poblacionTotal).toBe(0);
    });

    it('should handle cobertura forkJoin error', () => {
      mockActivityService.contarParticipantesTotales.and.returnValue(throwError(() => new Error('fail')));
      component.filtersRequest = { ...mockFilters, indicador: 'Cobertura' };
      component.nombreArea = 'Dir';
      component.tipoEstructura = 'DIRECCION';
      (component as any).loadCobertura();
      expect(component.totalParticipantes).toBe(0);
    });
  });
});
