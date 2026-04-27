import { IndicatorTypeResponse } from "./indicator-type.model";
import { ProjectResponse } from "./project.model";
import { FrequencyResponse } from "./frequency.model";
import { InterestedPublicResponse } from "./interested-public.model";

export interface IndicatorRequest{
    nombre: string;
    tipoIndicador: string;
    temporalidad: string;
    proyecto: string;
    publicosInteres: string[];
}

export interface EditIndicatorRequest{
    nombre: string;
    tipoIndicador: string;
    temporalidad: string;
    proyecto: string;
    publicosInteres: string[];
}

export interface IndicatorResponse{
    identificador: string;
    nombre: string;
    tipoIndicador: IndicatorTypeResponse;
    temporalidad: FrequencyResponse;
    proyecto: ProjectResponse;
    publicosInteres: InterestedPublicResponse[];
}