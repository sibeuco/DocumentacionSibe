package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.EjecucionActividadDTO;
import co.edu.uco.sibe.dominio.dto.EstadoActividadDTO;
import co.edu.uco.sibe.dominio.modelo.*;
import co.edu.uco.sibe.infraestructura.adaptador.dao.EjecucionActividadDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.argThat;

@ExtendWith(MockitoExtension.class)
class EjecucionActividadMapeadorTest {

    @Mock
    private EjecucionActividadEstadoActividadMapeador ejecucionActividadEstadoActividadMapeador;

    @Mock
    private ActividadMapeador actividadMapeador;

    @Mock
    private EstadoActividadMapeador estadoActividadMapeador;

    @Mock
    private EjecucionActividadDAO ejecucionActividadDAO;

    private EjecucionActividadMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new EjecucionActividadMapeador(ejecucionActividadEstadoActividadMapeador, actividadMapeador, estadoActividadMapeador, ejecucionActividadDAO);
    }

    @Test
    void deberiaConstruirEntidadDesdeModelo() {
        UUID id = UUID.randomUUID();
        EstadoActividad estado = EstadoActividad.construir(UUID.randomUUID(), "Pendiente");
        Actividad actividad = mock(Actividad.class);
        EjecucionActividad dominio = EjecucionActividad.construir(id, LocalDate.now(), null, null, null, estado, actividad);

        EjecucionActividadEstadoActividadEntidad estadoEntidad = mock(EjecucionActividadEstadoActividadEntidad.class);
        ActividadEntidad actividadEntidad = mock(ActividadEntidad.class);

        when(ejecucionActividadEstadoActividadMapeador.construirEntidad(estado)).thenReturn(estadoEntidad);
        when(actividadMapeador.construirEntidad(actividad)).thenReturn(actividadEntidad);

        EjecucionActividadEntidad entidad = mapeador.construirEntidad(dominio);

        assertEquals(id, entidad.getIdentificador());
        assertEquals(LocalDate.now(), entidad.getFechaProgramada());
    }

    @Test
    void deberiaConstruirModeloDesdeEntidad() {
        UUID id = UUID.randomUUID();
        EstadoActividadEntidad estadoActividadEntidad = new EstadoActividadEntidad(UUID.randomUUID(), "Pendiente");
        EjecucionActividadEstadoActividadEntidad estadoEntidad = new EjecucionActividadEstadoActividadEntidad(UUID.randomUUID(), estadoActividadEntidad);
        ActividadEntidad actividadEntidad = mock(ActividadEntidad.class);

        EjecucionActividadEntidad entidad = new EjecucionActividadEntidad(id, LocalDate.now(), null, null, null, estadoEntidad, actividadEntidad);

        EstadoActividad estadoModelo = EstadoActividad.construir(UUID.randomUUID(), "Pendiente");
        Actividad actividadModelo = mock(Actividad.class);

        when(ejecucionActividadEstadoActividadMapeador.construirModelo(estadoEntidad)).thenReturn(estadoModelo);
        when(actividadMapeador.construirModelo(actividadEntidad)).thenReturn(actividadModelo);

        EjecucionActividad modelo = mapeador.construirModelo(entidad);

        assertEquals(id, modelo.getIdentificador());
    }

    @Test
    void deberiaConstruirDTODesdeEntidad() {
        UUID id = UUID.randomUUID();
        EstadoActividadEntidad estadoActividadEntidad = new EstadoActividadEntidad(UUID.randomUUID(), "Completada");
        EjecucionActividadEstadoActividadEntidad estadoEntidad = new EjecucionActividadEstadoActividadEntidad(UUID.randomUUID(), estadoActividadEntidad);
        ActividadEntidad actividadEntidad = mock(ActividadEntidad.class);

        EjecucionActividadEntidad entidad = new EjecucionActividadEntidad(id, LocalDate.now(), LocalDate.now(), LocalTime.of(8, 0), LocalTime.of(10, 0), estadoEntidad, actividadEntidad);

        EstadoActividadDTO estadoDTO = new EstadoActividadDTO(UUID.randomUUID().toString(), "Completada");
        when(estadoActividadMapeador.construirDTO(estadoActividadEntidad)).thenReturn(estadoDTO);

        EjecucionActividadDTO dto = mapeador.construirDTO(entidad);

        assertEquals(id.toString(), dto.getIdentificador());
        assertNotNull(dto.getFechaProgramada());
        assertNotNull(dto.getFechaRealizacion());
    }

    @Test
    void deberiaActualizarEntidad() {
        UUID id = UUID.randomUUID();
        EstadoActividadEntidad estadoActividadEntidad = new EstadoActividadEntidad(UUID.randomUUID(), "Pendiente");
        EjecucionActividadEstadoActividadEntidad estadoEntidad = new EjecucionActividadEstadoActividadEntidad(UUID.randomUUID(), estadoActividadEntidad);
        ActividadEntidad actividadEntidad = mock(ActividadEntidad.class);
        EjecucionActividadEntidad entidad = new EjecucionActividadEntidad(id, LocalDate.now(), null, null, null, estadoEntidad, actividadEntidad);

        EstadoActividad nuevoEstado = EstadoActividad.construir(UUID.randomUUID(), "Completada");
        Actividad actividad = mock(Actividad.class);
        EjecucionActividad dominio = EjecucionActividad.construir(UUID.randomUUID(), LocalDate.now().plusDays(1), LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(11, 0), nuevoEstado, actividad);

        EjecucionActividadEstadoActividadEntidad nuevaEstadoEntidad = mock(EjecucionActividadEstadoActividadEntidad.class);
        when(ejecucionActividadEstadoActividadMapeador.construirEntidad(nuevoEstado)).thenReturn(nuevaEstadoEntidad);

        mapeador.actualizarEntidad(entidad, dominio);

        assertEquals(LocalDate.now().plusDays(1), entidad.getFechaProgramada());
        assertEquals(LocalDate.now(), entidad.getFechaRealizacion());
    }

    @Test
    void deberiaConstruirListaDTOs() {
        UUID id = UUID.randomUUID();
        EstadoActividadEntidad estadoActividadEntidad = new EstadoActividadEntidad(UUID.randomUUID(), "Pendiente");
        EjecucionActividadEstadoActividadEntidad estadoEntidad = new EjecucionActividadEstadoActividadEntidad(UUID.randomUUID(), estadoActividadEntidad);
        ActividadEntidad actividadEntidad = mock(ActividadEntidad.class);
        EjecucionActividadEntidad entidad = new EjecucionActividadEntidad(id, LocalDate.now(), null, null, null, estadoEntidad, actividadEntidad);

        EstadoActividadDTO estadoDTO = new EstadoActividadDTO(UUID.randomUUID().toString(), "Pendiente");
        when(estadoActividadMapeador.construirDTO(estadoActividadEntidad)).thenReturn(estadoDTO);

        List<EjecucionActividadDTO> dtos = mapeador.construirDTOs(List.of(entidad));

        assertEquals(1, dtos.size());
    }

    @Test
    void deberiaActualizarTodosConEliminacionYCreacionYActualizacion() {
        UUID actividadId = UUID.randomUUID();
        UUID ejExistenteId = UUID.randomUUID();
        UUID ejParaEliminarId = UUID.randomUUID();
        UUID ejNuevoId = UUID.randomUUID();

        // Existing entities in DB
        EstadoActividadEntidad estadoActividadEntidad = new EstadoActividadEntidad(UUID.randomUUID(), "Pendiente");
        EjecucionActividadEstadoActividadEntidad estadoEntidad = new EjecucionActividadEstadoActividadEntidad(UUID.randomUUID(), estadoActividadEntidad);
        ActividadEntidad actividadEntidad = mock(ActividadEntidad.class);

        EjecucionActividadEntidad entidadExistente = new EjecucionActividadEntidad(
                ejExistenteId, LocalDate.of(2026, 4, 15), null, null, null, estadoEntidad, actividadEntidad
        );
        EjecucionActividadEntidad entidadParaEliminar = new EjecucionActividadEntidad(
                ejParaEliminarId, LocalDate.of(2026, 5, 1), null, null, null, estadoEntidad, actividadEntidad
        );

        when(ejecucionActividadDAO.findByActividadIdentificador(actividadId))
                .thenReturn(List.of(entidadExistente, entidadParaEliminar));

        // New domain list: keeps ejExistente, adds ejNuevo (ejParaEliminar not included -> deleted)
        EstadoActividad estadoModelo = EstadoActividad.construir(UUID.randomUUID(), "Pendiente");
        Actividad actividadModelo = mock(Actividad.class);

        EjecucionActividad ejExistenteDominio = EjecucionActividad.construir(
                ejExistenteId, LocalDate.of(2026, 6, 1), LocalDate.of(2026, 6, 1),
                LocalTime.of(9, 0), LocalTime.of(11, 0), estadoModelo, actividadModelo
        );
        EjecucionActividad ejNuevoDominio = EjecucionActividad.construir(
                ejNuevoId, LocalDate.of(2026, 7, 1), null, null, null, estadoModelo, actividadModelo
        );

        EjecucionActividadEstadoActividadEntidad nuevaEstadoEntidad = mock(EjecucionActividadEstadoActividadEntidad.class);
        when(ejecucionActividadEstadoActividadMapeador.construirEntidad(estadoModelo)).thenReturn(nuevaEstadoEntidad);
        when(actividadMapeador.construirEntidad(actividadModelo)).thenReturn(actividadEntidad);

        mapeador.actualizarTodos(actividadId, List.of(ejExistenteDominio, ejNuevoDominio));

        // Verify deleteAll was called with the entity to remove
        verify(ejecucionActividadDAO).deleteAll(argThat(list -> {
            var items = (List<EjecucionActividadEntidad>) list;
            return items.size() == 1 && items.get(0).getIdentificador().equals(ejParaEliminarId);
        }));

        // Verify saveAll was called with 2 entities
        verify(ejecucionActividadDAO).saveAll(argThat(list -> {
            var items = (List<EjecucionActividadEntidad>) list;
            return items.size() == 2;
        }));

        // Verify existing entity was updated
        assertEquals(LocalDate.of(2026, 6, 1), entidadExistente.getFechaProgramada());
    }
}
