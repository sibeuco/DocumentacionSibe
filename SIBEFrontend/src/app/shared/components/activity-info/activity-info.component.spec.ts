import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';
import { Router } from '@angular/router';

import { ActivityInfoComponent } from './activity-info.component';
import { ActivityInfo } from '../../model/activity.model';

describe('ActivityInfoComponent', () => {
  let component: ActivityInfoComponent;
  let fixture: ComponentFixture<ActivityInfoComponent>;
  let router: Router;

  const mockActivityInfo: ActivityInfo = {
    nombre: 'Test Activity',
    colaborador: 'John Doe',
    objetivo: 'Test Objective',
    indicador: 'Test Indicator',
    fechaProgramada: '2025-06-15',
    estado: 'FINALIZADO',
    fechaRealizacion: '2025-06-20'
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule],
      declarations: [ActivityInfoComponent],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(ActivityInfoComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    sessionStorage.removeItem('selectedActivityInfo');
  });

  afterEach(() => {
    sessionStorage.removeItem('selectedActivityInfo');
  });

  it('should create', () => {
    fixture.detectChanges();
    expect(component).toBeTruthy();
  });

  it('should use @Input actividad when no state or storage is available', () => {
    component.actividad = mockActivityInfo;
    fixture.detectChanges();

    expect(component.actividadSeleccionada).toBeTruthy();
    expect(component.actividadSeleccionada!.nombre).toBe('Test Activity');
  });

  it('should set error message when no activity is available', () => {
    component.actividad = null;
    fixture.detectChanges();

    expect(component.error).toBe('No hay actividad seleccionada para mostrar.');
  });

  it('should load activity from sessionStorage', () => {
    const storedData = { actividad: mockActivityInfo, ejecucion: null };
    sessionStorage.setItem('selectedActivityInfo', JSON.stringify(storedData));

    fixture.detectChanges();

    expect(component.actividadSeleccionada).toBeTruthy();
    expect(component.actividadSeleccionada!.nombre).toBe('Test Activity');
  });

  it('should format date as dd/mm/yyyy', () => {
    fixture.detectChanges();
    const result = component.formatearFechaProgramada('2025-06-15');
    expect(result).toBe('15/06/2025');
  });

  it('should return empty string for undefined date', () => {
    fixture.detectChanges();
    expect(component.formatearFechaProgramada(undefined)).toBe('');
    expect(component.formatearFechaRealizacion(undefined)).toBe('');
  });

  it('should detect FINALIZADO status', () => {
    component.actividad = mockActivityInfo;
    fixture.detectChanges();

    expect(component.esActividadFinalizada()).toBeTrue();
  });

  it('should detect FINALIZADA status', () => {
    component.actividad = { ...mockActivityInfo, estado: 'FINALIZADA' };
    fixture.detectChanges();

    expect(component.esActividadFinalizada()).toBeTrue();
  });

  it('should return false for non-finalized status', () => {
    component.actividad = { ...mockActivityInfo, estado: 'PENDIENTE' };
    fixture.detectChanges();

    expect(component.esActividadFinalizada()).toBeFalse();
  });

  it('should handle invalid JSON in sessionStorage gracefully', () => {
    sessionStorage.setItem('selectedActivityInfo', '{invalid');
    component.actividad = mockActivityInfo;
    fixture.detectChanges();

    // Should fall back to @Input
    expect(component.actividadSeleccionada).toBeTruthy();
    expect(sessionStorage.getItem('selectedActivityInfo')).toBeNull();
  });

  it('should build ActivityInfo from ActivityResponse with nested indicator', () => {
    const activityResponse = {
      nombre: 'Act Response',
      nombreColaborador: 'Jane',
      objetivo: 'Resp Obj',
      indicador: { nombre: 'IndName', tipoIndicador: { tipologiaIndicador: 'Tipología' } }
    };
    const stored = { actividad: activityResponse, ejecucion: null };
    sessionStorage.setItem('selectedActivityInfo', JSON.stringify(stored));

    fixture.detectChanges();

    expect(component.actividadSeleccionada!.indicador).toBe('IndName - Tipología');
    expect(component.actividadSeleccionada!.colaborador).toBe('Jane');
  });

  it('should format fechaRealizacion correctly', () => {
    fixture.detectChanges();
    const result = component.formatearFechaRealizacion('2025-12-25');
    expect(result).toBe('25/12/2025');
  });

  it('should build ActivityInfo with string indicator', () => {
    const stored = {
      actividad: { nombre: 'Test', objetivo: 'Obj', indicador: 'Simple Indicator', colaborador: 'fallback-colab' },
      ejecucion: null
    };
    sessionStorage.setItem('selectedActivityInfo', JSON.stringify(stored));
    fixture.detectChanges();
    expect(component.actividadSeleccionada!.indicador).toBe('Simple Indicator');
    expect(component.actividadSeleccionada!.colaborador).toBe('fallback-colab');
  });

  it('should build ActivityInfo with indicator name but no tipologia', () => {
    const stored = {
      actividad: { nombre: 'Test', objetivo: 'Obj', indicador: { nombre: 'IndOnly' } },
      ejecucion: null
    };
    sessionStorage.setItem('selectedActivityInfo', JSON.stringify(stored));
    fixture.detectChanges();
    expect(component.actividadSeleccionada!.indicador).toBe('IndOnly');
  });

  it('should build ActivityInfo with ejecucion data overriding actividad', () => {
    const stored = {
      actividad: { nombre: 'Act', objetivo: 'Obj', indicador: 'Ind', fechaProgramada: '2025-01-01', estado: 'Pendiente' },
      ejecucion: { fechaProgramada: '2025-06-15', estadoActividad: { nombre: 'Finalizada' }, fechaRealizacion: '2025-06-20' }
    };
    sessionStorage.setItem('selectedActivityInfo', JSON.stringify(stored));
    fixture.detectChanges();
    expect(component.actividadSeleccionada!.estado).toBe('Finalizada');
    expect(component.actividadSeleccionada!.fechaProgramada).toBe('2025-06-15');
    expect(component.actividadSeleccionada!.fechaRealizacion).toBe('2025-06-20');
  });

  it('should load from history state when available', () => {
    const histState = {
      actividad: { nombre: 'From History', objetivo: 'HObj', indicador: 'HInd' },
      ejecucion: null
    };
    spyOnProperty(window, 'history').and.returnValue({ state: histState } as any);
    fixture.detectChanges();
    expect(component.actividadSeleccionada!.nombre).toBe('From History');
  });

  it('should build ActivityInfo with default collaborator', () => {
    const stored = {
      actividad: { nombre: 'NoColab', objetivo: 'Obj', indicador: '' },
      ejecucion: null
    };
    sessionStorage.setItem('selectedActivityInfo', JSON.stringify(stored));
    fixture.detectChanges();
    expect(component.actividadSeleccionada!.colaborador).toBe('Sin colaborador');
  });

  it('should handle ISO date with time in formatearFechaProgramada', () => {
    fixture.detectChanges();
    const result = component.formatearFechaProgramada('2025-06-15T14:30:00Z');
    expect(result).toMatch(/15\/06\/2025/);
  });

  it('should handle parseFechaLocal with invalid date', () => {
    fixture.detectChanges();
    const result = (component as any).parseFechaLocal('not-a-date');
    expect(result instanceof Date).toBeTrue();
  });

  it('should load from navigation state via getCurrentNavigation', () => {
    const navActividad = { nombre: 'NavAct', objetivo: 'NavObj', indicador: 'NavInd' };
    spyOn(router, 'getCurrentNavigation').and.returnValue({
      extras: { state: { actividad: navActividad, ejecucion: null } },
      initialUrl: '' as any, extractedUrl: '' as any, trigger: 'imperative' as any,
      previousNavigation: null, id: 1
    } as any);
    fixture.detectChanges();
    expect(component.actividadSeleccionada!.nombre).toBe('NavAct');
  });

  it('should persist activity to sessionStorage on setActividad', () => {
    const act = { nombre: 'Persist', objetivo: 'Obj', indicador: 'Ind' };
    spyOn(router, 'getCurrentNavigation').and.returnValue({
      extras: { state: { actividad: act, ejecucion: null } },
      initialUrl: '' as any, extractedUrl: '' as any, trigger: 'imperative' as any,
      previousNavigation: null, id: 1
    } as any);
    fixture.detectChanges();
    const stored = sessionStorage.getItem('selectedActivityInfo');
    expect(stored).toBeTruthy();
    const parsed = JSON.parse(stored!);
    expect(parsed.actividad.nombre).toBe('Persist');
  });

  it('should handle storage setItem throwing', () => {
    spyOn(sessionStorage, 'setItem').and.throwError('QuotaExceeded');
    spyOn(sessionStorage, 'removeItem').and.callThrough();
    const act = { nombre: 'Overflow', objetivo: 'Obj', indicador: 'Ind' };
    spyOn(router, 'getCurrentNavigation').and.returnValue({
      extras: { state: { actividad: act, ejecucion: null } },
      initialUrl: '' as any, extractedUrl: '' as any, trigger: 'imperative' as any,
      previousNavigation: null, id: 1
    } as any);
    fixture.detectChanges();
    expect(component.actividadSeleccionada!.nombre).toBe('Overflow');
  });

  it('should skip loading from storage if actividadSeleccionada already set', () => {
    const act = { nombre: 'First', objetivo: 'Obj', indicador: 'Ind' };
    const storedData = { actividad: { nombre: 'Second', objetivo: 'Obj2', indicador: 'Ind2' }, ejecucion: null };
    sessionStorage.setItem('selectedActivityInfo', JSON.stringify(storedData));
    spyOnProperty(window, 'history').and.returnValue({ state: { actividad: act, ejecucion: null } } as any);
    fixture.detectChanges();
    expect(component.actividadSeleccionada!.nombre).toBe('First');
  });

  it('should return false for esActividadFinalizada when estado is undefined', () => {
    component.actividad = { ...mockActivityInfo, estado: undefined as any };
    fixture.detectChanges();
    expect(component.esActividadFinalizada()).toBeFalse();
  });
});
