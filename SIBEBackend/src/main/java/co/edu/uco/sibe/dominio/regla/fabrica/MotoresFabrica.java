package co.edu.uco.sibe.dominio.regla.fabrica;

import co.edu.uco.sibe.dominio.modelo.*;
import co.edu.uco.sibe.dominio.regla.fabrica.implementacion.*;
import co.edu.uco.sibe.dominio.regla.motor.MotorRegla;

public final class MotoresFabrica {
    public static final MotorRegla<Accion> MOTOR_ACCION;
    public static final MotorRegla<Actividad> MOTOR_ACTIVIDAD;
    public static final MotorRegla<Area> MOTOR_AREA;
    public static final MotorRegla<CentroCostos> MOTOR_CENTRO_COSTOS;
    public static final MotorRegla<CiudadResidencia> MOTOR_CIUDAD_RESIDENCIA;
    public static final MotorRegla<Direccion> MOTOR_DIRECCION;
    public static final MotorRegla<EjecucionActividad> MOTOR_EJECUCION_ACTIVIDAD;
    public static final MotorRegla<Empleado> MOTOR_EMPLEADO;
    public static final MotorRegla<EstadoActividad> MOTOR_ESTADO_ACTIVIDAD;
    public static final MotorRegla<Estudiante> MOTOR_ESTUDIANTE;
    public static final MotorRegla<Externo> MOTOR_EXTERNO;
    public static final MotorRegla<Identificacion> MOTOR_IDENTIFICACION;
    public static final MotorRegla<Indicador> MOTOR_INDICADOR;
    public static final MotorRegla<ParticipanteEmpleado> MOTOR_PARTICIPANTE_EMPLEADO;
    public static final MotorRegla<ParticipanteEstudiante> MOTOR_PARTICIPANTE_ESTUDIANTE;
    public static final MotorRegla<ParticipanteExterno> MOTOR_PARTICIPANTE_EXTERNO;
    public static final MotorRegla<Persona> MOTOR_PERSONA;
    public static final MotorRegla<Proyecto> MOTOR_PROYECTO;
    public static final MotorRegla<PublicoInteres> MOTOR_PUBLICO_INTERES;
    public static final MotorRegla<RegistroAsistencia> MOTOR_REGISTRO_ASISTENCIA;
    public static final MotorRegla<RelacionLaboral> MOTOR_RELACION_LABORAL;
    public static final MotorRegla<Subarea> MOTOR_SUBAREA;
    public static final MotorRegla<Temporalidad> MOTOR_TEMPORALIDAD;
    public static final MotorRegla<TipoIdentificacion> MOTOR_TIPO_IDENTIFICACION;
    public static final MotorRegla<TipoIndicador> MOTOR_TIPO_INDICADOR;
    public static final MotorRegla<TipoUsuario> MOTOR_TIPO_USUARIO;
    public static final MotorRegla<Usuario> MOTOR_USUARIO;
    public static final MotorRegla<UsuarioOrganizacion> MOTOR_USUARIO_ORGANIZACION;

    static {
        MOTOR_ACCION = AccionMotorFabrica.obtenerInstancia().obtenerMotorReglas();
        MOTOR_ACTIVIDAD = ActividadMotorFabrica.obtenerInstancia().obtenerMotorReglas();
        MOTOR_AREA = AreaMotorFabrica.obtenerInstancia().obtenerMotorReglas();
        MOTOR_CENTRO_COSTOS = CentroCostosMotorFabrica.obtenerInstancia().obtenerMotorReglas();
        MOTOR_CIUDAD_RESIDENCIA = CiudadResidenciaMotorFabrica.obtenerInstancia().obtenerMotorReglas();
        MOTOR_DIRECCION = DireccionMotorFabrica.obtenerInstancia().obtenerMotorReglas();
        MOTOR_EJECUCION_ACTIVIDAD = EjecucionActividadMotorFabrica.obtenerInstancia().obtenerMotorReglas();
        MOTOR_EMPLEADO = EmpleadoMotorFabrica.obtenerInstancia().obtenerMotorReglas();
        MOTOR_ESTADO_ACTIVIDAD = EstadoActividadMotorFabrica.obtenerInstancia().obtenerMotorReglas();
        MOTOR_ESTUDIANTE = EstudianteMotorFabrica.obtenerInstancia().obtenerMotorReglas();
        MOTOR_EXTERNO = ExternoMotorFabrica.obtenerInstancia().obtenerMotorReglas();
        MOTOR_IDENTIFICACION = IdentificacionMotorFabrica.obtenerInstancia().obtenerMotorReglas();
        MOTOR_INDICADOR = IndicadorMotorFabrica.obtenerInstancia().obtenerMotorReglas();
        MOTOR_PARTICIPANTE_EMPLEADO = ParticipanteEmpleadoMotorFabrica.obtenerInstancia().obtenerMotorReglas();
        MOTOR_PARTICIPANTE_ESTUDIANTE = ParticipanteEstudianteMotorFabrica.obtenerInstancia().obtenerMotorReglas();
        MOTOR_PARTICIPANTE_EXTERNO = ParticipanteExternoMotorFabrica.obtenerInstancia().obtenerMotorReglas();
        MOTOR_PERSONA = PersonaMotorFabrica.obtenerInstancia().obtenerMotorReglas();
        MOTOR_PROYECTO = ProyectoMotorFabrica.obtenerInstancia().obtenerMotorReglas();
        MOTOR_PUBLICO_INTERES = PublicoInteresMotorFabrica.obtenerInstancia().obtenerMotorReglas();
        MOTOR_REGISTRO_ASISTENCIA = RegistroAsistenciaMotorFabrica.obtenerInstancia().obtenerMotorReglas();
        MOTOR_RELACION_LABORAL = RelacionLaboralMotorFabrica.obtenerInstancia().obtenerMotorReglas();
        MOTOR_SUBAREA = SubareaMotorFabrica.obtenerInstancia().obtenerMotorReglas();
        MOTOR_TEMPORALIDAD = TemporalidadMotorFabrica.obtenerInstancia().obtenerMotorReglas();
        MOTOR_TIPO_IDENTIFICACION = TipoIdentificacionMotorFabrica.obtenerInstancia().obtenerMotorReglas();
        MOTOR_TIPO_INDICADOR = TipoIndicadorMotorFabrica.obtenerInstancia().obtenerMotorReglas();
        MOTOR_TIPO_USUARIO = TipoUsuarioMotorFabrica.obtenerInstancia().obtenerMotorReglas();
        MOTOR_USUARIO = UsuarioMotorFabrica.obtenerInstancia().obtenerMotorReglas();
        MOTOR_USUARIO_ORGANIZACION = UsuarioOrganizacionMotorFabrica.obtenerInstancia().obtenerMotorReglas();
    }

    private MotoresFabrica() { }
}