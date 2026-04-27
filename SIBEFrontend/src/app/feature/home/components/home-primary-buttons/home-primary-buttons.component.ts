import { Component } from '@angular/core';
import { ExcelReportService } from 'src/app/shared/service/excel-report.service';
import { StateService } from 'src/app/shared/service/state.service';
import { StateProps } from 'src/app/shared/model/state.enum';
import { UserSession } from 'src/app/feature/login/model/user-session.model';

@Component({
  selector: 'app-home-primary-buttons',
  templateUrl: './home-primary-buttons.component.html',
  styleUrls: ['./home-primary-buttons.component.scss']
})
export class HomePrimaryButtonsComponent {

  private readonly NOMBRE_DIRECCION = 'Dirección de Bienestar y Evangelización';
  generandoExcel: boolean = false;
  mensajeError: string = '';
  mensajeExito: string = '';

  get esColaborador(): boolean {
    const session = this.stateService.getState(StateProps.USER_SESSION) as UserSession;
    const rolesPermitidos = ['ADMINISTRADOR_DIRECCION', 'ADMINISTRADOR_AREA'];
    return session ? !rolesPermitidos.includes(session.rol) : false;
  }

  constructor(private excelReportService: ExcelReportService, private stateService: StateService) {}

  scrollToFilters(): void {
    const element = document.getElementById('home-filters-section');
    if (element) {
      element.scrollIntoView({
        behavior: 'smooth',
        block: 'start'
      });
    }
  }

  generarExcel(): void {
    if (this.generandoExcel || this.esColaborador) {
      return;
    }

    this.generandoExcel = true;
    this.mensajeError = '';
    this.mensajeExito = '';

    this.excelReportService.generarInformeDireccion(this.NOMBRE_DIRECCION).subscribe({
      next: () => {
        this.generandoExcel = false;
        this.mensajeExito = 'Informe generado exitosamente.';
        this.limpiarMensajeDespues();
      },
      error: (error) => {
        console.error('Error al generar el informe:', error);
        if (error?.status === 403 && error?.error?.mensaje) {
          this.mensajeError = error.error.mensaje;
        } else if (error?.status === 403) {
          this.mensajeError = 'No tienes permisos para generar informes. Tu rol no permite realizar esta acción.';
        } else if (error?.message === 'NO_DATA') {
          this.mensajeError = 'No se encontraron actividades para exportar.';
        } else {
          this.mensajeError = 'Error al generar el informe. Por favor, intente nuevamente.';
        }
        this.generandoExcel = false;
        this.limpiarMensajeDespues();
      }
    });
  }

  private limpiarMensajeDespues(): void {
    setTimeout(() => {
      this.mensajeError = '';
      this.mensajeExito = '';
    }, 6000);
  }

}
