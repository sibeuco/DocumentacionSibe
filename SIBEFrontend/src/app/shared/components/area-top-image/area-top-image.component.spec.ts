import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { AreaTopImageComponent } from './area-top-image.component';

describe('AreaTopImageComponent', () => {
  let component: AreaTopImageComponent;
  let fixture: ComponentFixture<AreaTopImageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AreaTopImageComponent],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(AreaTopImageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have default empty imageSrc', () => {
    expect(component.imageSrc).toBe('');
  });

  it('should have default empty text', () => {
    expect(component.text).toBe('');
  });

  it('should accept imageSrc input', () => {
    component.imageSrc = 'assets/images/test.png';
    fixture.detectChanges();
    expect(component.imageSrc).toBe('assets/images/test.png');
  });

  it('should accept text input', () => {
    component.text = 'Area Title';
    fixture.detectChanges();
    expect(component.text).toBe('Area Title');
  });
});
