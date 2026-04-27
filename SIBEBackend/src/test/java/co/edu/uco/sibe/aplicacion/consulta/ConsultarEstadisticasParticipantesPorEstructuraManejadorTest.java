package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.EstadisticaDTO;
import co.edu.uco.sibe.dominio.dto.FiltroEstadisticaDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarEstadisticasParticipantesPorEstructuraUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarEstadisticasParticipantesPorEstructuraManejadorTest {

    @Mock private ConsultarEstadisticasParticipantesPorEstructuraUseCase useCase;

    private ConsultarEstadisticasParticipantesPorEstructuraManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarEstadisticasParticipantesPorEstructuraManejador(useCase);
    }

    @Test
    void deberiaConsultarEstadisticasPorEstructura() {
        FiltroEstadisticaDTO filtro = mock(FiltroEstadisticaDTO.class);
        List<EstadisticaDTO> esperado = List.of(mock(EstadisticaDTO.class));
        when(useCase.ejecutar(filtro)).thenReturn(esperado);

        List<EstadisticaDTO> resultado = manejador.ejecutar(filtro);

        assertEquals(esperado, resultado);
        verify(useCase).ejecutar(filtro);
    }
}
