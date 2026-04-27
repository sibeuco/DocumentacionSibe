package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.TipoUsuarioComando;
import co.edu.uco.sibe.aplicacion.comando.fabrica.TipoUsuarioFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.dominio.modelo.TipoUsuario;
import co.edu.uco.sibe.dominio.usecase.comando.GuardarTipoUsuarioUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuardarTipoUsuarioManejadorTest {

    @Mock private GuardarTipoUsuarioUseCase guardarTipoUsuarioUseCase;
    @Mock private TipoUsuarioFabrica tipoUsuarioFabrica;

    private GuardarTipoUsuarioManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new GuardarTipoUsuarioManejador(guardarTipoUsuarioUseCase, tipoUsuarioFabrica);
    }

    @Test
    void deberiaEjecutarGuardarTipoUsuario() {
        TipoUsuarioComando comando = mock(TipoUsuarioComando.class);
        TipoUsuario tipoUsuario = mock(TipoUsuario.class);
        UUID idEsperado = UUID.randomUUID();

        when(tipoUsuarioFabrica.construir(comando)).thenReturn(tipoUsuario);
        when(guardarTipoUsuarioUseCase.ejecutar(tipoUsuario)).thenReturn(idEsperado);

        ComandoRespuesta<UUID> resultado = manejador.ejecutar(comando);

        assertEquals(idEsperado, resultado.getValor());
        verify(tipoUsuarioFabrica).construir(comando);
        verify(guardarTipoUsuarioUseCase).ejecutar(tipoUsuario);
    }
}
