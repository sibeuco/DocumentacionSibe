package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.ActividadComando;
import co.edu.uco.sibe.aplicacion.comando.AreaComando;
import co.edu.uco.sibe.aplicacion.comando.fabrica.ActividadFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.dominio.enums.TipoArea;
import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.modelo.EjecucionActividad;
import co.edu.uco.sibe.dominio.usecase.comando.GuardarActividadUseCase;
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
class GuardarActividadManejadorTest {

    @Mock private ActividadFabrica actividadFabrica;
    @Mock private GuardarActividadUseCase guardarActividadUseCase;

    private GuardarActividadManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new GuardarActividadManejador(actividadFabrica, guardarActividadUseCase);
    }

    @Test
    void deberiaEjecutarGuardarActividad() {
        UUID areaUUID = UUID.randomUUID();
        ActividadComando comando = mock(ActividadComando.class);
        Actividad actividad = mock(Actividad.class);
        List<EjecucionActividad> ejecuciones = List.of(mock(EjecucionActividad.class));
        UUID idEsperado = UUID.randomUUID();
        List<String> fechas = List.of("2026-01-01");

        AreaComando areaComando = new AreaComando(areaUUID.toString(), "DIRECCION");
        when(comando.getArea()).thenReturn(areaComando);
        when(comando.getFechasProgramadas()).thenReturn(fechas);

        when(actividadFabrica.construir(comando)).thenReturn(actividad);
        when(actividadFabrica.construirEjecuciones(fechas, actividad)).thenReturn(ejecuciones);
        when(guardarActividadUseCase.ejecutar(actividad, ejecuciones, areaUUID, TipoArea.DIRECCION)).thenReturn(idEsperado);

        ComandoRespuesta<UUID> resultado = manejador.ejecutar(comando);

        assertEquals(idEsperado, resultado.getValor());
        verify(actividadFabrica).construir(comando);
        verify(guardarActividadUseCase).ejecutar(actividad, ejecuciones, areaUUID, TipoArea.DIRECCION);
    }
}
