package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.puerto.consulta.SubareaRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HayDatosSubareaManejadorTest {

    @Mock private SubareaRepositorioConsulta subareaRepositorioConsulta;

    private HayDatosSubareaManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new HayDatosSubareaManejador(subareaRepositorioConsulta);
    }

    @Test
    void deberiaRetornarSiHayDatos() {
        when(subareaRepositorioConsulta.hayDatos()).thenReturn(true);

        Boolean resultado = manejador.ejecutar();

        assertTrue(resultado);
        verify(subareaRepositorioConsulta).hayDatos();
    }
}
