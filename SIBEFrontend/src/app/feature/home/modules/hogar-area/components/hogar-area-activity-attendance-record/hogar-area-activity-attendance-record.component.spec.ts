import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { HogarAreaActivityAttendanceRecordComponent } from './hogar-area-activity-attendance-record.component';

describe('HogarAreaActivityAttendanceRecordComponent', () => {
  let component: HogarAreaActivityAttendanceRecordComponent;
  let fixture: ComponentFixture<HogarAreaActivityAttendanceRecordComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HogarAreaActivityAttendanceRecordComponent],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(HogarAreaActivityAttendanceRecordComponent);
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
