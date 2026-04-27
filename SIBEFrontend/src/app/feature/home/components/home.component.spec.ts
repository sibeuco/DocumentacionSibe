import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { HomeComponent } from './home.component';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HomeComponent],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(HomeComponent);
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
    it('should set filtersRequest with new filters', () => {
      const filters = {
        mes: 'Enero', anno: 2024, semestre: '2024-1',
        programaAcademico: '', tipoProgramaAcademico: '',
        centroCostos: '', tipoParticipante: '', indicador: ''
      };
      component.onFiltersChanged(filters);
      expect(component.filtersRequest).toEqual(filters);
    });

    it('should update filtersRequest when called again', () => {
      const filters1 = {
        mes: 'Enero', anno: 2024, semestre: '2024-1',
        programaAcademico: '', tipoProgramaAcademico: '',
        centroCostos: '', tipoParticipante: '', indicador: ''
      };
      const filters2 = {
        mes: 'Febrero', anno: 2025, semestre: '2025-1',
        programaAcademico: 'Ing', tipoProgramaAcademico: '',
        centroCostos: '', tipoParticipante: '', indicador: ''
      };
      component.onFiltersChanged(filters1);
      component.onFiltersChanged(filters2);
      expect(component.filtersRequest).toEqual(filters2);
    });
  });
});
