package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.aplicacion.comando.TipoIdentificacionComando;
import co.edu.uco.sibe.dominio.modelo.TipoIdentificacion;
import co.edu.uco.sibe.dominio.puerto.consulta.TipoIdentificacionRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TipoIdentificacionFabricaTest {

    @Mock
    private TipoIdentificacionRepositorioConsulta tipoIdentificacionRepositorioConsulta;

    private TipoIdentificacionFabrica fabrica;

    @BeforeEach
    void setUp() {
        fabrica = new TipoIdentificacionFabrica(tipoIdentificacionRepositorioConsulta);
    }

    @Test
    void deberiaConstruirTipoIdentificacionDesdeComando() {
        when(tipoIdentificacionRepositorioConsulta.consultarPorIdentificador(any())).thenReturn(null);
        TipoIdentificacionComando comando = new TipoIdentificacionComando("CC", "Cedula Ciudadania");

        TipoIdentificacion resultado = fabrica.construir(comando);

        assertNotNull(resultado.getIdentificador());
        assertEquals("CC", resultado.getSigla());
        assertEquals("Cedula Ciudadania", resultado.getDescripcion());
    }
}
