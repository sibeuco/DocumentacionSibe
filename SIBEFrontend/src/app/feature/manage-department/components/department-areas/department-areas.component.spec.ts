import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { DepartmentAreasComponent } from './department-areas.component';

describe('DepartmentAreasComponent', () => {
  let component: DepartmentAreasComponent;
  let fixture: ComponentFixture<DepartmentAreasComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DepartmentAreasComponent],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(DepartmentAreasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should render without errors', () => {
    expect(fixture.nativeElement).toBeTruthy();
  });
});
