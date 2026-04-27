import { Component, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { ActivityService } from 'src/app/shared/service/activity.service';
import { DepartmentService } from 'src/app/shared/service/department.service';
import { AreaService } from 'src/app/shared/service/area.service';
import { SubAreaService } from 'src/app/shared/service/subarea.service';
import { FiltersRequest, FiltersRequestWithoutArea } from 'src/app/shared/model/filters.model';
import { Observable, of } from 'rxjs';
import { switchMap, catchError } from 'rxjs/operators';

@Component({
  selector: 'app-completed-activities',
  templateUrl: './completed-activities.component.html',
  styleUrls: ['./completed-activities.component.scss']
})
export class CompletedActivitiesComponent implements OnInit, OnChanges {
  @Input() imageUrl: string = 'assets/images/actividades-bienestar.png';
  @Input() tipoEstructura: 'DIRECCION' | 'AREA' | 'SUBAREA' = 'DIRECCION';
  @Input() nombreArea: string = '';
  @Input() filtersRequest: FiltersRequestWithoutArea | null = null;

  totalActividades: number = 0;

  constructor(
    private activityService: ActivityService,
    private departmentService: DepartmentService,
    private areaService: AreaService,
    private subAreaService: SubAreaService
  ) {}

  ngOnInit(): void {
    this.loadCompletedActivities();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['filtersRequest'] || changes['tipoEstructura'] || changes['nombreArea']) {
      this.loadCompletedActivities();
    }
  }

  private loadCompletedActivities(): void {
    if (!this.filtersRequest || !this.nombreArea || !this.tipoEstructura) {
      this.totalActividades = 0;
      return;
    }

    // Obtener el identificador del área según el tipo de estructura
    this.getAreaIdentifier().pipe(
      switchMap((idArea: string | null) => {
        if (!idArea) {
          return of(0);
        }

        // Construir el FiltersRequest completo
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

        // Llamar al servicio
        return this.activityService.contarEjecucionesTotales(filtersRequest);
      }),
      catchError(error => {
        console.error('Error al contar actividades realizadas:', error);
        return of(0);
      })
    ).subscribe((total: number) => {
      this.totalActividades = total;
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
