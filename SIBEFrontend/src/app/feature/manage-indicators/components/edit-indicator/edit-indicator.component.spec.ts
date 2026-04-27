import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { NO_ERRORS_SCHEMA, SimpleChange } from '@angular/core';
import { of, throwError } from 'rxjs';

import { EditIndicatorComponent } from './edit-indicator.component';
import { IndicatorService } from '../../service/indicator.service';
import { ProjectService } from '../../service/project.service';
import { FrequencyService } from '../../service/frequency.service';
import { IndicatorTypeService } from '../../service/indicator-type.service';
import { InterestedPublicService } from '../../service/interested-public.service';
import { IndicatorResponse } from '../../model/indicator.model';

describe('EditIndicatorComponent', () => {
  let component: EditIndicatorComponent;
  let fixture: ComponentFixture<EditIndicatorComponent>;
  let mockIndicatorService: jasmine.SpyObj<IndicatorService>;
  let mockProjectService: jasmine.SpyObj<ProjectService>;
  let mockFrequencyService: jasmine.SpyObj<FrequencyService>;
  let mockIndicatorTypeService: jasmine.SpyObj<IndicatorTypeService>;
  let mockInterestedPublicService: jasmine.SpyObj<InterestedPublicService>;

  const mockProyectos = [{ identificador: 'p1', numeroProyecto: '001', nombre: 'Proy 1', objetivo: 'Obj' }];
  const mockTemporalidades = [{ identificador: 'f1', nombre: 'Mensual' }];
  const mockTiposIndicador = [{ identificador: 'ti1', naturalezaIndicador: 'N1', tipologiaIndicador: 'T1' }];
  const mockPublicoInteres = [{ identificador: 'pi1', nombre: 'Publico 1' }, { identificador: 'pi2', nombre: 'Publico 2' }];

  const mockIndicador: IndicatorResponse = {
    identificador: 'i1', nombre: 'Indicador Test',
    tipoIndicador: mockTiposIndicador[0],
    temporalidad: mockTemporalidades[0],
    proyecto: mockProyectos[0] as any,
    publicosInteres: [mockPublicoInteres[0]]
  };

  beforeEach(() => {
    mockIndicatorService = jasmine.createSpyObj('IndicatorService', ['modificarIndicador']);
    mockIndicatorService.modificarIndicador.and.returnValue(of({ valor: 'ok' }));

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
      declarations: [EditIndicatorComponent],
      providers: [
        { provide: IndicatorService, useValue: mockIndicatorService },
        { provide: ProjectService, useValue: mockProjectService },
        { provide: FrequencyService, useValue: mockFrequencyService },
        { provide: IndicatorTypeService, useValue: mockIndicatorTypeService },
        { provide: InterestedPublicService, useValue: mockInterestedPublicService }
      ],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(EditIndicatorComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    fixture.detectChanges();
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    it('should load projects, frequencies, indicator types and public interest', () => {
      fixture.detectChanges();
      expect(mockProjectService.consultarProyectos).toHaveBeenCalled();
      expect(mockFrequencyService.consultarTemporalidades).toHaveBeenCalled();
      expect(mockIndicatorTypeService.consultarTiposIndicador).toHaveBeenCalled();
      expect(mockInterestedPublicService.consultarPublicoInteres).toHaveBeenCalled();
    });

    it('should load indicator data if indicadorAEditar is set', () => {
      component.indicadorAEditar = mockIndicador;
      fixture.detectChanges();
      expect(component.indicador.nombre).toBe('Indicador Test');
    });
  });

  describe('cargarProyectos', () => {
    it('should populate proyectos on success', () => {
      component.cargarProyectos();
      expect(component.proyectos).toEqual(mockProyectos as any);
      expect(component.cargandoProyectos).toBeFalse();
    });

    it('should handle error', () => {
      mockProjectService.consultarProyectos.and.returnValue(throwError(() => new Error('fail')));
      component.cargarProyectos();
      expect(component.cargandoProyectos).toBeFalse();
    });
  });

  describe('cargarTemporalidades', () => {
    it('should populate temporalidades on success', () => {
      component.cargarTemporalidades();
      expect(component.temporalidades).toEqual(mockTemporalidades);
      expect(component.cargandoTemporalidades).toBeFalse();
    });

    it('should handle error', () => {
      mockFrequencyService.consultarTemporalidades.and.returnValue(throwError(() => new Error('fail')));
      component.cargarTemporalidades();
      expect(component.cargandoTemporalidades).toBeFalse();
    });
  });

  describe('cargarTiposIndicador', () => {
    it('should populate tiposIndicador on success', () => {
      component.cargarTiposIndicador();
      expect(component.tiposIndicador).toEqual(mockTiposIndicador);
      expect(component.cargandoTiposIndicador).toBeFalse();
    });

    it('should handle error', () => {
      mockIndicatorTypeService.consultarTiposIndicador.and.returnValue(throwError(() => new Error('fail')));
      component.cargarTiposIndicador();
      expect(component.cargandoTiposIndicador).toBeFalse();
    });
  });

  describe('cargarPublicoInteres', () => {
    it('should populate publicoInteresDisponibles on success', () => {
      component.cargarPublicoInteres();
      expect(component.publicoInteresDisponibles.length).toBe(2);
      expect(component.publicoInteresDisponibles[0]).toEqual({ value: 'pi1', label: 'Publico 1' });
      expect(component.cargandoPublicoInteres).toBeFalse();
    });

    it('should handle error', () => {
      mockInterestedPublicService.consultarPublicoInteres.and.returnValue(throwError(() => new Error('fail')));
      component.cargarPublicoInteres();
      expect(component.cargandoPublicoInteres).toBeFalse();
    });
  });

  describe('ngOnChanges', () => {
    it('should load indicator data when indicadorAEditar changes', () => {
      spyOn(component, 'cargarDatosIndicador');
      component.ngOnChanges({
        indicadorAEditar: new SimpleChange(null, mockIndicador, false)
      });
      expect(component.cargarDatosIndicador).toHaveBeenCalled();
    });

    it('should not load if indicadorAEditar is null', () => {
      spyOn(component, 'cargarDatosIndicador');
      component.ngOnChanges({
        indicadorAEditar: new SimpleChange(mockIndicador, null, false)
      });
      expect(component.cargarDatosIndicador).not.toHaveBeenCalled();
    });
  });

  describe('cargarDatosIndicador', () => {
    it('should populate indicador form from indicadorAEditar', () => {
      component.indicadorAEditar = mockIndicador;
      component.cargarDatosIndicador();
      expect(component.indicador.nombre).toBe('Indicador Test');
      expect(component.indicador.tipoIndicador).toBe('ti1');
      expect(component.indicador.temporalidad).toBe('f1');
      expect(component.indicador.proyecto).toBe('p1');
      expect(component.indicador.publicosInteres).toEqual(['pi1']);
    });
  });

  describe('modificarIndicador', () => {
    beforeEach(() => {
      component.indicadorAEditar = mockIndicador;
      component.indicador = {
        nombre: 'Modified', tipoIndicador: 'ti1', temporalidad: 'f1',
        proyecto: 'p1', publicosInteres: ['pi1']
      };
    });

    it('should not proceed if cargando is true', () => {
      component.cargando = true;
      component.modificarIndicador();
      expect(mockIndicatorService.modificarIndicador).not.toHaveBeenCalled();
    });

    it('should not proceed if indicadorAEditar is null', () => {
      component.indicadorAEditar = null;
      component.modificarIndicador();
      expect(mockIndicatorService.modificarIndicador).not.toHaveBeenCalled();
    });

    it('should set error if no publico interes selected', () => {
      component.indicador.publicosInteres = [];
      component.modificarIndicador();
      expect(component.error).toContain('público de interés');
      expect(component.cargando).toBeFalse();
    });

    it('should call service on valid data', fakeAsync(() => {
      spyOn(component.indicadorModificado, 'emit');
      component.modificarIndicador();
      expect(mockIndicatorService.modificarIndicador).toHaveBeenCalledWith('i1', {
        nombre: 'Modified', tipoIndicador: 'ti1', temporalidad: 'f1',
        proyecto: 'p1', publicosInteres: ['pi1']
      });
      expect(component.exito).toBe('Indicador modificado exitosamente');
      tick(1500);
      expect(component.indicadorModificado.emit).toHaveBeenCalled();
    }));

    it('should handle service error with mensaje', () => {
      mockIndicatorService.modificarIndicador.and.returnValue(throwError(() => ({
        error: { mensaje: 'Error del servidor' }
      })));
      component.modificarIndicador();
      expect(component.error).toBe('Error del servidor');
      expect(component.cargando).toBeFalse();
    });

    it('should handle service error with string error', () => {
      mockIndicatorService.modificarIndicador.and.returnValue(throwError(() => ({
        error: 'String error'
      })));
      component.modificarIndicador();
      expect(component.error).toBe('String error');
    });
  });

  describe('filterPublicoInteres', () => {
    beforeEach(() => {
      component.publicoInteresDisponibles = [
        { value: 'pi1', label: 'Estudiantes' },
        { value: 'pi2', label: 'Profesores' }
      ];
    });

    it('should return all when search is empty', () => {
      component.searchPublicoInteres = '';
      component.filterPublicoInteres();
      expect(component.publicoInteresFiltrados.length).toBe(2);
    });

    it('should filter by term', () => {
      component.searchPublicoInteres = 'estud';
      component.filterPublicoInteres();
      expect(component.publicoInteresFiltrados.length).toBe(1);
      expect(component.publicoInteresFiltrados[0].label).toBe('Estudiantes');
    });

    it('should return empty when no disponibles', () => {
      component.publicoInteresDisponibles = [];
      component.searchPublicoInteres = 'test';
      component.filterPublicoInteres();
      expect(component.publicoInteresFiltrados).toEqual([]);
    });
  });

  describe('togglePublicoInteres', () => {
    it('should add value if not present', () => {
      component.indicador.publicosInteres = [];
      component.togglePublicoInteres('pi1');
      expect(component.indicador.publicosInteres).toContain('pi1');
    });

    it('should remove value if already present', () => {
      component.indicador.publicosInteres = ['pi1'];
      component.togglePublicoInteres('pi1');
      expect(component.indicador.publicosInteres).not.toContain('pi1');
    });
  });

  describe('isPublicoInteresSelected', () => {
    it('should return true when value is in array', () => {
      component.indicador.publicosInteres = ['pi1'];
      expect(component.isPublicoInteresSelected('pi1')).toBeTrue();
    });

    it('should return false when value is not in array', () => {
      component.indicador.publicosInteres = [];
      expect(component.isPublicoInteresSelected('pi1')).toBeFalse();
    });
  });

  describe('removePublicoInteres', () => {
    it('should remove value from array', () => {
      component.indicador.publicosInteres = ['pi1', 'pi2'];
      component.removePublicoInteres('pi1');
      expect(component.indicador.publicosInteres).toEqual(['pi2']);
    });

    it('should do nothing if value not in array', () => {
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
      component.indicador.publicosInteres = ['pi1'];
      expect(component.getSelectedPublicoInteresLabels()).toEqual(['Estudiantes']);
    });

    it('should return empty array if nothing selected', () => {
      component.indicador.publicosInteres = [];
      expect(component.getSelectedPublicoInteresLabels()).toEqual([]);
    });
  });

  describe('getPublicoInteresValue', () => {
    it('should return value for matching label', () => {
      component.publicoInteresDisponibles = [{ value: 'pi1', label: 'Estudiantes' }];
      expect(component.getPublicoInteresValue('Estudiantes')).toBe('pi1');
    });

    it('should return empty string for unknown label', () => {
      component.publicoInteresDisponibles = [];
      expect(component.getPublicoInteresValue('Unknown')).toBe('');
    });
  });

  describe('cancelar', () => {
    it('should emit indicadorCancelado', () => {
      spyOn(component.indicadorCancelado, 'emit');
      component.cancelar();
      expect(component.indicadorCancelado.emit).toHaveBeenCalled();
    });
  });

  describe('limpiarFormulario', () => {
    it('should reset all fields', () => {
      component.indicador = { nombre: 'Test', tipoIndicador: 'ti1', temporalidad: 'f1', proyecto: 'p1', publicosInteres: ['pi1'] };
      component.error = 'err';
      component.exito = 'ok';
      component.cargando = true;
      component.searchPublicoInteres = 'search';
      component.showDropdown = true;
      component.limpiarFormulario();
      expect(component.indicador.nombre).toBe('');
      expect(component.indicador.tipoIndicador).toBe('');
      expect(component.indicador.publicosInteres.length).toBe(0);
      expect(component.error).toBe('');
      expect(component.exito).toBe('');
      expect(component.cargando).toBeFalse();
      expect(component.searchPublicoInteres).toBe('');
      expect(component.showDropdown).toBeFalse();
    });
  });

  describe('modificarIndicador error variants', () => {
    beforeEach(() => {
      component.indicadorAEditar = mockIndicador;
      component.indicador = {
        nombre: 'Mod', tipoIndicador: 'ti1', temporalidad: 'f1',
        proyecto: 'p1', publicosInteres: ['pi1']
      };
    });

    it('should handle error with message field', () => {
      mockIndicatorService.modificarIndicador.and.returnValue(throwError(() => ({
        error: { message: 'Message error' }
      })));
      component.modificarIndicador();
      expect(component.error).toBe('Message error');
    });

    it('should handle generic error with only message', () => {
      mockIndicatorService.modificarIndicador.and.returnValue(throwError(() => ({
        message: 'Generic msg'
      })));
      component.modificarIndicador();
      expect(component.error).toBe('Generic msg');
    });

    it('should handle completely generic error', () => {
      mockIndicatorService.modificarIndicador.and.returnValue(throwError(() => ({})));
      component.modificarIndicador();
      expect(component.error).toContain('Error al modificar');
    });
  });

  describe('cargarDatosIndicador with null publicosInteres', () => {
    it('should handle null publicosInteres', () => {
      component.indicadorAEditar = {
        ...mockIndicador,
        publicosInteres: null as any
      };
      component.cargarDatosIndicador();
      expect(component.indicador.publicosInteres).toEqual([]);
    });
  });

  describe('removePublicoInteres with null array', () => {
    it('should handle null publicosInteres', () => {
      component.indicador.publicosInteres = null as any;
      expect(() => component.removePublicoInteres('pi1')).not.toThrow();
    });
  });

  describe('togglePublicoInteres with null array', () => {
    it('should initialize and add value', () => {
      component.indicador.publicosInteres = null as any;
      component.togglePublicoInteres('pi1');
      expect(component.indicador.publicosInteres).toEqual(['pi1']);
    });
  });

  describe('isPublicoInteresSelected with null array', () => {
    it('should return false', () => {
      component.indicador.publicosInteres = null as any;
      expect(component.isPublicoInteresSelected('pi1')).toBeFalse();
    });
  });

  describe('getSelectedPublicoInteresLabels with null array', () => {
    it('should return empty array', () => {
      component.indicador.publicosInteres = null as any;
      expect(component.getSelectedPublicoInteresLabels()).toEqual([]);
    });
  });

  describe('getSelectedPublicoInteresLabels with unknown values', () => {
    it('should skip unknown values', () => {
      component.publicoInteresDisponibles = [{ value: 'pi1', label: 'Estudiantes' }];
      component.indicador.publicosInteres = ['unknown-id'];
      expect(component.getSelectedPublicoInteresLabels()).toEqual([]);
    });
  });

  describe('modificarIndicador success with null indicadorAEditar mid-flight', () => {
    it('should return early if indicadorAEditar becomes null in success callback', fakeAsync(() => {
      component.indicadorAEditar = mockIndicador;
      component.indicador = {
        nombre: 'Mod', tipoIndicador: 'ti1', temporalidad: 'f1',
        proyecto: 'p1', publicosInteres: ['pi1']
      };
      spyOn(component.indicadorModificado, 'emit');
      mockIndicatorService.modificarIndicador.and.callFake(() => {
        component.indicadorAEditar = null;
        return of({ valor: 'ok' });
      });
      component.modificarIndicador();
      expect(component.exito).toBe('Indicador modificado exitosamente');
      expect(component.cargando).toBeFalse();
      tick(1500);
      expect(component.indicadorModificado.emit).not.toHaveBeenCalled();
    }));
  });

  describe('cargarDatosIndicador without indicadorAEditar', () => {
    it('should do nothing when indicadorAEditar is null', () => {
      component.indicadorAEditar = null;
      const before = { ...component.indicador };
      component.cargarDatosIndicador();
      expect(component.indicador.nombre).toBe(before.nombre);
    });
  });

  describe('ngOnChanges with unrelated changes', () => {
    it('should not call cargarDatosIndicador for other input changes', () => {
      spyOn(component, 'cargarDatosIndicador');
      component.ngOnChanges({});
      expect(component.cargarDatosIndicador).not.toHaveBeenCalled();
    });
  });
});
