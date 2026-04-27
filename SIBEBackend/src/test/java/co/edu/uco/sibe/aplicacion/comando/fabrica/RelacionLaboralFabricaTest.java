package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.dominio.modelo.RelacionLaboral;
import co.edu.uco.sibe.dominio.puerto.consulta.RelacionLaboralRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RelacionLaboralFabricaTest {

    @Mock
    private RelacionLaboralRepositorioConsulta relacionLaboralRepositorioConsulta;

    private RelacionLaboralFabrica fabrica;

    @BeforeEach
    void setUp() {
        fabrica = new RelacionLaboralFabrica(relacionLaboralRepositorioConsulta);
    }

    @Test
    void deberiaConstruirNuevaRelacionCuandoNoExiste() {
        when(relacionLaboralRepositorioConsulta.consultarPorCodigo("PLT")).thenReturn(null);
        when(relacionLaboralRepositorioConsulta.consultarPorIdentificador(any())).thenReturn(null);

        RelacionLaboral resultado = fabrica.construir("PLT", "Planta");

        assertNotNull(resultado.getIdentificador());
        assertEquals("PLT", resultado.getCodigo());
        assertEquals("Planta", resultado.getDescripcion());
    }

    @Test
    void deberiaRetornarRelacionExistenteCuandoYaExiste() {
        RelacionLaboral existente = RelacionLaboral.construir(java.util.UUID.randomUUID(), "PLT", "Planta");
        when(relacionLaboralRepositorioConsulta.consultarPorCodigo("PLT")).thenReturn(existente);

        RelacionLaboral resultado = fabrica.construir("PLT", "Planta");

        assertEquals(existente, resultado);
    }
}
