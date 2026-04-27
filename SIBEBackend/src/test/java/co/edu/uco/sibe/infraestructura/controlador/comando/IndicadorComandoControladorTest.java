package co.edu.uco.sibe.infraestructura.controlador.comando;

import co.edu.uco.sibe.aplicacion.comando.IndicadorComando;
import co.edu.uco.sibe.aplicacion.comando.manejador.GuardarIndicadorManejador;
import co.edu.uco.sibe.aplicacion.comando.manejador.ModificarIndicadorManejador;
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
class IndicadorComandoControladorTest {

    @Mock private GuardarIndicadorManejador guardarIndicadorManejador;
    @Mock private ModificarIndicadorManejador modificarIndicadorManejador;

    private IndicadorComandoControlador controlador;

    @BeforeEach
    void setUp() {
        controlador = new IndicadorComandoControlador(guardarIndicadorManejador, modificarIndicadorManejador);
    }

    @Test
    void deberiaGuardarIndicador() {
        IndicadorComando comando = mock(IndicadorComando.class);
        UUID id = UUID.randomUUID();
        when(guardarIndicadorManejador.ejecutar(comando)).thenReturn(new ComandoRespuesta<>(id));

        ComandoRespuesta<UUID> resultado = controlador.guardar(comando);

        assertEquals(id, resultado.getValor());
        verify(guardarIndicadorManejador).ejecutar(comando);
    }

    @Test
    void deberiaModificarIndicador() {
        UUID parametro = UUID.randomUUID();
        IndicadorComando comando = mock(IndicadorComando.class);
        UUID id = UUID.randomUUID();
        when(modificarIndicadorManejador.ejecutar(comando, parametro)).thenReturn(new ComandoRespuesta<>(id));

        ComandoRespuesta<UUID> resultado = controlador.modificar(comando, parametro.toString());

        assertEquals(id, resultado.getValor());
        verify(modificarIndicadorManejador).ejecutar(comando, parametro);
    }
}
