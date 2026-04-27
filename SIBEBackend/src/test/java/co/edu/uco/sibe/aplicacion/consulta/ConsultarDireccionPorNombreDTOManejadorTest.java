package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.DireccionDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarDireccionPorNombreDTOUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarDireccionPorNombreDTOManejadorTest {

    @Mock private ConsultarDireccionPorNombreDTOUseCase consultarDireccionPorNombreDTOUseCase;

    private ConsultarDireccionPorNombreDTOManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarDireccionPorNombreDTOManejador(consultarDireccionPorNombreDTOUseCase);
    }

    @Test
    void deberiaConsultarDireccionPorNombre() {
        DireccionDTO esperado = mock(DireccionDTO.class);
        when(consultarDireccionPorNombreDTOUseCase.ejecutar("DireccionTest")).thenReturn(esperado);

        DireccionDTO resultado = manejador.ejecutar("DireccionTest");

        assertEquals(esperado, resultado);
        verify(consultarDireccionPorNombreDTOUseCase).ejecutar("DireccionTest");
    }
}
