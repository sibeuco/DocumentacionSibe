import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home.component';
import { DepartmentAttendanceRecordComponent } from './components/department-attendance-record/department-attendance-record.component';

const routes: Routes = [{ path: '', component: HomeComponent },
  { path: 'area-bienestar', loadChildren: () => import('./modules/bienestar-area/bienestar-area.module').then(m => m.BienestarAreaModule) },
  { path: 'area-evangelizacion', loadChildren: () => import('./modules/evangelizacion-area/evangelizacion-area.module').then(m => m.EvangelizacionAreaModule) },
  { path: 'area-hogar-santa-maria', loadChildren: () => import('./modules/hogar-area/hogar-area.module').then(m => m.HogarAreaModule) },
  { path: 'area-servicio-atencion', loadChildren: () => import('./modules/servicio-area/servicio-area.module').then(m => m.ServicioAreaModule) },
  { path: 'asistencia-actividad-direccion', component: DepartmentAttendanceRecordComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HomeRoutingModule { }
