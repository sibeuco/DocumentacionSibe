package co.edu.uco.sibe.infraestructura.controlador.consulta;

import co.edu.uco.sibe.aplicacion.consulta.ConsultarProyectosManejador;
import co.edu.uco.sibe.dominio.dto.ProyectoDTO;
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
class ProyectoConsultaControladorTest {

    @Mock
    private ConsultarProyectosManejador consultarProyectosManejador;

    private ProyectoConsultaControlador controlador;

    @BeforeEach
    void setUp() {
        controlador = new ProyectoConsultaControlador(consultarProyectosManejador);
    }

    @Test
    void deberiaConsultarTodosPaginado() {
        Page<ProyectoDTO> pagina = new PageImpl<>(List.of(new ProyectoDTO("1", "P001", "Proyecto 1", "Objetivo", List.of())));
        when(consultarProyectosManejador.ejecutar(0, 10)).thenReturn(pagina);

        Page<ProyectoDTO> resultado = controlador.consultarTodos(0, 10);

        assertEquals(1, resultado.getContent().size());
        assertEquals(1, resultado.getTotalElements());
        verify(consultarProyectosManejador).ejecutar(0, 10);
    }

    @Test
    void deberiaConsultarTodosConPaginaYTamanoPersonalizados() {
        Page<ProyectoDTO> pagina = new PageImpl<>(List.of());
        when(consultarProyectosManejador.ejecutar(2, 5)).thenReturn(pagina);

        Page<ProyectoDTO> resultado = controlador.consultarTodos(2, 5);

        assertNotNull(resultado);
        verify(consultarProyectosManejador).ejecutar(2, 5);
    }

}
