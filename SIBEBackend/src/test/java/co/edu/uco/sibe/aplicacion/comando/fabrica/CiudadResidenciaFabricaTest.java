package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.dominio.modelo.CiudadResidencia;
import co.edu.uco.sibe.dominio.puerto.consulta.CiudadResidenciaRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CiudadResidenciaFabricaTest {

    @Mock
    private CiudadResidenciaRepositorioConsulta ciudadResidenciaRepositorioConsulta;

    private CiudadResidenciaFabrica fabrica;

    @BeforeEach
    void setUp() {
        fabrica = new CiudadResidenciaFabrica(ciudadResidenciaRepositorioConsulta);
    }

    @Test
    void deberiaConstruirNuevaCiudadCuandoNoExiste() {
        when(ciudadResidenciaRepositorioConsulta.consultarPorDescripcion("Medellin")).thenReturn(null);
        when(ciudadResidenciaRepositorioConsulta.consultarPorIdentificador(any())).thenReturn(null);

        CiudadResidencia resultado = fabrica.construir("Medellin");

        assertNotNull(resultado.getIdentificador());
        assertEquals("Medellin", resultado.getDescripcion());
    }

    @Test
    void deberiaRetornarCiudadExistenteCuandoYaExiste() {
        CiudadResidencia existente = CiudadResidencia.construir(java.util.UUID.randomUUID(), "Medellin");
        when(ciudadResidenciaRepositorioConsulta.consultarPorDescripcion("Medellin")).thenReturn(existente);

        CiudadResidencia resultado = fabrica.construir("Medellin");

        assertEquals(existente, resultado);
    }
}
