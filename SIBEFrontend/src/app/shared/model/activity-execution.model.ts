import { ActivityStateResponse } from "./activity-state.model";
import { ParticipantResponse } from "./participant.model";

export interface ActivityExecutionResponse{
    identificador: string;
    fechaProgramada: string;
    fechaRealizacion: string;
    HoraInicio: string;
    HoraFin: string;
    estadoActividad: ActivityStateResponse;
}

export interface ActivityExecutionDetailResponse{
    identificador: string;
    fechaProgramada: string;
    fechaRealizacion: string;
    HoraInicio: string;
    HoraFin: string;
    estadoActividad: ActivityStateResponse;
    participantes: ParticipantResponse[];
}

export interface EditActivityExecutionRquest{
    identificador: string;
    fechaProgramada: string;
}