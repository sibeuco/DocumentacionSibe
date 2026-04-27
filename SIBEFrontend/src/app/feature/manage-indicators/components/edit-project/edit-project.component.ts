import { Component, EventEmitter, Input, OnInit, OnChanges, SimpleChanges, Output } from '@angular/core';
import { ProjectService } from '../../service/project.service';
import { ActionService } from '../../service/action.service';
import { ProjectResponse, EditProjectRequest } from '../../model/project.model';
import { ActionResponse } from '../../model/action.model';

@Component({
  selector: 'app-edit-project',
  templateUrl: './edit-project.component.html',
  styleUrls: ['./edit-project.component.scss']
})
export class EditProjectComponent implements OnInit, OnChanges {

  @Input() proyectoAEditar: ProjectResponse | null = null;
  @Output() proyectoModificado = new EventEmitter<ProjectResponse>();
  @Output() proyectoCancelado = new EventEmitter<void>();

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
        private projectService: ProjectService,
        private actionService: ActionService
      ) {
        // Asegurar que el array de acciones esté inicializado
        this.proyecto.acciones = [];
      }

      ngOnInit(): void {
        this.cargarAcciones();
        if (this.proyectoAEditar) {
          this.cargarDatosProyecto();
        }
      }

      ngOnChanges(changes: SimpleChanges): void {
        if (changes['proyectoAEditar'] && changes['proyectoAEditar'].currentValue) {
          this.cargarAcciones();
          this.cargarDatosProyecto();
        }
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

      cargarDatosProyecto(): void {
        if (this.proyectoAEditar) {
          this.proyecto = {
            numeroProyecto: this.proyectoAEditar.numeroProyecto,
            nombre: this.proyectoAEditar.nombre,
            objetivo: this.proyectoAEditar.objetivo,
            acciones: this.proyectoAEditar.acciones?.map(accion => accion.identificador) || []
          };
          // Limpiar mensajes previos cuando se cargan nuevos datos
          this.error = '';
          this.exito = '';
          this.cargando = false;
        }
      }

      modificarProyecto() {
        if (this.cargando || !this.proyectoAEditar) return;

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
        const proyectoRequest: EditProjectRequest = {
          numeroProyecto: this.proyecto.numeroProyecto,
          nombre: this.proyecto.nombre,
          objetivo: this.proyecto.objetivo,
          acciones: this.proyecto.acciones
        };

        this.projectService.modificarProyecto(this.proyectoAEditar.identificador, proyectoRequest).subscribe({
          next: (response) => {
            this.exito = 'Proyecto modificado exitosamente';

            // Crear el objeto de respuesta actualizado
            const proyectoModificado: ProjectResponse = {
              identificador: this.proyectoAEditar!.identificador,
              numeroProyecto: this.proyecto.numeroProyecto,
              nombre: this.proyecto.nombre,
              objetivo: this.proyecto.objetivo
            };

            // Esperar un momento antes de cerrar para que se vea el mensaje
            setTimeout(() => {
              this.proyectoModificado.emit(proyectoModificado);
            }, 1500);

            this.cargando = false;
          },
          error: (error) => {
            console.error('Error al modificar el proyecto:', error);

            // Extraer el mensaje de error
            let mensajeError = 'Error al modificar el proyecto. Por favor, intente nuevamente.';

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
        this.proyectoCancelado.emit();
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
        this.cargando = false;
      }

      filterAcciones() {
        const term = this.searchAcciones.toLowerCase();
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
        if (!this.proyecto.acciones) return [];
        return this.proyecto.acciones
          .map(value => this.accionesDisponibles.find(accion => accion.value === value)?.label)
          .filter(label => label) as string[];
      }

      getActionValue(label: string): string {
        const accion = this.accionesDisponibles.find(accion => accion.label === label);
        return accion?.value || '';
      }


}
