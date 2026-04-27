import { Component, AfterViewInit, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { Chart, registerables } from 'chart.js';
import ChartDataLabels from 'chartjs-plugin-datalabels';
import { FiltersRequestWithoutArea, FiltersRequest, StadisticAreasResponse } from 'src/app/shared/model/filters.model';
import { ActivityService } from 'src/app/shared/service/activity.service';
import { DepartmentService } from 'src/app/shared/service/department.service';
import { Observable, of, forkJoin } from 'rxjs';
import { switchMap, catchError } from 'rxjs/operators';

const INDICADOR_COBERTURA = 'Cobertura';

Chart.register(...registerables);

@Component({
  selector: 'app-top-data-container',
  templateUrl: './top-data-container.component.html',
  styleUrls: ['./top-data-container.component.scss']
})
export class TopDataContainerComponent implements AfterViewInit, OnInit, OnChanges {
  @Input() filtersRequest: FiltersRequestWithoutArea | null = null;

  tipoEstructura: 'DIRECCION' | 'AREA' | 'SUBAREA' = 'DIRECCION';
  nombreArea: string = 'Dirección de Bienestar y Evangelización';
  imageUrl: string = 'assets/images/home-college.png';

  private participantsChart: Chart | null = null;

  constructor(
    private activityService: ActivityService,
    private departmentService: DepartmentService
  ) {}

  ngOnInit(): void {
    // Los datos se cargarán después de inicializar el gráfico
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['filtersRequest'] && this.participantsChart) {
      this.loadStatistics();
    }
  }

  ngAfterViewInit(): void {
    this.initializeChart();
    // Cargar datos después de inicializar el gráfico
    this.loadStatistics();
  }

  private initializeChart(): void {
    const ctx = document.getElementById('participantsChart') as HTMLCanvasElement;
    if (!ctx) return;

    this.participantsChart = new Chart(ctx, {
      type: 'bar',
      data: {
        labels: [],
        datasets: [
          {
            label: 'Participantes',
            data: [],
            backgroundColor: 'rgba(0, 139, 80, 0.5)',
            borderColor: 'rgba(0, 139, 80, 0.5)',
            borderWidth: 1
          },
          {
            label: 'Asistencias',
            data: [],
            backgroundColor: 'rgba(255, 202, 0, 0.3)',
            borderColor: 'rgba(255, 202, 0, 0.3)',
            borderWidth: 1
          }
        ]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        indexAxis: 'y',
        layout: {
          padding: {
            left: 30
          }
        },
        scales: {
          x: {
            beginAtZero: true
          }
        },
        plugins: {
          datalabels: {
            color: '#000',
            anchor: 'end',
            align: 'end',
            font: {
              size: 10
            },
            formatter: (value) => value
          },
          legend: {
            display: true,
            position: 'top'
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
          return of({ statistics: [] as StadisticAreasResponse[], poblacion: 0 });
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
            statistics: this.activityService.consultarEstadisticasParticipantesPorEstructura(filtersRequest),
            poblacion: this.activityService.contarPoblacionTotal(filtersRequestPoblacion)
          });
        }

        return this.activityService.consultarEstadisticasParticipantesPorEstructura(filtersRequest).pipe(
          switchMap(stats => of({ statistics: stats, poblacion: 0 }))
        );
      }),
      catchError(() => of({ statistics: [] as StadisticAreasResponse[], poblacion: 0 }))
    ).subscribe((result: { statistics: StadisticAreasResponse[]; poblacion: number }) => {
      const filteredStatistics = result.statistics.filter(stat => stat.tipoArea === 'AREA');
      const labels = filteredStatistics.map(stat => stat.nombre);

      if (esCobertura && result.poblacion > 0) {
        const coberturaData = filteredStatistics.map(stat =>
          Math.round((stat.cantidadParticipantes / result.poblacion) * 10000) / 100
        );
        this.updateChart(labels, coberturaData, [], 'Cobertura (%)', '');
      } else {
        const participantesData = filteredStatistics.map(stat => stat.cantidadParticipantes);
        const asistenciasData = filteredStatistics.map(stat => stat.cantidadAsistencias);
        this.updateChart(labels, participantesData, asistenciasData, 'Participantes', 'Asistencias');
      }
    });
  }

  private updateChart(labels: string[], participantesData: number[], asistenciasData: number[],
                      labelDataset1: string = 'Participantes', labelDataset2: string = 'Asistencias'): void {
    if (this.participantsChart) {
      this.participantsChart.data.labels = labels;
      this.participantsChart.data.datasets[0].label = labelDataset1;
      this.participantsChart.data.datasets[0].data = participantesData;
      this.participantsChart.data.datasets[1].label = labelDataset2;
      this.participantsChart.data.datasets[1].data = asistenciasData;
      this.participantsChart.update();
    }
  }

  private getAreaIdentifier(): Observable<string | null> {
    if (!this.nombreArea) {
      return of(null);
    }

    if (this.tipoEstructura === 'DIRECCION') {
      return this.departmentService.consultarPorNombre(this.nombreArea).pipe(
        switchMap(response => of(response?.identificador || null)),
        catchError(() => of(null))
      );
    }

    return of(null);
  }
}
