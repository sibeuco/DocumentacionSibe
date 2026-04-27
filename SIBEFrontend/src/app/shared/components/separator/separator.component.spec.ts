import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { SeparatorComponent } from './separator.component';

describe('SeparatorComponent', () => {
  let component: SeparatorComponent;
  let fixture: ComponentFixture<SeparatorComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SeparatorComponent],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(SeparatorComponent);
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
