import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ManageIndicatorsComponent } from './components/manage-indicators.component';

const routes: Routes = [{ path: '', component: ManageIndicatorsComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ManageIndicatorsRoutingModule { }
