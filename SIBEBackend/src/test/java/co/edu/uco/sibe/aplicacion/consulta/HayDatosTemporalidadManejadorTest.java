package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.puerto.consulta.TemporalidadRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HayDatosTemporalidadManejadorTest {

    @Mock private TemporalidadRepositorioConsulta temporalidadRepositorioConsulta;

    private HayDatosTemporalidadManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new HayDatosTemporalidadManejador(temporalidadRepositorioConsulta);
    }

    @Test
    void deberiaRetornarSiHayDatos() {
        when(temporalidadRepositorioConsulta.hayDatos()).thenReturn(true);

        Boolean resultado = manejador.ejecutar();

        assertTrue(resultado);
        verify(temporalidadRepositorioConsulta).hayDatos();
    }
}
