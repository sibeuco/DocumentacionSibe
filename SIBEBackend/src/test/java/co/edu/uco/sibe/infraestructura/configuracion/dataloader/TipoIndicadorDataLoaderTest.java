package co.edu.uco.sibe.infraestructura.configuracion.dataloader;

import co.edu.uco.sibe.aplicacion.comando.manejador.GuardarTipoIndicadorManejador;
import co.edu.uco.sibe.aplicacion.consulta.HayDatosTipoIndicadorManejador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TipoIndicadorDataLoaderTest {

    @Mock private HayDatosTipoIndicadorManejador hayDatosTipoIndicadorManejador;
    @Mock private GuardarTipoIndicadorManejador guardarTipoIndicadorManejador;

    private TipoIndicadorDataLoader dataLoader;

    @BeforeEach
    void setUp() {
        dataLoader = new TipoIndicadorDataLoader(hayDatosTipoIndicadorManejador, guardarTipoIndicadorManejador);
    }

    @Test
    void deberiaCargarDatosCuandoNoExisten() throws Exception {
        when(hayDatosTipoIndicadorManejador.ejecutar()).thenReturn(false);

        dataLoader.run();

        verify(guardarTipoIndicadorManejador, atLeastOnce()).ejecutar(any());
    }

    @Test
    void noDeberiaCargarDatosCuandoYaExisten() throws Exception {
        when(hayDatosTipoIndicadorManejador.ejecutar()).thenReturn(true);

        dataLoader.run();

        verify(guardarTipoIndicadorManejador, never()).ejecutar(any());
    }
}
