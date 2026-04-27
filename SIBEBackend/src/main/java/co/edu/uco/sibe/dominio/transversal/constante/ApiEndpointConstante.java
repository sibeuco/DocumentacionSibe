package co.edu.uco.sibe.dominio.transversal.constante;

import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.NO_SE_PUEDE_INSTANCIAR_UNA_CLASE_UTILITARIA;

public final class ApiEndpointConstante {

    public static final String USER_API = "/usuarios";
    public static final String LOGIN_API = "/login";
    public static final String DIRECCION = "/direcciones";
    public static final String AREA = "/areas";
    public static final String SUBAREA = "/subareas";
    public static final String ACCIONES = "/acciones";
    public static final String INDICADORES = "/indicadores";
    public static final String PUBLICOS_INTERES = "/publicos_interes";
    public static final String TEMPORALIDADES = "/temporalidades";
    public static final String TIPOS_INDICADOR = "/tipos_indicador";
    public static final String CARGA_MASIVA = "/carga_masiva";
    public static final String PROYECTOS = "/proyectos";
    public static final String TIPOS_IDENTIFICACION = "/tipos_identificacion";
    public static final String TIPOS_USUARIO = "/tipos_usuario";
    public static final String EMPLEADOS = "/empleados";
    public static final String ESTUDIANTES = "/estudiantes";
    public static final String ACTIVIDADES = "/actividades";
    public static final String MIEMBROS = "/miembros";
    public static final String ORGANIZACION = "/organizacion";

    public static final String IDENTIFICADOR_PATH = "/{identificador}";
    public static final String RUTA_DETALLE = "/detalle/{identificador}";
    public static final String RUTA_NOMBRE = "/nombre/{nombre}";

    public static final String USUARIO_IDENTIFICADOR = "/usuario/{identificador}";
    public static final String MODIFICAR_CLAVE_PATH = "/modificar/clave";
    public static final String USUARIO_ID_IDENTIFICADOR = "/usuario/id/{identificador}";
    public static final String USUARIO_CORREO_CORREO = "/usuario/correo/{correo}";

    public static final String SOLICITAR_CODIGO_PATH = "/recuperacion/solicitar/{correo}";
    public static final String VALIDAR_CODIGO_PATH = "/recuperacion/validarCodigo";
    public static final String RECUPERAR_CLAVE_PATH = "/recuperacion/recuperarClave";

    public static final String ACTIVIDADES_AREA = "/area/{identificador}";
    public static final String ACTIVIDADES_DIRECCION = "/direccion/{identificador}";
    public static final String ACTIVIDADES_SUBAREA = "/subarea/{identificador}";
    public static final String ACTIVIDADES_EJECUCIONES = "/ejecuciones/{identificador}";
    public static final String PARTICIPANTES_EJECUCION_ACTIVIDAD = "/ejecuciones/ejecucion/participantes/{identificador}";
    public static final String MESES_EJECUCIONES_FINALIZADAS = "/ejecuciones/finalizadas/meses";
    public static final String ANNOS_EJECUCIONES_FINALIZADAS = "/ejecuciones/finalizadas/annos";
    public static final String SEMESTRES_ESTUDIANTES_EJECUCIONES_FINALIZADAS = "/ejecuciones/finalizadas/semestres";
    public static final String CENTROS_COSTOS_EMPLEADOS_EJECUCIONES_FINALIZADAS = "/ejecuciones/finalizadas/centros-costos";
    public static final String TIPOS_PARTICIPANTES_EJECUCIONES_FINALIZADAS = "/ejecuciones/finalizadas/tipos-participantes";
    public static final String PROGRAMAS_ACADEMICOS_ESTUDIANTES_EJECUCIONES_FINALIZADAS = "/ejecuciones/finalizadas/programas-academicos";
    public static final String NIVELES_FORMACION_ESTUDIANTES_EJECUCIONES_FINALIZADAS = "/ejecuciones/finalizadas/niveles-formacion";
    public static final String INDICADORES_EJECUCIONES_FINALIZADAS = "/ejecuciones/finalizadas/indicadores";
    public static final String CONTAR_PARTICIPANTES_EJECUCIONES_FINALIZADAS = "/ejecuciones/finalizadas/participantes/conteo";

    public static final String MIEMBRO_IDENTIFICACION = "/identificacion/{identificacion}";
    public static final String MIEMBRO_CARNET = "/carnet/{carnet}";

    public static final String ACTIVIDAD_INICIAR = "/iniciar/{identificador}";
    public static final String ACTIVIDAD_FINALIZAR = "/finalizar/{identificador}";
    public static final String ACTIVIDAD_CANCELAR = "/cancelar/{identificador}";

    public static final String CONTAR_USUARIOS_PATH = "/{identificador}/usuarios/contar";
    public static final String CONTAR_ASISTENCIAS_EJECUCIONES_FINALIZADAS = "/ejecuciones/finalizadas/asistencias/conteo";
    public static final String CONTAR_EJECUCIONES_FINALIZADAS = "/ejecuciones/finalizadas/conteo";
    public static final String ESTADISTICAS_PARTICIPANTES_POR_ESTRUCTURA = "/ejecuciones/finalizadas/participantes/estadisticas-estructura";
    public static final String ESTADISTICAS_PARTICIPANTES_POR_MES = "/ejecuciones/finalizadas/participantes/estadisticas-mes";

    public static final String CONTAR_POBLACION_TOTAL = "/ejecuciones/finalizadas/poblacion/conteo";

    public static final String INDICADORES_PARA_ACTIVIDADES = "/actividades";
    public static final String INDICADORES_TODOS = "/todos";

    private ApiEndpointConstante() {
        throw new UnsupportedOperationException(NO_SE_PUEDE_INSTANCIAR_UNA_CLASE_UTILITARIA);
    }
}