package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.AreaBaseComando;
import co.edu.uco.sibe.aplicacion.comando.fabrica.AreaFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.dominio.modelo.Area;
import co.edu.uco.sibe.dominio.modelo.Subarea;
import co.edu.uco.sibe.dominio.usecase.comando.GuardarAreaUseCase;
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
class GuardarAreaManejadorTest {

    @Mock private GuardarAreaUseCase guardarAreaUseCase;
    @Mock private AreaFabrica areaFabrica;

    private GuardarAreaManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new GuardarAreaManejador(guardarAreaUseCase, areaFabrica);
    }

    @Test
    void deberiaEjecutarGuardarArea() {
        AreaBaseComando comando = mock(AreaBaseComando.class);
        Area area = mock(Area.class);
        UUID idEsperado = UUID.randomUUID();
        List<Subarea> subareas = List.of(mock(Subarea.class));

        when(comando.getNombre()).thenReturn("Area Test");
        when(comando.getSubareas()).thenReturn(subareas);
        when(areaFabrica.construir("Area Test", subareas, new ArrayList<>())).thenReturn(area);
        when(guardarAreaUseCase.ejecutar(area)).thenReturn(idEsperado);

        ComandoRespuesta<UUID> resultado = manejador.ejecutar(comando);

        assertEquals(idEsperado, resultado.getValor());
        verify(guardarAreaUseCase).ejecutar(area);
    }
}
