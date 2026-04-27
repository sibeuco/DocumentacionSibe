package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.EstadoActividad;
import co.edu.uco.sibe.dominio.puerto.comando.EstadoActividadRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.EstadoActividadRepositorioConsulta;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorDuplicadoExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.NOMBRE_ESTADO_ACTIVIDAD_EXISTENTE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuardarEstadoActividadUseCaseTest {

    @Mock
    private EstadoActividadRepositorioComando estadoActividadRepositorioComando;
    @Mock
    private EstadoActividadRepositorioConsulta estadoActividadRepositorioConsulta;

    private GuardarEstadoActividadUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GuardarEstadoActividadUseCase(estadoActividadRepositorioComando, estadoActividadRepositorioConsulta);
    }

    @Test
    void deberiaGuardarEstadoActividadExitosamente() {
        UUID identificador = UUID.randomUUID();
        EstadoActividad estadoActividad = EstadoActividad.construir(UUID.randomUUID(), "Activa");

        when(estadoActividadRepositorioConsulta.consultarPorNombre("Activa")).thenReturn(null);
        when(estadoActividadRepositorioComando.guardar(estadoActividad)).thenReturn(identificador);

        UUID resultado = useCase.ejecutar(estadoActividad);

        assertEquals(identificador, resultado);
        verify(estadoActividadRepositorioComando).guardar(estadoActividad);
    }

    @Test
    void deberiaLanzarValorDuplicadoExcepcionCuandoNombreExiste() {
        EstadoActividad estadoActividad = EstadoActividad.construir(UUID.randomUUID(), "Activa");

        when(estadoActividadRepositorioConsulta.consultarPorNombre("Activa"))
                .thenReturn(EstadoActividad.construir());

        ValorDuplicadoExcepcion excepcion = assertThrows(ValorDuplicadoExcepcion.class,
                () -> useCase.ejecutar(estadoActividad));

        assertEquals(NOMBRE_ESTADO_ACTIVIDAD_EXISTENTE, excepcion.getMessage());
        verify(estadoActividadRepositorioComando, never()).guardar(any());
    }
}
