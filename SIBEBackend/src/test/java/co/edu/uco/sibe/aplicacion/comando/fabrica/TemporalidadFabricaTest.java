package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.dominio.modelo.Temporalidad;
import co.edu.uco.sibe.dominio.puerto.consulta.TemporalidadRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TemporalidadFabricaTest {

    @Mock
    private TemporalidadRepositorioConsulta temporalidadRepositorioConsulta;

    private TemporalidadFabrica fabrica;

    @BeforeEach
    void setUp() {
        fabrica = new TemporalidadFabrica(temporalidadRepositorioConsulta);
    }

    @Test
    void deberiaConstruirTemporalidadDesdeNombre() {
        when(temporalidadRepositorioConsulta.consultarPorIdentificador(any())).thenReturn(null);

        Temporalidad resultado = fabrica.construir("Semestral");

        assertNotNull(resultado.getIdentificador());
        assertEquals("Semestral", resultado.getNombre());
    }
}
