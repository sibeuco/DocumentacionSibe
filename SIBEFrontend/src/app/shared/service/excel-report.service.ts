import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { switchMap, map } from 'rxjs/operators';
import { DepartmentService } from './department.service';
import { AreaService } from './area.service';
import { SubAreaService } from './subarea.service';
import { DepartmentDetailResponse } from '../model/department.model';
import { AreaDetailResponse } from '../model/area.model';
import { SubAreaDetailResponse } from '../model/subarea.model';
import { ActivityDetailResponse } from '../model/activity.model';
import { ActivityExecutionDetailResponse } from '../model/activity-execution.model';
import { ParticipantResponse } from '../model/participant.model';
import * as XLSX from 'xlsx';

@Injectable({
  providedIn: 'root'
})
export class ExcelReportService {

  constructor(
    private departmentService: DepartmentService,
    private areaService: AreaService,
    private subAreaService: SubAreaService
  ) {}

  generarInformeDireccion(nombre: string): Observable<void> {
    return this.departmentService.consultarPorNombre(nombre).pipe(
      switchMap(department => this.departmentService.consultarDetalle(department.identificador)),
      map(detail => this.exportarDireccionAExcel(detail))
    );
  }

  generarInformeArea(nombre: string): Observable<void> {
    return this.areaService.consultarPorNombre(nombre).pipe(
      switchMap(area => this.areaService.consultarDetalle(area.identificador)),
      map(detail => this.exportarAreaAExcel(detail))
    );
  }

  generarInformeSubarea(nombre: string): Observable<void> {
    return this.subAreaService.consultarPorNombre(nombre).pipe(
      switchMap(subarea => this.subAreaService.consultarDetalle(subarea.identificador)),
      map(detail => this.exportarSubareaAExcel(detail))
    );
  }

  private exportarDireccionAExcel(detail: DepartmentDetailResponse): void {
    const datos: any[] = [];

    if (detail.actividades && detail.actividades.length > 0) {
      detail.actividades.forEach(actividad => {
        this.procesarActividad(actividad, detail.nombre, '', '', datos);
      });
    }

    if (detail.areas && detail.areas.length > 0) {
      detail.areas.forEach((area: AreaDetailResponse) => {
        if (area.actividades && area.actividades.length > 0) {
          area.actividades.forEach(actividad => {
            this.procesarActividad(actividad, detail.nombre, area.nombre, '', datos);
          });
        }

        if (area.subareas && area.subareas.length > 0) {
          area.subareas.forEach((subarea: SubAreaDetailResponse) => {
            this.procesarActividadesSubarea(subarea, detail.nombre, area.nombre, datos);
          });
        }
      });
    }

    this.generarArchivoExcel(datos, detail.nombre);
  }

  private exportarAreaAExcel(detail: AreaDetailResponse): void {
    const datos: any[] = [];
    const direccionNombre = detail.direccionNombre || '';

    if (detail.actividades && detail.actividades.length > 0) {
      detail.actividades.forEach(actividad => {
        this.procesarActividad(actividad, direccionNombre, detail.nombre, '', datos);
      });
    }

    if (detail.subareas && detail.subareas.length > 0) {
      detail.subareas.forEach((subarea: SubAreaDetailResponse) => {
        this.procesarActividadesSubarea(subarea, direccionNombre, detail.nombre, datos);
      });
    }

    this.generarArchivoExcel(datos, detail.nombre);
  }

  private exportarSubareaAExcel(detail: SubAreaDetailResponse): void {
    const datos: any[] = [];
    const areaNombre = detail.areaNombre || '';
    const direccionNombre = detail.direccionNombre || '';
    this.procesarActividadesSubarea(detail, direccionNombre, areaNombre, datos);
    this.generarArchivoExcel(datos, detail.nombre);
  }

  private procesarActividadesSubarea(
    subarea: SubAreaDetailResponse,
    direccionNombre: string,
    areaNombre: string,
    datos: any[]
  ): void {
    if (subarea.actividades) {
      const actividadesArray = Array.isArray(subarea.actividades)
        ? subarea.actividades
        : [subarea.actividades];

      actividadesArray.forEach((actividad: ActivityDetailResponse) => {
        if (actividad && actividad.identificador) {
          this.procesarActividad(actividad, direccionNombre, areaNombre, subarea.nombre, datos);
        }
      });
    }
  }

  private procesarActividad(
    actividad: ActivityDetailResponse,
    direccionNombre: string,
    areaNombre: string,
    subareaNombre: string,
    datos: any[]
  ): void {
    if (!actividad.fechasProgramadas || actividad.fechasProgramadas.length === 0) {
      const fila = this.crearFilaBase(actividad, direccionNombre, areaNombre, subareaNombre);
      datos.push(fila);
      return;
    }

    actividad.fechasProgramadas.forEach((ejecucion: ActivityExecutionDetailResponse) => {
      if (!ejecucion.participantes || ejecucion.participantes.length === 0) {
        const fila = this.crearFilaBase(actividad, direccionNombre, areaNombre, subareaNombre);
        this.llenarDatosEjecucion(fila, ejecucion);
        datos.push(fila);
        return;
      }

      ejecucion.participantes.forEach((participante: ParticipantResponse) => {
        const fila = this.crearFilaBase(actividad, direccionNombre, areaNombre, subareaNombre);
        this.llenarDatosEjecucion(fila, ejecucion);
        this.llenarDatosParticipante(fila, participante);
        datos.push(fila);
      });
    });
  }

  private llenarDatosEjecucion(fila: any, ejecucion: ActivityExecutionDetailResponse): void {
    fila['Ejecución Fecha Programada'] = ejecucion.fechaProgramada || '';
    fila['Ejecución Fecha Realización'] = ejecucion.fechaRealizacion || '';
    fila['Ejecución Hora Inicio'] = (ejecucion as any).horaInicio || ejecucion.HoraInicio || '';
    fila['Ejecución Hora Fin'] = (ejecucion as any).horaFin || ejecucion.HoraFin || '';
    fila['Estado Nombre'] = ejecucion.estadoActividad?.nombre || '';
  }

  private llenarDatosParticipante(fila: any, participante: ParticipantResponse): void {
    fila['Participante Nombre Completo'] = participante.nombreCompleto || '';
    fila['Participante Número Identificación'] = participante.numeroIdentificacion || '';
    fila['Participante Tipo'] = participante.tipo || '';
    fila['Participante Centro Costos Código'] = participante.centroCostos?.codigo || '';
    fila['Participante Centro Costos Descripción'] = participante.centroCostos?.descripcion || '';
    fila['Participante Fecha Nacimiento'] = participante.fechaNacimiento || '';
    fila['Participante Nacionalidad'] = participante.nacionalidad || '';
    fila['Participante Estado Civil'] = participante.estadoCivil || '';
    fila['Participante Correo Personal'] = participante.correoPersonal || '';
    fila['Participante Correo Institucional'] = participante.correoInstitucional || '';
    fila['Participante Programa Académico'] = participante.programaAcademico || '';
    fila['Participante Tipo Programa Académico'] = participante.tipoProgramaAcademico || '';
    fila['Participante Facultad'] = participante.facultad || '';
    fila['Participante Año Ingreso'] = participante.annoIngreso || '';
    fila['Participante Semestre Actual'] = participante.semestreActual || '';
    fila['Participante Estado Académico'] = participante.estadoAcademico || '';
    fila['Participante Modalidad Estudio'] = participante.modalidadEstudio || '';
    fila['Participante Tiempo Llegada Universidad'] = participante.tiempoLlegadaUniversidad || '';
    fila['Participante Medio Transporte'] = participante.medioTransporte || '';
  }

  private crearFilaBase(actividad: ActivityDetailResponse, nombreDireccion: string, nombreArea: string, nombreSubarea: string): any {
    const publicosInteres = actividad.indicador?.publicosInteres
      ? actividad.indicador.publicosInteres.map((pi) => pi.nombre).join(', ')
      : '';

    return {
      'Dirección Nombre': nombreDireccion,
      'Área Nombre': nombreArea || '',
      'Subárea Nombre': nombreSubarea || '',
      'Actividad Nombre': actividad.nombre || '',
      'Actividad Objetivo': actividad.objetivo || '',
      'Actividad Semestre': actividad.semestre || '',
      'Actividad Fecha Creación': actividad.fechaCreacion || '',
      'Actividad Colaborador': actividad.colaborador || '',
      'Indicador Nombre': actividad.indicador?.nombre || '',
      'Tipo Indicador Naturaleza': actividad.indicador?.tipoIndicador?.naturalezaIndicador || '',
      'Tipo Indicador Tipología': actividad.indicador?.tipoIndicador?.tipologiaIndicador || '',
      'Temporalidad Nombre': actividad.indicador?.temporalidad?.nombre || '',
      'Proyecto Número': actividad.indicador?.proyecto?.numeroProyecto || '',
      'Proyecto Nombre': actividad.indicador?.proyecto?.nombre || '',
      'Proyecto Objetivo': actividad.indicador?.proyecto?.objetivo || '',
      'Públicos de Interés': publicosInteres,
      'Ejecución Fecha Programada': '',
      'Ejecución Fecha Realización': '',
      'Ejecución Hora Inicio': '',
      'Ejecución Hora Fin': '',
      'Estado Nombre': '',
      'Participante Nombre Completo': '',
      'Participante Número Identificación': '',
      'Participante Tipo': '',
      'Participante Centro Costos Código': '',
      'Participante Centro Costos Descripción': '',
      'Participante Fecha Nacimiento': '',
      'Participante Nacionalidad': '',
      'Participante Estado Civil': '',
      'Participante Correo Personal': '',
      'Participante Correo Institucional': '',
      'Participante Programa Académico': '',
      'Participante Tipo Programa Académico': '',
      'Participante Facultad': '',
      'Participante Año Ingreso': '',
      'Participante Semestre Actual': '',
      'Participante Estado Académico': '',
      'Participante Modalidad Estudio': '',
      'Participante Tiempo Llegada Universidad': '',
      'Participante Medio Transporte': ''
    };
  }

  private generarArchivoExcel(datos: any[], nombre: string): void {
    if (datos.length === 0) {
      throw new Error('NO_DATA');
    }

    const ws = XLSX.utils.json_to_sheet(datos);
    const wb = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Actividades');

    ws['!cols'] = [
      { wch: 30 }, { wch: 20 }, { wch: 20 }, { wch: 30 }, { wch: 40 },
      { wch: 10 }, { wch: 12 }, { wch: 20 }, { wch: 20 }, { wch: 15 },
      { wch: 15 }, { wch: 15 }, { wch: 15 }, { wch: 30 }, { wch: 40 },
      { wch: 30 }, { wch: 12 }, { wch: 12 }, { wch: 12 }, { wch: 12 },
      { wch: 15 }, { wch: 40 }, { wch: 15 }, { wch: 10 }, { wch: 15 },
      { wch: 30 }, { wch: 12 }, { wch: 15 }, { wch: 15 }, { wch: 30 },
      { wch: 30 }, { wch: 30 }, { wch: 20 }, { wch: 20 }, { wch: 10 },
      { wch: 12 }, { wch: 10 }, { wch: 10 }, { wch: 15 }, { wch: 15 },
      { wch: 10 }, { wch: 20 }
    ];

    const fecha = new Date().toISOString().split('T')[0];
    const nombreArchivo = `Actividades_${nombre.replace(/\s+/g, '_')}_${fecha}.xlsx`;
    XLSX.writeFile(wb, nombreArchivo);
  }
}
