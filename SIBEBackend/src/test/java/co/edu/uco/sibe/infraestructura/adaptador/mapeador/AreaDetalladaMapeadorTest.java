package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.AreaDetalladaDTO;
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
class AreaDetalladaMapeadorTest {

    @Mock
    private SubareaDetalladaMapeador subareaDetalladaMapeador;

    @Mock
    private ActividadDetalladaMapeador actividadDetalladaMapeador;

    private AreaDetalladaMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new AreaDetalladaMapeador(subareaDetalladaMapeador, actividadDetalladaMapeador);
    }

    @Test
    void deberiaConstruirDTODesdeEntidad() {
        UUID id = UUID.randomUUID();
        AreaEntidad entidad = new AreaEntidad(id, "Area Detallada", List.of(), List.of());

        when(subareaDetalladaMapeador.construirDTOs(anyList())).thenReturn(List.of());
        when(actividadDetalladaMapeador.construirDTOs(anyList())).thenReturn(List.of());

        AreaDetalladaDTO dto = mapeador.construirDTO(entidad);

        assertEquals(id, dto.getIdentificador());
        assertEquals("Area Detallada", dto.getNombre());
        assertTrue(dto.getSubareas().isEmpty());
        assertTrue(dto.getActividades().isEmpty());
    }

    @Test
    void deberiaConstruirListaDTOs() {
        AreaEntidad e1 = new AreaEntidad(UUID.randomUUID(), "A1", List.of(), List.of());

        when(subareaDetalladaMapeador.construirDTOs(anyList())).thenReturn(List.of());
        when(actividadDetalladaMapeador.construirDTOs(anyList())).thenReturn(List.of());

        List<AreaDetalladaDTO> dtos = mapeador.construirDTOs(List.of(e1));

        assertEquals(1, dtos.size());
    }
}
