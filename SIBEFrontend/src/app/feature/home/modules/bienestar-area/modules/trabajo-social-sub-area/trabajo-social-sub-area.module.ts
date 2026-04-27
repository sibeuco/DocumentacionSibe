import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TrabajoSocialSubAreaRoutingModule } from './trabajo-social-sub-area-routing.module';
import { TrabajoSocialSubAreaComponent } from './components/trabajo-social-sub-area.component';
import { ActivitiesComponent } from './components/activities/activities.component';
import { FiltersComponent } from './components/filters/filters.component';
import { TopDataComponent } from './components/top-data/top-data.component';
import { BottomDataComponent } from './components/bottom-data/bottom-data.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { FormsModule } from '@angular/forms';
import { TrabajoSubareaActivityAttendanceRecordComponent } from './components/trabajo-subarea-activity-attendance-record/trabajo-subarea-activity-attendance-record.component';


@NgModule({
  declarations: [
    TrabajoSocialSubAreaComponent,
    ActivitiesComponent,
    FiltersComponent,
    TopDataComponent,
    BottomDataComponent,
    TrabajoSubareaActivityAttendanceRecordComponent
  ],
  imports: [
    CommonModule,
    TrabajoSocialSubAreaRoutingModule,
    SharedModule,
    FormsModule
  ]
})
export class TrabajoSocialSubAreaModule { }
