import { Component, Input } from '@angular/core';
import { Modal } from 'bootstrap';

@Component({
  selector: 'app-primary-button',
  templateUrl: './primary-button.component.html',
  styleUrls: ['./primary-button.component.scss']
})
export class PrimaryButtonComponent {
  @Input() icon: string = '';
  @Input() text: string = '';
  @Input() modalTargetId?: string;
  @Input() disabled: boolean = false;
  @Input() disabledTooltip: string = '';

  openModal(id: string) {
    const modalElement = document.getElementById(id);
    if (modalElement) {
      const modal = Modal.getInstance(modalElement) || new Modal(modalElement);
      modal.show();
    }
  }

}
