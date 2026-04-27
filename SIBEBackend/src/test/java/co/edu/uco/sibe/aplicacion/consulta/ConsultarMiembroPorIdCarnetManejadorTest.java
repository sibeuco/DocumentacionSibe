package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.MiembroDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarMiembroPorIdCarnetUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarMiembroPorIdCarnetManejadorTest {

    @Mock private ConsultarMiembroPorIdCarnetUseCase consultarMiembroPorIdCarnetUseCase;

    private ConsultarMiembroPorIdCarnetManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarMiembroPorIdCarnetManejador(consultarMiembroPorIdCarnetUseCase);
    }

    @Test
    void deberiaConsultarMiembroPorIdCarnet() {
        MiembroDTO esperado = mock(MiembroDTO.class);
        when(consultarMiembroPorIdCarnetUseCase.ejecutar("12345")).thenReturn(esperado);

        MiembroDTO resultado = manejador.ejecutar("12345");

        assertEquals(esperado, resultado);
        verify(consultarMiembroPorIdCarnetUseCase).ejecutar("12345");
    }
}
