package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.SubareaDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarSubareaPorNombreDTOUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarSubareaPorNombreDTOManejadorTest {

    @Mock private ConsultarSubareaPorNombreDTOUseCase consultarSubareaPorNombreDTOUseCase;

    private ConsultarSubareaPorNombreDTOManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarSubareaPorNombreDTOManejador(consultarSubareaPorNombreDTOUseCase);
    }

    @Test
    void deberiaConsultarSubareaPorNombre() {
        SubareaDTO esperado = mock(SubareaDTO.class);
        when(consultarSubareaPorNombreDTOUseCase.ejecutar("SubareaTest")).thenReturn(esperado);

        SubareaDTO resultado = manejador.ejecutar("SubareaTest");

        assertEquals(esperado, resultado);
        verify(consultarSubareaPorNombreDTOUseCase).ejecutar("SubareaTest");
    }
}
