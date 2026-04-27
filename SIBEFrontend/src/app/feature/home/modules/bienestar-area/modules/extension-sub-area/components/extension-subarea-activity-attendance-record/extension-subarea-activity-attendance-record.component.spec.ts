import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { ExtensionSubareaActivityAttendanceRecordComponent } from './extension-subarea-activity-attendance-record.component';

describe('ExtensionSubareaActivityAttendanceRecordComponent', () => {
  let component: ExtensionSubareaActivityAttendanceRecordComponent;
  let fixture: ComponentFixture<ExtensionSubareaActivityAttendanceRecordComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ExtensionSubareaActivityAttendanceRecordComponent],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(ExtensionSubareaActivityAttendanceRecordComponent);
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
