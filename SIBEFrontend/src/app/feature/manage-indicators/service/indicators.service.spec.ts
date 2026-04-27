import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { IndicatorsService } from './indicators.service';
import { HttpService } from 'src/app/core/service/http.service';

describe('IndicatorsService', () => {
  let service: IndicatorsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [IndicatorsService, HttpService]
    });
    service = TestBed.inject(IndicatorsService);
  });

  it('should be created', () => expect(service).toBeTruthy());

  it('should call doGet for obtenerIndicadores', () => {
    const httpService = TestBed.inject(HttpService);
    spyOn(httpService, 'doGet').and.returnValue(of({ valor: [] }));
    spyOn(httpService, 'createDefaultOptions').and.returnValue({} as any);
    service.obtenerIndicadores().subscribe();
    expect(httpService.doGet).toHaveBeenCalled();
  });

  it('should return mock indicators', () => {
    const mocks = service.obtenerIndicadoresMock();
    expect(mocks.length).toBeGreaterThan(0);
    expect(mocks[0].nombre).toBeDefined();
  });
});
