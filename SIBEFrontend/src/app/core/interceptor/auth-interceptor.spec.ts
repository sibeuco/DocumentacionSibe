import { TestBed } from '@angular/core/testing';
import { HTTP_INTERCEPTORS, HttpClient } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AuthInterceptor } from './auth-interceptor';

describe('AuthInterceptor', () => {
  let http: HttpClient;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
      ]
    });
    http = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
    sessionStorage.clear();
  });

  afterEach(() => {
    httpMock.verify();
    sessionStorage.clear();
  });

  it('should add Basic Authorization header when userdetails exist in sessionStorage', () => {
    const user = { correo: 'user@test.com', contrasena: 'pass123' };
    sessionStorage.setItem('userdetails', JSON.stringify(user));

    http.get('/api/test').subscribe();

    const req = httpMock.expectOne('/api/test');
    const expectedAuth = 'Basic ' + window.btoa('user@test.com:pass123');
    expect(req.request.headers.get('Authorization')).toBe(expectedAuth);
    req.flush({});
  });

  it('should use Authorization from sessionStorage when no userdetails', () => {
    sessionStorage.setItem('Authorization', 'Bearer saved-token');

    http.get('/api/test').subscribe();

    const req = httpMock.expectOne('/api/test');
    expect(req.request.headers.get('Authorization')).toBe('Bearer saved-token');
    req.flush({});
  });

  it('should not add Authorization header when no credentials exist', () => {
    http.get('/api/test').subscribe();

    const req = httpMock.expectOne('/api/test');
    expect(req.request.headers.has('Authorization')).toBeFalse();
    req.flush({});
  });

  it('should always add X-Requested-With header', () => {
    http.get('/api/test').subscribe();

    const req = httpMock.expectOne('/api/test');
    expect(req.request.headers.get('X-Requested-With')).toBe('XMLHttpRequest');
    req.flush({});
  });

  it('should add X-XSRF-TOKEN header when XSRF-TOKEN is in sessionStorage', () => {
    sessionStorage.setItem('XSRF-TOKEN', 'xsrf-value');

    http.get('/api/test').subscribe();

    const req = httpMock.expectOne('/api/test');
    expect(req.request.headers.get('X-XSRF-TOKEN')).toBe('xsrf-value');
    req.flush({});
  });

  it('should remove userdetails from sessionStorage after intercept', () => {
    const user = { correo: 'user@test.com', contrasena: 'pass123' };
    sessionStorage.setItem('userdetails', JSON.stringify(user));

    http.get('/api/test').subscribe();

    const req = httpMock.expectOne('/api/test');
    expect(sessionStorage.getItem('userdetails')).toBeNull();
    req.flush({});
  });

  it('should save Authorization header from response to sessionStorage', () => {
    http.get('/api/test').subscribe();

    const req = httpMock.expectOne('/api/test');
    req.flush({}, { headers: { Authorization: 'Bearer new-token' } });

    expect(sessionStorage.getItem('Authorization')).toBe('Bearer new-token');
  });

  it('should not overwrite Authorization in sessionStorage when response has no auth header', () => {
    sessionStorage.setItem('Authorization', 'Bearer old-token');

    http.get('/api/test').subscribe();

    const req = httpMock.expectOne('/api/test');
    req.flush({});

    expect(sessionStorage.getItem('Authorization')).toBe('Bearer old-token');
  });

  it('should handle 401 error without throwing', () => {
    http.get('/api/test').subscribe({
      error: () => { /* expected */ }
    });

    const req = httpMock.expectOne('/api/test');
    req.flush('Unauthorized', { status: 401, statusText: 'Unauthorized' });
  });

  it('should handle non-401 error without throwing', () => {
    http.get('/api/test').subscribe({
      error: () => { /* expected */ }
    });

    const req = httpMock.expectOne('/api/test');
    req.flush('Server Error', { status: 500, statusText: 'Internal Server Error' });
  });
});
