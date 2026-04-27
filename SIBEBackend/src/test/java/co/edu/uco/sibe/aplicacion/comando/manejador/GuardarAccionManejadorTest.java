package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.AccionComando;
import co.edu.uco.sibe.aplicacion.comando.fabrica.AccionFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.dominio.modelo.Accion;
import co.edu.uco.sibe.dominio.usecase.comando.GuardarAccionUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuardarAccionManejadorTest {

    @Mock private AccionFabrica accionFabrica;
    @Mock private GuardarAccionUseCase guardarAccionUseCase;

    private GuardarAccionManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new GuardarAccionManejador(accionFabrica, guardarAccionUseCase);
    }

    @Test
    void deberiaEjecutarGuardarAccion() {
        AccionComando comando = new AccionComando("Detalle test", "Objetivo test");
        Accion accion = mock(Accion.class);
        UUID idEsperado = UUID.randomUUID();

        when(accionFabrica.construir(comando)).thenReturn(accion);
        when(guardarAccionUseCase.ejecutar(accion)).thenReturn(idEsperado);

        ComandoRespuesta<UUID> resultado = manejador.ejecutar(comando);

        assertEquals(idEsperado, resultado.getValor());
        verify(accionFabrica).construir(comando);
        verify(guardarAccionUseCase).ejecutar(accion);
    }
}
