package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.dto.UsuarioDTO;
import co.edu.uco.sibe.dominio.modelo.Usuario;
import co.edu.uco.sibe.dominio.puerto.consulta.PersonaRepositorioConsulta;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarUsuarioPorCorreoUseCaseTest {

    @Mock
    private PersonaRepositorioConsulta personaRepositorioConsulta;

    private ConsultarUsuarioPorCorreoUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ConsultarUsuarioPorCorreoUseCase(personaRepositorioConsulta);
    }

    @Test
    void deberiaRetornarUsuarioPorCorreoExitosamente() {
        String correo = "usuario@ejemplo.com";
        Usuario usuario = mock(Usuario.class);
        UsuarioDTO dto = mock(UsuarioDTO.class);
        when(personaRepositorioConsulta.consultarUsuarioPorCorreo(correo)).thenReturn(usuario);
        when(personaRepositorioConsulta.consultarUsuarioPorCorreoDTO(correo)).thenReturn(dto);

        UsuarioDTO resultado = useCase.ejecutar(correo);

        assertEquals(dto, resultado);
    }

    @Test
    void deberiaLanzarExcepcionCuandoUsuarioNoExistePorCorreo() {
        String correo = "noexiste@ejemplo.com";
        when(personaRepositorioConsulta.consultarUsuarioPorCorreo(correo)).thenReturn(null);

        assertThrows(ValorInvalidoExcepcion.class, () -> useCase.ejecutar(correo));
    }
}
