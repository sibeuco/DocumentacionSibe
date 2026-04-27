package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.dto.PersonaDTO;
import co.edu.uco.sibe.dominio.dto.UsuarioDTO;
import co.edu.uco.sibe.dominio.modelo.Persona;
import co.edu.uco.sibe.dominio.modelo.Usuario;
import co.edu.uco.sibe.infraestructura.adaptador.dao.*;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.*;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.PersonaMapeador;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.PeticionRecuperacionClaveMapeador;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.UsuarioMapeador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonaRepositorioConsultaImplementacionTest {

    @Mock private PersonaDAO personaDAO;
    @Mock private PersonaMapeador personaMapeador;
    @Mock private UsuarioDAO usuarioDAO;
    @Mock private UsuarioMapeador usuarioMapeador;
    @Mock private IdentificacionDAO identificacionDAO;
    @Mock private PeticionRecuperacionClaveDAO peticionRecuperacionClaveDAO;
    @Mock private PeticionRecuperacionClaveMapeador peticionRecuperacionClaveMapeador;

    private PersonaRepositorioConsultaImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new PersonaRepositorioConsultaImplementacion(
                personaDAO, personaMapeador, usuarioDAO, usuarioMapeador,
                identificacionDAO, peticionRecuperacionClaveDAO, peticionRecuperacionClaveMapeador
        );
    }

    @Test
    void deberiaConsultarPersonaPorIdentificadorExistente() {
        UUID id = UUID.randomUUID();
        PersonaEntidad entidad = new PersonaEntidad();
        entidad.setCorreo("test@test.com");
        UsuarioEntidad usuario = new UsuarioEntidad();
        usuario.setEstaActivo(true);
        Persona modelo = mock(Persona.class);

        when(personaDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(usuarioDAO.findByCorreo("test@test.com")).thenReturn(usuario);
        when(personaMapeador.construirModelo(entidad)).thenReturn(modelo);

        Persona resultado = repositorio.consultarPersonaPorIdentificador(id);

        assertNotNull(resultado);
    }

    @Test
    void deberiaRetornarNullCuandoPersonaNoExiste() {
        UUID id = UUID.randomUUID();
        when(personaDAO.findById(id)).thenReturn(Optional.empty());

        Persona resultado = repositorio.consultarPersonaPorIdentificador(id);

        assertNull(resultado);
    }

    @Test
    void deberiaRetornarNullCuandoUsuarioInactivo() {
        UUID id = UUID.randomUUID();
        PersonaEntidad entidad = new PersonaEntidad();
        entidad.setCorreo("test@test.com");
        UsuarioEntidad usuario = new UsuarioEntidad();
        usuario.setEstaActivo(false);

        when(personaDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(usuarioDAO.findByCorreo("test@test.com")).thenReturn(usuario);

        Persona resultado = repositorio.consultarPersonaPorIdentificador(id);

        assertNull(resultado);
    }

    @Test
    void deberiaConsultarPersonaPorCorreoDTO() {
        PersonaEntidad entidad = new PersonaEntidad();
        UsuarioEntidad usuario = new UsuarioEntidad();
        usuario.setEstaActivo(true);
        PersonaDTO dto = new PersonaDTO();

        when(personaDAO.findByCorreo("test@test.com")).thenReturn(entidad);
        when(usuarioDAO.findByCorreo("test@test.com")).thenReturn(usuario);
        when(personaMapeador.construirDTO(entidad)).thenReturn(dto);

        PersonaDTO resultado = repositorio.consultarPersonaPorCorreoDTO("test@test.com");

        assertNotNull(resultado);
    }

    @Test
    void deberiaRetornarNullParaCorreoDTOInexistente() {
        when(personaDAO.findByCorreo("no@existe.com")).thenReturn(null);

        PersonaDTO resultado = repositorio.consultarPersonaPorCorreoDTO("no@existe.com");

        assertNull(resultado);
    }

    @Test
    void deberiaConsultarPersonaPorCorreo() {
        PersonaEntidad entidad = new PersonaEntidad();
        UsuarioEntidad usuario = new UsuarioEntidad();
        usuario.setEstaActivo(true);
        Persona modelo = mock(Persona.class);

        when(personaDAO.findByCorreo("test@test.com")).thenReturn(entidad);
        when(usuarioDAO.findByCorreo("test@test.com")).thenReturn(usuario);
        when(personaMapeador.construirModelo(entidad)).thenReturn(modelo);

        Persona resultado = repositorio.consultarPersonaPorCorreo("test@test.com");

        assertNotNull(resultado);
    }

    @Test
    void deberiaConsultarPersonaPorDocumento() {
        IdentificacionEntidad identificacion = new IdentificacionEntidad();
        PersonaEntidad entidad = new PersonaEntidad();
        entidad.setCorreo("test@test.com");
        UsuarioEntidad usuario = new UsuarioEntidad();
        usuario.setEstaActivo(true);
        Persona modelo = mock(Persona.class);

        when(identificacionDAO.findFirstByNumeroIdentificacion("123")).thenReturn(identificacion);
        when(personaDAO.findByIdentificacion(identificacion)).thenReturn(entidad);
        when(usuarioDAO.findByCorreo("test@test.com")).thenReturn(usuario);
        when(personaMapeador.construirModelo(entidad)).thenReturn(modelo);

        Persona resultado = repositorio.consultarPersonaPorDocumento("123");

        assertNotNull(resultado);
    }

    @Test
    void deberiaRetornarNullCuandoDocumentoNoExiste() {
        IdentificacionEntidad identificacion = new IdentificacionEntidad();
        when(identificacionDAO.findFirstByNumeroIdentificacion("999")).thenReturn(identificacion);
        when(personaDAO.findByIdentificacion(identificacion)).thenReturn(null);

        Persona resultado = repositorio.consultarPersonaPorDocumento("999");

        assertNull(resultado);
    }

    @Test
    void deberiaConsultarUsuarioPorIdentificadorDTO() {
        UUID id = UUID.randomUUID();
        PersonaEntidad entidad = new PersonaEntidad();
        entidad.setCorreo("test@test.com");
        UsuarioEntidad usuario = new UsuarioEntidad();
        usuario.setEstaActivo(true);
        UsuarioDTO dto = new UsuarioDTO();

        when(personaDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(usuarioDAO.findByCorreo("test@test.com")).thenReturn(usuario);
        when(usuarioMapeador.construirDTO(entidad)).thenReturn(dto);

        UsuarioDTO resultado = repositorio.consultarUsuarioPorIdentificadorDTO(id);

        assertNotNull(resultado);
    }

    @Test
    void deberiaConsultarUsuarioPorIdentificador() {
        UUID id = UUID.randomUUID();
        UsuarioEntidad entidad = new UsuarioEntidad();
        entidad.setEstaActivo(true);
        Usuario modelo = mock(Usuario.class);

        when(usuarioDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(usuarioMapeador.construirModelo(entidad)).thenReturn(modelo);

        Usuario resultado = repositorio.consultarUsuarioPorIdentificador(id);

        assertNotNull(resultado);
    }

    @Test
    void deberiaRetornarNullCuandoUsuarioPorIdNoExiste() {
        UUID id = UUID.randomUUID();
        when(usuarioDAO.findById(id)).thenReturn(Optional.empty());

        Usuario resultado = repositorio.consultarUsuarioPorIdentificador(id);

        assertNull(resultado);
    }

    @Test
    void deberiaConsultarUsuarioPorCorreoDTO() {
        PersonaEntidad personaEntidad = new PersonaEntidad();
        UsuarioEntidad usuario = new UsuarioEntidad();
        usuario.setEstaActivo(true);
        UsuarioDTO dto = new UsuarioDTO();

        when(usuarioDAO.findByCorreo("test@test.com")).thenReturn(usuario);
        when(personaDAO.findByCorreo("test@test.com")).thenReturn(personaEntidad);
        when(usuarioMapeador.construirDTO(personaEntidad)).thenReturn(dto);

        UsuarioDTO resultado = repositorio.consultarUsuarioPorCorreoDTO("test@test.com");

        assertNotNull(resultado);
    }

    @Test
    void deberiaConsultarUsuariosPorCorreo() {
        UsuarioEntidad usuario = new UsuarioEntidad();
        usuario.setEstaActivo(true);
        Usuario modelo = mock(Usuario.class);

        when(usuarioDAO.findByCorreo("test@test.com")).thenReturn(usuario);
        when(usuarioMapeador.construirModelo(usuario)).thenReturn(modelo);

        Usuario resultado = repositorio.consultarUsuarioPorCorreo("test@test.com");

        assertNotNull(resultado);
    }

    @Test
    void deberiaConsultarUsuariosDTO() {
        List<PersonaEntidad> entidades = List.of(new PersonaEntidad());
        UsuarioDTO dto = new UsuarioDTO();
        dto.setEstaActivo(true);

        when(personaDAO.findAll()).thenReturn(entidades);
        when(usuarioMapeador.construirDTOs(entidades)).thenReturn(List.of(dto));

        List<UsuarioDTO> resultado = repositorio.consultarUsuariosDTO();

        assertEquals(1, resultado.size());
    }

    @Test
    void deberiaConsultarUsuariosDTOPaginado() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "nombres"));
        Page<PersonaEntidad> paginaEntidades = new PageImpl<>(List.of(new PersonaEntidad()), pageRequest, 1);
        Page<UsuarioDTO> paginaDTOs = new PageImpl<>(List.of(new UsuarioDTO()), pageRequest, 1);
        when(personaDAO.findByTipoUsuario("Administrador de dirección", pageRequest)).thenReturn(paginaEntidades);
        when(usuarioMapeador.construirDTOsPaginado(paginaEntidades)).thenReturn(paginaDTOs);

        Page<UsuarioDTO> resultado = repositorio.consultarUsuariosDTOPaginado("Administrador de dirección", 0, 10, false);

        assertEquals(1, resultado.getContent().size());
        assertEquals(1, resultado.getTotalElements());
    }

    @Test
    void deberiaConsultarUsuariosDTOPaginadoExcluyendoTipo() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "nombres"));
        Page<PersonaEntidad> paginaEntidades = new PageImpl<>(List.of(new PersonaEntidad(), new PersonaEntidad()), pageRequest, 2);
        Page<UsuarioDTO> paginaDTOs = new PageImpl<>(List.of(new UsuarioDTO(), new UsuarioDTO()), pageRequest, 2);
        when(personaDAO.findByTipoUsuarioNot("Administrador de dirección", pageRequest)).thenReturn(paginaEntidades);
        when(usuarioMapeador.construirDTOsPaginado(paginaEntidades)).thenReturn(paginaDTOs);

        Page<UsuarioDTO> resultado = repositorio.consultarUsuariosDTOPaginado("Administrador de dirección", 0, 10, true);

        assertEquals(2, resultado.getContent().size());
        assertEquals(2, resultado.getTotalElements());
    }

    @Test
    void deberiaVerificarHayDatos() {
        when(usuarioDAO.count()).thenReturn(5L);

        assertTrue(repositorio.hayDatos());
    }

    @Test
    void deberiaVerificarNoHayDatos() {
        when(usuarioDAO.count()).thenReturn(0L);

        assertFalse(repositorio.hayDatos());
    }

    @Test
    void deberiaConsultarCodigoConCorreo() {
        PeticionRecuperacionClaveEntidad entidad = new PeticionRecuperacionClaveEntidad();
        entidad.setCodigo("ABC123");
        when(peticionRecuperacionClaveDAO.findByCorreo("test@test.com")).thenReturn(entidad);

        String resultado = repositorio.consultarCodigoConCorreo("test@test.com");

        assertEquals("ABC123", resultado);
    }

    @Test
    void deberiaRetornarNullCuandoCodigoNoExiste() {
        when(peticionRecuperacionClaveDAO.findByCorreo("no@existe.com")).thenReturn(null);

        String resultado = repositorio.consultarCodigoConCorreo("no@existe.com");

        assertNull(resultado);
    }

    @Test
    void deberiaConsultarFechaPeticionRecuperacionClave() {
        LocalDateTime fecha = LocalDateTime.now();
        PeticionRecuperacionClaveEntidad entidad = new PeticionRecuperacionClaveEntidad();
        entidad.setFecha(fecha);
        when(peticionRecuperacionClaveDAO.findByCorreo("test@test.com")).thenReturn(entidad);

        LocalDateTime resultado = repositorio.consultarFechaPeticionRecuperacionClaveConCorreo("test@test.com");

        assertEquals(fecha, resultado);
    }

    @Test
    void deberiaConsultarClaveConCorreo() {
        UsuarioEntidad usuario = new UsuarioEntidad();
        usuario.setClave("hashed");
        when(usuarioDAO.findByCorreo("test@test.com")).thenReturn(usuario);

        String resultado = repositorio.consultarClaveConCorreo("test@test.com");

        assertEquals("hashed", resultado);
    }

    @Test
    void deberiaRetornarNullClaveConCorreoInexistente() {
        when(usuarioDAO.findByCorreo("no@existe.com")).thenReturn(null);

        String resultado = repositorio.consultarClaveConCorreo("no@existe.com");

        assertNull(resultado);
    }

    @Test
    void deberiaRetornarNullParaCorreoDTOInactivo() {
        PersonaEntidad entidad = new PersonaEntidad();
        UsuarioEntidad usuario = new UsuarioEntidad();
        usuario.setEstaActivo(false);
        when(personaDAO.findByCorreo("inactivo@test.com")).thenReturn(entidad);
        when(usuarioDAO.findByCorreo("inactivo@test.com")).thenReturn(usuario);
        assertNull(repositorio.consultarPersonaPorCorreoDTO("inactivo@test.com"));
    }

    @Test
    void deberiaRetornarNullCuandoPersonaPorCorreoNoExiste() {
        when(personaDAO.findByCorreo("no@existe.com")).thenReturn(null);
        assertNull(repositorio.consultarPersonaPorCorreo("no@existe.com"));
    }

    @Test
    void deberiaRetornarNullCuandoPersonaPorCorreoInactiva() {
        PersonaEntidad entidad = new PersonaEntidad();
        UsuarioEntidad usuario = new UsuarioEntidad();
        usuario.setEstaActivo(false);
        when(personaDAO.findByCorreo("inactivo@test.com")).thenReturn(entidad);
        when(usuarioDAO.findByCorreo("inactivo@test.com")).thenReturn(usuario);
        assertNull(repositorio.consultarPersonaPorCorreo("inactivo@test.com"));
    }

    @Test
    void deberiaRetornarNullCuandoDocumentoUsuarioInactivo() {
        IdentificacionEntidad identificacion = new IdentificacionEntidad();
        PersonaEntidad entidad = new PersonaEntidad();
        entidad.setCorreo("inactivo@test.com");
        UsuarioEntidad usuario = new UsuarioEntidad();
        usuario.setEstaActivo(false);
        when(identificacionDAO.findFirstByNumeroIdentificacion("888")).thenReturn(identificacion);
        when(personaDAO.findByIdentificacion(identificacion)).thenReturn(entidad);
        when(usuarioDAO.findByCorreo("inactivo@test.com")).thenReturn(usuario);
        assertNull(repositorio.consultarPersonaPorDocumento("888"));
    }

    @Test
    void deberiaRetornarNullCuandoUsuarioPorIdentificadorDTONoExiste() {
        UUID id = UUID.randomUUID();
        when(personaDAO.findById(id)).thenReturn(Optional.empty());
        assertNull(repositorio.consultarUsuarioPorIdentificadorDTO(id));
    }

    @Test
    void deberiaRetornarNullCuandoUsuarioPorIdentificadorDTOInactivo() {
        UUID id = UUID.randomUUID();
        PersonaEntidad entidad = new PersonaEntidad();
        entidad.setCorreo("inactivo@test.com");
        UsuarioEntidad usuario = new UsuarioEntidad();
        usuario.setEstaActivo(false);
        when(personaDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(usuarioDAO.findByCorreo("inactivo@test.com")).thenReturn(usuario);
        assertNull(repositorio.consultarUsuarioPorIdentificadorDTO(id));
    }

    @Test
    void deberiaRetornarNullCuandoUsuarioCorreoDTONoExiste() {
        when(usuarioDAO.findByCorreo("no@existe.com")).thenReturn(null);
        assertNull(repositorio.consultarUsuarioPorCorreoDTO("no@existe.com"));
    }

    @Test
    void deberiaRetornarNullCuandoUsuarioPorCorreoNoExiste() {
        when(usuarioDAO.findByCorreo("no@existe.com")).thenReturn(null);
        assertNull(repositorio.consultarUsuarioPorCorreo("no@existe.com"));
    }

    @Test
    void deberiaRetornarNullFechaPeticionRecuperacionClaveInexistente() {
        when(peticionRecuperacionClaveDAO.findByCorreo("no@existe.com")).thenReturn(null);
        assertNull(repositorio.consultarFechaPeticionRecuperacionClaveConCorreo("no@existe.com"));
    }

    @Test
    void deberiaConsultarUsuarioInactivoPorCorreo() {
        UsuarioEntidad usuario = new UsuarioEntidad();
        usuario.setEstaActivo(false);
        Usuario modelo = mock(Usuario.class);

        when(usuarioDAO.findByCorreo("inactivo@test.com")).thenReturn(usuario);
        when(usuarioMapeador.construirModelo(usuario)).thenReturn(modelo);

        Usuario resultado = repositorio.consultarUsuarioInactivoPorCorreo("inactivo@test.com");

        assertNotNull(resultado);
    }

    @Test
    void deberiaRetornarNullCuandoUsuarioInactivoPorCorreoNoExiste() {
        when(usuarioDAO.findByCorreo("no@existe.com")).thenReturn(null);

        assertNull(repositorio.consultarUsuarioInactivoPorCorreo("no@existe.com"));
    }

    @Test
    void deberiaRetornarNullCuandoUsuarioInactivoPorCorreoEstaActivo() {
        UsuarioEntidad usuario = new UsuarioEntidad();
        usuario.setEstaActivo(true);

        when(usuarioDAO.findByCorreo("activo@test.com")).thenReturn(usuario);

        assertNull(repositorio.consultarUsuarioInactivoPorCorreo("activo@test.com"));
    }
}
