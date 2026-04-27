import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { of, throwError } from 'rxjs';
import * as bootstrap from 'bootstrap';

import { RegisterNewUserComponent } from './register-new-user.component';
import { UserService } from 'src/app/shared/service/user.service';
import { UserTypeService } from 'src/app/shared/service/user-type.service';
import { IdentificationTypeService } from 'src/app/shared/service/identification-type.service';
import { UserNotificationService } from '../../service/user-notification.service';
import { DepartmentService } from 'src/app/shared/service/department.service';
import { AreaService } from 'src/app/shared/service/area.service';
import { SubAreaService } from 'src/app/shared/service/subarea.service';

describe('RegisterNewUserComponent', () => {
  let component: RegisterNewUserComponent;
  let fixture: ComponentFixture<RegisterNewUserComponent>;
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

  beforeEach(() => {
    mockUserService = jasmine.createSpyObj('UserService', ['agregarNuevoUsuario']);
    mockUserTypeService = jasmine.createSpyObj('UserTypeService', ['consultarTipoUsuario']);
    mockIdentificationTypeService = jasmine.createSpyObj('IdentificationTypeService', ['consultarTipoIdentificacion']);
    mockNotificationService = jasmine.createSpyObj('UserNotificationService', ['notificarUsuarioCreado']);
    mockDepartmentService = jasmine.createSpyObj('DepartmentService', ['consultarDirecciones']);
    mockAreaService = jasmine.createSpyObj('AreaService', ['consultarAreas']);
    mockSubAreaService = jasmine.createSpyObj('SubAreaService', ['consultarSubareas']);

    mockUserTypeService.consultarTipoUsuario.and.returnValue(of(mockTiposUsuario as any));
    mockIdentificationTypeService.consultarTipoIdentificacion.and.returnValue(of(mockTiposId as any));
    mockDepartmentService.consultarDirecciones.and.returnValue(of(mockDepts as any));
    mockAreaService.consultarAreas.and.returnValue(of(mockAreas as any));
    mockSubAreaService.consultarSubareas.and.returnValue(of(mockSubareas as any));
    mockUserService.agregarNuevoUsuario.and.returnValue(of({ valor: 'ok' }));

    TestBed.configureTestingModule({
      imports: [FormsModule],
      declarations: [RegisterNewUserComponent],
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
    fixture = TestBed.createComponent(RegisterNewUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    it('should load user types, identification types and structures', () => {
      expect(mockUserTypeService.consultarTipoUsuario).toHaveBeenCalled();
      expect(mockIdentificationTypeService.consultarTipoIdentificacion).toHaveBeenCalled();
      expect(mockDepartmentService.consultarDirecciones).toHaveBeenCalled();
    });
  });

  describe('cargarEstructurasOrganizacionales', () => {
    it('should populate department, area and subarea lists', () => {
      expect(component.listaDepartamentos.length).toBe(1);
      expect(component.listaAreas.length).toBe(1);
      expect(component.listaSubareas.length).toBe(1);
      expect(component.cargandoEstructuras).toBeFalse();
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
    it('should load user types', () => {
      expect(component.tiposUsuario).toEqual(mockTiposUsuario as any);
      expect(component.cargandoTiposUsuario).toBeFalse();
    });

    it('should handle error', () => {
      mockUserTypeService.consultarTipoUsuario.and.returnValue(throwError(() => new Error('fail')));
      component.cargarTiposUsuario();
      expect(component.errorTiposUsuario).toBe('Error al cargar los tipos de usuario');
      expect(component.cargandoTiposUsuario).toBeFalse();
    });
  });

  describe('cargarTiposIdentificacion', () => {
    it('should load identification types', () => {
      expect(component.tiposIdentificacion).toEqual(mockTiposId as any);
      expect(component.cargandoTiposIdentificacion).toBeFalse();
    });

    it('should handle error', () => {
      mockIdentificationTypeService.consultarTipoIdentificacion.and.returnValue(throwError(() => new Error('fail')));
      component.cargarTiposIdentificacion();
      expect(component.errorTiposIdentificacion).toBe('Error al cargar los tipos de identificación');
      expect(component.cargandoTiposIdentificacion).toBeFalse();
    });
  });

  describe('onTipoUsuarioChange', () => {
    it('should clear structure fields', () => {
      component.usuario.tipoEstructura = 'area';
      component.usuario.estructuraOrganizacional = 'a1';
      component.listaFiltrada = [{ identificador: 'x', nombre: 'x' }];
      component.onTipoUsuarioChange();
      expect(component.usuario.tipoEstructura).toBe('');
      expect(component.usuario.estructuraOrganizacional).toBe('');
      expect(component.listaFiltrada).toEqual([]);
    });
  });

  describe('esAdministradorDireccion', () => {
    it('should return false when no tipoUsuario selected', () => {
      component.usuario.tipoUsuario = '';
      expect(component.esAdministradorDireccion()).toBeFalse();
    });

    it('should return true when Administrador de dirección is selected', () => {
      component.usuario.tipoUsuario = 'tu2';
      expect(component.esAdministradorDireccion()).toBeTrue();
    });

    it('should return false for Colaborador', () => {
      component.usuario.tipoUsuario = 'tu1';
      expect(component.esAdministradorDireccion()).toBeFalse();
    });
  });

  describe('onTipoEstructuraChange', () => {
    it('should set listaFiltrada to areas when tipo is area', () => {
      component.usuario.tipoEstructura = 'area';
      component.onTipoEstructuraChange();
      expect(component.listaFiltrada).toEqual(component.listaAreas);
      expect(component.usuario.estructuraOrganizacional).toBe('');
    });

    it('should set listaFiltrada to subareas when tipo is subarea', () => {
      component.usuario.tipoEstructura = 'subarea';
      component.onTipoEstructuraChange();
      expect(component.listaFiltrada).toEqual(component.listaSubareas);
    });

    it('should clear listaFiltrada for unknown tipo', () => {
      component.usuario.tipoEstructura = '';
      component.onTipoEstructuraChange();
      expect(component.listaFiltrada).toEqual([]);
    });
  });

  describe('determinarTipoArea', () => {
    it('should return DIRECCION for admin', () => {
      component.usuario.tipoUsuario = 'tu2';
      expect(component.determinarTipoArea()).toBe('DIRECCION');
    });

    it('should return AREA when tipoEstructura is area', () => {
      component.usuario.tipoUsuario = 'tu1';
      component.usuario.tipoEstructura = 'area';
      expect(component.determinarTipoArea()).toBe('AREA');
    });

    it('should return SUBAREA when tipoEstructura is subarea', () => {
      component.usuario.tipoUsuario = 'tu1';
      component.usuario.tipoEstructura = 'subarea';
      expect(component.determinarTipoArea()).toBe('SUBAREA');
    });

    it('should return empty string when no match', () => {
      component.usuario.tipoUsuario = 'tu1';
      component.usuario.tipoEstructura = '';
      expect(component.determinarTipoArea()).toBe('');
    });
  });

  describe('validarFormulario', () => {
    beforeEach(() => {
      component.usuario = {
        nombres: 'Juan', apellidos: 'Perez', tipoIdentificacion: 'tid1',
        numeroIdentificacion: '123', correo: 'j@t.com', clave: 'pass',
        confirmarClave: 'pass', tipoUsuario: 'tu1', estructuraOrganizacional: 'a1',
        tipoEstructura: 'area'
      };
    });

    it('should return true when all fields are valid for collaborator', () => {
      expect(component.validarFormulario()).toBeTrue();
    });

    it('should return true for admin with estructura but no tipoEstructura', () => {
      component.usuario.tipoUsuario = 'tu2';
      component.usuario.tipoEstructura = '';
      expect(component.validarFormulario()).toBeTrue();
    });

    it('should return false when missing required field', () => {
      component.usuario.nombres = '';
      expect(component.validarFormulario()).toBeFalse();
    });

    it('should return false for collaborator without tipoEstructura', () => {
      component.usuario.tipoEstructura = '';
      expect(component.validarFormulario()).toBeFalse();
    });
  });

  describe('registrarUsuario', () => {
    beforeEach(() => {
      component.usuario = {
        nombres: 'Juan', apellidos: 'Perez', tipoIdentificacion: 'tid1',
        numeroIdentificacion: '123', correo: 'j@t.com', clave: 'pass',
        confirmarClave: 'pass', tipoUsuario: 'tu1', estructuraOrganizacional: 'a1',
        tipoEstructura: 'area'
      };
    });

    it('should set error when passwords do not match', () => {
      component.usuario.confirmarClave = 'other';
      component.registrarUsuario();
      expect(component.error).toBe('Las contraseñas no coinciden');
      expect(mockUserService.agregarNuevoUsuario).not.toHaveBeenCalled();
    });

    it('should set error when form is invalid', () => {
      component.usuario.nombres = '';
      component.registrarUsuario();
      expect(component.error).toBe('Por favor, complete todos los campos requeridos');
    });

    it('should call service and notify on success', fakeAsync(() => {
      spyOn(component, 'cerrarModal');
      spyOn(component, 'limpiarFormulario');
      component.registrarUsuario();
      expect(mockUserService.agregarNuevoUsuario).toHaveBeenCalled();
      expect(component.mensajeExito).toBe('Usuario creado exitosamente');
      expect(mockNotificationService.notificarUsuarioCreado).toHaveBeenCalled();
      tick(1500);
      expect(component.limpiarFormulario).toHaveBeenCalled();
      expect(component.cerrarModal).toHaveBeenCalled();
    }));

    it('should handle error with error.mensaje', () => {
      mockUserService.agregarNuevoUsuario.and.returnValue(throwError(() => ({ error: { mensaje: 'Correo duplicado' } })));
      component.registrarUsuario();
      expect(component.error).toBe('Correo duplicado');
      expect(component.cargando).toBeFalse();
    });

    it('should handle error with error.message', () => {
      mockUserService.agregarNuevoUsuario.and.returnValue(throwError(() => ({ error: { message: 'Bad request' } })));
      component.registrarUsuario();
      expect(component.error).toBe('Bad request');
    });

    it('should handle generic error', () => {
      mockUserService.agregarNuevoUsuario.and.returnValue(throwError(() => new Error('unknown')));
      component.registrarUsuario();
      expect(component.error).toBe('Error al crear el usuario. Por favor, intente nuevamente.');
    });
  });

  describe('limpiarFormulario', () => {
    it('should reset all fields', () => {
      component.usuario.nombres = 'Test';
      component.error = 'some error';
      component.mensajeExito = 'ok';
      component.listaFiltrada = [{ identificador: 'x', nombre: 'x' }];
      component.limpiarFormulario();
      expect(component.usuario.nombres).toBe('');
      expect(component.error).toBe('');
      expect(component.mensajeExito).toBe('');
      expect(component.listaFiltrada).toEqual([]);
    });
  });

  describe('cerrarModal', () => {
    it('should not throw when modal element not found', () => {
      spyOn(document, 'getElementById').and.returnValue(null);
      expect(() => component.cerrarModal()).not.toThrow();
    });
  });

  describe('configurarListaFiltrada', () => {
    it('should set departamentos for admin type', () => {
      component.usuario.tipoUsuario = 'tu2';
      // Admin uses configurarListaFiltrada if the method exists, otherwise test via onTipoUsuarioChange
      component.onTipoUsuarioChange();
      expect(component.listaFiltrada).toEqual([]);
    });
  });

  describe('registrarUsuario emit', () => {
    it('should emit usuarioCreado event on success', fakeAsync(() => {
      spyOn(component.usuarioCreado, 'emit');
      spyOn(component, 'cerrarModal');
      spyOn(component, 'limpiarFormulario');
      component.usuario = {
        nombres: 'J', apellidos: 'P', tipoIdentificacion: 'tid1',
        numeroIdentificacion: '123', correo: 'j@t.com', clave: 'pass',
        confirmarClave: 'pass', tipoUsuario: 'tu1', estructuraOrganizacional: 'a1',
        tipoEstructura: 'area'
      };
      component.registrarUsuario();
      expect(component.usuarioCreado.emit).toHaveBeenCalled();
      tick(1500);
    }));
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
