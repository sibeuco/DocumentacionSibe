import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ManageDepartmentComponent } from './components/manage-department.component';

const routes: Routes = [{ path: '', component: ManageDepartmentComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ManageDepartmentRoutingModule { }
