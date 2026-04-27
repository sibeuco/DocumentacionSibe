package co.edu.uco.sibe.infraestructura.configuracion.dataloader;

import co.edu.uco.sibe.aplicacion.comando.manejador.GuardarTipoUsuarioManejador;
import co.edu.uco.sibe.aplicacion.consulta.HayDatosTipoUsuarioManejador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TipoUsuarioDataLoaderTest {

    @Mock private HayDatosTipoUsuarioManejador hayDatosTipoUsuarioManejador;
    @Mock private GuardarTipoUsuarioManejador guardarTipoUsuarioManejador;

    private TipoUsuarioDataLoader dataLoader;

    @BeforeEach
    void setUp() {
        dataLoader = new TipoUsuarioDataLoader(hayDatosTipoUsuarioManejador, guardarTipoUsuarioManejador);
    }

    @Test
    void deberiaCargarDatosCuandoNoExisten() throws Exception {
        when(hayDatosTipoUsuarioManejador.ejecutar()).thenReturn(false);

        dataLoader.run();

        verify(guardarTipoUsuarioManejador, times(3)).ejecutar(any());
    }

    @Test
    void noDeberiaCargarDatosCuandoYaExisten() throws Exception {
        when(hayDatosTipoUsuarioManejador.ejecutar()).thenReturn(true);

        dataLoader.run();

        verify(guardarTipoUsuarioManejador, never()).ejecutar(any());
    }
}
