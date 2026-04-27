import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { AreasComponent } from './areas.component';

describe('AreasComponent', () => {
  let component: AreasComponent;
  let fixture: ComponentFixture<AreasComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AreasComponent],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(AreasComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have 4 areas defined', () => {
    expect(component.areas.length).toBe(4);
  });

  it('should have areas with required properties', () => {
    component.areas.forEach(area => {
      expect(area.titulo).toBeDefined();
      expect(area.imagen).toBeDefined();
      expect(area.link).toBeDefined();
    });
  });

  it('should include Bienestar area', () => {
    const bienestar = component.areas.find(a => a.titulo === 'Bienestar');
    expect(bienestar).toBeDefined();
    expect(bienestar!.link).toContain('bienestar');
  });

  it('should call ngAfterViewInit without error', () => {
    fixture.detectChanges();
    expect(() => component.ngAfterViewInit()).not.toThrow();
  });
});
