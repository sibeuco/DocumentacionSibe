package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.DireccionDetalladaDTO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.DireccionEntidad;
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
class DireccionDetalladaMapeadorTest {

    @Mock
    private AreaDetalladaMapeador areaDetalladaMapeador;

    @Mock
    private ActividadDetalladaMapeador actividadDetalladaMapeador;

    private DireccionDetalladaMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new DireccionDetalladaMapeador(areaDetalladaMapeador, actividadDetalladaMapeador);
    }

    @Test
    void deberiaConstruirDTODesdeEntidad() {
        UUID id = UUID.randomUUID();
        DireccionEntidad entidad = new DireccionEntidad(id, "Direccion Detallada", List.of(), List.of());

        when(areaDetalladaMapeador.construirDTOs(anyList())).thenReturn(List.of());
        when(actividadDetalladaMapeador.construirDTOs(anyList())).thenReturn(List.of());

        DireccionDetalladaDTO dto = mapeador.construirDTO(entidad);

        assertEquals(id, dto.getIdentificador());
        assertEquals("Direccion Detallada", dto.getNombre());
        assertTrue(dto.getAreas().isEmpty());
        assertTrue(dto.getActividades().isEmpty());
    }

    @Test
    void deberiaConstruirListaDTOs() {
        DireccionEntidad e1 = new DireccionEntidad(UUID.randomUUID(), "D1", List.of(), List.of());

        when(areaDetalladaMapeador.construirDTOs(anyList())).thenReturn(List.of());
        when(actividadDetalladaMapeador.construirDTOs(anyList())).thenReturn(List.of());

        List<DireccionDetalladaDTO> dtos = mapeador.construirDTOs(List.of(e1));

        assertEquals(1, dtos.size());
    }
}
