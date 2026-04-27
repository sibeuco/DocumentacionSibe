import { ErrorHandler, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FooterComponent } from './components/footer/footer.component';
import { HeaderComponent } from './components/header/header.component';
import { HttpService } from './service/http.service';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AuthInterceptor } from './interceptor/auth-interceptor';
import { ManejadorError } from './interceptor/manejador-error';
import { TokenInterceptor } from './interceptor/token-interceptor';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/compiler';
import { AppRoutingModule } from '../app-routing.module';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [
    FooterComponent,
    HeaderComponent
  ],
  exports: [
    FooterComponent,
    HeaderComponent
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    AppRoutingModule,
    SharedModule
  ],
  providers:[
    HttpService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
    {
      provide: ErrorHandler,
      useClass: ManejadorError
    }
  ]
})
export class CoreModule { }
