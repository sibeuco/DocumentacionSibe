package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.dominio.modelo.PublicoInteres;
import co.edu.uco.sibe.dominio.puerto.consulta.PublicoInteresRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PublicoInteresFabricaTest {

    @Mock
    private PublicoInteresRepositorioConsulta publicoInteresRepositorioConsulta;

    private PublicoInteresFabrica fabrica;

    @BeforeEach
    void setUp() {
        fabrica = new PublicoInteresFabrica(publicoInteresRepositorioConsulta);
    }

    @Test
    void deberiaConstruirPublicoInteresDesdeNombre() {
        when(publicoInteresRepositorioConsulta.consultarPorIdentificador(any())).thenReturn(null);

        PublicoInteres resultado = fabrica.construir("Estudiantes");

        assertNotNull(resultado.getIdentificador());
        assertEquals("Estudiantes", resultado.getNombre());
    }
}
