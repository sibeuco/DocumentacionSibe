import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserTypeService } from './user-type.service';
import { environment } from 'src/environments/environment';

describe('UserTypeService', () => {
  let service: UserTypeService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UserTypeService]
    });
    service = TestBed.inject(UserTypeService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('should be created', () => expect(service).toBeTruthy());

  it('should call GET to consult user types', () => {
    service.consultarTipoUsuario().subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/tipos_usuario`);
    expect(req.request.method).toBe('GET');
    req.flush([]);
  });
});
