import { Component, Input } from '@angular/core';
import { FiltersRequestWithoutArea } from 'src/app/shared/model/filters.model';

@Component({
  selector: 'app-bottom-data-container',
  templateUrl: './bottom-data-container.component.html',
  styleUrls: ['./bottom-data-container.component.scss']
})
export class BottomDataContainerComponent {
  @Input() filtersRequest: FiltersRequestWithoutArea | null = null;
  
  tipoEstructura: 'DIRECCION' | 'AREA' | 'SUBAREA' = 'AREA';
  nombreArea: string = 'Evangelización';
  participantesColor: string = 'rgba(59, 82, 147, 1)';
  asistenciasColor: string = 'rgba(255, 202, 0, 0.3)';
  title: string = 'Histórico de participación del área';
}
