package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.UsuarioDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarUsuarioPorIdentificadorUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarUsuarioPorIdentificadorManejadorTest {

    @Mock private ConsultarUsuarioPorIdentificadorUseCase consultarUsuarioPorIdentificadorUseCase;

    private ConsultarUsuarioPorIdentificadorManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarUsuarioPorIdentificadorManejador(consultarUsuarioPorIdentificadorUseCase);
    }

    @Test
    void deberiaConsultarUsuarioPorIdentificador() {
        UUID id = UUID.randomUUID();
        UsuarioDTO esperado = mock(UsuarioDTO.class);
        when(consultarUsuarioPorIdentificadorUseCase.ejecutar(id)).thenReturn(esperado);

        UsuarioDTO resultado = manejador.ejecutar(id);

        assertEquals(esperado, resultado);
        verify(consultarUsuarioPorIdentificadorUseCase).ejecutar(id);
    }
}
