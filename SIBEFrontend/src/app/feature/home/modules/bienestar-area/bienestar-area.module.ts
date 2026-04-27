import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BienestarAreaRoutingModule } from './bienestar-area-routing.module';
import { BienestarAreaComponent } from './components/bienestar-area.component';
import { ActivitiesComponent } from './components/activities/activities.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { AreaFiltersComponent } from './components/area-filters/area-filters.component';
import { TopDataContainerComponent } from './components/top-data-container/top-data-container.component';
import { BottonDataContainerComponent } from './components/botton-data-container/botton-data-container.component';
import { SubAreasComponent } from './components/sub-areas/sub-areas.component';
import { FormsModule } from '@angular/forms';
import { BienestarAreaActivityAttendanceRecordComponent } from './components/bienestar-area-activity-attendance-record/bienestar-area-activity-attendance-record.component';

@NgModule({
  declarations: [
    BienestarAreaComponent,
    ActivitiesComponent,
    AreaFiltersComponent,
    TopDataContainerComponent,
    BottonDataContainerComponent,
    SubAreasComponent,
    BienestarAreaActivityAttendanceRecordComponent
  ],
  imports: [
    CommonModule,
    BienestarAreaRoutingModule,
    SharedModule,
    FormsModule
  ]
})
export class BienestarAreaModule { }
