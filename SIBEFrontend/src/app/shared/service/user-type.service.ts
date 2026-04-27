import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { HttpService } from "src/app/core/service/http.service";
import { UserTypeResponse } from "../model/user-type.model";
import { environment } from "src/environments/environment";

@Injectable({
  providedIn: 'root'
})

export class UserTypeService extends HttpService{
    private readonly USUARIO_ENDPOINT = '/tipos_usuario';

    constructor(http: HttpClient) {
        super(http);
    }

    consultarTipoUsuario(): Observable<UserTypeResponse[]> {
        const opts = {
          headers: new HttpHeaders({
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token') || ''}`
          })
        };
        const url = `${environment.endpoint}${this.USUARIO_ENDPOINT}`;
        return this.http.get<UserTypeResponse[]>(url, opts);
    }
}