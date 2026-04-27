package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.puerto.consulta.TipoIndicadorRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HayDatosTipoIndicadorManejadorTest {

    @Mock private TipoIndicadorRepositorioConsulta tipoIndicadorRepositorioConsulta;

    private HayDatosTipoIndicadorManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new HayDatosTipoIndicadorManejador(tipoIndicadorRepositorioConsulta);
    }

    @Test
    void deberiaRetornarSiHayDatos() {
        when(tipoIndicadorRepositorioConsulta.hayDatos()).thenReturn(true);

        Boolean resultado = manejador.ejecutar();

        assertTrue(resultado);
        verify(tipoIndicadorRepositorioConsulta).hayDatos();
    }
}
