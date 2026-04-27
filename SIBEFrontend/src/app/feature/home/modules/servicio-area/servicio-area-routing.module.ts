import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ServicioAreaComponent } from './components/servicio-area.component';
import { ServicioAreaActivityAttendanceRecordComponent } from './components/servicio-area-activity-attendance-record/servicio-area-activity-attendance-record.component';

const routes: Routes = [{ path: '', component: ServicioAreaComponent },
  { path: 'asistencia-actividad-area-servicio', component: ServicioAreaActivityAttendanceRecordComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ServicioAreaRoutingModule { }
