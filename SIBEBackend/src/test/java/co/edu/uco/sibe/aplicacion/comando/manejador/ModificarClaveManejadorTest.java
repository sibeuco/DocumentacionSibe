package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.ClaveModificacionComando;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.dominio.usecase.comando.ModificarClaveUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModificarClaveManejadorTest {

    @Mock private ModificarClaveUseCase modificarClaveUseCase;

    private ModificarClaveManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ModificarClaveManejador(modificarClaveUseCase);
    }

    @Test
    void deberiaEjecutarModificarClave() {
        UUID identificadorUUID = UUID.randomUUID();
        UUID idEsperado = UUID.randomUUID();
        ClaveModificacionComando comando = mock(ClaveModificacionComando.class);

        when(comando.getClaveAntigua()).thenReturn("claveVieja");
        when(comando.getClaveNueva()).thenReturn("claveNueva");
        when(comando.getIdentificador()).thenReturn(identificadorUUID.toString());
        when(modificarClaveUseCase.ejecutar("claveVieja", "claveNueva", identificadorUUID)).thenReturn(idEsperado);

        ComandoRespuesta<UUID> resultado = manejador.ejecutar(comando);

        assertEquals(idEsperado, resultado.getValor());
        verify(modificarClaveUseCase).ejecutar("claveVieja", "claveNueva", identificadorUUID);
    }
}
