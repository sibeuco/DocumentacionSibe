import { Component, EventEmitter, Output, OnInit } from '@angular/core';
import { ActivityService } from 'src/app/shared/service/activity.service';
import { IndicatorService } from 'src/app/feature/manage-indicators/service/indicator.service';
import { FiltersRequestWithoutArea } from 'src/app/shared/model/filters.model';

@Component({
  selector: 'app-filter-list',
  templateUrl: './filter-list.component.html',
  styleUrls: ['./filter-list.component.scss']
})
export class FilterListComponent implements OnInit {

  @Output() filterApplied = new EventEmitter<FiltersRequestWithoutArea>();

  // Propiedades para los valores seleccionados
  selectedYear: string = '';
  selectedSemester: string = '';
  selectedRelation: string = '';
  selectedMonth: string = '';
  selectedCostCenter: string = '';
  selectedAcademicProgram: string = '';
  selectedProgramType: string = '';
  selectedIndicator: string = '';

  // Opciones para los filtros
  years: string[] = [];
  semesters: string[] = [];
  months: string[] = [];
  academicRelations: string[] = [];
  costCenters: string[] = [];
  academicPrograms: string[] = [];
  programTypes: string[] = [];
  indicators: string[] = [];

  // Control de exclusividad entre semestre, mes y año
  isMonthDisabled: boolean = false;
  isSemesterDisabled: boolean = false;
  isYearDisabled: boolean = false;

  constructor(
    private activityService: ActivityService,
    private indicatorService: IndicatorService
  ) { }

  ngOnInit(): void {
    this.loadYears();
    this.loadMonths();
    this.loadSemesters();
    this.loadAcademicRelations();
    this.loadCostCenters();
    this.loadAcademicPrograms();
    this.loadProgramTypes();
    this.loadIndicators();
    // Emitir el estado inicial de los filtros (todos null)
    this.updateFilters();
  }

  private loadYears(): void {
    this.activityService.consultarAnnosEjecucionesFinalizadas().subscribe({
      next: (response: string[]) => {
        if (response && response.length > 0) {
          this.years = response;
        }
      },
      error: (error) => {
        console.error('Error al cargar los años:', error);
        this.years = [];
      }
    });
  }

  private loadMonths(): void {
    this.activityService.consultarMesesEjecucionesFinalizadas().subscribe({
      next: (response: string[]) => {
        if (response && response.length > 0) {
          this.months = response;
        }
      },
      error: (error) => {
        console.error('Error al cargar los meses:', error);
        this.months = [];
      }
    });
  }

  private loadSemesters(): void {
    this.activityService.consultarSemestresEstudiantesEnEjecucionesFinalizadas().subscribe({
      next: (response: string[]) => {
        if (response && response.length > 0) {
          this.semesters = response;
        }
      },
      error: (error) => {
        console.error('Error al cargar los semestres:', error);
        this.semesters = [];
      }
    });
  }

  private loadAcademicRelations(): void {
    this.activityService.consultarTiposParticipantesEnEjecucionesFinalizadas().subscribe({
      next: (response: string[]) => {
        if (response && response.length > 0) {
          this.academicRelations = response;
        }
      },
      error: (error) => {
        console.error('Error al cargar las relaciones académicas:', error);
        this.academicRelations = [];
      }
    });
  }

  private loadCostCenters(): void {
    this.activityService.consultarCentrosCostosEmpleadosEnEjecucionesFinalizadas().subscribe({
      next: (response: string[]) => {
        if (response && response.length > 0) {
          this.costCenters = response;
        }
      },
      error: (error) => {
        console.error('Error al cargar los centros de costos:', error);
        this.costCenters = [];
      }
    });
  }

  private loadAcademicPrograms(): void {
    this.activityService.consultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadas().subscribe({
      next: (response: string[]) => {
        if (response && response.length > 0) {
          this.academicPrograms = response;
        }
      },
      error: (error) => {
        console.error('Error al cargar los programas académicos:', error);
        this.academicPrograms = [];
      }
    });
  }

  private loadProgramTypes(): void {
    this.activityService.consultarNivelesFormacionEstudiantesEnEjecucionesFinalizadas().subscribe({
      next: (response: string[]) => {
        if (response && response.length > 0) {
          this.programTypes = response;
        }
      },
      error: (error) => {
        console.error('Error al cargar los tipos de programa:', error);
        this.programTypes = [];
      }
    });
  }

  private loadIndicators(): void {
    this.indicatorService.consultarTodosLosIndicadores().subscribe({
      next: (response) => {
        if (response && response.length > 0) {
          this.indicators = response.map((i: any) => i.nombre);
        }
      },
      error: (error) => {
        console.error('Error al cargar los indicadores:', error);
        this.indicators = [];
      }
    });
  }

  private buildFiltersRequest(): FiltersRequestWithoutArea {
    return {
      mes: this.selectedMonth && this.selectedMonth !== '' ? this.selectedMonth : null,
      anno: this.selectedYear && this.selectedYear !== '' ? Number(this.selectedYear) : null,
      semestre: this.selectedSemester && this.selectedSemester !== '' ? this.selectedSemester : null,
      programaAcademico: this.selectedAcademicProgram && this.selectedAcademicProgram !== '' ? this.selectedAcademicProgram : null,
      tipoProgramaAcademico: this.selectedProgramType && this.selectedProgramType !== '' ? this.selectedProgramType : null,
      centroCostos: this.selectedCostCenter && this.selectedCostCenter !== '' ? this.selectedCostCenter : null,
      tipoParticipante: this.selectedRelation && this.selectedRelation !== '' ? this.selectedRelation : null,
      indicador: this.selectedIndicator && this.selectedIndicator !== '' ? this.selectedIndicator : null
    } as FiltersRequestWithoutArea;
  }

  updateFilters(): void {
    const filtersRequest = this.buildFiltersRequest();
    this.filterApplied.emit(filtersRequest);
  }

  onFilter(): void {
    this.updateFilters();
  }

  clearFilters(): void {
    this.selectedYear = '';
    this.selectedSemester = '';
    this.selectedRelation = '';
    this.selectedMonth = '';
    this.selectedCostCenter = '';
    this.selectedAcademicProgram = '';
    this.selectedProgramType = '';
    this.selectedIndicator = '';
    this.isMonthDisabled = false;
    this.isSemesterDisabled = false;
    this.isYearDisabled = false;
    this.updateFilters();
  }

  onYearChange(): void {
    if (this.selectedYear && this.selectedYear !== '') {
      // Si se selecciona un año, desactivar semestre y limpiar su valor
      this.isSemesterDisabled = true;
      this.selectedSemester = '';
    } else {
      // Si se deselecciona el año, reactivar el semestre solo si el mes también está deseleccionado
      if (!this.selectedMonth || this.selectedMonth === '') {
        this.isSemesterDisabled = false;
      }
    }
    this.updateFilters();
  }

  onSemesterChange(): void {
    if (this.selectedSemester && this.selectedSemester !== '') {
      // Si se selecciona un semestre, desactivar año, mes y limpiar sus valores
      this.isYearDisabled = true;
      this.isMonthDisabled = true;
      this.selectedYear = '';
      this.selectedMonth = '';
    } else {
      // Si se deselecciona el semestre, reactivar año y mes
      this.isYearDisabled = false;
      this.isMonthDisabled = false;
    }
    this.updateFilters();
  }

  onMonthChange(): void {
    if (this.selectedMonth && this.selectedMonth !== '') {
      // Si se selecciona un mes, desactivar semestre y limpiar su valor
      this.isSemesterDisabled = true;
      this.selectedSemester = '';
    } else {
      // Si se deselecciona el mes, reactivar el semestre solo si el año también está deseleccionado
      if (!this.selectedYear || this.selectedYear === '') {
        this.isSemesterDisabled = false;
      }
    }
    this.updateFilters();
  }

}
