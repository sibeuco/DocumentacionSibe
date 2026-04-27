import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AcompanamientoSubAreaComponent } from './components/acompanamiento-sub-area.component';
import { AcompanamientoSubareaActivityAttendanceRecordComponent } from './components/acompanamiento-activity-attendance-record/acompanamiento-subarea-activity-attendance-record.component';

const routes: Routes = [{ path: '', component: AcompanamientoSubAreaComponent },
  { path: 'asistencia-actividad-subarea-acompanamiento', component: AcompanamientoSubareaActivityAttendanceRecordComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AcompanamientoSubAreaRoutingModule { }
