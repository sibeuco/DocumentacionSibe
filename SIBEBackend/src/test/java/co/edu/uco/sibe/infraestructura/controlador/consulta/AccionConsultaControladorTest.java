package co.edu.uco.sibe.infraestructura.controlador.consulta;

import co.edu.uco.sibe.aplicacion.consulta.ConsultarAccionesManejador;
import co.edu.uco.sibe.dominio.dto.AccionDTO;
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
class AccionConsultaControladorTest {

    @Mock
    private ConsultarAccionesManejador consultarAccionesManejador;

    private AccionConsultaControlador controlador;

    @BeforeEach
    void setUp() {
        controlador = new AccionConsultaControlador(consultarAccionesManejador);
    }

    @Test
    void deberiaConsultarTodosPaginado() {
        Page<AccionDTO> pagina = new PageImpl<>(List.of(new AccionDTO("1", "Detalle", "Objetivo")));
        when(consultarAccionesManejador.ejecutar(0, 10)).thenReturn(pagina);

        Page<AccionDTO> resultado = controlador.consultarTodos(0, 10);

        assertEquals(1, resultado.getContent().size());
        assertEquals(1, resultado.getTotalElements());
        verify(consultarAccionesManejador).ejecutar(0, 10);
    }

    @Test
    void deberiaConsultarTodosConPaginaYTamanoPersonalizados() {
        Page<AccionDTO> pagina = new PageImpl<>(List.of());
        when(consultarAccionesManejador.ejecutar(2, 5)).thenReturn(pagina);

        Page<AccionDTO> resultado = controlador.consultarTodos(2, 5);

        assertNotNull(resultado);
        verify(consultarAccionesManejador).ejecutar(2, 5);
    }

}
