package co.edu.uco.sibe.infraestructura.controlador.consulta;

import co.edu.uco.sibe.aplicacion.consulta.ConsultarTemporalidadesManejador;
import co.edu.uco.sibe.dominio.dto.TemporalidadDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TemporalidadConsultaControladorTest {

    @Mock
    private ConsultarTemporalidadesManejador consultarTemporalidadesManejador;

    private TemporalidadConsultaControlador controlador;

    @BeforeEach
    void setUp() {
        controlador = new TemporalidadConsultaControlador(consultarTemporalidadesManejador);
    }

    @Test
    void deberiaConsultarTodos() {
        List<TemporalidadDTO> temporalidades = List.of(new TemporalidadDTO("1", "Mensual"));
        when(consultarTemporalidadesManejador.ejecutar()).thenReturn(temporalidades);

        List<TemporalidadDTO> resultado = controlador.consultarTodos();

        assertEquals(1, resultado.size());
        verify(consultarTemporalidadesManejador).ejecutar();
    }
}
