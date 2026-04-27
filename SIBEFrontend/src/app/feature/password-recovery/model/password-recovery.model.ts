export interface CodeRequest {
    codigo: string;
    correo: string;
}

export interface RestorePasswordRequest{
    correo: string;
    clave: string;
}