import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ExtensionSubAreaRoutingModule } from './extension-sub-area-routing.module';
import { ExtensionSubAreaComponent } from './components/extension-sub-area.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { ActivitiesComponent } from './components/activities/activities.component';
import { FiltersComponent } from './components/filters/filters.component';
import { TopDataComponent } from './components/top-data/top-data.component';
import { BottomDataComponent } from './components/bottom-data/bottom-data.component';
import { FormsModule } from '@angular/forms';
import { ExtensionSubareaActivityAttendanceRecordComponent } from './components/extension-subarea-activity-attendance-record/extension-subarea-activity-attendance-record.component';


@NgModule({
  declarations: [
    ExtensionSubAreaComponent,
    ActivitiesComponent,
    FiltersComponent,
    TopDataComponent,
    BottomDataComponent,
    ExtensionSubareaActivityAttendanceRecordComponent
  ],
  imports: [
    CommonModule,
    ExtensionSubAreaRoutingModule,
    SharedModule,
    FormsModule
  ]
})
export class ExtensionSubAreaModule { }
