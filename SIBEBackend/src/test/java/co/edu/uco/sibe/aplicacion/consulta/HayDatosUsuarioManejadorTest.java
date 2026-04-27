package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.puerto.consulta.PersonaRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HayDatosUsuarioManejadorTest {

    @Mock private PersonaRepositorioConsulta usuarioRepositorioConsulta;

    private HayDatosUsuarioManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new HayDatosUsuarioManejador(usuarioRepositorioConsulta);
    }

    @Test
    void deberiaRetornarSiHayDatos() {
        when(usuarioRepositorioConsulta.hayDatos()).thenReturn(true);

        Boolean resultado = manejador.ejecutar();

        assertTrue(resultado);
        verify(usuarioRepositorioConsulta).hayDatos();
    }
}
