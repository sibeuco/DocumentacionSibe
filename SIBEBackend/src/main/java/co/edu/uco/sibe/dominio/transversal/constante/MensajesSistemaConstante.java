package co.edu.uco.sibe.dominio.transversal.constante;

public final class MensajesSistemaConstante {

    public static final String OCURRIO_UN_ERROR = "Ocurrió un error inesperado, por favor contactar al administrador del sistema.";
    public static final String NO_HAY_CAMPOS_POR_VALIDAR = "No hay campos por validar para este modelo de dominio";
    public static final String VALIDANDO_AUTENTICACION = "La autenticación está siendo validada.";
    public static final String ERROR_AL_ENVIAR_CORREO = "Error al enviar el correo";
    public static final String ERROR_INESPERADO_EN_EL_SERVICIO_DE_ENVIAR_CORREO = "Error inesperado en el servicio de correo";
    private static final String EL_USUARIO = "El Usuario ";
    private static final String FUE_AUTENTICADO_EXITOSAMENTE_CON_LOS_ROLES = " fue autenticado exitosamente y tiene el rol de: ";
    public static final String NO_SE_PUEDE_INSTANCIAR_UNA_CLASE_UTILITARIA = "No se puede instanciar una clase utilitaria";
    public static final String ERROR_DE_INTEGRIDAD_CON_CONSULTA_DEL_MIEMBRO = "Error de integridad: No se encontró el Miembro Interno con ID ";

    private MensajesSistemaConstante() {
        throw new UnsupportedOperationException(NO_SE_PUEDE_INSTANCIAR_UNA_CLASE_UTILITARIA);
    }

    public static String obtenerMensajeConParametro(String mensaje, Object parametro) {
        return  mensaje + parametro;
    }

    public static String obtenerMensajeDeConfirmacionDeInicioSesion(String correo, String roles) {
        return EL_USUARIO + correo + FUE_AUTENTICADO_EXITOSAMENTE_CON_LOS_ROLES + roles;
    }
}