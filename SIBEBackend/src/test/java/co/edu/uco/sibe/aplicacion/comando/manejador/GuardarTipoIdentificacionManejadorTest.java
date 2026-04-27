package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.TipoIdentificacionComando;
import co.edu.uco.sibe.aplicacion.comando.fabrica.TipoIdentificacionFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.dominio.modelo.TipoIdentificacion;
import co.edu.uco.sibe.dominio.usecase.comando.GuardarTipoIdentificacionUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuardarTipoIdentificacionManejadorTest {

    @Mock private GuardarTipoIdentificacionUseCase guardarTipoIdentificacionUseCase;
    @Mock private TipoIdentificacionFabrica tipoIdentificacionFabrica;

    private GuardarTipoIdentificacionManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new GuardarTipoIdentificacionManejador(guardarTipoIdentificacionUseCase, tipoIdentificacionFabrica);
    }

    @Test
    void deberiaEjecutarGuardarTipoIdentificacion() {
        TipoIdentificacionComando comando = mock(TipoIdentificacionComando.class);
        TipoIdentificacion tipoIdentificacion = mock(TipoIdentificacion.class);
        UUID idEsperado = UUID.randomUUID();

        when(tipoIdentificacionFabrica.construir(comando)).thenReturn(tipoIdentificacion);
        when(guardarTipoIdentificacionUseCase.ejecutar(tipoIdentificacion)).thenReturn(idEsperado);

        ComandoRespuesta<UUID> resultado = manejador.ejecutar(comando);

        assertEquals(idEsperado, resultado.getValor());
        verify(tipoIdentificacionFabrica).construir(comando);
        verify(guardarTipoIdentificacionUseCase).ejecutar(tipoIdentificacion);
    }
}
