import { Component } from '@angular/core';

@Component({
  selector: 'app-evangelizacion-area',
  templateUrl: './evangelizacion-area.component.html',
  styleUrls: ['./evangelizacion-area.component.scss']
})
export class EvangelizacionAreaComponent {
  tipoEstructura: 'DIRECCION' | 'AREA' | 'SUBAREA' = 'AREA';
  nombreArea: string = 'Evangelización';
}
