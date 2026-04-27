import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GimnasioSubAreaRoutingModule } from './gimnasio-sub-area-routing.module';
import { GimnasioSubAreaComponent } from './components/gimnasio-sub-area.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { ActivitiesComponent } from './components/activities/activities.component';
import { FiltersComponent } from './components/filters/filters.component';
import { TopDataComponent } from './components/top-data/top-data.component';
import { BottomDataComponent } from './components/bottom-data/bottom-data.component';
import { FormsModule } from '@angular/forms';
import { GimnasioSubareaActivityAttendanceRecordComponent } from './components/gimnasio-subarea-activity-attendance-record/gimnasio-subarea-activity-attendance-record.component';


@NgModule({
  declarations: [
    GimnasioSubAreaComponent,
    ActivitiesComponent,
    FiltersComponent,
    TopDataComponent,
    BottomDataComponent,
    GimnasioSubareaActivityAttendanceRecordComponent
  ],
  imports: [
    CommonModule,
    GimnasioSubAreaRoutingModule,
    SharedModule,
    FormsModule
  ]
})
export class GimnasioSubAreaModule { }
