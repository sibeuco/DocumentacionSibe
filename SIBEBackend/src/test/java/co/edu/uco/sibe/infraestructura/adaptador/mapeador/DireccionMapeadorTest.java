package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.DireccionDTO;
import co.edu.uco.sibe.dominio.modelo.Direccion;
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
class DireccionMapeadorTest {

    @Mock
    private AreaMapeador areaMapeador;

    @Mock
    private ActividadMapeador actividadMapeador;

    private DireccionMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new DireccionMapeador(areaMapeador, actividadMapeador);
    }

    @Test
    void deberiaConstruirEntidadDesdeModelo() {
        UUID id = UUID.randomUUID();
        Direccion direccion = Direccion.construir(id, "Direccion Test", List.of(), List.of());

        when(areaMapeador.construirEntidades(anyList())).thenReturn(List.of());
        when(actividadMapeador.construirEntidades(anyList())).thenReturn(List.of());

        DireccionEntidad entidad = mapeador.construirEntidad(direccion);

        assertEquals(id, entidad.getIdentificador());
        assertEquals("Direccion Test", entidad.getNombre());
    }

    @Test
    void deberiaConstruirModeloDesdeEntidad() {
        UUID id = UUID.randomUUID();
        DireccionEntidad entidad = new DireccionEntidad(id, "Direccion Entidad", List.of(), List.of());

        when(areaMapeador.construirModelos(anyList())).thenReturn(List.of());
        when(actividadMapeador.construirModelos(anyList())).thenReturn(List.of());

        Direccion modelo = mapeador.construirModelo(entidad);

        assertEquals(id, modelo.getIdentificador());
        assertEquals("Direccion Entidad", modelo.getNombre());
    }

    @Test
    void deberiaConstruirDTODesdeEntidad() {
        UUID id = UUID.randomUUID();
        DireccionEntidad entidad = new DireccionEntidad(id, "Direccion DTO", List.of(), List.of());

        DireccionDTO dto = mapeador.construirDTO(entidad);

        assertEquals(id.toString(), dto.getIdentificador());
        assertEquals("Direccion DTO", dto.getNombre());
    }

    @Test
    void deberiaConstruirListaDTOs() {
        DireccionEntidad e1 = new DireccionEntidad(UUID.randomUUID(), "D1", List.of(), List.of());

        List<DireccionDTO> dtos = mapeador.construirDTOs(List.of(e1));

        assertEquals(1, dtos.size());
    }
}
