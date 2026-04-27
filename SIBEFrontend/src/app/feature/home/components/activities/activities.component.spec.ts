import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Router } from '@angular/router';

import { ActivitiesComponent } from './activities.component';

describe('ActivitiesComponent (Home)', () => {
  let component: ActivitiesComponent;
  let fixture: ComponentFixture<ActivitiesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ActivitiesComponent],
      providers: [{ provide: Router, useValue: jasmine.createSpyObj('Router', ['navigate']) }],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(ActivitiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have default values', () => {
    expect(component.terminoBusqueda).toBe('');
    expect(component.nombreArea).toBe('Dirección de Bienestar y Evangelización');
    expect(component.tipoEstructura).toBe('direccion');
  });

  describe('onActividadSeleccionada', () => {
    it('should not throw', () => {
      const activity = { nombre: 'Test' } as any;
      expect(() => component.onActividadSeleccionada(activity)).not.toThrow();
    });
  });

  describe('onBuscarActividad', () => {
    it('should set terminoBusqueda', () => {
      component.onBuscarActividad('test');
      expect(component.terminoBusqueda).toBe('test');
    });
  });

  describe('onActividadCreada', () => {
    it('should call activitiesTable.recargarActividades when available', () => {
      const mockTable = jasmine.createSpyObj('ActivitiesTableComponent', ['recargarActividades']);
      component.activitiesTable = mockTable;
      component.onActividadCreada();
      expect(mockTable.recargarActividades).toHaveBeenCalled();
    });

    it('should not throw when activitiesTable is undefined', () => {
      component.activitiesTable = undefined as any;
      expect(() => component.onActividadCreada()).not.toThrow();
    });
  });
});
