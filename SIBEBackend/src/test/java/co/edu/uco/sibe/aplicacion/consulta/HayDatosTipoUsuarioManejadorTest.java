package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.puerto.consulta.TipoUsuarioRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HayDatosTipoUsuarioManejadorTest {

    @Mock private TipoUsuarioRepositorioConsulta tipoUsuarioRepositorioConsulta;

    private HayDatosTipoUsuarioManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new HayDatosTipoUsuarioManejador(tipoUsuarioRepositorioConsulta);
    }

    @Test
    void deberiaRetornarSiHayDatos() {
        when(tipoUsuarioRepositorioConsulta.hayDatos()).thenReturn(true);

        Boolean resultado = manejador.ejecutar();

        assertTrue(resultado);
        verify(tipoUsuarioRepositorioConsulta).hayDatos();
    }
}
