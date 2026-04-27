import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BandaSubAreaComponent } from './components/banda-sub-area.component';
import { BandaSubareaActivityAttendanceRecordComponent } from './components/banda-subarea-activity-attendance-record/banda-subarea-activity-attendance-record.component';

const routes: Routes = [{ path: '', component: BandaSubAreaComponent },
  { path: 'asistencia-actividad-subarea-banda', component: BandaSubareaActivityAttendanceRecordComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BandaSubAreaRoutingModule { }
