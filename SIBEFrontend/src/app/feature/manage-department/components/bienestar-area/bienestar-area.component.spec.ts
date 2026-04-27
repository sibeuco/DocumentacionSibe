import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { BienestarAreaComponent } from './bienestar-area.component';

describe('BienestarAreaComponent', () => {
  let component: BienestarAreaComponent;
  let fixture: ComponentFixture<BienestarAreaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BienestarAreaComponent],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(BienestarAreaComponent);
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
