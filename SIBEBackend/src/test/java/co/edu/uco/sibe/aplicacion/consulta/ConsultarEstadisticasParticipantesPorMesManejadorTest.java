package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.EstadisticaMesDTO;
import co.edu.uco.sibe.dominio.dto.FiltroEstadisticaDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarEstadisticasParticipantesPorMesUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarEstadisticasParticipantesPorMesManejadorTest {

    @Mock private ConsultarEstadisticasParticipantesPorMesUseCase useCase;

    private ConsultarEstadisticasParticipantesPorMesManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarEstadisticasParticipantesPorMesManejador(useCase);
    }

    @Test
    void deberiaConsultarEstadisticasPorMes() {
        FiltroEstadisticaDTO filtro = mock(FiltroEstadisticaDTO.class);
        List<EstadisticaMesDTO> esperado = List.of(mock(EstadisticaMesDTO.class));
        when(useCase.ejecutar(filtro)).thenReturn(esperado);

        List<EstadisticaMesDTO> resultado = manejador.ejecutar(filtro);

        assertEquals(esperado, resultado);
        verify(useCase).ejecutar(filtro);
    }
}
