import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GimnasioSubAreaComponent } from './components/gimnasio-sub-area.component';
import { GimnasioSubareaActivityAttendanceRecordComponent } from './components/gimnasio-subarea-activity-attendance-record/gimnasio-subarea-activity-attendance-record.component';

const routes: Routes = [{ path: '', component: GimnasioSubAreaComponent },
  { path: 'asistencia-actividad-subarea-gimnasio', component: GimnasioSubareaActivityAttendanceRecordComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GimnasioSubAreaRoutingModule { }
