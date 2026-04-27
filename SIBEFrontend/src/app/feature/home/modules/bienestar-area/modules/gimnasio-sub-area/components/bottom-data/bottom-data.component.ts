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
  nombreArea: string = 'Gimnasio';
  participantesColor: string = 'rgba(226, 130, 16, 1)';
  asistenciasColor: string = 'rgba(255, 202, 0, 0.3)';
  title: string = 'Histórico de participación del área';
}
