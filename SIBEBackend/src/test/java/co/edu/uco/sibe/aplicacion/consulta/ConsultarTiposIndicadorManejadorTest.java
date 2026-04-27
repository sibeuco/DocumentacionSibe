package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.TipoIndicadorDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.TipoIndicadorRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarTiposIndicadorManejadorTest {

    @Mock private TipoIndicadorRepositorioConsulta tipoIndicadorRepositorioConsulta;

    private ConsultarTiposIndicadorManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarTiposIndicadorManejador(tipoIndicadorRepositorioConsulta);
    }

    @Test
    void deberiaConsultarTiposIndicador() {
        List<TipoIndicadorDTO> esperado = List.of(mock(TipoIndicadorDTO.class));
        when(tipoIndicadorRepositorioConsulta.consultarDTOs()).thenReturn(esperado);

        List<TipoIndicadorDTO> resultado = manejador.ejecutar();

        assertEquals(esperado, resultado);
        verify(tipoIndicadorRepositorioConsulta).consultarDTOs();
    }
}
