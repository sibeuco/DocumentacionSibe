package co.edu.uco.sibe.infraestructura.controlador.comando;

import co.edu.uco.sibe.aplicacion.comando.ActividadComando;
import co.edu.uco.sibe.aplicacion.comando.ActividadModificacionComando;
import co.edu.uco.sibe.aplicacion.comando.ParticipanteComando;
import co.edu.uco.sibe.aplicacion.comando.manejador.*;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActividadComandoControladorTest {

    @Mock private GuardarActividadManejador guardarActividadManejador;
    @Mock private ModificarActividadManejador modificarActividadManejador;
    @Mock private IniciarActividadManejador iniciarActividadManejador;
    @Mock private FinalizarActividadManejador finalizarActividadManejador;
    @Mock private CancelarActividadManejador cancelarActividadManejador;

    private ActividadComandoControlador controlador;

    @BeforeEach
    void setUp() {
        controlador = new ActividadComandoControlador(
                guardarActividadManejador,
                modificarActividadManejador,
                iniciarActividadManejador,
                finalizarActividadManejador,
                cancelarActividadManejador
        );
    }

    @Test
    void deberiaGuardarActividad() {
        ActividadComando comando = mock(ActividadComando.class);
        UUID id = UUID.randomUUID();
        when(guardarActividadManejador.ejecutar(comando)).thenReturn(new ComandoRespuesta<>(id));

        ComandoRespuesta<UUID> resultado = controlador.guardar(comando);

        assertEquals(id, resultado.getValor());
        verify(guardarActividadManejador).ejecutar(comando);
    }

    @Test
    void deberiaModificarActividad() {
        UUID parametro = UUID.randomUUID();
        ActividadModificacionComando comando = mock(ActividadModificacionComando.class);
        UUID id = UUID.randomUUID();
        when(modificarActividadManejador.ejecutar(comando, parametro)).thenReturn(new ComandoRespuesta<>(id));

        ComandoRespuesta<UUID> resultado = controlador.modificar(comando, parametro.toString());

        assertEquals(id, resultado.getValor());
        verify(modificarActividadManejador).ejecutar(comando, parametro);
    }

    @Test
    void deberiaIniciarActividad() {
        UUID parametro = UUID.randomUUID();
        UUID id = UUID.randomUUID();
        when(iniciarActividadManejador.ejecutar(parametro)).thenReturn(new ComandoRespuesta<>(id));

        ComandoRespuesta<UUID> resultado = controlador.iniciar(parametro.toString());

        assertEquals(id, resultado.getValor());
        verify(iniciarActividadManejador).ejecutar(parametro);
    }

    @Test
    void deberiaFinalizarActividad() {
        UUID parametro = UUID.randomUUID();
        List<ParticipanteComando> comandos = List.of(mock(ParticipanteComando.class));
        UUID id = UUID.randomUUID();
        when(finalizarActividadManejador.ejecutar(comandos, parametro)).thenReturn(new ComandoRespuesta<>(id));

        ComandoRespuesta<UUID> resultado = controlador.finalizar(comandos, parametro.toString());

        assertEquals(id, resultado.getValor());
        verify(finalizarActividadManejador).ejecutar(comandos, parametro);
    }

    @Test
    void deberiaCancelarActividad() {
        UUID parametro = UUID.randomUUID();
        UUID id = UUID.randomUUID();
        when(cancelarActividadManejador.ejecutar(parametro)).thenReturn(new ComandoRespuesta<>(id));

        ComandoRespuesta<UUID> resultado = controlador.cancelar(parametro.toString());

        assertEquals(id, resultado.getValor());
        verify(cancelarActividadManejador).ejecutar(parametro);
    }
}
