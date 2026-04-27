package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.modelo.Area;
import co.edu.uco.sibe.dominio.puerto.consulta.AreaRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarAreaManejadorTest {

    @Mock private AreaRepositorioConsulta areaRepositorioConsulta;

    private ConsultarAreaManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarAreaManejador(areaRepositorioConsulta);
    }

    @Test
    void deberiaConsultarAreas() {
        List<Area> esperado = List.of(mock(Area.class));
        when(areaRepositorioConsulta.consultarTodos()).thenReturn(esperado);

        List<Area> resultado = manejador.ejecutar();

        assertEquals(esperado, resultado);
        verify(areaRepositorioConsulta).consultarTodos();
    }
}
