package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.puerto.consulta.AreaRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HayDatosAreaManejadorTest {

    @Mock private AreaRepositorioConsulta areaRepositorioConsulta;

    private HayDatosAreaManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new HayDatosAreaManejador(areaRepositorioConsulta);
    }

    @Test
    void deberiaRetornarSiHayDatos() {
        when(areaRepositorioConsulta.hayDatos()).thenReturn(true);

        Boolean resultado = manejador.ejecutar();

        assertTrue(resultado);
        verify(areaRepositorioConsulta).hayDatos();
    }
}
