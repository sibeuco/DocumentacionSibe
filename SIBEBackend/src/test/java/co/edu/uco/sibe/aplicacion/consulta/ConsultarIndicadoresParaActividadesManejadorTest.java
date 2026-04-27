package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.IndicadorDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarIndicadoresParaActividadesUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarIndicadoresParaActividadesManejadorTest {

    @Mock private ConsultarIndicadoresParaActividadesUseCase consultarIndicadoresParaActividadesUseCase;

    private ConsultarIndicadoresParaActividadesManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarIndicadoresParaActividadesManejador(consultarIndicadoresParaActividadesUseCase);
    }

    @Test
    void deberiaConsultarIndicadoresParaActividades() {
        List<IndicadorDTO> esperado = List.of(mock(IndicadorDTO.class));
        when(consultarIndicadoresParaActividadesUseCase.ejecutar()).thenReturn(esperado);

        List<IndicadorDTO> resultado = manejador.ejecutar();

        assertEquals(esperado, resultado);
        verify(consultarIndicadoresParaActividadesUseCase).ejecutar();
    }
}
