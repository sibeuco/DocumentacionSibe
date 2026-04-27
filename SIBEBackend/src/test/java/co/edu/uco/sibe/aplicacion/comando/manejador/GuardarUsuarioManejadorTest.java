package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.AreaComando;
import co.edu.uco.sibe.aplicacion.comando.UsuarioComando;
import co.edu.uco.sibe.aplicacion.comando.fabrica.PersonaFabrica;
import co.edu.uco.sibe.aplicacion.comando.fabrica.UsuarioFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.dominio.enums.TipoArea;
import co.edu.uco.sibe.dominio.modelo.Persona;
import co.edu.uco.sibe.dominio.modelo.Usuario;
import co.edu.uco.sibe.dominio.usecase.comando.GuardarUsuarioUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuardarUsuarioManejadorTest {

    @Mock private UsuarioFabrica usuarioFabrica;
    @Mock private PersonaFabrica personaFabrica;
    @Mock private GuardarUsuarioUseCase guardarUsuarioUseCase;

    private GuardarUsuarioManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new GuardarUsuarioManejador(usuarioFabrica, personaFabrica, guardarUsuarioUseCase);
    }

    @Test
    void deberiaEjecutarGuardarUsuario() {
        UUID areaUUID = UUID.randomUUID();
        UUID idEsperado = UUID.randomUUID();
        UsuarioComando comando = mock(UsuarioComando.class);
        Usuario usuario = mock(Usuario.class);
        Persona persona = mock(Persona.class);

        AreaComando areaComando = new AreaComando(areaUUID.toString(), "DIRECCION");
        when(comando.getArea()).thenReturn(areaComando);

        when(usuarioFabrica.construir(comando)).thenReturn(usuario);
        when(personaFabrica.construir(comando)).thenReturn(persona);
        when(guardarUsuarioUseCase.ejecutar(usuario, persona, areaUUID, TipoArea.DIRECCION)).thenReturn(idEsperado);

        ComandoRespuesta<UUID> resultado = manejador.ejecutar(comando);

        assertEquals(idEsperado, resultado.getValor());
        verify(usuarioFabrica).construir(comando);
        verify(personaFabrica).construir(comando);
        verify(guardarUsuarioUseCase).ejecutar(usuario, persona, areaUUID, TipoArea.DIRECCION);
    }
}
