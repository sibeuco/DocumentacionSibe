import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { PasswordRecoveryRoutingModule } from './password-recovery-routing.module';
import { PasswordRecoveryComponent } from './components/password-recovery.component';


@NgModule({
  declarations: [
    PasswordRecoveryComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    PasswordRecoveryRoutingModule
  ]
})
export class PasswordRecoveryModule { }
