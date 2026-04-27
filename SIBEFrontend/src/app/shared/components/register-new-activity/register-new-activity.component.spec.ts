import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { of, throwError } from 'rxjs';
import * as bootstrap from 'bootstrap';

import { RegisterNewActivityComponent } from './register-new-activity.component';
import { IndicatorService } from 'src/app/feature/manage-indicators/service/indicator.service';
import { UserService } from 'src/app/shared/service/user.service';
import { DepartmentService } from 'src/app/shared/service/department.service';
import { AreaService } from 'src/app/shared/service/area.service';
import { SubAreaService } from 'src/app/shared/service/subarea.service';
import { ActivityService } from 'src/app/shared/service/activity.service';
import { StateService } from 'src/app/shared/service/state.service';

describe('RegisterNewActivityComponent', () => {
  let component: RegisterNewActivityComponent;
  let fixture: ComponentFixture<RegisterNewActivityComponent>;
  let mockIndicatorService: jasmine.SpyObj<IndicatorService>;
  let mockUserService: jasmine.SpyObj<UserService>;
  let mockDepartmentService: jasmine.SpyObj<DepartmentService>;
  let mockAreaService: jasmine.SpyObj<AreaService>;
  let mockSubAreaService: jasmine.SpyObj<SubAreaService>;
  let mockActivityService: jasmine.SpyObj<ActivityService>;
  let mockStateService: jasmine.SpyObj<StateService>;

  beforeEach(() => {
    mockIndicatorService = jasmine.createSpyObj('IndicatorService', ['consultarIndicadoresParaActividades']);
    mockUserService = jasmine.createSpyObj('UserService', ['consultarUsuarios']);
    mockDepartmentService = jasmine.createSpyObj('DepartmentService', ['consultarDirecciones', 'consultarPorNombre']);
    mockAreaService = jasmine.createSpyObj('AreaService', ['consultarAreas', 'consultarPorNombre']);
    mockSubAreaService = jasmine.createSpyObj('SubAreaService', ['consultarSubareas', 'consultarPorNombre']);
    mockActivityService = jasmine.createSpyObj('ActivityService', ['agregarNuevaActividad']);
    mockStateService = jasmine.createSpyObj('StateService', ['getState']);

    mockIndicatorService.consultarIndicadoresParaActividades.and.returnValue(of([{ identificador: 'ind-1', nombre: 'Ind 1', tipoIndicador: { identificador: 'ti-1', tipologiaIndicador: 'Tipo 1' }, temporalidad: 'Mensual', proyecto: { identificador: 'p-1', nombre: 'Proyecto 1' }, publicosInteres: [] }] as any));
    mockUserService.consultarUsuarios.and.returnValue(of([{ identificador: 'usr-1', nombres: 'Juan' }] as any));
    mockDepartmentService.consultarDirecciones.and.returnValue(of([{ identificador: 'dir-1', nombre: 'Dir 1' }] as any));
    mockAreaService.consultarAreas.and.returnValue(of([{ identificador: 'area-1', nombre: 'Area 1', tipoArea: 'GENERAL' }] as any));
    mockSubAreaService.consultarSubareas.and.returnValue(of([{ identificador: 'sub-1', nombre: 'Sub 1' }] as any));
    mockDepartmentService.consultarPorNombre.and.returnValue(of({ identificador: 'dir-1', nombre: 'Dir 1' }));
    mockAreaService.consultarPorNombre.and.returnValue(of({ identificador: 'area-1', nombre: 'Area 1', tipoArea: 'AREA' }));
    mockSubAreaService.consultarPorNombre.and.returnValue(of({ identificador: 'sub-1', nombre: 'Sub 1' }));
    mockActivityService.agregarNuevaActividad.and.returnValue(of({ valor: 'ok' }));
    mockStateService.getState.and.returnValue(null);

    TestBed.configureTestingModule({
      imports: [FormsModule, ReactiveFormsModule, HttpClientTestingModule, RouterTestingModule],
      declarations: [RegisterNewActivityComponent],
      providers: [
        { provide: IndicatorService, useValue: mockIndicatorService },
        { provide: UserService, useValue: mockUserService },
        { provide: DepartmentService, useValue: mockDepartmentService },
        { provide: AreaService, useValue: mockAreaService },
        { provide: SubAreaService, useValue: mockSubAreaService },
        { provide: ActivityService, useValue: mockActivityService },
        { provide: StateService, useValue: mockStateService }
      ],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(RegisterNewActivityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    it('should load indicators', () => {
      expect(mockIndicatorService.consultarIndicadoresParaActividades).toHaveBeenCalled();
      expect(component.indicadores.length).toBe(1);
    });

    it('should load users', () => {
      expect(mockUserService.consultarUsuarios).toHaveBeenCalled();
      expect(component.usuarios.length).toBe(1);
    });

    it('should load organizational structures', () => {
      expect(mockDepartmentService.consultarDirecciones).toHaveBeenCalled();
      expect(mockAreaService.consultarAreas).toHaveBeenCalled();
      expect(mockSubAreaService.consultarSubareas).toHaveBeenCalled();
    });
  });

  describe('ngOnChanges', () => {
    it('should preload area when inputs change', () => {
      component.nombreArea = 'Dir Test';
      component.tipoEstructura = 'direccion';
      component.ngOnChanges({
        nombreArea: { currentValue: 'Dir Test', previousValue: '', firstChange: false, isFirstChange: () => false }
      });
      expect(mockDepartmentService.consultarPorNombre).toHaveBeenCalledWith('Dir Test');
    });
  });

  describe('buscarYPrecargarArea', () => {
    it('should use departmentService for direccion', () => {
      component.nombreArea = 'Dir Test';
      component.tipoEstructura = 'direccion';
      component.buscarYPrecargarArea();
      expect(mockDepartmentService.consultarPorNombre).toHaveBeenCalledWith('Dir Test');
    });

    it('should use areaService for area', () => {
      component.nombreArea = 'Area Test';
      component.tipoEstructura = 'area';
      component.buscarYPrecargarArea();
      expect(mockAreaService.consultarPorNombre).toHaveBeenCalledWith('Area Test');
    });

    it('should use subAreaService for subarea', () => {
      component.nombreArea = 'Sub Test';
      component.tipoEstructura = 'subarea';
      component.buscarYPrecargarArea();
      expect(mockSubAreaService.consultarPorNombre).toHaveBeenCalledWith('Sub Test');
    });

    it('should not call service when no inputs', () => {
      mockDepartmentService.consultarPorNombre.calls.reset();
      component.nombreArea = '';
      component.tipoEstructura = '';
      component.buscarYPrecargarArea();
      expect(mockDepartmentService.consultarPorNombre).not.toHaveBeenCalled();
    });

    it('should set area and tipoArea on success', () => {
      component.nombreArea = 'Dir Test';
      component.tipoEstructura = 'direccion';
      component.buscarYPrecargarArea();
      expect(component.actividad.area).toBe('dir-1');
      expect(component.areaSeleccionada).toBe('dir-1');
    });
  });

  describe('agregarFecha / eliminarFecha', () => {
    it('should show error when no date is set', () => {
      component.fechaTemporal = '';
      (component as any).agregarFecha();
      expect(component.errorFecha).toContain('seleccione una fecha');
    });

    it('should show error for past date', () => {
      component.fechaTemporal = '2020-01-01';
      (component as any).agregarFecha();
      expect(component.errorFecha).toContain('anterior');
    });

    it('should add valid future date', () => {
      component.fechaTemporal = '2030-12-15';
      (component as any).agregarFecha();
      expect(component.actividad.fechasProgramadas.length).toBe(1);
      expect(component.actividad.fechasProgramadas[0]).toBe('2030-12-15');
    });

    it('should not add duplicate date', () => {
      component.fechaTemporal = '2030-12-15';
      (component as any).agregarFecha();
      component.fechaTemporal = '2030-12-15';
      (component as any).agregarFecha();
      expect(component.actividad.fechasProgramadas.length).toBe(1);
    });

    it('should remove date at index', () => {
      component.actividad.fechasProgramadas = ['2030-06-01', '2030-07-01'];
      (component as any).eliminarFecha(0);
      expect(component.actividad.fechasProgramadas.length).toBe(1);
      expect(component.actividad.fechasProgramadas[0]).toBe('2030-07-01');
    });
  });

  describe('onTipoEstructuraChange', () => {
    it('should not clear area when parent inputs are set', () => {
      component.nombreArea = 'Test';
      component.tipoEstructura = 'direccion';
      component.actividad.area = 'dir-1';
      component.onTipoEstructuraChange();
      expect(component.actividad.area).toBe('dir-1');
    });
  });

  describe('cargarIndicadores error handling', () => {
    it('should handle error silently', () => {
      mockIndicatorService.consultarIndicadoresParaActividades.and.returnValue(throwError(() => new Error('fail')));
      component.cargarIndicadores();
      expect(component.indicadores.length).toBe(1); // from beforeEach
    });
  });

  describe('cargarUsuarios error handling', () => {
    it('should handle error silently', () => {
      mockUserService.consultarUsuarios.and.returnValue(throwError(() => new Error('fail')));
      component.cargarUsuarios();
      expect(component.usuarios.length).toBe(1); // from beforeEach
    });
  });

  describe('cargarEstructurasOrganizacionales error handling', () => {
    it('should handle error on areas', () => {
      mockAreaService.consultarAreas.and.returnValue(throwError(() => new Error('fail')));
      component.cargarEstructurasOrganizacionales();
      expect(component.areas.length).toBe(1); // from beforeEach
    });

    it('should handle error on subareas', () => {
      mockSubAreaService.consultarSubareas.and.returnValue(throwError(() => new Error('fail')));
      component.cargarEstructurasOrganizacionales();
      expect(component.subareas.length).toBe(1); // from beforeEach
    });

    it('should handle error on direcciones', () => {
      mockDepartmentService.consultarDirecciones.and.returnValue(throwError(() => new Error('fail')));
      component.cargarEstructurasOrganizacionales();
      expect(component.direcciones.length).toBe(1); // from beforeEach
    });
  });

  describe('determinarTipoArea', () => {
    it('should return DIRECCION for direccion', () => {
      component.tipoEstructura = 'direccion';
      expect(component.determinarTipoArea()).toBe('DIRECCION');
    });

    it('should return AREA for area', () => {
      component.tipoEstructura = 'area';
      expect(component.determinarTipoArea()).toBe('AREA');
    });

    it('should return SUBAREA for subarea', () => {
      component.tipoEstructura = 'subarea';
      expect(component.determinarTipoArea()).toBe('SUBAREA');
    });

    it('should return empty string for unknown', () => {
      component.tipoEstructura = '' as any;
      expect(component.determinarTipoArea()).toBe('');
    });
  });

  describe('trackByIndex', () => {
    it('should return the index', () => {
      expect(component.trackByIndex(3)).toBe(3);
    });
  });

  describe('validarFormulario', () => {
    it('should return true when all fields are filled', () => {
      component.actividad = {
        nombre: 'Test', objetivo: 'Obj', semestre: '2025-1', rutaInsumos: '/path',
        indicador: '', colaborador: 'usr-1', area: 'dir-1', tipoArea: 'DIRECCION',
        fechasProgramadas: ['2030-01-01']
      };
      component.indicadorSeleccionado = 'ind-1';
      expect(component.validarFormulario()).toBeTrue();
    });

    it('should return false when nombre is empty', () => {
      component.actividad.nombre = '';
      expect(component.validarFormulario()).toBeFalse();
    });

    it('should return false when no fechas', () => {
      component.actividad = {
        nombre: 'T', objetivo: 'O', semestre: 'S', rutaInsumos: 'R',
        indicador: '', colaborador: 'C', area: 'A', tipoArea: 'T',
        fechasProgramadas: []
      };
      component.indicadorSeleccionado = 'ind-1';
      expect(component.validarFormulario()).toBeFalse();
    });

    it('should return false when indicador is empty', () => {
      component.actividad = {
        nombre: 'T', objetivo: 'O', semestre: 'S', rutaInsumos: 'R',
        indicador: '', colaborador: 'C', area: 'A', tipoArea: 'T',
        fechasProgramadas: ['2030-01-01']
      };
      component.indicadorSeleccionado = '';
      expect(component.validarFormulario()).toBeFalse();
    });
  });

  describe('registrarActividad', () => {
    beforeEach(() => {
      component.actividad = {
        nombre: 'Test', objetivo: 'Obj', semestre: '2025-1', rutaInsumos: '/path',
        indicador: '', colaborador: 'usr-1', area: 'dir-1', tipoArea: 'DIRECCION',
        fechasProgramadas: ['2030-01-01']
      };
      component.indicadorSeleccionado = 'ind-1';
      component.tipoEstructura = 'direccion';
    });

    it('should not register when form is invalid', () => {
      component.actividad.nombre = '';
      component.registrarActividad();
      expect(component.error).toContain('campos requeridos');
      expect(mockActivityService.agregarNuevaActividad).not.toHaveBeenCalled();
    });

    it('should get creador from stateService', () => {
      mockStateService.getState.and.returnValue({ identificador: 'creator-1' });
      component.registrarActividad();
      expect(mockActivityService.agregarNuevaActividad).toHaveBeenCalled();
      const request = mockActivityService.agregarNuevaActividad.calls.mostRecent().args[0];
      expect(request.creador).toBe('creator-1');
    });

    it('should fallback to JWT token for creador', () => {
      mockStateService.getState.and.returnValue(null);
      const payload = btoa(JSON.stringify({ identificador: 'token-user-1' }));
      window.sessionStorage.setItem('Authorization', 'header.' + payload + '.sig');
      component.registrarActividad();
      expect(mockActivityService.agregarNuevaActividad).toHaveBeenCalled();
      const request = mockActivityService.agregarNuevaActividad.calls.mostRecent().args[0];
      expect(request.creador).toBe('token-user-1');
      window.sessionStorage.removeItem('Authorization');
    });

    it('should error when no creador available', () => {
      mockStateService.getState.and.returnValue(null);
      window.sessionStorage.removeItem('Authorization');
      component.registrarActividad();
      expect(component.error).toContain('información del usuario');
      expect(component.cargando).toBeFalse();
    });

    it('should error when indicador is empty after validation bypass', () => {
      mockStateService.getState.and.returnValue({ identificador: 'c1' });
      component.indicadorSeleccionado = '   ';
      spyOn(component, 'validarFormulario').and.returnValue(true);
      component.registrarActividad();
      expect(component.error).toContain('indicador');
    });

    it('should error when area is empty after validation bypass', () => {
      mockStateService.getState.and.returnValue({ identificador: 'c1' });
      component.actividad.area = '   ';
      spyOn(component, 'validarFormulario').and.returnValue(true);
      component.registrarActividad();
      expect(component.error).toContain('área');
    });

    it('should error when tipoArea cannot be determined after validation bypass', () => {
      mockStateService.getState.and.returnValue({ identificador: 'c1' });
      component.tipoEstructura = '' as any;
      spyOn(component, 'validarFormulario').and.returnValue(true);
      component.registrarActividad();
      expect(component.error).toContain('tipo de área');
    });

    it('should call service and emit on success', () => {
      mockStateService.getState.and.returnValue({ identificador: 'c1' });
      spyOn(component.actividadCreada, 'emit');
      component.registrarActividad();
      expect(mockActivityService.agregarNuevaActividad).toHaveBeenCalled();
      expect(component.mensajeExito).toContain('exitosamente');
      expect(component.actividadCreada.emit).toHaveBeenCalled();
    });

    it('should handle error with mensaje', () => {
      mockStateService.getState.and.returnValue({ identificador: 'c1' });
      mockActivityService.agregarNuevaActividad.and.returnValue(throwError(() => ({
        error: { mensaje: 'Actividad duplicada' }
      })));
      component.registrarActividad();
      expect(component.error).toBe('Actividad duplicada');
      expect(component.cargando).toBeFalse();
    });

    it('should handle error with message', () => {
      mockStateService.getState.and.returnValue({ identificador: 'c1' });
      mockActivityService.agregarNuevaActividad.and.returnValue(throwError(() => ({
        error: { message: 'Bad request' }
      })));
      component.registrarActividad();
      expect(component.error).toBe('Bad request');
    });

    it('should handle error with string error body', () => {
      mockStateService.getState.and.returnValue({ identificador: 'c1' });
      mockActivityService.agregarNuevaActividad.and.returnValue(throwError(() => ({
        error: 'String error body'
      })));
      component.registrarActividad();
      expect(component.error).toBe('String error body');
    });

    it('should handle error with no error body', () => {
      mockStateService.getState.and.returnValue({ identificador: 'c1' });
      mockActivityService.agregarNuevaActividad.and.returnValue(throwError(() => ({})));
      component.registrarActividad();
      expect(component.error).toContain('Error al crear');
    });

    it('should handle error with non-string error object', () => {
      mockStateService.getState.and.returnValue({ identificador: 'c1' });
      mockActivityService.agregarNuevaActividad.and.returnValue(throwError(() => ({
        error: { someObj: true }
      })));
      component.registrarActividad();
      expect(component.error).toContain('Error al crear');
    });
  });

  describe('onTipoEstructuraChange with no parent inputs', () => {
    beforeEach(() => {
      component.nombreArea = '';
      component.tipoEstructura = '' as any;
    });

    it('should populate listaFiltrada with direcciones', () => {
      component.tipoEstructura = 'direccion';
      component.onTipoEstructuraChange();
      expect(component.listaFiltrada.length).toBe(1);
      expect(component.listaFiltrada[0].nombre).toBe('Dir 1');
    });

    it('should populate listaFiltrada with areas', () => {
      component.tipoEstructura = 'area';
      component.onTipoEstructuraChange();
      expect(component.listaFiltrada.length).toBe(1);
      expect(component.listaFiltrada[0].nombre).toBe('Area 1');
    });

    it('should populate listaFiltrada with subareas', () => {
      component.tipoEstructura = 'subarea';
      component.onTipoEstructuraChange();
      expect(component.listaFiltrada.length).toBe(1);
      expect(component.listaFiltrada[0].nombre).toBe('Sub 1');
    });

    it('should clear listaFiltrada for empty type', () => {
      component.tipoEstructura = '' as any;
      component.onTipoEstructuraChange();
      expect(component.listaFiltrada.length).toBe(0);
    });

    it('should clear area selection', () => {
      component.actividad.area = 'old-area';
      component.areaSeleccionada = 'old-area';
      component.tipoEstructura = 'direccion';
      component.onTipoEstructuraChange();
      expect(component.actividad.area).toBe('');
      expect(component.areaSeleccionada).toBe('');
    });
  });

  describe('cerrarModal', () => {
    it('should not throw when modal element not found', () => {
      spyOn(document, 'getElementById').and.returnValue(null);
      expect(() => component.cerrarModal()).not.toThrow();
    });
  });

  describe('limpiarFormulario', () => {
    it('should reset all fields', () => {
      component.actividad.nombre = 'Test';
      component.indicadorSeleccionado = 'ind-1';
      component.error = 'some error';
      component.mensajeExito = 'success';
      component.fechaTemporal = '2030-01-01';
      component.limpiarFormulario();
      expect(component.actividad.nombre).toBe('');
      expect(component.indicadorSeleccionado).toBe('');
      expect(component.error).toBe('');
      expect(component.mensajeExito).toBe('');
      expect(component.fechaTemporal).toBe('');
    });

    it('should re-preload area when parent inputs are set', () => {
      component.nombreArea = 'Dir Test';
      component.tipoEstructura = 'direccion';
      mockDepartmentService.consultarPorNombre.calls.reset();
      component.limpiarFormulario();
      expect(mockDepartmentService.consultarPorNombre).toHaveBeenCalledWith('Dir Test');
    });
  });

  describe('buscarYPrecargarArea error handling', () => {
    it('should handle error silently for direccion', () => {
      mockDepartmentService.consultarPorNombre.and.returnValue(throwError(() => new Error('fail')));
      component.nombreArea = 'Dir Test';
      component.tipoEstructura = 'direccion';
      expect(() => component.buscarYPrecargarArea()).not.toThrow();
    });

    it('should not call service for empty tipoEstructura', () => {
      mockDepartmentService.consultarPorNombre.calls.reset();
      component.nombreArea = 'Test';
      component.tipoEstructura = '' as any;
      component.buscarYPrecargarArea();
      expect(mockDepartmentService.consultarPorNombre).not.toHaveBeenCalled();
    });

    it('should handle structure with no identificador', () => {
      mockDepartmentService.consultarPorNombre.and.returnValue(of({ nombre: 'test' } as any));
      component.nombreArea = 'Dir Test';
      component.tipoEstructura = 'direccion';
      component.buscarYPrecargarArea();
      expect(component.actividad.area).toBe('');
    });
  });

  describe('ngOnInit with inputs set', () => {
    it('should preload area when nombreArea and tipoEstructura are set', () => {
      mockDepartmentService.consultarPorNombre.calls.reset();
      const comp2Fixture = TestBed.createComponent(RegisterNewActivityComponent);
      const comp2 = comp2Fixture.componentInstance;
      comp2.nombreArea = 'Dir Init';
      comp2.tipoEstructura = 'direccion';
      comp2Fixture.detectChanges();
      expect(mockDepartmentService.consultarPorNombre).toHaveBeenCalledWith('Dir Init');
    });
  });

  describe('ngOnChanges edge cases', () => {
    it('should not preload when nombreArea is empty', () => {
      mockDepartmentService.consultarPorNombre.calls.reset();
      component.nombreArea = '';
      component.tipoEstructura = 'direccion';
      component.ngOnChanges({
        nombreArea: { currentValue: '', previousValue: 'old', firstChange: false, isFirstChange: () => false }
      });
      expect(mockDepartmentService.consultarPorNombre).not.toHaveBeenCalled();
    });
  });

  describe('registrarActividad JWT id fallback', () => {
    beforeEach(() => {
      component.actividad = {
        nombre: 'Test', objetivo: 'Obj', semestre: '2025-1', rutaInsumos: '/path',
        indicador: '', colaborador: 'usr-1', area: 'dir-1', tipoArea: 'DIRECCION',
        fechasProgramadas: ['2030-01-01']
      };
      component.indicadorSeleccionado = 'ind-1';
      component.tipoEstructura = 'direccion';
    });

    it('should use tokenPayload.id when identificador not present', () => {
      mockStateService.getState.and.returnValue(null);
      const payload = btoa(JSON.stringify({ id: 'token-id-user' }));
      window.sessionStorage.setItem('Authorization', 'header.' + payload + '.sig');
      component.registrarActividad();
      expect(mockActivityService.agregarNuevaActividad).toHaveBeenCalled();
      const request = mockActivityService.agregarNuevaActividad.calls.mostRecent().args[0];
      expect(request.creador).toBe('token-id-user');
      window.sessionStorage.removeItem('Authorization');
    });

    it('should handle invalid JWT token gracefully', () => {
      mockStateService.getState.and.returnValue(null);
      window.sessionStorage.setItem('Authorization', 'header.!!!invalid-base64!!!.sig');
      component.registrarActividad();
      expect(component.error).toContain('información del usuario');
      window.sessionStorage.removeItem('Authorization');
    });

    it('should handle JWT with no identificador or id', () => {
      mockStateService.getState.and.returnValue(null);
      const payload = btoa(JSON.stringify({ email: 'test@test.com' }));
      window.sessionStorage.setItem('Authorization', 'header.' + payload + '.sig');
      component.registrarActividad();
      expect(component.error).toContain('información del usuario');
      window.sessionStorage.removeItem('Authorization');
    });
  });

  describe('buscarYPrecargarArea default switch', () => {
    it('should return for default tipoEstructura', () => {
      component.nombreArea = 'Test';
      component.tipoEstructura = 'unknown' as any;
      mockDepartmentService.consultarPorNombre.calls.reset();
      component.buscarYPrecargarArea();
      expect(mockDepartmentService.consultarPorNombre).not.toHaveBeenCalled();
    });
  });

  describe('cerrarModal with DOM element', () => {
    it('should hide modal and clean up backdrops', fakeAsync(() => {
      const mockModal = { hide: jasmine.createSpy('hide') };
      spyOn(bootstrap.Modal, 'getInstance').and.returnValue(mockModal as any);
      const el = document.createElement('div');
      spyOn(document, 'getElementById').and.returnValue(el);

      const backdrop = document.createElement('div');
      backdrop.classList.add('modal-backdrop');
      document.body.appendChild(backdrop);
      document.body.classList.add('modal-open');

      component.cerrarModal();
      expect(mockModal.hide).toHaveBeenCalled();
      tick(300);

      expect(document.body.classList.contains('modal-open')).toBeFalse();
    }));
  });

  describe('cerrarModal when getInstance returns null', () => {
    it('should create a new Modal and call hide when getInstance returns null', fakeAsync(() => {
      const mockModalInstance = { hide: jasmine.createSpy('hide') };
      spyOn(bootstrap.Modal, 'getInstance').and.returnValue(null);
      spyOn(bootstrap.Modal.prototype, 'hide').and.callFake(function(this: any) {
        mockModalInstance.hide();
      });
      const el = document.createElement('div');
      spyOn(document, 'getElementById').and.returnValue(el);

      component.cerrarModal();
      expect(mockModalInstance.hide).toHaveBeenCalled();
      tick(300);
    }));
  });

  describe('validarFormulario missing field branches', () => {
    beforeEach(() => {
      component.actividad = {
        nombre: 'Test', objetivo: 'Obj', semestre: '2025-1', rutaInsumos: '/path',
        indicador: '', colaborador: 'usr-1', area: 'dir-1', tipoArea: 'DIRECCION',
        fechasProgramadas: ['2030-01-01']
      };
      component.indicadorSeleccionado = 'ind-1';
    });

    it('should return false when objetivo is empty', () => {
      component.actividad.objetivo = '';
      expect(component.validarFormulario()).toBeFalse();
    });

    it('should return false when semestre is empty', () => {
      component.actividad.semestre = '';
      expect(component.validarFormulario()).toBeFalse();
    });

    it('should return false when rutaInsumos is empty', () => {
      component.actividad.rutaInsumos = '';
      expect(component.validarFormulario()).toBeFalse();
    });

    it('should return false when colaborador is empty', () => {
      component.actividad.colaborador = '';
      expect(component.validarFormulario()).toBeFalse();
    });

    it('should return false when area is empty', () => {
      component.actividad.area = '';
      expect(component.validarFormulario()).toBeFalse();
    });

    it('should return false when indicadorSeleccionado is whitespace only', () => {
      component.indicadorSeleccionado = '   ';
      expect(component.validarFormulario()).toBeFalse();
    });
  });

  describe('registrarActividad success path with setTimeout', () => {
    beforeEach(() => {
      component.actividad = {
        nombre: 'Test', objetivo: 'Obj', semestre: '2025-1', rutaInsumos: '/path',
        indicador: '', colaborador: 'usr-1', area: 'dir-1', tipoArea: 'DIRECCION',
        fechasProgramadas: ['2030-01-01']
      };
      component.indicadorSeleccionado = 'ind-1';
      component.tipoEstructura = 'direccion';
    });

    it('should call limpiarFormulario and cerrarModal after timeout on success', fakeAsync(() => {
      mockStateService.getState.and.returnValue({ identificador: 'c1' });
      spyOn(component, 'limpiarFormulario');
      spyOn(component, 'cerrarModal');
      component.registrarActividad();
      expect(component.cargando).toBeFalse();
      expect(component.mensajeExito).toContain('exitosamente');
      tick(1500);
      expect(component.limpiarFormulario).toHaveBeenCalled();
      expect(component.cerrarModal).toHaveBeenCalled();
    }));

    it('should construct activityRequest with trimmed values', () => {
      mockStateService.getState.and.returnValue({ identificador: 'c1' });
      component.actividad.nombre = '  Test  ';
      component.actividad.objetivo = '  Obj  ';
      component.actividad.semestre = '  2025-1  ';
      component.actividad.rutaInsumos = '  /path  ';
      component.indicadorSeleccionado = '  ind-1  ';
      component.actividad.colaborador = '  usr-1  ';
      component.actividad.fechasProgramadas = ['  2030-01-01  '];
      component.registrarActividad();
      const request = mockActivityService.agregarNuevaActividad.calls.mostRecent().args[0];
      expect(request.nombre).toBe('Test');
      expect(request.objetivo).toBe('Obj');
      expect(request.semestre).toBe('2025-1');
      expect(request.rutaInsumos).toBe('/path');
      expect(request.indicador).toBe('ind-1');
      expect(request.colaborador).toBe('usr-1');
      expect(request.fechasProgramadas).toEqual(['2030-01-01']);
      expect(request.area).toEqual({ area: 'dir-1', tipoArea: 'DIRECCION' });
    });
  });

  describe('registrarActividad creador edge cases', () => {
    beforeEach(() => {
      component.actividad = {
        nombre: 'Test', objetivo: 'Obj', semestre: '2025-1', rutaInsumos: '/path',
        indicador: '', colaborador: 'usr-1', area: 'dir-1', tipoArea: 'DIRECCION',
        fechasProgramadas: ['2030-01-01']
      };
      component.indicadorSeleccionado = 'ind-1';
      component.tipoEstructura = 'direccion';
    });

    it('should fall through to JWT when userSession has empty identificador', () => {
      mockStateService.getState.and.returnValue({ identificador: '' });
      const payload = btoa(JSON.stringify({ identificador: 'jwt-user' }));
      window.sessionStorage.setItem('Authorization', 'header.' + payload + '.sig');
      component.registrarActividad();
      expect(mockActivityService.agregarNuevaActividad).toHaveBeenCalled();
      const request = mockActivityService.agregarNuevaActividad.calls.mostRecent().args[0];
      expect(request.creador).toBe('jwt-user');
      window.sessionStorage.removeItem('Authorization');
    });

    it('should error when creador is whitespace only from stateService', () => {
      mockStateService.getState.and.returnValue({ identificador: '   ' });
      window.sessionStorage.removeItem('Authorization');
      component.registrarActividad();
      expect(component.error).toContain('información del usuario');
      expect(component.cargando).toBeFalse();
    });

    it('should fallback to JWT when stateService returns null session', () => {
      mockStateService.getState.and.returnValue(null);
      window.sessionStorage.removeItem('Authorization');
      component.registrarActividad();
      expect(component.error).toContain('información del usuario');
    });

    it('should handle sessionStorage with no Authorization token', () => {
      mockStateService.getState.and.returnValue({ identificador: undefined } as any);
      window.sessionStorage.removeItem('Authorization');
      component.registrarActividad();
      expect(component.error).toContain('información del usuario');
      expect(component.cargando).toBeFalse();
    });
  });

  describe('eliminarFecha clears errorFecha', () => {
    it('should clear errorFecha when a date is removed', () => {
      component.actividad.fechasProgramadas = ['2030-06-01', '2030-07-01'];
      component.errorFecha = 'Esta fecha ya está agregada';
      component.eliminarFecha(1);
      expect(component.errorFecha).toBe('');
      expect(component.actividad.fechasProgramadas.length).toBe(1);
    });
  });

  describe('limpiarFormulario full reset', () => {
    it('should reset colaboradorSeleccionado, areaSeleccionada, and listaFiltrada', () => {
      component.colaboradorSeleccionado = 'usr-1';
      component.areaSeleccionada = 'dir-1';
      component.listaFiltrada = [{ identificador: 'dir-1', nombre: 'Dir 1' }];
      component.errorFecha = 'some error';
      component.limpiarFormulario();
      expect(component.colaboradorSeleccionado).toBe('');
      expect(component.areaSeleccionada).toBe('');
      expect(component.listaFiltrada.length).toBe(0);
      expect(component.errorFecha).toBe('');
    });

    it('should not call buscarYPrecargarArea without parent inputs', () => {
      component.nombreArea = '';
      component.tipoEstructura = '' as any;
      mockDepartmentService.consultarPorNombre.calls.reset();
      component.limpiarFormulario();
      expect(mockDepartmentService.consultarPorNombre).not.toHaveBeenCalled();
    });

    it('should reset fechasProgramadas to empty array', () => {
      component.actividad.fechasProgramadas = ['2030-01-01', '2030-02-01'];
      component.limpiarFormulario();
      expect(component.actividad.fechasProgramadas.length).toBe(0);
    });
  });

  describe('buscarYPrecargarArea error handling for area and subarea', () => {
    it('should handle error silently for area type', () => {
      mockAreaService.consultarPorNombre.and.returnValue(throwError(() => new Error('fail')));
      component.nombreArea = 'Area Test';
      component.tipoEstructura = 'area';
      expect(() => component.buscarYPrecargarArea()).not.toThrow();
    });

    it('should handle error silently for subarea type', () => {
      mockSubAreaService.consultarPorNombre.and.returnValue(throwError(() => new Error('fail')));
      component.nombreArea = 'Sub Test';
      component.tipoEstructura = 'subarea';
      expect(() => component.buscarYPrecargarArea()).not.toThrow();
    });

    it('should set tipoArea correctly for area type', () => {
      component.nombreArea = 'Area Test';
      component.tipoEstructura = 'area';
      component.buscarYPrecargarArea();
      expect(component.actividad.tipoArea).toBe('AREA');
      expect(component.actividad.area).toBe('area-1');
    });

    it('should set tipoArea correctly for subarea type', () => {
      component.nombreArea = 'Sub Test';
      component.tipoEstructura = 'subarea';
      component.buscarYPrecargarArea();
      expect(component.actividad.tipoArea).toBe('SUBAREA');
      expect(component.actividad.area).toBe('sub-1');
    });
  });

  describe('ngOnChanges tipoEstructura change', () => {
    it('should preload area when only tipoEstructura changes', () => {
      mockAreaService.consultarPorNombre.calls.reset();
      component.nombreArea = 'Area Test';
      component.tipoEstructura = 'area';
      component.ngOnChanges({
        tipoEstructura: { currentValue: 'area', previousValue: '', firstChange: false, isFirstChange: () => false }
      });
      expect(mockAreaService.consultarPorNombre).toHaveBeenCalledWith('Area Test');
    });

    it('should not preload when tipoEstructura is empty', () => {
      mockDepartmentService.consultarPorNombre.calls.reset();
      component.nombreArea = 'Test';
      component.tipoEstructura = '' as any;
      component.ngOnChanges({
        tipoEstructura: { currentValue: '', previousValue: 'direccion', firstChange: false, isFirstChange: () => false }
      });
      expect(mockDepartmentService.consultarPorNombre).not.toHaveBeenCalled();
    });
  });

  describe('agregarFecha clears previous error', () => {
    it('should clear errorFecha before validation', () => {
      component.errorFecha = 'previous error';
      component.fechaTemporal = '2030-12-15';
      component.agregarFecha();
      expect(component.errorFecha).toBe('');
    });

    it('should clear fechaTemporal after successful add', () => {
      component.fechaTemporal = '2030-12-15';
      component.agregarFecha();
      expect(component.fechaTemporal).toBe('');
    });
  });
});
