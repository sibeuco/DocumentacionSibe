import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HttpService } from 'src/app/core/service/http.service';
import { environment } from 'src/environments/environment';
import { FrequencyResponse } from '../model/frequency.model';

@Injectable({
    providedIn: 'root'
})
export class FrequencyService extends HttpService {
    private readonly FREQUENCY_ENDPOINT = '/temporalidades';

    constructor(http: HttpClient) {
        super(http);
    }

    consultarTemporalidades(): Observable<FrequencyResponse[]> {
        const opts = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token') || ''}`
            })
        };
        const url = `${environment.endpoint}${this.FREQUENCY_ENDPOINT}`;
        return this.http.get<FrequencyResponse[]>(url, opts);
    }
}