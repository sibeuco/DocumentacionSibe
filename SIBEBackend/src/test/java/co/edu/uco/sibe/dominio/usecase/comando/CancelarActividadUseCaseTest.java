package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.EjecucionActividad;
import co.edu.uco.sibe.dominio.modelo.EstadoActividad;
import co.edu.uco.sibe.dominio.puerto.comando.ActividadRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.ActividadRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.EstadoActividadRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import co.edu.uco.sibe.dominio.transversal.excepcion.AuthorizationException;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import org.apache.poi.openxml4j.exceptions.InvalidOperationException;
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
class CancelarActividadUseCaseTest {

    @Mock
    private ActividadRepositorioComando actividadRepositorioComando;
    @Mock
    private ActividadRepositorioConsulta actividadRepositorioConsulta;
    @Mock
    private EstadoActividadRepositorioConsulta estadoActividadRepositorioConsulta;
    @Mock
    private AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    private CancelarActividadUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new CancelarActividadUseCase(actividadRepositorioComando, actividadRepositorioConsulta,
                estadoActividadRepositorioConsulta, autorizacionServicio);
    }

    @Test
    void deberiaCancelarActividadEnCursoExitosamente() {
        UUID ejecucionId = UUID.randomUUID();
        EstadoActividad estadoEnCurso = EstadoActividad.construir(UUID.randomUUID(), EN_CURSO);
        EjecucionActividad ejecucion = EjecucionActividad.construir(ejecucionId, null, null, null, null,
                estadoEnCurso, null);
        EstadoActividad estadoPendiente = EstadoActividad.construir(UUID.randomUUID(), PENDIENTE);

        doNothing().when(autorizacionServicio).validarAccesoAEjecucionActividad(ejecucionId);
        when(actividadRepositorioConsulta.consultarEjecucionActividadPorIdentificador(ejecucionId))
                .thenReturn(ejecucion);
        when(estadoActividadRepositorioConsulta.consultarPorNombre(PENDIENTE)).thenReturn(estadoPendiente);

        UUID resultado = useCase.ejecutar(ejecucionId);

        assertEquals(ejecucionId, resultado);
        verify(actividadRepositorioComando).modificarEjecucion(ejecucion);
    }

    @Test
    void deberiaLanzarAuthorizationExceptionCuandoNoTieneAcceso() {
        UUID ejecucionId = UUID.randomUUID();

        doThrow(new AuthorizationException("No tiene acceso")).when(autorizacionServicio)
                .validarAccesoAEjecucionActividad(ejecucionId);

        assertThrows(AuthorizationException.class, () -> useCase.ejecutar(ejecucionId));

        verify(actividadRepositorioConsulta, never()).consultarEjecucionActividadPorIdentificador(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoEjecucionNoExiste() {
        UUID ejecucionId = UUID.randomUUID();

        doNothing().when(autorizacionServicio).validarAccesoAEjecucionActividad(ejecucionId);
        when(actividadRepositorioConsulta.consultarEjecucionActividadPorIdentificador(ejecucionId)).thenReturn(null);

        assertThrows(ValorInvalidoExcepcion.class, () -> useCase.ejecutar(ejecucionId));
    }

    @Test
    void deberiaLanzarExcepcionCuandoEjecucionNoEstaEnCurso() {
        UUID ejecucionId = UUID.randomUUID();
        EstadoActividad estadoPendiente = EstadoActividad.construir(UUID.randomUUID(), PENDIENTE);
        EjecucionActividad ejecucion = EjecucionActividad.construir(ejecucionId, null, null, null, null,
                estadoPendiente, null);

        doNothing().when(autorizacionServicio).validarAccesoAEjecucionActividad(ejecucionId);
        when(actividadRepositorioConsulta.consultarEjecucionActividadPorIdentificador(ejecucionId))
                .thenReturn(ejecucion);

        assertThrows(InvalidOperationException.class, () -> useCase.ejecutar(ejecucionId));

        verify(actividadRepositorioComando, never()).modificarEjecucion(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoEstadoPendienteNoExiste() {
        UUID ejecucionId = UUID.randomUUID();
        EstadoActividad estadoEnCurso = EstadoActividad.construir(UUID.randomUUID(), EN_CURSO);
        EjecucionActividad ejecucion = EjecucionActividad.construir(ejecucionId, null, null, null, null,
                estadoEnCurso, null);

        doNothing().when(autorizacionServicio).validarAccesoAEjecucionActividad(ejecucionId);
        when(actividadRepositorioConsulta.consultarEjecucionActividadPorIdentificador(ejecucionId))
                .thenReturn(ejecucion);
        when(estadoActividadRepositorioConsulta.consultarPorNombre(PENDIENTE)).thenReturn(null);

        assertThrows(ValorInvalidoExcepcion.class, () -> useCase.ejecutar(ejecucionId));
    }
}
