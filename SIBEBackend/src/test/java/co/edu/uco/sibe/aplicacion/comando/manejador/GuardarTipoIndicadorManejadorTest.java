package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.TipoIndicadorComando;
import co.edu.uco.sibe.aplicacion.comando.fabrica.TipoIndicadorFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.dominio.modelo.TipoIndicador;
import co.edu.uco.sibe.dominio.usecase.comando.GuardarTipoIndicadorUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuardarTipoIndicadorManejadorTest {

    @Mock private GuardarTipoIndicadorUseCase guardarTipoIndicadorUseCase;
    @Mock private TipoIndicadorFabrica tipoIndicadorFabrica;

    private GuardarTipoIndicadorManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new GuardarTipoIndicadorManejador(guardarTipoIndicadorUseCase, tipoIndicadorFabrica);
    }

    @Test
    void deberiaEjecutarGuardarTipoIndicador() {
        TipoIndicadorComando comando = mock(TipoIndicadorComando.class);
        TipoIndicador tipoIndicador = mock(TipoIndicador.class);
        UUID idEsperado = UUID.randomUUID();

        when(tipoIndicadorFabrica.construir(comando)).thenReturn(tipoIndicador);
        when(guardarTipoIndicadorUseCase.ejecutar(tipoIndicador)).thenReturn(idEsperado);

        ComandoRespuesta<UUID> resultado = manejador.ejecutar(comando);

        assertEquals(idEsperado, resultado.getValor());
        verify(tipoIndicadorFabrica).construir(comando);
        verify(guardarTipoIndicadorUseCase).ejecutar(tipoIndicador);
    }
}
