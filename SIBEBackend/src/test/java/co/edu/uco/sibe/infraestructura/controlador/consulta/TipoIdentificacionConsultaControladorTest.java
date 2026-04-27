package co.edu.uco.sibe.infraestructura.controlador.consulta;

import co.edu.uco.sibe.aplicacion.consulta.ConsultarTiposIdentificacionManejador;
import co.edu.uco.sibe.dominio.dto.TipoIdentificacionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TipoIdentificacionConsultaControladorTest {

    @Mock
    private ConsultarTiposIdentificacionManejador consultarTiposIdentificacionManejador;

    private TipoIdentificacionConsultaControlador controlador;

    @BeforeEach
    void setUp() {
        controlador = new TipoIdentificacionConsultaControlador(consultarTiposIdentificacionManejador);
    }

    @Test
    void deberiaConsultarTodos() {
        List<TipoIdentificacionDTO> tipos = List.of(new TipoIdentificacionDTO("1", "CC", "Cedula de Ciudadania"));
        when(consultarTiposIdentificacionManejador.ejecutar()).thenReturn(tipos);

        List<TipoIdentificacionDTO> resultado = controlador.consultarTodos();

        assertEquals(1, resultado.size());
        verify(consultarTiposIdentificacionManejador).ejecutar();
    }
}
