import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA, SimpleChange } from '@angular/core';
import { of, throwError } from 'rxjs';

import { TopDataContainerComponent } from './top-data-container.component';
import { ActivityService } from 'src/app/shared/service/activity.service';
import { DepartmentService } from 'src/app/shared/service/department.service';
import { FiltersRequestWithoutArea, StadisticAreasResponse } from 'src/app/shared/model/filters.model';

describe('TopDataContainerComponent', () => {
  let component: TopDataContainerComponent;
  let fixture: ComponentFixture<TopDataContainerComponent>;
  let mockActivityService: jasmine.SpyObj<ActivityService>;
  let mockDepartmentService: jasmine.SpyObj<DepartmentService>;

  const mockStats: StadisticAreasResponse[] = [
    { nombre: 'Area 1', tipoArea: 'AREA', cantidadParticipantes: 100, cantidadAsistencias: 200 },
    { nombre: 'Area 2', tipoArea: 'AREA', cantidadParticipantes: 50, cantidadAsistencias: 80 },
    { nombre: 'Dir 1', tipoArea: 'DIRECCION', cantidadParticipantes: 150, cantidadAsistencias: 280 }
  ];

  const mockFilters: FiltersRequestWithoutArea = {
    mes: 'Enero', anno: 2024, semestre: '2024-1',
    programaAcademico: '', tipoProgramaAcademico: '',
    centroCostos: '', tipoParticipante: '', indicador: ''
  };

  beforeEach(() => {
    mockActivityService = jasmine.createSpyObj('ActivityService', [
      'consultarEstadisticasParticipantesPorEstructura', 'contarPoblacionTotal'
    ]);
    mockActivityService.consultarEstadisticasParticipantesPorEstructura.and.returnValue(of(mockStats));
    mockActivityService.contarPoblacionTotal.and.returnValue(of(1000));

    mockDepartmentService = jasmine.createSpyObj('DepartmentService', ['consultarPorNombre']);
    mockDepartmentService.consultarPorNombre.and.returnValue(of({ identificador: 'dep1', nombre: 'Dir Bienestar' }));

    TestBed.configureTestingModule({
      declarations: [TopDataContainerComponent],
      providers: [
        { provide: ActivityService, useValue: mockActivityService },
        { provide: DepartmentService, useValue: mockDepartmentService }
      ],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(TopDataContainerComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have default values', () => {
    expect(component.tipoEstructura).toBe('DIRECCION');
    expect(component.nombreArea).toBe('Dirección de Bienestar y Evangelización');
    expect(component.filtersRequest).toBeNull();
  });

  describe('ngOnChanges', () => {
    it('should call loadStatistics when filtersRequest changes and chart exists', () => {
      (component as any).participantsChart = { data: { labels: [], datasets: [{ data: [] }, { data: [] }] }, update: jasmine.createSpy() };
      component.filtersRequest = mockFilters;
      component.ngOnChanges({
        filtersRequest: new SimpleChange(null, mockFilters, false)
      });
      expect(mockDepartmentService.consultarPorNombre).toHaveBeenCalled();
    });

    it('should not call loadStatistics when chart does not exist', () => {
      (component as any).participantsChart = null;
      component.filtersRequest = mockFilters;
      component.ngOnChanges({
        filtersRequest: new SimpleChange(null, mockFilters, false)
      });
      expect(mockDepartmentService.consultarPorNombre).not.toHaveBeenCalled();
    });
  });

  describe('updateChart (via loadStatistics path)', () => {
    it('should update chart data when chart exists', () => {
      const mockChart = {
        data: { labels: [] as string[], datasets: [{ label: '', data: [] as number[], backgroundColor: '', borderColor: '' }, { label: '', data: [] as number[], backgroundColor: '', borderColor: '' }] },
        update: jasmine.createSpy('update')
      };
      (component as any).participantsChart = mockChart;
      (component as any).updateChart(['A', 'B'], [10, 20], [30, 40]);
      expect(mockChart.data.labels).toEqual(['A', 'B']);
      expect(mockChart.data.datasets[0].data).toEqual([10, 20]);
      expect(mockChart.data.datasets[1].data).toEqual([30, 40]);
      expect(mockChart.update).toHaveBeenCalled();
    });

    it('should not throw when chart is null', () => {
      (component as any).participantsChart = null;
      expect(() => (component as any).updateChart([], [], [])).not.toThrow();
    });
  });

  describe('loadStatistics', () => {
    it('should clear chart when filtersRequest is null', () => {
      const mockChart = {
        data: { labels: ['old'], datasets: [{ label: '', data: [1], backgroundColor: '', borderColor: '' }, { label: '', data: [2], backgroundColor: '', borderColor: '' }] },
        update: jasmine.createSpy('update')
      };
      (component as any).participantsChart = mockChart;
      component.filtersRequest = null;
      (component as any).loadStatistics();
      expect(mockChart.data.labels).toEqual([]);
    });

    it('should filter only AREA type stats', () => {
      const mockChart = {
        data: { labels: [] as string[], datasets: [{ label: '', data: [] as number[], backgroundColor: '', borderColor: '' }, { label: '', data: [] as number[], backgroundColor: '', borderColor: '' }] },
        update: jasmine.createSpy('update')
      };
      (component as any).participantsChart = mockChart;
      component.filtersRequest = mockFilters;
      (component as any).loadStatistics();
      // Only AREA type entries should be in labels (Area 1, Area 2), not DIRECCION
      expect(mockChart.data.labels).toEqual(['Area 1', 'Area 2']);
    });

    it('should handle Cobertura indicator', () => {
      const mockChart = {
        data: { labels: [] as string[], datasets: [{ label: '', data: [] as number[], backgroundColor: '', borderColor: '' }, { label: '', data: [] as number[], backgroundColor: '', borderColor: '' }] },
        update: jasmine.createSpy('update')
      };
      (component as any).participantsChart = mockChart;
      component.filtersRequest = { ...mockFilters, indicador: 'Cobertura' };
      (component as any).loadStatistics();
      expect(mockActivityService.contarPoblacionTotal).toHaveBeenCalled();
      expect(mockChart.data.datasets[0].label).toBe('Cobertura (%)');
    });

    it('should handle error gracefully', () => {
      const mockChart = {
        data: { labels: [] as string[], datasets: [{ label: '', data: [] as number[], backgroundColor: '', borderColor: '' }, { label: '', data: [] as number[], backgroundColor: '', borderColor: '' }] },
        update: jasmine.createSpy('update')
      };
      (component as any).participantsChart = mockChart;
      mockDepartmentService.consultarPorNombre.and.returnValue(throwError(() => new Error('fail')));
      component.filtersRequest = mockFilters;
      (component as any).loadStatistics();
      expect(mockChart.data.labels).toEqual([]);
    });
  });

  describe('getAreaIdentifier', () => {
    it('should call consultarPorNombre for DIRECCION', (done) => {
      component.tipoEstructura = 'DIRECCION';
      component.nombreArea = 'Test';
      (component as any).getAreaIdentifier().subscribe((result: string | null) => {
        expect(result).toBe('dep1');
        done();
      });
    });

    it('should return null when nombreArea is empty', (done) => {
      component.nombreArea = '';
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
  });

  describe('loadStatistics null idArea path', () => {
    it('should clear chart when area identifier is null', () => {
      const mockChart = {
        data: { labels: [] as string[], datasets: [{ label: '', data: [] as number[], backgroundColor: '', borderColor: '' }, { label: '', data: [] as number[], backgroundColor: '', borderColor: '' }] },
        update: jasmine.createSpy('update')
      };
      (component as any).participantsChart = mockChart;
      mockDepartmentService.consultarPorNombre.and.returnValue(of(null as any));
      component.filtersRequest = mockFilters;
      (component as any).loadStatistics();
      expect(mockChart.data.labels).toEqual([]);
    });

    it('should handle Cobertura with zero population', () => {
      const mockChart = {
        data: { labels: [] as string[], datasets: [{ label: '', data: [] as number[], backgroundColor: '', borderColor: '' }, { label: '', data: [] as number[], backgroundColor: '', borderColor: '' }] },
        update: jasmine.createSpy('update')
      };
      (component as any).participantsChart = mockChart;
      mockActivityService.contarPoblacionTotal.and.returnValue(of(0));
      component.filtersRequest = { ...mockFilters, indicador: 'Cobertura' };
      (component as any).loadStatistics();
      // With population 0, esCobertura && poblacion > 0 is false, so it falls through to regular labels
      expect(mockChart.data.datasets[0].label).toBe('Participantes');
    });
  });

  describe('ngAfterViewInit with chart canvas', () => {
    it('should initialize chart when canvas element exists', () => {
      const canvas = document.createElement('canvas');
      canvas.id = 'participantsChart';
      document.body.appendChild(canvas);
      component.filtersRequest = mockFilters;
      component.ngAfterViewInit();
      expect((component as any).participantsChart).toBeTruthy();
      document.body.removeChild(canvas);
    });
  });

  describe('loadStatistics catchError', () => {
    it('should handle error in statistics loading pipeline', () => {
      const mockChart = {
        data: { labels: [] as string[], datasets: [{ label: '', data: [] as number[], backgroundColor: '', borderColor: '' }, { label: '', data: [] as number[], backgroundColor: '', borderColor: '' }] },
        update: jasmine.createSpy('update')
      };
      (component as any).participantsChart = mockChart;
      mockDepartmentService.consultarPorNombre.and.returnValue(throwError(() => new Error('fail')));
      component.filtersRequest = mockFilters;
      (component as any).loadStatistics();
      expect(mockChart.data.labels).toEqual([]);
    });
  });
});
