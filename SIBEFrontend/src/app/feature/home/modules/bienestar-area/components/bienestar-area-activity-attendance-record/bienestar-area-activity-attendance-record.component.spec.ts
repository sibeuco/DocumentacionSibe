import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { BienestarAreaActivityAttendanceRecordComponent } from './bienestar-area-activity-attendance-record.component';

describe('BienestarAreaActivityAttendanceRecordComponent', () => {
  let component: BienestarAreaActivityAttendanceRecordComponent;
  let fixture: ComponentFixture<BienestarAreaActivityAttendanceRecordComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BienestarAreaActivityAttendanceRecordComponent],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(BienestarAreaActivityAttendanceRecordComponent);
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
