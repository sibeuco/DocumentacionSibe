package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.EjecucionActividad;
import co.edu.uco.sibe.dominio.modelo.EstadoActividad;
import co.edu.uco.sibe.dominio.modelo.Participante;
import co.edu.uco.sibe.dominio.modelo.RegistroAsistencia;
import co.edu.uco.sibe.dominio.puerto.comando.ActividadRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.comando.RegistroAsistenciaRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.ActividadRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.EstadoActividadRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.RegistroAsistenciaRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import co.edu.uco.sibe.dominio.service.RegistrarParticipanteService;
import co.edu.uco.sibe.dominio.transversal.excepcion.AuthorizationException;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import org.apache.poi.openxml4j.exceptions.InvalidOperationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static co.edu.uco.sibe.dominio.transversal.constante.DatoConstante.EN_CURSO;
import static co.edu.uco.sibe.dominio.transversal.constante.DatoConstante.FINALIZADA;
import static co.edu.uco.sibe.dominio.transversal.constante.DatoConstante.PENDIENTE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FinalizarActividadUseCaseTest {

    @Mock
    private ActividadRepositorioComando actividadRepositorioComando;
    @Mock
    private ActividadRepositorioConsulta actividadRepositorioConsulta;
    @Mock
    private EstadoActividadRepositorioConsulta estadoActividadRepositorioConsulta;
    @Mock
    private RegistrarParticipanteService registrarParticipanteService;
    @Mock
    private RegistroAsistenciaRepositorioComando registroAsistenciaRepositorioComando;
    @Mock
    private RegistroAsistenciaRepositorioConsulta registroAsistenciaRepositorioConsulta;
    @Mock
    private AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    private FinalizarActividadUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new FinalizarActividadUseCase(actividadRepositorioComando, actividadRepositorioConsulta,
                estadoActividadRepositorioConsulta, registrarParticipanteService,
                registroAsistenciaRepositorioComando, registroAsistenciaRepositorioConsulta, autorizacionServicio);
    }

    @Test
    void deberiaFinalizarActividadEnCursoSinParticipantes() {
        UUID ejecucionId = UUID.randomUUID();
        EstadoActividad estadoEnCurso = EstadoActividad.construir(UUID.randomUUID(), EN_CURSO);
        EjecucionActividad ejecucion = EjecucionActividad.construir(ejecucionId, null, null, null, null,
                estadoEnCurso, null);
        EstadoActividad estadoFinalizada = EstadoActividad.construir(UUID.randomUUID(), FINALIZADA);

        doNothing().when(autorizacionServicio).validarAccesoAEjecucionActividad(ejecucionId);
        when(actividadRepositorioConsulta.consultarEjecucionActividadPorIdentificador(ejecucionId))
                .thenReturn(ejecucion);
        when(estadoActividadRepositorioConsulta.consultarPorNombre(FINALIZADA)).thenReturn(estadoFinalizada);

        UUID resultado = useCase.ejecutar(ejecucionId, Collections.emptyList());

        assertEquals(ejecucionId, resultado);
        verify(actividadRepositorioComando).modificarEjecucion(ejecucion);
        verify(registrarParticipanteService, never()).ejecutar(any());
    }

    @Test
    void deberiaFinalizarActividadYRegistrarParticipantes() {
        UUID ejecucionId = UUID.randomUUID();
        EstadoActividad estadoEnCurso = EstadoActividad.construir(UUID.randomUUID(), EN_CURSO);
        EjecucionActividad ejecucion = EjecucionActividad.construir(ejecucionId, null, null, null, null,
                estadoEnCurso, null);
        EstadoActividad estadoFinalizada = EstadoActividad.construir(UUID.randomUUID(), FINALIZADA);
        Participante participante = mock(Participante.class);

        doNothing().when(autorizacionServicio).validarAccesoAEjecucionActividad(ejecucionId);
        when(actividadRepositorioConsulta.consultarEjecucionActividadPorIdentificador(ejecucionId))
                .thenReturn(ejecucion);
        when(estadoActividadRepositorioConsulta.consultarPorNombre(FINALIZADA)).thenReturn(estadoFinalizada);
        when(registrarParticipanteService.ejecutar(participante)).thenReturn(participante);
        when(registroAsistenciaRepositorioConsulta.consultarPorIdentificador(any())).thenReturn(null);

        UUID resultado = useCase.ejecutar(ejecucionId, List.of(participante));

        assertEquals(ejecucionId, resultado);
        verify(registrarParticipanteService).ejecutar(participante);
        verify(registroAsistenciaRepositorioComando).guardar(any(RegistroAsistencia.class));
    }

    @Test
    void deberiaLanzarAuthorizationExceptionCuandoNoTieneAcceso() {
        UUID ejecucionId = UUID.randomUUID();

        doThrow(new AuthorizationException("No tiene acceso")).when(autorizacionServicio)
                .validarAccesoAEjecucionActividad(ejecucionId);

        assertThrows(AuthorizationException.class,
                () -> useCase.ejecutar(ejecucionId, Collections.emptyList()));

        verify(actividadRepositorioConsulta, never()).consultarEjecucionActividadPorIdentificador(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoEjecucionNoExiste() {
        UUID ejecucionId = UUID.randomUUID();

        doNothing().when(autorizacionServicio).validarAccesoAEjecucionActividad(ejecucionId);
        when(actividadRepositorioConsulta.consultarEjecucionActividadPorIdentificador(ejecucionId)).thenReturn(null);

        assertThrows(ValorInvalidoExcepcion.class,
                () -> useCase.ejecutar(ejecucionId, Collections.emptyList()));
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

        assertThrows(InvalidOperationException.class,
                () -> useCase.ejecutar(ejecucionId, Collections.emptyList()));

        verify(actividadRepositorioComando, never()).modificarEjecucion(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoEstadoFinalizadaNoExiste() {
        UUID ejecucionId = UUID.randomUUID();
        EstadoActividad estadoEnCurso = EstadoActividad.construir(UUID.randomUUID(), EN_CURSO);
        EjecucionActividad ejecucion = EjecucionActividad.construir(ejecucionId, null, null, null, null,
                estadoEnCurso, null);

        doNothing().when(autorizacionServicio).validarAccesoAEjecucionActividad(ejecucionId);
        when(actividadRepositorioConsulta.consultarEjecucionActividadPorIdentificador(ejecucionId))
                .thenReturn(ejecucion);
        when(estadoActividadRepositorioConsulta.consultarPorNombre(FINALIZADA)).thenReturn(null);

        assertThrows(ValorInvalidoExcepcion.class,
                () -> useCase.ejecutar(ejecucionId, Collections.emptyList()));
    }
}
