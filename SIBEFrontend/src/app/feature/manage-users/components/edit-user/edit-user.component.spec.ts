import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { NO_ERRORS_SCHEMA, SimpleChange } from '@angular/core';
import { of, throwError } from 'rxjs';
import * as bootstrap from 'bootstrap';

import { EditUserComponent } from './edit-user.component';
import { UserService } from 'src/app/shared/service/user.service';
import { UserTypeService } from 'src/app/shared/service/user-type.service';
import { IdentificationTypeService } from 'src/app/shared/service/identification-type.service';
import { UserNotificationService } from '../../service/user-notification.service';
import { DepartmentService } from 'src/app/shared/service/department.service';
import { AreaService } from 'src/app/shared/service/area.service';
import { SubAreaService } from 'src/app/shared/service/subarea.service';
import { UserResponse } from 'src/app/shared/model/user.model';

describe('EditUserComponent', () => {
  let component: EditUserComponent;
  let fixture: ComponentFixture<EditUserComponent>;
  let mockUserService: jasmine.SpyObj<UserService>;
  let mockUserTypeService: jasmine.SpyObj<UserTypeService>;
  let mockIdentificationTypeService: jasmine.SpyObj<IdentificationTypeService>;
  let mockNotificationService: jasmine.SpyObj<UserNotificationService>;
  let mockDepartmentService: jasmine.SpyObj<DepartmentService>;
  let mockAreaService: jasmine.SpyObj<AreaService>;
  let mockSubAreaService: jasmine.SpyObj<SubAreaService>;

  const mockTiposUsuario = [
    { identificador: 'tu1', nombre: 'Colaborador' },
    { identificador: 'tu2', nombre: 'Administrador de dirección' }
  ];
  const mockTiposId = [{ identificador: 'tid1', nombre: 'CC' }];
  const mockDepts = [{ identificador: 'd1', nombre: 'Dir 1' }];
  const mockAreas = [{ identificador: 'a1', nombre: 'Area 1', tipoArea: 'AREA' }];
  const mockSubareas = [{ identificador: 's1', nombre: 'Sub 1' }];

  const mockUsuario: UserResponse = {
    identificador: 'u1', nombres: 'Juan', apellidos: 'Perez', correo: 'j@t.com',
    identificacion: { tipoIdentificacion: { identificador: 'tid1', nombre: 'CC' }, numeroIdentificacion: '123' } as any,
    tipoUsuario: { identificador: 'tu1', nombre: 'Colaborador' } as any,
    estaActivo: true,
    area: { identificador: 'a1', tipoArea: 'AREA' } as any
  };

  beforeEach(() => {
    mockUserService = jasmine.createSpyObj('UserService', ['modificarUsuario']);
    mockUserTypeService = jasmine.createSpyObj('UserTypeService', ['consultarTipoUsuario']);
    mockIdentificationTypeService = jasmine.createSpyObj('IdentificationTypeService', ['consultarTipoIdentificacion']);
    mockNotificationService = jasmine.createSpyObj('UserNotificationService', ['notificarUsuarioActualizado']);
    mockDepartmentService = jasmine.createSpyObj('DepartmentService', ['consultarDirecciones']);
    mockAreaService = jasmine.createSpyObj('AreaService', ['consultarAreas']);
    mockSubAreaService = jasmine.createSpyObj('SubAreaService', ['consultarSubareas']);

    mockUserTypeService.consultarTipoUsuario.and.returnValue(of(mockTiposUsuario as any));
    mockIdentificationTypeService.consultarTipoIdentificacion.and.returnValue(of(mockTiposId as any));
    mockDepartmentService.consultarDirecciones.and.returnValue(of(mockDepts as any));
    mockAreaService.consultarAreas.and.returnValue(of(mockAreas as any));
    mockSubAreaService.consultarSubareas.and.returnValue(of(mockSubareas as any));
    mockUserService.modificarUsuario.and.returnValue(of({ valor: 'ok' }));

    TestBed.configureTestingModule({
      imports: [FormsModule],
      declarations: [EditUserComponent],
      providers: [
        { provide: UserService, useValue: mockUserService },
        { provide: UserTypeService, useValue: mockUserTypeService },
        { provide: IdentificationTypeService, useValue: mockIdentificationTypeService },
        { provide: UserNotificationService, useValue: mockNotificationService },
        { provide: DepartmentService, useValue: mockDepartmentService },
        { provide: AreaService, useValue: mockAreaService },
        { provide: SubAreaService, useValue: mockSubAreaService }
      ],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(EditUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    it('should load types and structures', () => {
      expect(mockUserTypeService.consultarTipoUsuario).toHaveBeenCalled();
      expect(mockIdentificationTypeService.consultarTipoIdentificacion).toHaveBeenCalled();
      expect(mockDepartmentService.consultarDirecciones).toHaveBeenCalled();
    });
  });

  describe('cargarEstructurasOrganizacionales', () => {
    it('should populate lists', () => {
      expect(component.listaDepartamentos.length).toBe(1);
      expect(component.listaAreas.length).toBe(1);
      expect(component.listaSubareas.length).toBe(1);
    });

    it('should call cargarDatosUsuario when usuario already set', () => {
      component.usuarioAEditar = mockUsuario;
      spyOn(component, 'cargarDatosUsuario');
      component.cargarEstructurasOrganizacionales();
      expect(component.cargarDatosUsuario).toHaveBeenCalled();
    });

    it('should handle error', () => {
      mockDepartmentService.consultarDirecciones.and.returnValue(throwError(() => new Error('fail')));
      mockAreaService.consultarAreas.and.returnValue(of([]));
      mockSubAreaService.consultarSubareas.and.returnValue(of([]));
      component.cargarEstructurasOrganizacionales();
      expect(component.cargandoEstructuras).toBeFalse();
    });
  });

  describe('cargarTiposUsuario', () => {
    it('should handle error', () => {
      mockUserTypeService.consultarTipoUsuario.and.returnValue(throwError(() => new Error('fail')));
      component.cargarTiposUsuario();
      expect(component.errorTiposUsuario).toBe('Error al cargar los tipos de usuario');
    });
  });

  describe('cargarTiposIdentificacion', () => {
    it('should handle error', () => {
      mockIdentificationTypeService.consultarTipoIdentificacion.and.returnValue(throwError(() => new Error('fail')));
      component.cargarTiposIdentificacion();
      expect(component.errorTiposIdentificacion).toBe('Error al cargar los tipos de identificación');
    });
  });

  describe('ngOnChanges', () => {
    it('should call cargarDatosUsuario when usuario changes and structures loaded', () => {
      spyOn(component, 'cargarDatosUsuario');
      component.usuarioAEditar = mockUsuario;
      component.ngOnChanges({
        usuarioAEditar: new SimpleChange(null, mockUsuario, false)
      });
      expect(component.cargarDatosUsuario).toHaveBeenCalled();
    });

    it('should call limpiarFormulario when usuario is null', () => {
      spyOn(component, 'limpiarFormulario');
      component.ngOnChanges({
        usuarioAEditar: new SimpleChange(mockUsuario, null, false)
      });
      expect(component.limpiarFormulario).toHaveBeenCalled();
    });

    it('should not call cargarDatosUsuario if structures still loading', () => {
      spyOn(component, 'cargarDatosUsuario');
      component.cargandoEstructuras = true;
      component.usuarioAEditar = mockUsuario;
      component.ngOnChanges({
        usuarioAEditar: new SimpleChange(null, mockUsuario, false)
      });
      expect(component.cargarDatosUsuario).not.toHaveBeenCalled();
    });
  });

  describe('cargarDatosUsuario', () => {
    it('should populate form from usuario', () => {
      component.usuarioAEditar = mockUsuario;
      component.cargarDatosUsuario();
      expect(component.usuario.nombres).toBe('Juan');
      expect(component.usuario.apellidos).toBe('Perez');
      expect(component.usuario.correo).toBe('j@t.com');
      expect(component.usuario.tipoEstructura).toBe('area');
    });
  });

  describe('esAdministradorDireccion', () => {
    it('should return true when admin selected', () => {
      component.usuario.tipoUsuario = 'tu2';
      expect(component.esAdministradorDireccion()).toBeTrue();
    });

    it('should check usuarioAEditar when no tipoUsuario', () => {
      component.usuario.tipoUsuario = '';
      component.usuarioAEditar = {
        ...mockUsuario,
        tipoUsuario: { identificador: 'tu2', nombre: 'Administrador de dirección' } as any
      };
      expect(component.esAdministradorDireccion()).toBeTrue();
    });

    it('should return false when no tipoUsuario and no usuario', () => {
      component.usuario.tipoUsuario = '';
      component.usuarioAEditar = null;
      expect(component.esAdministradorDireccion()).toBeFalse();
    });
  });

  describe('determinarTipoEstructuraInicial', () => {
    it('should return empty for admin', () => {
      component.usuarioAEditar = {
        ...mockUsuario,
        tipoUsuario: { identificador: 'tu2', nombre: 'Administrador de dirección' } as any
      };
      component.usuario.tipoUsuario = 'tu2';
      expect(component.determinarTipoEstructuraInicial()).toBe('');
    });

    it('should return area when tipoArea is AREA', () => {
      component.usuarioAEditar = mockUsuario;
      expect(component.determinarTipoEstructuraInicial()).toBe('area');
    });

    it('should return subarea when tipoArea is SUBAREA', () => {
      component.usuarioAEditar = { ...mockUsuario, area: { identificador: 's1', tipoArea: 'SUBAREA' } as any };
      expect(component.determinarTipoEstructuraInicial()).toBe('subarea');
    });

    it('should fallback to checking lists by identifier', () => {
      component.usuarioAEditar = { ...mockUsuario, area: { identificador: 'a1' } as any };
      expect(component.determinarTipoEstructuraInicial()).toBe('area');
    });
  });

  describe('configurarListaFiltrada', () => {
    it('should set departamentos for admin', () => {
      component.usuario.tipoUsuario = 'tu2';
      component.configurarListaFiltrada();
      expect(component.listaFiltrada).toEqual(component.listaDepartamentos);
    });

    it('should set areas for area type', () => {
      component.usuario.tipoUsuario = 'tu1';
      component.usuario.tipoEstructura = 'area';
      component.configurarListaFiltrada();
      expect(component.listaFiltrada).toEqual(component.listaAreas);
    });

    it('should set subareas for subarea type', () => {
      component.usuario.tipoUsuario = 'tu1';
      component.usuario.tipoEstructura = 'subarea';
      component.configurarListaFiltrada();
      expect(component.listaFiltrada).toEqual(component.listaSubareas);
    });

    it('should set empty for unknown', () => {
      component.usuario.tipoUsuario = 'tu1';
      component.usuario.tipoEstructura = '';
      component.configurarListaFiltrada();
      expect(component.listaFiltrada).toEqual([]);
    });
  });

  describe('onTipoEstructuraChange', () => {
    it('should filter to areas', () => {
      component.usuario.tipoEstructura = 'area';
      component.onTipoEstructuraChange();
      expect(component.listaFiltrada).toEqual(component.listaAreas);
      expect(component.usuario.estructuraOrganizacional).toBe('');
    });
  });

  describe('determinarTipoArea', () => {
    it('should return DIRECCION for admin', () => {
      component.usuario.tipoUsuario = 'tu2';
      expect(component.determinarTipoArea()).toBe('DIRECCION');
    });

    it('should return AREA', () => {
      component.usuario.tipoUsuario = 'tu1';
      component.usuario.tipoEstructura = 'area';
      expect(component.determinarTipoArea()).toBe('AREA');
    });

    it('should return SUBAREA', () => {
      component.usuario.tipoUsuario = 'tu1';
      component.usuario.tipoEstructura = 'subarea';
      expect(component.determinarTipoArea()).toBe('SUBAREA');
    });
  });

  describe('validarFormulario', () => {
    it('should return true with all fields', () => {
      component.usuario = {
        nombres: 'J', apellidos: 'P', tipoIdentificacion: 'tid1',
        numeroIdentificacion: '1', correo: 'j@t', tipoUsuario: 'tu1',
        estructuraOrganizacional: 'a1', tipoEstructura: 'area'
      };
      expect(component.validarFormulario()).toBeTrue();
    });

    it('should return false when missing field', () => {
      component.usuario = {
        nombres: '', apellidos: 'P', tipoIdentificacion: 'tid1',
        numeroIdentificacion: '1', correo: 'j@t', tipoUsuario: 'tu1',
        estructuraOrganizacional: 'a1', tipoEstructura: 'area'
      };
      expect(component.validarFormulario()).toBeFalse();
    });
  });

  describe('actualizarUsuario', () => {
    beforeEach(() => {
      component.usuarioAEditar = mockUsuario;
      component.usuario = {
        nombres: 'Juan', apellidos: 'Perez', tipoIdentificacion: 'tid1',
        numeroIdentificacion: '123', correo: 'j@t.com', tipoUsuario: 'tu1',
        estructuraOrganizacional: 'a1', tipoEstructura: 'area'
      };
    });

    it('should set error when form invalid', () => {
      component.usuario.nombres = '';
      component.actualizarUsuario();
      expect(component.error).toBe('Por favor, complete todos los campos requeridos');
    });

    it('should set error when no usuario to edit', () => {
      component.usuarioAEditar = null;
      component.actualizarUsuario();
      expect(component.error).toBe('No se ha seleccionado un usuario para editar');
    });

    it('should call service on success', fakeAsync(() => {
      spyOn(component, 'cerrarModal');
      spyOn(component, 'limpiarFormulario');
      component.actualizarUsuario();
      expect(mockUserService.modificarUsuario).toHaveBeenCalledWith('u1', jasmine.any(Object));
      expect(component.mensajeExito).toBe('Usuario actualizado exitosamente');
      expect(mockNotificationService.notificarUsuarioActualizado).toHaveBeenCalled();
      tick(1500);
      expect(component.limpiarFormulario).toHaveBeenCalled();
      expect(component.cerrarModal).toHaveBeenCalled();
    }));

    it('should handle error with mensaje', () => {
      mockUserService.modificarUsuario.and.returnValue(throwError(() => ({ error: { mensaje: 'Dup' } })));
      component.actualizarUsuario();
      expect(component.error).toBe('Dup');
    });

    it('should handle error with message', () => {
      mockUserService.modificarUsuario.and.returnValue(throwError(() => ({ error: { message: 'Bad' } })));
      component.actualizarUsuario();
      expect(component.error).toBe('Bad');
    });

    it('should handle generic error', () => {
      mockUserService.modificarUsuario.and.returnValue(throwError(() => new Error('x')));
      component.actualizarUsuario();
      expect(component.error).toBe('Error al actualizar el usuario. Por favor, intente nuevamente.');
    });
  });

  describe('limpiarFormulario', () => {
    it('should reset all fields and clear usuarioAEditar', () => {
      component.usuario.nombres = 'Test';
      component.error = 'e';
      component.mensajeExito = 's';
      component.usuarioAEditar = mockUsuario;
      component.limpiarFormulario();
      expect(component.usuario.nombres).toBe('');
      expect(component.error).toBe('');
      expect(component.mensajeExito).toBe('');
      expect(component.usuarioAEditar).toBeNull();
    });
  });

  describe('onTipoUsuarioChange', () => {
    it('should clear structure fields', () => {
      component.usuario.tipoEstructura = 'area';
      component.usuario.estructuraOrganizacional = 'a1';
      component.onTipoUsuarioChange();
      expect(component.usuario.tipoEstructura).toBe('');
      expect(component.usuario.estructuraOrganizacional).toBe('');
    });
  });

  describe('abrirModal', () => {
    it('should not throw when modal element not found', () => {
      spyOn(document, 'getElementById').and.returnValue(null);
      expect(() => component.abrirModal()).not.toThrow();
    });
  });

  describe('cerrarModal', () => {
    it('should not throw when modal element not found', () => {
      spyOn(document, 'getElementById').and.returnValue(null);
      expect(() => component.cerrarModal()).not.toThrow();
    });
  });

  describe('validarFormulario for admin direction user', () => {
    it('should return true with basic fields and estructuraOrganizacional for admin', () => {
      component.usuario = {
        nombres: 'J', apellidos: 'P', tipoIdentificacion: 'tid1',
        numeroIdentificacion: '1', correo: 'j@t', tipoUsuario: 'tu2',
        estructuraOrganizacional: 'd1', tipoEstructura: ''
      };
      expect(component.validarFormulario()).toBeTrue();
    });

    it('should return false for admin without estructuraOrganizacional', () => {
      component.usuario = {
        nombres: 'J', apellidos: 'P', tipoIdentificacion: 'tid1',
        numeroIdentificacion: '1', correo: 'j@t', tipoUsuario: 'tu2',
        estructuraOrganizacional: '', tipoEstructura: ''
      };
      expect(component.validarFormulario()).toBeFalse();
    });
  });

  describe('determinarTipoEstructuraInicial fallback to subarea list', () => {
    it('should detect subarea by identifier', () => {
      component.usuarioAEditar = { ...mockUsuario, area: { identificador: 's1' } as any };
      expect(component.determinarTipoEstructuraInicial()).toBe('subarea');
    });

    it('should return empty when identifier not found in any list', () => {
      component.usuarioAEditar = { ...mockUsuario, area: { identificador: 'unknown-id' } as any };
      expect(component.determinarTipoEstructuraInicial()).toBe('');
    });
  });

  describe('onTipoEstructuraChange for subarea', () => {
    it('should filter to subareas', () => {
      component.usuario.tipoEstructura = 'subarea';
      component.onTipoEstructuraChange();
      expect(component.listaFiltrada).toEqual(component.listaSubareas);
      expect(component.usuario.estructuraOrganizacional).toBe('');
    });

    it('should clear list for empty tipo', () => {
      component.usuario.tipoEstructura = '';
      component.onTipoEstructuraChange();
      expect(component.listaFiltrada).toEqual([]);
    });
  });

  describe('determinarTipoArea edge cases', () => {
    it('should return empty string for non-admin unknown tipoEstructura', () => {
      component.usuario.tipoUsuario = 'tu1';
      component.usuario.tipoEstructura = '';
      expect(component.determinarTipoArea()).toBe('');
    });
  });

  describe('cargarEstructurasOrganizacionales error handling', () => {
    it('should set cargandoEstructuras false on error', () => {
      mockDepartmentService.consultarDirecciones.and.returnValue(throwError(() => new Error('fail')));
      mockAreaService.consultarAreas.and.returnValue(of([]));
      mockSubAreaService.consultarSubareas.and.returnValue(of([]));
      component.cargarEstructurasOrganizacionales();
      expect(component.cargandoEstructuras).toBeFalse();
    });
  });

  describe('ngOnChanges structures still loading', () => {
    it('should not call cargarDatosUsuario if lists are empty and not loading', () => {
      spyOn(component, 'cargarDatosUsuario');
      component.cargandoEstructuras = false;
      component.listaDepartamentos = [];
      component.listaAreas = [];
      component.listaSubareas = [];
      component.usuarioAEditar = mockUsuario;
      component.ngOnChanges({
        usuarioAEditar: new SimpleChange(null, mockUsuario, false)
      });
      expect(component.cargarDatosUsuario).not.toHaveBeenCalled();
    });
  });

  describe('abrirModal with DOM element', () => {
    it('should show modal when element found', () => {
      const mockModal = { show: jasmine.createSpy('show') };
      spyOn(bootstrap.Modal, 'getInstance').and.returnValue(mockModal as any);
      const el = document.createElement('div');
      spyOn(document, 'getElementById').and.returnValue(el);
      component.abrirModal();
      expect(mockModal.show).toHaveBeenCalled();
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
});
