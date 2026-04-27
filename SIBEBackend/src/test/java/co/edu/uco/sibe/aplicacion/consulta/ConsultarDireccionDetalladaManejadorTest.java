package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.DireccionDetalladaDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarDireccionDetalladaUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarDireccionDetalladaManejadorTest {

    @Mock private ConsultarDireccionDetalladaUseCase consultarDireccionDetalladaUseCase;

    private ConsultarDireccionDetalladaManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarDireccionDetalladaManejador(consultarDireccionDetalladaUseCase);
    }

    @Test
    void deberiaConsultarDireccionDetallada() {
        UUID id = UUID.randomUUID();
        DireccionDetalladaDTO esperado = mock(DireccionDetalladaDTO.class);
        when(consultarDireccionDetalladaUseCase.ejecutar(id)).thenReturn(esperado);

        DireccionDetalladaDTO resultado = manejador.ejecutar(id.toString());

        assertEquals(esperado, resultado);
        verify(consultarDireccionDetalladaUseCase).ejecutar(id);
    }
}
