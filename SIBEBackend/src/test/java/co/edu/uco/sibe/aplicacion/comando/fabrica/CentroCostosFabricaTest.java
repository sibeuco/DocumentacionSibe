package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.dominio.modelo.CentroCostos;
import co.edu.uco.sibe.dominio.puerto.consulta.CentroCostosRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CentroCostosFabricaTest {

    @Mock
    private CentroCostosRepositorioConsulta centroCostosRepositorioConsulta;

    private CentroCostosFabrica fabrica;

    @BeforeEach
    void setUp() {
        fabrica = new CentroCostosFabrica(centroCostosRepositorioConsulta);
    }

    @Test
    void deberiaConstruirNuevoCentroCuandoNoExiste() {
        when(centroCostosRepositorioConsulta.consultarPorCodigo("CC01")).thenReturn(null);
        when(centroCostosRepositorioConsulta.consultarPorIdentificador(any())).thenReturn(null);

        CentroCostos resultado = fabrica.construir("CC01", "Centro Principal");

        assertNotNull(resultado.getIdentificador());
        assertEquals("CC01", resultado.getCodigo());
        assertEquals("Centro Principal", resultado.getDescripcion());
    }

    @Test
    void deberiaRetornarCentroExistenteCuandoYaExiste() {
        CentroCostos existente = CentroCostos.construir(java.util.UUID.randomUUID(), "CC01", "Centro Principal");
        when(centroCostosRepositorioConsulta.consultarPorCodigo("CC01")).thenReturn(existente);

        CentroCostos resultado = fabrica.construir("CC01", "Centro Principal");

        assertEquals(existente, resultado);
    }
}
