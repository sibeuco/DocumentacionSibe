import { Component, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { ActivityService } from 'src/app/shared/service/activity.service';
import { DepartmentService } from 'src/app/shared/service/department.service';
import { AreaService } from 'src/app/shared/service/area.service';
import { SubAreaService } from 'src/app/shared/service/subarea.service';
import { FiltersRequest } from 'src/app/shared/model/filters.model';
import { Observable, of } from 'rxjs';
import { switchMap, catchError } from 'rxjs/operators';

@Component({
  selector: 'app-area-statistics',
  templateUrl: './area-statistics.component.html',
  styleUrls: ['./area-statistics.component.scss']
})
export class AreaStatisticsComponent implements OnInit, OnChanges {
  @Input() tipoEstructura: 'DIRECCION' | 'AREA' | 'SUBAREA' = 'AREA';
  @Input() nombreArea: string = '';

  totalActividades: number = 0;
  totalParticipantes: number = 0;
  totalAsistencias: number = 0;

  constructor(
    private activityService: ActivityService,
    private departmentService: DepartmentService,
    private areaService: AreaService,
    private subAreaService: SubAreaService
  ) {}

  ngOnInit(): void {
    this.loadTotalActivities();
    this.loadTotalParticipants();
    this.loadTotalAsistencias();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['tipoEstructura'] || changes['nombreArea']) {
      this.loadTotalActivities();
      this.loadTotalParticipants();
      this.loadTotalAsistencias();
    }
  }

  private createFiltersRequest(idArea: string, tipoArea: 'DIRECCION' | 'AREA' | 'SUBAREA'): FiltersRequest {
    return {
      mes: '',
      anno: 0,
      semestre: '',
      programaAcademico: '',
      tipoProgramaAcademico: '',
      centroCostos: '',
      tipoParticipante: '',
      indicador: '',
      tipoArea: tipoArea,
      idArea: idArea
    };
  }

  private loadTotalActivities(): void {
    if (!this.nombreArea || !this.tipoEstructura) {
      this.totalActividades = 0;
      return;
    }

    this.getAreaIdentifier().pipe(
      switchMap((idArea: string | null) => {
        if (!idArea) {
          return of(0);
        }

        const filtersRequest = this.createFiltersRequest(idArea, this.tipoEstructura);
        return this.activityService.contarEjecucionesTotales(filtersRequest);
      }),
      catchError(error => {
        console.error('Error al contar ejecuciones totales:', error);
        return of(0);
      })
    ).subscribe((total: number) => {
      this.totalActividades = total;
    });
  }

  private loadTotalParticipants(): void {
    if (!this.nombreArea || !this.tipoEstructura) {
      this.totalParticipantes = 0;
      return;
    }

    this.getAreaIdentifier().pipe(
      switchMap((idArea: string | null) => {
        if (!idArea) {
          return of(0);
        }

        const filtersRequest = this.createFiltersRequest(idArea, this.tipoEstructura);
        return this.activityService.contarParticipantesTotales(filtersRequest);
      }),
      catchError(error => {
        console.error('Error al contar participantes totales:', error);
        return of(0);
      })
    ).subscribe((total: number) => {
      this.totalParticipantes = total;
    });
  }

  private loadTotalAsistencias(): void {
    if (!this.nombreArea || !this.tipoEstructura) {
      this.totalAsistencias = 0;
      return;
    }

    this.getAreaIdentifier().pipe(
      switchMap((idArea: string | null) => {
        if (!idArea) {
          return of(0);
        }

        const filtersRequest = this.createFiltersRequest(idArea, this.tipoEstructura);
        return this.activityService.contarAsistenciasTotales(filtersRequest);
      }),
      catchError(error => {
        console.error('Error al contar asistencias totales:', error);
        return of(0);
      })
    ).subscribe((total: number) => {
      this.totalAsistencias = total;
    });
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
