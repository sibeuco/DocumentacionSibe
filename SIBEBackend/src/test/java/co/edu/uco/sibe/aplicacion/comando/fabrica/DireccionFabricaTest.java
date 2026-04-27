package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.dominio.modelo.Direccion;
import co.edu.uco.sibe.dominio.puerto.consulta.DireccionRepositorioConsulta;
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
class DireccionFabricaTest {

    @Mock
    private DireccionRepositorioConsulta direccionRepositorioConsulta;

    private DireccionFabrica fabrica;

    @BeforeEach
    void setUp() {
        fabrica = new DireccionFabrica(direccionRepositorioConsulta);
    }

    @Test
    void deberiaConstruirDireccionDesdeParametros() {
        when(direccionRepositorioConsulta.consultarPorIdentificador(any())).thenReturn(null);

        Direccion resultado = fabrica.construir("Direccion de Bienestar", new ArrayList<>(), new ArrayList<>());

        assertNotNull(resultado.getIdentificador());
        assertEquals("Direccion de Bienestar", resultado.getNombre());
    }
}
