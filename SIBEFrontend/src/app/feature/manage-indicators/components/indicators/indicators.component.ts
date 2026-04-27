import { Component, OnInit } from '@angular/core';
import { Modal } from 'bootstrap';
import { IndicatorService } from '../../service/indicator.service';
import { IndicatorResponse } from '../../model/indicator.model';

@Component({
  selector: 'app-indicators',
  templateUrl: './indicators.component.html',
  styleUrls: ['./indicators.component.scss']
})
export class IndicatorsComponent implements OnInit{

  searchTerm: string = '';
  indicadores: IndicatorResponse[] = [];
  indicadoresFiltrados: IndicatorResponse[] = [];

  // Propiedades para paginación
  p: number = 1;
  totalElementos: number = 0;

  // Propiedades para el modal de edición
  indicadorSeleccionado: IndicatorResponse | null = null;

  constructor(private indicatorService: IndicatorService) {}

  ngOnInit(): void {
    this.loadIndicators();
  }

  loadIndicators(): void {
    this.indicatorService.consultarIndicadores(this.p - 1).subscribe({
      next: (response) => {
        this.indicadores = response.content;
        this.indicadoresFiltrados = response.content;
        this.totalElementos = response.totalElements;
      },
      error: (error) => {
        console.error('Error al cargar los indicadores:', error);
      }
    });
  }

  onPageChange(event: number): void {
    this.p = event;
    this.loadIndicators();
  }

  filterIndicators(): void {
    const term = this.searchTerm.toLowerCase();
    this.indicadoresFiltrados = this.indicadores.filter(indicator =>
      indicator.nombre.toLowerCase().includes(term) ||
      indicator.tipoIndicador.tipologiaIndicador.toLowerCase().includes(term) ||
      indicator.proyecto.nombre.toLowerCase().includes(term)
    );
  }

  // Métodos para el modal de edición
  abrirModalEdicion(indicator: IndicatorResponse): void {
    this.indicadorSeleccionado = indicator;

    // Abrir el modal usando Bootstrap
    const modalElement = document.getElementById('edit-indicator-modal');
    if (modalElement) {
      const modal = Modal.getInstance(modalElement) || new Modal(modalElement);
      modal.show();
    }
  }

  onIndicadorModificado(indicadorModificado: IndicatorResponse): void {
    // Recargar todos los indicadores desde el backend
    this.loadIndicators();

    // Cerrar el modal
    this.cerrarModal();
  }

  onIndicadorCancelado(): void {
    this.cerrarModal();
  }

  private cerrarModal(): void {
    const modalElement = document.getElementById('edit-indicator-modal');
    if (modalElement) {
      const modal = Modal.getInstance(modalElement);
      if (modal) {
        modal.hide();
      }
    }
    this.indicadorSeleccionado = null;
  }

}
