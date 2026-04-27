package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.dominio.modelo.EstadoActividad;
import co.edu.uco.sibe.dominio.puerto.consulta.EstadoActividadRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EstadoActividadFabricaTest {

    @Mock
    private EstadoActividadRepositorioConsulta estadoActividadRepositorioConsulta;

    private EstadoActividadFabrica fabrica;

    @BeforeEach
    void setUp() {
        fabrica = new EstadoActividadFabrica(estadoActividadRepositorioConsulta);
    }

    @Test
    void deberiaConstruirEstadoActividadDesdeNombre() {
        when(estadoActividadRepositorioConsulta.consultarPorIdentificador(any())).thenReturn(null);

        EstadoActividad resultado = fabrica.construir("Activa");

        assertNotNull(resultado.getIdentificador());
        assertEquals("Activa", resultado.getNombre());
    }
}
