package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.dominio.usecase.comando.LoginUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginManejadorTest {

    @Mock private LoginUseCase loginUseCase;

    private LoginManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new LoginManejador(loginUseCase);
    }

    @Test
    void deberiaEjecutarLogin() {
        UUID idEsperado = UUID.randomUUID();
        when(loginUseCase.ejecutar("token123")).thenReturn(idEsperado);

        ComandoRespuesta<UUID> resultado = manejador.ejecutar("token123");

        assertEquals(idEsperado, resultado.getValor());
    }
}
