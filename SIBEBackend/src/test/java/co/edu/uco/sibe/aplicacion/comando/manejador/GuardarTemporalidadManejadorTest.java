package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.fabrica.TemporalidadFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.dominio.modelo.Temporalidad;
import co.edu.uco.sibe.dominio.usecase.comando.GuardarTemporalidadUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuardarTemporalidadManejadorTest {

    @Mock private GuardarTemporalidadUseCase guardarTemporalidadUseCase;
    @Mock private TemporalidadFabrica temporalidadFabrica;

    private GuardarTemporalidadManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new GuardarTemporalidadManejador(guardarTemporalidadUseCase, temporalidadFabrica);
    }

    @Test
    void deberiaEjecutarGuardarTemporalidad() {
        Temporalidad temporalidad = mock(Temporalidad.class);
        UUID idEsperado = UUID.randomUUID();

        when(temporalidadFabrica.construir("Semestral")).thenReturn(temporalidad);
        when(guardarTemporalidadUseCase.ejecutar(temporalidad)).thenReturn(idEsperado);

        ComandoRespuesta<UUID> resultado = manejador.ejecutar("Semestral");

        assertEquals(idEsperado, resultado.getValor());
        verify(temporalidadFabrica).construir("Semestral");
        verify(guardarTemporalidadUseCase).ejecutar(temporalidad);
    }
}
