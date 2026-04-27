package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.DireccionBaseComando;
import co.edu.uco.sibe.aplicacion.comando.fabrica.DireccionFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.dominio.modelo.Area;
import co.edu.uco.sibe.dominio.modelo.Direccion;
import co.edu.uco.sibe.dominio.usecase.comando.GuardarDireccionUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuardarDireccionManejadorTest {

    @Mock private GuardarDireccionUseCase guardarDireccionUseCase;
    @Mock private DireccionFabrica direccionFabrica;

    private GuardarDireccionManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new GuardarDireccionManejador(guardarDireccionUseCase, direccionFabrica);
    }

    @Test
    void deberiaEjecutarGuardarDireccion() {
        DireccionBaseComando comando = mock(DireccionBaseComando.class);
        Direccion direccion = mock(Direccion.class);
        UUID idEsperado = UUID.randomUUID();
        List<Area> areas = List.of(mock(Area.class));

        when(comando.getNombre()).thenReturn("Direccion Test");
        when(comando.getAreas()).thenReturn(areas);
        when(direccionFabrica.construir("Direccion Test", areas, new ArrayList<>())).thenReturn(direccion);
        when(guardarDireccionUseCase.ejecutar(direccion)).thenReturn(idEsperado);

        ComandoRespuesta<UUID> resultado = manejador.ejecutar(comando);

        assertEquals(idEsperado, resultado.getValor());
        verify(guardarDireccionUseCase).ejecutar(direccion);
    }
}
