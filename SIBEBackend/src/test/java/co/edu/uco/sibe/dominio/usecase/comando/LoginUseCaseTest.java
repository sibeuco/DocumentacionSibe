package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.Usuario;
import co.edu.uco.sibe.dominio.puerto.consulta.PersonaRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.servicio.EncriptarClaveServicio;
import co.edu.uco.sibe.dominio.transversal.excepcion.AuthorizationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.USUARIO_O_CLAVE_INCORRECTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseTest {

    @Mock
    private PersonaRepositorioConsulta personaRepositorioConsulta;
    @Mock
    private EncriptarClaveServicio encriptarClaveServicio;

    private LoginUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new LoginUseCase(personaRepositorioConsulta, encriptarClaveServicio);
    }

    @Test
    void deberiaRetornarIdentificadorCuandoUsuarioExiste() {
        UUID identificador = UUID.randomUUID();
        String correo = "test@test.com";
        Usuario usuario = mock(Usuario.class);
        when(usuario.getIdentificador()).thenReturn(identificador);

        when(personaRepositorioConsulta.consultarUsuarioPorCorreo(correo)).thenReturn(usuario);

        UUID resultado = useCase.ejecutar(correo);

        assertEquals(identificador, resultado);
    }

    @Test
    void deberiaLanzarAuthorizationExceptionCuandoUsuarioNoExiste() {
        String correo = "noexiste@test.com";

        when(personaRepositorioConsulta.consultarUsuarioPorCorreo(correo)).thenReturn(null);

        AuthorizationException excepcion = assertThrows(AuthorizationException.class,
                () -> useCase.ejecutar(correo));

        assertEquals(USUARIO_O_CLAVE_INCORRECTO, excepcion.getMessage());
    }
}
