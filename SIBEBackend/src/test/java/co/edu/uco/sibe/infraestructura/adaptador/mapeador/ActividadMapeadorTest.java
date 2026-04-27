package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.ActividadDTO;
import co.edu.uco.sibe.dominio.dto.IndicadorDTO;
import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.modelo.Indicador;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActividadMapeadorTest {

    @Mock
    private PersonaDAO personaDAO;

    @Mock
    private IndicadorMapeador indicadorMapeador;

    private ActividadMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new ActividadMapeador(personaDAO, indicadorMapeador);
    }

    @Test
    void deberiaConstruirEntidadDesdeModelo() {
        UUID id = UUID.randomUUID();
        UUID colaboradorId = UUID.randomUUID();
        UUID creadorId = UUID.randomUUID();
        Indicador indicadorModelo = mock(Indicador.class);
        IndicadorEntidad indicadorEntidad = mock(IndicadorEntidad.class);
        LocalDate fecha = LocalDate.now();

        Actividad actividad = Actividad.construir(id, "Nombre", "Objetivo", "2025-1", "/ruta", fecha, indicadorModelo, colaboradorId, creadorId);

        when(indicadorMapeador.construirEntidad(indicadorModelo)).thenReturn(indicadorEntidad);

        ActividadEntidad entidad = mapeador.construirEntidad(actividad);

        assertEquals(id, entidad.getIdentificador());
        assertEquals("Nombre", entidad.getNombre());
        assertEquals("Objetivo", entidad.getObjetivo());
        assertEquals("2025-1", entidad.getSemestre());
        assertEquals("/ruta", entidad.getRutaInsumos());
        assertEquals(fecha, entidad.getFechaCreacion());
        assertEquals(colaboradorId, entidad.getColaborador());
        assertEquals(creadorId, entidad.getCreador());
    }

    @Test
    void deberiaConstruirModeloDesdeEntidad() {
        UUID id = UUID.randomUUID();
        UUID colaboradorId = UUID.randomUUID();
        UUID creadorId = UUID.randomUUID();
        IndicadorEntidad indicadorEntidad = mock(IndicadorEntidad.class);
        Indicador indicadorModelo = mock(Indicador.class);
        LocalDate fecha = LocalDate.now();

        ActividadEntidad entidad = new ActividadEntidad(id, "Nombre", "Objetivo", "2025-1", "/ruta", fecha, indicadorEntidad, colaboradorId, creadorId);

        when(indicadorMapeador.construriModelo(indicadorEntidad)).thenReturn(indicadorModelo);

        Actividad modelo = mapeador.construirModelo(entidad);

        assertEquals(id, modelo.getIdentificador());
        assertEquals("Nombre", modelo.getNombre());
        assertEquals("Objetivo", modelo.getObjetivo());
        assertEquals("2025-1", modelo.getSemestre());
    }

    @Test
    void deberiaActualizarEntidad() {
        UUID id = UUID.randomUUID();
        UUID colaboradorId = UUID.randomUUID();
        UUID creadorId = UUID.randomUUID();
        IndicadorEntidad indicadorEntidad = mock(IndicadorEntidad.class);
        Indicador indicadorModelo = mock(Indicador.class);
        IndicadorEntidad nuevaIndicadorEntidad = mock(IndicadorEntidad.class);
        LocalDate fecha = LocalDate.now();

        ActividadEntidad entidad = new ActividadEntidad(id, "Original", "Original", "2024-2", "/old", fecha, indicadorEntidad, colaboradorId, creadorId);
        Actividad actividad = Actividad.construir(UUID.randomUUID(), "Nuevo", "Nuevo Obj", "2025-1", "/new", fecha, indicadorModelo, UUID.randomUUID(), UUID.randomUUID());

        when(indicadorMapeador.construirEntidad(indicadorModelo)).thenReturn(nuevaIndicadorEntidad);

        mapeador.actualizarEntidad(entidad, actividad);

        assertEquals("Nuevo", entidad.getNombre());
        assertEquals("Nuevo Obj", entidad.getObjetivo());
        assertEquals("2025-1", entidad.getSemestre());
        assertEquals("/new", entidad.getRutaInsumos());
    }

    @Test
    void deberiaConstruirDTODesdeEntidad() {
        UUID id = UUID.randomUUID();
        UUID colaboradorId = UUID.randomUUID();
        UUID creadorId = UUID.randomUUID();
        IndicadorEntidad indicadorEntidad = mock(IndicadorEntidad.class);
        IndicadorDTO indicadorDTO = mock(IndicadorDTO.class);
        LocalDate fecha = LocalDate.now();

        ActividadEntidad entidad = new ActividadEntidad(id, "Nombre", "Objetivo", "2025-1", "/ruta", fecha, indicadorEntidad, colaboradorId, creadorId);

        PersonaEntidad persona = mock(PersonaEntidad.class);
        when(persona.getNombres()).thenReturn("Juan");
        when(persona.getApellidos()).thenReturn("Pérez");
        when(personaDAO.findById(colaboradorId)).thenReturn(Optional.of(persona));
        when(indicadorMapeador.construirDTO(indicadorEntidad)).thenReturn(indicadorDTO);

        ActividadDTO dto = mapeador.construirDTO(entidad);

        assertEquals(id.toString(), dto.getIdentificador());
        assertEquals("Nombre", dto.getNombre());
        assertEquals("Objetivo", dto.getObjetivo());
    }

    @Test
    void deberiaConstruirListaEntidades() {
        UUID id = UUID.randomUUID();
        Indicador indicadorModelo = mock(Indicador.class);
        IndicadorEntidad indicadorEntidad = mock(IndicadorEntidad.class);
        Actividad actividad = Actividad.construir(id, "N", "O", "2025-1", "/r", LocalDate.now(), indicadorModelo, UUID.randomUUID(), UUID.randomUUID());

        when(indicadorMapeador.construirEntidad(indicadorModelo)).thenReturn(indicadorEntidad);

        List<ActividadEntidad> entidades = mapeador.construirEntidades(List.of(actividad));

        assertEquals(1, entidades.size());
    }

    @Test
    void deberiaConstruirListaModelos() {
        IndicadorEntidad indicadorEntidad = mock(IndicadorEntidad.class);
        Indicador indicadorModelo = mock(Indicador.class);
        ActividadEntidad entidad = new ActividadEntidad(UUID.randomUUID(), "N", "O", "2025-1", "/r", LocalDate.now(), indicadorEntidad, UUID.randomUUID(), UUID.randomUUID());

        when(indicadorMapeador.construriModelo(indicadorEntidad)).thenReturn(indicadorModelo);

        List<Actividad> modelos = mapeador.construirModelos(List.of(entidad));

        assertEquals(1, modelos.size());
    }

    @Test
    void deberiaConstruirListaDTOs() {
        UUID colaboradorId = UUID.randomUUID();
        IndicadorEntidad indicadorEntidad = mock(IndicadorEntidad.class);
        IndicadorDTO indicadorDTO = mock(IndicadorDTO.class);
        ActividadEntidad entidad = new ActividadEntidad(UUID.randomUUID(), "N", "O", "2025-1", "/r", LocalDate.now(), indicadorEntidad, colaboradorId, UUID.randomUUID());

        PersonaEntidad persona = mock(PersonaEntidad.class);
        when(persona.getNombres()).thenReturn("Juan");
        when(persona.getApellidos()).thenReturn("Pérez");
        when(personaDAO.findById(colaboradorId)).thenReturn(Optional.of(persona));
        when(indicadorMapeador.construirDTO(indicadorEntidad)).thenReturn(indicadorDTO);

        List<ActividadDTO> dtos = mapeador.construirDTOs(List.of(entidad));

        assertEquals(1, dtos.size());
    }
}
