package co.edu.uco.sibe.infraestructura.configuracion.bean;

import co.edu.uco.sibe.dominio.puerto.comando.*;
import co.edu.uco.sibe.dominio.puerto.consulta.*;
import co.edu.uco.sibe.dominio.puerto.servicio.EncriptarClaveServicio;
import co.edu.uco.sibe.dominio.puerto.servicio.EnviarCorreoElectronicoService;
import co.edu.uco.sibe.dominio.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UseCaseBeanTest {

    @Mock private AccionRepositorioComando accionRepositorioComando;
    @Mock private AccionRepositorioConsulta accionRepositorioConsulta;
    @Mock private ProyectoRepositorioComando proyectoRepositorioComando;
    @Mock private ProyectoRepositorioConsulta proyectoRepositorioConsulta;
    @Mock private PersonaRepositorioComando personaRepositorioComando;
    @Mock private PersonaRepositorioConsulta personaRepositorioConsulta;
    @Mock private EncriptarClaveServicio encriptarClaveServicio;
    @Mock private EnviarCorreoElectronicoService enviarCorreoElectronicoService;
    @Mock private VincularUsuarioConAreaService vincularUsuarioConAreaService;
    @Mock private ModificarVinculacionUsuarioConAreaService modificarVinculacionUsuarioConAreaService;
    @Mock private VincularActividadConAreaService vincularActividadConAreaService;
    @Mock private ModificarVinculacionActividadConAreaService modificarVinculacionActividadConAreaService;
    @Mock private AutorizacionContextoOrganizacionalServicio autorizacionServicio;
    @Mock private RegistrarParticipanteService registrarParticipanteService;
    @Mock private TipoUsuarioRepositorioComando tipoUsuarioRepositorioComando;
    @Mock private TipoUsuarioRepositorioConsulta tipoUsuarioRepositorioConsulta;
    @Mock private TipoIdentificacionRepositorioComando tipoIdentificacionRepositorioComando;
    @Mock private TipoIdentificacionRepositorioConsulta tipoIdentificacionRepositorioConsulta;
    @Mock private SubareaRepositorioComando subareaRepositorioComando;
    @Mock private SubareaRepositorioConsulta subareaRepositorioConsulta;
    @Mock private AreaRepositorioComando areaRepositorioComando;
    @Mock private AreaRepositorioConsulta areaRepositorioConsulta;
    @Mock private DireccionRepositorioComando direccionRepositorioComando;
    @Mock private DireccionRepositorioConsulta direccionRepositorioConsulta;
    @Mock private EstadoActividadRepositorioComando estadoActividadRepositorioComando;
    @Mock private EstadoActividadRepositorioConsulta estadoActividadRepositorioConsulta;
    @Mock private TemporalidadRepositorioComando temporalidadRepositorioComando;
    @Mock private TemporalidadRepositorioConsulta temporalidadRepositorioConsulta;
    @Mock private ActividadRepositorioComando actividadRepositorioComando;
    @Mock private ActividadRepositorioConsulta actividadRepositorioConsulta;
    @Mock private IndicadorRepositorioComando indicadorRepositorioComando;
    @Mock private IndicadorRepositorioConsulta indicadorRepositorioConsulta;
    @Mock private PublicoInteresRepositorioComando publicoInteresRepositorioComando;
    @Mock private PublicoInteresRepositorioConsulta publicoInteresRepositorioConsulta;
    @Mock private TipoIndicadorRepositorioComando tipoIndicadorRepositorioComando;
    @Mock private TipoIndicadorRepositorioConsulta tipoIndicadorRepositorioConsulta;
    @Mock private MiembroRepositorioConsulta miembroRepositorioConsulta;
    @Mock private EmpleadoRepositorioComando empleadoRepositorioComando;
    @Mock private EmpleadoRepositorioConsulta empleadoRepositorioConsulta;
    @Mock private EstudianteRepositorioComando estudianteRepositorioComando;
    @Mock private EstudianteRepositorioConsulta estudianteRepositorioConsulta;
    @Mock private MiembroRepositorioComando miembroRepositorioComando;
    @Mock private RegistroAsistenciaRepositorioComando registroAsistenciaRepositorioComando;
    @Mock private RegistroAsistenciaRepositorioConsulta registroAsistenciaRepositorioConsulta;

    private UseCaseBean useCaseBean;

    @BeforeEach
    void setUp() {
        useCaseBean = new UseCaseBean();
    }

    @Test
    void deberiaCrearGuardarAccionUseCase() {
        assertNotNull(useCaseBean.agregarNuevaAccionUseCase(accionRepositorioComando, accionRepositorioConsulta));
    }

    @Test
    void deberiaCrearGuardarProyectoUseCase() {
        assertNotNull(useCaseBean.agregarNuevoProyectoUseCase(proyectoRepositorioComando, proyectoRepositorioConsulta));
    }

    @Test
    void deberiaCrearGuardarUsuarioUseCase() {
        assertNotNull(useCaseBean.agregarNuevoUsuarioUseCase(personaRepositorioComando, personaRepositorioConsulta,
                encriptarClaveServicio, vincularUsuarioConAreaService, autorizacionServicio,
                modificarVinculacionUsuarioConAreaService));
    }

    @Test
    void deberiaCrearEliminarUsuarioUseCase() {
        assertNotNull(useCaseBean.eliminarUsuarioUseCase(personaRepositorioComando, personaRepositorioConsulta, autorizacionServicio));
    }

    @Test
    void deberiaCrearModificarUsuarioUseCase() {
        assertNotNull(useCaseBean.modificarUsuarioUseCase(personaRepositorioComando, personaRepositorioConsulta,
                modificarVinculacionUsuarioConAreaService, autorizacionServicio));
    }

    @Test
    void deberiaCrearModificarContrasenaUseCase() {
        assertNotNull(useCaseBean.modificarContrasenaUseCase(personaRepositorioComando, personaRepositorioConsulta));
    }

    @Test
    void deberiaCrearLoginUseCase() {
        assertNotNull(useCaseBean.loginUseCase(personaRepositorioConsulta, encriptarClaveServicio));
    }

    @Test
    void deberiaCrearConsultarUsuarioPorIdentificadorUseCase() {
        assertNotNull(useCaseBean.consultarUsuarioPorIdentificadorUseCase(personaRepositorioConsulta, autorizacionServicio));
    }

    @Test
    void deberiaCrearConsultarUsuarioPorCorreoUseCase() {
        assertNotNull(useCaseBean.consultarUsuarioPorCorreoUseCase(personaRepositorioConsulta));
    }

    @Test
    void deberiaCrearConsultarUsuariosUseCase() {
        assertNotNull(useCaseBean.consultarUsuariosUseCase(personaRepositorioConsulta, autorizacionServicio, areaRepositorioConsulta));
    }

    @Test
    void deberiaCrearConsultarDireccionPorNombreUseCase() {
        assertNotNull(useCaseBean.consultarDireccionPorNombreUseCase(direccionRepositorioConsulta, autorizacionServicio));
    }

    @Test
    void deberiaCrearConsultarTipoIdentificacionPorSiglaUseCase() {
        assertNotNull(useCaseBean.consultarTipoIdentificacionPorSiglaUseCase(tipoIdentificacionRepositorioConsulta));
    }

    @Test
    void deberiaCrearConsultarTipoUsuarioPorCodigoUseCase() {
        assertNotNull(useCaseBean.consultarTipoUsuarioPorCodigoUseCase(tipoUsuarioRepositorioConsulta));
    }

    @Test
    void deberiaCrearGuardarTipoUsuarioUseCase() {
        assertNotNull(useCaseBean.guardarTipoUsuarioUseCase(tipoUsuarioRepositorioComando, tipoUsuarioRepositorioConsulta));
    }

    @Test
    void deberiaCrearGuardarTipoIdentificacionUseCase() {
        assertNotNull(useCaseBean.guardarTipoIdentificacionUseCase(tipoIdentificacionRepositorioComando, tipoIdentificacionRepositorioConsulta));
    }

    @Test
    void deberiaCrearGuardarSubareaUseCase() {
        assertNotNull(useCaseBean.guardarSubareaUseCase(subareaRepositorioComando, subareaRepositorioConsulta));
    }

    @Test
    void deberiaCrearGuardarAreaUseCase() {
        assertNotNull(useCaseBean.guardarAreaUseCase(areaRepositorioComando, areaRepositorioConsulta));
    }

    @Test
    void deberiaCrearGuardarDireccionUseCase() {
        assertNotNull(useCaseBean.guardarDireccionUseCase(direccionRepositorioComando, direccionRepositorioConsulta));
    }

    @Test
    void deberiaCrearGuardarEstadoActividadUseCase() {
        assertNotNull(useCaseBean.guardarEstadoActividadUseCase(estadoActividadRepositorioComando, estadoActividadRepositorioConsulta));
    }

    @Test
    void deberiaCrearGuardarTemporalidadUseCase() {
        assertNotNull(useCaseBean.guardarTemporalidadUseCase(temporalidadRepositorioComando, temporalidadRepositorioConsulta));
    }

    @Test
    void deberiaCrearSolicitarCodigoUseCase() {
        assertNotNull(useCaseBean.solicitarCodigoUseCase(personaRepositorioConsulta, personaRepositorioComando,
                encriptarClaveServicio, enviarCorreoElectronicoService));
    }

    @Test
    void deberiaCrearValidarCodigoRecuperacionClaveUseCase() {
        assertNotNull(useCaseBean.validarCodigoRecuperacionClaveUseCase(personaRepositorioConsulta, personaRepositorioComando, encriptarClaveServicio));
    }

    @Test
    void deberiaCrearRecuperarClaveUseCase() {
        assertNotNull(useCaseBean.recuperarClaveUseCase(personaRepositorioComando, personaRepositorioConsulta, encriptarClaveServicio));
    }

    @Test
    void deberiaCrearModificarClaveUseCase() {
        assertNotNull(useCaseBean.modificarClaveUseCase(personaRepositorioComando, personaRepositorioConsulta, encriptarClaveServicio, autorizacionServicio));
    }

    @Test
    void deberiaCrearCargarMasivamenteEmpleadosUseCase() {
        assertNotNull(useCaseBean.cargarMasivamenteEmpleadosUseCase(empleadoRepositorioComando, empleadoRepositorioConsulta, miembroRepositorioComando));
    }

    @Test
    void deberiaCrearCargarMasivamenteEstudiantesUseCase() {
        assertNotNull(useCaseBean.cargarMasivamenteEstudiantesUseCase(estudianteRepositorioComando, estudianteRepositorioConsulta, miembroRepositorioComando));
    }

    @Test
    void deberiaCrearModificarAccionUseCase() {
        assertNotNull(useCaseBean.modificarAccionUseCase(accionRepositorioComando, accionRepositorioConsulta));
    }

    @Test
    void deberiaCrearModificarProyectoUseCase() {
        assertNotNull(useCaseBean.modificarProyectoUseCase(proyectoRepositorioComando, proyectoRepositorioConsulta));
    }

    @Test
    void deberiaCrearGuardarIndicadorUseCase() {
        assertNotNull(useCaseBean.agregarNuevoIndicadorUseCase(indicadorRepositorioComando, indicadorRepositorioConsulta));
    }

    @Test
    void deberiaCrearModificarIndicadorUseCase() {
        assertNotNull(useCaseBean.modificarIndicadorUseCase(indicadorRepositorioComando, indicadorRepositorioConsulta));
    }

    @Test
    void deberiaCrearGuardarPublicoInteresUseCase() {
        assertNotNull(useCaseBean.guardarPublicoInteresUseCase(publicoInteresRepositorioComando, publicoInteresRepositorioConsulta));
    }

    @Test
    void deberiaCrearGuardarTipoIndicadorUseCase() {
        assertNotNull(useCaseBean.guardarTipoIndicadorUseCase(tipoIndicadorRepositorioComando, tipoIndicadorRepositorioConsulta));
    }

    @Test
    void deberiaCrearGuardarActividadUseCase() {
        assertNotNull(useCaseBean.guardarActividadUseCase(actividadRepositorioComando, actividadRepositorioConsulta,
                vincularActividadConAreaService, autorizacionServicio));
    }

    @Test
    void deberiaCrearModificarActividadUseCase() {
        assertNotNull(useCaseBean.modificarActividadUseCase(actividadRepositorioComando, actividadRepositorioConsulta,
                modificarVinculacionActividadConAreaService, autorizacionServicio));
    }

    @Test
    void deberiaCrearConsultarActividadesPorAreaUseCase() {
        assertNotNull(useCaseBean.consultarActividadesPorAreaUseCase(actividadRepositorioConsulta, areaRepositorioConsulta, autorizacionServicio));
    }

    @Test
    void deberiaCrearConsultarActividadesPorDireccionUseCase() {
        assertNotNull(useCaseBean.consultarActividadesPorDireccionUseCase(actividadRepositorioConsulta, direccionRepositorioConsulta, autorizacionServicio));
    }

    @Test
    void deberiaCrearConsultarActividadesPorSubareaUseCase() {
        assertNotNull(useCaseBean.consultarActividadesPorSubareaUseCase(actividadRepositorioConsulta, subareaRepositorioConsulta, autorizacionServicio));
    }

    @Test
    void deberiaCrearConsultarEjecucionesPorActividadUseCase() {
        assertNotNull(useCaseBean.consultarEjecucionesPorActividadUseCase(actividadRepositorioConsulta, autorizacionServicio));
    }

    @Test
    void deberiaCrearConsultarMiembroPorIdentificacionUseCase() {
        assertNotNull(useCaseBean.consultarMiembroPorIdentificacionUseCase(miembroRepositorioConsulta));
    }

    @Test
    void deberiaCrearConsultarMiembroPorIdCarnetUseCase() {
        assertNotNull(useCaseBean.consultarMiembroPorIdCarnetUseCase(miembroRepositorioConsulta));
    }

    @Test
    void deberiaCrearIniciarActividadUseCase() {
        assertNotNull(useCaseBean.iniciarActividadUseCase(actividadRepositorioComando, actividadRepositorioConsulta,
                estadoActividadRepositorioConsulta, autorizacionServicio));
    }

    @Test
    void deberiaCrearCancelarActividadUseCase() {
        assertNotNull(useCaseBean.cancelarActividadUseCase(actividadRepositorioComando, actividadRepositorioConsulta,
                estadoActividadRepositorioConsulta, autorizacionServicio));
    }

    @Test
    void deberiaCrearFinalizarActividadUseCase() {
        assertNotNull(useCaseBean.finalizarActividadUseCase(actividadRepositorioComando, actividadRepositorioConsulta,
                estadoActividadRepositorioConsulta, registrarParticipanteService,
                registroAsistenciaRepositorioComando, registroAsistenciaRepositorioConsulta, autorizacionServicio));
    }

    @Test
    void deberiaCrearConsultarDireccionDetalladaUseCase() {
        assertNotNull(useCaseBean.consultarDireccionDetalladaUseCase(direccionRepositorioConsulta, autorizacionServicio));
    }

    @Test
    void deberiaCrearConsultarAreaDetalladaUseCase() {
        assertNotNull(useCaseBean.consultarAreaDetalladaUseCase(areaRepositorioConsulta, autorizacionServicio));
    }

    @Test
    void deberiaCrearConsultarSubareaDetalladaUseCase() {
        assertNotNull(useCaseBean.consultarSubareaDetalladaUseCase(subareaRepositorioConsulta, autorizacionServicio));
    }

    @Test
    void deberiaCrearConsultarAreaPorNombreDTOUseCase() {
        assertNotNull(useCaseBean.consultarAreaPorNombreDTOUseCase(areaRepositorioConsulta, autorizacionServicio));
    }

    @Test
    void deberiaCrearConsultarDireccionPorNombreDTOUseCase() {
        assertNotNull(useCaseBean.consultarDireccionPorNombreDTOUseCase(direccionRepositorioConsulta, autorizacionServicio));
    }

    @Test
    void deberiaCrearConsultarSubareaPorNombreDTOUseCase() {
        assertNotNull(useCaseBean.consultarSubareaPorNombreDTOUseCase(subareaRepositorioConsulta, autorizacionServicio));
    }

    @Test
    void deberiaCrearConsultarParticipantesPorEjecucionActividadUseCase() {
        assertNotNull(useCaseBean.consultarParticipantesPorEjecucionActividadUseCase(actividadRepositorioConsulta, autorizacionServicio));
    }

    @Test
    void deberiaCrearConsultarMesesEjecucionesFinalizadasUseCase() {
        assertNotNull(useCaseBean.consultarMesesEjecucionesFinalizadasUseCase(actividadRepositorioConsulta));
    }

    @Test
    void deberiaCrearConsultarAnnosEjecucionesFinalizadasUseCase() {
        assertNotNull(useCaseBean.consultarAnnosEjecucionesFinalizadasUseCase(actividadRepositorioConsulta));
    }

    @Test
    void deberiaCrearConsultarSemestresActividadesEnEjecucionesFinalizadasUseCase() {
        assertNotNull(useCaseBean.consultarSemestresEstudiantesEnEjecucionesFinalizadasUseCase(actividadRepositorioConsulta));
    }

    @Test
    void deberiaCrearConsultarCentrosCostosEmpleadosEnEjecucionesFinalizadasUseCase() {
        assertNotNull(useCaseBean.consultarCentrosCostosEmpleadosEnEjecucionesFinalizadasUseCase(actividadRepositorioConsulta));
    }

    @Test
    void deberiaCrearConsultarTiposParticipantesEnEjecucionesFinalizadasUseCase() {
        assertNotNull(useCaseBean.consultarTiposParticipantesEnEjecucionesFinalizadasUseCase(actividadRepositorioConsulta));
    }

    @Test
    void deberiaCrearConsultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadasUseCase() {
        assertNotNull(useCaseBean.consultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadasUseCase(actividadRepositorioConsulta));
    }

    @Test
    void deberiaCrearConsultarNivelesFormacionEstudiantesEnEjecucionesFinalizadasUseCase() {
        assertNotNull(useCaseBean.consultarNivelesFormacionEstudiantesEnEjecucionesFinalizadasUseCase(actividadRepositorioConsulta));
    }

    @Test
    void deberiaCrearConsultarIndicadoresEnEjecucionesFinalizadasUseCase() {
        assertNotNull(useCaseBean.consultarIndicadoresEnEjecucionesFinalizadasUseCase(actividadRepositorioConsulta));
    }

    @Test
    void deberiaCrearContarParticipantesTotalesUseCase() {
        assertNotNull(useCaseBean.contarParticipantesTotalesUseCase(actividadRepositorioConsulta, autorizacionServicio));
    }

    @Test
    void deberiaCrearContarAsistenciasTotalesUseCase() {
        assertNotNull(useCaseBean.contarAsistenciasTotalesUseCase(actividadRepositorioConsulta, autorizacionServicio));
    }

    @Test
    void deberiaCrearContarEjecucionesTotalesUseCase() {
        assertNotNull(useCaseBean.contarEjecucionesTotalesUseCase(actividadRepositorioConsulta, autorizacionServicio));
    }

    @Test
    void deberiaCrearConsultarEstadisticasParticipantesPorEstructuraUseCase() {
        assertNotNull(useCaseBean.consultarEstadisticasParticipantesPorEstructuraUseCase(actividadRepositorioConsulta, autorizacionServicio));
    }

    @Test
    void deberiaCrearConsultarEstadisticasParticipantesPorMesUseCase() {
        assertNotNull(useCaseBean.consultarEstadisticasParticipantesPorMesUseCase(actividadRepositorioConsulta, autorizacionServicio));
    }

    @Test
    void deberiaCrearConsultarIndicadoresParaActividadesUseCase() {
        assertNotNull(useCaseBean.consultarIndicadoresParaActividadesUseCase(indicadorRepositorioConsulta));
    }

    @Test
    void deberiaCrearContarPoblacionTotalUseCase() {
        assertNotNull(useCaseBean.contarPoblacionTotalUseCase(actividadRepositorioConsulta, autorizacionServicio));
    }
}
