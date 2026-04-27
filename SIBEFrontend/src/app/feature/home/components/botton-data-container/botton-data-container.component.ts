import { Component, Input } from '@angular/core';
import { FiltersRequestWithoutArea } from 'src/app/shared/model/filters.model';

@Component({
  selector: 'app-botton-data-container',
  templateUrl: './botton-data-container.component.html',
  styleUrls: ['./botton-data-container.component.scss']
})
export class BottonDataContainerComponent {
  @Input() filtersRequest: FiltersRequestWithoutArea | null = null;
  
  tipoEstructura: 'DIRECCION' | 'AREA' | 'SUBAREA' = 'DIRECCION';
  nombreArea: string = 'Dirección de Bienestar y Evangelización';
  participantesColor: string = 'rgba(0, 139, 80, 0.5)';
  asistenciasColor: string = 'rgba(255, 202, 0, 0.3)';
  title: string = 'Histórico de participación de la Dirección';
}
