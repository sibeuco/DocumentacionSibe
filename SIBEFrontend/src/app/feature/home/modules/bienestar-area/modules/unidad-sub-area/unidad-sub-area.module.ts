import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UnidadSubAreaRoutingModule } from './unidad-sub-area-routing.module';
import { UnidadSubAreaComponent } from './components/unidad-sub-area.component';
import { ActivitiesComponent } from './components/activities/activities.component';
import { FiltersComponent } from './components/filters/filters.component';
import { TopDataComponent } from './components/top-data/top-data.component';
import { BottomDataComponent } from './components/bottom-data/bottom-data.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { FormsModule } from '@angular/forms';
import { UnidadSubareaActivityAttendanceRecordComponent } from './components/unidad-subarea-activity-attendance-record/unidad-subarea-activity-attendance-record.component';


@NgModule({
  declarations: [
    UnidadSubAreaComponent,
    ActivitiesComponent,
    FiltersComponent,
    TopDataComponent,
    BottomDataComponent,
    UnidadSubareaActivityAttendanceRecordComponent
  ],
  imports: [
    CommonModule,
    UnidadSubAreaRoutingModule,
    SharedModule,
    FormsModule
  ]
})
export class UnidadSubAreaModule { }
