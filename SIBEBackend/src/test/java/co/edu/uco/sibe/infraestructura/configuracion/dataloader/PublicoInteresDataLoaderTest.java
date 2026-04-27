package co.edu.uco.sibe.infraestructura.configuracion.dataloader;

import co.edu.uco.sibe.aplicacion.comando.manejador.GuardarPublicoInteresManejador;
import co.edu.uco.sibe.aplicacion.consulta.HayDatosPublicoInteresManejador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PublicoInteresDataLoaderTest {

    @Mock private HayDatosPublicoInteresManejador hayDatosPublicoInteresManejador;
    @Mock private GuardarPublicoInteresManejador guardarPublicoInteresManejador;

    private PublicoInteresDataLoader dataLoader;

    @BeforeEach
    void setUp() {
        dataLoader = new PublicoInteresDataLoader(hayDatosPublicoInteresManejador, guardarPublicoInteresManejador);
    }

    @Test
    void deberiaCargarDatosCuandoNoExisten() throws Exception {
        when(hayDatosPublicoInteresManejador.ejecutar()).thenReturn(false);

        dataLoader.run();

        verify(guardarPublicoInteresManejador, atLeastOnce()).ejecutar(any());
    }

    @Test
    void noDeberiaCargarDatosCuandoYaExisten() throws Exception {
        when(hayDatosPublicoInteresManejador.ejecutar()).thenReturn(true);

        dataLoader.run();

        verify(guardarPublicoInteresManejador, never()).ejecutar(any());
    }
}
