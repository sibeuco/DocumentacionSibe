import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CanchaSubAreaComponent } from './components/cancha-sub-area.component';
import { CanchaSubareaActivityAttendanceRecordComponent } from './components/cancha-subarea-activity-attendance-record/cancha-subarea-activity-attendance-record.component';

const routes: Routes = [{ path: '', component: CanchaSubAreaComponent },
  { path: 'asistencia-actividad-subarea-cancha', component: CanchaSubareaActivityAttendanceRecordComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CanchaSubAreaRoutingModule { }
