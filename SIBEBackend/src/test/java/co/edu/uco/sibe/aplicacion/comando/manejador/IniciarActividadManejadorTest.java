package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.dominio.usecase.comando.IniciarActividadUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IniciarActividadManejadorTest {

    @Mock private IniciarActividadUseCase iniciarActividadUseCase;

    private IniciarActividadManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new IniciarActividadManejador(iniciarActividadUseCase);
    }

    @Test
    void deberiaEjecutarIniciarActividad() {
        UUID comando = UUID.randomUUID();
        UUID idEsperado = UUID.randomUUID();
        when(iniciarActividadUseCase.ejecutar(comando)).thenReturn(idEsperado);

        ComandoRespuesta<UUID> resultado = manejador.ejecutar(comando);

        assertEquals(idEsperado, resultado.getValor());
    }
}
