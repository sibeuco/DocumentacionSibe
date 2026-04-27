package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.PublicoInteresDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.PublicoInteresRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarPublicosInteresManejadorTest {

    @Mock private PublicoInteresRepositorioConsulta publicoInteresRepositorioConsulta;

    private ConsultarPublicosInteresManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarPublicosInteresManejador(publicoInteresRepositorioConsulta);
    }

    @Test
    void deberiaConsultarPublicosInteres() {
        List<PublicoInteresDTO> esperado = List.of(mock(PublicoInteresDTO.class));
        when(publicoInteresRepositorioConsulta.consultarDTOs()).thenReturn(esperado);

        List<PublicoInteresDTO> resultado = manejador.ejecutar();

        assertEquals(esperado, resultado);
        verify(publicoInteresRepositorioConsulta).consultarDTOs();
    }
}
