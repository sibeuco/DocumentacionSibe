import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { BottomDataComponent } from './bottom-data.component';

describe('BottomDataComponent', () => {
  let component: BottomDataComponent;
  let fixture: ComponentFixture<BottomDataComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BottomDataComponent],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(BottomDataComponent);
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
    const filters = { mes: 'Febrero', anno: 2025 } as any;
    component.filtersRequest = filters;
    fixture.detectChanges();
    expect(component.filtersRequest).toEqual(filters);
  });

  it('should have default colors and title defined', () => {
    expect(component.participantesColor).toBeDefined();
    expect(component.asistenciasColor).toBeDefined();
    expect(component.title).toBeDefined();
  });
});
