import { Component, Input } from '@angular/core';
import { FiltersRequestWithoutArea } from 'src/app/shared/model/filters.model';

@Component({
  selector: 'app-botton-data-container',
  templateUrl: './botton-data-container.component.html',
  styleUrls: ['./botton-data-container.component.scss']
})
export class BottonDataContainerComponent {
  @Input() filtersRequest: FiltersRequestWithoutArea | null = null;
  
  tipoEstructura: 'DIRECCION' | 'AREA' | 'SUBAREA' = 'AREA';
  nombreArea: string = 'Bienestar';
  participantesColor: string = 'rgba(255, 206, 86, 1)';
  asistenciasColor: string = 'rgba(255, 202, 0, 0.3)';
  title: string = 'Histórico de participación del área';
}
