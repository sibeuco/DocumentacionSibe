import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { FrequencyService } from './frequency.service';
import { environment } from 'src/environments/environment';

describe('FrequencyService', () => {
  let service: FrequencyService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [FrequencyService]
    });
    service = TestBed.inject(FrequencyService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('should be created', () => expect(service).toBeTruthy());

  it('should call GET to consult frequencies', () => {
    service.consultarTemporalidades().subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/temporalidades`);
    expect(req.request.method).toBe('GET');
    req.flush([]);
  });
});
