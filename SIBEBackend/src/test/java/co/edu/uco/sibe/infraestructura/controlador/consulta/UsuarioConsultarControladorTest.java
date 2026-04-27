package co.edu.uco.sibe.infraestructura.controlador.consulta;

import co.edu.uco.sibe.aplicacion.consulta.ConsultarUsuarioPorCorreoManejador;
import co.edu.uco.sibe.aplicacion.consulta.ConsultarUsuarioPorIdentificadorManejador;
import co.edu.uco.sibe.aplicacion.consulta.ConsultarUsuariosManejador;
import co.edu.uco.sibe.dominio.dto.UsuarioDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioConsultarControladorTest {

    @Mock private ConsultarUsuarioPorCorreoManejador consultarUsuarioPorCorreoManejador;
    @Mock private ConsultarUsuarioPorIdentificadorManejador consultarUsuarioPorIdentificadorManejador;
    @Mock private ConsultarUsuariosManejador consultarUsuariosManejador;

    private UsuarioConsultarControlador controlador;

    @BeforeEach
    void setUp() {
        controlador = new UsuarioConsultarControlador(
                consultarUsuarioPorCorreoManejador,
                consultarUsuarioPorIdentificadorManejador,
                consultarUsuariosManejador
        );
    }

    @Test
    void deberiaConsultarTodos() {
        List<UsuarioDTO> usuarios = List.of(new UsuarioDTO());
        when(consultarUsuariosManejador.ejecutar()).thenReturn(usuarios);

        List<UsuarioDTO> resultado = controlador.consultarTodos();

        assertEquals(1, resultado.size());
        verify(consultarUsuariosManejador).ejecutar();
    }

    @Test
    void deberiaConsultarPorIdentificador() {
        UUID identificador = UUID.randomUUID();
        UsuarioDTO usuario = new UsuarioDTO();
        when(consultarUsuarioPorIdentificadorManejador.ejecutar(identificador)).thenReturn(usuario);

        UsuarioDTO resultado = controlador.consultarPorIdentificador(identificador);

        assertNotNull(resultado);
        verify(consultarUsuarioPorIdentificadorManejador).ejecutar(identificador);
    }

    @Test
    void deberiaConsultarPorCorreo() {
        String correo = "test@test.com";
        UsuarioDTO usuario = new UsuarioDTO();
        when(consultarUsuarioPorCorreoManejador.ejecutar(correo)).thenReturn(usuario);

        UsuarioDTO resultado = controlador.consultarPorCorreo(correo);

        assertNotNull(resultado);
        verify(consultarUsuarioPorCorreoManejador).ejecutar(correo);
    }

    @Test
    void deberiaConsultarTodosPaginado() {
        Page<UsuarioDTO> pagina = new PageImpl<>(List.of(new UsuarioDTO()));
        when(consultarUsuariosManejador.ejecutar("Administrador de dirección", 0, 10, false)).thenReturn(pagina);

        Page<UsuarioDTO> resultado = controlador.consultarTodosPaginado("Administrador de dirección", 0, 10, false);

        assertEquals(1, resultado.getContent().size());
        assertEquals(1, resultado.getTotalElements());
        verify(consultarUsuariosManejador).ejecutar("Administrador de dirección", 0, 10, false);
    }

    @Test
    void deberiaConsultarTodosPaginadoExcluyendoTipo() {
        Page<UsuarioDTO> pagina = new PageImpl<>(List.of(new UsuarioDTO(), new UsuarioDTO()));
        when(consultarUsuariosManejador.ejecutar("Administrador de dirección", 0, 10, true)).thenReturn(pagina);

        Page<UsuarioDTO> resultado = controlador.consultarTodosPaginado("Administrador de dirección", 0, 10, true);

        assertEquals(2, resultado.getContent().size());
        verify(consultarUsuariosManejador).ejecutar("Administrador de dirección", 0, 10, true);
    }
}
