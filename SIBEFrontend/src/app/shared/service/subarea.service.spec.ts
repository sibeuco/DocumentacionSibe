import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { SubAreaService } from './subarea.service';
import { environment } from 'src/environments/environment';

describe('SubAreaService', () => {
  let service: SubAreaService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [SubAreaService]
    });
    service = TestBed.inject(SubAreaService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('should be created', () => expect(service).toBeTruthy());

  it('should call GET to consult subareas', () => {
    service.consultarSubareas().subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/subareas`);
    expect(req.request.method).toBe('GET');
    req.flush([]);
  });

  it('should call GET to consult subarea by name', () => {
    service.consultarPorNombre('Deporte').subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/subareas/nombre/Deporte`);
    expect(req.request.method).toBe('GET');
    req.flush({});
  });

  it('should call GET to consult subarea detail', () => {
    service.consultarDetalle('789').subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/subareas/detalle/789`);
    expect(req.request.method).toBe('GET');
    req.flush({});
  });
});
