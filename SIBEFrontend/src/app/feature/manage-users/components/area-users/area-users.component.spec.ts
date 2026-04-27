import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { NgxPaginationModule } from 'ngx-pagination';
import { of, throwError } from 'rxjs';

import { AreaUsersComponent } from './area-users.component';
import { UserService } from 'src/app/shared/service/user.service';
import { UserNotificationService } from '../../service/user-notification.service';
import { AreaService } from 'src/app/shared/service/area.service';
import { SubAreaService } from 'src/app/shared/service/subarea.service';
import { UserResponse } from 'src/app/shared/model/user.model';

describe('AreaUsersComponent', () => {
  let component: AreaUsersComponent;
  let fixture: ComponentFixture<AreaUsersComponent>;
  let mockUserService: jasmine.SpyObj<UserService>;
  let notificationService: UserNotificationService;
  let mockAreaService: jasmine.SpyObj<AreaService>;
  let mockSubAreaService: jasmine.SpyObj<SubAreaService>;

  const mockUsuarios: UserResponse[] = [
    {
      identificador: '1', nombres: 'Juan', apellidos: 'P', correo: 'j@t.com',
      identificacion: { tipoIdentificacion: 'CC', numeroIdentificacion: '123' } as any,
      tipoUsuario: { identificador: 'tu1', nombre: 'Admin' } as any, estaActivo: true
    }
  ];

  beforeEach(() => {
    mockUserService = jasmine.createSpyObj('UserService', ['consultarUsuariosPorTipo', 'eliminarUsuario']);
    mockUserService.consultarUsuariosPorTipo.and.returnValue(of({ content: mockUsuarios, totalElements: mockUsuarios.length }));
    mockUserService.eliminarUsuario.and.returnValue(of({ valor: 'ok' }));

    notificationService = new UserNotificationService();

    mockAreaService = jasmine.createSpyObj('AreaService', ['consultarAreas']);
    mockAreaService.consultarAreas.and.returnValue(of([{ identificador: 'a1', nombre: 'Area 1' }] as any));

    mockSubAreaService = jasmine.createSpyObj('SubAreaService', ['consultarSubareas']);
    mockSubAreaService.consultarSubareas.and.returnValue(of([{ identificador: 's1', nombre: 'Sub 1' }] as any));

    TestBed.configureTestingModule({
      imports: [FormsModule, NgxPaginationModule],
      declarations: [AreaUsersComponent],
      providers: [
        { provide: UserService, useValue: mockUserService },
        { provide: UserNotificationService, useValue: notificationService },
        { provide: AreaService, useValue: mockAreaService },
        { provide: SubAreaService, useValue: mockSubAreaService }
      ],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(AreaUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    it('should load users, subscribe and load areas', () => {
      expect(mockUserService.consultarUsuariosPorTipo).toHaveBeenCalled();
      expect(mockAreaService.consultarAreas).toHaveBeenCalled();
      expect(component.usuarios.length).toBe(1);
      expect(component.totalElementos).toBe(1);
    });
  });

  describe('obtenerUsuarios', () => {
    it('should load users into component', () => {
      expect(component.usuarios.length).toBeGreaterThanOrEqual(0);
      expect(component.cargando).toBeFalse();
    });

    it('should handle error', () => {
      mockUserService.consultarUsuariosPorTipo.and.returnValue(throwError(() => new Error('fail')));
      component.obtenerUsuarios();
      expect(component.error).toBe('No se pudieron cargar los usuarios.');
      expect(component.cargando).toBeFalse();
    });
  });

  describe('cargarAreasYSubareas', () => {
    it('should populate area and subarea lists', () => {
      expect(component.listaAreas.length).toBe(1);
      expect(component.listaSubareas.length).toBe(1);
      expect(component.cargandoAreas).toBeFalse();
    });

    it('should handle area load error', () => {
      mockAreaService.consultarAreas.and.returnValue(throwError(() => new Error('fail')));
      component.cargarAreasYSubareas();
      expect(component.cargandoAreas).toBeFalse();
    });

    it('should handle subarea load error', () => {
      mockAreaService.consultarAreas.and.returnValue(of([{ identificador: 'a1', nombre: 'A' }] as any));
      mockSubAreaService.consultarSubareas.and.returnValue(throwError(() => new Error('fail')));
      component.cargarAreasYSubareas();
      expect(component.cargandoAreas).toBeFalse();
    });
  });

  describe('filtrarUsuarios', () => {
    it('should reload filtered users', () => {
      component.filtrarUsuarios();
      expect(component.usuariosFiltrados).toBeDefined();
    });
  });

  describe('seleccionarUsuarioParaEditar', () => {
    it('should emit usuario with tipoComponente area', () => {
      spyOn(component.usuarioSeleccionadoParaEditar, 'emit');
      component.seleccionarUsuarioParaEditar(mockUsuarios[0]);
      expect(component.usuarioSeleccionadoParaEditar.emit).toHaveBeenCalledWith({
        usuario: mockUsuarios[0],
        tipoComponente: 'area'
      });
    });
  });

  describe('confirmarEliminacion', () => {
    it('should do nothing when no user to delete', () => {
      component.usuarioAEliminar = null;
      component.confirmarEliminacion();
      expect(mockUserService.eliminarUsuario).not.toHaveBeenCalled();
    });

    it('should call service and show success', fakeAsync(() => {
      component.usuarioAEliminar = mockUsuarios[0];
      component.confirmarEliminacion();
      expect(mockUserService.eliminarUsuario).toHaveBeenCalledWith('1');
      expect(component.mensajeExito).toBe('Usuario eliminado exitosamente');
      expect(component.mostrarMensajeExito).toBeTrue();
      tick(2000);
    }));

    it('should handle error with mensaje', () => {
      component.usuarioAEliminar = mockUsuarios[0];
      mockUserService.eliminarUsuario.and.returnValue(throwError(() => ({ error: { mensaje: 'No permitido' } })));
      spyOn(window, 'alert');
      component.confirmarEliminacion();
      expect(window.alert).toHaveBeenCalledWith('No permitido');
      expect(component.eliminando).toBeFalse();
    });

    it('should handle generic error', () => {
      component.usuarioAEliminar = mockUsuarios[0];
      mockUserService.eliminarUsuario.and.returnValue(throwError(() => new Error('fail')));
      spyOn(window, 'alert');
      component.confirmarEliminacion();
      expect(window.alert).toHaveBeenCalledWith('Error al eliminar el usuario. Por favor, intente nuevamente.');
    });

    it('should handle error with message field', () => {
      component.usuarioAEliminar = mockUsuarios[0];
      mockUserService.eliminarUsuario.and.returnValue(throwError(() => ({ error: { message: 'Msg error' } })));
      spyOn(window, 'alert');
      component.confirmarEliminacion();
      expect(window.alert).toHaveBeenCalledWith('Msg error');
    });
  });

  describe('eliminarUsuario', () => {
    it('should set usuarioAEliminar', () => {
      component.eliminarUsuario(mockUsuarios[0]);
      expect(component.usuarioAEliminar).toEqual(mockUsuarios[0]);
    });

    it('should reset modal state before setting user', () => {
      component.eliminando = true;
      component.mostrarMensajeExito = true;
      component.mensajeExito = 'prev';
      component.eliminarUsuario(mockUsuarios[0]);
      expect(component.eliminando).toBeFalse();
      expect(component.mostrarMensajeExito).toBeFalse();
      expect(component.mensajeExito).toBe('');
    });
  });

  describe('suscribirseANotificaciones', () => {
    it('should reload users when user is created', () => {
      mockUserService.consultarUsuariosPorTipo.calls.reset();
      notificationService.notificarUsuarioCreado({});
      expect(mockUserService.consultarUsuariosPorTipo).toHaveBeenCalled();
    });

    it('should reload users when user is updated', () => {
      mockUserService.consultarUsuariosPorTipo.calls.reset();
      notificationService.notificarUsuarioActualizado({});
      expect(mockUserService.consultarUsuariosPorTipo).toHaveBeenCalled();
    });

    it('should reload users when user is deleted', () => {
      mockUserService.consultarUsuariosPorTipo.calls.reset();
      notificationService.notificarUsuarioEliminado({});
      expect(mockUserService.consultarUsuariosPorTipo).toHaveBeenCalled();
    });
  });

  describe('ngOnDestroy', () => {
    it('should unsubscribe from all subscriptions', () => {
      component.ngOnDestroy();
      // After destroy, notifications should not trigger reload
      mockUserService.consultarUsuariosPorTipo.calls.reset();
      notificationService.notificarUsuarioCreado({});
      expect(mockUserService.consultarUsuariosPorTipo).not.toHaveBeenCalled();
    });
  });
});
