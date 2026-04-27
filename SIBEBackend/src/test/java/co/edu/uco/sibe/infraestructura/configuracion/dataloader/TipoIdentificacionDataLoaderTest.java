package co.edu.uco.sibe.infraestructura.configuracion.dataloader;

import co.edu.uco.sibe.aplicacion.comando.manejador.GuardarTipoIdentificacionManejador;
import co.edu.uco.sibe.aplicacion.consulta.HayDatosTipoIdentificacionManejador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TipoIdentificacionDataLoaderTest {

    @Mock private HayDatosTipoIdentificacionManejador hayDatosTipoIdentificacionManejador;
    @Mock private GuardarTipoIdentificacionManejador guardarTipoIdentificacionManejador;

    private TipoIdentificacionDataLoader dataLoader;

    @BeforeEach
    void setUp() {
        dataLoader = new TipoIdentificacionDataLoader(hayDatosTipoIdentificacionManejador, guardarTipoIdentificacionManejador);
    }

    @Test
    void deberiaCargarDatosCuandoNoExisten() throws Exception {
        when(hayDatosTipoIdentificacionManejador.ejecutar()).thenReturn(false);

        dataLoader.run();

        verify(guardarTipoIdentificacionManejador, times(3)).ejecutar(any());
    }

    @Test
    void noDeberiaCargarDatosCuandoYaExisten() throws Exception {
        when(hayDatosTipoIdentificacionManejador.ejecutar()).thenReturn(true);

        dataLoader.run();

        verify(guardarTipoIdentificacionManejador, never()).ejecutar(any());
    }
}
