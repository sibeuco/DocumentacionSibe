package co.edu.uco.sibe.infraestructura.controlador.consulta;

import co.edu.uco.sibe.aplicacion.consulta.ConsultarIndicadoresManejador;
import co.edu.uco.sibe.aplicacion.consulta.ConsultarIndicadoresParaActividadesManejador;
import co.edu.uco.sibe.dominio.dto.IndicadorDTO;
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
class IndicadorConsultaControladorTest {

    @Mock private ConsultarIndicadoresManejador consultarIndicadoresManejador;
    @Mock private ConsultarIndicadoresParaActividadesManejador consultarIndicadoresParaActividadesManejador;

    private IndicadorConsultaControlador controlador;

    @BeforeEach
    void setUp() {
        controlador = new IndicadorConsultaControlador(consultarIndicadoresManejador, consultarIndicadoresParaActividadesManejador);
    }

    @Test
    void deberiaConsultarTodosPaginado() {
        Page<IndicadorDTO> pagina = new PageImpl<>(List.of());
        when(consultarIndicadoresManejador.ejecutar(0, 10)).thenReturn(pagina);

        Page<IndicadorDTO> resultado = controlador.consultarTodos(0, 10);

        assertEquals(0, resultado.getContent().size());
        assertEquals(0, resultado.getTotalElements());
        verify(consultarIndicadoresManejador).ejecutar(0, 10);
    }

    @Test
    void deberiaConsultarTodosConPaginaYTamanoPersonalizados() {
        Page<IndicadorDTO> pagina = new PageImpl<>(List.of());
        when(consultarIndicadoresManejador.ejecutar(2, 5)).thenReturn(pagina);

        Page<IndicadorDTO> resultado = controlador.consultarTodos(2, 5);

        assertNotNull(resultado);
        verify(consultarIndicadoresManejador).ejecutar(2, 5);
    }

    @Test
    void deberiaConsultarParaActividades() {
        List<IndicadorDTO> indicadores = List.of();
        when(consultarIndicadoresParaActividadesManejador.ejecutar()).thenReturn(indicadores);

        List<IndicadorDTO> resultado = controlador.consultarParaActividades();

        assertNotNull(resultado);
    }

    @Test
    void deberiaConsultarTodosSinPaginar() {
        List<IndicadorDTO> indicadores = List.of(mock(IndicadorDTO.class), mock(IndicadorDTO.class));
        when(consultarIndicadoresManejador.ejecutar()).thenReturn(indicadores);

        List<IndicadorDTO> resultado = controlador.consultarTodosSinPaginar();

        assertEquals(2, resultado.size());
        verify(consultarIndicadoresManejador).ejecutar();
    }

}
