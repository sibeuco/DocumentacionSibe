import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpParams } from '@angular/common/http';
import { HttpService } from './http.service';

describe('HttpService', () => {
  let service: HttpService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [HttpService]
    });
    service = TestBed.inject(HttpService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('should be created', () => expect(service).toBeTruthy());

  it('should make a GET request via doGet', () => {
    const opts = service.createDefaultOptions();
    service.doGet('/api/test', opts).subscribe(data => expect(data).toEqual({ id: 1 }));
    const req = httpMock.expectOne('/api/test');
    expect(req.request.method).toBe('GET');
    req.flush({ id: 1 });
  });

  it('should make a POST request via doPost', () => {
    const opts = service.createDefaultOptions();
    service.doPost('/api/test', { name: 'test' }, opts).subscribe(data => expect(data).toBeTruthy());
    const req = httpMock.expectOne('/api/test');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual({ name: 'test' });
    req.flush({ id: 1 });
  });

  it('should make a PUT request via doPut', () => {
    const opts = service.createDefaultOptions();
    service.doPut('/api/test', { name: 'updated' }, opts).subscribe();
    const req = httpMock.expectOne('/api/test');
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual({ name: 'updated' });
    req.flush({});
  });

  it('should make a DELETE request via doDelete', () => {
    const opts = service.createDefaultOptions();
    service.doDelete('/api/test', opts).subscribe();
    const req = httpMock.expectOne('/api/test');
    expect(req.request.method).toBe('DELETE');
    req.flush({});
  });

  it('should make a GET request with params via doGetParameters', () => {
    const params = new HttpParams().set('page', '1');
    const opts = service.createDefaultOptions();
    service.doGetParameters('/api/test', params, opts).subscribe();
    const req = httpMock.expectOne(r => r.url === '/api/test');
    expect(req.request.method).toBe('GET');
    expect(req.request.params.get('page')).toBe('1');
    req.flush([]);
  });

  it('should set Content-Type header by default', () => {
    const opts = service.createDefaultOptions();
    expect(opts.headers.get('Content-Type')).toBe('application/json');
  });

  it('should make a GET request by id via doGetById', () => {
    const opts = service.createDefaultOptions();
    service.doGetById('/api/test/', 5, opts).subscribe();
    const req = httpMock.expectOne('/api/test/5');
    expect(req.request.method).toBe('GET');
    req.flush({});
  });

  it('should make a GET request by email via doGetByEmail', () => {
    const opts = service.createDefaultOptions();
    service.doGetByEmail('/api/test/', 'user@test.com', opts).subscribe();
    const req = httpMock.expectOne('/api/test/user@test.com');
    expect(req.request.method).toBe('GET');
    req.flush({});
  });

  it('should make a POST without body via doPostWithOutBody', () => {
    const opts = service.createDefaultOptions();
    service.doPostWithOutBody('/api/test/', 'user@test.com', opts).subscribe();
    const req = httpMock.expectOne('/api/test/user@test.com');
    expect(req.request.method).toBe('POST');
    req.flush({});
  });

  it('should make a POST without body and id via doPostWithOutBodyAndId', () => {
    const opts = service.createDefaultOptions();
    service.doPostWithOutBodyAndId('/api/test/', 10, opts).subscribe();
    const req = httpMock.expectOne('/api/test/10');
    expect(req.request.method).toBe('POST');
    req.flush({});
  });

  it('should make a POST via doRequestMapping', () => {
    const opts = service.createDefaultOptions();
    service.doRequestMapping('/api/test/', 7, opts).subscribe();
    const req = httpMock.expectOne('/api/test/7');
    expect(req.request.method).toBe('POST');
    req.flush({});
  });

  it('should make a PUT without body via doPutWithOutBody', () => {
    const opts = service.createDefaultOptions();
    service.doPutWithOutBody('/api/test/', 3, opts).subscribe();
    const req = httpMock.expectOne('/api/test/3');
    expect(req.request.method).toBe('PUT');
    req.flush({});
  });

  it('should set custom header via setHeader', () => {
    const opts = service.setHeader('X-Custom', 'valor');
    expect(opts.headers.get('X-Custom')).toBe('valor');
    expect(opts.headers.get('Content-Type')).toBe('application/json');
  });

  it('should use default options when doGet is called without opts', () => {
    service.doGet('/api/test').subscribe();
    const req = httpMock.expectOne('/api/test');
    expect(req.request.method).toBe('GET');
    req.flush({});
  });

  it('should handle doGetParameters with null params', () => {
    const opts = service.createDefaultOptions();
    service.doGetParameters('/api/test', null as any, opts).subscribe();
    const req = httpMock.expectOne('/api/test');
    expect(req.request.method).toBe('GET');
    req.flush([]);
  });

  it('should add Content-Type when opts headers lack it', () => {
    const { HttpHeaders } = require('@angular/common/http');
    const opts = { headers: new HttpHeaders({ 'Authorization': 'Bearer test' }) };
    service.doGet('/api/test', opts as any).subscribe();
    const req = httpMock.expectOne('/api/test');
    expect(req.request.headers.get('Content-Type')).toBe('application/json');
    req.flush({});
  });
});
