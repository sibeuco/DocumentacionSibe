package co.edu.uco.sibe.dominio.transversal.constante;

import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.NO_SE_PUEDE_INSTANCIAR_UNA_CLASE_UTILITARIA;

public final class PersistenciaConstante {
    public static final String TABLA_ACCION = "accion";
    public static final String TABLA_ACTIVIDAD = "actividad";
    public static final String TABLA_AREA = "area";
    public static final String TABLA_CENTRO_COSTOS = "centro_costos";
    public static final String TABLA_CIUDAD_RESIDENCIA = "ciudad_residencia";
    public static final String TABLA_DIRECCION = "direccion";
    public static final String TABLA_EJECUCION_ACTIVIDAD = "ejecucion_actividad";
    public static final String TABLA_EMPLEADO = "empleado";
    public static final String TABLA_ESTADO_ACTIVIDAD = "estado_actividad";
    public static final String PETICION_RECUPERACION_CLAVE = "peticion_recuperacion_clave";
    public static final String PERSONA = "persona";
    public static final String PUBLICO_INTERES = "publico_interes";
    public static final String PROYECTO = "proyecto";
    public static final String PARTICIPANTE_INTERNO = "participante_interno";
    public static final String PARTICIPANTE_EXTERNO = "participante_externo";
    public static final String PARTICIPANTE_EMPLEADO = "participante_empleado";
    public static final String PARTICIPANTE_ESTUDIANTE = "participante_estudiante";
    public static final String PROGRAMA_ACADEMICO = "programa_academico";
    public static final String FACULTAD = "facultad";
    public static final String ESTADO_ACADEMICO = "estado_academico";
    public static final String MODALIDAD_ESTUDIO = "modalidad_estudio";
    public static final String MEDIO_TRANSPORTE = "medio_transporte";
    public static final String ESTUDIANTE = "estudiante";
    public static final String REGISTRO_ASISTENCIA = "registro_asistencia";
    public static final String RELACION_LABORAL = "relacion_laboral";
    public static final String TIPO_IDENTIFICACION = "tipo_identificacion";
    public static final String TEMPORALIDAD = "temporalidad";
    public static final String TIPO_INDICADOR = "tipo_indicador";
    public static final String NATURALEZA = "naturaleza";
    public static final String TIPOLOGIA = "tipologia";
    public static final String SUB_AREA = "subarea";
    public static final String TIPO_USUARIO = "tipo_usuario";
    public static final String MIEMBRO = "miembro";
    public static final String INTERNO = "interno";
    public static final String EXTERNO = "externo";
    public static final String PARTICIPANTE = "participante";
    public static final String USUARIO = "usuario";

    public static final String CAMPO_IDENTIFICADOR = "identificador";
    public static final String CAMPO_DETALLE = "detalle";
    public static final String CAMPO_OBJETIVO = "objetivo";
    public static final String CAMPO_NOMBRE = "nombre";
    public static final String CAMPO_SEMESTRE = "semestre";
    public static final String CAMPO_RUTA_INSUMOS = "ruta_insumos";
    public static final String CAMPO_FECHA_CREACION = "fecha_creacion";
    public static final String CAMPO_INDICADOR = "indicador";
    public static final String CAMPO_COLABORADOR = "colaborador";
    public static final String CAMPO_CREADOR = "creador";
    public static final String CAMPO_AREA = "area_id";
    public static final String CAMPO_ACTIVIDAD = "actividad";
    public static final String CAMPO_DIRECCION = "direccion_id";
    public static final String CAMPO_FECHA_PROGRAMADA = "fecha_programada";
    public static final String CAMPO_FECHA_REALIZACION = "fecha_realizacion";
    public static final String CAMPO_HORA_INICIO = "hora_inicio";
    public static final String CAMPO_HORA_FIN = "hora_fin";
    public static final String CAMPO_EJECUCION_ACTIVIDAD = "ejecucion_actividad";
    public static final String CAMPO_ACTIVIDAD_ID = "actividad_id";
    public static final String CAMPO_ESTADO_ACTIVIDAD = "estado_actividad";
    public static final String CAMPO_CENTRO_COSTOS = "centro_costos";
    public static final String CAMPO_RELACION_LABORAL = "relacion_laboral";
    public static final String ESTA_ACTIVO = "esta_activo";
    public static final String NUMERO_PROYECTO = "numero_proyecto";
    public static final String ACCION = "accion";
    public static final String IDENTIFICACION = "identificacion";
    public static final String CIUDAD_RESIDENCIA = "ciudad_residencia";
    public static final String ID_CARNET = "id_carnet";
    public static final String SEXO = "sexo";
    public static final String ESTADO_CIVIL = "estado_civil";
    public static final String ANNO_INGRESO = "anno_ingreso";
    public static final String SEMESTRE_ACTUAL = "semestre_actual";
    public static final String CREDITOS_APROBADOS = "creditos_aprobados";
    public static final String PROMEDIO_GENERAL = "promedio_general";
    public static final String TIEMPO_LLEGADA_UNIVERSIDAD = "tiempo_llegada_universidad";
    public static final String NOMBRE_COMPLETO = "nombre_completo";
    public static final String NUMERO_IDENTIFICACION = "numero_identificacion";
    public static final String FECHA_NACIMIENTO = "fecha_nacimiento";
    public static final String NACIONALIDAD = "nacionalidad";
    public static final String CORREO_PERSONAL = "correo_personal";
    public static final String CORREO_INSTITUCIONAL = "correo_institucional";

    public static final String AREA_ACTIVIDAD = "area_actividad";
    public static final String DIRECCION_ACTIVIDAD = "direccion_actividad";
    public static final String TABLA_EJECUCION_ACTIVIDAD_ESTADO_ACTIVIDAD = "ejecucion_actividad_estado_actividad";
    public static final String EMPLEADO_CENTRO_COSTOS = "empleado_centro_costos";
    public static final String TABLA_EMPLEADO_RELACION_LABORAL = "empleado_relacion_laboral";
    public static final String TABLA_USUARIO_TIPO_USUARIO = "usuario_tipo_usuario";
    public static final String USUARIO_ORGANIZACION = "usuario_organizacion";
    public static final String SUB_AREA_ACTIVIDAD = "subarea_actividad";
    public static final String PROYECTO_ACCION = "proyecto_accion";
    public static final String INTERNO_CIUDAD_RESIDENCIA = "interno_ciudad_residencia";
    public static final String INDICADOR_TIPO_INDICADOR = "indicador_tipo_indicador";
    public static final String INDICADOR_TEMPORALIDAD = "indicador_temporalidad";
    public static final String INDICADOR_PUBLICO_INTERES = "indicador_publico_interes";
    public static final String INDICADOR_PROYECTO = "indicador_proyecto";
    public static final String IDENTIFICACION_TIPO_IDENTIFICACION = "identificacion_tipo_identificacion";

    public static final String MAPEADOR_ESTUDIANTE_COMPONENTE = "mapeadorEstudiante";
    public static final String MAPEADOR_EMPLEADO_COMPONENTE = "mapeadorEmpleado";

    public static final String CONSULTAR_PARTICIPANTE_POR_NUMERO_IDENTIFICACION = "SELECT p FROM ParticipanteEntidad p WHERE p.miembro.numeroIdentificacion = :documento AND p.miembro.estaActivo = true";
    public static final String CONSULTAR_PARTICIPANTE_POR_DOCUMENTO_Y_SEMESTRE = "SELECT DISTINCT ra.participante FROM RegistroAsistenciaEntidad ra WHERE ra.participante.miembro.numeroIdentificacion = :documento AND ra.ejecucionActividad.actividad.semestre = :semestre AND ra.participante.miembro.estaActivo = true";
    public static final String CONSULTAR_PARTICIPANTES_POR_EJECUCION_ACTIVIDAD = "SELECT DISTINCT p FROM RegistroAsistenciaEntidad ra JOIN ra.participante p JOIN FETCH p.miembro m WHERE ra.ejecucionActividad.identificador = :ejecucionActividadId";
    public static final String CONSULTAR_FECHAS_REALIZACION_POR_ESTADO = "SELECT ea.fechaRealizacion FROM EjecucionActividadEntidad ea WHERE ea.estadoActividad.estadoActividad.nombre = :estado AND ea.fechaRealizacion IS NOT NULL ORDER BY ea.fechaRealizacion DESC";
    public static final String CONSULTAR_SEMESTRES_ACTIVIDADES_FINALIZADAS = "SELECT DISTINCT CONCAT(YEAR(ea.fechaRealizacion), CASE WHEN MONTH(ea.fechaRealizacion) <= 6 THEN '01' ELSE '02' END) FROM EjecucionActividadEntidad ea WHERE ea.estadoActividad.estadoActividad.nombre = :estado AND ea.fechaRealizacion IS NOT NULL ORDER BY 1 DESC";
    public static final String CONSULTAR_CENTROS_COSTOS_EMPLEADOS_FINALIZADOS = "SELECT DISTINCT cc.descripcion FROM RegistroAsistenciaEntidad ra JOIN ra.ejecucionActividad ea JOIN ParticipanteEmpleadoEntidad pe ON pe.identificador = ra.participante.identificador JOIN pe.centroCostos ecc JOIN ecc.centroCostos cc WHERE ea.estadoActividad.estadoActividad.nombre = :estado ORDER BY cc.descripcion ASC";
    public static final String CONSULTAR_TIPOS_PARTICIPANTES_FINALIZADOS = "SELECT DISTINCT CASE WHEN TYPE(p) = ParticipanteEstudianteEntidad THEN 'ESTUDIANTE' WHEN TYPE(p) = ParticipanteEmpleadoEntidad THEN 'EMPLEADO' WHEN TYPE(p) = ParticipanteExternoEntidad THEN 'EXTERNO' END FROM RegistroAsistenciaEntidad ra JOIN ra.ejecucionActividad ea JOIN ra.participante p WHERE ea.estadoActividad.estadoActividad.nombre = :estado ORDER BY 1 ASC";
    public static final String CONSULTAR_PROGRAMAS_ESTUDIANTES_FINALIZADOS = "SELECT DISTINCT pe.programaAcademico FROM RegistroAsistenciaEntidad ra JOIN ra.ejecucionActividad ea JOIN ParticipanteEstudianteEntidad pe ON pe.identificador = ra.participante.identificador WHERE ea.estadoActividad.estadoActividad.nombre = :estado ORDER BY pe.programaAcademico ASC";
    public static final String CONSULTAR_INDICADORES_EJECUCIONES_FINALIZADAS = "SELECT DISTINCT i.nombre FROM EjecucionActividadEntidad ea JOIN ea.actividad a JOIN a.indicador i WHERE ea.estadoActividad.estadoActividad.nombre = :estado ORDER BY i.nombre ASC";
    public static final String SELECT_COUNT_DISTINCT_PARTICIPANTE = "SELECT COUNT(DISTINCT p.identificador) ";
    public static final String SELECT_COUNT_ASISTENCIA = "SELECT COUNT(ra) ";
    public static final String SELECT_COUNT_DISTINCT_EJECUCION = "SELECT COUNT(DISTINCT ea.identificador) ";
    public static final String FROM_JOIN_BASE_ESTADISTICAS = "FROM RegistroAsistenciaEntidad ra JOIN ra.participante p JOIN ra.ejecucionActividad ea JOIN ea.actividad a LEFT JOIN ParticipanteEstudianteEntidad pe ON pe.identificador = p.identificador LEFT JOIN ParticipanteEmpleadoEntidad pem ON pem.identificador = p.identificador ";

    public static final String WHERE_ESTADO_ACTIVIDAD = "WHERE ea.estadoActividad.estadoActividad.nombre = :estado ";
    public static final String AND_ANNO = "AND YEAR(ea.fechaRealizacion) = :anno ";
    public static final String AND_MES_VALOR = "AND MONTH(ea.fechaRealizacion) = :mesValor ";
    public static final String AND_INDICADOR = "AND a.indicador.nombre = :indicador ";
    public static final String AND_TYPE_PARTICIPANTE = "AND TYPE(p) = :tipoEntidad ";
    public static final String AND_SEMESTRE = "AND CONCAT(YEAR(ea.fechaRealizacion), CASE WHEN MONTH(ea.fechaRealizacion) <= 6 THEN '01' ELSE '02' END) = :semestre ";
    public static final String AND_PROGRAMA = "AND pe.programaAcademico = :programa ";
    public static final String AND_CENTRO_COSTOS = "AND pem.centroCostos.centroCostos.descripcion = :centroCostos ";

    public static final String PARAM_ANNO = "anno";
    public static final String PARAM_MES_VALOR = "mesValor";
    public static final String PARAM_INDICADOR = "indicador";
    public static final String PARAM_TIPO_ENTIDAD = "tipoEntidad";
    public static final String PARAM_SEMESTRE = "semestre";
    public static final String PARAM_PROGRAMA = "programa";
    public static final String PARAM_CENTRO_COSTOS = "centroCostos";
    public static final String PARAM_ID_AREA = "idArea";

    public static final String AND_POSTGRADO = "AND (UPPER(pe.programaAcademico) LIKE '%ESPECIALIZACION%' OR UPPER(pe.programaAcademico) LIKE '%MAESTRIA%' OR UPPER(pe.programaAcademico) LIKE '%DOCTORADO%') ";
    public static final String AND_PREGRADO = "AND (UPPER(pe.programaAcademico) NOT LIKE '%ESPECIALIZACION%' AND UPPER(pe.programaAcademico) NOT LIKE '%MAESTRIA%' AND UPPER(pe.programaAcademico) NOT LIKE '%DOCTORADO%') ";
    public static final String AND_EXISTS_SUBAREA = "AND EXISTS (SELECT 1 FROM SubareaEntidad sub JOIN sub.actividades act WHERE sub.identificador = :idArea AND act.identificador = a.identificador) ";
    public static final String AND_EXISTS_AREA = "AND (EXISTS (SELECT 1 FROM AreaEntidad ar JOIN ar.actividades act WHERE ar.identificador = :idArea AND act.identificador = a.identificador) OR EXISTS (SELECT 1 FROM AreaEntidad ar JOIN ar.subareas sub JOIN sub.actividades act WHERE ar.identificador = :idArea AND act.identificador = a.identificador)) ";
    public static final String AND_EXISTS_DIRECCION = "AND (EXISTS (SELECT 1 FROM DireccionEntidad dir JOIN dir.actividades act WHERE dir.identificador = :idArea AND act.identificador = a.identificador) OR EXISTS (SELECT 1 FROM DireccionEntidad dir JOIN dir.areas ar JOIN ar.actividades act WHERE dir.identificador = :idArea AND act.identificador = a.identificador) OR EXISTS (SELECT 1 FROM DireccionEntidad dir JOIN dir.areas ar JOIN ar.subareas sub JOIN sub.actividades act WHERE dir.identificador = :idArea AND act.identificador = a.identificador)) ";

    public static final String DOCUMENTO_PARAMETRO = "documento";
    public static final String SEMESTRE_PARAMETRO = "semestre";
    public static final String EJECUCION_ACTIVIDAD_PARAMETRO = "ejecucionActividadId";
    public static final String ESTADO_PARAMETRO = "estado";

    private PersistenciaConstante() {
        throw new UnsupportedOperationException(NO_SE_PUEDE_INSTANCIAR_UNA_CLASE_UTILITARIA);
    }
}