package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarTiposParticipantesEnEjecucionesFinalizadasUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarTiposParticipantesEnEjecucionesFinalizadasManejadorTest {

    @Mock private ConsultarTiposParticipantesEnEjecucionesFinalizadasUseCase useCase;

    private ConsultarTiposParticipantesEnEjecucionesFinalizadasManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarTiposParticipantesEnEjecucionesFinalizadasManejador(useCase);
    }

    @Test
    void deberiaConsultarTiposParticipantes() {
        List<String> esperado = List.of("Estudiante", "Empleado");
        when(useCase.ejecutar()).thenReturn(esperado);

        List<String> resultado = manejador.ejecutar();

        assertEquals(esperado, resultado);
        verify(useCase).ejecutar();
    }
}
