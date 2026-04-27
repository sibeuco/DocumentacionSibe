import { Component, EventEmitter, OnInit, AfterViewInit, OnDestroy, Output } from '@angular/core';
import { Modal } from 'bootstrap';
import { ActionService } from '../../service/action.service';
import { ActionResponse } from '../../model/action.model';
import { ProjectService } from '../../service/project.service';
import { ProjectRequest } from '../../model/project.model';

@Component({
  selector: 'app-register-new-project',
  templateUrl: './register-new-project.component.html',
  styleUrls: ['./register-new-project.component.scss']
})
export class RegisterNewProjectComponent implements OnInit, AfterViewInit, OnDestroy {

  @Output() proyectoCreado = new EventEmitter<any>();
  private modalShowListener: (() => void) | null = null;

      proyecto = {
        numeroProyecto: '',
        nombre: '',
        objetivo: '',
        acciones: [] as string[]
      };

      // Lista de acciones disponibles desde el servicio
      accionesDisponibles: { value: string; label: string }[] = [];

      accionesFiltradas: { value: string; label: string }[] = [];
      searchAcciones = '';
      showDropdown = false;
      cargandoAcciones = false;
      cargando = false;
      error = '';
      exito = '';

      constructor(
        private actionService: ActionService,
        private projectService: ProjectService
      ) {
        // Asegurar que el array de acciones esté inicializado
        this.proyecto.acciones = [];
      }

      ngOnInit(): void {
        this.cargarAcciones();
        this.suscripcionEventoAccion();
      }

      ngAfterViewInit(): void {
        const modalElement = document.getElementById('register-project-modal');
        if (modalElement) {
          this.modalShowListener = () => this.cargarAcciones();
          modalElement.addEventListener('show.bs.modal', this.modalShowListener);
        }
      }

      ngOnDestroy(): void {
        const modalElement = document.getElementById('register-project-modal');
        if (modalElement && this.modalShowListener) {
          modalElement.removeEventListener('show.bs.modal', this.modalShowListener);
        }
      }

      suscripcionEventoAccion(): void {
        // Escuchar cuando se crea una nueva acción desde cualquier lugar
        // Este método se puede llamar manualmente para actualizar la lista
      }

      // Método público para recargar acciones
      recargarAcciones(): void {
        this.cargarAcciones();
      }

      cargarAcciones(): void {
        this.cargandoAcciones = true;
        this.actionService.consultarAcciones(0, 1000).subscribe({
          next: (response: any) => {
            const acciones: ActionResponse[] = response.content;
            // Mapear las acciones del servicio al formato esperado
            this.accionesDisponibles = acciones.map(accion => ({
              value: accion.identificador,
              label: accion.detalle
            }));
            this.accionesFiltradas = [...this.accionesDisponibles];
            this.cargandoAcciones = false;
          },
          error: (error) => {
            console.error('Error al cargar las acciones:', error);
            this.cargandoAcciones = false;
          }
        });
      }

      registrarProyecto() {
        if (this.cargando) return;

        this.cargando = true;
        this.error = '';
        this.exito = '';

        // Validar que hay acciones seleccionadas
        if (!this.proyecto.acciones || this.proyecto.acciones.length === 0) {
          this.error = 'Debe seleccionar al menos una acción para el proyecto';
          this.cargando = false;
          return;
        }

        // Preparar el objeto del proyecto para enviar
        const proyectoRequest: ProjectRequest = {
          numeroProyecto: this.proyecto.numeroProyecto,
          nombre: this.proyecto.nombre,
          objetivo: this.proyecto.objetivo,
          acciones: this.proyecto.acciones // Lista de identificadores de acciones
        };

        this.projectService.agregarNuevoProyecto(proyectoRequest).subscribe({
          next: (response) => {
            this.exito = 'Proyecto creado exitosamente';
            this.proyectoCreado.emit(this.proyecto);

            // Esperar un momento antes de cerrar para que se vea el mensaje
            setTimeout(() => {
              this.limpiarFormulario();
              this.cerrarModal();
            }, 1500);

            this.cargando = false;
          },
          error: (error) => {
            console.error('Error completo:', JSON.stringify(error));

            // Extraer el mensaje de error de diferentes formatos posibles
            let mensajeError = 'Error al registrar el proyecto. Por favor, intente nuevamente.';

            if (error?.error) {
              if (typeof error.error === 'string') {
                mensajeError = error.error;
              } else if (error.error.mensaje) {
                mensajeError = error.error.mensaje;
              } else if (error.error.message) {
                mensajeError = error.error.message;
              } else if (error.error.error) {
                mensajeError = typeof error.error.error === 'string'
                  ? error.error.error
                  : mensajeError;
              } else if (error.error.valor) {
                mensajeError = error.error.valor;
              }
            } else if (error?.message) {
              mensajeError = error.message;
            }

            this.error = mensajeError;
            this.cargando = false;
          }
        });
      }

      cerrarModal() {
        const modalElement = document.getElementById('register-project-modal');
        if (modalElement) {
          const modal = Modal.getInstance(modalElement) || new Modal(modalElement);
          modal.hide();
        }
      }

      limpiarFormulario() {
        this.proyecto = {
          numeroProyecto: '',
          nombre: '',
          objetivo: '',
          acciones: [] as string[]
        };
        this.searchAcciones = '';
        this.accionesFiltradas = [...this.accionesDisponibles];
        this.showDropdown = false;
        this.error = '';
        this.exito = '';
      }

      filterAcciones() {
        const term = this.searchAcciones.toLowerCase();
        if (!this.accionesDisponibles || this.accionesDisponibles.length === 0) {
          this.accionesFiltradas = [];
          return;
        }

        if (term.trim() === '') {
          this.accionesFiltradas = [...this.accionesDisponibles];
        } else {
          this.accionesFiltradas = this.accionesDisponibles.filter(accion =>
            accion.label.toLowerCase().includes(term)
          );
        }
      }

      isActionSelected(value: string): boolean {
        return this.proyecto.acciones?.includes(value) || false;
      }

      toggleAction(value: string) {
        if (!this.proyecto.acciones) {
          this.proyecto.acciones = [];
        }

        const index = this.proyecto.acciones.indexOf(value);
        if (index > -1) {
          this.proyecto.acciones.splice(index, 1);
        } else {
          this.proyecto.acciones.push(value);
        }
      }

      removeAction(value: string) {
        if (this.proyecto.acciones) {
          const index = this.proyecto.acciones.indexOf(value);
          if (index > -1) {
            this.proyecto.acciones.splice(index, 1);
          }
        }
      }

      getSelectedActionsLabels(): string[] {
        if (!this.proyecto.acciones) {
          return [];
        }
        return this.proyecto.acciones
          .map(value => this.accionesDisponibles.find(accion => accion.value === value)?.label)
          .filter(label => label) as string[];
      }

      getActionValue(label: string): string {
        const accion = this.accionesDisponibles.find(accion => accion.label === label);
        return accion?.value || '';
      }

}
