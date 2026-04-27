package co.edu.uco.sibe.infraestructura.controlador;

import co.edu.uco.sibe.aplicacion.comando.manejador.LoginManejador;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginControladorTest {

    @Mock private LoginManejador loginManejador;
    @Mock private Principal principal;

    private LoginControlador controlador;

    @BeforeEach
    void setUp() {
        controlador = new LoginControlador(loginManejador);
    }

    @Test
    void deberiaRealizarLogin() {
        UUID id = UUID.randomUUID();
        when(principal.getName()).thenReturn("usuario@correo.com");
        when(loginManejador.ejecutar("usuario@correo.com")).thenReturn(new ComandoRespuesta<>(id));

        ComandoRespuesta<UUID> resultado = controlador.login(principal);

        assertEquals(id, resultado.getValor());
        verify(loginManejador).ejecutar("usuario@correo.com");
    }
}
