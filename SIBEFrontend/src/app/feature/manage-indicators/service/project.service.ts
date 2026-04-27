import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HttpService } from 'src/app/core/service/http.service';
import { environment } from 'src/environments/environment';
import { ProjectResponse, ProjectRequest, EditProjectRequest } from '../model/project.model';
import { Response } from 'src/app/shared/model/response.model';

@Injectable({
    providedIn: 'root'
})
export class ProjectService extends HttpService {
    private readonly PROJECT_ENDPOINT = '/proyectos';

    constructor(http: HttpClient) {
        super(http);
    }

    consultarProyectos(pagina: number = 0, tamano: number = 10): Observable<any> {
        const opts = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token') || ''}`
            })
        };
        const params = new HttpParams()
            .set('pagina', pagina.toString())
            .set('tamano', tamano.toString());
        const url = `${environment.endpoint}${this.PROJECT_ENDPOINT}`;
        return this.doGetParameters<any>(url, params, opts);
    }

    agregarNuevoProyecto(proyecto: ProjectRequest): Observable<Response<string>> {
        const opts = this.createDefaultOptions();
        const url = `${environment.endpoint}${this.PROJECT_ENDPOINT}`;

        return this.doPost<ProjectRequest, Response<string>>(url, proyecto, opts);
    }

    modificarProyecto(identificador: string, proyecto: EditProjectRequest): Observable<{ valor: string }> {
        const opts = this.createDefaultOptions();
        const url = `${environment.endpoint}${this.PROJECT_ENDPOINT}/${identificador}`;

        return this.http.put<{ valor: string }>(url, proyecto, opts);
    }

}
