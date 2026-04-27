package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.SubareaDetalladaDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarSubareaDetalladaUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarSubareaDetalladaManejadorTest {

    @Mock private ConsultarSubareaDetalladaUseCase consultarSubareaDetalladaUseCase;

    private ConsultarSubareaDetalladaManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarSubareaDetalladaManejador(consultarSubareaDetalladaUseCase);
    }

    @Test
    void deberiaConsultarSubareaDetallada() {
        UUID id = UUID.randomUUID();
        SubareaDetalladaDTO esperado = mock(SubareaDetalladaDTO.class);
        when(consultarSubareaDetalladaUseCase.ejecutar(id)).thenReturn(esperado);

        SubareaDetalladaDTO resultado = manejador.ejecutar(id.toString());

        assertEquals(esperado, resultado);
        verify(consultarSubareaDetalladaUseCase).ejecutar(id);
    }
}
