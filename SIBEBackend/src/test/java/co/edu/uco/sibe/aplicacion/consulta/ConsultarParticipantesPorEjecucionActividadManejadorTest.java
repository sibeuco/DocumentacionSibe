package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.ParticipanteDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarParticipantesPorEjecucionActividadUseCase;
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
class ConsultarParticipantesPorEjecucionActividadManejadorTest {

    @Mock private ConsultarParticipantesPorEjecucionActividadUseCase consultarParticipantesPorEjecucionActividadUseCase;

    private ConsultarParticipantesPorEjecucionActividadManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarParticipantesPorEjecucionActividadManejador(consultarParticipantesPorEjecucionActividadUseCase);
    }

    @Test
    void deberiaConsultarParticipantesPorEjecucion() {
        UUID ejecucionId = UUID.randomUUID();
        List<ParticipanteDTO> esperado = List.of(mock(ParticipanteDTO.class));
        when(consultarParticipantesPorEjecucionActividadUseCase.ejecutar(ejecucionId)).thenReturn(esperado);

        List<ParticipanteDTO> resultado = manejador.ejecutar(ejecucionId);

        assertEquals(esperado, resultado);
        verify(consultarParticipantesPorEjecucionActividadUseCase).ejecutar(ejecucionId);
    }

}
