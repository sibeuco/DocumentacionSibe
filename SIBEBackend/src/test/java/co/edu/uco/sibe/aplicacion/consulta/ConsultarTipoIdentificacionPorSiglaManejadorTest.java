package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.modelo.TipoIdentificacion;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarTipoIdentificacionPorSiglaUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarTipoIdentificacionPorSiglaManejadorTest {

    @Mock private ConsultarTipoIdentificacionPorSiglaUseCase consultarTipoIdentificacionPorSiglaUseCase;

    private ConsultarTipoIdentificacionPorSiglaManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarTipoIdentificacionPorSiglaManejador(consultarTipoIdentificacionPorSiglaUseCase);
    }

    @Test
    void deberiaConsultarTipoIdentificacionPorSigla() {
        TipoIdentificacion esperado = mock(TipoIdentificacion.class);
        when(consultarTipoIdentificacionPorSiglaUseCase.ejecutar("CC")).thenReturn(esperado);

        TipoIdentificacion resultado = manejador.ejecutar("CC");

        assertEquals(esperado, resultado);
        verify(consultarTipoIdentificacionPorSiglaUseCase).ejecutar("CC");
    }
}
