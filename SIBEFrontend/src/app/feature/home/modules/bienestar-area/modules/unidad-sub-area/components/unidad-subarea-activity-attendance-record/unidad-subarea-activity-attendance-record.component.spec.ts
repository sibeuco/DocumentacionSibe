import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { UnidadSubareaActivityAttendanceRecordComponent } from './unidad-subarea-activity-attendance-record.component';

describe('UnidadSubareaActivityAttendanceRecordComponent', () => {
  let component: UnidadSubareaActivityAttendanceRecordComponent;
  let fixture: ComponentFixture<UnidadSubareaActivityAttendanceRecordComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UnidadSubareaActivityAttendanceRecordComponent],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(UnidadSubareaActivityAttendanceRecordComponent);
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
