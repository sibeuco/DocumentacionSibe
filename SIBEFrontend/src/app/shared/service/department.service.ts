import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HttpService } from 'src/app/core/service/http.service';
import { Response } from '../model/response.model';
import { environment } from 'src/environments/environment';
import { DepartmentDetailResponse, DepartmentResponse } from '../model/department.model';

@Injectable({
    providedIn: 'root'
})
export class DepartmentService extends HttpService {
    private readonly DEPARTMENT_ENDPOINT = '/direcciones';
    private readonly DEPARTMENT_NAME_ENDPOINT = '/direcciones/nombre';
    private readonly DEPARTMENT_DETAIL_ENDPOINT = '/direcciones/detalle';

    constructor(http: HttpClient) {
        super(http);
    }

    consultarDirecciones(): Observable<DepartmentResponse[]> {
        const opts = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token') || ''}`
            })
        };
        const url = `${environment.endpoint}${this.DEPARTMENT_ENDPOINT}`;
        return this.http.get<DepartmentResponse[]>(url, opts);
    }

    consultarPorNombre(nombre: string): Observable<DepartmentResponse> {
        const opts = this.createDefaultOptions();
        const url = `${environment.endpoint}${this.DEPARTMENT_NAME_ENDPOINT}/${nombre}`;

        return this.doGet<DepartmentResponse>(url, opts);
    }

    consultarDetalle(identificador: string): Observable<DepartmentDetailResponse> {
        const opts = this.createDefaultOptions();
        const url = `${environment.endpoint}${this.DEPARTMENT_DETAIL_ENDPOINT}/${identificador}`;

        return this.doGet<DepartmentDetailResponse>(url, opts);
    }

}