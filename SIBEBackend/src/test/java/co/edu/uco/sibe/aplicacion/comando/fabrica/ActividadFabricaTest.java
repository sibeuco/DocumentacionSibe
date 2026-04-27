package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.aplicacion.comando.ActividadComando;
import co.edu.uco.sibe.aplicacion.comando.ActividadModificacionComando;
import co.edu.uco.sibe.aplicacion.comando.FechaProgramadaModificacionComando;
import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.modelo.EjecucionActividad;
import co.edu.uco.sibe.dominio.modelo.EstadoActividad;
import co.edu.uco.sibe.dominio.modelo.Indicador;
import co.edu.uco.sibe.dominio.puerto.consulta.ActividadRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.EstadoActividadRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.IndicadorRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActividadFabricaTest {

    @Mock
    private ActividadRepositorioConsulta actividadRepositorioConsulta;
    @Mock
    private IndicadorRepositorioConsulta indicadorRepositorioConsulta;
    @Mock
    private EstadoActividadRepositorioConsulta estadoActividadRepositorioConsulta;

    private ActividadFabrica fabrica;

    @BeforeEach
    void setUp() {
        fabrica = new ActividadFabrica(actividadRepositorioConsulta, indicadorRepositorioConsulta, estadoActividadRepositorioConsulta);
    }

    @Test
    void deberiaConstruirActividadDesdeComando() {
        UUID indicadorId = UUID.randomUUID();
        UUID colaboradorId = UUID.randomUUID();
        UUID creadorId = UUID.randomUUID();
        Indicador indicador = Indicador.construir();

        when(actividadRepositorioConsulta.consultarPorIdentificador(any())).thenReturn(null);
        when(indicadorRepositorioConsulta.consultarPorIdentificador(any())).thenReturn(indicador);

        ActividadComando comando = new ActividadComando(
                "Actividad Test",
                "Objetivo de la actividad test",
                "2026-1",
                "/ruta/insumos",
                indicadorId.toString(),
                colaboradorId.toString(),
                creadorId.toString(),
                List.of("2026-04-15"),
                null
        );

        Actividad resultado = fabrica.construir(comando);

        assertNotNull(resultado.getIdentificador());
        assertEquals("Actividad Test", resultado.getNombre());
        assertEquals("Objetivo de la actividad test", resultado.getObjetivo());
        assertEquals("2026-1", resultado.getSemestre());
        assertEquals("/ruta/insumos", resultado.getRutaInsumos());
    }

    @Test
    void deberiaConstruirActualizarActividadDesdeComando() {
        UUID identificador = UUID.randomUUID();
        UUID indicadorId = UUID.randomUUID();
        UUID colaboradorId = UUID.randomUUID();
        UUID creadorId = UUID.randomUUID();
        Indicador indicador = Indicador.construir();

        Actividad actividadExistente = Actividad.construir(
                identificador, "Vieja", "Obj viejo", "2025-2", "/vieja",
                java.time.LocalDate.now(), indicador, colaboradorId, creadorId
        );

        when(indicadorRepositorioConsulta.consultarPorIdentificador(any())).thenReturn(indicador);
        when(actividadRepositorioConsulta.consultarPorIdentificador(identificador)).thenReturn(actividadExistente);

        ActividadModificacionComando comando = new ActividadModificacionComando(
                "Actividad Actualizada",
                "Objetivo actualizado",
                "2026-1",
                "/ruta/nueva",
                indicadorId.toString(),
                colaboradorId.toString(),
                null,
                null
        );

        Actividad resultado = fabrica.construirActualizar(comando, identificador);

        assertNotNull(resultado);
        assertEquals(identificador, resultado.getIdentificador());
        assertEquals("Actividad Actualizada", resultado.getNombre());
        assertEquals("Objetivo actualizado", resultado.getObjetivo());
        assertEquals(creadorId, resultado.getCreador());
    }

    @Test
    void deberiaConstruirEjecucionesDesdeListaDeFechas() {
        UUID indicadorId = UUID.randomUUID();
        Indicador indicador = Indicador.construir();
        Actividad actividad = Actividad.construir(
                UUID.randomUUID(), "Act", "Obj", "2026-1", "/ruta",
                java.time.LocalDate.now(), indicador, UUID.randomUUID(), UUID.randomUUID()
        );

        EstadoActividad estadoPendiente = EstadoActividad.construir(UUID.randomUUID(), "Pendiente");
        when(estadoActividadRepositorioConsulta.consultarPorNombre("Pendiente")).thenReturn(estadoPendiente);
        when(actividadRepositorioConsulta.consultarEjecucionActividadPorIdentificador(any())).thenReturn(null);

        List<String> fechas = List.of("2026-04-15", "2026-05-20");

        List<EjecucionActividad> resultado = fabrica.construirEjecuciones(fechas, actividad);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(actividad, resultado.get(0).getActividad());
        assertEquals(actividad, resultado.get(1).getActividad());
    }

    @Test
    void deberiaConstruirActualizarEjecucionesConNuevasYExistentes() {
        UUID indicadorId = UUID.randomUUID();
        Indicador indicador = Indicador.construir();
        Actividad actividad = Actividad.construir(
                UUID.randomUUID(), "Act", "Obj", "2026-1", "/ruta",
                java.time.LocalDate.now(), indicador, UUID.randomUUID(), UUID.randomUUID()
        );

        EstadoActividad estadoPendiente = EstadoActividad.construir(UUID.randomUUID(), "Pendiente");
        when(estadoActividadRepositorioConsulta.consultarPorNombre("Pendiente")).thenReturn(estadoPendiente);
        when(actividadRepositorioConsulta.consultarEjecucionActividadPorIdentificador(any(UUID.class)))
                .thenReturn(null);

        // New execution (null identificador)
        FechaProgramadaModificacionComando nuevaFecha = new FechaProgramadaModificacionComando(null, "2026-06-01");

        // Existing execution (with identificador)
        UUID ejecucionExistenteId = UUID.randomUUID();
        EjecucionActividad ejecucionExistente = EjecucionActividad.construir(
                ejecucionExistenteId, java.time.LocalDate.of(2026, 4, 15), java.time.LocalDate.of(2026, 4, 15),
                null, null, estadoPendiente, actividad
        );
        when(actividadRepositorioConsulta.consultarEjecucionActividadPorIdentificador(ejecucionExistenteId))
                .thenReturn(ejecucionExistente);

        FechaProgramadaModificacionComando fechaExistente = new FechaProgramadaModificacionComando(
                ejecucionExistenteId.toString(), "2026-07-15"
        );

        List<FechaProgramadaModificacionComando> fechas = List.of(nuevaFecha, fechaExistente);

        List<EjecucionActividad> resultado = fabrica.construirActualizarEjecuciones(fechas, actividad);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
    }
}
