package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.TemporalidadDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.TemporalidadRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarTemporalidadesManejadorTest {

    @Mock private TemporalidadRepositorioConsulta temporalidadRepositorioConsulta;

    private ConsultarTemporalidadesManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarTemporalidadesManejador(temporalidadRepositorioConsulta);
    }

    @Test
    void deberiaConsultarTemporalidades() {
        List<TemporalidadDTO> esperado = List.of(mock(TemporalidadDTO.class));
        when(temporalidadRepositorioConsulta.consultarDTOs()).thenReturn(esperado);

        List<TemporalidadDTO> resultado = manejador.ejecutar();

        assertEquals(esperado, resultado);
        verify(temporalidadRepositorioConsulta).consultarDTOs();
    }
}
