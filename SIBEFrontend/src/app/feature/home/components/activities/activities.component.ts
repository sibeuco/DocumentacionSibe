import { Component, ViewChild, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { StateService } from 'src/app/shared/service/state.service';
import { StateProps } from 'src/app/shared/model/state.enum';
import { UserSession } from 'src/app/feature/login/model/user-session.model';
import { ActivityResponse} from 'src/app/shared/model/activity.model';
import { ActivitiesTableComponent } from 'src/app/shared/components/activities-table/activities-table.component';

@Component({
  selector: 'app-activities',
  templateUrl: './activities.component.html',
  styleUrls: ['./activities.component.scss']
})
export class ActivitiesComponent implements AfterViewInit {

  terminoBusqueda: string = '';
  nombreArea: string = 'Dirección de Bienestar y Evangelización';
  tipoEstructura: 'direccion' | 'area' | 'subarea' = 'direccion';

  @ViewChild(ActivitiesTableComponent) activitiesTable!: ActivitiesTableComponent;

  get esColaborador(): boolean {
    const session = this.stateService.getState(StateProps.USER_SESSION) as UserSession;
    const rolesPermitidos = ['ADMINISTRADOR_DIRECCION', 'ADMINISTRADOR_AREA'];
    return session ? !rolesPermitidos.includes(session.rol) : false;
  }

  constructor(private router: Router, private stateService: StateService) {}

  ngAfterViewInit(): void {
  }

  onActividadSeleccionada(actividad: ActivityResponse): void {
    console.log('Actividad seleccionada:', actividad);

    console.log(`Procesando actividad: ${actividad.nombre}`);
  }

  onBuscarActividad(termino: string): void {
    this.terminoBusqueda = termino;
    console.log('Buscando actividades con término:', termino);
  }

  onActividadCreada(): void {
    if (this.activitiesTable) {
      this.activitiesTable.recargarActividades();
    }
  }

}
