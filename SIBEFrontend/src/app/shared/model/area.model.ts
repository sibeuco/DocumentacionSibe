import { ActivityDetailResponse } from "./activity.model";
import { SubAreaDetailResponse } from "./subarea.model";

export interface AreaRequest{
    area: string;
    tipoArea: string;
}

export interface AreaResponse{
    identificador: string;
    nombre: string;
    tipoArea: string;
}

export interface AreaDetailResponse{
    identificador: string;
    nombre: string;
    subareas: SubAreaDetailResponse[];
    actividades: ActivityDetailResponse[];
    direccionNombre: string;
}
