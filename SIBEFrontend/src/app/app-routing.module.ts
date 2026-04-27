import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeModule } from './feature/home/home.module';
import { securityGuard } from './core/guard/security.guard';
import { publicRouteGuard } from './core/guard/public-route.guard';

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', loadChildren: () => import('./feature/login/login.module').then(m => m.LoginModule), canActivate: [publicRouteGuard]},
  { path: 'home', loadChildren: () => import('./feature/home/home.module').then(m => m.HomeModule), canActivate: [securityGuard]},
  { path: 'gestionar-direccion', loadChildren: () => import('./feature/manage-department/manage-department.module').then(m => m.ManageDepartmentModule), canActivate: [securityGuard]},
  { path: 'gestionar-usuarios', loadChildren: () => import('./feature/manage-users/manage-users.module').then(m => m.ManageUsersModule), canActivate: [securityGuard]},
  { path: 'gestionar-indicadores', loadChildren: () => import('./feature/manage-indicators/manage-indicators.module').then(m => m.ManageIndicatorsModule), canActivate: [securityGuard]},
  { path: 'recuperar-contrasena', loadChildren: () => import('./feature/password-recovery/password-recovery.module').then(m => m.PasswordRecoveryModule), canActivate: [publicRouteGuard]},
  { path: '**', redirectTo: 'login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    scrollPositionRestoration: 'top',
    anchorScrolling: 'enabled'
  })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
