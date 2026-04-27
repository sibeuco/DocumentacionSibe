import { Injectable } from '@angular/core';
import { HttpService } from 'src/app/core/service/http.service';
import { Login } from '../model/login.model';
import { Observable } from 'rxjs';
import { Response } from 'src/app/shared/model/response.model';
import { environment } from 'src/environments/environment';

@Injectable()
export class LoginService {
  private readonly LOGIN_ENDPOINT = '/login';
  constructor(private http:HttpService){}

  validarLogin(login: Login): Observable<Response<string>>  {
    window.sessionStorage.setItem('userdetails', JSON.stringify(login));

    return this.http.doGet<Response<string>>(`${environment.endpoint}${this.LOGIN_ENDPOINT}`);
  }

}
