import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HttpService } from 'src/app/core/service/http.service';
import { IndicatorTypeResponse } from '../model/indicator-type.model';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class IndicatorTypeService extends HttpService {
    private readonly INDICATOR_TYPE_ENDPOINT = '/tipos_indicador';

    constructor(http: HttpClient) {
        super(http);
    }

    consultarTiposIndicador(): Observable<IndicatorTypeResponse[]> {
        const opts = {
                    headers: new HttpHeaders({
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${localStorage.getItem('token') || ''}`
                    })
                };
                const url = `${environment.endpoint}${this.INDICATOR_TYPE_ENDPOINT}`;
                return this.http.get<IndicatorTypeResponse[]>(url, opts);
    }
}