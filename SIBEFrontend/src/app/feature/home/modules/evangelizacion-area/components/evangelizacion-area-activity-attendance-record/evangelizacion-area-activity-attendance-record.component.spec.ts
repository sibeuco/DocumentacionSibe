import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { EvangelizacionAreaActivityAttendanceRecordComponent } from './evangelizacion-area-activity-attendance-record.component';

describe('EvangelizacionAreaActivityAttendanceRecordComponent', () => {
  let component: EvangelizacionAreaActivityAttendanceRecordComponent;
  let fixture: ComponentFixture<EvangelizacionAreaActivityAttendanceRecordComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EvangelizacionAreaActivityAttendanceRecordComponent],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(EvangelizacionAreaActivityAttendanceRecordComponent);
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
