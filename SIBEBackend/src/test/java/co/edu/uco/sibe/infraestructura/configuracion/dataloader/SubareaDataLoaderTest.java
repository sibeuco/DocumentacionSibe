package co.edu.uco.sibe.infraestructura.configuracion.dataloader;

import co.edu.uco.sibe.aplicacion.comando.manejador.GuardarSubareaManejador;
import co.edu.uco.sibe.aplicacion.consulta.HayDatosSubareaManejador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubareaDataLoaderTest {

    @Mock private HayDatosSubareaManejador hayDatosSubareaManejador;
    @Mock private GuardarSubareaManejador guardarSubareaManejador;

    private SubareaDataLoader dataLoader;

    @BeforeEach
    void setUp() {
        dataLoader = new SubareaDataLoader(hayDatosSubareaManejador, guardarSubareaManejador);
    }

    @Test
    void deberiaCargarDatosCuandoNoExisten() throws Exception {
        when(hayDatosSubareaManejador.ejecutar()).thenReturn(false);

        dataLoader.run();

        verify(guardarSubareaManejador, atLeastOnce()).ejecutar(any());
    }

    @Test
    void noDeberiaCargarDatosCuandoYaExisten() throws Exception {
        when(hayDatosSubareaManejador.ejecutar()).thenReturn(true);

        dataLoader.run();

        verify(guardarSubareaManejador, never()).ejecutar(any());
    }
}
