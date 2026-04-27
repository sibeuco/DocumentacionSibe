import { Component } from '@angular/core';

@Component({
  selector: 'app-bienestar-area',
  templateUrl: './bienestar-area.component.html',
  styleUrls: ['./bienestar-area.component.scss']
})
export class BienestarAreaComponent {
  tipoEstructura: 'DIRECCION' | 'AREA' | 'SUBAREA' = 'AREA';
  nombreArea: string = 'Bienestar';
}
