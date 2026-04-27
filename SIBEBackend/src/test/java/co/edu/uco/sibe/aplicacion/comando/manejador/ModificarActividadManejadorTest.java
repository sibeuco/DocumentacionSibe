package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.ActividadModificacionComando;
import co.edu.uco.sibe.aplicacion.comando.AreaComando;
import co.edu.uco.sibe.aplicacion.comando.FechaProgramadaModificacionComando;
import co.edu.uco.sibe.aplicacion.comando.fabrica.ActividadFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.dominio.enums.TipoArea;
import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.modelo.EjecucionActividad;
import co.edu.uco.sibe.dominio.usecase.comando.ModificarActividadUseCase;
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
class ModificarActividadManejadorTest {

    @Mock private ActividadFabrica actividadFabrica;
    @Mock private ModificarActividadUseCase modificarActividadUseCase;

    private ModificarActividadManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ModificarActividadManejador(actividadFabrica, modificarActividadUseCase);
    }

    @Test
    void deberiaEjecutarModificarActividad() {
        UUID identificador = UUID.randomUUID();
        UUID areaUUID = UUID.randomUUID();
        UUID idEsperado = UUID.randomUUID();
        ActividadModificacionComando comando = mock(ActividadModificacionComando.class);
        Actividad actividad = mock(Actividad.class);
        List<EjecucionActividad> ejecuciones = List.of(mock(EjecucionActividad.class));
        List<FechaProgramadaModificacionComando> fechas = List.of(new FechaProgramadaModificacionComando(UUID.randomUUID().toString(), "2026-01-01"));

        AreaComando areaComando = new AreaComando(areaUUID.toString(), "DIRECCION");
        when(comando.getArea()).thenReturn(areaComando);
        when(comando.getFechasProgramada()).thenReturn(fechas);

        when(actividadFabrica.construirActualizar(comando, identificador)).thenReturn(actividad);
        when(actividadFabrica.construirActualizarEjecuciones(fechas, actividad)).thenReturn(ejecuciones);
        when(modificarActividadUseCase.ejecutar(actividad, ejecuciones, areaUUID, TipoArea.DIRECCION, identificador)).thenReturn(idEsperado);

        ComandoRespuesta<UUID> resultado = manejador.ejecutar(comando, identificador);

        assertEquals(idEsperado, resultado.getValor());
        verify(actividadFabrica).construirActualizar(comando, identificador);
        verify(modificarActividadUseCase).ejecutar(actividad, ejecuciones, areaUUID, TipoArea.DIRECCION, identificador);
    }
}
