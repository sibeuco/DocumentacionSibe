package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.RecuperarClaveComando;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.dominio.usecase.comando.RecuperarClaveUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecuperarClaveManejadorTest {

    @Mock private RecuperarClaveUseCase recuperarClaveUseCase;

    private RecuperarClaveManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new RecuperarClaveManejador(recuperarClaveUseCase);
    }

    @Test
    void deberiaEjecutarRecuperarClave() {
        RecuperarClaveComando comando = mock(RecuperarClaveComando.class);
        UUID idEsperado = UUID.randomUUID();

        when(comando.getCorreo()).thenReturn("test@correo.com");
        when(comando.getClave()).thenReturn("nuevaClave123");
        when(recuperarClaveUseCase.ejecutar("test@correo.com", "nuevaClave123")).thenReturn(idEsperado);

        ComandoRespuesta<UUID> resultado = manejador.ejecutar(comando);

        assertEquals(idEsperado, resultado.getValor());
        verify(recuperarClaveUseCase).ejecutar("test@correo.com", "nuevaClave123");
    }
}
