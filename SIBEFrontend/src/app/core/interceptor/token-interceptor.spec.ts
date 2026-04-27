import { TestBed } from '@angular/core/testing';
import { HTTP_INTERCEPTORS, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { TokenInterceptor } from './token-interceptor';
import { Component } from '@angular/core';

@Component({ template: '' })
class DummyLoginComponent {}

describe('TokenInterceptor', () => {
  let http: HttpClient;
  let httpMock: HttpTestingController;
  let cookieService: jasmine.SpyObj<CookieService>;
  let router: Router;

  function createValidToken(expInSeconds: number): string {
    const header = btoa(JSON.stringify({ alg: 'HS256', typ: 'JWT' }));
    const payload = btoa(JSON.stringify({ sub: 'user', exp: expInSeconds }));
    return `${header}.${payload}.fake-signature`;
  }

  beforeEach(() => {
    cookieService = jasmine.createSpyObj('CookieService', ['get', 'delete']);

    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        RouterTestingModule.withRoutes([
          { path: 'login', component: DummyLoginComponent }
        ])
      ],
      declarations: [DummyLoginComponent],
      providers: [
        { provide: CookieService, useValue: cookieService },
        { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true }
      ]
    });

    http = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
    router = TestBed.inject(Router);
    spyOn(router, 'navigate');
  });

  afterEach(() => {
    httpMock.verify();
    sessionStorage.clear();
    localStorage.clear();
  });

  it('should add Bearer token header when a valid token exists', () => {
    const futureExp = Math.floor(Date.now() / 1000) + 3600;
    const token = createValidToken(futureExp);
    cookieService.get.and.returnValue(token);

    http.get('/api/test').subscribe();

    const req = httpMock.expectOne('/api/test');
    expect(req.request.headers.get('Authorization')).toBe(`Bearer ${token}`);
    req.flush({});
  });

  it('should pass request without Authorization when no token exists', () => {
    cookieService.get.and.returnValue('');

    http.get('/api/test').subscribe();

    const req = httpMock.expectOne('/api/test');
    expect(req.request.headers.has('Authorization')).toBeFalse();
    req.flush({});
  });

  it('should redirect to /login and throw error when token is expired', () => {
    const pastExp = Math.floor(Date.now() / 1000) - 3600;
    const token = createValidToken(pastExp);
    cookieService.get.and.returnValue(token);

    http.get('/api/test').subscribe({
      error: (err) => {
        expect(err.message).toBe('Token expirado');
      }
    });

    expect(router.navigate).toHaveBeenCalledWith(['/login']);
    expect(cookieService.delete).toHaveBeenCalledWith('token');
  });

  it('should logout and redirect on 401 response', () => {
    const futureExp = Math.floor(Date.now() / 1000) + 3600;
    const token = createValidToken(futureExp);
    cookieService.get.and.returnValue(token);

    http.get('/api/test').subscribe({
      error: (err: HttpErrorResponse) => {
        expect(err.status).toBe(401);
      }
    });

    const req = httpMock.expectOne('/api/test');
    req.flush('Unauthorized', { status: 401, statusText: 'Unauthorized' });

    expect(router.navigate).toHaveBeenCalledWith(['/login']);
    expect(cookieService.delete).toHaveBeenCalledWith('token');
  });

  it('should rethrow non-401 errors without redirecting', () => {
    const futureExp = Math.floor(Date.now() / 1000) + 3600;
    const token = createValidToken(futureExp);
    cookieService.get.and.returnValue(token);

    http.get('/api/test').subscribe({
      error: (err: HttpErrorResponse) => {
        expect(err.status).toBe(500);
      }
    });

    const req = httpMock.expectOne('/api/test');
    req.flush('Server Error', { status: 500, statusText: 'Internal Server Error' });

    expect(router.navigate).not.toHaveBeenCalledWith(['/login']);
  });

  it('should treat a malformed token as expired', () => {
    cookieService.get.and.returnValue('not-a-jwt');

    http.get('/api/test').subscribe({
      error: (err) => {
        expect(err.message).toBe('Token expirado');
      }
    });

    expect(router.navigate).toHaveBeenCalledWith(['/login']);
  });
});
