import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpService } from 'src/app/core/service/http.service';
import { environment } from 'src/environments/environment';
import { UniversityMemberResponse } from '../model/university-member.model';

@Injectable({
  providedIn: 'root'
})
export class UniversityMemberService extends HttpService {
  private readonly MEMBER_ENDPOINT = '/miembros';
  private readonly MEMBER_ID_ENDPOINT = '/miembros/identificacion';
  private readonly MEMBER_CARD_ENDPOINT = '/miembros/carnet';

  consultarPorIdentificacion(identificacion: string): Observable<UniversityMemberResponse> {
    const opts = this.createDefaultOptions();
    const url = `${environment.endpoint}${this.MEMBER_ID_ENDPOINT}/${identificacion}`;
    return this.doGet<UniversityMemberResponse>(url, opts);
  }

  consultarPorCarnet(carnet: string): Observable<UniversityMemberResponse> {
    const opts = this.createDefaultOptions();
    const url = `${environment.endpoint}${this.MEMBER_CARD_ENDPOINT}/${carnet}`;
    return this.doGet<UniversityMemberResponse>(url, opts);
  }
}