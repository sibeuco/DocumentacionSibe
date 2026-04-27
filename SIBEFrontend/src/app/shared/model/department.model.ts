import { ActivityDetailResponse } from "./activity.model";
import { AreaDetailResponse } from "./area.model";

export interface DepartmentResponse{
    identificador: string;
    nombre: string;
}

export interface DepartmentDetailResponse{
    identificador: string;
    nombre: string;
    areas: AreaDetailResponse[];
    actividades: ActivityDetailResponse[];
}