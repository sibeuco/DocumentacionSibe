import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HogarAreaComponent } from './components/hogar-area.component';
import { HogarAreaActivityAttendanceRecordComponent } from './components/hogar-area-activity-attendance-record/hogar-area-activity-attendance-record.component';

const routes: Routes = [{ path: '', component: HogarAreaComponent },
  { path: 'asistencia-actividad-area-hogar', component: HogarAreaActivityAttendanceRecordComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HogarAreaRoutingModule { }
