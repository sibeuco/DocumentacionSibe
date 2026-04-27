import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { PrincipalHomeComponent } from './principal-home.component';

describe('PrincipalHomeComponent', () => {
  let component: PrincipalHomeComponent;
  let fixture: ComponentFixture<PrincipalHomeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PrincipalHomeComponent],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(PrincipalHomeComponent);
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
