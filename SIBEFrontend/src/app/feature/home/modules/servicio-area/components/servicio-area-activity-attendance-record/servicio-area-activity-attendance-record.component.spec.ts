import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { ServicioAreaActivityAttendanceRecordComponent } from './servicio-area-activity-attendance-record.component';

describe('ServicioAreaActivityAttendanceRecordComponent', () => {
  let component: ServicioAreaActivityAttendanceRecordComponent;
  let fixture: ComponentFixture<ServicioAreaActivityAttendanceRecordComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ServicioAreaActivityAttendanceRecordComponent],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(ServicioAreaActivityAttendanceRecordComponent);
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
