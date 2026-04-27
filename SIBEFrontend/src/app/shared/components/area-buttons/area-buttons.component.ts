import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { ExcelReportService } from '../../service/excel-report.service';
import { StateService } from '../../service/state.service';
import { StateProps } from '../../model/state.enum';
import { UserSession } from 'src/app/feature/login/model/user-session.model';

@Component({
  selector: 'app-area-buttons',
  templateUrl: './area-buttons.component.html',
  styleUrls: ['./area-buttons.component.scss']
})
export class AreaButtonsComponent {

  @Input() buttons: { text: string, icon: string, link?: string, scrollTarget?: string, action?: string }[] = [];
  @Input() tipoEstructura: 'area' | 'subarea' = 'area';
  @Input() nombreEstructura: string = '';

  generandoExcel = false;
  mensajeError: string = '';
  mensajeExito: string = '';

  get esColaborador(): boolean {
    const session = this.stateService.getState(StateProps.USER_SESSION) as UserSession;
    const rolesPermitidos = ['ADMINISTRADOR_DIRECCION', 'ADMINISTRADOR_AREA'];
    return session ? !rolesPermitidos.includes(session.rol) : false;
  }

  constructor(private router: Router, private excelReportService: ExcelReportService, private stateService: StateService) {}

  onButtonClick(button: { text: string, icon: string, link?: string, scrollTarget?: string, action?: string }): void {
    if (button.action === 'generarInforme') {
      if (this.esColaborador) {
        return;
      }
      this.generarInforme();
    } else {
      this.navigateTo(button);
    }
  }

  navigateTo(button: { text: string, icon: string, link?: string, scrollTarget?: string }) {

    if (button.scrollTarget) {
      const element = document.getElementById(button.scrollTarget);
      if (element) {
        element.scrollIntoView({
          behavior: 'smooth',
          block: 'start'
        });
      }
    }

    else if (button.link) {
      this.router.navigate([button.link]);
    }
  }

  private generarInforme(): void {
    if (this.generandoExcel) {
      return;
    }

    this.generandoExcel = true;
    this.mensajeError = '';
    this.mensajeExito = '';

    const observable = this.tipoEstructura === 'area'
      ? this.excelReportService.generarInformeArea(this.nombreEstructura)
      : this.excelReportService.generarInformeSubarea(this.nombreEstructura);

    observable.subscribe({
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
