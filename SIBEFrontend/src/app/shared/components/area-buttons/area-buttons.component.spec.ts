import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';

import { AreaButtonsComponent } from './area-buttons.component';
import { ExcelReportService } from '../../service/excel-report.service';
import { StateService } from '../../service/state.service';

describe('AreaButtonsComponent', () => {
  let component: AreaButtonsComponent;
  let fixture: ComponentFixture<AreaButtonsComponent>;
  let mockExcelReportService: jasmine.SpyObj<ExcelReportService>;
  let mockStateService: jasmine.SpyObj<StateService>;
  let router: Router;

  beforeEach(() => {
    mockExcelReportService = jasmine.createSpyObj('ExcelReportService', ['generarInformeArea', 'generarInformeSubarea']);
    mockStateService = jasmine.createSpyObj('StateService', ['getState']);
    mockStateService.getState.and.returnValue({ rol: 'ADMINISTRADOR_DIRECCION' });

    TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientTestingModule],
      declarations: [AreaButtonsComponent],
      providers: [
        { provide: ExcelReportService, useValue: mockExcelReportService },
        { provide: StateService, useValue: mockStateService }
      ],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(AreaButtonsComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    spyOn(router, 'navigate');
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should accept button inputs', () => {
    component.buttons = [
      { text: 'Button 1', icon: 'icon1', link: '/test' }
    ];
    fixture.detectChanges();
    expect(component.buttons.length).toBe(1);
  });

  it('should navigate to link on button click', () => {
    const button = { text: 'Go', icon: 'arrow', link: '/activities' };
    component.onButtonClick(button);
    expect(router.navigate).toHaveBeenCalledWith(['/activities']);
  });

  it('should scroll to target element on button click', () => {
    const element = document.createElement('div');
    element.id = 'targetSection';
    document.body.appendChild(element);
    spyOn(element, 'scrollIntoView');

    const button = { text: 'Scroll', icon: 'down', scrollTarget: 'targetSection' };
    component.navigateTo(button);

    expect(element.scrollIntoView).toHaveBeenCalledWith({ behavior: 'smooth', block: 'start' });
    document.body.removeChild(element);
  });

  it('should call generarInformeArea when tipoEstructura is area', () => {
    mockExcelReportService.generarInformeArea.and.returnValue(of(undefined));
    component.tipoEstructura = 'area';
    component.nombreEstructura = 'Area Test';

    const button = { text: 'Export', icon: 'excel', action: 'generarInforme' };
    component.onButtonClick(button);

    expect(mockExcelReportService.generarInformeArea).toHaveBeenCalledWith('Area Test');
    expect(component.generandoExcel).toBeFalse();
  });

  it('should call generarInformeSubarea when tipoEstructura is subarea', () => {
    mockExcelReportService.generarInformeSubarea.and.returnValue(of(undefined));
    component.tipoEstructura = 'subarea';
    component.nombreEstructura = 'SubArea Test';

    const button = { text: 'Export', icon: 'excel', action: 'generarInforme' };
    component.onButtonClick(button);

    expect(mockExcelReportService.generarInformeSubarea).toHaveBeenCalledWith('SubArea Test');
  });

  it('should prevent double execution while generating excel', () => {
    mockExcelReportService.generarInformeArea.and.returnValue(of(undefined));
    component.tipoEstructura = 'area';
    component.nombreEstructura = 'Area';
    component.generandoExcel = true;

    const button = { text: 'Export', icon: 'excel', action: 'generarInforme' };
    component.onButtonClick(button);

    expect(mockExcelReportService.generarInformeArea).not.toHaveBeenCalled();
  });

  it('should handle excel generation error', () => {
    spyOn(window, 'alert');
    mockExcelReportService.generarInformeArea.and.returnValue(throwError(() => new Error('fail')));
    component.tipoEstructura = 'area';
    component.nombreEstructura = 'Area';

    const button = { text: 'Export', icon: 'excel', action: 'generarInforme' };
    component.onButtonClick(button);

    expect(component.generandoExcel).toBeFalse();
    expect(window.alert).toHaveBeenCalled();
  });

  it('should not generate report when user is collaborator', () => {
    mockStateService.getState.and.returnValue({ rol: 'COLABORADOR' });
    component.tipoEstructura = 'area';
    component.nombreEstructura = 'Area';

    const button = { text: 'Export', icon: 'excel', action: 'generarInforme' };
    component.onButtonClick(button);

    expect(mockExcelReportService.generarInformeArea).not.toHaveBeenCalled();
  });
});
