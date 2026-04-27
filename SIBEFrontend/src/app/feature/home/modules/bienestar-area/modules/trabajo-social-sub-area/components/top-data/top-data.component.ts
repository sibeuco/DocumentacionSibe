import { Component, Input } from '@angular/core';
import { FiltersRequestWithoutArea } from 'src/app/shared/model/filters.model';

@Component({
  selector: 'app-top-data',
  templateUrl: './top-data.component.html',
  styleUrls: ['./top-data.component.scss']
})
export class TopDataComponent {
  @Input() filtersRequest: FiltersRequestWithoutArea | null = null;
  
  tipoEstructura: 'DIRECCION' | 'AREA' | 'SUBAREA' = 'SUBAREA';
  nombreArea: string = 'Trabajo social';
  imageUrl: string = 'assets/images/Appreciation-amico.png';
  imageUrl2: string = 'assets/images/actividades-hogar.png';
}
