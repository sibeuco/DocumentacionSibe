import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { publicRouteGuard } from './public-route.guard';

describe('publicRouteGuard', () => {
  let router: Router;

  beforeEach(() => {
    sessionStorage.clear();
    TestBed.configureTestingModule({
      providers: [{ provide: Router, useValue: { navigate: jasmine.createSpy('navigate') } }]
    });
    router = TestBed.inject(Router);
  });

  afterEach(() => sessionStorage.clear());

  it('should allow access when no token exists', () => {
    const result = TestBed.runInInjectionContext(() => publicRouteGuard({} as any, {} as any));
    expect(result).toBe(true);
  });

  it('should allow access when token is expired', () => {
    const payload = btoa(JSON.stringify({ exp: Math.floor(Date.now() / 1000) - 3600 }));
    const fakeToken = `header.${payload}.signature`;
    sessionStorage.setItem('Authorization', fakeToken);
    const result = TestBed.runInInjectionContext(() => publicRouteGuard({} as any, {} as any));
    expect(result).toBe(true);
  });

  it('should clear expired token from sessionStorage', () => {
    const payload = btoa(JSON.stringify({ exp: Math.floor(Date.now() / 1000) - 3600 }));
    const fakeToken = `header.${payload}.signature`;
    sessionStorage.setItem('Authorization', fakeToken);
    TestBed.runInInjectionContext(() => publicRouteGuard({} as any, {} as any));
    expect(sessionStorage.getItem('Authorization')).toBeNull();
  });

  it('should redirect to /home when token is valid', () => {
    const payload = btoa(JSON.stringify({ exp: Math.floor(Date.now() / 1000) + 3600 }));
    const fakeToken = `header.${payload}.signature`;
    sessionStorage.setItem('Authorization', fakeToken);
    const result = TestBed.runInInjectionContext(() => publicRouteGuard({} as any, {} as any));
    expect(result).toBe(false);
    expect(router.navigate).toHaveBeenCalledWith(['/home']);
  });

  it('should allow access when token is malformed', () => {
    sessionStorage.setItem('Authorization', 'not-a-valid-token');
    const result = TestBed.runInInjectionContext(() => publicRouteGuard({} as any, {} as any));
    expect(result).toBe(true);
  });
});
