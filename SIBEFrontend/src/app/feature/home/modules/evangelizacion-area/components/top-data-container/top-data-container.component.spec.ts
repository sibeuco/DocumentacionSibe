import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { TopDataContainerComponent } from './top-data-container.component';

describe('TopDataContainerComponent', () => {
  let component: TopDataContainerComponent;
  let fixture: ComponentFixture<TopDataContainerComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TopDataContainerComponent],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(TopDataContainerComponent);
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
