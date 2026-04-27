package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.dominio.usecase.comando.EliminarUsuarioUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EliminarPersonaManejadorTest {

    @Mock private EliminarUsuarioUseCase eliminarUsuarioUseCase;

    private EliminarPersonaManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new EliminarPersonaManejador(eliminarUsuarioUseCase);
    }

    @Test
    void deberiaEjecutarEliminarPersona() {
        UUID parametro = UUID.randomUUID();
        UUID idEsperado = UUID.randomUUID();
        when(eliminarUsuarioUseCase.ejecutar(parametro)).thenReturn(idEsperado);

        ComandoRespuesta<UUID> resultado = manejador.ejecutar(parametro);

        assertEquals(idEsperado, resultado.getValor());
    }
}
