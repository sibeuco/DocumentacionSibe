import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UniversityMemberService } from './university-member.service';
import { environment } from 'src/environments/environment';

describe('UniversityMemberService', () => {
  let service: UniversityMemberService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UniversityMemberService]
    });
    service = TestBed.inject(UniversityMemberService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('should be created', () => expect(service).toBeTruthy());

  it('should call GET to consult member by identification', () => {
    service.consultarPorIdentificacion('123456').subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/miembros/identificacion/123456`);
    expect(req.request.method).toBe('GET');
    req.flush({});
  });

  it('should call GET to consult member by card', () => {
    service.consultarPorCarnet('CAR001').subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/miembros/carnet/CAR001`);
    expect(req.request.method).toBe('GET');
    req.flush({});
  });
});
