import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ActivityInfo, ActivityResponse } from '../../model/activity.model';
import { ActivityExecutionResponse } from '../../model/activity-execution.model';

@Component({
  selector: 'app-activity-info',
  templateUrl: './activity-info.component.html',
  styleUrls: ['./activity-info.component.scss']
})
export class ActivityInfoComponent implements OnInit {

  @Input() actividad: ActivityInfo | null = null;

  actividadSeleccionada: ActivityInfo | null = null;
  cargando = false;
  error = '';

  private readonly STORAGE_KEY = 'selectedActivityInfo';

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.cargarDesdeEstado();
    this.cargarDesdeStorage();

    if (!this.actividadSeleccionada && this.actividad) {
      this.actividadSeleccionada = this.actividad;
    }

    if (!this.actividadSeleccionada) {
      this.error = 'No hay actividad seleccionada para mostrar.';
    }
  }

  formatearFechaProgramada(fechaISO?: string): string {
    if (!fechaISO) return '';
    const fecha = this.parseFechaLocal(fechaISO);
    const dia = fecha.getDate().toString().padStart(2, '0');
    const mes = (fecha.getMonth() + 1).toString().padStart(2, '0');
    const año = fecha.getFullYear();
    return `${dia}/${mes}/${año}`;
  }

  esActividadFinalizada(): boolean {
    return (this.actividadSeleccionada?.estado || '').toUpperCase() === 'FINALIZADO' || (this.actividadSeleccionada?.estado || '').toUpperCase() === 'FINALIZADA';
  }

  formatearFechaRealizacion(fechaISO?: string): string {
    if (!fechaISO) return '';
    const fecha = this.parseFechaLocal(fechaISO);
    const dia = fecha.getDate().toString().padStart(2, '0');
    const mes = (fecha.getMonth() + 1).toString().padStart(2, '0');
    const año = fecha.getFullYear();
    return `${dia}/${mes}/${año}`;
  }

  private cargarDesdeEstado(): void {
    const navigationState = this.router.getCurrentNavigation()?.extras?.state as { actividad?: ActivityResponse | ActivityInfo; ejecucion?: ActivityExecutionResponse } | undefined;
    if (navigationState?.actividad) {
      this.setActividad(navigationState.actividad, navigationState.ejecucion || null);
      return;
    }

    const historyState = window.history.state as { actividad?: ActivityResponse | ActivityInfo; ejecucion?: ActivityExecutionResponse } | undefined;
    if (historyState?.actividad) {
      this.setActividad(historyState.actividad, historyState.ejecucion || null);
    }
  }

  private cargarDesdeStorage(): void {
    if (this.actividadSeleccionada) {
      return;
    }

    const storage = this.obtenerStorage();
    const raw = storage?.getItem(this.STORAGE_KEY);
    if (!raw) {
      return;
    }

    try {
      const data = JSON.parse(raw) as { actividad?: ActivityResponse | ActivityInfo; ejecucion?: ActivityExecutionResponse | null };
      if (data.actividad) {
        this.setActividad(data.actividad, data.ejecucion || null, false);
      }
    } catch {
      storage?.removeItem(this.STORAGE_KEY);
    }
  }

  private setActividad(actividadDato: ActivityResponse | ActivityInfo, ejecucion: ActivityExecutionResponse | null, persistir = true): void {
    this.error = '';
    this.actividadSeleccionada = this.construirActividadInfo(actividadDato, ejecucion);

    if (persistir) {
      const storage = this.obtenerStorage();
      if (storage) {
        try {
          storage.setItem(this.STORAGE_KEY, JSON.stringify({ actividad: actividadDato, ejecucion }));
        } catch {
          storage.removeItem(this.STORAGE_KEY);
        }
      }
    }
  }

  private construirActividadInfo(actividadDato: ActivityResponse | ActivityInfo, ejecucion: ActivityExecutionResponse | null): ActivityInfo {
    const nombre = (actividadDato as any).nombre || '';
    const colaborador = (actividadDato as any).nombreColaborador || (actividadDato as any).colaborador || 'Sin colaborador';
    const objetivo = (actividadDato as any).objetivo || '';

    const indicadorDato = (actividadDato as any).indicador;
    let indicador = '';
    if (typeof indicadorDato === 'string') {
      indicador = indicadorDato;
    } else if (indicadorDato && typeof indicadorDato === 'object') {
      const nombreIndicador = indicadorDato.nombre || '';
      const tipologia = indicadorDato.tipoIndicador?.tipologiaIndicador;
      indicador = tipologia ? `${nombreIndicador} - ${tipologia}` : nombreIndicador;
    }

    return {
      nombre,
      colaborador,
      objetivo,
      indicador,
      fechaProgramada: ejecucion?.fechaProgramada || (actividadDato as any).fechaProgramada,
      estado: ejecucion?.estadoActividad?.nombre || (actividadDato as any).estado,
      fechaRealizacion: ejecucion?.fechaRealizacion || (actividadDato as any).fechaRealizacion
    };
  }

  private obtenerStorage(): Storage | null {
    if (typeof window === 'undefined') {
      return null;
    }
    return window.sessionStorage;
  }

  private parseFechaLocal(fechaStr: string): Date {
    const soloFecha = /^\d{4}-\d{2}-\d{2}$/;
    if (soloFecha.test(fechaStr)) {
      const [y, m, d] = fechaStr.split('-').map(Number);
      return new Date(y, m - 1, d);
    }
    const d = new Date(fechaStr);
    if (!isNaN(d.getTime())) {
      return new Date(d.getFullYear(), d.getMonth(), d.getDate());
    }
    return new Date();
  }
}


