import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UploadDatabaseService } from './upload-database.service';
import { environment } from 'src/environments/environment';

describe('UploadDatabaseService', () => {
  let service: UploadDatabaseService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UploadDatabaseService]
    });
    service = TestBed.inject(UploadDatabaseService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('should be created', () => expect(service).toBeTruthy());

  it('should call POST to upload employees file', () => {
    const file = new File(['test'], 'test.xlsx', { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
    service.subirArchivoEmpleados({ archivo: file }).subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/carga_masiva/empleados`);
    expect(req.request.method).toBe('POST');
    req.flush({});
  });

  it('should call POST to upload students file', () => {
    const file = new File(['test'], 'test.xlsx', { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
    service.subirArchivoEstudiantes({ archivo: file }).subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/carga_masiva/estudiantes`);
    expect(req.request.method).toBe('POST');
    req.flush({});
  });

  it('should retry without headers on 401 error for employees', () => {
    sessionStorage.setItem('Authorization', 'test-token');
    const file = new File(['test'], 'test.xlsx');
    service.subirArchivoEmpleados({ archivo: file }).subscribe();
    const req1 = httpMock.expectOne(r => r.url.includes('/empleados'));
    req1.flush('Unauthorized', { status: 401, statusText: 'Unauthorized' });
    const req2 = httpMock.expectOne(r => r.url.includes('/empleados'));
    expect(req2.request.method).toBe('POST');
    req2.flush({ ok: true });
    sessionStorage.removeItem('Authorization');
  });

  it('should retry without headers on 403 error for students', () => {
    sessionStorage.setItem('Authorization', 'test-token');
    const file = new File(['test'], 'test.xlsx');
    service.subirArchivoEstudiantes({ archivo: file }).subscribe();
    const req1 = httpMock.expectOne(r => r.url.includes('/estudiantes'));
    req1.flush('Forbidden', { status: 403, statusText: 'Forbidden' });
    const req2 = httpMock.expectOne(r => r.url.includes('/estudiantes'));
    expect(req2.request.method).toBe('POST');
    req2.flush({ ok: true });
    sessionStorage.removeItem('Authorization');
  });

  it('should propagate non-auth errors', () => {
    const file = new File(['test'], 'test.xlsx');
    let errorCaught: any;
    service.subirArchivoEmpleados({ archivo: file }).subscribe({ error: e => errorCaught = e });
    const req = httpMock.expectOne(r => r.url.includes('/empleados'));
    req.flush('Server Error', { status: 500, statusText: 'Server Error' });
    expect(errorCaught).toBeTruthy();
  });

  it('should add Bearer token to headers when token exists', () => {
    sessionStorage.setItem('Authorization', 'my-token');
    const file = new File(['test'], 'test.xlsx');
    service.subirArchivoEmpleados({ archivo: file }).subscribe();
    const req = httpMock.expectOne(r => r.url.includes('/empleados'));
    expect(req.request.headers.get('Authorization')).toBe('Bearer my-token');
    req.flush({});
    sessionStorage.removeItem('Authorization');
  });

  it('should handle Bearer prefix in existing token', () => {
    sessionStorage.setItem('Authorization', 'Bearer existing-token');
    const file = new File(['test'], 'test.xlsx');
    service.subirArchivoEmpleados({ archivo: file }).subscribe();
    const req = httpMock.expectOne(r => r.url.includes('/empleados'));
    expect(req.request.headers.get('Authorization')).toBe('Bearer existing-token');
    req.flush({});
    sessionStorage.removeItem('Authorization');
  });
});
