import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserService } from './user.service';
import { environment } from 'src/environments/environment';

describe('UserService', () => {
  let service: UserService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UserService]
    });
    service = TestBed.inject(UserService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('should be created', () => expect(service).toBeTruthy());

  it('should call GET to consult users', () => {
    service.consultarUsuarios().subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/usuarios`);
    expect(req.request.method).toBe('GET');
    req.flush([]);
  });

  it('should call GET to consult users by type paginated', () => {
    service.consultarUsuariosPorTipo('Administrador de dirección', 0, 10).subscribe();
    const req = httpMock.expectOne(r => r.url.includes('/usuarios/paginado'));
    expect(req.request.method).toBe('GET');
    expect(req.request.params.get('tipoUsuario')).toBe('Administrador de dirección');
    expect(req.request.params.get('pagina')).toBe('0');
    expect(req.request.params.get('tamano')).toBe('10');
    expect(req.request.params.get('excluir')).toBe('false');
    req.flush({ content: [], totalElements: 0 });
  });

  it('should call GET to consult users by type paginated with excluir true', () => {
    service.consultarUsuariosPorTipo('Administrador de dirección', 0, 10, true).subscribe();
    const req = httpMock.expectOne(r => r.url.includes('/usuarios/paginado'));
    expect(req.request.method).toBe('GET');
    expect(req.request.params.get('excluir')).toBe('true');
    req.flush({ content: [], totalElements: 0 });
  });

  it('should call POST to add a new user', () => {
    const user = { correo: 'test@test.com' } as any;
    service.agregarNuevoUsuario(user).subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/usuarios` && r.method === 'POST');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(user);
    req.flush({});
  });

  it('should call DELETE to remove a user', () => {
    const id = '123';
    service.eliminarUsuario(id).subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/usuarios/usuario/${id}`);
    expect(req.request.method).toBe('DELETE');
    req.flush({});
  });

  it('should call PUT to modify a user', () => {
    const id = '456';
    const user = { nombre: 'Updated' } as any;
    service.modificarUsuario(id, user).subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/usuarios/usuario/${id}`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(user);
    req.flush({});
  });

  it('should call GET to consult user by identifier', () => {
    const id = '789';
    service.consultarUsuarioPorIdentificador(id).subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/usuarios/usuario/id/${id}`);
    expect(req.request.method).toBe('GET');
    req.flush({});
  });

  it('should call GET to consult user by correo', () => {
    service.consultarUsuarioPorCorreo('test@mail.com').subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/usuarios/usuario/correo/test@mail.com`);
    expect(req.request.method).toBe('GET');
    req.flush({});
  });

  it('should call PUT to modify password', () => {
    const passReq = { identificador: '1', claveAntigua: 'old', claveNueva: 'new' } as any;
    service.modificarClave(passReq).subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/usuarios/modificar/clave`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(passReq);
    req.flush({ valor: 'ok' });
  });

  it('should propagate error from modificarClave', () => {
    const passReq = { identificador: '1', claveAntigua: 'old', claveNueva: 'new' } as any;
    let errorCaught: any;
    service.modificarClave(passReq).subscribe({ error: (e) => errorCaught = e });
    const req = httpMock.expectOne(r => r.url.includes('/modificar/clave'));
    req.flush('bad request', { status: 400, statusText: 'Bad Request' });
    expect(errorCaught).toBeTruthy();
  });

});
