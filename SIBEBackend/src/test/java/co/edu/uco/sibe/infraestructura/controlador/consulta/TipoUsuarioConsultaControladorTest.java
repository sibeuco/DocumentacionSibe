package co.edu.uco.sibe.infraestructura.controlador.consulta;

import co.edu.uco.sibe.aplicacion.consulta.ConsultarTiposUsuarioManejador;
import co.edu.uco.sibe.dominio.dto.TipoUsuarioDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TipoUsuarioConsultaControladorTest {

    @Mock
    private ConsultarTiposUsuarioManejador consultarTiposUsuarioManejador;

    private TipoUsuarioConsultaControlador controlador;

    @BeforeEach
    void setUp() {
        controlador = new TipoUsuarioConsultaControlador(consultarTiposUsuarioManejador);
    }

    @Test
    void deberiaConsultarTodos() {
        List<TipoUsuarioDTO> tipos = List.of(new TipoUsuarioDTO("1", "ADM", "Administrador", true, true, true, true));
        when(consultarTiposUsuarioManejador.ejecutar()).thenReturn(tipos);

        List<TipoUsuarioDTO> resultado = controlador.consultarTodos();

        assertEquals(1, resultado.size());
        verify(consultarTiposUsuarioManejador).ejecutar();
    }
}
