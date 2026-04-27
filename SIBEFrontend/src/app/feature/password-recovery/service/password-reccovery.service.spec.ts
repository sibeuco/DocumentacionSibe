import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { PasswordRecoveryService } from './password-reccovery.service';
import { environment } from 'src/environments/environment';

describe('PasswordRecoveryService', () => {
  let service: PasswordRecoveryService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [PasswordRecoveryService]
    });
    service = TestBed.inject(PasswordRecoveryService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('should be created', () => expect(service).toBeTruthy());

  it('should call POST to request recovery code', () => {
    service.solicitarCodigo('test@correo.com').subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/usuarios/recuperacion/solicitar/test@correo.com`);
    expect(req.request.method).toBe('POST');
    req.flush({ valor: '123' });
  });

  it('should call POST to validate recovery code', () => {
    const codeRequest = { correo: 'test@correo.com', codigo: '123456' } as any;
    service.validarCodigo(codeRequest).subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/usuarios/recuperacion/validarCodigo`);
    expect(req.request.method).toBe('POST');
    req.flush({ valor: true });
  });

  it('should call PUT to recover password', () => {
    const request = { correo: 'test@correo.com', nuevaClave: 'Pass123!' } as any;
    service.recuperarClave(request).subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/usuarios/recuperacion/recuperarClave`);
    expect(req.request.method).toBe('PUT');
    req.flush({ valor: '123' });
  });

  it('should propagate error from solicitarCodigo', () => {
    let errorCaught: any;
    service.solicitarCodigo('fail@correo.com').subscribe({ error: e => errorCaught = e });
    const req = httpMock.expectOne(r => r.url.includes('/solicitar/'));
    req.flush('error', { status: 500, statusText: 'Server Error' });
    expect(errorCaught).toBeTruthy();
  });

  it('should propagate error from validarCodigo', () => {
    let errorCaught: any;
    service.validarCodigo({ correo: 'test@t.com', codigo: '999' } as any).subscribe({ error: e => errorCaught = e });
    const req = httpMock.expectOne(r => r.url.includes('/validarCodigo'));
    req.flush('error', { status: 400, statusText: 'Bad Request' });
    expect(errorCaught).toBeTruthy();
  });

  it('should propagate error from recuperarClave', () => {
    let errorCaught: any;
    service.recuperarClave({ correo: 'test@t.com', clave: 'x' } as any).subscribe({ error: e => errorCaught = e });
    const req = httpMock.expectOne(r => r.url.includes('/recuperarClave'));
    req.flush('error', { status: 500, statusText: 'Server Error' });
    expect(errorCaught).toBeTruthy();
  });
});
