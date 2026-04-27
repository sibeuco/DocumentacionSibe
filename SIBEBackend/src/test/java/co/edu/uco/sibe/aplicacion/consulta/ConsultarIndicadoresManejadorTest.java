package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.IndicadorDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.IndicadorRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarIndicadoresManejadorTest {

    @Mock private IndicadorRepositorioConsulta indicadorRepositorioConsulta;

    private ConsultarIndicadoresManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarIndicadoresManejador(indicadorRepositorioConsulta);
    }

    @Test
    void deberiaConsultarIndicadoresPaginados() {
        Page<IndicadorDTO> esperado = new PageImpl<>(List.of(mock(IndicadorDTO.class)));
        when(indicadorRepositorioConsulta.consultarDTOsPaginado(0, 10)).thenReturn(esperado);

        Page<IndicadorDTO> resultado = manejador.ejecutar(0, 10);

        assertEquals(esperado, resultado);
        verify(indicadorRepositorioConsulta).consultarDTOsPaginado(0, 10);
    }

    @Test
    void deberiaConsultarTodosLosIndicadores() {
        List<IndicadorDTO> esperado = List.of(mock(IndicadorDTO.class), mock(IndicadorDTO.class));
        when(indicadorRepositorioConsulta.consultarDTOs()).thenReturn(esperado);

        List<IndicadorDTO> resultado = manejador.ejecutar();

        assertEquals(esperado, resultado);
        verify(indicadorRepositorioConsulta).consultarDTOs();
    }

}
