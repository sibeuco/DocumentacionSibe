import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { IdentificationTypeService } from './identification-type.service';
import { environment } from 'src/environments/environment';

describe('IdentificationTypeService', () => {
  let service: IdentificationTypeService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [IdentificationTypeService]
    });
    service = TestBed.inject(IdentificationTypeService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('should be created', () => expect(service).toBeTruthy());

  it('should call GET to consult identification types', () => {
    service.consultarTipoIdentificacion().subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/tipos_identificacion`);
    expect(req.request.method).toBe('GET');
    req.flush([]);
  });
});
