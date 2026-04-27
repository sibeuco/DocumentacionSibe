import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ActivityService } from './activity.service';
import { environment } from 'src/environments/environment';

describe('ActivityService', () => {
  let service: ActivityService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ActivityService]
    });
    service = TestBed.inject(ActivityService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('should be created', () => expect(service).toBeTruthy());

  it('should call POST to create a new activity', () => {
    const activity = { nombre: 'Test', descripcion: 'Desc' } as any;
    service.agregarNuevaActividad(activity).subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/actividades`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(activity);
    req.flush({});
  });

  it('should call GET to consult activities', () => {
    service.consultarActividades().subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/actividades`);
    expect(req.request.method).toBe('GET');
    req.flush([]);
  });

  it('should call PUT to modify an activity', () => {
    const id = '456';
    const activity = { nombre: 'Updated' } as any;
    service.modificarActividad(id, activity).subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/actividades/${id}`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(activity);
    req.flush({});
  });

  it('should call GET to consult executions for an activity', () => {
    const id = '789';
    service.consultarEjecuciones(id).subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/actividades/ejecuciones/${id}`);
    expect(req.request.method).toBe('GET');
    req.flush([]);
  });

  it('should call PUT to start an activity', () => {
    service.iniciarActividad('act1').subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/actividades/iniciar/act1`);
    expect(req.request.method).toBe('PUT');
    req.flush({});
  });

  it('should call PUT to cancel an activity', () => {
    service.cancelarActividad('act1').subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/actividades/cancelar/act1`);
    expect(req.request.method).toBe('PUT');
    req.flush({});
  });

  it('should call PUT to finalize an activity with participants', () => {
    const participants = [{ identificador: 'p1', nombreCompleto: 'Test', documentoIdentificacion: '123' }] as any;
    service.finalizarActividad('act1', participants).subscribe();
    const req = httpMock.expectOne(r => r.url === `${environment.endpoint}/actividades/finalizar/act1`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(participants);
    req.flush({});
  });

  it('should call POST to count total participants', () => {
    const filtro = {} as any;
    service.contarParticipantesTotales(filtro).subscribe();
    const req = httpMock.expectOne(r => r.url.includes('/participantes/conteo'));
    expect(req.request.method).toBe('POST');
    req.flush(100);
  });

  it('should call POST to count total asistencias', () => {
    const filtro = {} as any;
    service.contarAsistenciasTotales(filtro).subscribe();
    const req = httpMock.expectOne(r => r.url.includes('/asistencias/conteo'));
    expect(req.request.method).toBe('POST');
    req.flush(50);
  });

  it('should call POST to count total ejecuciones', () => {
    const filtro = {} as any;
    service.contarEjecucionesTotales(filtro).subscribe();
    const req = httpMock.expectOne(r => r.url.includes('/finalizadas/conteo'));
    expect(req.request.method).toBe('POST');
    req.flush(25);
  });

  it('should call POST for estadisticas por estructura', () => {
    const filtro = {} as any;
    service.consultarEstadisticasParticipantesPorEstructura(filtro).subscribe();
    const req = httpMock.expectOne(r => r.url.includes('/estadisticas-estructura'));
    expect(req.request.method).toBe('POST');
    req.flush([]);
  });

  it('should call POST for estadisticas por mes', () => {
    const filtro = {} as any;
    service.consultarEstadisticasParticipantesPorMes(filtro).subscribe();
    const req = httpMock.expectOne(r => r.url.includes('/estadisticas-mes'));
    expect(req.request.method).toBe('POST');
    req.flush([]);
  });

  it('should call POST for poblacion total', () => {
    const filtro = {} as any;
    service.contarPoblacionTotal(filtro).subscribe();
    const req = httpMock.expectOne(r => r.url.includes('/poblacion/conteo'));
    expect(req.request.method).toBe('POST');
    req.flush(1000);
  });

  it('should call GET for meses ejecuciones finalizadas', () => {
    service.consultarMesesEjecucionesFinalizadas().subscribe();
    const req = httpMock.expectOne(r => r.url.includes('/finalizadas/meses'));
    expect(req.request.method).toBe('GET');
    req.flush(['Enero', 'Febrero']);
  });

  it('should call GET for annos ejecuciones finalizadas', () => {
    service.consultarAnnosEjecucionesFinalizadas().subscribe();
    const req = httpMock.expectOne(r => r.url.includes('/finalizadas/annos'));
    expect(req.request.method).toBe('GET');
    req.flush(['2024', '2025']);
  });

  it('should call GET for semestres estudiantes', () => {
    service.consultarSemestresEstudiantesEnEjecucionesFinalizadas().subscribe();
    const req = httpMock.expectOne(r => r.url.includes('/finalizadas/semestres'));
    expect(req.request.method).toBe('GET');
    req.flush(['1', '2']);
  });

  it('should call GET for centros costos empleados', () => {
    service.consultarCentrosCostosEmpleadosEnEjecucionesFinalizadas().subscribe();
    const req = httpMock.expectOne(r => r.url.includes('/finalizadas/centros-costos'));
    expect(req.request.method).toBe('GET');
    req.flush([]);
  });

  it('should call GET for tipos participantes', () => {
    service.consultarTiposParticipantesEnEjecucionesFinalizadas().subscribe();
    const req = httpMock.expectOne(r => r.url.includes('/finalizadas/tipos-participantes'));
    expect(req.request.method).toBe('GET');
    req.flush([]);
  });

  it('should call GET for programas academicos', () => {
    service.consultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadas().subscribe();
    const req = httpMock.expectOne(r => r.url.includes('/finalizadas/programas-academicos'));
    expect(req.request.method).toBe('GET');
    req.flush([]);
  });

  it('should call GET for niveles formacion', () => {
    service.consultarNivelesFormacionEstudiantesEnEjecucionesFinalizadas().subscribe();
    const req = httpMock.expectOne(r => r.url.includes('/finalizadas/niveles-formacion'));
    expect(req.request.method).toBe('GET');
    req.flush([]);
  });

  it('should call GET for indicadores', () => {
    service.consultarIndicadoresEnEjecucionesFinalizadas().subscribe();
    const req = httpMock.expectOne(r => r.url.includes('/finalizadas/indicadores'));
    expect(req.request.method).toBe('GET');
    req.flush([]);
  });

  it('should call GET for consultarPorAreaPaginado with params', () => {
    service.consultarPorAreaPaginado('area-1', 0, 10).subscribe();
    const req = httpMock.expectOne(r => r.url.includes('/actividades/area/area-1/paginado'));
    expect(req.request.method).toBe('GET');
    expect(req.request.params.get('pagina')).toBe('0');
    expect(req.request.params.get('tamano')).toBe('10');
    req.flush({ content: [], totalElements: 0 });
  });

  it('should call GET for consultarPorDireccionPaginado with params', () => {
    service.consultarPorDireccionPaginado('dir-1', 1, 10).subscribe();
    const req = httpMock.expectOne(r => r.url.includes('/actividades/direccion/dir-1/paginado'));
    expect(req.request.method).toBe('GET');
    expect(req.request.params.get('pagina')).toBe('1');
    expect(req.request.params.get('tamano')).toBe('10');
    req.flush({ content: [], totalElements: 0 });
  });

  it('should call GET for consultarPorSubareaPaginado with params', () => {
    service.consultarPorSubareaPaginado('sub-1', 0, 10).subscribe();
    const req = httpMock.expectOne(r => r.url.includes('/actividades/subarea/sub-1/paginado'));
    expect(req.request.method).toBe('GET');
    expect(req.request.params.get('pagina')).toBe('0');
    expect(req.request.params.get('tamano')).toBe('10');
    req.flush({ content: [], totalElements: 0 });
  });

  it('should call GET for consultarEjecucionesPaginado with params', () => {
    service.consultarEjecucionesPaginado('act-1', 0, 10).subscribe();
    const req = httpMock.expectOne(r => r.url.includes('/actividades/ejecuciones/act-1/paginado'));
    expect(req.request.method).toBe('GET');
    expect(req.request.params.get('pagina')).toBe('0');
    expect(req.request.params.get('tamano')).toBe('10');
    req.flush({ content: [], totalElements: 0 });
  });

  it('should call GET for consultarParticipantesPorEjecucionPaginado with params', () => {
    service.consultarParticipantesPorEjecucionPaginado('ej-1', 0, 10).subscribe();
    const req = httpMock.expectOne(r => r.url.includes('/actividades/ejecuciones/ejecucion/participantes/ej-1/paginado'));
    expect(req.request.method).toBe('GET');
    expect(req.request.params.get('pagina')).toBe('0');
    expect(req.request.params.get('tamano')).toBe('10');
    req.flush({ content: [], totalElements: 0 });
  });
});
