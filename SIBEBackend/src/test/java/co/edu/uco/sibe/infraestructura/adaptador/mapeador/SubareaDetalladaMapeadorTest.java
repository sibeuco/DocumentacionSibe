package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.SubareaDetalladaDTO;
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
class SubareaDetalladaMapeadorTest {

    @Mock
    private ActividadDetalladaMapeador actividadDetalladaMapeador;

    private SubareaDetalladaMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new SubareaDetalladaMapeador(actividadDetalladaMapeador);
    }

    @Test
    void deberiaConstruirDTODesdeEntidad() {
        UUID id = UUID.randomUUID();
        SubareaEntidad entidad = new SubareaEntidad(id, "Subarea Detallada", List.of());

        when(actividadDetalladaMapeador.construirDTOs(anyList())).thenReturn(List.of());

        SubareaDetalladaDTO dto = mapeador.construirDTO(entidad);

        assertEquals(id.toString(), dto.getIdentificador());
        assertEquals("Subarea Detallada", dto.getNombre());
        assertTrue(dto.getActividades().isEmpty());
    }

    @Test
    void deberiaConstruirListaDTOs() {
        SubareaEntidad e1 = new SubareaEntidad(UUID.randomUUID(), "S1", List.of());
        SubareaEntidad e2 = new SubareaEntidad(UUID.randomUUID(), "S2", List.of());

        when(actividadDetalladaMapeador.construirDTOs(anyList())).thenReturn(List.of());

        List<SubareaDetalladaDTO> dtos = mapeador.construirDTOs(List.of(e1, e2));

        assertEquals(2, dtos.size());
    }
}
