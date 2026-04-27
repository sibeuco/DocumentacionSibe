import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ServicioAreaRoutingModule } from './servicio-area-routing.module';
import { ServicioAreaComponent } from './components/servicio-area.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { ActivitiesComponent } from './components/activities/activities.component';
import { AreaFiltersComponent } from './components/area-filters/area-filters.component';
import { TopDataContainerComponent } from './components/top-data-container/top-data-container.component';
import { BottomDataContainerComponent } from './components/bottom-data-container/bottom-data-container.component';
import { FormsModule } from '@angular/forms';
import { ServicioAreaActivityAttendanceRecordComponent } from './components/servicio-area-activity-attendance-record/servicio-area-activity-attendance-record.component';


@NgModule({
  declarations: [
    ServicioAreaComponent,
    ActivitiesComponent,
    AreaFiltersComponent,
    TopDataContainerComponent,
    BottomDataContainerComponent,
    ServicioAreaActivityAttendanceRecordComponent
  ],
  imports: [
    CommonModule,
    ServicioAreaRoutingModule,
    SharedModule,
    FormsModule
  ]
})
export class ServicioAreaModule { }
