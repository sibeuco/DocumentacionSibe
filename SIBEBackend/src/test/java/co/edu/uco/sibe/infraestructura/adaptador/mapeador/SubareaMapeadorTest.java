package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.SubareaDTO;
import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.modelo.Subarea;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.ActividadEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.SubareaEntidad;
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
class SubareaMapeadorTest {

    @Mock
    private ActividadMapeador actividadMapeador;

    private SubareaMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new SubareaMapeador(actividadMapeador);
    }

    @Test
    void deberiaConstruirEntidadDesdeModelo() {
        UUID id = UUID.randomUUID();
        Subarea subarea = Subarea.construir(id, "Subarea Test", List.of());

        when(actividadMapeador.construirEntidades(anyList())).thenReturn(List.of());

        SubareaEntidad entidad = mapeador.construirEntidad(subarea);

        assertEquals(id, entidad.getIdentificador());
        assertEquals("Subarea Test", entidad.getNombre());
        assertTrue(entidad.getActividades().isEmpty());
    }

    @Test
    void deberiaConstruirModeloDesdeEntidad() {
        UUID id = UUID.randomUUID();
        SubareaEntidad entidad = new SubareaEntidad(id, "Subarea Entidad", List.of());

        when(actividadMapeador.construirModelos(anyList())).thenReturn(List.of());

        Subarea modelo = mapeador.construirModelo(entidad);

        assertEquals(id, modelo.getIdentificador());
        assertEquals("Subarea Entidad", modelo.getNombre());
        assertTrue(modelo.getActividades().isEmpty());
    }

    @Test
    void deberiaConstruirDTODesdeEntidad() {
        UUID id = UUID.randomUUID();
        SubareaEntidad entidad = new SubareaEntidad(id, "Subarea DTO", List.of());

        SubareaDTO dto = mapeador.construirDTO(entidad);

        assertEquals(id.toString(), dto.getIdentificador());
        assertEquals("Subarea DTO", dto.getNombre());
    }

    @Test
    void deberiaConstruirListaEntidades() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        Subarea s1 = Subarea.construir(id1, "S1", List.of());
        Subarea s2 = Subarea.construir(id2, "S2", List.of());

        when(actividadMapeador.construirEntidades(anyList())).thenReturn(List.of());

        List<SubareaEntidad> entidades = mapeador.construirEntidades(List.of(s1, s2));

        assertEquals(2, entidades.size());
    }

    @Test
    void deberiaConstruirListaModelos() {
        SubareaEntidad e1 = new SubareaEntidad(UUID.randomUUID(), "S1", List.of());

        when(actividadMapeador.construirModelos(anyList())).thenReturn(List.of());

        List<Subarea> modelos = mapeador.construirModelos(List.of(e1));

        assertEquals(1, modelos.size());
    }

    @Test
    void deberiaConstruirListaDTOs() {
        SubareaEntidad e1 = new SubareaEntidad(UUID.randomUUID(), "S1", List.of());
        SubareaEntidad e2 = new SubareaEntidad(UUID.randomUUID(), "S2", List.of());

        List<SubareaDTO> dtos = mapeador.construirDTOs(List.of(e1, e2));

        assertEquals(2, dtos.size());
    }
}
