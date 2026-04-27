import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TrabajoSocialSubAreaComponent } from './components/trabajo-social-sub-area.component';
import { TrabajoSubareaActivityAttendanceRecordComponent } from './components/trabajo-subarea-activity-attendance-record/trabajo-subarea-activity-attendance-record.component';

const routes: Routes = [{ path: '', component: TrabajoSocialSubAreaComponent },
  { path: 'asistencia-actividad-subarea-trabajo-social', component: TrabajoSubareaActivityAttendanceRecordComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TrabajoSocialSubAreaRoutingModule { }
