package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.TipoUsuario;
import co.edu.uco.sibe.dominio.modelo.Usuario;
import co.edu.uco.sibe.dominio.puerto.comando.PersonaRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.PersonaRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.servicio.EncriptarClaveServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecuperarClaveUseCaseTest {

    @Mock
    private PersonaRepositorioComando personaRepositorioComando;
    @Mock
    private PersonaRepositorioConsulta personaRepositorioConsulta;
    @Mock
    private EncriptarClaveServicio encriptarClaveServicio;

    private RecuperarClaveUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new RecuperarClaveUseCase(personaRepositorioComando, personaRepositorioConsulta,
                encriptarClaveServicio);
    }

    @Test
    void deberiaRecuperarClaveExitosamente() {
        String correo = "test@correo.com";
        String nuevaClave = "NuevaClave123!";
        UUID identificador = UUID.randomUUID();
        TipoUsuario tipoUsuario = TipoUsuario.construir(UUID.randomUUID(), "ADM", "Admin", true, true, true, true);
        Usuario usuario = Usuario.construir(UUID.randomUUID(), correo, "OldClave123!", tipoUsuario, true);

        when(personaRepositorioConsulta.consultarUsuarioPorCorreo(correo)).thenReturn(usuario);
        when(encriptarClaveServicio.ejecutar(nuevaClave)).thenReturn("claveCifrada");
        when(personaRepositorioComando.modificarClaveConCorreo("claveCifrada", correo)).thenReturn(identificador);

        UUID resultado = useCase.ejecutar(correo, nuevaClave);

        assertEquals(identificador, resultado);
        verify(encriptarClaveServicio).ejecutar(nuevaClave);
    }

    @Test
    void deberiaLanzarExcepcionCuandoUsuarioNoExisteConCorreo() {
        String correo = "noexiste@test.com";

        when(personaRepositorioConsulta.consultarUsuarioPorCorreo(correo)).thenReturn(null);

        assertThrows(NullPointerException.class,
                () -> useCase.ejecutar(correo, "clave"));

        verify(personaRepositorioComando, never()).modificarClaveConCorreo(any(), any());
    }
}
