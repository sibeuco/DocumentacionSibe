import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AreaService } from './area.service';
import { environment } from 'src/environments/environment';

describe('AreaService', () => {
  let service: AreaService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AreaService]
    });
    service = TestBed.inject(AreaService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('should be created', () => expect(service).toBeTruthy());

  it('should call GET to consult areas', () => {
    service.consultarAreas().subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/areas`);
    expect(req.request.method).toBe('GET');
    req.flush([]);
  });

  it('should call GET to consult area by name', () => {
    service.consultarPorNombre('Salud').subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/areas/nombre/Salud`);
    expect(req.request.method).toBe('GET');
    req.flush({});
  });

  it('should call GET to consult area detail', () => {
    service.consultarDetalle('123').subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/areas/detalle/123`);
    expect(req.request.method).toBe('GET');
    req.flush({});
  });
});
