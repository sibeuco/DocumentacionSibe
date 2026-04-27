package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.AccionComando;
import co.edu.uco.sibe.aplicacion.comando.fabrica.AccionFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.dominio.modelo.Accion;
import co.edu.uco.sibe.dominio.usecase.comando.ModificarAccionUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModificarAccionManejadorTest {

    @Mock private AccionFabrica accionFabrica;
    @Mock private ModificarAccionUseCase modificarAccionUseCase;

    private ModificarAccionManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ModificarAccionManejador(accionFabrica, modificarAccionUseCase);
    }

    @Test
    void deberiaEjecutarModificarAccion() {
        UUID parametro = UUID.randomUUID();
        AccionComando comando = new AccionComando("Detalle mod", "Objetivo mod");
        Accion accion = mock(Accion.class);
        UUID idEsperado = UUID.randomUUID();

        when(accionFabrica.construirActualizar(comando, parametro)).thenReturn(accion);
        when(modificarAccionUseCase.ejecutar(accion, parametro)).thenReturn(idEsperado);

        ComandoRespuesta<UUID> resultado = manejador.ejecutar(comando, parametro);

        assertEquals(idEsperado, resultado.getValor());
    }
}
