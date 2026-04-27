import { Component, EventEmitter, OnInit, AfterViewInit, OnDestroy, Output } from '@angular/core';
import { Modal } from 'bootstrap';
import { ProjectService } from '../../service/project.service';
import { ProjectResponse } from '../../model/project.model';
import { FrequencyService } from '../../service/frequency.service';
import { FrequencyResponse } from '../../model/frequency.model';
import { IndicatorTypeResponse } from '../../model/indicator-type.model';
import { IndicatorTypeService } from '../../service/indicator-type.service';
import { InterestedPublicResponse } from '../../model/interested-public.model';
import { InterestedPublicService } from '../../service/interested-public.service';
import { IndicatorService } from '../../service/indicator.service';
import { IndicatorRequest } from '../../model/indicator.model';

@Component({
  selector: 'app-register-new-indicator',
  templateUrl: './register-new-indicator.component.html',
  styleUrls: ['./register-new-indicator.component.scss']
})
export class RegisterNewIndicatorComponent implements OnInit, AfterViewInit, OnDestroy {
    @Output() indicadorCreado = new EventEmitter<any>();
    private modalShowListener: (() => void) | null = null;

      indicador = {
        nombre: '',
        tipoIndicador: '',
        temporalidad: '',
        proyecto: '',
        publicosInteres: [] as string[]
      };

      proyectos: ProjectResponse[] = [];
      cargandoProyectos: boolean = false;

      temporalidades: FrequencyResponse[] = [];
      cargandoTemporalidades: boolean = false;

      tiposIndicador: IndicatorTypeResponse[] = [];
      cargandoTiposIndicador: boolean = false;

      // Lista de público de interés disponible desde el servicio
      publicoInteresDisponibles: { value: string; label: string }[] = [];
      publicoInteresFiltrados: { value: string; label: string }[] = [];
      searchPublicoInteres = '';
      showDropdown = false;
      cargandoPublicoInteres: boolean = false;
      cargando = false;
      error = '';
      exito = '';

      constructor(
        private projectService: ProjectService,
        private frequencyService: FrequencyService,
        private indicatorTypeService: IndicatorTypeService,
        private interestedPublicService: InterestedPublicService,
        private indicatorService: IndicatorService
      ) {
        // Asegurar que el array de público de interés esté inicializado
        this.indicador.publicosInteres = [];
      }

      ngOnInit(): void {
        this.cargarProyectos();
        this.cargarTemporalidades();
        this.cargarTiposIndicador();
        this.cargarPublicoInteres();
      }

      ngAfterViewInit(): void {
        const modalElement = document.getElementById('register-indicator-modal');
        if (modalElement) {
          this.modalShowListener = () => this.cargarProyectos();
          modalElement.addEventListener('show.bs.modal', this.modalShowListener);
        }
      }

      ngOnDestroy(): void {
        const modalElement = document.getElementById('register-indicator-modal');
        if (modalElement && this.modalShowListener) {
          modalElement.removeEventListener('show.bs.modal', this.modalShowListener);
        }
      }

      cargarProyectos(): void {
        this.cargandoProyectos = true;
        this.projectService.consultarProyectos(0, 1000).subscribe({
          next: (response: any) => {
            this.proyectos = response.content;
            this.cargandoProyectos = false;
          },
          error: (error) => {
            console.error('Error al cargar los proyectos:', error);
            this.cargandoProyectos = false;
          }
        });
      }

      cargarTemporalidades(): void {
        this.cargandoTemporalidades = true;
        this.frequencyService.consultarTemporalidades().subscribe({
          next: (temporalidades: FrequencyResponse[]) => {
            this.temporalidades = temporalidades;
            this.cargandoTemporalidades = false;
          },
          error: (error) => {
            console.error('Error al cargar las temporalidades:', error);
            this.cargandoTemporalidades = false;
          }
        });
      }

      cargarTiposIndicador(): void {
        this.cargandoTiposIndicador = true;
        this.indicatorTypeService.consultarTiposIndicador().subscribe({
          next: (tiposIndicador: IndicatorTypeResponse[]) => {
            this.tiposIndicador = tiposIndicador;
            this.cargandoTiposIndicador = false;
          },
          error: (error) => {
            console.error('Error al cargar los tipos de indicador:', error);
            this.cargandoTiposIndicador = false;
          }
        });
      }

      cargarPublicoInteres(): void {
        this.cargandoPublicoInteres = true;
        this.interestedPublicService.consultarPublicoInteres().subscribe({
          next: (publicoInteres: InterestedPublicResponse[]) => {
            // Mapear el público de interés del servicio al formato esperado
            this.publicoInteresDisponibles = publicoInteres.map(publico => ({
              value: publico.identificador,
              label: publico.nombre
            }));
            this.publicoInteresFiltrados = [...this.publicoInteresDisponibles];
            this.cargandoPublicoInteres = false;
          },
          error: (error) => {
            console.error('Error al cargar el público de interés:', error);
            this.cargandoPublicoInteres = false;
          }
        });
      }

      filterPublicoInteres() {
        const term = this.searchPublicoInteres.toLowerCase();
        if (!this.publicoInteresDisponibles || this.publicoInteresDisponibles.length === 0) {
          this.publicoInteresFiltrados = [];
          return;
        }

        if (term.trim() === '') {
          this.publicoInteresFiltrados = [...this.publicoInteresDisponibles];
        } else {
          this.publicoInteresFiltrados = this.publicoInteresDisponibles.filter(publico =>
            publico.label.toLowerCase().includes(term)
          );
        }
      }

      isPublicoInteresSelected(value: string): boolean {
        return this.indicador.publicosInteres?.includes(value) || false;
      }

      togglePublicoInteres(value: string) {
        if (!this.indicador.publicosInteres) {
          this.indicador.publicosInteres = [];
        }

        const index = this.indicador.publicosInteres.indexOf(value);
        if (index > -1) {
          this.indicador.publicosInteres.splice(index, 1);
        } else {
          this.indicador.publicosInteres.push(value);
        }
      }

      removePublicoInteres(value: string) {
        if (this.indicador.publicosInteres) {
          const index = this.indicador.publicosInteres.indexOf(value);
          if (index > -1) {
            this.indicador.publicosInteres.splice(index, 1);
          }
        }
      }

      getSelectedPublicoInteresLabels(): string[] {
        if (!this.indicador.publicosInteres) {
          return [];
        }
        return this.indicador.publicosInteres
          .map(value => this.publicoInteresDisponibles.find(publico => publico.value === value)?.label)
          .filter(label => label) as string[];
      }

      getPublicoInteresValue(label: string): string {
        const publico = this.publicoInteresDisponibles.find(publico => publico.label === label);
        return publico?.value || '';
      }

      registrarIndicador() {
        if (this.cargando) return;

        this.cargando = true;
        this.error = '';
        this.exito = '';

        // Validar que hay público de interés seleccionado
        if (!this.indicador.publicosInteres || this.indicador.publicosInteres.length === 0) {
          this.error = 'Debe seleccionar al menos un público de interés para el indicador';
          this.cargando = false;
          return;
        }

        // Preparar el objeto del indicador para enviar
        const indicadorRequest: IndicatorRequest = {
          nombre: this.indicador.nombre,
          tipoIndicador: this.indicador.tipoIndicador,
          temporalidad: this.indicador.temporalidad,
          proyecto: this.indicador.proyecto,
          publicosInteres: this.indicador.publicosInteres // Lista de identificadores
        };

        this.indicatorService.agregarNuevoIndicador(indicadorRequest).subscribe({
          next: (response) => {
            this.exito = 'Indicador creado exitosamente';
            this.indicadorCreado.emit(this.indicador);

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
            let mensajeError = 'Error al registrar el indicador. Por favor, intente nuevamente.';

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
        const modalElement = document.getElementById('register-indicator-modal');
        if (modalElement) {
          const modal = Modal.getInstance(modalElement) || new Modal(modalElement);
          modal.hide();
        }
      }

      limpiarFormulario() {
        this.indicador = {
          nombre: '',
          tipoIndicador: '',
          temporalidad: '',
          proyecto: '',
          publicosInteres: [] as string[]
        };
        this.searchPublicoInteres = '';
        this.publicoInteresFiltrados = [...this.publicoInteresDisponibles];
        this.showDropdown = false;
        this.error = '';
        this.exito = '';
      }

}
