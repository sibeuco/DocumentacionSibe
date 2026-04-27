import { Component, OnInit } from '@angular/core';
import { Modal } from 'bootstrap';
import { ActionResponse } from '../../model/action.model';
import { ActionService } from '../../service/action.service';

@Component({
  selector: 'app-actions',
  templateUrl: './actions.component.html',
  styleUrls: ['./actions.component.scss']
})
export class ActionsComponent implements OnInit{

  searchTerm: string = '';
  acciones: ActionResponse[] = [];
  accionesFiltradas: ActionResponse[] = [];
  cargando: boolean = false;
  error: string = '';

  // Propiedades para paginación
  p: number = 1;
  totalElementos: number = 0;

  // Propiedades para el modal de edición
  accionSeleccionada: ActionResponse | null = null;

  constructor(private actionService: ActionService) {}

  ngOnInit(): void {
    this.obtenerAcciones();
  }

  obtenerAcciones(): void {
    this.cargando = true;
    this.error = '';

    this.actionService.consultarAcciones(this.p - 1).subscribe({
      next: (response) => {
        this.acciones = response.content;
        this.accionesFiltradas = [...response.content];
        this.totalElementos = response.totalElements;
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al obtener acciones:', err);
        this.error = 'No se pudieron cargar las acciones.';
        this.cargando = false;
      }
    });
  }

  onPageChange(event: number): void {
    this.p = event;
    this.obtenerAcciones();
  }

  filterActions(): void {
    const term = this.searchTerm.toLowerCase();
    this.accionesFiltradas = this.acciones.filter(action =>
      Object.values(action).some(value =>
        value && value.toString().toLowerCase().includes(term)
      )
    );
  }

  // Métodos para el modal de edición
  abrirModalEdicion(accion: ActionResponse): void {
    this.accionSeleccionada = accion;

    // Abrir el modal usando Bootstrap
    const modalElement = document.getElementById('edit-action-modal');
    if (modalElement) {
      const modal = Modal.getInstance(modalElement) || new Modal(modalElement);
      modal.show();
    }
  }

  onAccionModificada(accionModificada: ActionResponse): void {
    // Recargar todas las acciones desde el backend
    this.obtenerAcciones();

    // Cerrar el modal
    this.cerrarModal();
  }

  onAccionCancelada(): void {
    this.cerrarModal();
  }

  private cerrarModal(): void {
    const modalElement = document.getElementById('edit-action-modal');
    if (modalElement) {
      const modal = Modal.getInstance(modalElement);
      if (modal) {
        modal.hide();
      }
    }
    this.accionSeleccionada = null;
  }

}
