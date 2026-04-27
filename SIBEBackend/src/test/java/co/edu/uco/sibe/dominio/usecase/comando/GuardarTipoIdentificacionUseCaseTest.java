package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.TipoIdentificacion;
import co.edu.uco.sibe.dominio.puerto.comando.TipoIdentificacionRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.TipoIdentificacionRepositorioConsulta;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorDuplicadoExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.DESCRIPCION_TIPO_IDENTIFICACION_EXISTENTE;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.SIGLA_TIPO_IDENTIFICACION_EXISTENTE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuardarTipoIdentificacionUseCaseTest {

    @Mock
    private TipoIdentificacionRepositorioComando tipoIdentificacionRepositorioComando;
    @Mock
    private TipoIdentificacionRepositorioConsulta tipoIdentificacionRepositorioConsulta;

    private GuardarTipoIdentificacionUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GuardarTipoIdentificacionUseCase(tipoIdentificacionRepositorioComando, tipoIdentificacionRepositorioConsulta);
    }

    @Test
    void deberiaGuardarTipoIdentificacionExitosamente() {
        UUID identificador = UUID.randomUUID();
        TipoIdentificacion tipoIdentificacion = TipoIdentificacion.construir(UUID.randomUUID(), "CC", "Cedula de Ciudadania");

        when(tipoIdentificacionRepositorioConsulta.consultarPorSigla("Cedula de Ciudadania")).thenReturn(null);
        when(tipoIdentificacionRepositorioConsulta.consultarPorDescripcion("Cedula de Ciudadania")).thenReturn(null);
        when(tipoIdentificacionRepositorioComando.guardar(tipoIdentificacion)).thenReturn(identificador);

        UUID resultado = useCase.ejecutar(tipoIdentificacion);

        assertEquals(identificador, resultado);
        verify(tipoIdentificacionRepositorioComando).guardar(tipoIdentificacion);
    }

    @Test
    void deberiaLanzarValorDuplicadoExcepcionCuandoSiglaExiste() {
        TipoIdentificacion tipoIdentificacion = TipoIdentificacion.construir(UUID.randomUUID(), "CC", "Cedula de Ciudadania");

        when(tipoIdentificacionRepositorioConsulta.consultarPorSigla("Cedula de Ciudadania"))
                .thenReturn(TipoIdentificacion.construir());

        ValorDuplicadoExcepcion excepcion = assertThrows(ValorDuplicadoExcepcion.class,
                () -> useCase.ejecutar(tipoIdentificacion));

        assertEquals(SIGLA_TIPO_IDENTIFICACION_EXISTENTE, excepcion.getMessage());
        verify(tipoIdentificacionRepositorioComando, never()).guardar(any());
    }

    @Test
    void deberiaLanzarValorDuplicadoExcepcionCuandoDescripcionExiste() {
        TipoIdentificacion tipoIdentificacion = TipoIdentificacion.construir(UUID.randomUUID(), "CC", "Cedula de Ciudadania");

        when(tipoIdentificacionRepositorioConsulta.consultarPorSigla("Cedula de Ciudadania")).thenReturn(null);
        when(tipoIdentificacionRepositorioConsulta.consultarPorDescripcion("Cedula de Ciudadania"))
                .thenReturn(TipoIdentificacion.construir());

        ValorDuplicadoExcepcion excepcion = assertThrows(ValorDuplicadoExcepcion.class,
                () -> useCase.ejecutar(tipoIdentificacion));

        assertEquals(DESCRIPCION_TIPO_IDENTIFICACION_EXISTENTE, excepcion.getMessage());
        verify(tipoIdentificacionRepositorioComando, never()).guardar(any());
    }
}
