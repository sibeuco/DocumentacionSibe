import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { of, throwError } from 'rxjs';

import { FilterListComponent } from './filter-list.component';
import { ActivityService } from 'src/app/shared/service/activity.service';
import { IndicatorService } from 'src/app/feature/manage-indicators/service/indicator.service';

describe('FilterListComponent', () => {
  let component: FilterListComponent;
  let fixture: ComponentFixture<FilterListComponent>;
  let mockActivityService: jasmine.SpyObj<ActivityService>;
  let mockIndicatorService: jasmine.SpyObj<IndicatorService>;

  beforeEach(() => {
    mockActivityService = jasmine.createSpyObj('ActivityService', [
      'consultarAnnosEjecucionesFinalizadas',
      'consultarMesesEjecucionesFinalizadas',
      'consultarSemestresEstudiantesEnEjecucionesFinalizadas',
      'consultarTiposParticipantesEnEjecucionesFinalizadas',
      'consultarCentrosCostosEmpleadosEnEjecucionesFinalizadas',
      'consultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadas',
      'consultarNivelesFormacionEstudiantesEnEjecucionesFinalizadas'
    ]);
    mockIndicatorService = jasmine.createSpyObj('IndicatorService', ['consultarTodosLosIndicadores']);

    // Default returns
    mockActivityService.consultarAnnosEjecucionesFinalizadas.and.returnValue(of(['2024', '2025']));
    mockActivityService.consultarMesesEjecucionesFinalizadas.and.returnValue(of(['Enero', 'Febrero']));
    mockActivityService.consultarSemestresEstudiantesEnEjecucionesFinalizadas.and.returnValue(of(['1', '2']));
    mockActivityService.consultarTiposParticipantesEnEjecucionesFinalizadas.and.returnValue(of(['Estudiante', 'Docente']));
    mockActivityService.consultarCentrosCostosEmpleadosEnEjecucionesFinalizadas.and.returnValue(of(['CC1']));
    mockActivityService.consultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadas.and.returnValue(of(['Ing. Sistemas']));
    mockActivityService.consultarNivelesFormacionEstudiantesEnEjecucionesFinalizadas.and.returnValue(of(['Pregrado']));
    mockIndicatorService.consultarTodosLosIndicadores.and.returnValue(of([{ nombre: 'Ind1' }, { nombre: 'Ind2' }] as any));

    TestBed.configureTestingModule({
      imports: [FormsModule, HttpClientTestingModule, RouterTestingModule],
      declarations: [FilterListComponent],
      providers: [
        { provide: ActivityService, useValue: mockActivityService },
        { provide: IndicatorService, useValue: mockIndicatorService }
      ],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(FilterListComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    fixture.detectChanges();
    expect(component).toBeTruthy();
  });

  it('should load all filter options on init', () => {
    fixture.detectChanges();
    expect(component.years).toEqual(['2024', '2025']);
    expect(component.months).toEqual(['Enero', 'Febrero']);
    expect(component.semesters).toEqual(['1', '2']);
    expect(component.academicRelations).toEqual(['Estudiante', 'Docente']);
    expect(component.costCenters).toEqual(['CC1']);
    expect(component.academicPrograms).toEqual(['Ing. Sistemas']);
    expect(component.programTypes).toEqual(['Pregrado']);
    expect(component.indicators).toEqual(['Ind1', 'Ind2']);
  });

  it('should emit initial filters on init', () => {
    spyOn(component.filterApplied, 'emit');
    fixture.detectChanges();
    expect(component.filterApplied.emit).toHaveBeenCalled();
  });

  it('should handle errors when loading years', () => {
    mockActivityService.consultarAnnosEjecucionesFinalizadas.and.returnValue(throwError(() => new Error('fail')));
    fixture.detectChanges();
    expect(component.years).toEqual([]);
  });

  describe('filter exclusivity logic', () => {
    beforeEach(() => fixture.detectChanges());

    it('should disable semester when year is selected', () => {
      component.selectedYear = '2025';
      component.onYearChange();
      expect(component.isSemesterDisabled).toBeTrue();
      expect(component.selectedSemester).toBe('');
    });

    it('should re-enable semester when year is cleared and month is empty', () => {
      component.selectedYear = '2025';
      component.onYearChange();

      component.selectedYear = '';
      component.onYearChange();
      expect(component.isSemesterDisabled).toBeFalse();
    });

    it('should disable year and month when semester is selected', () => {
      component.selectedSemester = '1';
      component.onSemesterChange();
      expect(component.isYearDisabled).toBeTrue();
      expect(component.isMonthDisabled).toBeTrue();
      expect(component.selectedYear).toBe('');
      expect(component.selectedMonth).toBe('');
    });

    it('should re-enable year and month when semester is cleared', () => {
      component.selectedSemester = '1';
      component.onSemesterChange();

      component.selectedSemester = '';
      component.onSemesterChange();
      expect(component.isYearDisabled).toBeFalse();
      expect(component.isMonthDisabled).toBeFalse();
    });

    it('should disable semester when month is selected', () => {
      component.selectedMonth = 'Enero';
      component.onMonthChange();
      expect(component.isSemesterDisabled).toBeTrue();
      expect(component.selectedSemester).toBe('');
    });

    it('should re-enable semester when month is cleared and year is empty', () => {
      component.selectedMonth = 'Enero';
      component.onMonthChange();

      component.selectedMonth = '';
      component.onMonthChange();
      expect(component.isSemesterDisabled).toBeFalse();
    });
  });

  describe('buildFiltersRequest and emit', () => {
    beforeEach(() => fixture.detectChanges());

    it('should emit filter request with selected values on onFilter', () => {
      spyOn(component.filterApplied, 'emit');
      component.selectedYear = '2025';
      component.selectedMonth = 'Marzo';
      component.selectedIndicator = 'Ind1';

      component.onFilter();

      const emitted = (component.filterApplied.emit as jasmine.Spy).calls.mostRecent().args[0];
      expect(emitted.anno).toBe(2025);
      expect(emitted.mes).toBe('Marzo');
      expect(emitted.indicador).toBe('Ind1');
      expect(emitted.semestre).toBeNull();
    });

    it('should emit null values for empty selections', () => {
      spyOn(component.filterApplied, 'emit');
      component.onFilter();

      const emitted = (component.filterApplied.emit as jasmine.Spy).calls.mostRecent().args[0];
      expect(emitted.anno).toBeNull();
      expect(emitted.mes).toBeNull();
      expect(emitted.semestre).toBeNull();
      expect(emitted.programaAcademico).toBeNull();
    });
  });

  describe('clearFilters', () => {
    beforeEach(() => fixture.detectChanges());

    it('should reset all selections and disabled states', () => {
      component.selectedYear = '2025';
      component.selectedSemester = '1';
      component.isMonthDisabled = true;
      component.isSemesterDisabled = true;
      component.isYearDisabled = true;

      spyOn(component.filterApplied, 'emit');
      component.clearFilters();

      expect(component.selectedYear).toBe('');
      expect(component.selectedSemester).toBe('');
      expect(component.selectedRelation).toBe('');
      expect(component.selectedMonth).toBe('');
      expect(component.selectedCostCenter).toBe('');
      expect(component.selectedAcademicProgram).toBe('');
      expect(component.selectedProgramType).toBe('');
      expect(component.selectedIndicator).toBe('');
      expect(component.isMonthDisabled).toBeFalse();
      expect(component.isSemesterDisabled).toBeFalse();
      expect(component.isYearDisabled).toBeFalse();
      expect(component.filterApplied.emit).toHaveBeenCalled();
    });
  });

  describe('error handling for all services', () => {
    it('should handle error loading months', () => {
      mockActivityService.consultarMesesEjecucionesFinalizadas.and.returnValue(throwError(() => new Error('fail')));
      fixture.detectChanges();
      expect(component.months).toEqual([]);
    });

    it('should handle error loading semesters', () => {
      mockActivityService.consultarSemestresEstudiantesEnEjecucionesFinalizadas.and.returnValue(throwError(() => new Error('fail')));
      fixture.detectChanges();
      expect(component.semesters).toEqual([]);
    });

    it('should handle error loading academic relations', () => {
      mockActivityService.consultarTiposParticipantesEnEjecucionesFinalizadas.and.returnValue(throwError(() => new Error('fail')));
      fixture.detectChanges();
      expect(component.academicRelations).toEqual([]);
    });

    it('should handle error loading cost centers', () => {
      mockActivityService.consultarCentrosCostosEmpleadosEnEjecucionesFinalizadas.and.returnValue(throwError(() => new Error('fail')));
      fixture.detectChanges();
      expect(component.costCenters).toEqual([]);
    });

    it('should handle error loading academic programs', () => {
      mockActivityService.consultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadas.and.returnValue(throwError(() => new Error('fail')));
      fixture.detectChanges();
      expect(component.academicPrograms).toEqual([]);
    });

    it('should handle error loading program types', () => {
      mockActivityService.consultarNivelesFormacionEstudiantesEnEjecucionesFinalizadas.and.returnValue(throwError(() => new Error('fail')));
      fixture.detectChanges();
      expect(component.programTypes).toEqual([]);
    });

    it('should handle error loading indicators', () => {
      mockIndicatorService.consultarTodosLosIndicadores.and.returnValue(throwError(() => new Error('fail')));
      fixture.detectChanges();
      expect(component.indicators).toEqual([]);
    });
  });

  describe('empty response handling', () => {
    it('should not set years when response is empty', () => {
      mockActivityService.consultarAnnosEjecucionesFinalizadas.and.returnValue(of([]));
      fixture.detectChanges();
      expect(component.years).toEqual([]);
    });

    it('should not set months when response is empty', () => {
      mockActivityService.consultarMesesEjecucionesFinalizadas.and.returnValue(of([]));
      fixture.detectChanges();
      expect(component.months).toEqual([]);
    });

    it('should not set semesters when response is empty', () => {
      mockActivityService.consultarSemestresEstudiantesEnEjecucionesFinalizadas.and.returnValue(of([]));
      fixture.detectChanges();
      expect(component.semesters).toEqual([]);
    });

    it('should not set indicators when response is empty', () => {
      mockIndicatorService.consultarTodosLosIndicadores.and.returnValue(of([]));
      fixture.detectChanges();
      expect(component.indicators).toEqual([]);
    });

    it('should not set indicators when response is null', () => {
      mockIndicatorService.consultarTodosLosIndicadores.and.returnValue(of(null as any));
      fixture.detectChanges();
      expect(component.indicators).toEqual([]);
    });
  });

  describe('onYearChange when month is also selected', () => {
    beforeEach(() => fixture.detectChanges());

    it('should keep semester disabled when year is cleared but month is selected', () => {
      component.selectedMonth = 'Enero';
      component.onMonthChange();
      expect(component.isSemesterDisabled).toBeTrue();

      component.selectedYear = '';
      component.onYearChange();
      // month is still selected, so semester should stay disabled
      expect(component.isSemesterDisabled).toBeTrue();
    });
  });

  describe('onMonthChange when year is also selected', () => {
    beforeEach(() => fixture.detectChanges());

    it('should keep semester disabled when month is cleared but year is selected', () => {
      component.selectedYear = '2025';
      component.onYearChange();
      expect(component.isSemesterDisabled).toBeTrue();

      component.selectedMonth = '';
      component.onMonthChange();
      // year is still selected, so semester should stay disabled
      expect(component.isSemesterDisabled).toBeTrue();
    });
  });

  describe('updateFilters', () => {
    beforeEach(() => fixture.detectChanges());

    it('should emit all selected filters', () => {
      spyOn(component.filterApplied, 'emit');
      component.selectedYear = '2024';
      component.selectedSemester = '1';
      component.selectedRelation = 'Estudiante';
      component.selectedCostCenter = 'CC1';
      component.selectedAcademicProgram = 'Ing. Sistemas';
      component.selectedProgramType = 'Pregrado';
      component.selectedIndicator = 'Ind1';
      component.updateFilters();
      const emitted = (component.filterApplied.emit as jasmine.Spy).calls.mostRecent().args[0];
      expect(emitted.anno).toBe(2024);
      expect(emitted.semestre).toBe('1');
      expect(emitted.tipoParticipante).toBe('Estudiante');
      expect(emitted.centroCostos).toBe('CC1');
      expect(emitted.programaAcademico).toBe('Ing. Sistemas');
      expect(emitted.tipoProgramaAcademico).toBe('Pregrado');
      expect(emitted.indicador).toBe('Ind1');
    });
  });

  describe('empty response handling for remaining arrays', () => {
    it('should not set academicRelations when response is empty', () => {
      mockActivityService.consultarTiposParticipantesEnEjecucionesFinalizadas.and.returnValue(of([]));
      fixture.detectChanges();
      expect(component.academicRelations).toEqual([]);
    });

    it('should not set costCenters when response is empty', () => {
      mockActivityService.consultarCentrosCostosEmpleadosEnEjecucionesFinalizadas.and.returnValue(of([]));
      fixture.detectChanges();
      expect(component.costCenters).toEqual([]);
    });

    it('should not set academicPrograms when response is empty', () => {
      mockActivityService.consultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadas.and.returnValue(of([]));
      fixture.detectChanges();
      expect(component.academicPrograms).toEqual([]);
    });

    it('should not set programTypes when response is empty', () => {
      mockActivityService.consultarNivelesFormacionEstudiantesEnEjecucionesFinalizadas.and.returnValue(of([]));
      fixture.detectChanges();
      expect(component.programTypes).toEqual([]);
    });
  });

  describe('null response handling', () => {
    it('should not set years when response is null', () => {
      mockActivityService.consultarAnnosEjecucionesFinalizadas.and.returnValue(of(null as any));
      fixture.detectChanges();
      expect(component.years).toEqual([]);
    });

    it('should not set months when response is null', () => {
      mockActivityService.consultarMesesEjecucionesFinalizadas.and.returnValue(of(null as any));
      fixture.detectChanges();
      expect(component.months).toEqual([]);
    });

    it('should not set semesters when response is null', () => {
      mockActivityService.consultarSemestresEstudiantesEnEjecucionesFinalizadas.and.returnValue(of(null as any));
      fixture.detectChanges();
      expect(component.semesters).toEqual([]);
    });

    it('should not set academicRelations when response is null', () => {
      mockActivityService.consultarTiposParticipantesEnEjecucionesFinalizadas.and.returnValue(of(null as any));
      fixture.detectChanges();
      expect(component.academicRelations).toEqual([]);
    });

    it('should not set costCenters when response is null', () => {
      mockActivityService.consultarCentrosCostosEmpleadosEnEjecucionesFinalizadas.and.returnValue(of(null as any));
      fixture.detectChanges();
      expect(component.costCenters).toEqual([]);
    });

    it('should not set academicPrograms when response is null', () => {
      mockActivityService.consultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadas.and.returnValue(of(null as any));
      fixture.detectChanges();
      expect(component.academicPrograms).toEqual([]);
    });

    it('should not set programTypes when response is null', () => {
      mockActivityService.consultarNivelesFormacionEstudiantesEnEjecucionesFinalizadas.and.returnValue(of(null as any));
      fixture.detectChanges();
      expect(component.programTypes).toEqual([]);
    });
  });

  describe('buildFiltersRequest with month only', () => {
    beforeEach(() => fixture.detectChanges());

    it('should emit month without year', () => {
      spyOn(component.filterApplied, 'emit');
      component.selectedMonth = 'Febrero';
      component.updateFilters();
      const emitted = (component.filterApplied.emit as jasmine.Spy).calls.mostRecent().args[0];
      expect(emitted.mes).toBe('Febrero');
      expect(emitted.anno).toBeNull();
    });
  });
});
