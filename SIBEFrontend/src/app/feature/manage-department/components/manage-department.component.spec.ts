import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { ManageDepartmentComponent } from './manage-department.component';

describe('ManageDepartmentComponent', () => {
  let component: ManageDepartmentComponent;
  let fixture: ComponentFixture<ManageDepartmentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ManageDepartmentComponent],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(ManageDepartmentComponent);
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
