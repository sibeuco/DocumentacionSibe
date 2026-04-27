package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.FiltroEstadisticaDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ContarEjecucionesTotalesUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContarEjecucionesTotalesManejadorTest {

    @Mock private ContarEjecucionesTotalesUseCase useCase;

    private ContarEjecucionesTotalesManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ContarEjecucionesTotalesManejador(useCase);
    }

    @Test
    void deberiaContarEjecucionesTotales() {
        FiltroEstadisticaDTO filtro = mock(FiltroEstadisticaDTO.class);
        when(useCase.ejecutar(filtro)).thenReturn(50L);

        Long resultado = manejador.ejecutar(filtro);

        assertEquals(50L, resultado);
        verify(useCase).ejecutar(filtro);
    }
}
