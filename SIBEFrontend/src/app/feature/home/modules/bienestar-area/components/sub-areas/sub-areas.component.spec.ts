import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { SubAreasComponent } from './sub-areas.component';

describe('SubAreasComponent', () => {
  let component: SubAreasComponent;
  let fixture: ComponentFixture<SubAreasComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SubAreasComponent],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(SubAreasComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have 8 sub-areas defined', () => {
    expect(component.areas.length).toBe(8);
  });

  it('should have areas with required properties', () => {
    component.areas.forEach(area => {
      expect(area.titulo).toBeDefined();
      expect(area.imagen).toBeDefined();
      expect(area.link).toBeDefined();
    });
  });

  it('should include Deportes sub-area', () => {
    const deportes = component.areas.find(a => a.titulo === 'Deportes');
    expect(deportes).toBeDefined();
    expect(deportes!.link).toContain('deportes');
  });

  it('should include Gimnasio sub-area', () => {
    const gimnasio = component.areas.find(a => a.titulo === 'Gimnasio');
    expect(gimnasio).toBeDefined();
  });

  it('should call ngAfterViewInit without error', () => {
    fixture.detectChanges();
    expect(() => component.ngAfterViewInit()).not.toThrow();
  });
});
