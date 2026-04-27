package co.edu.uco.sibe.infraestructura.adaptador.servicio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EncriptarClaveServicioEncoderTest {

    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks
    private EncriptarClaveServicioEncoder servicio;

    @Test
    void deberiaEncriptarClave() {
        when(passwordEncoder.encode("miClave")).thenReturn("claveEncriptada");

        String resultado = servicio.ejecutar("miClave");

        assertEquals("claveEncriptada", resultado);
    }

    @Test
    void deberiaRetornarTrueCuandoClaveCoincide() {
        when(passwordEncoder.matches("miClave", "claveEncriptada")).thenReturn(true);

        assertTrue(servicio.existe("miClave", "claveEncriptada"));
    }

    @Test
    void deberiaRetornarFalseCuandoClaveNoCoincide() {
        when(passwordEncoder.matches("otraClave", "claveEncriptada")).thenReturn(false);

        assertFalse(servicio.existe("otraClave", "claveEncriptada"));
    }
}
