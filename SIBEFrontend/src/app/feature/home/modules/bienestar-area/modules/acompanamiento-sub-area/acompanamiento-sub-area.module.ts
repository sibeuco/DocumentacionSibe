import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AcompanamientoSubAreaRoutingModule } from './acompanamiento-sub-area-routing.module';
import { AcompanamientoSubAreaComponent } from './components/acompanamiento-sub-area.component';
import { ActivitiesComponent } from './components/activities/activities.component';
import { FiltersComponent } from './components/filters/filters.component';
import { TopDataComponent } from './components/top-data/top-data.component';
import { BottomDataComponent } from './components/bottom-data/bottom-data.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { FormsModule } from '@angular/forms';
import { ActivitiesTableComponent } from 'src/app/shared/components/activities-table/activities-table.component';
import { AcompanamientoSubareaActivityAttendanceRecordComponent } from './components/acompanamiento-activity-attendance-record/acompanamiento-subarea-activity-attendance-record.component';


@NgModule({
  declarations: [
    AcompanamientoSubAreaComponent,
    ActivitiesComponent,
    FiltersComponent,
    TopDataComponent,
    BottomDataComponent,
    AcompanamientoSubareaActivityAttendanceRecordComponent
  ],
  imports: [
    CommonModule,
    AcompanamientoSubAreaRoutingModule,
    SharedModule,
    FormsModule
  ]
})
export class AcompanamientoSubAreaModule { }
