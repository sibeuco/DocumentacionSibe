import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ActionService } from './action.service';
import { environment } from 'src/environments/environment';

describe('ActionService', () => {
  let service: ActionService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ActionService]
    });
    service = TestBed.inject(ActionService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('should be created', () => expect(service).toBeTruthy());

  it('should call GET to consult actions with pagination params', () => {
    service.consultarAcciones(0, 10).subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/acciones`);
    expect(req.request.method).toBe('GET');
    expect(req.request.params.get('pagina')).toBe('0');
    expect(req.request.params.get('tamano')).toBe('10');
    req.flush({ content: [], totalElements: 0 });
  });

  it('should use default pagination params', () => {
    service.consultarAcciones().subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/acciones`);
    expect(req.request.params.get('pagina')).toBe('0');
    expect(req.request.params.get('tamano')).toBe('10');
    req.flush({ content: [], totalElements: 0 });
  });

  it('should call POST to add a new action', () => {
    const accion = { detalle: 'Test' } as any;
    service.agregarNuevaAccion(accion).subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/acciones`);
    expect(req.request.method).toBe('POST');
    req.flush({});
  });

  it('should call PUT to modify an action', () => {
    const accion = { detalle: 'Updated' } as any;
    service.modificarAccion('123', accion).subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/acciones/123`);
    expect(req.request.method).toBe('PUT');
    req.flush({});
  });
});
