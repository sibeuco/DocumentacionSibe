package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.fabrica.PublicoInteresFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.dominio.modelo.PublicoInteres;
import co.edu.uco.sibe.dominio.usecase.comando.GuardarPublicoInteresUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuardarPublicoInteresManejadorTest {

    @Mock private GuardarPublicoInteresUseCase guardarPublicoInteresUseCase;
    @Mock private PublicoInteresFabrica publicoInteresFabrica;

    private GuardarPublicoInteresManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new GuardarPublicoInteresManejador(guardarPublicoInteresUseCase, publicoInteresFabrica);
    }

    @Test
    void deberiaEjecutarGuardarPublicoInteres() {
        PublicoInteres publicoInteres = mock(PublicoInteres.class);
        UUID idEsperado = UUID.randomUUID();

        when(publicoInteresFabrica.construir("Estudiantes")).thenReturn(publicoInteres);
        when(guardarPublicoInteresUseCase.ejecutar(publicoInteres)).thenReturn(idEsperado);

        ComandoRespuesta<UUID> resultado = manejador.ejecutar("Estudiantes");

        assertEquals(idEsperado, resultado.getValor());
        verify(publicoInteresFabrica).construir("Estudiantes");
        verify(guardarPublicoInteresUseCase).ejecutar(publicoInteres);
    }
}
