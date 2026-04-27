import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { SantaMariaAreaComponent } from './santa-maria-area.component';

describe('SantaMariaAreaComponent', () => {
  let component: SantaMariaAreaComponent;
  let fixture: ComponentFixture<SantaMariaAreaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SantaMariaAreaComponent],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(SantaMariaAreaComponent);
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
