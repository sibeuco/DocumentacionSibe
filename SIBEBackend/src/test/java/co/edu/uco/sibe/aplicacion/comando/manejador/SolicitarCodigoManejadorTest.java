package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.dominio.usecase.comando.SolicitarCodigoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SolicitarCodigoManejadorTest {

    @Mock private SolicitarCodigoUseCase solicitarCodigoUseCase;

    private SolicitarCodigoManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new SolicitarCodigoManejador(solicitarCodigoUseCase);
    }

    @Test
    void deberiaEjecutarSolicitarCodigo() {
        UUID idEsperado = UUID.randomUUID();
        when(solicitarCodigoUseCase.ejecutar("test@correo.com")).thenReturn(idEsperado);

        ComandoRespuesta<UUID> resultado = manejador.ejecutar("test@correo.com");

        assertEquals(idEsperado, resultado.getValor());
        verify(solicitarCodigoUseCase).ejecutar("test@correo.com");
    }
}
