package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.AreaDetalladaDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarAreaDetalladaUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarAreaDetalladaManejadorTest {

    @Mock private ConsultarAreaDetalladaUseCase consultarAreaDetalladaUseCase;

    private ConsultarAreaDetalladaManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarAreaDetalladaManejador(consultarAreaDetalladaUseCase);
    }

    @Test
    void deberiaConsultarAreaDetallada() {
        UUID id = UUID.randomUUID();
        AreaDetalladaDTO esperado = mock(AreaDetalladaDTO.class);
        when(consultarAreaDetalladaUseCase.ejecutar(id)).thenReturn(esperado);

        AreaDetalladaDTO resultado = manejador.ejecutar(id.toString());

        assertEquals(esperado, resultado);
        verify(consultarAreaDetalladaUseCase).ejecutar(id);
    }
}
