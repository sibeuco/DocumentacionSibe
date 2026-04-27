package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.MiembroDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarMiembroPorIdentificacionUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarMiembroPorIdentificacionManejadorTest {

    @Mock private ConsultarMiembroPorIdentificacionUseCase consultarMiembroPorIdentificacionUseCase;

    private ConsultarMiembroPorIdentificacionManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarMiembroPorIdentificacionManejador(consultarMiembroPorIdentificacionUseCase);
    }

    @Test
    void deberiaConsultarMiembroPorIdentificacion() {
        MiembroDTO esperado = mock(MiembroDTO.class);
        when(consultarMiembroPorIdentificacionUseCase.ejecutar("1234567890")).thenReturn(esperado);

        MiembroDTO resultado = manejador.ejecutar("1234567890");

        assertEquals(esperado, resultado);
        verify(consultarMiembroPorIdentificacionUseCase).ejecutar("1234567890");
    }
}
