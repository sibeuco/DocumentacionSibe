import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { AcompanamientoSubareaActivityAttendanceRecordComponent } from './acompanamiento-subarea-activity-attendance-record.component';

describe('AcompanamientoSubareaActivityAttendanceRecordComponent', () => {
  let component: AcompanamientoSubareaActivityAttendanceRecordComponent;
  let fixture: ComponentFixture<AcompanamientoSubareaActivityAttendanceRecordComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AcompanamientoSubareaActivityAttendanceRecordComponent],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(AcompanamientoSubareaActivityAttendanceRecordComponent);
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
