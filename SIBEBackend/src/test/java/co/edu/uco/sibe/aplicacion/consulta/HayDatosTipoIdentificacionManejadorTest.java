package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.puerto.consulta.TipoIdentificacionRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HayDatosTipoIdentificacionManejadorTest {

    @Mock private TipoIdentificacionRepositorioConsulta tipoIdentificacionRepositorioConsulta;

    private HayDatosTipoIdentificacionManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new HayDatosTipoIdentificacionManejador(tipoIdentificacionRepositorioConsulta);
    }

    @Test
    void deberiaRetornarSiHayDatos() {
        when(tipoIdentificacionRepositorioConsulta.hayDatos()).thenReturn(true);

        Boolean resultado = manejador.ejecutar();

        assertTrue(resultado);
        verify(tipoIdentificacionRepositorioConsulta).hayDatos();
    }
}
