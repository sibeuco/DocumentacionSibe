package co.edu.uco.sibe.infraestructura.configuracion.dataloader;

import co.edu.uco.sibe.aplicacion.comando.manejador.GuardarAreaManejador;
import co.edu.uco.sibe.aplicacion.consulta.HayDatosAreaManejador;
import co.edu.uco.sibe.infraestructura.adaptador.dao.SubareaDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.SubareaEntidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AreaDataLoaderTest {

    @Mock private HayDatosAreaManejador hayDatosAreaManejador;
    @Mock private GuardarAreaManejador guardarAreaManejador;
    @Mock private SubareaDAO subareaDAO;

    private AreaDataLoader dataLoader;

    @BeforeEach
    void setUp() {
        dataLoader = new AreaDataLoader(hayDatosAreaManejador, guardarAreaManejador, subareaDAO);
    }

    @Test
    void deberiaCargarDatosCuandoNoExistenAreas() throws Exception {
        SubareaEntidad entidad = new SubareaEntidad();
        entidad.setIdentificador(UUID.randomUUID());
        entidad.setNombre("Subarea Test");

        when(hayDatosAreaManejador.ejecutar()).thenReturn(false);
        when(subareaDAO.findAll()).thenReturn(List.of(entidad));

        dataLoader.run();

        verify(guardarAreaManejador, times(4)).ejecutar(any());
    }

    @Test
    void noDeberiaCargarDatosCuandoYaExistenAreas() throws Exception {
        when(hayDatosAreaManejador.ejecutar()).thenReturn(true);

        dataLoader.run();

        verify(guardarAreaManejador, never()).ejecutar(any());
    }
}
