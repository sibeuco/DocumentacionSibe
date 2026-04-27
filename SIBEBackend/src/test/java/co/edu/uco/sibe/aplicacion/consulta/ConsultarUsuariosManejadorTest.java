package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.UsuarioDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarUsuariosUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarUsuariosManejadorTest {

    @Mock private ConsultarUsuariosUseCase consultarUsuariosUseCase;

    private ConsultarUsuariosManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarUsuariosManejador(consultarUsuariosUseCase);
    }

    @Test
    void deberiaConsultarUsuarios() {
        List<UsuarioDTO> esperado = List.of(mock(UsuarioDTO.class));
        when(consultarUsuariosUseCase.ejecutar()).thenReturn(esperado);

        List<UsuarioDTO> resultado = manejador.ejecutar();

        assertEquals(esperado, resultado);
        verify(consultarUsuariosUseCase).ejecutar();
    }

    @Test
    void deberiaConsultarUsuariosPaginado() {
        Page<UsuarioDTO> esperado = new PageImpl<>(List.of(mock(UsuarioDTO.class)));
        when(consultarUsuariosUseCase.ejecutar("Administrador de dirección", 0, 10, false)).thenReturn(esperado);

        Page<UsuarioDTO> resultado = manejador.ejecutar("Administrador de dirección", 0, 10, false);

        assertEquals(esperado, resultado);
        verify(consultarUsuariosUseCase).ejecutar("Administrador de dirección", 0, 10, false);
    }

    @Test
    void deberiaConsultarUsuariosPaginadoExcluyendoTipo() {
        Page<UsuarioDTO> esperado = new PageImpl<>(List.of(mock(UsuarioDTO.class)));
        when(consultarUsuariosUseCase.ejecutar("Administrador de dirección", 0, 10, true)).thenReturn(esperado);

        Page<UsuarioDTO> resultado = manejador.ejecutar("Administrador de dirección", 0, 10, true);

        assertEquals(esperado, resultado);
        verify(consultarUsuariosUseCase).ejecutar("Administrador de dirección", 0, 10, true);
    }

}
