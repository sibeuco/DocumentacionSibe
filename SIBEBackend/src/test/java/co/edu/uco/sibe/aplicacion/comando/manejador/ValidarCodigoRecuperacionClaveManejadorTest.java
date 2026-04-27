package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.ValidarCodigoRecuperacionClaveComando;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.dominio.usecase.comando.ValidarCodigoRecuperacionClaveUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValidarCodigoRecuperacionClaveManejadorTest {

    @Mock private ValidarCodigoRecuperacionClaveUseCase validarCodigoRecuperacionClaveUseCase;

    private ValidarCodigoRecuperacionClaveManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ValidarCodigoRecuperacionClaveManejador(validarCodigoRecuperacionClaveUseCase);
    }

    @Test
    void deberiaEjecutarValidarCodigo() {
        ValidarCodigoRecuperacionClaveComando comando = mock(ValidarCodigoRecuperacionClaveComando.class);

        when(comando.getCorreo()).thenReturn("test@correo.com");
        when(comando.getCodigo()).thenReturn("123456");
        when(validarCodigoRecuperacionClaveUseCase.ejecutar("test@correo.com", "123456")).thenReturn(true);

        ComandoRespuesta<Boolean> resultado = manejador.ejecutar(comando);

        assertTrue(resultado.getValor());
        verify(validarCodigoRecuperacionClaveUseCase).ejecutar("test@correo.com", "123456");
    }
}
