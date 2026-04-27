import { Component, OnInit } from '@angular/core';
import { Modal } from 'bootstrap';
import { ProjectResponse } from '../../model/project.model';
import { ProjectService } from '../../service/project.service';

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.scss']
})
export class ProjectsComponent implements OnInit{

  searchTerm: string = '';
  proyectos: ProjectResponse[] = [];
  proyectosFiltrados: ProjectResponse[] = [];
  cargando: boolean = false;
  error: string = '';

  // Propiedades para paginación
  p: number = 1;
  totalElementos: number = 0;

  // Propiedades para el modal de edición
  proyectoSeleccionado: ProjectResponse | null = null;

  constructor(private projectService: ProjectService) {}

  ngOnInit(): void {
    this.cargarProyectos();
  }

  cargarProyectos(): void {
    this.cargando = true;
    this.projectService.consultarProyectos(this.p - 1).subscribe({
      next: (response) => {
        this.proyectos = response.content;
        this.proyectosFiltrados = [...response.content];
        this.totalElementos = response.totalElements;
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al obtener proyectos:', err);
        this.error = 'No se pudieron cargar los proyectos.';
        this.cargando = false;
      }
    });
  }

  onPageChange(event: number): void {
    this.p = event;
    this.cargarProyectos();
  }

  filterProjects(): void {
    const term = this.searchTerm.toLowerCase();
    this.proyectosFiltrados = this.proyectos.filter(project =>
      Object.values(project).some(value =>
        String(value).toLowerCase().includes(term)
      )
    );
  }

  // Métodos para el modal de edición
  abrirModalEdicion(proyecto: ProjectResponse): void {
    this.proyectoSeleccionado = proyecto;

    // Abrir el modal usando Bootstrap
    const modalElement = document.getElementById('edit-project-modal');
    if (modalElement) {
      const modal = Modal.getInstance(modalElement) || new Modal(modalElement);
      modal.show();
    }
  }

  onProyectoModificado(proyectoModificado: ProjectResponse): void {
    // Recargar todos los proyectos desde el backend
    this.cargarProyectos();

    // Cerrar el modal
    this.cerrarModal();
  }

  onProyectoCancelado(): void {
    this.cerrarModal();
  }

  private cerrarModal(): void {
    const modalElement = document.getElementById('edit-project-modal');
    if (modalElement) {
      const modal = Modal.getInstance(modalElement);
      if (modal) {
        modal.hide();
      }
    }
    this.proyectoSeleccionado = null;
  }
}
