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
  nombreArea: string = 'Unidad de Salud';
  imageUrl: string = 'assets/images/Phisiotherapy-amico.png';
  imageUrl2: string = 'assets/images/Doctors-amico.png';
}
