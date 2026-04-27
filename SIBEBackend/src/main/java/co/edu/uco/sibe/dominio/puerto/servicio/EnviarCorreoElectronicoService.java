package co.edu.uco.sibe.dominio.puerto.servicio;

public interface EnviarCorreoElectronicoService {
    void enviarCorreo(String destinatario, String asunto, String cuerpo);
}