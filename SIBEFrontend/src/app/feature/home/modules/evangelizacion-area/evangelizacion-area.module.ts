import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EvangelizacionAreaRoutingModule } from './evangelizacion-area-routing.module';
import { EvangelizacionAreaComponent } from './components/evangelizacion-area.component';
import { ActivitiesComponent } from './components/activities/activities.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { AreaFiltersComponent } from './components/area-filters/area-filters.component';
import { TopDataContainerComponent } from './components/top-data-container/top-data-container.component';
import { BottomDataContainerComponent } from './components/bottom-data-container/bottom-data-container.component';
import { FormsModule } from '@angular/forms';
import { EvangelizacionAreaActivityAttendanceRecordComponent } from './components/evangelizacion-area-activity-attendance-record/evangelizacion-area-activity-attendance-record.component';


@NgModule({
  declarations: [
    EvangelizacionAreaComponent,
    ActivitiesComponent,
    AreaFiltersComponent,
    TopDataContainerComponent,
    BottomDataContainerComponent,
    EvangelizacionAreaActivityAttendanceRecordComponent
  ],
  imports: [
    CommonModule,
    EvangelizacionAreaRoutingModule,
    SharedModule,
    FormsModule
  ]
})
export class EvangelizacionAreaModule { }
