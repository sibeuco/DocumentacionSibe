import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { TopDataComponent } from './top-data.component';

describe('TopDataComponent', () => {
  let component: TopDataComponent;
  let fixture: ComponentFixture<TopDataComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TopDataComponent],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(TopDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have filtersRequest as null initially', () => {
    expect(component.filtersRequest).toBeNull();
  });

  it('should accept filtersRequest input', () => {
    const filters = { mes: 'Enero', anno: 2024 } as any;
    component.filtersRequest = filters;
    fixture.detectChanges();
    expect(component.filtersRequest).toEqual(filters);
  });

  it('should have tipoEstructura defined', () => {
    expect(component.tipoEstructura).toBeDefined();
  });

  it('should have nombreArea defined', () => {
    expect(component.nombreArea).toBeDefined();
  });
});
