import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CodeRequest, RestorePasswordRequest } from '../model/password-recovery.model';

@Injectable({
    providedIn: 'root'
})
export class PasswordRecoveryService {
    private readonly SOLICITAR_CODIGO_ENDPOINT = '/usuarios/recuperacion/solicitar';
    private readonly VALIDAR_CODIGO_ENDPOINT = '/usuarios/recuperacion/validarCodigo';
    private readonly RECUPERAR_CLAVE_ENDPOINT = '/usuarios/recuperacion/recuperarClave';

    constructor(private http: HttpClient) { }

    solicitarCodigo(correo: string): Observable<{ valor: string }> {
        const url = `${environment.endpoint}${this.SOLICITAR_CODIGO_ENDPOINT}/${correo}`;

        const opts = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json'
            })
        };

        return this.http.post<{ valor: string }>(url, {}, opts).pipe(
            catchError(error => {
                console.error('Error al solicitar el código de recuperación:', error);
                return throwError(() => error);
            })
        );

    }

    validarCodigo(codeRequest: CodeRequest): Observable<{ valor: boolean }> {
        const url = `${environment.endpoint}${this.VALIDAR_CODIGO_ENDPOINT}`;

        const opts = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json'
            })
        };

        return this.http.post<{ valor: boolean }>(url, codeRequest, opts).pipe(
            catchError(error => {
                console.error('Error al validar el código de recuperación:', error);
                return throwError(() => error);
            })
        );
    }

    recuperarClave(request: RestorePasswordRequest): Observable<{ valor: string }> {
        const url = `${environment.endpoint}${this.RECUPERAR_CLAVE_ENDPOINT}`;

        const opts = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json'
            })
        };

        return this.http.put<{ valor: string }>(url, request, opts).pipe(
            catchError(error => {
                console.error('Error al recuperar la clave:', error);
                return throwError(() => error);
            })
        );
    }

}