import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HomeRoutingModule } from './home-routing.module';
import { HomeComponent } from './components/home.component';
import { PrincipalHomeComponent } from './components/principal-home/principal-home.component';
import { HomePrimaryButtonsComponent } from './components/home-primary-buttons/home-primary-buttons.component';
import { AreasComponent } from './components/areas/areas.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { HomeFiltersComponent } from './components/home-filters/home-filters.component';
import { TopDataContainerComponent } from './components/top-data-container/top-data-container.component';
import { BottonDataContainerComponent } from './components/botton-data-container/botton-data-container.component';
import { FormsModule } from '@angular/forms';
import { ActivitiesComponent } from './components/activities/activities.component';
import { DepartmentAttendanceRecordComponent } from './components/department-attendance-record/department-attendance-record.component';


@NgModule({
  declarations: [
    HomeComponent,
    PrincipalHomeComponent,
    HomePrimaryButtonsComponent,
    AreasComponent,
    HomeFiltersComponent,
    TopDataContainerComponent,
    BottonDataContainerComponent,
    ActivitiesComponent,
    DepartmentAttendanceRecordComponent
  ],
  imports: [
    CommonModule,
    HomeRoutingModule,
    SharedModule,
    FormsModule
  ]
})
export class HomeModule { }
