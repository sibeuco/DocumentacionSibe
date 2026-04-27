package co.edu.uco.sibe.dominio.transversal.constante;

import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.NO_SE_PUEDE_INSTANCIAR_UNA_CLASE_UTILITARIA;

public final class CorreoConstante {
    public static final String UTF_OCHO = "UTF-8";
    public static final String LOGO_IMAGE_PROPERTY = "logoImage";
    public static final String LOGO_PATH = "static/images/Logo.png";
    public static final String ASUNTO_CORREO = "Codigo de Recuperación SIBE";
    public static final String PLANTILLA_HTML_RECUPERACION_CLAVE = "<html><body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 30px;\"><div style=\"max-width: 600px; margin: auto; background: white; padding: 20px; border-radius: 10px; box-shadow: 0 2px 6px rgba(0,0,0,0.1);\"><div style=\"text-align: center; margin-bottom: 20px;\"><img src=\"cid:logoImage\" alt=\"SIBE Logo\" style=\"max-width: 150px;\" /></div><p style=\"font-size: 16px; color: #333; text-align: center;\">Hola 👋,</p><p style=\"font-size: 16px; color: #333; text-align: center;\">Tu códido de recuperación de contraseña es:</p><p style=\"text-align: center; font-size: 32px;\"><a style=\"background-color: #008b50; color:  white; font-weight: 600; padding: 10px 20px; text-decoration: none; border-radius: 5px;\">%s</a></p><p style=\"font-size: 16px; color: #333; text-align: center;\"><b> Este código se vencerá en los próximos 5 minutos</b>. Si no has sido tú, ignora este correo.</p><p style=\"font-size: 14px; color: #777; text-align: center;\">© 2025 SIBE</p></div></body></html>";

    private CorreoConstante() {
        throw new UnsupportedOperationException(NO_SE_PUEDE_INSTANCIAR_UNA_CLASE_UTILITARIA);
    }
}