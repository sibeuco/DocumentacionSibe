package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.FiltroEstadisticaDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ContarPoblacionTotalUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContarPoblacionTotalManejadorTest {

    @Mock private ContarPoblacionTotalUseCase useCase;

    private ContarPoblacionTotalManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ContarPoblacionTotalManejador(useCase);
    }

    @Test
    void deberiaContarPoblacionTotal() {
        FiltroEstadisticaDTO filtro = mock(FiltroEstadisticaDTO.class);
        when(useCase.ejecutar(filtro)).thenReturn(500L);

        Long resultado = manejador.ejecutar(filtro);

        assertEquals(500L, resultado);
        verify(useCase).ejecutar(filtro);
    }
}
