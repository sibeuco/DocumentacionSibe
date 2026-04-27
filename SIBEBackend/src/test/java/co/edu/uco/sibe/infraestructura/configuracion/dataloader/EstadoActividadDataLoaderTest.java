package co.edu.uco.sibe.infraestructura.configuracion.dataloader;

import co.edu.uco.sibe.aplicacion.comando.manejador.GuardarEstadoActividadManejador;
import co.edu.uco.sibe.aplicacion.consulta.HayDatosEstadoActividadManejador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstadoActividadDataLoaderTest {

    @Mock private HayDatosEstadoActividadManejador hayDatosEstadoActividadManejador;
    @Mock private GuardarEstadoActividadManejador guardarEstadoActividadManejador;

    private EstadoActividadDataLoader dataLoader;

    @BeforeEach
    void setUp() {
        dataLoader = new EstadoActividadDataLoader(hayDatosEstadoActividadManejador, guardarEstadoActividadManejador);
    }

    @Test
    void deberiaCargarDatosCuandoNoExisten() throws Exception {
        when(hayDatosEstadoActividadManejador.ejecutar()).thenReturn(false);

        dataLoader.run();

        verify(guardarEstadoActividadManejador, atLeastOnce()).ejecutar(any());
    }

    @Test
    void noDeberiaCargarDatosCuandoYaExisten() throws Exception {
        when(hayDatosEstadoActividadManejador.ejecutar()).thenReturn(true);

        dataLoader.run();

        verify(guardarEstadoActividadManejador, never()).ejecutar(any());
    }
}
