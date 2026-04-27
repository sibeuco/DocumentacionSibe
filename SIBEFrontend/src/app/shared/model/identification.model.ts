import { IdentificationTypeResponse } from "./identification-type.model";

export interface IdentificationResponse{
    identificador: string;
    numeroIdentificacion: string;
    tipoIdentificacion: IdentificationTypeResponse;
}