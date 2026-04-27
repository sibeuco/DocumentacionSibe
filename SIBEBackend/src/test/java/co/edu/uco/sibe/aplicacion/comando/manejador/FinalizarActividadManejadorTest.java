package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.ParticipanteComando;
import co.edu.uco.sibe.aplicacion.comando.fabrica.ParticipanteFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.dominio.modelo.Participante;
import co.edu.uco.sibe.dominio.usecase.comando.FinalizarActividadUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FinalizarActividadManejadorTest {

    @Mock private ParticipanteFabrica participanteFabrica;
    @Mock private FinalizarActividadUseCase finalizarActividadUseCase;

    private FinalizarActividadManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new FinalizarActividadManejador(participanteFabrica, finalizarActividadUseCase);
    }

    @Test
    void deberiaEjecutarFinalizarActividad() {
        UUID parametro = UUID.randomUUID();
        UUID idEsperado = UUID.randomUUID();
        List<ParticipanteComando> comando = List.of(mock(ParticipanteComando.class));
        List<Participante> participantes = List.of(mock(Participante.class));

        when(participanteFabrica.construirParticipantes(comando, parametro)).thenReturn(participantes);
        when(finalizarActividadUseCase.ejecutar(parametro, participantes)).thenReturn(idEsperado);

        ComandoRespuesta<UUID> resultado = manejador.ejecutar(comando, parametro);

        assertEquals(idEsperado, resultado.getValor());
        verify(participanteFabrica).construirParticipantes(comando, parametro);
        verify(finalizarActividadUseCase).ejecutar(parametro, participantes);
    }
}
