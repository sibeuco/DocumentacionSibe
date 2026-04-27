import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { EvangelizacionAreaComponent } from './evangelizacion-area.component';

describe('EvangelizacionAreaComponent', () => {
  let component: EvangelizacionAreaComponent;
  let fixture: ComponentFixture<EvangelizacionAreaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EvangelizacionAreaComponent],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(EvangelizacionAreaComponent);
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
