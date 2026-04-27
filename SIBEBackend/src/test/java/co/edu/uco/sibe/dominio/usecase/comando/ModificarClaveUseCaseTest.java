package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.Persona;
import co.edu.uco.sibe.dominio.modelo.Usuario;
import co.edu.uco.sibe.dominio.puerto.comando.PersonaRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.PersonaRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.servicio.EncriptarClaveServicio;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import co.edu.uco.sibe.dominio.transversal.excepcion.AuthorizationException;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModificarClaveUseCaseTest {

    @Mock
    private PersonaRepositorioComando personaRepositorioComando;

    @Mock
    private PersonaRepositorioConsulta personaRepositorioConsulta;

    @Mock
    private EncriptarClaveServicio encriptarClaveServicio;

    @Mock
    private AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    private ModificarClaveUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ModificarClaveUseCase(personaRepositorioComando, personaRepositorioConsulta,
                encriptarClaveServicio, autorizacionServicio);
    }

    @Test
    void deberiaLanzarAuthorizationExceptionCuandoNoTieneAccesoAlUsuario() {
        UUID userId = UUID.randomUUID();

        doThrow(new AuthorizationException("No tiene acceso")).when(autorizacionServicio).validarAccesoAUsuario(userId);

        assertThrows(AuthorizationException.class, () -> useCase.ejecutar("oldPass", "newPass", userId));

        verify(autorizacionServicio).validarAccesoAUsuario(userId);
        verify(personaRepositorioConsulta, never()).consultarPersonaPorIdentificador(any());
    }

    @Test
    void deberiaValidarAccesoAntesDeModificarClave() {
        UUID userId = UUID.randomUUID();

        doThrow(new AuthorizationException("Sin acceso")).when(autorizacionServicio).validarAccesoAUsuario(userId);

        assertThrows(AuthorizationException.class, () -> useCase.ejecutar("old", "new", userId));

        verify(personaRepositorioComando, never()).modificarClave(any(), any());
    }

    @Test
    void deberiaLanzarNullPointerCuandoUsuarioNoExiste() {
        UUID userId = UUID.randomUUID();

        when(personaRepositorioConsulta.consultarPersonaPorIdentificador(userId)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> useCase.ejecutar("old", "new", userId));
    }

    @Test
    void deberiaLanzarValorInvalidoCuandoClaveNuevaEsIgualAAntigua() {
        UUID userId = UUID.randomUUID();
        Persona persona = mock(Persona.class);

        when(personaRepositorioConsulta.consultarPersonaPorIdentificador(userId)).thenReturn(persona);

        assertThrows(ValorInvalidoExcepcion.class, () -> useCase.ejecutar("mismaClave", "mismaClave", userId));
    }

    @Test
    void deberiaLanzarValorInvalidoCuandoClaveAntiguaEsIncorrecta() {
        UUID userId = UUID.randomUUID();
        Persona persona = mock(Persona.class);
        Usuario usuario = mock(Usuario.class);

        when(personaRepositorioConsulta.consultarPersonaPorIdentificador(userId)).thenReturn(persona);
        when(persona.getCorreo()).thenReturn("test@test.com");
        when(personaRepositorioConsulta.consultarUsuarioPorCorreo("test@test.com")).thenReturn(usuario);
        when(usuario.getCorreo()).thenReturn("test@test.com");
        when(personaRepositorioConsulta.consultarClaveConCorreo("test@test.com")).thenReturn("hashedOld");
        when(encriptarClaveServicio.existe("OldIncorrecta1", "hashedOld")).thenReturn(false);

        assertThrows(ValorInvalidoExcepcion.class, () -> useCase.ejecutar("OldIncorrecta1", "NewPass123!", userId));
    }

    @Test
    void deberiaModificarClaveExitosamente() {
        UUID userId = UUID.randomUUID();
        UUID idEsperado = UUID.randomUUID();
        Persona persona = mock(Persona.class);
        Usuario usuario = mock(Usuario.class);

        when(personaRepositorioConsulta.consultarPersonaPorIdentificador(userId)).thenReturn(persona);
        when(persona.getCorreo()).thenReturn("test@test.com");
        when(personaRepositorioConsulta.consultarUsuarioPorCorreo("test@test.com")).thenReturn(usuario);
        when(usuario.getCorreo()).thenReturn("test@test.com");
        when(usuario.getIdentificador()).thenReturn(userId);
        when(personaRepositorioConsulta.consultarClaveConCorreo("test@test.com")).thenReturn("hashedOld");
        when(encriptarClaveServicio.existe("OldPass123!", "hashedOld")).thenReturn(true);
        when(usuario.getClave()).thenReturn("NewPass123!");
        when(encriptarClaveServicio.ejecutar("NewPass123!")).thenReturn("hashedNew");
        when(personaRepositorioComando.modificarClave("hashedNew", userId)).thenReturn(idEsperado);

        UUID resultado = useCase.ejecutar("OldPass123!", "NewPass123!", userId);

        assertEquals(idEsperado, resultado);
        verify(usuario).actualizarClave("NewPass123!");
    }
}
