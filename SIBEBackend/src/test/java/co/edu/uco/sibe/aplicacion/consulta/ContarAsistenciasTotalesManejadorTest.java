package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.FiltroEstadisticaDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ContarAsistenciasTotalesUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContarAsistenciasTotalesManejadorTest {

    @Mock private ContarAsistenciasTotalesUseCase useCase;

    private ContarAsistenciasTotalesManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ContarAsistenciasTotalesManejador(useCase);
    }

    @Test
    void deberiaContarAsistenciasTotales() {
        FiltroEstadisticaDTO filtro = mock(FiltroEstadisticaDTO.class);
        when(useCase.ejecutar(filtro)).thenReturn(100L);

        Long resultado = manejador.ejecutar(filtro);

        assertEquals(100L, resultado);
        verify(useCase).ejecutar(filtro);
    }
}
