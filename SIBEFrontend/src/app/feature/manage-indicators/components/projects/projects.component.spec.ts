import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { of, throwError } from 'rxjs';
import * as bootstrap from 'bootstrap';

import { ProjectsComponent } from './projects.component';
import { ProjectService } from '../../service/project.service';
import { ProjectResponse } from '../../model/project.model';
import { NgxPaginationModule } from 'ngx-pagination';

describe('ProjectsComponent', () => {
  let component: ProjectsComponent;
  let fixture: ComponentFixture<ProjectsComponent>;
  let mockProjectService: jasmine.SpyObj<ProjectService>;

  const mockProyectos: ProjectResponse[] = [
    { identificador: 'p1', numeroProyecto: '001', nombre: 'Proyecto 1', objetivo: 'Obj 1' },
    { identificador: 'p2', numeroProyecto: '002', nombre: 'Proyecto 2', objetivo: 'Obj 2' }
  ];

  const mockPaginatedResponse = {
    content: mockProyectos,
    totalElements: 2
  };

  beforeEach(() => {
    mockProjectService = jasmine.createSpyObj('ProjectService', ['consultarProyectos']);
    mockProjectService.consultarProyectos.and.returnValue(of(mockPaginatedResponse));

    TestBed.configureTestingModule({
      imports: [FormsModule, NgxPaginationModule],
      declarations: [ProjectsComponent],
      providers: [
        { provide: ProjectService, useValue: mockProjectService }
      ],
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(ProjectsComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    fixture.detectChanges();
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    it('should load projects', () => {
      fixture.detectChanges();
      expect(mockProjectService.consultarProyectos).toHaveBeenCalled();
      expect(component.proyectos).toEqual(mockProyectos);
    });
  });

  describe('cargarProyectos', () => {
    it('should populate projects from paginated response on success', () => {
      component.cargarProyectos();
      expect(component.proyectos).toEqual(mockProyectos);
      expect(component.proyectosFiltrados).toEqual(mockProyectos);
      expect(component.totalElementos).toBe(2);
      expect(component.cargando).toBeFalse();
    });

    it('should handle error', () => {
      mockProjectService.consultarProyectos.and.returnValue(throwError(() => new Error('fail')));
      component.cargarProyectos();
      expect(component.error).toBe('No se pudieron cargar los proyectos.');
      expect(component.cargando).toBeFalse();
    });
  });

  describe('onPageChange', () => {
    it('should update page and reload projects', () => {
      mockProjectService.consultarProyectos.calls.reset();
      component.onPageChange(3);
      expect(component.p).toBe(3);
      expect(mockProjectService.consultarProyectos).toHaveBeenCalledWith(2);
    });
  });

  describe('filterProjects', () => {
    it('should reload projects', () => {
      component.filterProjects();
      expect(component.proyectosFiltrados).toBeDefined();
    });
  });

  describe('abrirModalEdicion', () => {
    it('should set proyectoSeleccionado', () => {
      component.abrirModalEdicion(mockProyectos[0]);
      expect(component.proyectoSeleccionado).toEqual(mockProyectos[0]);
    });
  });

  describe('onProyectoModificado', () => {
    it('should reload projects and clear selection', () => {
      mockProjectService.consultarProyectos.calls.reset();
      component.proyectoSeleccionado = mockProyectos[0];
      component.onProyectoModificado(mockProyectos[0]);
      expect(mockProjectService.consultarProyectos).toHaveBeenCalled();
      expect(component.proyectoSeleccionado).toBeNull();
    });
  });

  describe('onProyectoCancelado', () => {
    it('should clear selection', () => {
      component.proyectoSeleccionado = mockProyectos[0];
      component.onProyectoCancelado();
      expect(component.proyectoSeleccionado).toBeNull();
    });
  });

  describe('abrirModalEdicion with DOM element', () => {
    it('should show modal when element exists', () => {
      const mockModal = { show: jasmine.createSpy('show') };
      spyOn(bootstrap.Modal, 'getInstance').and.returnValue(mockModal as any);
      spyOn(document, 'getElementById').and.returnValue(document.createElement('div'));
      component.abrirModalEdicion(mockProyectos[0]);
      expect(mockModal.show).toHaveBeenCalled();
    });
  });

  describe('cerrarModal with DOM element', () => {
    it('should hide modal when element and instance exist', () => {
      const mockModal = { hide: jasmine.createSpy('hide') };
      spyOn(bootstrap.Modal, 'getInstance').and.returnValue(mockModal as any);
      spyOn(document, 'getElementById').and.returnValue(document.createElement('div'));
      component.onProyectoCancelado();
      expect(mockModal.hide).toHaveBeenCalled();
    });
  });
});
