import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { CanchaSubareaActivityAttendanceRecordComponent } from './cancha-subarea-activity-attendance-record.component';

describe('CanchaSubareaActivityAttendanceRecordComponent', () => {
  let component: CanchaSubareaActivityAttendanceRecordComponent;
  let fixture: ComponentFixture<CanchaSubareaActivityAttendanceRecordComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CanchaSubareaActivityAttendanceRecordComponent],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(CanchaSubareaActivityAttendanceRecordComponent);
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
