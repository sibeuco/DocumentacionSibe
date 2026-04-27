package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.TipoIdentificacionDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.TipoIdentificacionRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarTiposIdentificacionManejadorTest {

    @Mock private TipoIdentificacionRepositorioConsulta tipoIdentificacionRepositorioConsulta;

    private ConsultarTiposIdentificacionManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarTiposIdentificacionManejador(tipoIdentificacionRepositorioConsulta);
    }

    @Test
    void deberiaConsultarTiposIdentificacion() {
        List<TipoIdentificacionDTO> esperado = List.of(mock(TipoIdentificacionDTO.class));
        when(tipoIdentificacionRepositorioConsulta.consultarDTOs()).thenReturn(esperado);

        List<TipoIdentificacionDTO> resultado = manejador.ejecutar();

        assertEquals(esperado, resultado);
        verify(tipoIdentificacionRepositorioConsulta).consultarDTOs();
    }
}
