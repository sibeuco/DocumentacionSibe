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
  nombreArea: string = 'Hogar Juvenil Santa María';
  participantesColor: string = 'rgba(4, 181, 172, 1)';
  asistenciasColor: string = 'rgba(255, 202, 0, 0.3)';
  title: string = 'Histórico de participación del área';
}
