package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.SubareaDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.SubareaRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarSubareasDTOManejadorTest {

    @Mock private SubareaRepositorioConsulta subareaRepositorioConsulta;

    private ConsultarSubareasDTOManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarSubareasDTOManejador(subareaRepositorioConsulta);
    }

    @Test
    void deberiaConsultarSubareasDTO() {
        List<SubareaDTO> esperado = List.of(mock(SubareaDTO.class));
        when(subareaRepositorioConsulta.consultarDTOs()).thenReturn(esperado);

        List<SubareaDTO> resultado = manejador.ejecutar();

        assertEquals(esperado, resultado);
        verify(subareaRepositorioConsulta).consultarDTOs();
    }
}
