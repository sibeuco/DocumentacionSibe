import { Component } from '@angular/core';

@Component({
  selector: 'app-servicio-area',
  templateUrl: './servicio-area.component.html',
  styleUrls: ['./servicio-area.component.scss']
})
export class ServicioAreaComponent {
  tipoEstructura: 'DIRECCION' | 'AREA' | 'SUBAREA' = 'AREA';
  nombreArea: string = 'Servicio y atención al usuario';
}
