package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.dominio.modelo.Subarea;
import co.edu.uco.sibe.dominio.puerto.consulta.SubareaRepositorioConsulta;
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
class SubareaFabricaTest {

    @Mock
    private SubareaRepositorioConsulta subareaRepositorioConsulta;

    private SubareaFabrica fabrica;

    @BeforeEach
    void setUp() {
        fabrica = new SubareaFabrica(subareaRepositorioConsulta);
    }

    @Test
    void deberiaConstruirSubareaDesdeParametros() {
        when(subareaRepositorioConsulta.consultarPorIdentificador(any())).thenReturn(null);

        Subarea resultado = fabrica.construir("Deporte Formativo", new ArrayList<>());

        assertNotNull(resultado.getIdentificador());
        assertEquals("Deporte Formativo", resultado.getNombre());
    }
}
