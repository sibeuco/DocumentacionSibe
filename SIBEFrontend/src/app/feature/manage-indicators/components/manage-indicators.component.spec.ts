import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { ManageIndicatorsComponent } from './manage-indicators.component';

describe('ManageIndicatorsComponent', () => {
  let component: ManageIndicatorsComponent;
  let fixture: ComponentFixture<ManageIndicatorsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ManageIndicatorsComponent],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(ManageIndicatorsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have the correct selector', () => {
    const element = fixture.nativeElement;
    expect(element).toBeTruthy();
  });
});
