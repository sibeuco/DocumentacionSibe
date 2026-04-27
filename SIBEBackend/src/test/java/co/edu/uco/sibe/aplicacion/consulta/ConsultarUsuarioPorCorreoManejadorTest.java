package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.UsuarioDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarUsuarioPorCorreoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarUsuarioPorCorreoManejadorTest {

    @Mock private ConsultarUsuarioPorCorreoUseCase consultarUsuarioPorCorreoUseCase;

    private ConsultarUsuarioPorCorreoManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarUsuarioPorCorreoManejador(consultarUsuarioPorCorreoUseCase);
    }

    @Test
    void deberiaConsultarUsuarioPorCorreo() {
        UsuarioDTO esperado = mock(UsuarioDTO.class);
        when(consultarUsuarioPorCorreoUseCase.ejecutar("test@correo.com")).thenReturn(esperado);

        UsuarioDTO resultado = manejador.ejecutar("test@correo.com");

        assertEquals(esperado, resultado);
        verify(consultarUsuarioPorCorreoUseCase).ejecutar("test@correo.com");
    }
}
