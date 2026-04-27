package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarCentrosCostosEmpleadosEnEjecucionesFinalizadasUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarCentrosCostosEmpleadosEnEjecucionesFinalizadasManejadorTest {

    @Mock private ConsultarCentrosCostosEmpleadosEnEjecucionesFinalizadasUseCase useCase;

    private ConsultarCentrosCostosEmpleadosEnEjecucionesFinalizadasManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarCentrosCostosEmpleadosEnEjecucionesFinalizadasManejador(useCase);
    }

    @Test
    void deberiaConsultarCentrosCostos() {
        List<String> esperado = List.of("Centro1", "Centro2");
        when(useCase.ejecutar()).thenReturn(esperado);

        List<String> resultado = manejador.ejecutar();

        assertEquals(esperado, resultado);
        verify(useCase).ejecutar();
    }
}
