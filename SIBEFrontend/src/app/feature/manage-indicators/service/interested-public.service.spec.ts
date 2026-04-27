import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { InterestedPublicService } from './interested-public.service';
import { environment } from 'src/environments/environment';

describe('InterestedPublicService', () => {
  let service: InterestedPublicService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [InterestedPublicService]
    });
    service = TestBed.inject(InterestedPublicService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('should be created', () => expect(service).toBeTruthy());

  it('should call GET to consult interested publics', () => {
    service.consultarPublicoInteres().subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/publicos_interes`);
    expect(req.request.method).toBe('GET');
    req.flush([]);
  });
});
