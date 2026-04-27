import { Component, ViewChild } from '@angular/core';
import { UploadDatabaseComponent } from 'src/app/shared/components/upload-database/upload-database.component';

@Component({
  selector: 'app-department',
  templateUrl: './department.component.html',
  styleUrls: ['./department.component.scss']
})
export class DepartmentComponent {
  @ViewChild('uploadDatabaseComponent') uploadDatabaseComponent!: UploadDatabaseComponent;

  /**
   * Abre el modal para cargar base de datos de empleados
   */
  abrirModalEmpleados() {
    this.uploadDatabaseComponent.abrirModal('empleados');
  }

  /**
   * Abre el modal para cargar base de datos de estudiantes
   */
  abrirModalEstudiantes() {
    this.uploadDatabaseComponent.abrirModal('estudiantes');
  }

  /**
   * Se ejecuta cuando se selecciona un archivo (mantiene compatibilidad)
   */
  onArchivoSeleccionado(archivo: File) {
    // Archivo seleccionado - lógica adicional si es necesaria
  }

  /**
   * Se ejecuta cuando la carga al backend se completa exitosamente
   */
  onCargaCompleta(respuesta: any) {
    // Aquí puedes:
    // - Actualizar una tabla de datos
    // - Mostrar un mensaje de éxito
    // - Recargar información
    // - etc.
  }

}
