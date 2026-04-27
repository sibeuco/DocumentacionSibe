import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';

import { GoToAreaButtonComponent } from './go-to-area-button.component';

describe('GoToAreaButtonComponent', () => {
  let component: GoToAreaButtonComponent;
  let fixture: ComponentFixture<GoToAreaButtonComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule],
      declarations: [GoToAreaButtonComponent],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(GoToAreaButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have default text as Botón', () => {
    expect(component.text).toBe('Botón');
  });

  it('should have default routerLink as /', () => {
    expect(component.routerLink).toBe('/');
  });

  it('should accept text input', () => {
    component.text = 'Ir a Área';
    fixture.detectChanges();
    expect(component.text).toBe('Ir a Área');
  });

  it('should accept routerLink as string', () => {
    component.routerLink = '/home/area/1';
    fixture.detectChanges();
    expect(component.routerLink).toBe('/home/area/1');
  });

  it('should accept routerLink as array', () => {
    component.routerLink = ['/home', 'area', '1'];
    fixture.detectChanges();
    expect(component.routerLink).toEqual(['/home', 'area', '1']);
  });
});
