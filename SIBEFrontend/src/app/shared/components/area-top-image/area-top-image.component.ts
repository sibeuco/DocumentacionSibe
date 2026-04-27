import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-area-top-image',
  templateUrl: './area-top-image.component.html',
  styleUrls: ['./area-top-image.component.scss']
})
export class AreaTopImageComponent {
  @Input() imageSrc: string = '';
  @Input() text: string = '';
}
