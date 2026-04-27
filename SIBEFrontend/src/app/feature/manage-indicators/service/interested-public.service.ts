import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HttpService } from 'src/app/core/service/http.service';
import { environment } from 'src/environments/environment';
import { InterestedPublicResponse } from '../model/interested-public.model';

@Injectable({
    providedIn: 'root'
})
export class InterestedPublicService extends HttpService {
    private readonly INTERESTED_PUBLIC_ENDPOINT = '/publicos_interes';

    constructor(http: HttpClient) {
        super(http);
    }

    consultarPublicoInteres(): Observable<InterestedPublicResponse[]> {
        const opts = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token') || ''}`
            })
        };
        const url = `${environment.endpoint}${this.INTERESTED_PUBLIC_ENDPOINT}`;
        return this.http.get<InterestedPublicResponse[]>(url, opts);
    }
}