import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { IndicatorTypeService } from './indicator-type.service';
import { environment } from 'src/environments/environment';

describe('IndicatorTypeService', () => {
  let service: IndicatorTypeService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [IndicatorTypeService]
    });
    service = TestBed.inject(IndicatorTypeService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('should be created', () => expect(service).toBeTruthy());

  it('should call GET to consult indicator types', () => {
    service.consultarTiposIndicador().subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/tipos_indicador`);
    expect(req.request.method).toBe('GET');
    req.flush([]);
  });
});
