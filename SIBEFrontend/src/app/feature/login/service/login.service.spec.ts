import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpService } from 'src/app/core/service/http.service';
import { LoginService } from './login.service';
import { environment } from 'src/environments/environment';

describe('LoginService', () => {
  let service: LoginService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    sessionStorage.clear();
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [LoginService, HttpService]
    });
    service = TestBed.inject(LoginService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
    sessionStorage.clear();
  });

  it('should be created', () => expect(service).toBeTruthy());

  it('should call GET to validate login', () => {
    const login = { correo: 'test@test.com', clave: '123' } as any;
    service.validarLogin(login).subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/login`);
    expect(req.request.method).toBe('GET');
    req.flush({ valor: 'token123' });
  });

  it('should store user details in sessionStorage when validating login', () => {
    const login = { correo: 'test@test.com', clave: '123' } as any;
    service.validarLogin(login).subscribe();
    expect(sessionStorage.getItem('userdetails')).toBe(JSON.stringify(login));
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/login`);
    req.flush({ valor: 'token123' });
  });
});
