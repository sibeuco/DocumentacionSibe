package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.TipoUsuarioDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.TipoUsuarioRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarTiposUsuarioManejadorTest {

    @Mock private TipoUsuarioRepositorioConsulta tipoUsuarioRepositorioConsulta;

    private ConsultarTiposUsuarioManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarTiposUsuarioManejador(tipoUsuarioRepositorioConsulta);
    }

    @Test
    void deberiaConsultarTiposUsuario() {
        List<TipoUsuarioDTO> esperado = List.of(mock(TipoUsuarioDTO.class));
        when(tipoUsuarioRepositorioConsulta.consultarDTOs()).thenReturn(esperado);

        List<TipoUsuarioDTO> resultado = manejador.ejecutar();

        assertEquals(esperado, resultado);
        verify(tipoUsuarioRepositorioConsulta).consultarDTOs();
    }
}
