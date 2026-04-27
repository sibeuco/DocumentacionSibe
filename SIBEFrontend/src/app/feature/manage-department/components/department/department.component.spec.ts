import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { DepartmentComponent } from './department.component';

describe('DepartmentComponent', () => {
  let component: DepartmentComponent;
  let fixture: ComponentFixture<DepartmentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DepartmentComponent],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(DepartmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('abrirModalEmpleados', () => {
    it('should call uploadDatabaseComponent.abrirModal with empleados', () => {
      const mockUpload = jasmine.createSpyObj('UploadDatabaseComponent', ['abrirModal']);
      component.uploadDatabaseComponent = mockUpload;
      component.abrirModalEmpleados();
      expect(mockUpload.abrirModal).toHaveBeenCalledWith('empleados');
    });
  });

  describe('abrirModalEstudiantes', () => {
    it('should call uploadDatabaseComponent.abrirModal with estudiantes', () => {
      const mockUpload = jasmine.createSpyObj('UploadDatabaseComponent', ['abrirModal']);
      component.uploadDatabaseComponent = mockUpload;
      component.abrirModalEstudiantes();
      expect(mockUpload.abrirModal).toHaveBeenCalledWith('estudiantes');
    });
  });

  describe('onArchivoSeleccionado', () => {
    it('should not throw', () => {
      expect(() => component.onArchivoSeleccionado(new File([], 'test.csv'))).not.toThrow();
    });
  });

  describe('onCargaCompleta', () => {
    it('should not throw', () => {
      expect(() => component.onCargaCompleta({})).not.toThrow();
    });
  });
});
