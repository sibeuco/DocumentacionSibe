import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpHandler, HttpRequest, HttpErrorResponse, HttpHeaders, HttpEvent, HttpResponse } from '@angular/common/http';
import { tap } from 'rxjs/operators';
import { Usuario } from '../model/usuario.model';

const UNAUTHORIZED = 401;

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  user?: Usuario;

  constructor() {}

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    let httpHeaders = new HttpHeaders();

    const userDetails = sessionStorage.getItem('userdetails');
    if (userDetails !== null) {
      this.user = JSON.parse(userDetails) as Usuario;
    } else {
      this.user = undefined;
    }

    if (this.user && this.user.contrasena && this.user.correo) {
      httpHeaders = httpHeaders.append('Authorization', 'Basic ' + window.btoa(this.user.correo + ':' + this.user.contrasena));
    } else {
      const authorization = sessionStorage.getItem('Authorization');
      if (authorization) {
        httpHeaders = httpHeaders.append('Authorization', authorization);
      }
    }

    this.user = undefined;
    sessionStorage.removeItem('userdetails');

    const xsrf = sessionStorage.getItem('XSRF-TOKEN');
    if (xsrf) {
      httpHeaders = httpHeaders.append('X-XSRF-TOKEN', xsrf);
    }

    httpHeaders = httpHeaders.append('X-Requested-With', 'XMLHttpRequest');

    const xhr = req.clone({
      headers: httpHeaders
    });

    return next.handle(xhr).pipe(tap(
      (response: HttpEvent<any>) => {
        if (response instanceof HttpResponse) {
          const authHeader = response.headers.get('Authorization');
          if (authHeader !== null) {
            window.sessionStorage.setItem('Authorization', authHeader);
          }
        }
      },
      (err: any) => {
        if (err instanceof HttpErrorResponse) {
          if (err.status !== UNAUTHORIZED) {
            return;
          }
        }
      }
    ));
  }
}