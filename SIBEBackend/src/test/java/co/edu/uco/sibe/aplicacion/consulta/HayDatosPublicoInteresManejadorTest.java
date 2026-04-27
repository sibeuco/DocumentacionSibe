package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.puerto.consulta.PublicoInteresRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HayDatosPublicoInteresManejadorTest {

    @Mock private PublicoInteresRepositorioConsulta publicoInteresRepositorioConsulta;

    private HayDatosPublicoInteresManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new HayDatosPublicoInteresManejador(publicoInteresRepositorioConsulta);
    }

    @Test
    void deberiaRetornarSiHayDatos() {
        when(publicoInteresRepositorioConsulta.hayDatos()).thenReturn(true);

        Boolean resultado = manejador.ejecutar();

        assertTrue(resultado);
        verify(publicoInteresRepositorioConsulta).hayDatos();
    }
}
