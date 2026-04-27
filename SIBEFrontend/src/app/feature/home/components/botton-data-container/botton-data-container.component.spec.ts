import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { BottonDataContainerComponent } from './botton-data-container.component';

describe('BottonDataContainerComponent', () => {
  let component: BottonDataContainerComponent;
  let fixture: ComponentFixture<BottonDataContainerComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BottonDataContainerComponent],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(BottonDataContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have default input values', () => {
    expect(component.filtersRequest).toBeNull();
    expect(component.tipoEstructura).toBe('DIRECCION');
    expect(component.nombreArea).toBe('Dirección de Bienestar y Evangelización');
    expect(component.participantesColor).toBeDefined();
    expect(component.asistenciasColor).toBeDefined();
    expect(component.title).toBeDefined();
  });

  it('should accept filtersRequest input', () => {
    const filters = { mes: 'Enero', anno: 2024 } as any;
    component.filtersRequest = filters;
    fixture.detectChanges();
    expect(component.filtersRequest).toEqual(filters);
  });
});
