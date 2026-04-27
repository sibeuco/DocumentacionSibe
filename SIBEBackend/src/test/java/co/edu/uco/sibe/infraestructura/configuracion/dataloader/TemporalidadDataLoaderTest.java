package co.edu.uco.sibe.infraestructura.configuracion.dataloader;

import co.edu.uco.sibe.aplicacion.comando.manejador.GuardarTemporalidadManejador;
import co.edu.uco.sibe.aplicacion.consulta.HayDatosTemporalidadManejador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TemporalidadDataLoaderTest {

    @Mock private HayDatosTemporalidadManejador hayDatosTemporalidadManejador;
    @Mock private GuardarTemporalidadManejador guardarTemporalidadManejador;

    private TemporalidadDataLoader dataLoader;

    @BeforeEach
    void setUp() {
        dataLoader = new TemporalidadDataLoader(hayDatosTemporalidadManejador, guardarTemporalidadManejador);
    }

    @Test
    void deberiaCargarDatosCuandoNoExisten() throws Exception {
        when(hayDatosTemporalidadManejador.ejecutar()).thenReturn(false);

        dataLoader.run();

        verify(guardarTemporalidadManejador, atLeastOnce()).ejecutar(any());
    }

    @Test
    void noDeberiaCargarDatosCuandoYaExisten() throws Exception {
        when(hayDatosTemporalidadManejador.ejecutar()).thenReturn(true);

        dataLoader.run();

        verify(guardarTemporalidadManejador, never()).ejecutar(any());
    }
}
