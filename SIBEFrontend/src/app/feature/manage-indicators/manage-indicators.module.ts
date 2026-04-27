import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ManageIndicatorsRoutingModule } from './manage-indicators-routing.module';
import { ManageIndicatorsComponent } from './components/manage-indicators.component';
import { IndicatorsComponent } from './components/indicators/indicators.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { FormsModule } from '@angular/forms';
import { RegisterNewIndicatorComponent } from './components/register-new-indicator/register-new-indicator.component';
import { ProjectsComponent } from './components/projects/projects.component';
import { RegisterNewProjectComponent } from './components/register-new-project/register-new-project.component';
import { EditIndicatorComponent } from './components/edit-indicator/edit-indicator.component';
import { EditProjectComponent } from './components/edit-project/edit-project.component';
import { ActionsComponent } from './components/actions/actions.component';
import { RegisterNewActionComponent } from './components/register-new-action/register-new-action.component';
import { EditActionComponent } from './components/edit-action/edit-action.component';
import { NgxPaginationModule } from 'ngx-pagination';


@NgModule({
  declarations: [
    ManageIndicatorsComponent,
    IndicatorsComponent,
    RegisterNewIndicatorComponent,
    ProjectsComponent,
    RegisterNewProjectComponent,
    EditIndicatorComponent,
    EditProjectComponent,
    ActionsComponent,
    RegisterNewActionComponent,
    EditActionComponent
  ],
  imports: [
    CommonModule,
    ManageIndicatorsRoutingModule,
    SharedModule,
    FormsModule,
    NgxPaginationModule
  ]
})
export class ManageIndicatorsModule { }
