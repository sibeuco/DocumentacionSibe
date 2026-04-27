package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.ActividadDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarActividadesPorDireccionUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarActividadesPorDireccionManejadorTest {

    @Mock private ConsultarActividadesPorDireccionUseCase consultarActividadesPorDireccionUseCase;

    private ConsultarActividadesPorDireccionManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarActividadesPorDireccionManejador(consultarActividadesPorDireccionUseCase);
    }

    @Test
    void deberiaConsultarActividadesPorDireccion() {
        List<ActividadDTO> esperado = List.of(mock(ActividadDTO.class));
        when(consultarActividadesPorDireccionUseCase.ejecutar("direccion1")).thenReturn(esperado);

        List<ActividadDTO> resultado = manejador.ejecutar("direccion1");

        assertEquals(esperado, resultado);
        verify(consultarActividadesPorDireccionUseCase).ejecutar("direccion1");
    }

}
