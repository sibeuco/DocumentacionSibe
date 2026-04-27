package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.modelo.TipoIdentificacion;
import co.edu.uco.sibe.dominio.puerto.consulta.TipoIdentificacionRepositorioConsulta;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarTipoIdentificacionPorSiglaUseCaseTest {

    @Mock
    private TipoIdentificacionRepositorioConsulta tipoIdentificacionRepositorioConsulta;

    private ConsultarTipoIdentificacionPorSiglaUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ConsultarTipoIdentificacionPorSiglaUseCase(tipoIdentificacionRepositorioConsulta);
    }

    @Test
    void deberiaRetornarTipoIdentificacionPorSiglaExitosamente() {
        TipoIdentificacion tipoIdentificacion = TipoIdentificacion.construir();
        when(tipoIdentificacionRepositorioConsulta.consultarPorSigla("CC")).thenReturn(tipoIdentificacion);

        TipoIdentificacion resultado = useCase.ejecutar("CC");

        assertEquals(tipoIdentificacion, resultado);
    }

    @Test
    void deberiaLanzarExcepcionCuandoTipoIdentificacionNoExistePorSigla() {
        when(tipoIdentificacionRepositorioConsulta.consultarPorSigla("XX")).thenReturn(null);

        assertThrows(ValorInvalidoExcepcion.class, () -> useCase.ejecutar("XX"));
    }
}
