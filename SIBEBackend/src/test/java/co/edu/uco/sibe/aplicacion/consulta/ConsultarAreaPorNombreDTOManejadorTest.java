package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.AreaDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarAreaPorNombreDTOUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarAreaPorNombreDTOManejadorTest {

    @Mock private ConsultarAreaPorNombreDTOUseCase consultarAreaPorNombreDTOUseCase;

    private ConsultarAreaPorNombreDTOManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarAreaPorNombreDTOManejador(consultarAreaPorNombreDTOUseCase);
    }

    @Test
    void deberiaConsultarAreaPorNombre() {
        AreaDTO esperado = mock(AreaDTO.class);
        when(consultarAreaPorNombreDTOUseCase.ejecutar("AreaTest")).thenReturn(esperado);

        AreaDTO resultado = manejador.ejecutar("AreaTest");

        assertEquals(esperado, resultado);
        verify(consultarAreaPorNombreDTOUseCase).ejecutar("AreaTest");
    }
}
