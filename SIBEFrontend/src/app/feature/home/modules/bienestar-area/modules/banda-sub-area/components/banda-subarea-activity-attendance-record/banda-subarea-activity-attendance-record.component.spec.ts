import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { BandaSubareaActivityAttendanceRecordComponent } from './banda-subarea-activity-attendance-record.component';

describe('BandaSubareaActivityAttendanceRecordComponent', () => {
  let component: BandaSubareaActivityAttendanceRecordComponent;
  let fixture: ComponentFixture<BandaSubareaActivityAttendanceRecordComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BandaSubareaActivityAttendanceRecordComponent],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(BandaSubareaActivityAttendanceRecordComponent);
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
