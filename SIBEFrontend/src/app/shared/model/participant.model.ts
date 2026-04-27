import { CostCenterResponse } from "./cost-center.model";
import { EmploymentRelationshipResponse } from "./employment-relationship.model";

export interface ParticipantRequest {
  identificador: string | null;
  nombreCompleto: string;
  documentoIdentificacion: string;
}

export interface ParticipantResponse {
  identificador: string;
  nombreCompleto: string;
  numeroIdentificacion: string;
  relacionLaboral: EmploymentRelationshipResponse;
  centroCostos: CostCenterResponse;
  tipo: string;
  fechaNacimiento: string;
  nacionalidad: string;
  estadoCivil: string;
  correoPersonal: string;
  correoInstitucional: string;
  programaAcademico: string;
  tipoProgramaAcademico: string;
  facultad: string;
  annoIngreso: number;
  semestreActual: string;
  creditosAprobados: number;
  promedioGeneral: number;
  estadoAcademico: string;
  modalidadEstudio: string;
  tiempoLlegadaUniversidad: number;
  medioTransporte: string;
}