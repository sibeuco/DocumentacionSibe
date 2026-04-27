import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UnidadSubAreaComponent } from './components/unidad-sub-area.component';
import { UnidadSubareaActivityAttendanceRecordComponent } from './components/unidad-subarea-activity-attendance-record/unidad-subarea-activity-attendance-record.component';

const routes: Routes = [{ path: '', component: UnidadSubAreaComponent },
  { path: 'asistencia-actividad-subarea-unidad', component: UnidadSubareaActivityAttendanceRecordComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UnidadSubAreaRoutingModule { }
