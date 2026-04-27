import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EvangelizacionAreaComponent } from './components/evangelizacion-area.component';
import { EvangelizacionAreaActivityAttendanceRecordComponent } from './components/evangelizacion-area-activity-attendance-record/evangelizacion-area-activity-attendance-record.component';

const routes: Routes = [{ path: '', component: EvangelizacionAreaComponent },
  { path: 'asistencia-actividad-area-evangelizacion',component: EvangelizacionAreaActivityAttendanceRecordComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EvangelizacionAreaRoutingModule { }
