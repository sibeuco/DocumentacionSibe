package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.UsuarioAreaDTO;
import co.edu.uco.sibe.dominio.enums.TipoArea;
import co.edu.uco.sibe.infraestructura.adaptador.dao.UsuarioOrganizacionDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioOrganizacionMapeadorTest {

    @Mock
    private UsuarioOrganizacionDAO usuarioOrganizacionDAO;

    private UsuarioOrganizacionMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new UsuarioOrganizacionMapeador(usuarioOrganizacionDAO);
    }

    @Test
    void deberiaConstruirEntidadVinculadaConDireccion() {
        UsuarioEntidad usuario = mock(UsuarioEntidad.class);
        DireccionEntidad direccion = new DireccionEntidad(UUID.randomUUID(), "Dirección Test", List.of(), List.of());

        when(usuarioOrganizacionDAO.findById(any(UUID.class))).thenReturn(Optional.empty());

        UsuarioOrganizacionEntidad entidad = mapeador.construirEntidadVinculadaConDireccion(usuario, direccion);

        assertNotNull(entidad);
        assertEquals(direccion, entidad.getDireccion());
        assertNull(entidad.getArea());
        assertNull(entidad.getSubarea());
    }

    @Test
    void deberiaCambiarEntidadVinculadaConDireccion() {
        DireccionEntidad nuevaDireccion = new DireccionEntidad(UUID.randomUUID(), "Nueva Dirección", List.of(), List.of());
        AreaEntidad area = new AreaEntidad(UUID.randomUUID(), "Area", List.of(), List.of());
        UsuarioOrganizacionEntidad entidad = new UsuarioOrganizacionEntidad(UUID.randomUUID(), mock(UsuarioEntidad.class), null, area, null);

        mapeador.cambiarEntidadVinculadaConDireccion(entidad, nuevaDireccion);

        assertEquals(nuevaDireccion, entidad.getDireccion());
        assertNull(entidad.getArea());
        assertNull(entidad.getSubarea());
    }

    @Test
    void deberiaConstruirEntidadVinculadaConArea() {
        UsuarioEntidad usuario = mock(UsuarioEntidad.class);
        AreaEntidad area = new AreaEntidad(UUID.randomUUID(), "Area Test", List.of(), List.of());

        when(usuarioOrganizacionDAO.findById(any(UUID.class))).thenReturn(Optional.empty());

        UsuarioOrganizacionEntidad entidad = mapeador.construirEntidadVinculadaConArea(usuario, area);

        assertNotNull(entidad);
        assertNull(entidad.getDireccion());
        assertEquals(area, entidad.getArea());
        assertNull(entidad.getSubarea());
    }

    @Test
    void deberiaCambiarEntidadVinculadaConArea() {
        AreaEntidad nuevaArea = new AreaEntidad(UUID.randomUUID(), "Nueva Area", List.of(), List.of());
        DireccionEntidad direccion = new DireccionEntidad(UUID.randomUUID(), "Dir", List.of(), List.of());
        UsuarioOrganizacionEntidad entidad = new UsuarioOrganizacionEntidad(UUID.randomUUID(), mock(UsuarioEntidad.class), direccion, null, null);

        mapeador.cambiarEntidadVinculadaConArea(entidad, nuevaArea);

        assertNull(entidad.getDireccion());
        assertEquals(nuevaArea, entidad.getArea());
        assertNull(entidad.getSubarea());
    }

    @Test
    void deberiaConstruirEntidadVinculadaConSubarea() {
        UsuarioEntidad usuario = mock(UsuarioEntidad.class);
        SubareaEntidad subarea = new SubareaEntidad(UUID.randomUUID(), "Subarea Test", List.of());

        when(usuarioOrganizacionDAO.findById(any(UUID.class))).thenReturn(Optional.empty());

        UsuarioOrganizacionEntidad entidad = mapeador.construirEntidadVinculadaConSubarea(usuario, subarea);

        assertNotNull(entidad);
        assertNull(entidad.getDireccion());
        assertNull(entidad.getArea());
        assertEquals(subarea, entidad.getSubarea());
    }

    @Test
    void deberiaConstruirDTOConDireccion() {
        UUID dirId = UUID.randomUUID();
        DireccionEntidad direccion = new DireccionEntidad(dirId, "Dirección DTO", List.of(), List.of());
        UsuarioOrganizacionEntidad entidad = new UsuarioOrganizacionEntidad(UUID.randomUUID(), mock(UsuarioEntidad.class), direccion, null, null);

        UsuarioAreaDTO dto = mapeador.construirDTO(entidad);

        assertEquals(dirId.toString(), dto.getIdentificador());
        assertEquals("Dirección DTO", dto.getNombre());
        assertEquals(TipoArea.DIRECCION.name(), dto.getTipoArea());
    }

    @Test
    void deberiaConstruirDTOConArea() {
        UUID areaId = UUID.randomUUID();
        AreaEntidad area = new AreaEntidad(areaId, "Area DTO", List.of(), List.of());
        UsuarioOrganizacionEntidad entidad = new UsuarioOrganizacionEntidad(UUID.randomUUID(), mock(UsuarioEntidad.class), null, area, null);

        UsuarioAreaDTO dto = mapeador.construirDTO(entidad);

        assertEquals(areaId.toString(), dto.getIdentificador());
        assertEquals("Area DTO", dto.getNombre());
        assertEquals(TipoArea.AREA.name(), dto.getTipoArea());
    }

    @Test
    void deberiaConstruirDTOConSubarea() {
        UUID subareaId = UUID.randomUUID();
        SubareaEntidad subarea = new SubareaEntidad(subareaId, "Subarea DTO", List.of());
        UsuarioOrganizacionEntidad entidad = new UsuarioOrganizacionEntidad(UUID.randomUUID(), mock(UsuarioEntidad.class), null, null, subarea);

        UsuarioAreaDTO dto = mapeador.construirDTO(entidad);

        assertEquals(subareaId.toString(), dto.getIdentificador());
        assertEquals("Subarea DTO", dto.getNombre());
        assertEquals(TipoArea.SUBAREA.name(), dto.getTipoArea());
    }

    @Test
    void deberiaCambiarEntidadVinculadaConSubarea() {
        SubareaEntidad nuevaSubarea = new SubareaEntidad(UUID.randomUUID(), "Nueva Subarea", List.of());
        DireccionEntidad direccion = new DireccionEntidad(UUID.randomUUID(), "Dir", List.of(), List.of());
        UsuarioOrganizacionEntidad entidad = new UsuarioOrganizacionEntidad(UUID.randomUUID(), mock(UsuarioEntidad.class), direccion, null, null);

        mapeador.cambiarEntidadVinculadaConArea(entidad, nuevaSubarea);

        assertNull(entidad.getDireccion());
        assertNull(entidad.getArea());
        assertEquals(nuevaSubarea, entidad.getSubarea());
    }
}
