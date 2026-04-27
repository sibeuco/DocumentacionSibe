package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.EjecucionActividad;
import co.edu.uco.sibe.dominio.modelo.EstadoActividad;
import co.edu.uco.sibe.dominio.puerto.comando.ActividadRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.ActividadRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.EstadoActividadRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import co.edu.uco.sibe.dominio.transversal.excepcion.AuthorizationException;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static co.edu.uco.sibe.dominio.transversal.constante.DatoConstante.EN_CURSO;
import static co.edu.uco.sibe.dominio.transversal.constante.DatoConstante.PENDIENTE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IniciarActividadUseCaseTest {

    @Mock
    private ActividadRepositorioComando actividadRepositorioComando;

    @Mock
    private ActividadRepositorioConsulta actividadRepositorioConsulta;

    @Mock
    private EstadoActividadRepositorioConsulta estadoActividadRepositorioConsulta;

    @Mock
    private AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    private IniciarActividadUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new IniciarActividadUseCase(actividadRepositorioComando, actividadRepositorioConsulta,
                estadoActividadRepositorioConsulta, autorizacionServicio);
    }

    @Test
    void deberiaIniciarActividadCuandoTieneAccesoYEstaPendiente() {
        UUID ejecucionId = UUID.randomUUID();
        EstadoActividad estadoPendiente = EstadoActividad.construir(UUID.randomUUID(), PENDIENTE);
        EjecucionActividad ejecucion = EjecucionActividad.construir(ejecucionId, null, null, null, null,
                estadoPendiente, null);
        EstadoActividad estadoEnCurso = EstadoActividad.construir(UUID.randomUUID(), EN_CURSO);

        doNothing().when(autorizacionServicio).validarAccesoAEjecucionActividad(ejecucionId);
        when(actividadRepositorioConsulta.consultarEjecucionActividadPorIdentificador(ejecucionId))
                .thenReturn(ejecucion);
        when(estadoActividadRepositorioConsulta.consultarPorNombre(EN_CURSO)).thenReturn(estadoEnCurso);

        UUID resultado = useCase.ejecutar(ejecucionId);

        assertEquals(ejecucionId, resultado);
        verify(autorizacionServicio).validarAccesoAEjecucionActividad(ejecucionId);
        verify(actividadRepositorioComando).modificarEjecucion(ejecucion);
    }

    @Test
    void deberiaLanzarAuthorizationExceptionCuandoNoTieneAcceso() {
        UUID ejecucionId = UUID.randomUUID();

        doThrow(new AuthorizationException("No tiene acceso")).when(autorizacionServicio)
                .validarAccesoAEjecucionActividad(ejecucionId);

        assertThrows(AuthorizationException.class, () -> useCase.ejecutar(ejecucionId));

        verify(autorizacionServicio).validarAccesoAEjecucionActividad(ejecucionId);
        verify(actividadRepositorioConsulta, never()).consultarEjecucionActividadPorIdentificador(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoEjecucionNoExiste() {
        UUID ejecucionId = UUID.randomUUID();

        doNothing().when(autorizacionServicio).validarAccesoAEjecucionActividad(ejecucionId);
        when(actividadRepositorioConsulta.consultarEjecucionActividadPorIdentificador(ejecucionId)).thenReturn(null);

        assertThrows(ValorInvalidoExcepcion.class, () -> useCase.ejecutar(ejecucionId));

        verify(autorizacionServicio).validarAccesoAEjecucionActividad(ejecucionId);
    }

    @Test
    void deberiaValidarAccesoAntesDeConsultarEjecucion() {
        UUID ejecucionId = UUID.randomUUID();

        doThrow(new AuthorizationException("Sin acceso")).when(autorizacionServicio)
                .validarAccesoAEjecucionActividad(ejecucionId);

        assertThrows(AuthorizationException.class, () -> useCase.ejecutar(ejecucionId));

        verify(actividadRepositorioComando, never()).modificarEjecucion(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoEstadoNoEsPendiente() {
        UUID ejecucionId = UUID.randomUUID();
        EstadoActividad estadoEnCurso = EstadoActividad.construir(UUID.randomUUID(), "EN_CURSO");
        EjecucionActividad ejecucion = EjecucionActividad.construir(ejecucionId, null, null, null, null,
                estadoEnCurso, null);

        doNothing().when(autorizacionServicio).validarAccesoAEjecucionActividad(ejecucionId);
        when(actividadRepositorioConsulta.consultarEjecucionActividadPorIdentificador(ejecucionId))
                .thenReturn(ejecucion);

        assertThrows(org.apache.poi.openxml4j.exceptions.InvalidOperationException.class,
                () -> useCase.ejecutar(ejecucionId));
    }

    @Test
    void deberiaLanzarExcepcionCuandoEstadoEnCursoNoExiste() {
        UUID ejecucionId = UUID.randomUUID();
        EstadoActividad estadoPendiente = EstadoActividad.construir(UUID.randomUUID(), PENDIENTE);
        EjecucionActividad ejecucion = EjecucionActividad.construir(ejecucionId, null, null, null, null,
                estadoPendiente, null);

        doNothing().when(autorizacionServicio).validarAccesoAEjecucionActividad(ejecucionId);
        when(actividadRepositorioConsulta.consultarEjecucionActividadPorIdentificador(ejecucionId))
                .thenReturn(ejecucion);
        when(estadoActividadRepositorioConsulta.consultarPorNombre(EN_CURSO)).thenReturn(null);

        assertThrows(ValorInvalidoExcepcion.class, () -> useCase.ejecutar(ejecucionId));
    }
}
