import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { ServicioAreaComponent } from './servicio-area.component';

describe('ServicioAreaComponent', () => {
  let component: ServicioAreaComponent;
  let fixture: ComponentFixture<ServicioAreaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ServicioAreaComponent],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(ServicioAreaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have filtersRequest as null initially', () => {
    expect(component.filtersRequest).toBeNull();
  });

  describe('onFiltersChanged', () => {
    it('should set filtersRequest with the provided filters', () => {
      const filters = {
        mes: 'Enero', anno: 2024, semestre: '2024-1',
        programaAcademico: '', tipoProgramaAcademico: '',
        centroCostos: '', tipoParticipante: '', indicador: ''
      } as any;
      component.onFiltersChanged(filters);
      expect(component.filtersRequest).toEqual(filters);
    });

    it('should update filtersRequest on subsequent calls', () => {
      const f1 = { mes: 'Enero', anno: 2024 } as any;
      const f2 = { mes: 'Febrero', anno: 2025 } as any;
      component.onFiltersChanged(f1);
      component.onFiltersChanged(f2);
      expect(component.filtersRequest).toEqual(f2);
    });
  });
});
