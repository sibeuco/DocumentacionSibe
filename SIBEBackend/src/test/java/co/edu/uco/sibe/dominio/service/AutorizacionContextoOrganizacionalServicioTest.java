package co.edu.uco.sibe.dominio.service;

import co.edu.uco.sibe.dominio.dto.ContextoUsuarioAutenticado;
import co.edu.uco.sibe.dominio.modelo.EjecucionActividad;
import co.edu.uco.sibe.dominio.puerto.consulta.ActividadRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.AreaRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.SubareaRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.UsuarioOrganizacionRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.servicio.ContextoUsuarioProveedorServicio;
import co.edu.uco.sibe.dominio.testdatabuilder.ActividadTestDataBuilder;
import co.edu.uco.sibe.dominio.testdatabuilder.AreaTestDataBuilder;
import co.edu.uco.sibe.dominio.testdatabuilder.ContextoUsuarioAutenticadoTestDataBuilder;
import co.edu.uco.sibe.dominio.testdatabuilder.SubareaTestDataBuilder;
import co.edu.uco.sibe.dominio.transversal.excepcion.AuthorizationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AutorizacionContextoOrganizacionalServicioTest {

    @Mock
    private ContextoUsuarioProveedorServicio contextoProveedorServicio;
    @Mock
    private AreaRepositorioConsulta areaRepositorioConsulta;
    @Mock
    private SubareaRepositorioConsulta subareaRepositorioConsulta;
    @Mock
    private ActividadRepositorioConsulta actividadRepositorioConsulta;
    @Mock
    private UsuarioOrganizacionRepositorioConsulta usuarioOrganizacionRepositorioConsulta;

    private AutorizacionContextoOrganizacionalServicio servicio;
    private UUID areaId;
    private UUID otraAreaId;
    private UUID subareaId;
    private UUID otraSubareaId;
    private UUID direccionId;
    private UUID usuarioId;

    @BeforeEach
    void setUp() {
        servicio = new AutorizacionContextoOrganizacionalServicio(
                contextoProveedorServicio, areaRepositorioConsulta, subareaRepositorioConsulta,
                actividadRepositorioConsulta, usuarioOrganizacionRepositorioConsulta);
        areaId = UUID.randomUUID();
        otraAreaId = UUID.randomUUID();
        subareaId = UUID.randomUUID();
        otraSubareaId = UUID.randomUUID();
        direccionId = UUID.randomUUID();
        usuarioId = UUID.randomUUID();
    }

    // ==================== validarAccesoADireccion ====================

    @Test
    void deberiaPermitirAccesoADireccionCuandoEsAdministradorDeDireccion() {
        configurarContexto(ADMINISTRADOR_DIRECCION);
        assertDoesNotThrow(() -> servicio.validarAccesoADireccion(direccionId));
    }

    @Test
    void deberiaDenegarAccesoADireccionCuandoEsAdministradorDeArea() {
        configurarContexto(ADMINISTRADOR_AREA);
        assertThrows(AuthorizationException.class, () -> servicio.validarAccesoADireccion(direccionId));
    }

    @Test
    void deberiaDenegarAccesoADireccionCuandoEsColaborador() {
        configurarContexto(COLABORADOR);
        assertThrows(AuthorizationException.class, () -> servicio.validarAccesoADireccion(direccionId));
    }

    // ==================== validarAccesoAArea ====================

    @Test
    void deberiaPermitirAccesoAAreaCuandoEsAdministradorDeDireccion() {
        configurarContexto(ADMINISTRADOR_DIRECCION);
        assertDoesNotThrow(() -> servicio.validarAccesoAArea(areaId));
    }

    @Test
    void deberiaPermitirAccesoASuAreaCuandoEsAdministradorDeArea() {
        configurarContexto(ADMINISTRADOR_AREA);
        assertDoesNotThrow(() -> servicio.validarAccesoAArea(areaId));
    }

    @Test
    void deberiaDenegarAccesoAOtraAreaCuandoEsAdministradorDeArea() {
        configurarContexto(ADMINISTRADOR_AREA);
        assertThrows(AuthorizationException.class, () -> servicio.validarAccesoAArea(otraAreaId));
    }

    @Test
    void deberiaDenegarAccesoAAreaCuandoEsColaboradorSinSubareaVinculada() {
        var contexto = new ContextoUsuarioAutenticadoTestDataBuilder()
                .conIdentificador(usuarioId).comoColaborador()
                .conDireccionId(direccionId).conAreaId(null).conSubareaId(null)
                .construir();
        when(contextoProveedorServicio.obtenerContextoActual()).thenReturn(contexto);
        assertThrows(AuthorizationException.class, () -> servicio.validarAccesoAArea(areaId));
    }

    @Test
    void deberiaPermitirAccesoASuAreaCuandoEsColaboradorConArea() {
        var contexto = new ContextoUsuarioAutenticadoTestDataBuilder()
                .conIdentificador(usuarioId).comoColaborador()
                .conDireccionId(direccionId).conAreaId(areaId).conSubareaId(null)
                .construir();
        when(contextoProveedorServicio.obtenerContextoActual()).thenReturn(contexto);
        assertDoesNotThrow(() -> servicio.validarAccesoAArea(areaId));
    }

    @Test
    void deberiaDenegarAccesoAOtraAreaCuandoEsColaboradorConArea() {
        var contexto = new ContextoUsuarioAutenticadoTestDataBuilder()
                .conIdentificador(usuarioId).comoColaborador()
                .conDireccionId(direccionId).conAreaId(areaId).conSubareaId(null)
                .construir();
        when(contextoProveedorServicio.obtenerContextoActual()).thenReturn(contexto);
        assertThrows(AuthorizationException.class, () -> servicio.validarAccesoAArea(otraAreaId));
    }

    @Test
    void deberiaDenegarAccesoAAreaPadreCuandoEsColaboradorConSubarea() {
        var contexto = new ContextoUsuarioAutenticadoTestDataBuilder()
                .conIdentificador(usuarioId).comoColaborador()
                .conDireccionId(direccionId).conAreaId(null).conSubareaId(subareaId)
                .construir();
        when(contextoProveedorServicio.obtenerContextoActual()).thenReturn(contexto);

        assertThrows(AuthorizationException.class, () -> servicio.validarAccesoAArea(areaId));
    }

    @Test
    void deberiaDenegarAccesoAOtraAreaCuandoEsColaboradorConSubarea() {
        var contexto = new ContextoUsuarioAutenticadoTestDataBuilder()
                .conIdentificador(usuarioId).comoColaborador()
                .conDireccionId(direccionId).conAreaId(null).conSubareaId(subareaId)
                .construir();
        when(contextoProveedorServicio.obtenerContextoActual()).thenReturn(contexto);

        assertThrows(AuthorizationException.class, () -> servicio.validarAccesoAArea(otraAreaId));
    }

    // ==================== validarAccesoASubarea ====================

    @Test
    void deberiaPermitirAccesoASubareaCuandoEsAdministradorDeDireccion() {
        configurarContexto(ADMINISTRADOR_DIRECCION);
        assertDoesNotThrow(() -> servicio.validarAccesoASubarea(subareaId));
    }

    @Test
    void deberiaPermitirAccesoASubareaDeSuAreaCuandoEsAdministradorDeArea() {
        configurarContexto(ADMINISTRADOR_AREA);
        var subarea = new SubareaTestDataBuilder().conIdentificador(subareaId).construir();
        var area = new AreaTestDataBuilder().conIdentificador(areaId).conSubareas(List.of(subarea)).construir();
        when(subareaRepositorioConsulta.consultarPorIdentificador(subareaId)).thenReturn(subarea);
        when(areaRepositorioConsulta.consultarPorIdentificador(areaId)).thenReturn(area);

        assertDoesNotThrow(() -> servicio.validarAccesoASubarea(subareaId));
    }

    @Test
    void deberiaDenegarAccesoASubareaDeOtraAreaCuandoEsAdministradorDeArea() {
        configurarContexto(ADMINISTRADOR_AREA);
        var subareaOtra = new SubareaTestDataBuilder().conIdentificador(otraSubareaId).construir();
        var area = new AreaTestDataBuilder().conIdentificador(areaId).construir();
        when(subareaRepositorioConsulta.consultarPorIdentificador(otraSubareaId)).thenReturn(subareaOtra);
        when(areaRepositorioConsulta.consultarPorIdentificador(areaId)).thenReturn(area);

        assertThrows(AuthorizationException.class, () -> servicio.validarAccesoASubarea(otraSubareaId));
    }

    @Test
    void deberiaPermitirAccesoASuSubareaCuandoEsColaborador() {
        configurarContexto(COLABORADOR);
        assertDoesNotThrow(() -> servicio.validarAccesoASubarea(subareaId));
    }

    @Test
    void deberiaDenegarAccesoAOtraSubareaCuandoEsColaborador() {
        configurarContexto(COLABORADOR);
        assertThrows(AuthorizationException.class, () -> servicio.validarAccesoASubarea(otraSubareaId));
    }

    @Test
    void deberiaPermitirAccesoASubareaDeSuAreaCuandoEsColaboradorConArea() {
        var contexto = new ContextoUsuarioAutenticadoTestDataBuilder()
                .conIdentificador(usuarioId).comoColaborador()
                .conDireccionId(direccionId).conAreaId(areaId).conSubareaId(null)
                .construir();
        when(contextoProveedorServicio.obtenerContextoActual()).thenReturn(contexto);

        var subarea = new SubareaTestDataBuilder().conIdentificador(subareaId).construir();
        var area = new AreaTestDataBuilder().conIdentificador(areaId).conSubareas(List.of(subarea)).construir();
        when(areaRepositorioConsulta.consultarPorIdentificador(areaId)).thenReturn(area);

        assertDoesNotThrow(() -> servicio.validarAccesoASubarea(subareaId));
    }

    @Test
    void deberiaDenegarAccesoASubareaDeOtraAreaCuandoEsColaboradorConArea() {
        var contexto = new ContextoUsuarioAutenticadoTestDataBuilder()
                .conIdentificador(usuarioId).comoColaborador()
                .conDireccionId(direccionId).conAreaId(areaId).conSubareaId(null)
                .construir();
        when(contextoProveedorServicio.obtenerContextoActual()).thenReturn(contexto);

        var area = new AreaTestDataBuilder().conIdentificador(areaId).construir();
        when(areaRepositorioConsulta.consultarPorIdentificador(areaId)).thenReturn(area);

        assertThrows(AuthorizationException.class, () -> servicio.validarAccesoASubarea(otraSubareaId));
    }

    // ==================== validarAccesoAActividad ====================

    @Test
    void deberiaPermitirAccesoAActividadCuandoEsAdministradorDeDireccion() {
        configurarContexto(ADMINISTRADOR_DIRECCION);
        var actividadId = UUID.randomUUID();
        assertDoesNotThrow(() -> servicio.validarAccesoAActividad(actividadId));
    }

    @Test
    void deberiaPermitirAccesoAActividadDeSuAreaCuandoEsAdminArea() {
        configurarContexto(ADMINISTRADOR_AREA);
        var actividadId = UUID.randomUUID();
        var actividad = new ActividadTestDataBuilder().conIdentificador(actividadId).construir();
        var area = new AreaTestDataBuilder().conIdentificador(areaId).construir();

        when(actividadRepositorioConsulta.consultarPorIdentificador(actividadId)).thenReturn(actividad);
        when(areaRepositorioConsulta.consultarPorActividad(actividad)).thenReturn(area);

        assertDoesNotThrow(() -> servicio.validarAccesoAActividad(actividadId));
    }

    @Test
    void deberiaDenegarAccesoAActividadDeOtraAreaCuandoEsAdminArea() {
        configurarContexto(ADMINISTRADOR_AREA);
        var actividadId = UUID.randomUUID();
        var actividad = new ActividadTestDataBuilder().conIdentificador(actividadId).construir();
        var otraArea = new AreaTestDataBuilder().conIdentificador(otraAreaId).construir();

        when(actividadRepositorioConsulta.consultarPorIdentificador(actividadId)).thenReturn(actividad);
        when(areaRepositorioConsulta.consultarPorActividad(actividad)).thenReturn(otraArea);

        assertThrows(AuthorizationException.class, () -> servicio.validarAccesoAActividad(actividadId));
    }

    @Test
    void deberiaPermitirAccesoAActividadAsignadaCuandoEsColaborador() {
        var contexto = new ContextoUsuarioAutenticadoTestDataBuilder()
                .conIdentificador(usuarioId).comoColaborador()
                .conDireccionId(direccionId).conAreaId(areaId).conSubareaId(subareaId)
                .construir();
        when(contextoProveedorServicio.obtenerContextoActual()).thenReturn(contexto);

        var actividadId = UUID.randomUUID();
        var actividad = new ActividadTestDataBuilder().conIdentificador(actividadId).conColaborador(usuarioId)
                .construir();
        when(actividadRepositorioConsulta.consultarPorIdentificador(actividadId)).thenReturn(actividad);

        assertDoesNotThrow(() -> servicio.validarAccesoAActividad(actividadId));
    }

    @Test
    void deberiaDenegarAccesoAActividadNoAsignadaCuandoEsColaborador() {
        configurarContexto(COLABORADOR);
        var actividadId = UUID.randomUUID();
        var otroColaborador = UUID.randomUUID();
        var actividad = new ActividadTestDataBuilder().conIdentificador(actividadId).conColaborador(otroColaborador)
                .construir();
        var otraSubarea = new SubareaTestDataBuilder().conIdentificador(otraSubareaId).construir();

        when(actividadRepositorioConsulta.consultarPorIdentificador(actividadId)).thenReturn(actividad);
        when(subareaRepositorioConsulta.consultarPorActividad(actividad)).thenReturn(otraSubarea);

        assertThrows(AuthorizationException.class, () -> servicio.validarAccesoAActividad(actividadId));
    }

    // ==================== validarAccesoAUsuario ====================

    @Test
    void deberiaPermitirAccesoATodoUsuarioCuandoEsAdministradorDeDireccion() {
        configurarContexto(ADMINISTRADOR_DIRECCION);
        assertDoesNotThrow(() -> servicio.validarAccesoAUsuario(UUID.randomUUID()));
    }

    @Test
    void deberiaPermitirColaboradorAccederASuPropioUsuario() {
        var contexto = new ContextoUsuarioAutenticadoTestDataBuilder()
                .conIdentificador(usuarioId).comoColaborador()
                .conDireccionId(direccionId).conAreaId(areaId).conSubareaId(subareaId)
                .construir();
        when(contextoProveedorServicio.obtenerContextoActual()).thenReturn(contexto);

        assertDoesNotThrow(() -> servicio.validarAccesoAUsuario(usuarioId));
    }

    @Test
    void deberiaDenegarColaboradorAccederAOtroUsuario() {
        configurarContexto(COLABORADOR);
        assertThrows(AuthorizationException.class, () -> servicio.validarAccesoAUsuario(UUID.randomUUID()));
    }

    @Test
    void deberiaPermitirAdminAreaAccederAUsuarioDeSuArea() {
        configurarContexto(ADMINISTRADOR_AREA);
        var otroUsuarioId = UUID.randomUUID();
        when(usuarioOrganizacionRepositorioConsulta.consultarAreaIdPorUsuarioId(otroUsuarioId)).thenReturn(areaId);
        assertDoesNotThrow(() -> servicio.validarAccesoAUsuario(otroUsuarioId));
    }

    @Test
    void deberiaDenegarAdminAreaAccederAUsuarioDeOtraArea() {
        configurarContexto(ADMINISTRADOR_AREA);
        var otroUsuarioId = UUID.randomUUID();
        when(usuarioOrganizacionRepositorioConsulta.consultarAreaIdPorUsuarioId(otroUsuarioId)).thenReturn(otraAreaId);
        assertThrows(AuthorizationException.class, () -> servicio.validarAccesoAUsuario(otroUsuarioId));
    }

    // ==================== validarAccesoAEjecucionActividad ====================

    @Test
    void deberiaPermitirAccesoAEjecucionDeSuAreaCuandoEsAdminArea() {
        configurarContexto(ADMINISTRADOR_AREA);
        var actividadId = UUID.randomUUID();
        var ejecucionId = UUID.randomUUID();
        var actividad = new ActividadTestDataBuilder().conIdentificador(actividadId).construir();
        var ejecucion = EjecucionActividad.construir(ejecucionId, null, null, null, null, null, actividad);
        var area = new AreaTestDataBuilder().conIdentificador(areaId).construir();

        when(actividadRepositorioConsulta.consultarEjecucionActividadPorIdentificador(ejecucionId))
                .thenReturn(ejecucion);
        when(actividadRepositorioConsulta.consultarPorIdentificador(actividadId)).thenReturn(actividad);
        when(areaRepositorioConsulta.consultarPorActividad(actividad)).thenReturn(area);

        assertDoesNotThrow(() -> servicio.validarAccesoAEjecucionActividad(ejecucionId));
    }

    @Test
    void deberiaLanzarExcepcionCuandoActividadNoExiste() {
        configurarContexto(ADMINISTRADOR_AREA);
        var actividadId = UUID.randomUUID();
        when(actividadRepositorioConsulta.consultarPorIdentificador(actividadId)).thenReturn(null);
        assertThrows(AuthorizationException.class, () -> servicio.validarAccesoAActividad(actividadId));
    }

    @Test
    void deberiaPermitirAccesoAActividadDeSubareaDeSuAreaCuandoEsAdminArea() {
        configurarContexto(ADMINISTRADOR_AREA);
        var actividadId = UUID.randomUUID();
        var actividad = new ActividadTestDataBuilder().conIdentificador(actividadId).construir();
        var otraArea = new AreaTestDataBuilder().conIdentificador(otraAreaId).construir();
        var subarea = new SubareaTestDataBuilder().conIdentificador(subareaId).construir();
        var areaAdmin = new AreaTestDataBuilder().conIdentificador(areaId).conSubareas(List.of(subarea)).construir();

        when(actividadRepositorioConsulta.consultarPorIdentificador(actividadId)).thenReturn(actividad);
        when(areaRepositorioConsulta.consultarPorActividad(actividad)).thenReturn(otraArea);
        when(subareaRepositorioConsulta.consultarPorActividad(actividad)).thenReturn(subarea);
        when(areaRepositorioConsulta.consultarPorIdentificador(areaId)).thenReturn(areaAdmin);

        assertDoesNotThrow(() -> servicio.validarAccesoAActividad(actividadId));
    }

    @Test
    void deberiaPermitirAccesoAActividadDeSuSubareaCuandoEsColaborador() {
        var contexto = new ContextoUsuarioAutenticadoTestDataBuilder()
                .conIdentificador(usuarioId).comoColaborador()
                .conDireccionId(direccionId).conAreaId(areaId).conSubareaId(subareaId)
                .construir();
        when(contextoProveedorServicio.obtenerContextoActual()).thenReturn(contexto);

        var actividadId = UUID.randomUUID();
        var actividad = new ActividadTestDataBuilder().conIdentificador(actividadId).conColaborador(null).construir();
        var subarea = new SubareaTestDataBuilder().conIdentificador(subareaId).construir();

        when(actividadRepositorioConsulta.consultarPorIdentificador(actividadId)).thenReturn(actividad);
        when(subareaRepositorioConsulta.consultarPorActividad(actividad)).thenReturn(subarea);

        assertDoesNotThrow(() -> servicio.validarAccesoAActividad(actividadId));
    }

    @Test
    void deberiaPermitirAccesoAActividadDeSuAreaCuandoEsColaboradorConArea() {
        var contexto = new ContextoUsuarioAutenticadoTestDataBuilder()
                .conIdentificador(usuarioId).comoColaborador()
                .conDireccionId(direccionId).conAreaId(areaId).conSubareaId(null)
                .construir();
        when(contextoProveedorServicio.obtenerContextoActual()).thenReturn(contexto);

        var actividadId = UUID.randomUUID();
        var actividad = new ActividadTestDataBuilder().conIdentificador(actividadId).conColaborador(null).construir();
        var area = new AreaTestDataBuilder().conIdentificador(areaId).construir();

        when(actividadRepositorioConsulta.consultarPorIdentificador(actividadId)).thenReturn(actividad);
        when(areaRepositorioConsulta.consultarPorActividad(actividad)).thenReturn(area);

        assertDoesNotThrow(() -> servicio.validarAccesoAActividad(actividadId));
    }

    @Test
    void deberiaPermitirAccesoAActividadDeSubareaDeSuAreaCuandoEsColaboradorConArea() {
        var contexto = new ContextoUsuarioAutenticadoTestDataBuilder()
                .conIdentificador(usuarioId).comoColaborador()
                .conDireccionId(direccionId).conAreaId(areaId).conSubareaId(null)
                .construir();
        when(contextoProveedorServicio.obtenerContextoActual()).thenReturn(contexto);

        var actividadId = UUID.randomUUID();
        var actividad = new ActividadTestDataBuilder().conIdentificador(actividadId).conColaborador(null).construir();
        var subarea = new SubareaTestDataBuilder().conIdentificador(subareaId).construir();
        var areaColaborador = new AreaTestDataBuilder().conIdentificador(areaId).conSubareas(List.of(subarea)).construir();

        when(actividadRepositorioConsulta.consultarPorIdentificador(actividadId)).thenReturn(actividad);
        when(areaRepositorioConsulta.consultarPorActividad(actividad)).thenReturn(null);
        when(subareaRepositorioConsulta.consultarPorActividad(actividad)).thenReturn(subarea);
        when(areaRepositorioConsulta.consultarPorIdentificador(areaId)).thenReturn(areaColaborador);

        assertDoesNotThrow(() -> servicio.validarAccesoAActividad(actividadId));
    }

    // ==================== Admin Area sin areaId (vinculado a subarea) ====================

    @Test
    void deberiaDenegarAccesoAAreaCuandoEsAdminDeSubarea() {
        var contexto = new ContextoUsuarioAutenticadoTestDataBuilder()
                .conIdentificador(usuarioId).comoAdministradorArea()
                .conDireccionId(direccionId).conAreaId(null).conSubareaId(subareaId)
                .construir();
        when(contextoProveedorServicio.obtenerContextoActual()).thenReturn(contexto);

        assertThrows(AuthorizationException.class, () -> servicio.validarAccesoAArea(areaId));
    }

    @Test
    void deberiaPermitirAccesoASuSubareaCuandoEsAdminSubarea() {
        var contexto = new ContextoUsuarioAutenticadoTestDataBuilder()
                .conIdentificador(usuarioId).comoAdministradorArea()
                .conDireccionId(direccionId).conAreaId(null).conSubareaId(subareaId)
                .construir();
        when(contextoProveedorServicio.obtenerContextoActual()).thenReturn(contexto);

        assertDoesNotThrow(() -> servicio.validarAccesoASubarea(subareaId));
    }

    @Test
    void deberiaDenegarAccesoAOtraSubareaCuandoEsAdminSubarea() {
        var contexto = new ContextoUsuarioAutenticadoTestDataBuilder()
                .conIdentificador(usuarioId).comoAdministradorArea()
                .conDireccionId(direccionId).conAreaId(null).conSubareaId(subareaId)
                .construir();
        when(contextoProveedorServicio.obtenerContextoActual()).thenReturn(contexto);

        assertThrows(AuthorizationException.class, () -> servicio.validarAccesoASubarea(otraSubareaId));
    }

    @Test
    void deberiaPermitirAccesoAActividadDeSuSubareaCuandoEsAdminSubarea() {
        var contexto = new ContextoUsuarioAutenticadoTestDataBuilder()
                .conIdentificador(usuarioId).comoAdministradorArea()
                .conDireccionId(direccionId).conAreaId(null).conSubareaId(subareaId)
                .construir();
        when(contextoProveedorServicio.obtenerContextoActual()).thenReturn(contexto);

        var actividadId = UUID.randomUUID();
        var actividad = new ActividadTestDataBuilder().conIdentificador(actividadId).construir();
        var subarea = new SubareaTestDataBuilder().conIdentificador(subareaId).construir();
        when(actividadRepositorioConsulta.consultarPorIdentificador(actividadId)).thenReturn(actividad);
        when(subareaRepositorioConsulta.consultarPorActividad(actividad)).thenReturn(subarea);

        assertDoesNotThrow(() -> servicio.validarAccesoAActividad(actividadId));
    }

    @Test
    void deberiaDenegarAccesoAActividadDeOtraSubareaCuandoEsAdminSubarea() {
        var contexto = new ContextoUsuarioAutenticadoTestDataBuilder()
                .conIdentificador(usuarioId).comoAdministradorArea()
                .conDireccionId(direccionId).conAreaId(null).conSubareaId(subareaId)
                .construir();
        when(contextoProveedorServicio.obtenerContextoActual()).thenReturn(contexto);

        var actividadId = UUID.randomUUID();
        var actividad = new ActividadTestDataBuilder().conIdentificador(actividadId).construir();
        var otraSubarea = new SubareaTestDataBuilder().conIdentificador(otraSubareaId).construir();
        when(actividadRepositorioConsulta.consultarPorIdentificador(actividadId)).thenReturn(actividad);
        when(subareaRepositorioConsulta.consultarPorActividad(actividad)).thenReturn(otraSubarea);

        assertThrows(AuthorizationException.class, () -> servicio.validarAccesoAActividad(actividadId));
    }

    // ==================== Helpers ====================

    private void configurarContexto(String rol) {
        var contexto = new ContextoUsuarioAutenticadoTestDataBuilder()
                .conIdentificador(usuarioId).conRol(rol)
                .conDireccionId(direccionId).conAreaId(areaId).conSubareaId(subareaId)
                .construir();
        when(contextoProveedorServicio.obtenerContextoActual()).thenReturn(contexto);
    }
}
