import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { HomeFiltersComponent } from './home-filters.component';

describe('HomeFiltersComponent', () => {
  let component: HomeFiltersComponent;
  let fixture: ComponentFixture<HomeFiltersComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HomeFiltersComponent],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(HomeFiltersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have filtersChanged output', () => {
    expect(component.filtersChanged).toBeDefined();
  });

  describe('onFilterApplied', () => {
    it('should emit filtersChanged with the filters', () => {
      spyOn(component.filtersChanged, 'emit');
      const filters = { mes: 'Enero', anno: 2024, semestre: '2024-1', programaAcademico: '', tipoProgramaAcademico: '', centroCostos: '', tipoParticipante: '', indicador: '' } as any;
      component.onFilterApplied(filters);
      expect(component.filtersChanged.emit).toHaveBeenCalledWith(filters);
    });
  });
});
