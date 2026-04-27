package co.edu.uco.sibe.infraestructura.adaptador.repositorio.comando;

import co.edu.uco.sibe.dominio.modelo.Persona;
import co.edu.uco.sibe.dominio.modelo.Usuario;
import co.edu.uco.sibe.infraestructura.adaptador.dao.PersonaDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.PeticionRecuperacionClaveDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.UsuarioDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.PersonaEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.PeticionRecuperacionClaveEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.UsuarioEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.PersonaMapeador;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.PeticionRecuperacionClaveMapeador;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.UsuarioMapeador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonaRepositorioComandoImplementacionTest {

    @Mock private PersonaDAO personaDAO;
    @Mock private PersonaMapeador personaMapeador;
    @Mock private UsuarioDAO usuarioDAO;
    @Mock private UsuarioMapeador usuarioMapeador;
    @Mock private PeticionRecuperacionClaveDAO peticionRecuperacionClaveDAO;
    @Mock private PeticionRecuperacionClaveMapeador peticionRecuperacionClaveMapeador;

    private PersonaRepositorioComandoImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new PersonaRepositorioComandoImplementacion(
                personaDAO, personaMapeador, usuarioDAO, usuarioMapeador,
                peticionRecuperacionClaveDAO, peticionRecuperacionClaveMapeador
        );
    }

    @Test
    void deberiaAgregarNuevoUsuario() {
        UUID resultadoId = UUID.randomUUID();
        Usuario usuario = mock(Usuario.class);
        Persona persona = mock(Persona.class);
        UsuarioEntidad usuarioEntidad = new UsuarioEntidad();
        usuarioEntidad.setIdentificador(resultadoId);
        PersonaEntidad personaEntidad = new PersonaEntidad();

        when(usuarioMapeador.construirEntidad(usuario, "claveHash")).thenReturn(usuarioEntidad);
        when(personaMapeador.construirEntidad(persona)).thenReturn(personaEntidad);
        when(usuarioDAO.save(usuarioEntidad)).thenReturn(usuarioEntidad);

        UUID resultado = repositorio.agregarNuevoUsuario(usuario, persona, "claveHash");

        assertEquals(resultadoId, resultado);
        verify(personaDAO).save(personaEntidad);
        verify(usuarioDAO).save(usuarioEntidad);
    }

    @Test
    void deberiaModificarUsuario() {
        UUID personaId = UUID.randomUUID();
        UUID usuarioId = UUID.randomUUID();
        UUID resultadoId = UUID.randomUUID();

        Persona persona = mock(Persona.class);
        when(persona.getIdentificador()).thenReturn(personaId);
        Usuario usuario = mock(Usuario.class);
        when(usuario.getIdentificador()).thenReturn(usuarioId);

        PersonaEntidad personaEntidad = new PersonaEntidad();
        UsuarioEntidad usuarioEntidad = new UsuarioEntidad();
        usuarioEntidad.setIdentificador(resultadoId);

        when(personaDAO.findById(personaId)).thenReturn(Optional.of(personaEntidad));
        when(usuarioDAO.findById(usuarioId)).thenReturn(Optional.of(usuarioEntidad));
        when(usuarioDAO.save(usuarioEntidad)).thenReturn(usuarioEntidad);

        UUID resultado = repositorio.modificarUsuario(usuario, persona);

        assertEquals(resultadoId, resultado);
        verify(personaMapeador).modificarEntidad(personaEntidad, persona);
        verify(usuarioMapeador).modificarEntidad(usuarioEntidad, usuario);
    }

    @Test
    void deberiaModificarClave() {
        UUID personaId = UUID.randomUUID();
        UUID resultadoId = UUID.randomUUID();
        PersonaEntidad personaEntidad = new PersonaEntidad();
        personaEntidad.setCorreo("test@test.com");
        UsuarioEntidad usuarioEntidad = new UsuarioEntidad();
        usuarioEntidad.setIdentificador(resultadoId);

        when(personaDAO.findById(personaId)).thenReturn(Optional.of(personaEntidad));
        when(usuarioDAO.findByCorreo("test@test.com")).thenReturn(usuarioEntidad);
        when(usuarioDAO.save(usuarioEntidad)).thenReturn(usuarioEntidad);

        UUID resultado = repositorio.modificarClave("nuevaClave", personaId);

        assertEquals(resultadoId, resultado);
        verify(usuarioMapeador).construirModificarContrasenaEntidad(usuarioEntidad, "nuevaClave");
    }

    @Test
    void deberiaEliminarUsuario() {
        UUID personaId = UUID.randomUUID();
        PersonaEntidad personaEntidad = new PersonaEntidad();
        personaEntidad.setCorreo("test@test.com");
        UsuarioEntidad usuarioEntidad = new UsuarioEntidad();
        usuarioEntidad.setEstaActivo(true);

        when(personaDAO.findById(personaId)).thenReturn(Optional.of(personaEntidad));
        when(usuarioDAO.findByCorreo("test@test.com")).thenReturn(usuarioEntidad);

        repositorio.eliminarUsuario(personaId);

        assertFalse(usuarioEntidad.isEstaActivo());
        verify(usuarioDAO).save(usuarioEntidad);
    }

    @Test
    void deberiaCrearPeticionRecuperacionClaveNueva() {
        UUID resultadoId = UUID.randomUUID();
        LocalDateTime fecha = LocalDateTime.now();
        PeticionRecuperacionClaveEntidad entidad = new PeticionRecuperacionClaveEntidad();
        entidad.setIdentificador(resultadoId);

        when(peticionRecuperacionClaveDAO.findByCorreo("test@test.com")).thenReturn(null);
        when(peticionRecuperacionClaveMapeador.construirEntidad("codigo", "test@test.com", fecha)).thenReturn(entidad);
        when(peticionRecuperacionClaveDAO.save(entidad)).thenReturn(entidad);

        UUID resultado = repositorio.crearPeticionRecuperacionClave("codigo", "test@test.com", fecha);

        assertEquals(resultadoId, resultado);
    }

    @Test
    void deberiaActualizarPeticionRecuperacionClaveExistente() {
        UUID resultadoId = UUID.randomUUID();
        LocalDateTime fecha = LocalDateTime.now();
        PeticionRecuperacionClaveEntidad entidad = new PeticionRecuperacionClaveEntidad();
        entidad.setIdentificador(resultadoId);

        when(peticionRecuperacionClaveDAO.findByCorreo("test@test.com")).thenReturn(entidad);
        when(peticionRecuperacionClaveDAO.save(entidad)).thenReturn(entidad);

        UUID resultado = repositorio.crearPeticionRecuperacionClave("codigoNuevo", "test@test.com", fecha);

        assertEquals(resultadoId, resultado);
        verify(peticionRecuperacionClaveMapeador).actualizarEntidad(entidad, "codigoNuevo", fecha);
    }

    @Test
    void deberiaEliminarPeticionRecuperacionClaveConCorreo() {
        UUID entidadId = UUID.randomUUID();
        PeticionRecuperacionClaveEntidad entidad = new PeticionRecuperacionClaveEntidad();
        entidad.setIdentificador(entidadId);

        when(peticionRecuperacionClaveDAO.findByCorreo("test@test.com")).thenReturn(entidad);

        repositorio.eliminarPeticionRecuperacionClaveConCorreo("test@test.com");

        verify(peticionRecuperacionClaveDAO).deleteById(entidadId);
    }

    @Test
    void deberiaModificarClaveConCorreo() {
        UUID resultadoId = UUID.randomUUID();
        UsuarioEntidad usuarioEntidad = new UsuarioEntidad();
        usuarioEntidad.setIdentificador(resultadoId);

        when(usuarioDAO.findByCorreo("test@test.com")).thenReturn(usuarioEntidad);
        when(usuarioDAO.save(usuarioEntidad)).thenReturn(usuarioEntidad);

        UUID resultado = repositorio.modificarClaveConCorreo("nuevaClave", "test@test.com");

        assertEquals(resultadoId, resultado);
        verify(usuarioMapeador).construirModificarContrasenaEntidad(usuarioEntidad, "nuevaClave");
    }

    @Test
    void deberiaReactivarUsuario() {
        UUID personaId = UUID.randomUUID();
        Usuario usuario = mock(Usuario.class);
        Persona persona = mock(Persona.class);
        UsuarioEntidad usuarioEntidad = new UsuarioEntidad();
        usuarioEntidad.setEstaActivo(false);
        PersonaEntidad personaEntidad = new PersonaEntidad();
        personaEntidad.setIdentificador(personaId);

        when(usuarioDAO.findByCorreo("reactivar@test.com")).thenReturn(usuarioEntidad);
        when(personaDAO.findByCorreo("reactivar@test.com")).thenReturn(personaEntidad);

        UUID resultado = repositorio.reactivarUsuario("reactivar@test.com", usuario, persona, "claveHash");

        assertEquals(personaId, resultado);
        assertTrue(usuarioEntidad.isEstaActivo());
        assertEquals("claveHash", usuarioEntidad.getClave());
        verify(personaMapeador).modificarEntidad(personaEntidad, persona);
        verify(usuarioMapeador).modificarEntidad(usuarioEntidad, usuario);
        verify(personaDAO).save(personaEntidad);
        verify(usuarioDAO).save(usuarioEntidad);
    }
}
