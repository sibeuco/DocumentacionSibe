import { Injectable } from '@angular/core';
import { HttpService } from 'src/app/core/service/http.service';
import { Observable } from 'rxjs';
import { Response } from 'src/app/shared/model/response.model';
import { environment } from 'src/environments/environment';
import { EditIndicatorRequest, IndicatorRequest, IndicatorResponse } from '../model/indicator.model';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class IndicatorService extends HttpService {
  private readonly INDICATOR_ENDPOINT = '/indicadores';

  constructor(http: HttpClient) {
    super(http);
  }

  consultarIndicadores(pagina: number = 0, tamano: number = 10): Observable<any> {
    const opts = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token') || ''}`
      })
    };
    const params = new HttpParams()
        .set('pagina', pagina.toString())
        .set('tamano', tamano.toString());
    const url = `${environment.endpoint}${this.INDICATOR_ENDPOINT}`;
    return this.doGetParameters<any>(url, params, opts);
  }

  consultarIndicadoresParaActividades(): Observable<IndicatorResponse[]> {
    const opts = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token') || ''}`
      })
    };
    const url = `${environment.endpoint}${this.INDICATOR_ENDPOINT}/actividades`;
    return this.http.get<IndicatorResponse[]>(url, opts);
  }

  consultarTodosLosIndicadores(): Observable<IndicatorResponse[]> {
    const opts = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token') || ''}`
      })
    };
    const url = `${environment.endpoint}${this.INDICATOR_ENDPOINT}/todos`;
    return this.http.get<IndicatorResponse[]>(url, opts);
  }

  agregarNuevoIndicador(indicador: IndicatorRequest): Observable<Response<string>> {
    const opts = this.createDefaultOptions();
        const url = `${environment.endpoint}${this.INDICATOR_ENDPOINT}`;

        return this.doPost<IndicatorRequest, Response<string>>(url, indicador, opts);
  }

  modificarIndicador(identificador: string, indicador: EditIndicatorRequest): Observable<Response<string>> {
    const opts = this.createDefaultOptions();
        const url = `${environment.endpoint}${this.INDICATOR_ENDPOINT}/${identificador}`;

        return this.doPut<EditIndicatorRequest, Response<string>>(url, indicador, opts);

  }

}


