import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BienestarAreaComponent } from './components/bienestar-area.component';
import { BienestarAreaActivityAttendanceRecordComponent } from './components/bienestar-area-activity-attendance-record/bienestar-area-activity-attendance-record.component';

const routes: Routes = [{ path: '', component: BienestarAreaComponent},
  { path: 'sub-area-deportes', loadChildren: () => import('./modules/deportes-sub-area/deportes-sub-area.module').then(m => m.DeportesSubAreaModule) },
  { path: 'sub-area-cancha-sintetica', loadChildren: () => import('./modules/cancha-sub-area/cancha-sub-area.module').then(m => m.CanchaSubAreaModule) },
  { path: 'sub-area-gimnasio', loadChildren: () => import('./modules/gimnasio-sub-area/gimnasio-sub-area.module').then(m => m.GimnasioSubAreaModule) },
  { path: 'sub-area-extension-cultural', loadChildren: () => import('./modules/extension-sub-area/extension-sub-area.module').then(m => m.ExtensionSubAreaModule) },
  { path: 'sub-area-banda-sinfonica', loadChildren: () => import('./modules/banda-sub-area/banda-sub-area.module').then(m => m.BandaSubAreaModule) },
  { path: 'sub-area-unidad-de-salud', loadChildren: () => import('./modules/unidad-sub-area/unidad-sub-area.module').then(m => m.UnidadSubAreaModule) },
  { path: 'sub-area-acompanamiento-psicosocial', loadChildren: () => import('./modules/acompanamiento-sub-area/acompanamiento-sub-area.module').then(m => m.AcompanamientoSubAreaModule) },
  { path: 'sub-area-trabajo-social', loadChildren: () => import('./modules/trabajo-social-sub-area/trabajo-social-sub-area.module').then(m => m.TrabajoSocialSubAreaModule) },
  { path: 'asistencia-actividad-area-bienestar', component: BienestarAreaActivityAttendanceRecordComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BienestarAreaRoutingModule { }
