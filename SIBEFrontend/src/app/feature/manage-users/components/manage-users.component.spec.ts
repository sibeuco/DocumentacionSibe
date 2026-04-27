import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { of, throwError } from 'rxjs';

import { ManageUsersComponent } from './manage-users.component';
import { DepartmentService } from 'src/app/shared/service/department.service';
import { AreaService } from 'src/app/shared/service/area.service';
import { SubAreaService } from 'src/app/shared/service/subarea.service';
import { EditUserComponent } from './edit-user/edit-user.component';

describe('ManageUsersComponent', () => {
  let component: ManageUsersComponent;
  let fixture: ComponentFixture<ManageUsersComponent>;
  let mockDepartmentService: jasmine.SpyObj<DepartmentService>;
  let mockAreaService: jasmine.SpyObj<AreaService>;
  let mockSubAreaService: jasmine.SpyObj<SubAreaService>;

  const mockDepartments = [{ identificador: 'd1', nombre: 'Dir 1' }];
  const mockAreas = [{ identificador: 'a1', nombre: 'Area 1', tipoArea: 'AREA' }];
  const mockSubareas = [{ identificador: 's1', nombre: 'Sub 1' }];

  beforeEach(() => {
    mockDepartmentService = jasmine.createSpyObj('DepartmentService', ['consultarDirecciones']);
    mockDepartmentService.consultarDirecciones.and.returnValue(of(mockDepartments));

    mockAreaService = jasmine.createSpyObj('AreaService', ['consultarAreas']);
    mockAreaService.consultarAreas.and.returnValue(of(mockAreas));

    mockSubAreaService = jasmine.createSpyObj('SubAreaService', ['consultarSubareas']);
    mockSubAreaService.consultarSubareas.and.returnValue(of(mockSubareas));

    TestBed.configureTestingModule({
      declarations: [ManageUsersComponent],
      providers: [
        { provide: DepartmentService, useValue: mockDepartmentService },
        { provide: AreaService, useValue: mockAreaService },
        { provide: SubAreaService, useValue: mockSubAreaService }
      ],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(ManageUsersComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    fixture.detectChanges();
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    it('should load departments, areas and subareas', () => {
      fixture.detectChanges();
      expect(mockDepartmentService.consultarDirecciones).toHaveBeenCalled();
      expect(mockAreaService.consultarAreas).toHaveBeenCalled();
      expect(mockSubAreaService.consultarSubareas).toHaveBeenCalled();
    });
  });

  describe('cargarDepartamentos', () => {
    it('should populate listaDepartamentos on success', () => {
      component.cargarDepartamentos();
      expect(component.listaDepartamentos).toEqual([{ identificador: 'd1', nombre: 'Dir 1' }]);
    });

    it('should handle error gracefully', () => {
      mockDepartmentService.consultarDirecciones.and.returnValue(throwError(() => new Error('fail')));
      component.cargarDepartamentos();
      expect(component.listaDepartamentos).toEqual([]);
    });
  });

  describe('cargarAreasYSubareas', () => {
    it('should populate listaAreas on success', () => {
      component.cargarAreasYSubareas();
      expect(component.listaAreas).toEqual([{ identificador: 'a1', nombre: 'Area 1' }]);
    });

    it('should populate listaSubareas on success', () => {
      component.cargarAreasYSubareas();
      expect(component.listaSubareas).toEqual([{ identificador: 's1', nombre: 'Sub 1' }]);
    });

    it('should handle area error gracefully', () => {
      mockAreaService.consultarAreas.and.returnValue(throwError(() => new Error('fail')));
      component.cargarAreasYSubareas();
      expect(component.listaAreas).toEqual([]);
    });

    it('should handle subarea error gracefully', () => {
      mockSubAreaService.consultarSubareas.and.returnValue(throwError(() => new Error('fail')));
      component.cargarAreasYSubareas();
      expect(component.listaSubareas).toEqual([]);
    });
  });

  describe('onUsuarioSeleccionadoParaEditar', () => {
    it('should set usuarioSeleccionado and tipoComponenteOrigen', fakeAsync(() => {
      const mockUser = { identificador: '1', nombres: 'Test' } as any;
      component.onUsuarioSeleccionadoParaEditar({ usuario: mockUser, tipoComponente: 'area' });
      expect(component.usuarioSeleccionado).toEqual(mockUser);
      expect(component.tipoComponenteOrigen).toBe('area');
      tick(100);
    }));
  });

  describe('onUsuarioActualizado', () => {
    it('should clear usuarioSeleccionado', () => {
      component.usuarioSeleccionado = { identificador: '1' } as any;
      component.onUsuarioActualizado({});
      expect(component.usuarioSeleccionado).toBeNull();
    });
  });

  describe('abrirModalEditar', () => {
    it('should call editUserComponent.abrirModal if available', () => {
      const mockEditUser = jasmine.createSpyObj('EditUserComponent', ['abrirModal']);
      component.editUserComponent = mockEditUser;
      component.abrirModalEditar();
      expect(mockEditUser.abrirModal).toHaveBeenCalled();
    });

    it('should not throw if editUserComponent is undefined', () => {
      component.editUserComponent = undefined as any;
      expect(() => component.abrirModalEditar()).not.toThrow();
    });
  });
});
