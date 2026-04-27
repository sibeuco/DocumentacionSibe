export interface FiltersRequest {
    mes: string
    anno: number;
    semestre: string;
    programaAcademico: string;
    tipoProgramaAcademico: string;
    centroCostos: string;
    tipoParticipante: string;
    indicador: string;
    tipoArea: string;
    idArea: string;
}

export interface FiltersRequestWithoutArea {
    mes: string | null;
    anno: number | null;
    semestre: string | null;
    programaAcademico: string | null;
    tipoProgramaAcademico: string | null;
    centroCostos: string | null;
    tipoParticipante: string | null;
    indicador: string | null;
}

export interface StadisticAreasResponse{
    nombre: string;
    tipoArea: string;
    cantidadParticipantes: number;
    cantidadAsistencias: number;
}

export interface StadisticMonthsResponse{
    mes: string;
    cantidadParticipantes: number;
    cantidadAsistencias: number;
}