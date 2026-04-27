export interface UserSession{
    correo: string;
    identificador: string;
    authorities: string[];
    logged: boolean;
    rol: string;
    direccionId: string;
    areaId: string;
    subareaId: string;
}
