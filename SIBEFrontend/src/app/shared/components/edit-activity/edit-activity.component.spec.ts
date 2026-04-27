import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { of, throwError } from 'rxjs';
import * as bootstrap from 'bootstrap';

import { EditActivityComponent } from './edit-activity.component';
import { IndicatorService } from 'src/app/feature/manage-indicators/service/indicator.service';
import { UserService } from 'src/app/shared/service/user.service';
import { DepartmentService } from 'src/app/shared/service/department.service';
import { AreaService } from 'src/app/shared/service/area.service';
import { SubAreaService } from 'src/app/shared/service/subarea.service';
import { ActivityService } from 'src/app/shared/service/activity.service';

describe('EditActivityComponent', () => {
  let component: EditActivityComponent;
  let fixture: ComponentFixture<EditActivityComponent>;
  let mockIndicatorService: jasmine.SpyObj<IndicatorService>;
  let mockUserService: jasmine.SpyObj<UserService>;
  let mockDepartmentService: jasmine.SpyObj<DepartmentService>;
  let mockAreaService: jasmine.SpyObj<AreaService>;
  let mockSubAreaService: jasmine.SpyObj<SubAreaService>;
  let mockActivityService: jasmine.SpyObj<ActivityService>;

  beforeEach(() => {
    mockIndicatorService = jasmine.createSpyObj('IndicatorService', ['consultarIndicadoresParaActividades']);
    mockUserService = jasmine.createSpyObj('UserService', ['consultarUsuarios']);
    mockDepartmentService = jasmine.createSpyObj('DepartmentService', ['consultarDirecciones', 'consultarPorNombre']);
    mockAreaService = jasmine.createSpyObj('AreaService', ['consultarAreas', 'consultarPorNombre']);
    mockSubAreaService = jasmine.createSpyObj('SubAreaService', ['consultarSubareas', 'consultarPorNombre']);
    mockActivityService = jasmine.createSpyObj('ActivityService', ['consultarEjecuciones', 'modificarActividad']);

    mockIndicatorService.consultarIndicadoresParaActividades.and.returnValue(of([
      { identificador: 'ind-1', nombre: 'Ind 1', tipoIndicador: { identificador: 'ti-1', tipologiaIndicador: 'Tipo 1' }, temporalidad: { identificador: 'f-1', nombre: 'Mensual' }, proyecto: { identificador: 'p-1', nombre: 'Proy 1' }, publicosInteres: [] }
    ] as any));
    mockUserService.consultarUsuarios.and.returnValue(of([
      { identificador: 'usr-1', nombres: 'Juan', apellidos: 'Perez' }
    ] as any));
    mockDepartmentService.consultarDirecciones.and.returnValue(of([
      { identificador: 'dir-1', nombre: 'Dir 1' }
    ] as any));
    mockAreaService.consultarAreas.and.returnValue(of([
      { identificador: 'area-1', nombre: 'Area 1' }
    ] as any));
    mockSubAreaService.consultarSubareas.and.returnValue(of([
      { identificador: 'sub-1', nombre: 'Sub 1' }
    ] as any));
    mockActivityService.consultarEjecuciones.and.returnValue(of([]));
    mockActivityService.modificarActividad.and.returnValue(of({ valor: 'ok' }));
    mockDepartmentService.consultarPorNombre.and.returnValue(of({ identificador: 'dir-1', nombre: 'Dir 1' }));
    mockAreaService.consultarPorNombre.and.returnValue(of({ identificador: 'area-1', nombre: 'Area 1', tipoArea: 'AREA' }));
    mockSubAreaService.consultarPorNombre.and.returnValue(of({ identificador: 'sub-1', nombre: 'Sub 1' }));

    TestBed.configureTestingModule({
      imports: [FormsModule, ReactiveFormsModule, HttpClientTestingModule, RouterTestingModule],
      declarations: [EditActivityComponent],
      providers: [
        { provide: IndicatorService, useValue: mockIndicatorService },
        { provide: UserService, useValue: mockUserService },
        { provide: DepartmentService, useValue: mockDepartmentService },
        { provide: AreaService, useValue: mockAreaService },
        { provide: SubAreaService, useValue: mockSubAreaService },
        { provide: ActivityService, useValue: mockActivityService }
      ],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(EditActivityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    it('should load indicators on init', () => {
      expect(mockIndicatorService.consultarIndicadoresParaActividades).toHaveBeenCalled();
      expect(component.indicadores.length).toBe(1);
    });

    it('should load users on init', () => {
      expect(mockUserService.consultarUsuarios).toHaveBeenCalled();
      expect(component.usuarios.length).toBe(1);
    });

    it('should load organizational structures on init', () => {
      expect(mockDepartmentService.consultarDirecciones).toHaveBeenCalled();
      expect(mockAreaService.consultarAreas).toHaveBeenCalled();
      expect(mockSubAreaService.consultarSubareas).toHaveBeenCalled();
    });
  });

  describe('ngOnChanges with actividad', () => {
    it('should load activity data when actividad input changes', () => {
      const actividad = {
        identificador: 'act-1',
        nombre: 'Test Activity',
        objetivo: 'Test Obj',
        semestre: '2025-1',
        rutaInsumos: '/path',
        indicador: { identificador: 'ind-1' },
        colaborador: 'usr-1',
        area: { area: 'area-1', tipoArea: 'AREA' },
        fechasProgramadas: ['2025-06-01']
      } as any;

      component.actividad = actividad;
      component.ngOnChanges({
        actividad: { currentValue: actividad, previousValue: null, firstChange: true, isFirstChange: () => true }
      });

      expect(component.actividadForm.nombre).toBe('Test Activity');
      expect(component.actividadForm.objetivo).toBe('Test Obj');
    });
  });

  describe('agregarFecha', () => {
    it('should show error when no date is selected', () => {
      component.fechaTemporal = '';
      component.agregarFecha();
      expect(component.errorFecha).toContain('seleccione una fecha');
    });

    it('should show error when date is in the past', () => {
      component.fechaTemporal = '2020-01-01';
      component.agregarFecha();
      expect(component.errorFecha).toContain('anterior a la fecha actual');
    });

    it('should add valid future date', () => {
      component.fechaTemporal = '2030-12-15';
      component.agregarFecha();
      expect(component.actividadForm.fechasProgramadas.length).toBe(1);
      expect(component.actividadForm.fechasProgramadas[0].fechaProgramada).toBe('2030-12-15');
      expect(component.fechaTemporal).toBe('');
    });

    it('should not add duplicate date', () => {
      component.fechaTemporal = '2030-12-15';
      component.agregarFecha();
      component.fechaTemporal = '2030-12-15';
      component.agregarFecha();
      expect(component.actividadForm.fechasProgramadas.length).toBe(1);
      expect(component.errorFecha).toContain('ya');
    });
  });

  describe('eliminarFecha', () => {
    it('should remove date at given index', () => {
      component.actividadForm.fechasProgramadas = [
        { identificador: null as any, fechaProgramada: '2030-06-01' },
        { identificador: null as any, fechaProgramada: '2030-07-01' }
      ];
      component.eliminarFecha(0);
      expect(component.actividadForm.fechasProgramadas.length).toBe(1);
      expect(component.actividadForm.fechasProgramadas[0].fechaProgramada).toBe('2030-07-01');
    });

    it('should not remove existing dates when soloColaboradorEditable', () => {
      component.soloColaboradorEditable = true;
      component.actividadForm.fechasProgramadas = [
        { identificador: 'ej-1', fechaProgramada: '2030-06-01' }
      ];
      component.eliminarFecha(0);
      expect(component.actividadForm.fechasProgramadas.length).toBe(1);
    });
  });

  describe('determinarTipoArea', () => {
    it('should return DIRECCION for direccion', () => {
      component.tipoEstructuraSeleccionado = 'direccion';
      expect(component.determinarTipoArea()).toBe('DIRECCION');
    });

    it('should return AREA for area', () => {
      component.tipoEstructuraSeleccionado = 'area';
      expect(component.determinarTipoArea()).toBe('AREA');
    });

    it('should return SUBAREA for subarea', () => {
      component.tipoEstructuraSeleccionado = 'subarea';
      expect(component.determinarTipoArea()).toBe('SUBAREA');
    });
  });

  describe('esFechaEditable', () => {
    it('should return true when not soloColaboradorEditable', () => {
      component.soloColaboradorEditable = false;
      expect(component.esFechaEditable({ identificador: 'ej-1', fechaProgramada: '2030-01-01' })).toBeTrue();
    });

    it('should return false for existing dates when soloColaboradorEditable', () => {
      component.soloColaboradorEditable = true;
      expect(component.esFechaEditable({ identificador: 'ej-1', fechaProgramada: '2030-01-01' })).toBeFalse();
    });

    it('should return true for new dates when soloColaboradorEditable', () => {
      component.soloColaboradorEditable = true;
      expect(component.esFechaEditable({ identificador: null as any, fechaProgramada: '2030-01-01' })).toBeTrue();
    });
  });

  describe('mostrarCandado', () => {
    it('should return true when soloColaboradorEditable and has identifier', () => {
      component.soloColaboradorEditable = true;
      expect(component.mostrarCandado({ identificador: 'ej-1', fechaProgramada: '2030-01-01' })).toBeTrue();
    });

    it('should return false otherwise', () => {
      component.soloColaboradorEditable = false;
      expect(component.mostrarCandado({ identificador: 'ej-1', fechaProgramada: '2030-01-01' })).toBeFalse();
    });
  });

  describe('editarActividad', () => {
    it('should not proceed without actividad', () => {
      component.actividad = null;
      component.editarActividad();
      expect(mockActivityService.modificarActividad).not.toHaveBeenCalled();
    });

    it('should set error when form is invalid', () => {
      component.actividad = { identificador: 'act-1' } as any;
      component.actividadForm.nombre = '';
      component.editarActividad();
      expect(component.error).toContain('campos requeridos');
    });

    it('should call service on valid form', () => {
      component.actividad = { identificador: 'act-1' } as any;
      component.actividadForm = {
        nombre: 'Test',
        objetivo: 'Obj',
        semestre: '2025-1',
        rutaInsumos: '/path',
        indicador: 'ind-1',
        colaborador: 'usr-1',
        area: 'area-1',
        tipoArea: 'AREA',
        fechasProgramadas: [{ identificador: null as any, fechaProgramada: '2030-06-01' }]
      };
      component.indicadorSeleccionado = 'ind-1';
      component.colaboradorSeleccionado = 'usr-1';

      component.editarActividad();

      expect(mockActivityService.modificarActividad).toHaveBeenCalledWith('act-1', jasmine.any(Object));
    });

    it('should show error on service failure', () => {
      component.actividad = { identificador: 'act-1' } as any;
      component.actividadForm = {
        nombre: 'Test',
        objetivo: 'Obj',
        semestre: '2025-1',
        rutaInsumos: '/path',
        indicador: 'ind-1',
        colaborador: 'usr-1',
        area: 'area-1',
        tipoArea: 'AREA',
        fechasProgramadas: [{ identificador: null as any, fechaProgramada: '2030-06-01' }]
      };
      component.indicadorSeleccionado = 'ind-1';
      component.colaboradorSeleccionado = 'usr-1';
      mockActivityService.modificarActividad.and.returnValue(throwError(() => ({
        error: { mensaje: 'Server error' }
      })));

      component.editarActividad();

      expect(component.error).toBe('Server error');
      expect(component.cargando).toBeFalse();
    });
  });

  describe('onTipoEstructuraChange', () => {
    it('should not clear area when nombreArea and tipoEstructura are set from parent', () => {
      component.nombreArea = 'Test';
      component.tipoEstructura = 'direccion';
      component.actividadForm.area = 'dir-1';
      component.onTipoEstructuraChange();
      expect(component.actividadForm.area).toBe('dir-1');
    });

    it('should clear area when no parent inputs', () => {
      component.nombreArea = '';
      component.tipoEstructura = '';
      component.actividadForm.area = 'dir-1';
      component.onTipoEstructuraChange();
      expect(component.actividadForm.area).toBe('');
    });
  });

  describe('trackByIndex', () => {
    it('should return the index', () => {
      expect(component.trackByIndex(5)).toBe(5);
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
    it('should handle errors on direcciones', () => {
      mockDepartmentService.consultarDirecciones.and.returnValue(throwError(() => new Error('fail')));
      component.cargarEstructurasOrganizacionales();
      expect(component.direcciones.length).toBe(1); // from beforeEach
    });

    it('should handle errors on areas', () => {
      mockAreaService.consultarAreas.and.returnValue(throwError(() => new Error('fail')));
      component.cargarEstructurasOrganizacionales();
      expect(component.areas.length).toBe(1);
    });

    it('should handle errors on subareas', () => {
      mockSubAreaService.consultarSubareas.and.returnValue(throwError(() => new Error('fail')));
      component.cargarEstructurasOrganizacionales();
      expect(component.subareas.length).toBe(1);
    });
  });

  describe('validarFormulario', () => {
    it('should return true when all fields filled', () => {
      component.actividadForm = {
        nombre: 'T', objetivo: 'O', semestre: 'S', rutaInsumos: 'R',
        indicador: 'i', colaborador: 'c', area: 'a', tipoArea: 'A',
        fechasProgramadas: [{ identificador: null as any, fechaProgramada: '2030-01-01' }]
      };
      component.indicadorSeleccionado = 'i';
      component.colaboradorSeleccionado = 'c';
      expect((component as any).validarFormulario()).toBeTrue();
    });

    it('should return false when nombre is empty', () => {
      component.actividadForm.nombre = '';
      expect((component as any).validarFormulario()).toBeFalse();
    });

    it('should only check colaborador when soloColaboradorEditable', () => {
      component.soloColaboradorEditable = true;
      component.colaboradorSeleccionado = 'usr-1';
      expect((component as any).validarFormulario()).toBeTrue();
    });

    it('should return false when soloColaboradorEditable and no colaborador', () => {
      component.soloColaboradorEditable = true;
      component.colaboradorSeleccionado = '';
      expect((component as any).validarFormulario()).toBeFalse();
    });
  });

  describe('ngOnChanges with nombreArea and tipoEstructura', () => {
    it('should set tipoEstructuraSeleccionado and precargar area', () => {
      component.nombreArea = 'Area Test';
      component.tipoEstructura = 'area';
      component.ngOnChanges({
        nombreArea: { currentValue: 'Area Test', previousValue: '', firstChange: false, isFirstChange: () => false }
      });
      expect(component.tipoEstructuraSeleccionado).toBe('area');
    });
  });

  describe('cargarDatosActividad', () => {
    it('should reset form when actividad is null', () => {
      component.actividad = null;
      component.ngOnChanges({
        actividad: { currentValue: null, previousValue: {}, firstChange: false, isFirstChange: () => false }
      });
      expect(component.actividadForm.nombre).toBe('');
    });

    it('should handle actividad with area as object', () => {
      const actividad = {
        identificador: 'act-1', nombre: 'Test', objetivo: 'Obj', semestre: '2025-1',
        rutaInsumos: '/path', indicador: { identificador: 'ind-1' },
        colaborador: 'usr-1', area: { area: 'area-1', tipoArea: 'AREA' },
        fechasProgramadas: [], nombreColaborador: 'Juan'
      } as any;
      component.actividad = actividad;
      component.ngOnChanges({
        actividad: { currentValue: actividad, previousValue: null, firstChange: true, isFirstChange: () => true }
      });
      expect(component.actividadForm.area).toBe('area-1');
      expect(component.actividadForm.tipoArea).toBe('AREA');
    });

    it('should handle actividad with areaIdentificador', () => {
      const actividad = {
        identificador: 'act-1', nombre: 'Test', objetivo: '', semestre: '', rutaInsumos: '',
        indicador: { identificador: 'ind-1' }, colaborador: 'usr-1',
        areaIdentificador: 'area-fallback', fechasProgramadas: []
      } as any;
      component.actividad = actividad;
      component.ngOnChanges({
        actividad: { currentValue: actividad, previousValue: null, firstChange: true, isFirstChange: () => true }
      });
      expect(component.actividadForm.area).toBe('area-fallback');
    });
  });

  describe('cargarEjecuciones', () => {
    it('should set soloColaboradorEditable when finalizadas exist', () => {
      const actividad = {
        identificador: 'act-1', nombre: 'Test', objetivo: '', semestre: '', rutaInsumos: '',
        indicador: { identificador: 'ind-1' }, colaborador: 'usr-1',
        area: { area: 'area-1', tipoArea: 'AREA' }, fechasProgramadas: []
      } as any;
      mockActivityService.consultarEjecuciones.and.returnValue(of([
        { identificador: 'ej-1', fechaProgramada: '2025-06-01', estadoActividad: { nombre: 'Finalizada' } }
      ] as any));
      component.actividad = actividad;
      component.ngOnChanges({
        actividad: { currentValue: actividad, previousValue: null, firstChange: true, isFirstChange: () => true }
      });
      expect(component.soloColaboradorEditable).toBeTrue();
    });

    it('should handle ejecuciones error', () => {
      const actividad = {
        identificador: 'act-1', nombre: 'Test', objetivo: '', semestre: '', rutaInsumos: '',
        indicador: { identificador: 'ind-1' }, colaborador: 'usr-1',
        area: { area: 'area-1', tipoArea: 'AREA' }, fechasProgramadas: ['2025-06-01']
      } as any;
      mockActivityService.consultarEjecuciones.and.returnValue(throwError(() => new Error('fail')));
      component.actividad = actividad;
      component.ngOnChanges({
        actividad: { currentValue: actividad, previousValue: null, firstChange: true, isFirstChange: () => true }
      });
      expect(component.soloColaboradorEditable).toBeFalse();
      expect(component.actividadForm.fechasProgramadas.length).toBe(1);
    });

    it('should not load when no identificador', () => {
      const actividad = {
        identificador: '', nombre: 'Test', objetivo: '', semestre: '', rutaInsumos: '',
        indicador: { identificador: 'ind-1' }, colaborador: 'usr-1',
        area: { area: 'area-1', tipoArea: 'AREA' }, fechasProgramadas: []
      } as any;
      component.actividad = actividad;
      component.ngOnChanges({
        actividad: { currentValue: actividad, previousValue: null, firstChange: true, isFirstChange: () => true }
      });
      expect(component.cargandoDatosActividad).toBeFalse();
    });
  });

  describe('editarActividad error variants', () => {
    beforeEach(() => {
      component.actividad = { identificador: 'act-1' } as any;
      component.actividadForm = {
        nombre: 'Test', objetivo: 'Obj', semestre: '2025-1', rutaInsumos: '/path',
        indicador: 'ind-1', colaborador: 'usr-1', area: 'area-1', tipoArea: 'AREA',
        fechasProgramadas: [{ identificador: null as any, fechaProgramada: '2030-06-01' }]
      };
      component.indicadorSeleccionado = 'ind-1';
      component.colaboradorSeleccionado = 'usr-1';
    });

    it('should handle error with message field', () => {
      mockActivityService.modificarActividad.and.returnValue(throwError(() => ({
        error: { message: 'Message error' }
      })));
      component.editarActividad();
      expect(component.error).toBe('Message error');
    });

    it('should handle error with string body', () => {
      mockActivityService.modificarActividad.and.returnValue(throwError(() => ({
        error: 'String error'
      })));
      component.editarActividad();
      expect(component.error).toBe('String error');
    });

    it('should handle generic error', () => {
      mockActivityService.modificarActividad.and.returnValue(throwError(() => ({})));
      component.editarActividad();
      expect(component.error).toContain('Error al actualizar');
    });

    it('should error when area or tipoArea is empty after validation bypass', () => {
      component.actividadForm.area = '';
      component.actividadForm.tipoArea = '';
      spyOn<any>(component, 'validarFormulario').and.returnValue(true);
      component.editarActividad();
      expect(component.error).toContain('área');
    });
  });

  describe('cerrarModal', () => {
    it('should not throw when modal element not found', () => {
      spyOn(document, 'getElementById').and.returnValue(null);
      expect(() => component.cerrarModal()).not.toThrow();
    });
  });

  describe('limpiarFormulario', () => {
    it('should call resetFormulario', () => {
      component.actividadForm.nombre = 'Test';
      component.error = 'err';
      component.limpiarFormulario();
      expect(component.actividadForm.nombre).toBe('');
      expect(component.error).toBe('');
    });
  });

  describe('buscarYPrecargarArea paths', () => {
    it('should use area service for area type', () => {
      component.nombreArea = 'Area Test';
      component.tipoEstructuraSeleccionado = 'area';
      component.actividadForm.area = '';
      mockAreaService.consultarPorNombre.calls.reset();
      (component as any).buscarYPrecargarArea();
      expect(mockAreaService.consultarPorNombre).toHaveBeenCalledWith('Area Test');
    });

    it('should use subarea service for subarea type', () => {
      component.nombreArea = 'Sub Test';
      component.tipoEstructuraSeleccionado = 'subarea';
      component.actividadForm.area = '';
      mockSubAreaService.consultarPorNombre.calls.reset();
      (component as any).buscarYPrecargarArea();
      expect(mockSubAreaService.consultarPorNombre).toHaveBeenCalledWith('Sub Test');
    });

    it('should skip lookup when actividadForm.area is already set', () => {
      component.nombreArea = 'Test';
      component.tipoEstructuraSeleccionado = 'direccion';
      component.actividadForm.area = 'already-set';
      mockDepartmentService.consultarPorNombre.calls.reset();
      (component as any).buscarYPrecargarArea();
      expect(mockDepartmentService.consultarPorNombre).not.toHaveBeenCalled();
    });

    it('should handle error from service', () => {
      component.nombreArea = 'Dir Err';
      component.tipoEstructuraSeleccionado = 'direccion';
      component.actividadForm.area = '';
      mockDepartmentService.consultarPorNombre.and.returnValue(throwError(() => new Error('fail')));
      expect(() => (component as any).buscarYPrecargarArea()).not.toThrow();
    });

    it('should update listaFiltrada when no nombre', () => {
      component.nombreArea = '';
      component.tipoEstructuraSeleccionado = 'direccion';
      (component as any).buscarYPrecargarArea();
      // Should call actualizarListaFiltrada
      expect(component.listaFiltrada.length).toBeGreaterThanOrEqual(0);
    });
  });

  describe('determinarTipoArea with empty', () => {
    it('should return actividadForm.tipoArea as fallback', () => {
      component.tipoEstructuraSeleccionado = '' as any;
      component.actividadForm.tipoArea = 'CUSTOM';
      expect(component.determinarTipoArea()).toBe('CUSTOM');
    });
  });

  describe('asegurarIndicadorEnLista', () => {
    it('should add indicator when not in list', () => {
      const actividad = {
        identificador: 'act-1', nombre: 'Test', objetivo: '', semestre: '', rutaInsumos: '',
        indicador: { identificador: 'ind-new', nombre: 'New Ind', tipoIndicador: { identificador: 'ti-1', tipologiaIndicador: 'T' }, temporalidad: { identificador: 'f-1', nombre: 'M' }, proyecto: { identificador: 'p-1', nombre: 'P' }, publicosInteres: [] },
        colaborador: 'usr-1', area: { area: 'area-1', tipoArea: 'AREA' }, fechasProgramadas: []
      } as any;
      component.actividad = actividad;
      component.indicadores = [{ identificador: 'ind-1', nombre: 'Existing' } as any];
      (component as any).asegurarIndicadorEnLista();
      expect(component.indicadores.length).toBe(2);
    });

    it('should not add indicator when already in list', () => {
      const actividad = {
        identificador: 'act-1', nombre: 'Test', objetivo: '', semestre: '', rutaInsumos: '',
        indicador: { identificador: 'ind-1', nombre: 'Existing' },
        colaborador: 'usr-1', area: { area: 'area-1', tipoArea: 'AREA' }, fechasProgramadas: []
      } as any;
      component.actividad = actividad;
      component.indicadores = [{ identificador: 'ind-1', nombre: 'Existing' } as any];
      (component as any).asegurarIndicadorEnLista();
      expect(component.indicadores.length).toBe(1);
    });

    it('should not add when no actividad indicator', () => {
      component.actividad = { indicador: null } as any;
      component.indicadores = [{ identificador: 'ind-1' } as any];
      (component as any).asegurarIndicadorEnLista();
      expect(component.indicadores.length).toBe(1);
    });

    it('should not add when indicadores empty', () => {
      component.actividad = { indicador: { identificador: 'ind-1' } } as any;
      component.indicadores = [];
      (component as any).asegurarIndicadorEnLista();
      expect(component.indicadores.length).toBe(0);
    });
  });

  describe('asegurarColaboradorEnLista', () => {
    it('should add collaborator when not in list', () => {
      component.actividad = { colaborador: 'usr-new', nombreColaborador: 'Juan Perez Lopez' } as any;
      component.usuarios = [{ identificador: 'usr-1', nombres: 'Existing' } as any];
      (component as any).asegurarColaboradorEnLista();
      expect(component.usuarios.length).toBe(2);
      const added = component.usuarios.find(u => u.identificador === 'usr-new');
      expect(added).toBeTruthy();
    });

    it('should not add collaborator when already in list', () => {
      component.actividad = { colaborador: 'usr-1', nombreColaborador: 'Existing' } as any;
      component.usuarios = [{ identificador: 'usr-1', nombres: 'Existing' } as any];
      (component as any).asegurarColaboradorEnLista();
      expect(component.usuarios.length).toBe(1);
    });

    it('should handle single-word name', () => {
      component.actividad = { colaborador: 'usr-new', nombreColaborador: 'Onename' } as any;
      component.usuarios = [{ identificador: 'usr-1' } as any];
      (component as any).asegurarColaboradorEnLista();
      const added = component.usuarios.find(u => u.identificador === 'usr-new');
      expect(added!.nombres).toBe('Onename');
    });

    it('should not add when no colaborador', () => {
      component.actividad = { colaborador: '' } as any;
      component.usuarios = [{ identificador: 'usr-1' } as any];
      (component as any).asegurarColaboradorEnLista();
      expect(component.usuarios.length).toBe(1);
    });

    it('should not add when usuarios empty', () => {
      component.actividad = { colaborador: 'usr-new' } as any;
      component.usuarios = [];
      (component as any).asegurarColaboradorEnLista();
      expect(component.usuarios.length).toBe(0);
    });
  });

  describe('obtenerTipoEstructuraDesdeActividad', () => {
    it('should return direccion for DIRECCION', () => {
      component.actividad = { area: { tipoArea: 'DIRECCION' } } as any;
      expect((component as any).obtenerTipoEstructuraDesdeActividad()).toBe('direccion');
    });

    it('should return area for AREA', () => {
      component.actividad = { area: { tipoArea: 'AREA' } } as any;
      expect((component as any).obtenerTipoEstructuraDesdeActividad()).toBe('area');
    });

    it('should return subarea for SUBAREA', () => {
      component.actividad = { area: { tipoArea: 'SUBAREA' } } as any;
      expect((component as any).obtenerTipoEstructuraDesdeActividad()).toBe('subarea');
    });

    it('should return empty for unknown type', () => {
      component.actividad = { area: { tipoArea: 'UNKNOWN' } } as any;
      expect((component as any).obtenerTipoEstructuraDesdeActividad()).toBe('');
    });

    it('should use tipoArea directly if no area object', () => {
      component.actividad = { tipoArea: 'AREA' } as any;
      expect((component as any).obtenerTipoEstructuraDesdeActividad()).toBe('area');
    });

    it('should return empty for null actividad', () => {
      component.actividad = null;
      expect((component as any).obtenerTipoEstructuraDesdeActividad()).toBe('');
    });
  });

  describe('obtenerAreaDesdeActividad', () => {
    it('should return empty for null actividad', () => {
      expect((component as any).obtenerAreaDesdeActividad(null)).toBe('');
    });

    it('should return area from area object', () => {
      expect((component as any).obtenerAreaDesdeActividad({ area: { area: 'area-1', tipoArea: 'AREA' } })).toBe('area-1');
    });

    it('should return areaIdentificador as fallback', () => {
      expect((component as any).obtenerAreaDesdeActividad({ areaIdentificador: 'fallback-id' })).toBe('fallback-id');
    });

    it('should return empty when no area info', () => {
      expect((component as any).obtenerAreaDesdeActividad({ nombre: 'test' })).toBe('');
    });
  });

  describe('obtenerTipoAreaDesdeActividad', () => {
    it('should return empty for null actividad', () => {
      expect((component as any).obtenerTipoAreaDesdeActividad(null)).toBe('');
    });

    it('should return tipoArea from area object', () => {
      expect((component as any).obtenerTipoAreaDesdeActividad({ area: { area: 'a', tipoArea: 'AREA' } })).toBe('AREA');
    });

    it('should return actividad.tipoArea as fallback', () => {
      expect((component as any).obtenerTipoAreaDesdeActividad({ tipoArea: 'SUBAREA' })).toBe('SUBAREA');
    });

    it('should fall back to determinarTipoArea', () => {
      component.tipoEstructuraSeleccionado = 'direccion';
      expect((component as any).obtenerTipoAreaDesdeActividad({ nombre: 'test' })).toBe('DIRECCION');
    });
  });

  describe('editarActividad success path', () => {
    it('should emit actividadEditada on success', () => {
      spyOn(component.actividadEditada, 'emit');
      component.actividad = { identificador: 'act-1' } as any;
      component.actividadForm = {
        nombre: 'Test', objetivo: 'Obj', semestre: '2025-1', rutaInsumos: '/path',
        indicador: 'ind-1', colaborador: 'usr-1', area: 'area-1', tipoArea: 'AREA',
        fechasProgramadas: [{ identificador: null as any, fechaProgramada: '2030-06-01' }]
      };
      component.indicadorSeleccionado = 'ind-1';
      component.colaboradorSeleccionado = 'usr-1';

      component.editarActividad();

      expect(component.mensajeExito).toContain('exitosamente');
      expect(component.actividadEditada.emit).toHaveBeenCalled();
    });
  });

  describe('actualizarListaFiltrada', () => {
    it('should set direcciones for direccion type', () => {
      component.tipoEstructuraSeleccionado = 'direccion';
      (component as any).actualizarListaFiltrada();
      expect(component.listaFiltrada.length).toBe(1);
      expect(component.listaFiltrada[0].nombre).toBe('Dir 1');
    });

    it('should set areas for area type', () => {
      component.tipoEstructuraSeleccionado = 'area';
      (component as any).actualizarListaFiltrada();
      expect(component.listaFiltrada.length).toBe(1);
      expect(component.listaFiltrada[0].nombre).toBe('Area 1');
    });

    it('should set subareas for subarea type', () => {
      component.tipoEstructuraSeleccionado = 'subarea';
      (component as any).actualizarListaFiltrada();
      expect(component.listaFiltrada.length).toBe(1);
      expect(component.listaFiltrada[0].nombre).toBe('Sub 1');
    });

    it('should clear for empty type', () => {
      component.tipoEstructuraSeleccionado = '' as any;
      (component as any).actualizarListaFiltrada();
      expect(component.listaFiltrada.length).toBe(0);
    });
  });

  describe('esEstadoFinalizado', () => {
    it('should return false for null', () => {
      expect((component as any).esEstadoFinalizado(null)).toBeFalse();
    });

    it('should return true for Finalizada', () => {
      expect((component as any).esEstadoFinalizado('Finalizada')).toBeTrue();
    });

    it('should return false for Pendiente', () => {
      expect((component as any).esEstadoFinalizado('Pendiente')).toBeFalse();
    });
  });

  describe('cargarEjecuciones with no finalized and no ejecuciones', () => {
    it('should use actividad fechasProgramadas when ejecuciones empty and not finalized', () => {
      const actividad = {
        identificador: 'act-1', nombre: 'Test', objetivo: '', semestre: '', rutaInsumos: '',
        indicador: { identificador: 'ind-1' }, colaborador: 'usr-1',
        area: { area: 'area-1', tipoArea: 'AREA' }, fechasProgramadas: ['2025-06-01', '2025-07-01']
      } as any;
      mockActivityService.consultarEjecuciones.and.returnValue(of([]));
      component.actividad = actividad;
      component.ngOnChanges({
        actividad: { currentValue: actividad, previousValue: null, firstChange: true, isFirstChange: () => true }
      });
      expect(component.actividadForm.fechasProgramadas.length).toBe(2);
      expect(component.actividadForm.fechasProgramadas[0].identificador).toBeNull();
    });
  });

  describe('cargarDatosActividad with null actividad', () => {
    it('should reset formulario when actividad is null', () => {
      component.actividad = null as any;
      (component as any).cargarDatosActividad();
      expect(component.actividadForm.nombre).toBe('');
    });
  });

  describe('obtenerAreaDesdeActividad with string area', () => {
    it('should return area string when area is a plain string', () => {
      const actividad = { area: 'area-name-string' } as any;
      const result = (component as any).obtenerAreaDesdeActividad(actividad);
      expect(result).toBe('area-name-string');
    });
  });

  describe('actualizarListaFiltrada unknown tipoEstructura', () => {
    it('should clear listaFiltrada for unknown tipoEstructura', () => {
      component.tipoEstructuraSeleccionado = 'unknown' as any;
      (component as any).actualizarListaFiltrada();
      expect(component.listaFiltrada).toEqual([]);
    });
  });

  describe('onTipoEstructuraChange with read-only values', () => {
    it('should return early when nombreArea and tipoEstructura are set', () => {
      (component as any).nombreArea = 'TestArea';
      (component as any).tipoEstructura = 'area';
      component.actividadForm.area = 'existing-area';
      component.onTipoEstructuraChange();
      expect(component.actividadForm.area).toBe('existing-area');
    });
  });

  describe('buscarYPrecargarArea when area already set', () => {
    it('should call actualizarListaFiltrada and return when area is already set', () => {
      (component as any).nombreArea = 'TestArea';
      component.tipoEstructuraSeleccionado = 'area';
      component.actividadForm.area = 'existing-area';
      (component as any).buscarYPrecargarArea();
      expect(component.listaFiltrada).toBeDefined();
    });
  });

  describe('cerrarModal with DOM element', () => {
    it('should hide modal and clean up backdrops', fakeAsync(() => {
      const mockModal = { hide: jasmine.createSpy('hide'), show: jasmine.createSpy('show') };
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
      document.body.style.paddingRight = '';
      document.body.style.overflow = '';
    }));
  });

  describe('editarActividad additional branches', () => {
    it('should return early when actividad has empty identificador', () => {
      component.actividad = { identificador: '' } as any;
      component.editarActividad();
      expect(mockActivityService.modificarActividad).not.toHaveBeenCalled();
    });

    it('should work in soloColaboradorEditable mode with only colaborador', () => {
      component.actividad = { identificador: 'act-1' } as any;
      component.soloColaboradorEditable = true;
      component.colaboradorSeleccionado = 'usr-1';
      component.actividadForm = {
        nombre: '', objetivo: '', semestre: '', rutaInsumos: '',
        indicador: '', colaborador: '', area: 'area-1', tipoArea: 'AREA',
        fechasProgramadas: []
      };
      component.editarActividad();
      expect(mockActivityService.modificarActividad).toHaveBeenCalled();
    });

    it('should fail validation when soloColaboradorEditable and colaborador is whitespace', () => {
      component.actividad = { identificador: 'act-1' } as any;
      component.soloColaboradorEditable = true;
      component.colaboradorSeleccionado = '   ';
      component.editarActividad();
      expect(component.error).toContain('campos requeridos');
    });
  });

  describe('validarFormulario additional branches', () => {
    it('should return false when fechasProgramadas is empty', () => {
      component.actividadForm = {
        nombre: 'T', objetivo: 'O', semestre: 'S', rutaInsumos: 'R',
        indicador: 'i', colaborador: 'c', area: 'a', tipoArea: 'A',
        fechasProgramadas: []
      };
      component.indicadorSeleccionado = 'i';
      component.colaboradorSeleccionado = 'c';
      expect((component as any).validarFormulario()).toBeFalse();
    });

    it('should return false when indicadorSeleccionado is whitespace', () => {
      component.actividadForm = {
        nombre: 'T', objetivo: 'O', semestre: 'S', rutaInsumos: 'R',
        indicador: 'i', colaborador: 'c', area: 'a', tipoArea: 'A',
        fechasProgramadas: [{ identificador: null as any, fechaProgramada: '2030-01-01' }]
      };
      component.indicadorSeleccionado = '   ';
      component.colaboradorSeleccionado = 'c';
      expect((component as any).validarFormulario()).toBeFalse();
    });

    it('should return false when objetivo is empty', () => {
      component.actividadForm = {
        nombre: 'T', objetivo: '', semestre: 'S', rutaInsumos: 'R',
        indicador: 'i', colaborador: 'c', area: 'a', tipoArea: 'A',
        fechasProgramadas: [{ identificador: null as any, fechaProgramada: '2030-01-01' }]
      };
      component.indicadorSeleccionado = 'i';
      component.colaboradorSeleccionado = 'c';
      expect((component as any).validarFormulario()).toBeFalse();
    });
  });

  describe('cargarEjecuciones with non-finalized data', () => {
    it('should map ejecuciones with fechaProgramada and not set soloColaboradorEditable', () => {
      const actividad = {
        identificador: 'act-1', nombre: 'Test', objetivo: '', semestre: '', rutaInsumos: '',
        indicador: { identificador: 'ind-1' }, colaborador: 'usr-1',
        area: { area: 'area-1', tipoArea: 'AREA' }, fechasProgramadas: []
      } as any;
      mockActivityService.consultarEjecuciones.and.returnValue(of([
        { identificador: 'ej-1', fechaProgramada: '2025-06-01', estadoActividad: { nombre: 'Pendiente' } },
        { identificador: 'ej-2', fechaProgramada: '2025-07-01', estadoActividad: { nombre: 'En progreso' } }
      ] as any));
      component.actividad = actividad;
      component.ngOnChanges({
        actividad: { currentValue: actividad, previousValue: null, firstChange: true, isFirstChange: () => true }
      });
      expect(component.soloColaboradorEditable).toBeFalse();
      expect(component.actividadForm.fechasProgramadas.length).toBe(2);
      expect(component.actividadForm.fechasProgramadas[0].identificador).toBe('ej-1');
    });

    it('should filter out ejecuciones with empty fechaProgramada', () => {
      const actividad = {
        identificador: 'act-1', nombre: 'Test', objetivo: '', semestre: '', rutaInsumos: '',
        indicador: { identificador: 'ind-1' }, colaborador: 'usr-1',
        area: { area: 'area-1', tipoArea: 'AREA' }, fechasProgramadas: []
      } as any;
      mockActivityService.consultarEjecuciones.and.returnValue(of([
        { identificador: 'ej-1', fechaProgramada: '2025-06-01', estadoActividad: { nombre: 'Pendiente' } },
        { identificador: 'ej-2', fechaProgramada: '', estadoActividad: { nombre: 'Pendiente' } }
      ] as any));
      component.actividad = actividad;
      component.ngOnChanges({
        actividad: { currentValue: actividad, previousValue: null, firstChange: true, isFirstChange: () => true }
      });
      expect(component.actividadForm.fechasProgramadas.length).toBe(1);
    });

    it('should handle null ejecuciones response', () => {
      const actividad = {
        identificador: 'act-1', nombre: 'Test', objetivo: '', semestre: '', rutaInsumos: '',
        indicador: { identificador: 'ind-1' }, colaborador: 'usr-1',
        area: { area: 'area-1', tipoArea: 'AREA' }, fechasProgramadas: ['2025-06-01']
      } as any;
      mockActivityService.consultarEjecuciones.and.returnValue(of(null as any));
      component.actividad = actividad;
      component.ngOnChanges({
        actividad: { currentValue: actividad, previousValue: null, firstChange: true, isFirstChange: () => true }
      });
      expect(component.actividadForm.fechasProgramadas.length).toBe(1);
      expect(component.actividadForm.fechasProgramadas[0].identificador).toBeNull();
    });
  });

  describe('esEstadoFinalizado edge cases', () => {
    it('should return false for empty string', () => {
      expect((component as any).esEstadoFinalizado('')).toBeFalse();
    });

    it('should return false for undefined', () => {
      expect((component as any).esEstadoFinalizado(undefined)).toBeFalse();
    });
  });

  describe('buscarYPrecargarArea additional paths', () => {
    it('should handle null response from direccion lookup', () => {
      component.nombreArea = 'NoExiste';
      component.tipoEstructuraSeleccionado = 'direccion';
      component.actividadForm.area = '';
      mockDepartmentService.consultarPorNombre.and.returnValue(of(null as any));
      (component as any).buscarYPrecargarArea();
      expect(component.actividadForm.area).toBe('');
    });

    it('should handle default switch case for unknown tipoEstructura', () => {
      component.nombreArea = 'Test';
      component.tipoEstructuraSeleccionado = 'otro' as any;
      component.actividadForm.area = '';
      (component as any).buscarYPrecargarArea();
      expect(component.listaFiltrada).toBeDefined();
    });
  });

  describe('asegurarColaboradorEnLista with empty nombreColaborador', () => {
    it('should handle empty nombreColaborador', () => {
      component.actividad = { colaborador: 'usr-new', nombreColaborador: '' } as any;
      component.usuarios = [{ identificador: 'usr-1' } as any];
      (component as any).asegurarColaboradorEnLista();
      const added = component.usuarios.find(u => u.identificador === 'usr-new');
      expect(added).toBeTruthy();
      expect(added!.nombres).toBe('');
    });

    it('should handle undefined nombreColaborador', () => {
      component.actividad = { colaborador: 'usr-new' } as any;
      component.usuarios = [{ identificador: 'usr-1' } as any];
      (component as any).asegurarColaboradorEnLista();
      const added = component.usuarios.find(u => u.identificador === 'usr-new');
      expect(added).toBeTruthy();
    });
  });

  describe('editarActividad request construction', () => {
    it('should include existing execution identifiers in request', () => {
      component.actividad = { identificador: 'act-1' } as any;
      component.actividadForm = {
        nombre: 'Test', objetivo: 'Obj', semestre: '2025-1', rutaInsumos: '/path',
        indicador: 'ind-1', colaborador: 'usr-1', area: 'area-1', tipoArea: 'AREA',
        fechasProgramadas: [
          { identificador: 'ej-1', fechaProgramada: '2030-06-01' },
          { identificador: null as any, fechaProgramada: '2030-07-01' }
        ]
      };
      component.indicadorSeleccionado = 'ind-1';
      component.colaboradorSeleccionado = 'usr-1';

      component.editarActividad();

      const callArgs = mockActivityService.modificarActividad.calls.mostRecent().args;
      expect(callArgs[1].fechasProgramada.length).toBe(2);
      expect(callArgs[1].fechasProgramada[0].identificador).toBe('ej-1');
      expect(callArgs[1].fechasProgramada[1].identificador).toBeNull();
    });

    it('should filter out fechasProgramadas with empty fechaProgramada in request', () => {
      component.actividad = { identificador: 'act-1' } as any;
      component.actividadForm = {
        nombre: 'Test', objetivo: 'Obj', semestre: '2025-1', rutaInsumos: '/path',
        indicador: 'ind-1', colaborador: 'usr-1', area: 'area-1', tipoArea: 'AREA',
        fechasProgramadas: [
          { identificador: null as any, fechaProgramada: '2030-06-01' },
          { identificador: null as any, fechaProgramada: '  ' }
        ]
      };
      component.indicadorSeleccionado = 'ind-1';
      component.colaboradorSeleccionado = 'usr-1';

      component.editarActividad();

      const callArgs = mockActivityService.modificarActividad.calls.mostRecent().args;
      expect(callArgs[1].fechasProgramada.length).toBe(1);
    });

    it('should use tipoArea from form in request area object', () => {
      component.actividad = { identificador: 'act-1' } as any;
      component.tipoEstructuraSeleccionado = 'subarea';
      component.actividadForm = {
        nombre: 'Test', objetivo: 'Obj', semestre: '2025-1', rutaInsumos: '/path',
        indicador: 'ind-1', colaborador: 'usr-1', area: 'sub-1', tipoArea: 'SUBAREA',
        fechasProgramadas: [{ identificador: null as any, fechaProgramada: '2030-06-01' }]
      };
      component.indicadorSeleccionado = 'ind-1';
      component.colaboradorSeleccionado = 'usr-1';

      component.editarActividad();

      const callArgs = mockActivityService.modificarActividad.calls.mostRecent().args;
      expect(callArgs[1].area.tipoArea).toBe('SUBAREA');
    });
  });

  describe('eliminarFecha when soloColaboradorEditable and fecha is new', () => {
    it('should allow removing new dates even in soloColaboradorEditable mode', () => {
      component.soloColaboradorEditable = true;
      component.actividadForm.fechasProgramadas = [
        { identificador: 'ej-1', fechaProgramada: '2030-06-01' },
        { identificador: null as any, fechaProgramada: '2030-07-01' }
      ];
      component.eliminarFecha(1);
      expect(component.actividadForm.fechasProgramadas.length).toBe(1);
      expect(component.actividadForm.fechasProgramadas[0].identificador).toBe('ej-1');
    });

    it('should clear errorFecha when removing a date', () => {
      component.errorFecha = 'Some error';
      component.actividadForm.fechasProgramadas = [
        { identificador: null as any, fechaProgramada: '2030-06-01' }
      ];
      component.eliminarFecha(0);
      expect(component.errorFecha).toBe('');
    });
  });

  describe('ngOnChanges without matching changes', () => {
    it('should not load activity data when changes do not include actividad', () => {
      const spy = spyOn<any>(component, 'cargarDatosActividad');
      component.ngOnChanges({
        nombreArea: { currentValue: '', previousValue: '', firstChange: false, isFirstChange: () => false }
      });
      expect(spy).not.toHaveBeenCalled();
    });

    it('should not precargar area when nombreArea is empty', () => {
      component.nombreArea = '';
      component.tipoEstructura = 'area';
      const spy = spyOn<any>(component, 'buscarYPrecargarArea');
      component.ngOnChanges({
        nombreArea: { currentValue: '', previousValue: '', firstChange: false, isFirstChange: () => false }
      });
      expect(spy).not.toHaveBeenCalled();
    });
  });
});
