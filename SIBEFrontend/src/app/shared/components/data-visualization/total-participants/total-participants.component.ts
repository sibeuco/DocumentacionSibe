import { Component, Input, OnChanges, SimpleChanges, OnInit } from '@angular/core';
import { ActivityService } from 'src/app/shared/service/activity.service';
import { DepartmentService } from 'src/app/shared/service/department.service';
import { AreaService } from 'src/app/shared/service/area.service';
import { SubAreaService } from 'src/app/shared/service/subarea.service';
import { FiltersRequestWithoutArea } from 'src/app/shared/model/filters.model';
import { FiltersRequest } from 'src/app/shared/model/filters.model';
import { Observable, of, forkJoin } from 'rxjs';
import { switchMap, catchError } from 'rxjs/operators';

const INDICADOR_COBERTURA = 'Cobertura';

@Component({
  selector: 'app-total-participants',
  templateUrl: './total-participants.component.html',
  styleUrls: ['./total-participants.component.scss']
})
export class TotalParticipantsComponent implements OnInit, OnChanges {
  @Input() imageUrl: string = 'assets/images/home-college.png';
  @Input() tipoEstructura: 'DIRECCION' | 'AREA' | 'SUBAREA' = 'DIRECCION';
  @Input() nombreArea: string = '';
  @Input() filtersRequest: FiltersRequestWithoutArea | null = null;

  totalParticipantes: number = 0;
  totalAsistencias: number = 0;
  esCobertura: boolean = false;
  porcentajeCobertura: number = 0;
  poblacionTotal: number = 0;

  constructor(
    private activityService: ActivityService,
    private departmentService: DepartmentService,
    private areaService: AreaService,
    private subAreaService: SubAreaService
  ) {}

  ngOnInit(): void {
    this.loadStatistics();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['filtersRequest'] || changes['tipoEstructura'] || changes['nombreArea']) {
      this.loadStatistics();
    }
  }

  private loadStatistics(): void {
    this.esCobertura = this.filtersRequest?.indicador?.toLowerCase().trim() === INDICADOR_COBERTURA.toLowerCase();

    if (this.esCobertura) {
      this.loadCobertura();
    } else {
      this.loadTotalParticipants();
      this.loadTotalAsistencias();
    }
  }

  private loadCobertura(): void {
    if (!this.filtersRequest || !this.nombreArea || !this.tipoEstructura) {
      this.porcentajeCobertura = 0;
      this.poblacionTotal = 0;
      this.totalParticipantes = 0;
      return;
    }

    this.getAreaIdentifier().pipe(
      switchMap((idArea: string | null) => {
        if (!idArea) {
          return of({ participantes: 0, poblacion: 0 });
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
          participantes: this.activityService.contarParticipantesTotales(filtersRequest),
          poblacion: this.activityService.contarPoblacionTotal(filtersRequestPoblacion)
        });
      }),
      catchError(() => of({ participantes: 0, poblacion: 0 }))
    ).subscribe((result: { participantes: number; poblacion: number }) => {
      this.totalParticipantes = result.participantes;
      this.poblacionTotal = result.poblacion;
      this.porcentajeCobertura = result.poblacion > 0
        ? Math.round((result.participantes / result.poblacion) * 10000) / 100
        : 0;
    });
  }

  private loadTotalParticipants(): void {
    if (!this.filtersRequest || !this.nombreArea || !this.tipoEstructura) {
      this.totalParticipantes = 0;
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
    if (!this.filtersRequest || !this.nombreArea || !this.tipoEstructura) {
      this.totalAsistencias = 0;
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
