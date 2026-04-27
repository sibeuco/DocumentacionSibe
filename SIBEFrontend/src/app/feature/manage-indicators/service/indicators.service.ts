import { Injectable } from '@angular/core';
import { HttpService } from 'src/app/core/service/http.service';
import { Observable } from 'rxjs';
import { Response } from 'src/app/shared/model/response.model';
import { environment } from 'src/environments/environment';

export interface Indicador {
  id?: number;
  nombre: string;
  tipologia: string;
  proyecto: string;
}

@Injectable({
  providedIn: 'root'
})
export class IndicatorsService {
  private readonly INDICATORS_ENDPOINT = '/indicators';

  constructor(private http: HttpService) { }

  // Método para obtener todos los indicadores
  obtenerIndicadores(): Observable<Response<Indicador[]>> {
    return this.http.doGet<Response<Indicador[]>>(
      `${environment.endpoint}${this.INDICATORS_ENDPOINT}`, 
      this.http.createDefaultOptions()
    );
  }

  // Método temporal con datos mock hasta que esté la API
  obtenerIndicadoresMock(): Indicador[] {
    return [
      { id: 1, nombre: 'Satisfacción grupos de Interés', tipologia: 'eficacia', proyecto: 'Proyecto 1' },
      { id: 2, nombre: 'Nivel satisfacción', tipologia: 'impacto', proyecto: 'Proyecto 7' },
      { id: 3, nombre: 'Reducción de quejas y reclamos', tipologia: 'efectividad', proyecto: 'Proyecto 2' },
      { id: 4, nombre: 'Tiempo de respuesta', tipologia: 'eficiencia', proyecto: 'Proyecto 3' },
      { id: 5, nombre: 'Cobertura de servicios', tipologia: 'cobertura', proyecto: 'Proyecto 4' }
    ];
  }
}



