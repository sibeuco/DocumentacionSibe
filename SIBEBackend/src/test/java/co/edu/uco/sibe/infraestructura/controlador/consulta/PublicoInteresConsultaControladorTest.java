package co.edu.uco.sibe.infraestructura.controlador.consulta;

import co.edu.uco.sibe.aplicacion.consulta.ConsultarPublicosInteresManejador;
import co.edu.uco.sibe.dominio.dto.PublicoInteresDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PublicoInteresConsultaControladorTest {

    @Mock
    private ConsultarPublicosInteresManejador consultarPublicosInteresManejador;

    private PublicoInteresConsultaControlador controlador;

    @BeforeEach
    void setUp() {
        controlador = new PublicoInteresConsultaControlador(consultarPublicosInteresManejador);
    }

    @Test
    void deberiaConsultarTodos() {
        List<PublicoInteresDTO> publicos = List.of(new PublicoInteresDTO("1", "Publico 1"));
        when(consultarPublicosInteresManejador.ejecutar()).thenReturn(publicos);

        List<PublicoInteresDTO> resultado = controlador.consultarTodos();

        assertEquals(1, resultado.size());
        verify(consultarPublicosInteresManejador).ejecutar();
    }
}
