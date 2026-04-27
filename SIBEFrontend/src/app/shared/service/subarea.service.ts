import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HttpService } from 'src/app/core/service/http.service';
import { environment } from 'src/environments/environment';
import { SubAreaDetailResponse, SubAreaResponse } from '../model/subarea.model';

@Injectable({
    providedIn: 'root'
})
export class SubAreaService extends HttpService {
    private readonly SUBAREA_ENDPOINT = '/subareas';
    private readonly SUBAREA_NAME_ENDPOINT = '/subareas/nombre';
    private readonly SUBAREA_DETAIL_ENDPOINT = '/subareas/detalle';

    constructor(http: HttpClient) {
        super(http);
    }

    consultarSubareas(): Observable<SubAreaResponse[]> {
        const opts = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token') || ''}`
            })
        };
        const url = `${environment.endpoint}${this.SUBAREA_ENDPOINT}`;
        return this.http.get<SubAreaResponse[]>(url, opts);
    }

    consultarPorNombre(nombre: string): Observable<SubAreaResponse> {
        const opts = this.createDefaultOptions();
        const url = `${environment.endpoint}${this.SUBAREA_NAME_ENDPOINT}/${nombre}`;

        return this.doGet<SubAreaResponse>(url, opts);
    }

    consultarDetalle(identificador: string): Observable<SubAreaDetailResponse> {
        const opts = this.createDefaultOptions();
        const url = `${environment.endpoint}${this.SUBAREA_DETAIL_ENDPOINT}/${identificador}`;

        return this.doGet<SubAreaDetailResponse>(url, opts);
    }
}