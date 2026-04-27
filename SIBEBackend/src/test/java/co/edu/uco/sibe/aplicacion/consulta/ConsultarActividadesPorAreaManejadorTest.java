package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.ActividadDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarActividadesPorAreaUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarActividadesPorAreaManejadorTest {

    @Mock private ConsultarActividadesPorAreaUseCase consultarActividadesPorAreaUseCase;

    private ConsultarActividadesPorAreaManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarActividadesPorAreaManejador(consultarActividadesPorAreaUseCase);
    }

    @Test
    void deberiaConsultarActividadesPorArea() {
        List<ActividadDTO> esperado = List.of(mock(ActividadDTO.class));
        when(consultarActividadesPorAreaUseCase.ejecutar("area1")).thenReturn(esperado);

        List<ActividadDTO> resultado = manejador.ejecutar("area1");

        assertEquals(esperado, resultado);
        verify(consultarActividadesPorAreaUseCase).ejecutar("area1");
    }

}
