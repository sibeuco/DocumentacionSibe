package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.fabrica.SubareaFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.dominio.modelo.Subarea;
import co.edu.uco.sibe.dominio.usecase.comando.GuardarSubareaUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuardarSubareaManejadorTest {

    @Mock private GuardarSubareaUseCase guardarSubareaUseCase;
    @Mock private SubareaFabrica subareaFabrica;

    private GuardarSubareaManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new GuardarSubareaManejador(guardarSubareaUseCase, subareaFabrica);
    }

    @Test
    void deberiaEjecutarGuardarSubarea() {
        Subarea subarea = mock(Subarea.class);
        UUID idEsperado = UUID.randomUUID();

        when(subareaFabrica.construir("Subarea Test", new ArrayList<>())).thenReturn(subarea);
        when(guardarSubareaUseCase.ejecutar(subarea)).thenReturn(idEsperado);

        ComandoRespuesta<UUID> resultado = manejador.ejecutar("Subarea Test");

        assertEquals(idEsperado, resultado.getValor());
        verify(guardarSubareaUseCase).ejecutar(subarea);
    }
}
