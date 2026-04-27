package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.IdentificacionDTO;
import co.edu.uco.sibe.dominio.dto.TipoUsuarioDTO;
import co.edu.uco.sibe.dominio.dto.UsuarioAreaDTO;
import co.edu.uco.sibe.dominio.dto.UsuarioDTO;
import co.edu.uco.sibe.dominio.modelo.TipoUsuario;
import co.edu.uco.sibe.dominio.modelo.Usuario;
import co.edu.uco.sibe.infraestructura.adaptador.dao.UsuarioDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.UsuarioOrganizacionDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.UsuarioTipoUsuarioDAO;
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
class UsuarioMapeadorTest {

    @Mock
    private TipoUsuarioMapeador tipoUsuarioMapeador;

    @Mock
    private IdentificacionMapeador identificacionMapeador;

    @Mock
    private UsuarioOrganizacionMapeador usuarioOrganizacionMapeador;

    @Mock
    private UsuarioDAO usuarioDAO;

    @Mock
    private UsuarioTipoUsuarioDAO usuarioTipoUsuarioDAO;

    @Mock
    private UsuarioOrganizacionDAO usuarioOrganizacionDAO;

    private UsuarioMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new UsuarioMapeador(tipoUsuarioMapeador, identificacionMapeador, usuarioOrganizacionMapeador, usuarioDAO, usuarioTipoUsuarioDAO, usuarioOrganizacionDAO);
    }

    @Test
    void deberiaConstruirDTODesdePersonaEntidad() {
        UUID personaId = UUID.randomUUID();
        IdentificacionEntidad idEntidad = mock(IdentificacionEntidad.class);
        PersonaEntidad persona = new PersonaEntidad(personaId, "Juan", "Pérez", "juan@email.com", idEntidad);

        TipoUsuarioEntidad tipoUsuarioEntidad = new TipoUsuarioEntidad(UUID.randomUUID(), "ADM", "Admin", true, true, true, true);
        UsuarioTipoUsuarioEntidad rolEntidad = new UsuarioTipoUsuarioEntidad(UUID.randomUUID(), tipoUsuarioEntidad);
        UsuarioEntidad usuario = new UsuarioEntidad(UUID.randomUUID(), "juan@email.com", "clave", true, rolEntidad);
        UsuarioOrganizacionEntidad orgEntidad = mock(UsuarioOrganizacionEntidad.class);

        IdentificacionDTO idDTO = mock(IdentificacionDTO.class);
        TipoUsuarioDTO tipoDTO = new TipoUsuarioDTO(UUID.randomUUID().toString(), "ADM", "Admin", true, true, true, true);
        UsuarioAreaDTO areaDTO = new UsuarioAreaDTO(UUID.randomUUID().toString(), "Dirección", "DIRECCION");

        when(usuarioDAO.findByCorreo("juan@email.com")).thenReturn(usuario);
        when(identificacionMapeador.construirDTO(idEntidad)).thenReturn(idDTO);
        when(tipoUsuarioMapeador.construirDTO(tipoUsuarioEntidad)).thenReturn(tipoDTO);
        when(usuarioOrganizacionDAO.findByUsuario(usuario)).thenReturn(orgEntidad);
        when(usuarioOrganizacionMapeador.construirDTO(orgEntidad)).thenReturn(areaDTO);

        UsuarioDTO dto = mapeador.construirDTO(persona);

        assertEquals(personaId.toString(), dto.getIdentificador());
        assertEquals("Juan", dto.getNombres());
        assertEquals("Pérez", dto.getApellidos());
        assertTrue(dto.getEstaActivo());
    }

    @Test
    void deberiaConstruirEntidadDesdeModelo() {
        UUID id = UUID.randomUUID();
        TipoUsuario tipoUsuario = TipoUsuario.construir(UUID.randomUUID(), "COORD", "Coordinador", true, true, false, true);
        Usuario usuario = Usuario.construir(id, "maria@email.com", "clave", tipoUsuario, true);

        TipoUsuarioEntidad tipoEntidad = new TipoUsuarioEntidad(UUID.randomUUID(), "COORD", "Coordinador", true, true, false, true);
        when(tipoUsuarioMapeador.construirEntidad(tipoUsuario)).thenReturn(tipoEntidad);
        when(usuarioTipoUsuarioDAO.findById(any(UUID.class))).thenReturn(Optional.empty());

        UsuarioEntidad entidad = mapeador.construirEntidad(usuario, "claveEncriptada");

        assertEquals(id, entidad.getIdentificador());
        assertEquals("maria@email.com", entidad.getCorreo());
        assertEquals("claveEncriptada", entidad.getClave());
        assertTrue(entidad.isEstaActivo());
    }

    @Test
    void deberiaConstruirModeloDesdeEntidad() {
        UUID id = UUID.randomUUID();
        TipoUsuarioEntidad tipoEntidad = new TipoUsuarioEntidad(UUID.randomUUID(), "ADM", "Admin", true, true, true, true);
        UsuarioTipoUsuarioEntidad rolEntidad = new UsuarioTipoUsuarioEntidad(UUID.randomUUID(), tipoEntidad);
        UsuarioEntidad entidad = new UsuarioEntidad(id, "test@email.com", "clave", true, rolEntidad);

        TipoUsuario tipoModelo = TipoUsuario.construir(UUID.randomUUID(), "ADM", "Admin", true, true, true, true);
        when(tipoUsuarioMapeador.construirModelo(tipoEntidad)).thenReturn(tipoModelo);

        Usuario modelo = mapeador.construirModelo(entidad);

        assertEquals(id, modelo.getIdentificador());
        assertEquals("test@email.com", modelo.getCorreo());
    }

    @Test
    void deberiaModificarContrasenaEntidad() {
        UsuarioEntidad entidad = new UsuarioEntidad(UUID.randomUUID(), "test@email.com", "vieja", true, mock(UsuarioTipoUsuarioEntidad.class));

        mapeador.construirModificarContrasenaEntidad(entidad, "nueva");

        assertEquals("nueva", entidad.getClave());
    }

    @Test
    void deberiaModificarEntidad() {
        TipoUsuarioEntidad tipoEntidad = new TipoUsuarioEntidad(UUID.randomUUID(), "ADM", "Admin", true, true, true, true);
        UsuarioTipoUsuarioEntidad rolEntidad = new UsuarioTipoUsuarioEntidad(UUID.randomUUID(), tipoEntidad);
        UsuarioEntidad entidad = new UsuarioEntidad(UUID.randomUUID(), "old@email.com", "clave", true, rolEntidad);

        TipoUsuario nuevoTipo = TipoUsuario.construir(UUID.randomUUID(), "COORD", "Coordinador", true, true, false, true);
        Usuario usuario = Usuario.construir(UUID.randomUUID(), "new@email.com", "nueva", nuevoTipo, false);

        mapeador.modificarEntidad(entidad, usuario);

        assertEquals("new@email.com", entidad.getCorreo());
        verify(tipoUsuarioMapeador).modificarEntidad(eq(rolEntidad), eq(nuevoTipo));
    }

    @Test
    void deberiaConstruirDTOs() {
        UUID personaId = UUID.randomUUID();
        IdentificacionEntidad idEntidad = mock(IdentificacionEntidad.class);
        PersonaEntidad persona = new PersonaEntidad(personaId, "Ana", "Gómez", "ana@email.com", idEntidad);

        TipoUsuarioEntidad tipoUsuarioEntidad = new TipoUsuarioEntidad(UUID.randomUUID(), "ADM", "Admin", true, true, true, true);
        UsuarioTipoUsuarioEntidad rolEntidad = new UsuarioTipoUsuarioEntidad(UUID.randomUUID(), tipoUsuarioEntidad);
        UsuarioEntidad usuario = new UsuarioEntidad(UUID.randomUUID(), "ana@email.com", "clave", true, rolEntidad);
        UsuarioOrganizacionEntidad orgEntidad = mock(UsuarioOrganizacionEntidad.class);

        when(usuarioDAO.findByCorreo("ana@email.com")).thenReturn(usuario);
        when(identificacionMapeador.construirDTO(idEntidad)).thenReturn(mock(co.edu.uco.sibe.dominio.dto.IdentificacionDTO.class));
        when(tipoUsuarioMapeador.construirDTO(tipoUsuarioEntidad)).thenReturn(mock(co.edu.uco.sibe.dominio.dto.TipoUsuarioDTO.class));
        when(usuarioOrganizacionDAO.findByUsuario(usuario)).thenReturn(orgEntidad);
        when(usuarioOrganizacionMapeador.construirDTO(orgEntidad)).thenReturn(mock(co.edu.uco.sibe.dominio.dto.UsuarioAreaDTO.class));

        List<co.edu.uco.sibe.dominio.dto.UsuarioDTO> dtos = mapeador.construirDTOs(List.of(persona));

        assertEquals(1, dtos.size());
    }
}
