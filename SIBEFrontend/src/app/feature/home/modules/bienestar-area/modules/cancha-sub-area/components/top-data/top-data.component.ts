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
  nombreArea: string = 'Cancha sintética';
  imageUrl: string = 'assets/images/High five-amico.png';
  imageUrl2: string = 'assets/images/Soccer-amico.png';
}
