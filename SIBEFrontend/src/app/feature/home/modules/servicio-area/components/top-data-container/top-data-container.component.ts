import { Component, Input } from '@angular/core';
import { FiltersRequestWithoutArea } from 'src/app/shared/model/filters.model';

@Component({
  selector: 'app-top-data-container',
  templateUrl: './top-data-container.component.html',
  styleUrls: ['./top-data-container.component.scss']
})
export class TopDataContainerComponent {
  @Input() filtersRequest: FiltersRequestWithoutArea | null = null;
  
  tipoEstructura: 'DIRECCION' | 'AREA' | 'SUBAREA' = 'AREA';
  nombreArea: string = 'Servicio y atención al usuario';
  imageUrl: string = 'assets/images/estudiantes-servicio.png';
  imageUrl2: string = 'assets/images/actividades-servicio.png';
}
