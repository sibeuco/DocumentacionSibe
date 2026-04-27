import { Component } from '@angular/core';

@Component({
  selector: 'app-santa-maria-area',
  templateUrl: './santa-maria-area.component.html',
  styleUrls: ['./santa-maria-area.component.scss']
})
export class SantaMariaAreaComponent {
  tipoEstructura: 'DIRECCION' | 'AREA' | 'SUBAREA' = 'AREA';
  nombreArea: string = 'Hogar Juvenil Santa María';
}
