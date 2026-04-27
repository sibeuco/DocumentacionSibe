package co.edu.uco.sibe.infraestructura.controlador.comando;

import co.edu.uco.sibe.aplicacion.comando.AccionComando;
import co.edu.uco.sibe.aplicacion.comando.manejador.GuardarAccionManejador;
import co.edu.uco.sibe.aplicacion.comando.manejador.ModificarAccionManejador;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccionComandoControladorTest {

    @Mock private GuardarAccionManejador guardarAccionManejador;
    @Mock private ModificarAccionManejador modificarAccionManejador;

    private AccionComandoControlador controlador;

    @BeforeEach
    void setUp() {
        controlador = new AccionComandoControlador(guardarAccionManejador, modificarAccionManejador);
    }

    @Test
    void deberiaGuardarAccion() {
        AccionComando comando = new AccionComando("Detalle", "Objetivo");
        UUID id = UUID.randomUUID();
        when(guardarAccionManejador.ejecutar(comando)).thenReturn(new ComandoRespuesta<>(id));

        ComandoRespuesta<UUID> resultado = controlador.guardar(comando);

        assertEquals(id, resultado.getValor());
    }

    @Test
    void deberiaModificarAccion() {
        UUID parametro = UUID.randomUUID();
        String identificador = parametro.toString();
        AccionComando comando = new AccionComando("Detalle Mod", "Objetivo Mod");
        UUID id = UUID.randomUUID();
        when(modificarAccionManejador.ejecutar(comando, parametro)).thenReturn(new ComandoRespuesta<>(id));

        ComandoRespuesta<UUID> resultado = controlador.modificar(comando, identificador);

        assertEquals(id, resultado.getValor());
    }
}
