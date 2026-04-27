package co.edu.uco.sibe.dominio.transversal.constante;

import java.util.Set;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.NO_SE_PUEDE_INSTANCIAR_UNA_CLASE_UTILITARIA;

public final class DatoConstante {
    public static final String SIGLA_CC = "CC";
    public static final String DESCRIPCION_CC = "Cédula de Ciudadanía";
    public static final String SIGLA_TI = "TI";
    public static final String DESCRIPCION_TI = "Tarjeta de Identidad";
    public static final String SIGLA_CE = "CE";
    public static final String DESCRIPCION_CE = "Cédula de Extranjería";

    public static final String NATURALEZA_EFICIENCIA = "Eficiencia";
    public static final String TIPOLOGIA_EFICIENCIA = "Gestión";
    public static final String NATURALEZA_CAPACIDAD_INSTALADA = "Capacidad instalada";
    public static final String TIPOLOGIA_CAPACIDAD_INSTALADA = "Capacidad";
    public static final String NATURALEZA_EFICACIA = "Eficacia";
    public static final String TIPOLOGIA_EFICACIA = "Resultado";
    public static final String NATURALEZA_EFECTIVIDAD = "Efectividad";
    public static final String TIPOLOGIA_EFECTIVIDAD = "Resultado";
    public static final String NATURALEZA_VALOR = "Valor";
    public static final String TIPOLOGIA_VALOR = "Impacto";

    public static final String PUBLICO_INTERES_REGISTROS_CALIFICADOS_PROGRAMA = "Registros calificados programa";
    public static final String PUBLICO_INTERES_ACREDITACION_INSTITUCIONAL = "Acreditación institucional";
    public static final String PUBLICO_INTERES_CERTIFICACIONES_ISO = "Certificaciones ISO";
    public static final String PUBLICO_INTERES_MINISTERIO_DE_EDUCACION = "Ministerio de Educación";
    public static final String PUBLICO_INTERES_PLAN_PASTORAL_DIOSESANO = "Plan Pastoral Diosesano";

    public static final String NOMBRE_AREA_BIENESTAR = "Bienestar";
    public static final String NOMBRE_AREA_EVANGELIZACION = "Evangelización";
    public static final String NOMBRE_AREA_HOGAR_JUVENIL = "Hogar Juvenil Santa María";
    public static final String NOMBRE_AREA_SERVICIO_USUARIO = "Servicio y atención al usuario";
    public static final String NOMBRE_DIRECCION_BIENESTAR_EVANGELIZACION = "Dirección de Bienestar y Evangelización";

    public static final String NOMBRE_SUB_AREA_DEPORTERS = "Deportes";
    public static final String NOMBRE_SUB_AREA_CANCHA = "Cancha sintética";
    public static final String NOMBRE_SUB_AREA_EXTENSION = "Extensión cultural";
    public static final String NOMBRE_SUB_AREA_BANDA = "Banda Sinfónica";
    public static final String NOMBRE_SUB_AREA_GIMNASIO = "Gimnasio";
    public static final String NOMBRE_SUB_AREA_UNIDAD_SALUD = "Unidad de Salud";
    public static final String NOMBRE_SUB_AREA_ACOMPANAMIENTO = "Acompañamiento psicosocial";
    public static final String NOMBRE_SUB_AREA_TRABAJO_SOCIAL = "Trabajo social";

    public static final String PENDIENTE = "Pendiente";
    public static final String EN_CURSO = "En curso";
    public static final String FINALIZADA = "Finalizada";

    public static final String TEMPORALIDAD_DIARIA = "Diaria";
    public static final String TEMPORALIDAD_SEMANAL = "Semanal";
    public static final String TEMPORALIDAD_MENSUAL = "Mensual";
    public static final String TEMPORALIDAD_TRIMESTRAL = "Trimestral";
    public static final String TEMPORALIDAD_SEMESTRAL = "Semestral";
    public static final String TEMPORALIDAD_ANUAL = "Anual";

    public static final String DESCRIPCION_ADMINISTRADOR_DIRECCION = "Administrador de dirección";
    public static final String DESCRIPCION_ADMINISTRADOR_AREA = "Administrador de Area";
    public static final String DESCRIPCION_COLABORADOR = "Colaborador";

    public static final String NUMERO_ID_ADMIN = "1111111111";
    public static final String NOMBRE_ADMIN = "Administrador";
    public static final String APELLIDO_ADMIN = "UCO";
    public static final String EMAIL_ADMIN = "administrador@uco.net.co";
    public static final String CLAVE_ADMIN = "Administrador123";

    public static final String ESTUDIANTE = "Estudiante";
    public static final String EMPLEADO = "Empleado";

    public static final String PREGRADO = "Pregrado";
    public static final String POSTGRADO = "Postgrado";

    public static final Set<String> PALABRAS_POSTRGRADO = Set.of("ESPECIALIZACION", "MAESTRIA", "DOCTORADO");

    public static final String FORMATO_MES_ANIO_PATTERN = "MMMM yyyy";
    public static final String FORMATO_MES_PATTERN = "MMMM";
    public static final String IDIOMA_ES = "es";
    public static final String PAIS_CO = "CO";

    private DatoConstante() {
        throw new UnsupportedOperationException(NO_SE_PUEDE_INSTANCIAR_UNA_CLASE_UTILITARIA);
    }
}