package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.ActividadDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarActividadesPorSubareaUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarActividadesPorSubareaManejadorTest {

    @Mock private ConsultarActividadesPorSubareaUseCase consultarActividadesPorSubareaUseCase;

    private ConsultarActividadesPorSubareaManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarActividadesPorSubareaManejador(consultarActividadesPorSubareaUseCase);
    }

    @Test
    void deberiaConsultarActividadesPorSubarea() {
        List<ActividadDTO> esperado = List.of(mock(ActividadDTO.class));
        when(consultarActividadesPorSubareaUseCase.ejecutar("subarea1")).thenReturn(esperado);

        List<ActividadDTO> resultado = manejador.ejecutar("subarea1");

        assertEquals(esperado, resultado);
        verify(consultarActividadesPorSubareaUseCase).ejecutar("subarea1");
    }

}
