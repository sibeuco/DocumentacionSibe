import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BandaSubAreaRoutingModule } from './banda-sub-area-routing.module';
import { BandaSubAreaComponent } from './components/banda-sub-area.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { ActivitiesComponent } from './components/activities/activities.component';
import { FiltersComponent } from './components/filters/filters.component';
import { TopDataComponent } from './components/top-data/top-data.component';
import { BottomDataComponent } from './components/bottom-data/bottom-data.component';
import { FormsModule } from '@angular/forms';
import { BandaSubareaActivityAttendanceRecordComponent } from './components/banda-subarea-activity-attendance-record/banda-subarea-activity-attendance-record.component';


@NgModule({
  declarations: [
    BandaSubAreaComponent,
    ActivitiesComponent,
    FiltersComponent,
    TopDataComponent,
    BottomDataComponent,
    BandaSubareaActivityAttendanceRecordComponent
  ],
  imports: [
    CommonModule,
    BandaSubAreaRoutingModule,
    SharedModule,
    FormsModule
  ]
})
export class BandaSubAreaModule { }
