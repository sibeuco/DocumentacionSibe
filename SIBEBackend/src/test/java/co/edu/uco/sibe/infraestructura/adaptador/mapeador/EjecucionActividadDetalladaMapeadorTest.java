package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.EjecucionActividadDetalladaDTO;
import co.edu.uco.sibe.dominio.dto.EstadoActividadDTO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.RegistroAsistenciaDAO;
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

@ExtendWith(MockitoExtension.class)
class EjecucionActividadDetalladaMapeadorTest {

    @Mock
    private EstadoActividadMapeador estadoActividadMapeador;

    @Mock
    private ParticipanteDetalladoMapeador participanteDetalladoMapeador;

    @Mock
    private RegistroAsistenciaDAO registroAsistenciaDAO;

    private EjecucionActividadDetalladaMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new EjecucionActividadDetalladaMapeador(estadoActividadMapeador, participanteDetalladoMapeador, registroAsistenciaDAO);
    }

    @Test
    void deberiaConstruirDTODesdeEntidad() {
        UUID id = UUID.randomUUID();
        EstadoActividadEntidad estadoActividadEntidad = new EstadoActividadEntidad(UUID.randomUUID(), "Pendiente");
        EjecucionActividadEstadoActividadEntidad estadoEntidad = new EjecucionActividadEstadoActividadEntidad(UUID.randomUUID(), estadoActividadEntidad);
        ActividadEntidad actividadEntidad = mock(ActividadEntidad.class);

        EjecucionActividadEntidad entidad = new EjecucionActividadEntidad(id, LocalDate.now(), LocalDate.now(), LocalTime.of(8, 0), LocalTime.of(10, 0), estadoEntidad, actividadEntidad);

        when(registroAsistenciaDAO.findAllByEjecucionActividadIdentificador(id)).thenReturn(List.of());
        when(estadoActividadMapeador.construirDTO(estadoActividadEntidad)).thenReturn(new EstadoActividadDTO(UUID.randomUUID().toString(), "Pendiente"));

        EjecucionActividadDetalladaDTO dto = mapeador.construirDTO(entidad);

        assertEquals(id.toString(), dto.getIdentificador());
        assertNotNull(dto.getFechaProgramada());
        assertNotNull(dto.getFechaRealizacion());
    }

    @Test
    void deberiaConstruirListaDTOs() {
        UUID id = UUID.randomUUID();
        EstadoActividadEntidad estadoActividadEntidad = new EstadoActividadEntidad(UUID.randomUUID(), "Completada");
        EjecucionActividadEstadoActividadEntidad estadoEntidad = new EjecucionActividadEstadoActividadEntidad(UUID.randomUUID(), estadoActividadEntidad);
        ActividadEntidad actividadEntidad = mock(ActividadEntidad.class);

        EjecucionActividadEntidad entidad = new EjecucionActividadEntidad(id, LocalDate.now(), null, null, null, estadoEntidad, actividadEntidad);

        when(registroAsistenciaDAO.findAllByEjecucionActividadIdentificador(id)).thenReturn(List.of());
        when(estadoActividadMapeador.construirDTO(estadoActividadEntidad)).thenReturn(new EstadoActividadDTO(UUID.randomUUID().toString(), "Completada"));

        List<EjecucionActividadDetalladaDTO> dtos = mapeador.construirDTOs(List.of(entidad));

        assertEquals(1, dtos.size());
    }
}
