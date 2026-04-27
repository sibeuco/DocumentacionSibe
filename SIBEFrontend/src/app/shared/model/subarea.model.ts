import { ActivityDetailResponse } from "./activity.model";

export interface SubAreaResponse{
    identificador: string;
    nombre: string;
}

export interface SubAreaDetailResponse{
    identificador: string;
    nombre: string;
    actividades: ActivityDetailResponse;
    areaNombre: string;
    direccionNombre: string;
}
