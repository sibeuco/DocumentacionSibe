import { Component, EventEmitter, Output } from '@angular/core';
import { Modal } from 'bootstrap';

@Component({
  selector: 'app-external-participant',
  templateUrl: './external-participant.component.html',
  styleUrls: ['./external-participant.component.scss']
})
export class ExternalParticipantComponent {
  @Output() participanteAgregado = new EventEmitter<any>();

  participante = {
    documento: '',
    nombreCompleto: ''
  };

  precargarDocumento(doc: string): void {
    this.participante.documento = doc;
  }

  agregarParticipante() {
    // Emitir el evento con los datos del participante para que el componente padre maneje la lógica
    this.participanteAgregado.emit({
      documento: this.participante.documento,
      nombreCompleto: this.participante.nombreCompleto
    });

    this.limpiarFormulario();
    this.cerrarModal();
  }

  limpiarFormulario() {
    this.participante = {
      documento: '',
      nombreCompleto: ''
    };
  }

  cerrarModal() {
    const modalElement = document.getElementById('external-participant-modal');
    if (modalElement) {
      const modal = Modal.getInstance(modalElement) || new Modal(modalElement);
      modal.hide();
    }
  }
}
