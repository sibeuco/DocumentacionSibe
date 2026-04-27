import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-go-to-area-button',
  templateUrl: './go-to-area-button.component.html',
  styleUrls: ['./go-to-area-button.component.scss']
})
export class GoToAreaButtonComponent {
  @Input() text: string = 'Botón';
  @Input() routerLink: string | any[] = '/';
}
