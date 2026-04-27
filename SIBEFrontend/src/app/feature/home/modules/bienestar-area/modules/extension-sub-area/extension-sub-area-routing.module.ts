import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ExtensionSubAreaComponent } from './components/extension-sub-area.component';
import { ExtensionSubareaActivityAttendanceRecordComponent } from './components/extension-subarea-activity-attendance-record/extension-subarea-activity-attendance-record.component';

const routes: Routes = [{ path: '', component: ExtensionSubAreaComponent },
  { path: 'asistencia-actividad-subarea-extension', component: ExtensionSubareaActivityAttendanceRecordComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ExtensionSubAreaRoutingModule { }
