package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.AreaDTO;
import co.edu.uco.sibe.dominio.modelo.Area;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.AreaEntidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AreaMapeadorTest {

    @Mock
    private SubareaMapeador subareaMapeador;

    @Mock
    private ActividadMapeador actividadMapeador;

    private AreaMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new AreaMapeador(subareaMapeador, actividadMapeador);
    }

    @Test
    void deberiaConstruirEntidadDesdeModelo() {
        UUID id = UUID.randomUUID();
        Area area = Area.construir(id, "Area Test", List.of(), List.of());

        when(subareaMapeador.construirEntidades(anyList())).thenReturn(List.of());
        when(actividadMapeador.construirEntidades(anyList())).thenReturn(List.of());

        AreaEntidad entidad = mapeador.construirEntidad(area);

        assertEquals(id, entidad.getIdentificador());
        assertEquals("Area Test", entidad.getNombre());
    }

    @Test
    void deberiaConstruirModeloDesdeEntidad() {
        UUID id = UUID.randomUUID();
        AreaEntidad entidad = new AreaEntidad(id, "Area Entidad", List.of(), List.of());

        when(subareaMapeador.construirModelos(anyList())).thenReturn(List.of());
        when(actividadMapeador.construirModelos(anyList())).thenReturn(List.of());

        Area modelo = mapeador.construirModelo(entidad);

        assertEquals(id, modelo.getIdentificador());
        assertEquals("Area Entidad", modelo.getNombre());
    }

    @Test
    void deberiaConstruirDTODesdeEntidad() {
        UUID id = UUID.randomUUID();
        AreaEntidad entidad = new AreaEntidad(id, "Area DTO", List.of(), List.of());

        AreaDTO dto = mapeador.construirDTO(entidad);

        assertEquals(id.toString(), dto.getIdentificador());
        assertEquals("Area DTO", dto.getNombre());
    }

    @Test
    void deberiaConstruirListaEntidades() {
        Area a1 = Area.construir(UUID.randomUUID(), "A1", List.of(), List.of());

        when(subareaMapeador.construirEntidades(anyList())).thenReturn(List.of());
        when(actividadMapeador.construirEntidades(anyList())).thenReturn(List.of());

        List<AreaEntidad> entidades = mapeador.construirEntidades(List.of(a1));

        assertEquals(1, entidades.size());
    }

    @Test
    void deberiaConstruirListaModelos() {
        AreaEntidad e1 = new AreaEntidad(UUID.randomUUID(), "A1", List.of(), List.of());

        when(subareaMapeador.construirModelos(anyList())).thenReturn(List.of());
        when(actividadMapeador.construirModelos(anyList())).thenReturn(List.of());

        List<Area> modelos = mapeador.construirModelos(List.of(e1));

        assertEquals(1, modelos.size());
    }

    @Test
    void deberiaConstruirListaDTOs() {
        AreaEntidad e1 = new AreaEntidad(UUID.randomUUID(), "A1", List.of(), List.of());
        AreaEntidad e2 = new AreaEntidad(UUID.randomUUID(), "A2", List.of(), List.of());

        List<AreaDTO> dtos = mapeador.construirDTOs(List.of(e1, e2));

        assertEquals(2, dtos.size());
    }
}
