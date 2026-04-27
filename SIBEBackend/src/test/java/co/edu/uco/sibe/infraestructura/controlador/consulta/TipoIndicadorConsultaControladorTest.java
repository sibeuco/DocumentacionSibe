package co.edu.uco.sibe.infraestructura.controlador.consulta;

import co.edu.uco.sibe.aplicacion.consulta.ConsultarTiposIndicadorManejador;
import co.edu.uco.sibe.dominio.dto.TipoIndicadorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TipoIndicadorConsultaControladorTest {

    @Mock
    private ConsultarTiposIndicadorManejador consultarTiposIndicadorManejador;

    private TipoIndicadorConsultaControlador controlador;

    @BeforeEach
    void setUp() {
        controlador = new TipoIndicadorConsultaControlador(consultarTiposIndicadorManejador);
    }

    @Test
    void deberiaConsultarTodos() {
        List<TipoIndicadorDTO> tipos = List.of(new TipoIndicadorDTO("1", "Cuantitativo", "Resultado"));
        when(consultarTiposIndicadorManejador.ejecutar()).thenReturn(tipos);

        List<TipoIndicadorDTO> resultado = controlador.consultarTodos();

        assertEquals(1, resultado.size());
        verify(consultarTiposIndicadorManejador).ejecutar();
    }
}
