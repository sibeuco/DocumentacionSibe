package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarSemestresActividadesEnEjecucionesFinalizadasUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarSemestresActividadesEnEjecucionesFinalizadasManejadorTest {

    @Mock private ConsultarSemestresActividadesEnEjecucionesFinalizadasUseCase useCase;

    private ConsultarSemestresActividadesEnEjecucionesFinalizadasManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarSemestresActividadesEnEjecucionesFinalizadasManejador(useCase);
    }

    @Test
    void deberiaConsultarSemestres() {
        List<String> esperado = List.of("2025-1", "2025-2");
        when(useCase.ejecutar()).thenReturn(esperado);

        List<String> resultado = manejador.ejecutar();

        assertEquals(esperado, resultado);
        verify(useCase).ejecutar();
    }
}
