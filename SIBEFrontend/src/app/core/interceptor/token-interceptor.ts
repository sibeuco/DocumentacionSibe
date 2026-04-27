import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpHandler, HttpRequest, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';


@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(private cookieService: CookieService, private router: Router) { }

  intercept<T, R>(req: HttpRequest<T>, next: HttpHandler): Observable<HttpEvent<R>> {
    const token = this.cookieService.get('token');

    // 🔍 Verificar si el token existe y si está vencido
    if (token && this.isTokenExpired(token)) {
      console.warn('⚠️ Token expirado, cerrando sesión automáticamente');
      this.logout();
      this.router.navigate(['/login']);
      // Cancelamos la petición retornando un observable vacío
      return throwError(() => new Error('Token expirado'));
    }

    // Agregamos el token si existe y no está vencido
    if (token) {
      const authReq = req.clone({
        headers: req.headers.set('Authorization', `Bearer ${token}`)
      });
      return next.handle(authReq).pipe(
        catchError((error: HttpErrorResponse) => {
          if (error.status === 401) {
            // Token inválido o expirado
            this.logout();
            this.router.navigate(['/login']);
          }
          return throwError(() => error);
        })
      );
    }

    return next.handle(req);
  }

  // 🧩 Decodificar el JWT y verificar expiración
  private isTokenExpired(token: string): boolean {
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      const exp = payload.exp * 1000; // Convertir a milisegundos
      return Date.now() > exp;
    } catch (e) {
      console.error('Error al decodificar el token', e);
      return true;
    }
  }

  // 🚪 Método para cerrar sesión
  private logout(): void {
    this.cookieService.delete('token');
    sessionStorage.clear();
    localStorage.clear();
  }
}