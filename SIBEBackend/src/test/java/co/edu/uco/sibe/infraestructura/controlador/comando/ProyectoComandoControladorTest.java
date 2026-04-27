package co.edu.uco.sibe.infraestructura.controlador.comando;

import co.edu.uco.sibe.aplicacion.comando.ProyectoComando;
import co.edu.uco.sibe.aplicacion.comando.ProyectoModificacionComando;
import co.edu.uco.sibe.aplicacion.comando.manejador.GuardarProyectoManejador;
import co.edu.uco.sibe.aplicacion.comando.manejador.ModificarProyectoManejador;
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
class ProyectoComandoControladorTest {

    @Mock private GuardarProyectoManejador guardarProyectoManejador;
    @Mock private ModificarProyectoManejador modificarProyectoManejador;

    private ProyectoComandoControlador controlador;

    @BeforeEach
    void setUp() {
        controlador = new ProyectoComandoControlador(guardarProyectoManejador, modificarProyectoManejador);
    }

    @Test
    void deberiaGuardarProyecto() {
        ProyectoComando comando = new ProyectoComando("P001", "Proyecto 1", "Objetivo", List.of());
        UUID id = UUID.randomUUID();
        when(guardarProyectoManejador.ejecutar(comando)).thenReturn(new ComandoRespuesta<>(id));

        ComandoRespuesta<UUID> resultado = controlador.guardar(comando);

        assertEquals(id, resultado.getValor());
    }

    @Test
    void deberiaModificarProyecto() {
        UUID parametro = UUID.randomUUID();
        String identificador = parametro.toString();
        ProyectoModificacionComando comando = new ProyectoModificacionComando("PMOD01", "Proyecto Mod", "Objetivo Mod", List.of());
        UUID id = UUID.randomUUID();
        when(modificarProyectoManejador.ejecutar(comando, parametro)).thenReturn(new ComandoRespuesta<>(id));

        ComandoRespuesta<UUID> resultado = controlador.modificar(comando, identificador);

        assertEquals(id, resultado.getValor());
    }
}
