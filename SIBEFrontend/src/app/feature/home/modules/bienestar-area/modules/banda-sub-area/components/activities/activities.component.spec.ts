import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Router } from '@angular/router';

import { ActivitiesComponent } from './activities.component';

describe('ActivitiesComponent (Banda)', () => {
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

  it('should have default property values', () => {
    expect(component.terminoBusqueda).toBe('');
    expect(component.nombreArea).toBeDefined();
    expect(component.tipoEstructura).toBeDefined();
  });

  describe('onActividadSeleccionada', () => {
    it('should not throw', () => {
      expect(() => component.onActividadSeleccionada({ nombre: 'Test' } as any)).not.toThrow();
    });
  });

  describe('onBuscarActividad', () => {
    it('should set terminoBusqueda', () => {
      component.onBuscarActividad('search');
      expect(component.terminoBusqueda).toBe('search');
    });
  });

  describe('onActividadCreada', () => {
    it('should call recargarActividades when table is available', () => {
      const mockTable = jasmine.createSpyObj('ActivitiesTableComponent', ['recargarActividades']);
      component.activitiesTable = mockTable;
      component.onActividadCreada();
      expect(mockTable.recargarActividades).toHaveBeenCalled();
    });

    it('should not throw when table is undefined', () => {
      component.activitiesTable = undefined as any;
      expect(() => component.onActividadCreada()).not.toThrow();
    });
  });
});
