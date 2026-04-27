import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { HttpService } from "src/app/core/service/http.service";
import { environment } from "src/environments/environment";
import { IdentificationTypeResponse } from "../model/identification-type.model";

@Injectable({
  providedIn: 'root'
})

export class IdentificationTypeService extends HttpService{
    private readonly USUARIO_ENDPOINT = '/tipos_identificacion';

    constructor(http: HttpClient) {
        super(http);
    }

    consultarTipoIdentificacion(): Observable<IdentificationTypeResponse[]> {
        const opts = {
          headers: new HttpHeaders({
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token') || ''}`
          })
        };
        const url = `${environment.endpoint}${this.USUARIO_ENDPOINT}`;
        return this.http.get<IdentificationTypeResponse[]>(url, opts);
    }
}