package co.edu.uco.sibe.infraestructura.configuracion.dataloader;

import co.edu.uco.sibe.aplicacion.comando.manejador.GuardarDireccionManejador;
import co.edu.uco.sibe.aplicacion.consulta.ConsultarAreaManejador;
import co.edu.uco.sibe.aplicacion.consulta.HayDatosDireccionManejador;
import co.edu.uco.sibe.dominio.modelo.Area;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DireccionDataLoaderTest {

    @Mock private HayDatosDireccionManejador hayDatosDireccionManejador;
    @Mock private GuardarDireccionManejador guardarDireccionManejador;
    @Mock private ConsultarAreaManejador consultarAreaManejador;

    private DireccionDataLoader dataLoader;

    @BeforeEach
    void setUp() {
        dataLoader = new DireccionDataLoader(hayDatosDireccionManejador, guardarDireccionManejador, consultarAreaManejador);
    }

    @Test
    void deberiaCargarDatosCuandoNoExisten() throws Exception {
        when(hayDatosDireccionManejador.ejecutar()).thenReturn(false);
        when(consultarAreaManejador.ejecutar()).thenReturn(List.of(mock(Area.class)));

        dataLoader.run();

        verify(guardarDireccionManejador).ejecutar(any());
    }

    @Test
    void noDeberiaCargarDatosCuandoYaExisten() throws Exception {
        when(hayDatosDireccionManejador.ejecutar()).thenReturn(true);

        dataLoader.run();

        verify(guardarDireccionManejador, never()).ejecutar(any());
    }
}
