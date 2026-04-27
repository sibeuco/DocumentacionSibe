package co.edu.uco.sibe.infraestructura.seguridad.configuration;

import co.edu.uco.sibe.aplicacion.consulta.ConsultarUsuarioPorCorreoManejador;
import co.edu.uco.sibe.dominio.dto.TipoUsuarioDTO;
import co.edu.uco.sibe.dominio.dto.UsuarioDTO;
import co.edu.uco.sibe.dominio.puerto.servicio.EncriptarClaveServicio;
import co.edu.uco.sibe.dominio.transversal.excepcion.AuthorizationException;
import co.edu.uco.sibe.infraestructura.adaptador.dao.PersonaDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.UsuarioDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.PersonaEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.UsuarioEntidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsernamePwdAuthenticationProviderTest {

    @Mock private UsuarioDAO usuarioDAO;
    @Mock private PersonaDAO personaDAO;
    @Mock private ConsultarUsuarioPorCorreoManejador consultarUsuarioPorCorreoManejador;
    @Mock private EncriptarClaveServicio encriptarClaveServicio;

    @InjectMocks
    private UsernamePwdAuthenticationProvider provider;

    private final String correo = "test@example.com";
    private final String clave = "password123";

    @BeforeEach
    void setUp() {
        // @InjectMocks handles field injection for @Autowired fields
    }

    @Test
    void deberiaAutenticarUsuarioExitosamente() {
        UsuarioEntidad usuarioEntidad = new UsuarioEntidad();
        usuarioEntidad.setClave("hashedPassword");

        PersonaEntidad personaEntidad = new PersonaEntidad();
        UUID personaId = UUID.randomUUID();
        personaEntidad.setIdentificador(personaId);

        TipoUsuarioDTO tipoUsuario = new TipoUsuarioDTO("1", "ADMIN", "Administrador", true, true, true, true);
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setTipoUsuario(tipoUsuario);

        when(usuarioDAO.findByCorreo(correo)).thenReturn(usuarioEntidad);
        when(consultarUsuarioPorCorreoManejador.ejecutar(correo)).thenReturn(usuarioDTO);
        when(encriptarClaveServicio.existe(clave, "hashedPassword")).thenReturn(true);
        when(personaDAO.findByCorreo(correo)).thenReturn(personaEntidad);

        Authentication auth = new UsernamePasswordAuthenticationToken(correo, clave);
        Authentication resultado = provider.authenticate(auth);

        assertNotNull(resultado);
        assertEquals(correo, resultado.getName());
        assertFalse(resultado.getAuthorities().isEmpty());
        assertEquals(personaId, resultado.getDetails());
    }

    @Test
    void deberiaLanzarExcepcionCuandoUsuarioNoExiste() {
        when(usuarioDAO.findByCorreo(correo)).thenReturn(null);
        when(consultarUsuarioPorCorreoManejador.ejecutar(correo)).thenReturn(null);

        Authentication auth = new UsernamePasswordAuthenticationToken(correo, clave);

        assertThrows(AuthorizationException.class, () -> provider.authenticate(auth));
    }

    @Test
    void deberiaLanzarExcepcionCuandoClaveEsIncorrecta() {
        UsuarioEntidad usuarioEntidad = new UsuarioEntidad();
        usuarioEntidad.setClave("hashedPassword");

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setTipoUsuario(new TipoUsuarioDTO("1", "ADMIN", "Admin", true, true, true, true));

        when(usuarioDAO.findByCorreo(correo)).thenReturn(usuarioEntidad);
        when(consultarUsuarioPorCorreoManejador.ejecutar(correo)).thenReturn(usuarioDTO);
        when(encriptarClaveServicio.existe(clave, "hashedPassword")).thenReturn(false);

        Authentication auth = new UsernamePasswordAuthenticationToken(correo, clave);

        assertThrows(AuthorizationException.class, () -> provider.authenticate(auth));
    }

    @Test
    void deberiaSoportarUsernamePasswordAuthenticationToken() {
        assertTrue(provider.supports(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void noDeberiaSoportarOtroTipoDeAuthentication() {
        assertFalse(provider.supports(Authentication.class));
    }

    @Test
    void deberiaAgregarPrivilegiosCRUDCompletos() {
        UsuarioEntidad usuarioEntidad = new UsuarioEntidad();
        usuarioEntidad.setClave("hashedPassword");

        PersonaEntidad personaEntidad = new PersonaEntidad();
        personaEntidad.setIdentificador(UUID.randomUUID());

        TipoUsuarioDTO tipoUsuario = new TipoUsuarioDTO("1", "ADMIN", "Administrador", true, true, true, true);
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setTipoUsuario(tipoUsuario);

        when(usuarioDAO.findByCorreo(correo)).thenReturn(usuarioEntidad);
        when(consultarUsuarioPorCorreoManejador.ejecutar(correo)).thenReturn(usuarioDTO);
        when(encriptarClaveServicio.existe(clave, "hashedPassword")).thenReturn(true);
        when(personaDAO.findByCorreo(correo)).thenReturn(personaEntidad);

        Authentication auth = new UsernamePasswordAuthenticationToken(correo, clave);
        Authentication resultado = provider.authenticate(auth);

        // role + 4 CRUD privileges = 5 authorities
        assertEquals(5, resultado.getAuthorities().size());
    }

    @Test
    void deberiaAgregarSoloPrivilegiosDeLectura() {
        UsuarioEntidad usuarioEntidad = new UsuarioEntidad();
        usuarioEntidad.setClave("hashedPassword");

        PersonaEntidad personaEntidad = new PersonaEntidad();
        personaEntidad.setIdentificador(UUID.randomUUID());

        TipoUsuarioDTO tipoUsuario = new TipoUsuarioDTO("1", "USER", "Usuario", false, false, false, true);
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setTipoUsuario(tipoUsuario);

        when(usuarioDAO.findByCorreo(correo)).thenReturn(usuarioEntidad);
        when(consultarUsuarioPorCorreoManejador.ejecutar(correo)).thenReturn(usuarioDTO);
        when(encriptarClaveServicio.existe(clave, "hashedPassword")).thenReturn(true);
        when(personaDAO.findByCorreo(correo)).thenReturn(personaEntidad);

        Authentication auth = new UsernamePasswordAuthenticationToken(correo, clave);
        Authentication resultado = provider.authenticate(auth);

        // role + 1 read privilege = 2 authorities
        assertEquals(2, resultado.getAuthorities().size());
    }
}
