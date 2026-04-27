package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.ActividadDetalladaDTO;
import co.edu.uco.sibe.dominio.dto.IndicadorDTO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.EjecucionActividadDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.PersonaDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.ActividadEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.IndicadorEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.PersonaEntidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActividadDetalladaMapeadorTest {

    @Mock
    private IndicadorMapeador indicadorMapeador;

    @Mock
    private EjecucionActividadDAO ejecucionActividadDAO;

    @Mock
    private EjecucionActividadDetalladaMapeador ejecucionActividadDetalladaMapeador;

    @Mock
    private PersonaDAO personaDAO;

    private ActividadDetalladaMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new ActividadDetalladaMapeador(indicadorMapeador, ejecucionActividadDAO, ejecucionActividadDetalladaMapeador, personaDAO);
    }

    @Test
    void deberiaConstruirDTODesdeEntidad() {
        UUID id = UUID.randomUUID();
        UUID colaboradorId = UUID.randomUUID();
        IndicadorEntidad indicadorEntidad = mock(IndicadorEntidad.class);
        IndicadorDTO indicadorDTO = mock(IndicadorDTO.class);
        LocalDate fecha = LocalDate.now();

        ActividadEntidad entidad = new ActividadEntidad(id, "Actividad", "Objetivo", "2025-1", "/ruta", fecha, indicadorEntidad, colaboradorId, UUID.randomUUID());

        PersonaEntidad persona = mock(PersonaEntidad.class);
        when(persona.getNombres()).thenReturn("María");
        when(persona.getApellidos()).thenReturn("Gómez");
        when(personaDAO.findById(colaboradorId)).thenReturn(Optional.of(persona));
        when(ejecucionActividadDAO.findByActividadIdentificador(id)).thenReturn(List.of());
        when(ejecucionActividadDetalladaMapeador.construirDTOs(anyList())).thenReturn(List.of());
        when(indicadorMapeador.construirDTO(indicadorEntidad)).thenReturn(indicadorDTO);

        ActividadDetalladaDTO dto = mapeador.construirDTO(entidad);

        assertEquals(id.toString(), dto.getIdentificador());
        assertEquals("Actividad", dto.getNombre());
        assertEquals("Objetivo", dto.getObjetivo());
    }

    @Test
    void deberiaConstruirListaDTOs() {
        UUID id = UUID.randomUUID();
        UUID colaboradorId = UUID.randomUUID();
        IndicadorEntidad indicadorEntidad = mock(IndicadorEntidad.class);
        IndicadorDTO indicadorDTO = mock(IndicadorDTO.class);

        ActividadEntidad entidad = new ActividadEntidad(id, "A1", "O1", "2025-1", "/r", LocalDate.now(), indicadorEntidad, colaboradorId, UUID.randomUUID());

        PersonaEntidad persona = mock(PersonaEntidad.class);
        when(persona.getNombres()).thenReturn("Carlos");
        when(persona.getApellidos()).thenReturn("López");
        when(personaDAO.findById(colaboradorId)).thenReturn(Optional.of(persona));
        when(ejecucionActividadDAO.findByActividadIdentificador(id)).thenReturn(List.of());
        when(ejecucionActividadDetalladaMapeador.construirDTOs(anyList())).thenReturn(List.of());
        when(indicadorMapeador.construirDTO(indicadorEntidad)).thenReturn(indicadorDTO);

        List<ActividadDetalladaDTO> dtos = mapeador.construirDTOs(List.of(entidad));

        assertEquals(1, dtos.size());
    }
}
