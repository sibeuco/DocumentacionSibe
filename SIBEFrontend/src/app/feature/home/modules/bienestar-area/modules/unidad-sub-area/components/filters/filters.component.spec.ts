import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { FiltersComponent } from './filters.component';

describe('FiltersComponent', () => {
  let component: FiltersComponent;
  let fixture: ComponentFixture<FiltersComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FiltersComponent],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(FiltersComponent);
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
