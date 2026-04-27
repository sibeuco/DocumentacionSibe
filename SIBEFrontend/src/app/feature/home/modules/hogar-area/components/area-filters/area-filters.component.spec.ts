import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { AreaFiltersComponent } from './area-filters.component';

describe('AreaFiltersComponent', () => {
  let component: AreaFiltersComponent;
  let fixture: ComponentFixture<AreaFiltersComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AreaFiltersComponent],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(AreaFiltersComponent);
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
    it('should emit filtersChanged with filters', () => {
      spyOn(component.filtersChanged, 'emit');
      const filters = { mes: 'Marzo', anno: 2024, semestre: '2024-1', programaAcademico: '', tipoProgramaAcademico: '', centroCostos: '', tipoParticipante: '', indicador: '' } as any;
      component.onFilterApplied(filters);
      expect(component.filtersChanged.emit).toHaveBeenCalledWith(filters);
    });
  });
});
