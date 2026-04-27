import { Component, EventEmitter, Output } from '@angular/core';
import { Modal } from 'bootstrap';
import { UploadDatabaseService } from '../../service/upload-database.service';
import { EmployeeFileRequest } from '../../model/employee.model';
import { StudentFileRequest } from '../../model/student.model';

@Component({
  selector: 'app-upload-database',
  templateUrl: './upload-database.component.html',
  styleUrls: ['./upload-database.component.scss']
})
export class UploadDatabaseComponent {
  @Output() archivoSeleccionado = new EventEmitter<File>();
  @Output() cargaCompleta = new EventEmitter<any>();

  archivoActual: File | null = null;
  nombreArchivo: string = '';
  mensajeError: string = '';
  mensajeExito: string = '';
  cargando: boolean = false;
  tipoBaseDatos: 'empleados' | 'estudiantes' = 'empleados';

  // Configuracion de validaciones
  private readonly TAMANO_MAXIMO_MB = 40;
  private readonly TAMANO_MAXIMO_BYTES = this.TAMANO_MAXIMO_MB * 1024 * 1024;
  private readonly EXTENSIONES_PERMITIDAS = ['.xlsx', '.xls'];
  private readonly TIPOS_MIME_PERMITIDOS = [
    'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
    'application/vnd.ms-excel'
  ];

  constructor(private uploadService: UploadDatabaseService) { }

  onFileSelected(event: any) {
    this.mensajeError = '';
    this.mensajeExito = '';

    const archivo: File = event.target.files[0];

    if (archivo) {

      if (!archivo.name) {
        this.mensajeError = 'Por favor selecciona un archivo válido';
        this.limpiarSeleccion();
        return;
      }

      const extension = this.obtenerExtension(archivo.name);
      if (!this.EXTENSIONES_PERMITIDAS.includes(extension)) {
        this.mensajeError = `Solo se permiten archivos de Excel (${this.EXTENSIONES_PERMITIDAS.join(', ')})`;
        this.limpiarSeleccion();
        return;
      }

      // Validar tipo MIME
      if (!this.TIPOS_MIME_PERMITIDOS.includes(archivo.type)) {
        this.mensajeError = 'El archivo seleccionado no es un archivo de Excel válido';
        this.limpiarSeleccion();
        return;
      }

      if (archivo.size > this.TAMANO_MAXIMO_BYTES) {
        this.mensajeError = `El archivo es demasiado grande. Tamano maximo: ${this.TAMANO_MAXIMO_MB}MB`;
        this.limpiarSeleccion();
        return;
      }

      this.archivoActual = archivo;
      this.nombreArchivo = archivo.name;
      this.mensajeExito = 'Archivo seleccionado correctamente';
    }
  }

  abrirModal(tipo: 'empleados' | 'estudiantes') {
    this.tipoBaseDatos = tipo;
    this.limpiarFormulario();
    const modalElement = document.getElementById('upload-database-modal');
    if (modalElement) {
      const modal = Modal.getInstance(modalElement) || new Modal(modalElement);
      modal.show();
    }
  }

  cargarArchivo() {
    if (!this.archivoActual) {
      this.mensajeError = 'Por favor selecciona un archivo primero';
      return;
    }

    this.cargando = true;
    this.mensajeError = '';
    this.mensajeExito = '';

    const request = this.crearRequestCarga();
    if (!request) {
      this.cargando = false;
      this.mensajeError = 'No se pudo preparar la solicitud de carga';
      return;
    }

    const carga$ = this.tipoBaseDatos === 'empleados'
      ? this.uploadService.subirArchivoEmpleados(request as EmployeeFileRequest)
      : this.uploadService.subirArchivoEstudiantes(request as StudentFileRequest);

    carga$.subscribe({
      next: (respuesta) => {
        this.cargando = false;

        if (respuesta && respuesta.mensaje) {
          this.mensajeExito = respuesta.mensaje;
        } else if (respuesta && respuesta.message) {
          this.mensajeExito = respuesta.message;
        } else {
          this.mensajeExito = 'Archivo cargado exitosamente en la base de datos';
        }

        // Emitir el archivo al componente padre (por compatibilidad)
        this.archivoSeleccionado.emit(this.archivoActual!);

        // Emitir la respuesta del backend
        this.cargaCompleta.emit(respuesta);

        setTimeout(() => {
          this.cerrarModal();
          this.limpiarFormulario();
        }, 2000);
      },
      error: (error) => {
        this.cargando = false;

        if (error.status === 0) {
          this.mensajeError = 'No se pudo conectar con el servidor';
        } else if (error.status === 400) {
          this.mensajeError = error.error?.mensaje || error.error?.message || 'Error en la validación del archivo';
        } else if (error.status === 401) {
          this.mensajeError = error.error?.mensaje || 'No tienes permisos para realizar esta acción. Tu rol no permite acceder a este recurso.';
        } else if (error.status === 403) {
          this.mensajeError = error.error?.mensaje || 'No tienes permisos para realizar esta acción. Tu rol no permite acceder a este recurso.';
        } else if (error.status === 500) {
          this.mensajeError = error.error?.mensaje || error.error?.message || 'Error del servidor al procesar el archivo';
        } else {
          this.mensajeError = error.error?.mensaje || error.error?.message || 'Error al cargar el archivo al servidor';
        }
      },
      complete: () => {}
    });
  }

  eliminarArchivo() {
    this.limpiarSeleccion();
    this.mensajeError = '';
    this.mensajeExito = 'Archivo eliminado';
  }

  private obtenerExtension(nombreArchivo: string): string {
    return nombreArchivo.substring(nombreArchivo.lastIndexOf('.')).toLowerCase();
  }

  private limpiarSeleccion() {
    this.archivoActual = null;
    this.nombreArchivo = '';
    // Limpiar el input file
    const fileInput = document.getElementById('fileInput') as HTMLInputElement;
    if (fileInput) {
      fileInput.value = '';
    }
  }

  private limpiarFormulario() {
    this.limpiarSeleccion();
    this.mensajeError = '';
    this.mensajeExito = '';
    this.cargando = false;
  }

  private cerrarModal() {
    const modalElement = document.getElementById('upload-database-modal');
    if (modalElement) {
      const modal = Modal.getInstance(modalElement) || new Modal(modalElement);
      modal.hide();
    }
  }

  obtenerTamanoArchivo(): string {
    if (!this.archivoActual) return '';
    const tamanoMB = (this.archivoActual.size / (1024 * 1024)).toFixed(2);
    return `${tamanoMB} MB`;
  }

  obtenerTituloModal(): string {
    return this.tipoBaseDatos === 'empleados'
      ? 'Cargar Base de Datos de Empleados'
      : 'Cargar Base de Datos de Estudiantes';
  }

  obtenerDescripcionModal(): string {
    return this.tipoBaseDatos === 'empleados'
      ? 'Selecciona un archivo de Excel (.xlsx o .xls) para cargar la base de datos del personal universitario'
      : 'Selecciona un archivo de Excel (.xlsx o .xls) para cargar la base de datos de estudiantes';
  }

  obtenerEtiquetaTipo(): string {
    return this.tipoBaseDatos === 'empleados' ? 'Empleados' : 'Estudiantes';
  }

  private crearRequestCarga(): EmployeeFileRequest | StudentFileRequest | null {
    if (!this.archivoActual) {
      return null;
    }

    if (this.tipoBaseDatos === 'empleados') {
      return { archivo: this.archivoActual };
    }

    return { archivo: this.archivoActual };
  }
}
