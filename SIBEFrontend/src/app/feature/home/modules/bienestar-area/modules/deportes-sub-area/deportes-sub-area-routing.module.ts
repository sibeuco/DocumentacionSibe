import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DeportesSubAreaComponent } from './components/deportes-sub-area.component';
import { DeportesSubareaActivityAttendanceRecordComponent } from './components/deportes-subarea-activity-attendance-record/deportes-subarea-activity-attendance-record.component';

const routes: Routes = [{ path: '', component: DeportesSubAreaComponent },
  { path: 'asistencia-actividad-subarea-deportes', component: DeportesSubareaActivityAttendanceRecordComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DeportesSubAreaRoutingModule { }
