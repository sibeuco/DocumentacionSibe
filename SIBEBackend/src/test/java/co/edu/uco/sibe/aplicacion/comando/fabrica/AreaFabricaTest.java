package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.dominio.modelo.Area;
import co.edu.uco.sibe.dominio.puerto.consulta.AreaRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AreaFabricaTest {

    @Mock
    private AreaRepositorioConsulta areaRepositorioConsulta;

    private AreaFabrica fabrica;

    @BeforeEach
    void setUp() {
        fabrica = new AreaFabrica(areaRepositorioConsulta);
    }

    @Test
    void deberiaConstruirAreaDesdeParametros() {
        when(areaRepositorioConsulta.consultarPorIdentificador(any())).thenReturn(null);

        Area resultado = fabrica.construir("Area de Salud", new ArrayList<>(), new ArrayList<>());

        assertNotNull(resultado.getIdentificador());
        assertEquals("Area de Salud", resultado.getNombre());
    }
}
