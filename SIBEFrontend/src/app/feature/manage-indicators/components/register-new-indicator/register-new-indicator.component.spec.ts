import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { of, throwError } from 'rxjs';

import { RegisterNewIndicatorComponent } from './register-new-indicator.component';
import { IndicatorService } from '../../service/indicator.service';
import { ProjectService } from '../../service/project.service';
import { FrequencyService } from '../../service/frequency.service';
import { IndicatorTypeService } from '../../service/indicator-type.service';
import { InterestedPublicService } from '../../service/interested-public.service';

describe('RegisterNewIndicatorComponent', () => {
  let component: RegisterNewIndicatorComponent;
  let fixture: ComponentFixture<RegisterNewIndicatorComponent>;
  let mockIndicatorService: jasmine.SpyObj<IndicatorService>;
  let mockProjectService: jasmine.SpyObj<ProjectService>;
  let mockFrequencyService: jasmine.SpyObj<FrequencyService>;
  let mockIndicatorTypeService: jasmine.SpyObj<IndicatorTypeService>;
  let mockInterestedPublicService: jasmine.SpyObj<InterestedPublicService>;

  const mockProyectos = [{ identificador: 'p1', numeroProyecto: '001', nombre: 'Proy 1', objetivo: 'Obj' }];
  const mockTemporalidades = [{ identificador: 'f1', nombre: 'Mensual' }];
  const mockTiposIndicador = [{ identificador: 'ti1', naturalezaIndicador: 'N1', tipologiaIndicador: 'T1' }];
  const mockPublicoInteres = [{ identificador: 'pi1', nombre: 'Publico 1' }, { identificador: 'pi2', nombre: 'Publico 2' }];

  beforeEach(() => {
    mockIndicatorService = jasmine.createSpyObj('IndicatorService', ['agregarNuevoIndicador']);
    mockIndicatorService.agregarNuevoIndicador.and.returnValue(of({ valor: 'ok' }));

    mockProjectService = jasmine.createSpyObj('ProjectService', ['consultarProyectos']);
    mockProjectService.consultarProyectos.and.returnValue(of({ content: mockProyectos, totalElements: mockProyectos.length }));

    mockFrequencyService = jasmine.createSpyObj('FrequencyService', ['consultarTemporalidades']);
    mockFrequencyService.consultarTemporalidades.and.returnValue(of(mockTemporalidades));

    mockIndicatorTypeService = jasmine.createSpyObj('IndicatorTypeService', ['consultarTiposIndicador']);
    mockIndicatorTypeService.consultarTiposIndicador.and.returnValue(of(mockTiposIndicador));

    mockInterestedPublicService = jasmine.createSpyObj('InterestedPublicService', ['consultarPublicoInteres']);
    mockInterestedPublicService.consultarPublicoInteres.and.returnValue(of(mockPublicoInteres));

    TestBed.configureTestingModule({
      imports: [FormsModule],
      declarations: [RegisterNewIndicatorComponent],
      providers: [
        { provide: IndicatorService, useValue: mockIndicatorService },
        { provide: ProjectService, useValue: mockProjectService },
        { provide: FrequencyService, useValue: mockFrequencyService },
        { provide: IndicatorTypeService, useValue: mockIndicatorTypeService },
        { provide: InterestedPublicService, useValue: mockInterestedPublicService }
      ],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(RegisterNewIndicatorComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    fixture.detectChanges();
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    it('should load all dropdowns', () => {
      fixture.detectChanges();
      expect(mockProjectService.consultarProyectos).toHaveBeenCalled();
      expect(mockFrequencyService.consultarTemporalidades).toHaveBeenCalled();
      expect(mockIndicatorTypeService.consultarTiposIndicador).toHaveBeenCalled();
      expect(mockInterestedPublicService.consultarPublicoInteres).toHaveBeenCalled();
    });
  });

  describe('cargarProyectos', () => {
    it('should populate proyectos', () => {
      component.cargarProyectos();
      expect(component.proyectos.length).toBe(1);
      expect(component.cargandoProyectos).toBeFalse();
    });

    it('should handle error', () => {
      mockProjectService.consultarProyectos.and.returnValue(throwError(() => new Error('fail')));
      component.cargarProyectos();
      expect(component.cargandoProyectos).toBeFalse();
    });
  });

  describe('cargarTemporalidades', () => {
    it('should populate temporalidades', () => {
      component.cargarTemporalidades();
      expect(component.temporalidades.length).toBe(1);
      expect(component.cargandoTemporalidades).toBeFalse();
    });

    it('should handle error', () => {
      mockFrequencyService.consultarTemporalidades.and.returnValue(throwError(() => new Error('fail')));
      component.cargarTemporalidades();
      expect(component.cargandoTemporalidades).toBeFalse();
    });
  });

  describe('cargarTiposIndicador', () => {
    it('should populate tiposIndicador', () => {
      component.cargarTiposIndicador();
      expect(component.tiposIndicador.length).toBe(1);
      expect(component.cargandoTiposIndicador).toBeFalse();
    });

    it('should handle error', () => {
      mockIndicatorTypeService.consultarTiposIndicador.and.returnValue(throwError(() => new Error('fail')));
      component.cargarTiposIndicador();
      expect(component.cargandoTiposIndicador).toBeFalse();
    });
  });

  describe('cargarPublicoInteres', () => {
    it('should populate publicoInteresDisponibles', () => {
      component.cargarPublicoInteres();
      expect(component.publicoInteresDisponibles.length).toBe(2);
      expect(component.publicoInteresFiltrados.length).toBe(2);
      expect(component.cargandoPublicoInteres).toBeFalse();
    });

    it('should handle error', () => {
      mockInterestedPublicService.consultarPublicoInteres.and.returnValue(throwError(() => new Error('fail')));
      component.cargarPublicoInteres();
      expect(component.cargandoPublicoInteres).toBeFalse();
    });
  });

  describe('filterPublicoInteres', () => {
    beforeEach(() => {
      component.publicoInteresDisponibles = [
        { value: 'pi1', label: 'Estudiantes' },
        { value: 'pi2', label: 'Profesores' }
      ];
    });

    it('should show all when search is empty', () => {
      component.searchPublicoInteres = '';
      component.filterPublicoInteres();
      expect(component.publicoInteresFiltrados.length).toBe(2);
    });

    it('should filter by term', () => {
      component.searchPublicoInteres = 'prof';
      component.filterPublicoInteres();
      expect(component.publicoInteresFiltrados.length).toBe(1);
      expect(component.publicoInteresFiltrados[0].label).toBe('Profesores');
    });

    it('should return empty when no disponibles', () => {
      component.publicoInteresDisponibles = [];
      component.filterPublicoInteres();
      expect(component.publicoInteresFiltrados).toEqual([]);
    });
  });

  describe('isPublicoInteresSelected', () => {
    it('should return true when selected', () => {
      component.indicador.publicosInteres = ['pi1'];
      expect(component.isPublicoInteresSelected('pi1')).toBeTrue();
    });

    it('should return false when not selected', () => {
      component.indicador.publicosInteres = [];
      expect(component.isPublicoInteresSelected('pi1')).toBeFalse();
    });
  });

  describe('togglePublicoInteres', () => {
    it('should add value if not present', () => {
      component.indicador.publicosInteres = [];
      component.togglePublicoInteres('pi1');
      expect(component.indicador.publicosInteres).toContain('pi1');
    });

    it('should remove value if present', () => {
      component.indicador.publicosInteres = ['pi1'];
      component.togglePublicoInteres('pi1');
      expect(component.indicador.publicosInteres).not.toContain('pi1');
    });

    it('should initialize array if null', () => {
      component.indicador.publicosInteres = null as any;
      component.togglePublicoInteres('pi1');
      expect(component.indicador.publicosInteres).toEqual(['pi1']);
    });
  });

  describe('removePublicoInteres', () => {
    it('should remove value', () => {
      component.indicador.publicosInteres = ['pi1', 'pi2'];
      component.removePublicoInteres('pi1');
      expect(component.indicador.publicosInteres).toEqual(['pi2']);
    });

    it('should not fail if value not present', () => {
      component.indicador.publicosInteres = ['pi1'];
      component.removePublicoInteres('pi3');
      expect(component.indicador.publicosInteres).toEqual(['pi1']);
    });
  });

  describe('getSelectedPublicoInteresLabels', () => {
    it('should return labels for selected values', () => {
      component.publicoInteresDisponibles = [
        { value: 'pi1', label: 'Estudiantes' },
        { value: 'pi2', label: 'Profesores' }
      ];
      component.indicador.publicosInteres = ['pi1', 'pi2'];
      expect(component.getSelectedPublicoInteresLabels()).toEqual(['Estudiantes', 'Profesores']);
    });

    it('should return empty array when none selected', () => {
      component.indicador.publicosInteres = [];
      expect(component.getSelectedPublicoInteresLabels()).toEqual([]);
    });

    it('should return empty array when publicosInteres is null', () => {
      component.indicador.publicosInteres = null as any;
      expect(component.getSelectedPublicoInteresLabels()).toEqual([]);
    });
  });

  describe('getPublicoInteresValue', () => {
    it('should return value for label', () => {
      component.publicoInteresDisponibles = [{ value: 'pi1', label: 'Estudiantes' }];
      expect(component.getPublicoInteresValue('Estudiantes')).toBe('pi1');
    });

    it('should return empty string for unknown label', () => {
      component.publicoInteresDisponibles = [];
      expect(component.getPublicoInteresValue('Unknown')).toBe('');
    });
  });

  describe('registrarIndicador', () => {
    beforeEach(() => {
      component.indicador = {
        nombre: 'Nuevo', tipoIndicador: 'ti1', temporalidad: 'f1',
        proyecto: 'p1', publicosInteres: ['pi1']
      };
    });

    it('should not proceed if already loading', () => {
      component.cargando = true;
      component.registrarIndicador();
      expect(mockIndicatorService.agregarNuevoIndicador).not.toHaveBeenCalled();
    });

    it('should set error if no public interest selected', () => {
      component.indicador.publicosInteres = [];
      component.registrarIndicador();
      expect(component.error).toContain('público de interés');
      expect(component.cargando).toBeFalse();
    });

    it('should call service and emit on success', fakeAsync(() => {
      spyOn(component.indicadorCreado, 'emit');
      component.registrarIndicador();
      expect(mockIndicatorService.agregarNuevoIndicador).toHaveBeenCalled();
      expect(component.exito).toBe('Indicador creado exitosamente');
      expect(component.indicadorCreado.emit).toHaveBeenCalled();
      tick(1500);
    }));

    it('should handle service error with mensaje', () => {
      mockIndicatorService.agregarNuevoIndicador.and.returnValue(throwError(() => ({
        error: { mensaje: 'Duplicado' }
      })));
      component.registrarIndicador();
      expect(component.error).toBe('Duplicado');
      expect(component.cargando).toBeFalse();
    });

    it('should handle service error with string error', () => {
      mockIndicatorService.agregarNuevoIndicador.and.returnValue(throwError(() => ({
        error: 'Error string'
      })));
      component.registrarIndicador();
      expect(component.error).toBe('Error string');
    });

    it('should handle service error with valor', () => {
      mockIndicatorService.agregarNuevoIndicador.and.returnValue(throwError(() => ({
        error: { valor: 'Valor error' }
      })));
      component.registrarIndicador();
      expect(component.error).toBe('Valor error');
    });
  });

  describe('limpiarFormulario', () => {
    it('should reset all form fields', () => {
      component.indicador = {
        nombre: 'Test', tipoIndicador: 'ti1', temporalidad: 'f1',
        proyecto: 'p1', publicosInteres: ['pi1']
      };
      component.searchPublicoInteres = 'search';
      component.error = 'error';
      component.exito = 'exito';
      component.publicoInteresDisponibles = [{ value: 'pi1', label: 'Test' }];

      component.limpiarFormulario();

      expect(component.indicador.nombre).toBe('');
      expect(component.indicador.publicosInteres).toEqual([]);
      expect(component.searchPublicoInteres).toBe('');
      expect(component.error).toBe('');
      expect(component.exito).toBe('');
    });
  });
});
