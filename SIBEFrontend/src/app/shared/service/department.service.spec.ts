import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { DepartmentService } from './department.service';
import { environment } from 'src/environments/environment';

describe('DepartmentService', () => {
  let service: DepartmentService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [DepartmentService]
    });
    service = TestBed.inject(DepartmentService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('should be created', () => expect(service).toBeTruthy());

  it('should call GET to consult departments', () => {
    service.consultarDirecciones().subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/direcciones`);
    expect(req.request.method).toBe('GET');
    req.flush([]);
  });

  it('should call GET to consult department by name', () => {
    service.consultarPorNombre('Bienestar').subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/direcciones/nombre/Bienestar`);
    expect(req.request.method).toBe('GET');
    req.flush({});
  });

  it('should call GET to consult department detail', () => {
    service.consultarDetalle('456').subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/direcciones/detalle/456`);
    expect(req.request.method).toBe('GET');
    req.flush({});
  });
});
