import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { HttpService } from 'src/app/core/service/http.service';
import { UserResponse, UserRequest, EditUserRequest } from '../model/user.model';
import { Response } from '../model/response.model';
import { environment } from 'src/environments/environment';
import { EditPasswordRequest } from '../model/password.model';

@Injectable({
  providedIn: 'root'
})
export class UserService extends HttpService {
  private readonly USER_ENDPOINT = '/usuarios';
  private readonly  USER_ID_ENDPOINT = '/usuarios/usuario/id';
  private readonly EDIT_PASSWORD_ENDPOINT = '/usuarios/modificar/clave';

  constructor(http: HttpClient) {
    super(http);
  }

  consultarUsuarioPorIdentificador(identificador: string): Observable<UserResponse> {
    const opts = this.createDefaultOptions();
    const url = `${environment.endpoint}${this.USER_ID_ENDPOINT}/${identificador}`;

    return this.doGet<UserResponse>(url, opts);
  }

  consultarUsuarios(): Observable<UserResponse[]> {
    const opts = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token') || ''}`
      })
    };
    const url = `${environment.endpoint}${this.USER_ENDPOINT}`;
    return this.http.get<UserResponse[]>(url, opts);
  }

  consultarUsuariosPorTipo(tipoUsuario: string, pagina: number = 0, tamano: number = 10, excluir: boolean = false): Observable<any> {
    const opts = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token') || ''}`
      })
    };
    const params = new HttpParams()
        .set('tipoUsuario', tipoUsuario)
        .set('pagina', pagina.toString())
        .set('tamano', tamano.toString())
        .set('excluir', excluir.toString());
    const url = `${environment.endpoint}${this.USER_ENDPOINT}/paginado`;
    return this.doGetParameters<any>(url, params, opts);
  }

  consultarUsuarioPorCorreo(correo: string): Observable<UserResponse> {
    const opts = this.createDefaultOptions();
    const url = `${environment.endpoint}${this.USER_ENDPOINT}/usuario/correo/${correo}`;

    return this.doGet<UserResponse>(url, opts);
  }

  agregarNuevoUsuario(usuario: UserRequest): Observable<Response<string>> {
    const opts = this.createDefaultOptions();
    const url = `${environment.endpoint}${this.USER_ENDPOINT}`;

    return this.doPost<UserRequest, Response<string>>(url, usuario, opts);
  }

  modificarUsuario(identificador: string, usuario: EditUserRequest): Observable<Response<string>> {
    const opts = this.createDefaultOptions();
    const url = `${environment.endpoint}${this.USER_ENDPOINT}/usuario/${identificador}`;

    return this.doPut<EditUserRequest, Response<string>>(url, usuario, opts);
  }

  eliminarUsuario(identificador: string): Observable<Response<string>> {
    const opts = this.createDefaultOptions();
    const url = `${environment.endpoint}${this.USER_ENDPOINT}/usuario/${identificador}`;

    return this.doDelete<Response<string>>(url, opts);
  }

  modificarClave(request: EditPasswordRequest): Observable<{ valor: string }> {
    const url = `${environment.endpoint}${this.EDIT_PASSWORD_ENDPOINT}`;

    // Obtener el token del sessionStorage
    const token = window.sessionStorage.getItem('Authorization');

    const opts = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token || ''}`
      })
    };

    return this.http.put<{ valor: string }>(url, request, opts).pipe(
      catchError(error => {
        console.error('Error al modificar la clave:', error);
        return throwError(() => error);
      })
    );
  }

}
