import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HttpService } from 'src/app/core/service/http.service';
import { environment } from 'src/environments/environment';
import { ActionRequest, ActionResponse } from '../model/action.model';
import { Response } from 'src/app/shared/model/response.model';

@Injectable({
    providedIn: 'root'
})
export class ActionService extends HttpService {
    private readonly ACTION_ENDPOINT = '/acciones';

    constructor(http: HttpClient) {
        super(http);
    }

    consultarAcciones(pagina: number = 0, tamano: number = 10): Observable<any> {
        const opts = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token') || ''}`
            })
        };
        const params = new HttpParams()
            .set('pagina', pagina.toString())
            .set('tamano', tamano.toString());
        const url = `${environment.endpoint}${this.ACTION_ENDPOINT}`;
        return this.doGetParameters<any>(url, params, opts);
    }

    agregarNuevaAccion(accion: ActionRequest): Observable<Response<string>> {
        const opts = this.createDefaultOptions();
        const url = `${environment.endpoint}${this.ACTION_ENDPOINT}`;

        return this.doPost<ActionRequest, Response<string>>(url, accion, opts);
    }

    modificarAccion(identificador: string, accion: ActionRequest): Observable<{ valor: string }> {
        const opts = this.createDefaultOptions();
        const url = `${environment.endpoint}${this.ACTION_ENDPOINT}/${identificador}`;

        return this.http.put<{ valor: string }>(url, accion, opts);
    }
}
