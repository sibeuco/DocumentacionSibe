import { Component, Input } from '@angular/core';
import { FiltersRequestWithoutArea } from 'src/app/shared/model/filters.model';

@Component({
  selector: 'app-bottom-data',
  templateUrl: './bottom-data.component.html',
  styleUrls: ['./bottom-data.component.scss']
})
export class BottomDataComponent {
  @Input() filtersRequest: FiltersRequestWithoutArea | null = null;
  
  tipoEstructura: 'DIRECCION' | 'AREA' | 'SUBAREA' = 'SUBAREA';
  nombreArea: string = 'Unidad de Salud';
  participantesColor: string = 'rgba(29, 52, 117, 1)';
  asistenciasColor: string = 'rgba(255, 202, 0, 0.3)';
  title: string = 'Histórico de participación del área';
}
