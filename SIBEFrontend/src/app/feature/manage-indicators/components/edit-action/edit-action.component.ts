import { Component, EventEmitter, Input, OnInit, OnChanges, SimpleChanges, Output } from '@angular/core';
import { ActionService } from '../../service/action.service';
import { ActionRequest, ActionResponse } from '../../model/action.model';

@Component({
  selector: 'app-edit-action',
  templateUrl: './edit-action.component.html',
  styleUrls: ['./edit-action.component.scss']
})
export class EditActionComponent implements OnInit, OnChanges {

  @Input() accionAEditar: ActionResponse | null = null;
  @Output() accionModificada = new EventEmitter<ActionResponse>();
  @Output() accionCancelada = new EventEmitter<void>();
    
      accion = {
        detalle: '',
        objetivo: ''
      };

      cargando = false;
      error = '';
      exito = '';

      constructor(private actionService: ActionService) {}

      ngOnInit(): void {
        if (this.accionAEditar) {
          this.cargarDatosAccion();
        }
      }

      ngOnChanges(changes: SimpleChanges): void {
        if (changes['accionAEditar'] && changes['accionAEditar'].currentValue) {
          this.cargarDatosAccion();
        }
      }

      cargarDatosAccion(): void {
        if (this.accionAEditar) {
          this.accion = {
            detalle: this.accionAEditar.detalle,
            objetivo: this.accionAEditar.objetivo
          };
          // Limpiar mensajes previos cuando se cargan nuevos datos
          this.error = '';
          this.exito = '';
          this.cargando = false;
        }
      }
    
      modificarAccion() {
        if (this.cargando || !this.accionAEditar) return;

        this.cargando = true;
        this.error = '';
        this.exito = '';

        const accionRequest: ActionRequest = {
          detalle: this.accion.detalle,
          objetivo: this.accion.objetivo
        };

        this.actionService.modificarAccion(this.accionAEditar.identificador, accionRequest).subscribe({
          next: (response) => {
            this.exito = 'Acción modificada exitosamente';
            
            // Crear el objeto de respuesta actualizado
            const accionModificada: ActionResponse = {
              identificador: this.accionAEditar!.identificador,
              detalle: this.accion.detalle,
              objetivo: this.accion.objetivo
            };

            // Esperar un momento antes de cerrar para que se vea el mensaje
            setTimeout(() => {
              this.accionModificada.emit(accionModificada);
            }, 1500);

            this.cargando = false;
          },
          error: (error) => {
            console.error('Error al modificar la acción:', error);
            
            // Extraer el mensaje de error
            let mensajeError = 'Error al modificar la acción. Por favor, intente nuevamente.';
            
            if (error?.error) {
              if (typeof error.error === 'string') {
                mensajeError = error.error;
              } else if (error.error.mensaje) {
                mensajeError = error.error.mensaje;
              } else if (error.error.message) {
                mensajeError = error.error.message;
              }
            } else if (error?.message) {
              mensajeError = error.message;
            }

            this.error = mensajeError;
            this.cargando = false;
          }
        });
      }

      cancelar() {
        this.accionCancelada.emit();
      }
    
      limpiarFormulario() {
        this.accion = {
          detalle: '',
          objetivo: ''
        };
        this.error = '';
        this.exito = '';
        this.cargando = false;
      }

}
