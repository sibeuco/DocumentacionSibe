package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.AreaComando;
import co.edu.uco.sibe.aplicacion.comando.UsuarioModificacionComando;
import co.edu.uco.sibe.aplicacion.comando.fabrica.PersonaFabrica;
import co.edu.uco.sibe.aplicacion.comando.fabrica.UsuarioFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.dominio.enums.TipoArea;
import co.edu.uco.sibe.dominio.modelo.Persona;
import co.edu.uco.sibe.dominio.modelo.Usuario;
import co.edu.uco.sibe.dominio.usecase.comando.ModificarUsuarioUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModificarUsuarioManejadorTest {

    @Mock private UsuarioFabrica usuarioFabrica;
    @Mock private PersonaFabrica personaFabrica;
    @Mock private ModificarUsuarioUseCase modificarUsuarioUseCase;

    private ModificarUsuarioManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ModificarUsuarioManejador(usuarioFabrica, personaFabrica, modificarUsuarioUseCase);
    }

    @Test
    void deberiaEjecutarModificarUsuario() {
        UUID parametro = UUID.randomUUID();
        UUID areaUUID = UUID.randomUUID();
        UUID idEsperado = UUID.randomUUID();
        UsuarioModificacionComando comando = mock(UsuarioModificacionComando.class);
        Usuario usuario = mock(Usuario.class);
        Persona persona = mock(Persona.class);

        AreaComando areaComando = new AreaComando(areaUUID.toString(), "DIRECCION");
        when(comando.getArea()).thenReturn(areaComando);

        when(usuarioFabrica.construirActualizar(comando, parametro)).thenReturn(usuario);
        when(personaFabrica.construirActualizar(comando, parametro)).thenReturn(persona);
        when(modificarUsuarioUseCase.ejecutar(usuario, persona, areaUUID, TipoArea.DIRECCION, parametro)).thenReturn(idEsperado);

        ComandoRespuesta<UUID> resultado = manejador.ejecutar(comando, parametro);

        assertEquals(idEsperado, resultado.getValor());
        verify(usuarioFabrica).construirActualizar(comando, parametro);
        verify(personaFabrica).construirActualizar(comando, parametro);
        verify(modificarUsuarioUseCase).ejecutar(usuario, persona, areaUUID, TipoArea.DIRECCION, parametro);
    }
}
