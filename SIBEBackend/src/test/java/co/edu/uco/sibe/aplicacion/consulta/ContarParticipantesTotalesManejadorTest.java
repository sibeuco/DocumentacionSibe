package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.FiltroEstadisticaDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ContarParticipantesTotalesUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContarParticipantesTotalesManejadorTest {

    @Mock private ContarParticipantesTotalesUseCase useCase;

    private ContarParticipantesTotalesManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ContarParticipantesTotalesManejador(useCase);
    }

    @Test
    void deberiaContarParticipantesTotales() {
        FiltroEstadisticaDTO filtro = mock(FiltroEstadisticaDTO.class);
        when(useCase.ejecutar(filtro)).thenReturn(200L);

        Long resultado = manejador.ejecutar(filtro);

        assertEquals(200L, resultado);
        verify(useCase).ejecutar(filtro);
    }
}
