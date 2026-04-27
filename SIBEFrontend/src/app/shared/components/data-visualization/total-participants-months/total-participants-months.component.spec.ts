import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA, SimpleChange } from '@angular/core';
import { of, throwError } from 'rxjs';

import { TotalParticipantsMonthsComponent } from './total-participants-months.component';
import { ActivityService } from 'src/app/shared/service/activity.service';
import { DepartmentService } from 'src/app/shared/service/department.service';
import { AreaService } from 'src/app/shared/service/area.service';
import { SubAreaService } from 'src/app/shared/service/subarea.service';
import { FiltersRequestWithoutArea, StadisticMonthsResponse } from 'src/app/shared/model/filters.model';

describe('TotalParticipantsMonthsComponent', () => {
  let component: TotalParticipantsMonthsComponent;
  let fixture: ComponentFixture<TotalParticipantsMonthsComponent>;
  let mockActivityService: jasmine.SpyObj<ActivityService>;
  let mockDepartmentService: jasmine.SpyObj<DepartmentService>;
  let mockAreaService: jasmine.SpyObj<AreaService>;
  let mockSubAreaService: jasmine.SpyObj<SubAreaService>;

  const mockFilters: FiltersRequestWithoutArea = {
    mes: 'Enero', anno: 2024, semestre: '2024-1',
    programaAcademico: '', tipoProgramaAcademico: '',
    centroCostos: '', tipoParticipante: '', indicador: ''
  };

  const mockStats: StadisticMonthsResponse[] = [
    { mes: 'Enero', cantidadParticipantes: 100, cantidadAsistencias: 200 },
    { mes: 'Febrero', cantidadParticipantes: 120, cantidadAsistencias: 250 }
  ];

  beforeEach(() => {
    mockActivityService = jasmine.createSpyObj('ActivityService', [
      'consultarEstadisticasParticipantesPorMes', 'contarPoblacionTotal'
    ]);
    mockActivityService.consultarEstadisticasParticipantesPorMes.and.returnValue(of(mockStats));
    mockActivityService.contarPoblacionTotal.and.returnValue(of(1000));

    mockDepartmentService = jasmine.createSpyObj('DepartmentService', ['consultarPorNombre']);
    mockDepartmentService.consultarPorNombre.and.returnValue(of({ identificador: 'dep1', nombre: 'Dir' }));

    mockAreaService = jasmine.createSpyObj('AreaService', ['consultarPorNombre']);
    mockAreaService.consultarPorNombre.and.returnValue(of({ identificador: 'a1', nombre: 'Area', tipoArea: 'AREA' }));

    mockSubAreaService = jasmine.createSpyObj('SubAreaService', ['consultarPorNombre']);
    mockSubAreaService.consultarPorNombre.and.returnValue(of({ identificador: 's1', nombre: 'Sub' }));

    TestBed.configureTestingModule({
      declarations: [TotalParticipantsMonthsComponent],
      providers: [
        { provide: ActivityService, useValue: mockActivityService },
        { provide: DepartmentService, useValue: mockDepartmentService },
        { provide: AreaService, useValue: mockAreaService },
        { provide: SubAreaService, useValue: mockSubAreaService }
      ],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(TotalParticipantsMonthsComponent);
    component = fixture.componentInstance;
    component.participantesColor = 'rgba(0,139,80,0.5)';
    component.asistenciasColor = 'rgba(255,202,0,0.3)';
    component.title = 'Test Chart';
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnChanges', () => {
    it('should call loadStatistics when filtersRequest changes and chart exists', () => {
      (component as any).myBarChart = {
        data: { labels: [], datasets: [{ label: '', data: [], backgroundColor: '', borderColor: '' }, { label: '', data: [], backgroundColor: '', borderColor: '' }] },
        update: jasmine.createSpy()
      };
      component.filtersRequest = mockFilters;
      component.nombreArea = 'Dir';
      component.ngOnChanges({
        filtersRequest: new SimpleChange(null, mockFilters, false)
      });
      expect(mockDepartmentService.consultarPorNombre).toHaveBeenCalled();
    });

    it('should not call loadStatistics when chart is null', () => {
      (component as any).myBarChart = null;
      component.ngOnChanges({
        filtersRequest: new SimpleChange(null, mockFilters, false)
      });
      expect(mockDepartmentService.consultarPorNombre).not.toHaveBeenCalled();
    });
  });

  describe('updateChart', () => {
    it('should update chart data when chart exists', () => {
      const mockChart = {
        data: { labels: [] as string[], datasets: [
          { label: '', data: [] as number[], backgroundColor: '', borderColor: '' },
          { label: '', data: [] as number[], backgroundColor: '', borderColor: '' }
        ] },
        update: jasmine.createSpy('update')
      };
      (component as any).myBarChart = mockChart;
      (component as any).updateChart(['Ene', 'Feb'], [10, 20], [30, 40]);
      expect(mockChart.data.labels).toEqual(['Ene', 'Feb']);
      expect(mockChart.data.datasets[0].data).toEqual([10, 20]);
      expect(mockChart.data.datasets[1].data).toEqual([30, 40]);
      expect(mockChart.update).toHaveBeenCalled();
    });

    it('should not throw when chart is null', () => {
      (component as any).myBarChart = null;
      expect(() => (component as any).updateChart([], [], [])).not.toThrow();
    });
  });

  describe('loadStatistics', () => {
    let mockChart: any;

    beforeEach(() => {
      mockChart = {
        data: { labels: [] as string[], datasets: [
          { label: '', data: [] as number[], backgroundColor: '', borderColor: '' },
          { label: '', data: [] as number[], backgroundColor: '', borderColor: '' }
        ] },
        update: jasmine.createSpy('update')
      };
      (component as any).myBarChart = mockChart;
    });

    it('should clear chart when filtersRequest is null', () => {
      component.filtersRequest = null;
      (component as any).loadStatistics();
      expect(mockChart.data.labels).toEqual([]);
    });

    it('should load monthly stats for regular indicator', () => {
      component.filtersRequest = mockFilters;
      component.nombreArea = 'Dir';
      component.tipoEstructura = 'DIRECCION';
      (component as any).loadStatistics();
      expect(mockChart.data.labels).toEqual(['Enero', 'Febrero']);
      expect(mockChart.data.datasets[0].data).toEqual([100, 120]);
      expect(mockChart.data.datasets[1].data).toEqual([200, 250]);
    });

    it('should handle Cobertura indicator', () => {
      component.filtersRequest = { ...mockFilters, indicador: 'Cobertura' };
      component.nombreArea = 'Dir';
      component.tipoEstructura = 'DIRECCION';
      (component as any).loadStatistics();
      expect(mockActivityService.contarPoblacionTotal).toHaveBeenCalled();
      expect(mockChart.data.datasets[0].label).toBe('Cobertura (%)');
    });

    it('should handle error gracefully', () => {
      mockDepartmentService.consultarPorNombre.and.returnValue(throwError(() => new Error('fail')));
      component.filtersRequest = mockFilters;
      component.nombreArea = 'Dir';
      (component as any).loadStatistics();
      expect(mockChart.data.labels).toEqual([]);
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

    it('should use departmentService for DIRECCION', (done) => {
      component.tipoEstructura = 'DIRECCION';
      component.nombreArea = 'Test';
      (component as any).getAreaIdentifier().subscribe((result: string | null) => {
        expect(result).toBe('dep1');
        done();
      });
    });

    it('should use areaService for AREA', (done) => {
      component.tipoEstructura = 'AREA';
      component.nombreArea = 'Test';
      (component as any).getAreaIdentifier().subscribe((result: string | null) => {
        expect(result).toBe('a1');
        done();
      });
    });

    it('should use subAreaService for SUBAREA', (done) => {
      component.tipoEstructura = 'SUBAREA';
      component.nombreArea = 'Test';
      (component as any).getAreaIdentifier().subscribe((result: string | null) => {
        expect(result).toBe('s1');
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

  describe('loadStatistics null idArea path', () => {
    let mockChart: any;

    beforeEach(() => {
      mockChart = {
        data: { labels: [] as string[], datasets: [
          { label: '', data: [] as number[], backgroundColor: '', borderColor: '' },
          { label: '', data: [] as number[], backgroundColor: '', borderColor: '' }
        ] },
        update: jasmine.createSpy('update')
      };
      (component as any).myBarChart = mockChart;
    });

    it('should clear chart when area identifier is null', () => {
      mockDepartmentService.consultarPorNombre.and.returnValue(of(null as any));
      component.filtersRequest = mockFilters;
      component.nombreArea = 'Dir';
      (component as any).loadStatistics();
      expect(mockChart.data.labels).toEqual([]);
    });

    it('should handle Cobertura with zero population', () => {
      mockActivityService.contarPoblacionTotal.and.returnValue(of(0));
      component.filtersRequest = { ...mockFilters, indicador: 'Cobertura' };
      component.nombreArea = 'Dir';
      (component as any).loadStatistics();
      // With population 0, esCobertura && poblacion > 0 is false, so regular labels used
      expect(mockChart.data.datasets[0].label).toBe('Participantes');
    });
  });
});
