import { Component, EventEmitter, Output } from '@angular/core';
import { Modal } from 'bootstrap';
import { ActionService } from '../../service/action.service';
import { ActionRequest } from '../../model/action.model';

@Component({
  selector: 'app-register-new-action',
  templateUrl: './register-new-action.component.html',
  styleUrls: ['./register-new-action.component.scss']
})
export class RegisterNewActionComponent {

  @Output() accionCreada = new EventEmitter<any>();
    
      accion: ActionRequest = {
        detalle: '',
        objetivo: ''
      };
      
      cargando: boolean = false;
      error: string = '';
      exito: string = '';

      constructor(private actionService: ActionService) {}
    
      registrarAccion() {
        if (this.cargando) return;

        this.cargando = true;
        this.error = '';
        
        console.log('Enviando acción:', this.accion);

        this.actionService.agregarNuevaAccion(this.accion).subscribe({
          next: (response) => {
            console.log('Acción creada exitosamente:', response);
            this.exito = 'Acción creada exitosamente';
            this.accionCreada.emit(this.accion);
            
            // Esperar un momento antes de cerrar para que se vea el mensaje
            setTimeout(() => {
              this.limpiarFormulario();
              this.cerrarModal();
            }, 1500);
            
            this.cargando = false;
          },
          error: (error) => {
            console.error('Error al crear la acción:', error);
            console.error('Error completo:', JSON.stringify(error));
            
            // Extraer el mensaje de error de diferentes formatos posibles
            let mensajeError = 'Error al registrar la acción. Por favor, intente nuevamente.';
            
            if (error?.error) {
              // Si error.error es un string
              if (typeof error.error === 'string') {
                mensajeError = error.error;
              }
              // Si error.error tiene una propiedad mensaje
              else if (error.error.mensaje) {
                mensajeError = error.error.mensaje;
              }
              // Si error.error tiene una propiedad message
              else if (error.error.message) {
                mensajeError = error.error.message;
              }
              // Si error.error tiene una propiedad error
              else if (error.error.error) {
                mensajeError = typeof error.error.error === 'string' 
                  ? error.error.error 
                  : mensajeError;
              }
              // Si error.error es un objeto con algún texto
              else if (error.error.valor) {
                mensajeError = error.error.valor;
              }
            }
            // Si el error tiene una propiedad message directamente
            else if (error?.message) {
              mensajeError = error.message;
            }
            
            this.error = mensajeError;
            this.cargando = false;
          }
        });
      }
      
      cerrarModal() {
        const modalElement = document.getElementById('register-action-modal');
        if (modalElement) {
          const modal = Modal.getInstance(modalElement) || new Modal(modalElement);
          modal.hide();
        }
      }
    
      limpiarFormulario() {
        this.accion = {
          detalle: '',
          objetivo: ''
        };
        this.error = '';
        this.exito = '';
      }

}
