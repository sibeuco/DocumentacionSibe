package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.modelo.TipoUsuario;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarTipoUsuarioPorCodigoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarTipoUsuarioPorCodigoManejadorTest {

    @Mock private ConsultarTipoUsuarioPorCodigoUseCase consultarTipoUsuarioPorCodigoUseCase;

    private ConsultarTipoUsuarioPorCodigoManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarTipoUsuarioPorCodigoManejador(consultarTipoUsuarioPorCodigoUseCase);
    }

    @Test
    void deberiaConsultarTipoUsuarioPorCodigo() {
        TipoUsuario esperado = mock(TipoUsuario.class);
        when(consultarTipoUsuarioPorCodigoUseCase.ejecutar("ADMIN")).thenReturn(esperado);

        TipoUsuario resultado = manejador.ejecutar("ADMIN");

        assertEquals(esperado, resultado);
        verify(consultarTipoUsuarioPorCodigoUseCase).ejecutar("ADMIN");
    }
}
