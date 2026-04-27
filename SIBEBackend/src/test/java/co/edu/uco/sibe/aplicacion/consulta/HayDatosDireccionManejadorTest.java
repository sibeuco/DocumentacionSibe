package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.puerto.consulta.DireccionRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HayDatosDireccionManejadorTest {

    @Mock private DireccionRepositorioConsulta direccionRepositorioConsulta;

    private HayDatosDireccionManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new HayDatosDireccionManejador(direccionRepositorioConsulta);
    }

    @Test
    void deberiaRetornarSiHayDatos() {
        when(direccionRepositorioConsulta.hayDatos()).thenReturn(true);

        Boolean resultado = manejador.ejecutar();

        assertTrue(resultado);
        verify(direccionRepositorioConsulta).hayDatos();
    }
}
