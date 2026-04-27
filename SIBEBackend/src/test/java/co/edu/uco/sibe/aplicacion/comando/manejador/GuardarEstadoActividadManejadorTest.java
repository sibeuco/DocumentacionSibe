package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.fabrica.EstadoActividadFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.dominio.modelo.EstadoActividad;
import co.edu.uco.sibe.dominio.usecase.comando.GuardarEstadoActividadUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuardarEstadoActividadManejadorTest {

    @Mock private GuardarEstadoActividadUseCase guardarEstadoActividadUseCase;
    @Mock private EstadoActividadFabrica estadoActividadFabrica;

    private GuardarEstadoActividadManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new GuardarEstadoActividadManejador(guardarEstadoActividadUseCase, estadoActividadFabrica);
    }

    @Test
    void deberiaEjecutarGuardarEstadoActividad() {
        EstadoActividad estado = mock(EstadoActividad.class);
        UUID idEsperado = UUID.randomUUID();

        when(estadoActividadFabrica.construir("Activo")).thenReturn(estado);
        when(guardarEstadoActividadUseCase.ejecutar(estado)).thenReturn(idEsperado);

        ComandoRespuesta<UUID> resultado = manejador.ejecutar("Activo");

        assertEquals(idEsperado, resultado.getValor());
        verify(estadoActividadFabrica).construir("Activo");
        verify(guardarEstadoActividadUseCase).ejecutar(estado);
    }
}
