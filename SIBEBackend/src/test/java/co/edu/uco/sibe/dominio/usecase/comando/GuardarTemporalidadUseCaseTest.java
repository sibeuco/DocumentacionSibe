package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.Temporalidad;
import co.edu.uco.sibe.dominio.puerto.comando.TemporalidadRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.TemporalidadRepositorioConsulta;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorDuplicadoExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.NOMBRE_TEMPORALIDAD_EXISTENTE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuardarTemporalidadUseCaseTest {

    @Mock
    private TemporalidadRepositorioComando temporalidadRepositorioComando;
    @Mock
    private TemporalidadRepositorioConsulta temporalidadRepositorioConsulta;

    private GuardarTemporalidadUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GuardarTemporalidadUseCase(temporalidadRepositorioComando, temporalidadRepositorioConsulta);
    }

    @Test
    void deberiaGuardarTemporalidadExitosamente() {
        UUID identificador = UUID.randomUUID();
        Temporalidad temporalidad = Temporalidad.construir(UUID.randomUUID(), "Semestral");

        when(temporalidadRepositorioConsulta.consultarPorNombre("Semestral")).thenReturn(null);
        when(temporalidadRepositorioComando.guardar(temporalidad)).thenReturn(identificador);

        UUID resultado = useCase.ejecutar(temporalidad);

        assertEquals(identificador, resultado);
        verify(temporalidadRepositorioComando).guardar(temporalidad);
    }

    @Test
    void deberiaLanzarValorDuplicadoExcepcionCuandoNombreExiste() {
        Temporalidad temporalidad = Temporalidad.construir(UUID.randomUUID(), "Semestral");

        when(temporalidadRepositorioConsulta.consultarPorNombre("Semestral"))
                .thenReturn(Temporalidad.construir());

        ValorDuplicadoExcepcion excepcion = assertThrows(ValorDuplicadoExcepcion.class,
                () -> useCase.ejecutar(temporalidad));

        assertEquals(NOMBRE_TEMPORALIDAD_EXISTENTE, excepcion.getMessage());
        verify(temporalidadRepositorioComando, never()).guardar(any());
    }
}
