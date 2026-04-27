import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { of, throwError } from 'rxjs';

import { HomePrimaryButtonsComponent } from './home-primary-buttons.component';
import { ExcelReportService } from 'src/app/shared/service/excel-report.service';
import { StateService } from 'src/app/shared/service/state.service';
import { StateProps } from 'src/app/shared/model/state.enum';

describe('HomePrimaryButtonsComponent', () => {
  let component: HomePrimaryButtonsComponent;
  let fixture: ComponentFixture<HomePrimaryButtonsComponent>;
  let mockExcelService: jasmine.SpyObj<ExcelReportService>;
  let mockStateService: jasmine.SpyObj<StateService>;

  beforeEach(() => {
    mockExcelService = jasmine.createSpyObj('ExcelReportService', ['generarInformeDireccion']);
    mockExcelService.generarInformeDireccion.and.returnValue(of(void 0));
    mockStateService = jasmine.createSpyObj('StateService', ['getState']);
    mockStateService.getState.and.returnValue({ rol: 'ADMINISTRADOR_DIRECCION' });

    TestBed.configureTestingModule({
      declarations: [HomePrimaryButtonsComponent],
      providers: [
        { provide: ExcelReportService, useValue: mockExcelService },
        { provide: StateService, useValue: mockStateService }
      ],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(HomePrimaryButtonsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have generandoExcel as false initially', () => {
    expect(component.generandoExcel).toBeFalse();
  });

  describe('generarExcel', () => {
    it('should call service and reset flag on success', () => {
      component.generarExcel();
      expect(mockExcelService.generarInformeDireccion).toHaveBeenCalledWith('Dirección de Bienestar y Evangelización');
      expect(component.generandoExcel).toBeFalse();
    });

    it('should not call service if already generating', () => {
      component.generandoExcel = true;
      component.generarExcel();
      expect(mockExcelService.generarInformeDireccion).not.toHaveBeenCalled();
    });

    it('should not call service when user is collaborator', () => {
      mockStateService.getState.and.returnValue({ rol: 'COLABORADOR' });
      component.generarExcel();
      expect(mockExcelService.generarInformeDireccion).not.toHaveBeenCalled();
    });

    it('should handle error and reset flag', () => {
      mockExcelService.generarInformeDireccion.and.returnValue(throwError(() => new Error('fail')));
      spyOn(window, 'alert');
      component.generarExcel();
      expect(component.generandoExcel).toBeFalse();
      expect(window.alert).toHaveBeenCalled();
    });
  });

  describe('scrollToFilters', () => {
    it('should not throw when element not found', () => {
      expect(() => component.scrollToFilters()).not.toThrow();
    });

    it('should scroll to element when found', () => {
      const fakeEl = document.createElement('div');
      fakeEl.id = 'home-filters-section';
      document.body.appendChild(fakeEl);
      spyOn(fakeEl, 'scrollIntoView');
      component.scrollToFilters();
      expect(fakeEl.scrollIntoView).toHaveBeenCalledWith({ behavior: 'smooth', block: 'start' });
      document.body.removeChild(fakeEl);
    });
  });
});
