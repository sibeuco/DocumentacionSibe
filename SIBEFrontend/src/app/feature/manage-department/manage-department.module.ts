import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ManageDepartmentRoutingModule } from './manage-department-routing.module';
import { ManageDepartmentComponent } from './components/manage-department.component';
import { DepartmentComponent } from './components/department/department.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { DepartmentAreasComponent } from './components/department-areas/department-areas.component';
import { BienestarAreaComponent } from './components/bienestar-area/bienestar-area.component';
import { EvangelizacionAreaComponent } from './components/evangelizacion-area/evangelizacion-area.component';
import { SantaMariaAreaComponent } from './components/santa-maria-area/santa-maria-area.component';
import { ServicioAreaComponent } from './components/servicio-area/servicio-area.component';
import { AreaStatisticsComponent } from './components/area-statistics/area-statistics.component';

@NgModule({
  declarations: [
    ManageDepartmentComponent,
    DepartmentComponent,
    DepartmentAreasComponent,
    BienestarAreaComponent,
    EvangelizacionAreaComponent,
    SantaMariaAreaComponent,
    ServicioAreaComponent,
    AreaStatisticsComponent
  ],
  imports: [
    CommonModule,
    ManageDepartmentRoutingModule,
    SharedModule
  ]
})
export class ManageDepartmentModule { }
