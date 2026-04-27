package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.dominio.modelo.Identificacion;
import co.edu.uco.sibe.dominio.modelo.TipoIdentificacion;
import co.edu.uco.sibe.dominio.puerto.consulta.IdentificacionRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.TipoIdentificacionRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IdentificacionFabricaTest {

    @Mock
    private TipoIdentificacionRepositorioConsulta tipoIdentificacionRepositorioConsulta;
    @Mock
    private IdentificacionRepositorioConsulta identificacionRepositorioConsulta;

    private IdentificacionFabrica fabrica;

    @BeforeEach
    void setUp() {
        fabrica = new IdentificacionFabrica(tipoIdentificacionRepositorioConsulta, identificacionRepositorioConsulta);
    }

    @Test
    void deberiaConstruirIdentificacion() {
        UUID tipoIdUUID = UUID.randomUUID();
        TipoIdentificacion tipo = TipoIdentificacion.construir(tipoIdUUID, "CC", "Cedula Ciudadania");
        when(identificacionRepositorioConsulta.consultarPorIdentificador(any())).thenReturn(null);
        when(tipoIdentificacionRepositorioConsulta.consultarPorIdentificador(tipoIdUUID)).thenReturn(tipo);

        Identificacion resultado = fabrica.construir("123456789", tipoIdUUID.toString());

        assertNotNull(resultado.getIdentificador());
        assertEquals("123456789", resultado.getNumeroIdentificacion());
        assertEquals(tipo, resultado.getTipoIdentificacion());
    }

    @Test
    void deberiaConstruirActualizarIdentificacion() {
        UUID tipoIdUUID = UUID.randomUUID();
        UUID idIdentificacion = UUID.randomUUID();
        TipoIdentificacion tipo = TipoIdentificacion.construir(tipoIdUUID, "CC", "Cedula Ciudadania");
        when(tipoIdentificacionRepositorioConsulta.consultarPorIdentificador(tipoIdUUID)).thenReturn(tipo);

        Identificacion resultado = fabrica.construirActualizar("987654321", tipoIdUUID.toString(), idIdentificacion);

        assertEquals(idIdentificacion, resultado.getIdentificador());
        assertEquals("987654321", resultado.getNumeroIdentificacion());
    }
}
