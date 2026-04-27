export interface FechaProgramada {
  id: number;
  fecha: Date;
  estado: EstadoFechaProgramada;
  actividadId: number;
}

export enum EstadoFechaProgramada {
  PENDIENTE = 'Pendiente',
  EN_CURSO = 'En curso',
  FINALIZADO = 'Finalizada'
}
