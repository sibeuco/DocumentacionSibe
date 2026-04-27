import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { DeportesSubareaActivityAttendanceRecordComponent } from './deportes-subarea-activity-attendance-record.component';

describe('DeportesSubareaActivityAttendanceRecordComponent', () => {
  let component: DeportesSubareaActivityAttendanceRecordComponent;
  let fixture: ComponentFixture<DeportesSubareaActivityAttendanceRecordComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DeportesSubareaActivityAttendanceRecordComponent],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(DeportesSubareaActivityAttendanceRecordComponent);
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
