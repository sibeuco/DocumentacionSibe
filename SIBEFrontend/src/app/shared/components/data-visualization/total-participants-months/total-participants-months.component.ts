import { AfterViewInit, Component, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { Chart, registerables } from 'chart.js';
import ChartDataLabels from 'chartjs-plugin-datalabels';
import { FiltersRequestWithoutArea, FiltersRequest, StadisticMonthsResponse } from 'src/app/shared/model/filters.model';
import { ActivityService } from 'src/app/shared/service/activity.service';
import { DepartmentService } from 'src/app/shared/service/department.service';
import { AreaService } from 'src/app/shared/service/area.service';
import { SubAreaService } from 'src/app/shared/service/subarea.service';
import { Observable, of, forkJoin } from 'rxjs';
import { switchMap, catchError } from 'rxjs/operators';

const INDICADOR_COBERTURA = 'Cobertura';

Chart.register(...registerables);

@Component({
  selector: 'app-total-participants-months',
  templateUrl: './total-participants-months.component.html',
  styleUrls: ['./total-participants-months.component.scss']
})
export class TotalParticipantsMonthsComponent implements AfterViewInit, OnInit, OnChanges {
  @Input() filtersRequest: FiltersRequestWithoutArea | null = null;
  @Input() tipoEstructura: 'DIRECCION' | 'AREA' | 'SUBAREA' = 'DIRECCION';
  @Input() nombreArea: string = '';
  @Input() participantesColor!: string;
  @Input() asistenciasColor!: string;
  @Input() title!: string;

  private myBarChart: Chart | null = null;

  constructor(
    private activityService: ActivityService,
    private departmentService: DepartmentService,
    private areaService: AreaService,
    private subAreaService: SubAreaService
  ) {}

  ngOnInit(): void {
    // Los datos se cargarán después de inicializar el gráfico
  }

  ngOnChanges(changes: SimpleChanges): void {
    if ((changes['filtersRequest'] || changes['tipoEstructura'] || changes['nombreArea'] ||
         changes['participantesColor'] || changes['asistenciasColor']) && this.myBarChart) {
      this.loadStatistics();
    }
  }

  ngAfterViewInit(): void {
    this.initializeChart();
    // Cargar datos después de inicializar el gráfico
    this.loadStatistics();
  }

  private initializeChart(): void {
    const ctx = document.getElementById('myBarChart') as HTMLCanvasElement;
    if (!ctx) return;

    this.myBarChart = new Chart(ctx, {
      type: 'bar',
      data: {
        labels: [],
        datasets: [
          {
            label: 'Participantes',
            data: [],
            backgroundColor: this.participantesColor,
            borderColor: this.participantesColor,
            borderWidth: 1
          },
          {
            label: 'Asistencias',
            data: [],
            backgroundColor: this.asistenciasColor,
            borderColor: this.asistenciasColor,
            borderWidth: 1
          }
        ]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        layout: {
          padding: {
            left: 30,
            right: 30
          }
        },
        plugins: {
          datalabels: {
            color: 'black',
            anchor: 'end',
            align: 'end',
            font: {
              size: 10
            }
          },
          legend: {
            display: true,
            position: 'top'
          }
        },
        scales: {
          y: {
            beginAtZero: true
          }
        }
      },
      plugins: [ChartDataLabels]
    });
  }

  private loadStatistics(): void {
    if (!this.filtersRequest || !this.nombreArea || !this.tipoEstructura) {
      this.updateChart([], [], []);
      return;
    }

    const esCobertura = this.filtersRequest?.indicador?.toLowerCase().trim() === INDICADOR_COBERTURA.toLowerCase();

    this.getAreaIdentifier().pipe(
      switchMap((idArea: string | null) => {
        if (!idArea) {
          return of({ statistics: [] as StadisticMonthsResponse[], poblacion: 0 });
        }

        const filtersRequest: FiltersRequest = {
          mes: this.filtersRequest!.mes ?? '',
          anno: this.filtersRequest!.anno ?? 0,
          semestre: this.filtersRequest!.semestre ?? '',
          programaAcademico: this.filtersRequest!.programaAcademico ?? '',
          tipoProgramaAcademico: this.filtersRequest!.tipoProgramaAcademico ?? '',
          centroCostos: this.filtersRequest!.centroCostos ?? '',
          tipoParticipante: this.filtersRequest!.tipoParticipante ?? '',
          indicador: this.filtersRequest!.indicador ?? '',
          tipoArea: this.tipoEstructura,
          idArea: idArea
        };

        if (esCobertura) {
          const filtersRequestPoblacion: FiltersRequest = {
            mes: '',
            anno: 0,
            semestre: '',
            programaAcademico: this.filtersRequest!.programaAcademico ?? '',
            tipoProgramaAcademico: this.filtersRequest!.tipoProgramaAcademico ?? '',
            centroCostos: this.filtersRequest!.centroCostos ?? '',
            tipoParticipante: this.filtersRequest!.tipoParticipante ?? '',
            indicador: '',
            tipoArea: '',
            idArea: ''
          };

          return forkJoin({
            statistics: this.activityService.consultarEstadisticasParticipantesPorMes(filtersRequest),
            poblacion: this.activityService.contarPoblacionTotal(filtersRequestPoblacion)
          });
        }

        return this.activityService.consultarEstadisticasParticipantesPorMes(filtersRequest).pipe(
          switchMap(stats => of({ statistics: stats, poblacion: 0 }))
        );
      }),
      catchError(() => of({ statistics: [] as StadisticMonthsResponse[], poblacion: 0 }))
    ).subscribe((result: { statistics: StadisticMonthsResponse[]; poblacion: number }) => {
      const labels = result.statistics.map(stat => stat.mes);

      if (esCobertura && result.poblacion > 0) {
        const coberturaData = result.statistics.map(stat =>
          Math.round((stat.cantidadParticipantes / result.poblacion) * 10000) / 100
        );
        this.updateChart(labels, coberturaData, [], 'Cobertura (%)', '');
      } else {
        const participantesData = result.statistics.map(stat => stat.cantidadParticipantes);
        const asistenciasData = result.statistics.map(stat => stat.cantidadAsistencias);
        this.updateChart(labels, participantesData, asistenciasData, 'Participantes', 'Asistencias');
      }
    });
  }

  private updateChart(labels: string[], participantesData: number[], asistenciasData: number[],
                      labelDataset1: string = 'Participantes', labelDataset2: string = 'Asistencias'): void {
    if (this.myBarChart) {
      this.myBarChart.data.labels = labels;
      this.myBarChart.data.datasets[0].label = labelDataset1;
      this.myBarChart.data.datasets[0].data = participantesData;
      this.myBarChart.data.datasets[0].backgroundColor = this.participantesColor;
      this.myBarChart.data.datasets[0].borderColor = this.participantesColor;
      this.myBarChart.data.datasets[1].label = labelDataset2;
      this.myBarChart.data.datasets[1].data = asistenciasData;
      this.myBarChart.data.datasets[1].backgroundColor = this.asistenciasColor;
      this.myBarChart.data.datasets[1].borderColor = this.asistenciasColor;
      this.myBarChart.update();
    }
  }

  private getAreaIdentifier(): Observable<string | null> {
    if (!this.nombreArea) {
      return of(null);
    }

    switch (this.tipoEstructura) {
      case 'DIRECCION':
        return this.departmentService.consultarPorNombre(this.nombreArea).pipe(
          switchMap(response => of(response?.identificador || null)),
          catchError(() => of(null))
        );
      case 'AREA':
        return this.areaService.consultarPorNombre(this.nombreArea).pipe(
          switchMap(response => of(response?.identificador || null)),
          catchError(() => of(null))
        );
      case 'SUBAREA':
        return this.subAreaService.consultarPorNombre(this.nombreArea).pipe(
          switchMap(response => of(response?.identificador || null)),
          catchError(() => of(null))
        );
      default:
        return of(null);
    }
  }
}
