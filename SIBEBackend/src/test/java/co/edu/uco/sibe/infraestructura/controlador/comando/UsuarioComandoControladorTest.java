package co.edu.uco.sibe.infraestructura.controlador.comando;

import co.edu.uco.sibe.aplicacion.comando.*;
import co.edu.uco.sibe.aplicacion.comando.manejador.*;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioComandoControladorTest {

    @Mock private GuardarUsuarioManejador guardarUsuarioManejador;
    @Mock private EliminarPersonaManejador eliminarPersonaManejador;
    @Mock private ModificarUsuarioManejador modificarUsuarioManejador;
    @Mock private SolicitarCodigoManejador solicitarCodigoManejador;
    @Mock private ValidarCodigoRecuperacionClaveManejador validarCodigoRecuperacionClaveManejador;
    @Mock private RecuperarClaveManejador recuperarClaveManejador;
    @Mock private ModificarClaveManejador modificarClaveManejador;

    private UsuarioComandoControlador controlador;

    @BeforeEach
    void setUp() {
        controlador = new UsuarioComandoControlador(
                guardarUsuarioManejador,
                eliminarPersonaManejador,
                modificarUsuarioManejador,
                solicitarCodigoManejador,
                validarCodigoRecuperacionClaveManejador,
                recuperarClaveManejador,
                modificarClaveManejador
        );
    }

    @Test
    void deberiaGuardarUsuario() {
        UsuarioComando comando = mock(UsuarioComando.class);
        UUID id = UUID.randomUUID();
        when(guardarUsuarioManejador.ejecutar(comando)).thenReturn(new ComandoRespuesta<>(id));

        ComandoRespuesta<UUID> resultado = controlador.guardar(comando);

        assertEquals(id, resultado.getValor());
        verify(guardarUsuarioManejador).ejecutar(comando);
    }

    @Test
    void deberiaModificarUsuario() {
        UUID identificador = UUID.randomUUID();
        UsuarioModificacionComando comando = mock(UsuarioModificacionComando.class);
        UUID id = UUID.randomUUID();
        when(modificarUsuarioManejador.ejecutar(comando, identificador)).thenReturn(new ComandoRespuesta<>(id));

        ComandoRespuesta<UUID> resultado = controlador.modificar(comando, identificador);

        assertEquals(id, resultado.getValor());
        verify(modificarUsuarioManejador).ejecutar(comando, identificador);
    }

    @Test
    void deberiaModificarClave() {
        ClaveModificacionComando comando = mock(ClaveModificacionComando.class);
        UUID id = UUID.randomUUID();
        when(modificarClaveManejador.ejecutar(comando)).thenReturn(new ComandoRespuesta<>(id));

        ComandoRespuesta<UUID> resultado = controlador.modificarClave(comando);

        assertEquals(id, resultado.getValor());
        verify(modificarClaveManejador).ejecutar(comando);
    }

    @Test
    void deberiaEliminarUsuario() {
        UUID identificador = UUID.randomUUID();
        UUID id = UUID.randomUUID();
        when(eliminarPersonaManejador.ejecutar(identificador)).thenReturn(new ComandoRespuesta<>(id));

        ComandoRespuesta<UUID> resultado = controlador.eliminar(identificador);

        assertEquals(id, resultado.getValor());
        verify(eliminarPersonaManejador).ejecutar(identificador);
    }

    @Test
    void deberiaSolicitarCodigo() {
        String correo = "test@test.com";
        UUID id = UUID.randomUUID();
        when(solicitarCodigoManejador.ejecutar(correo)).thenReturn(new ComandoRespuesta<>(id));

        ComandoRespuesta<UUID> resultado = controlador.solicitarCodigo(correo);

        assertEquals(id, resultado.getValor());
        verify(solicitarCodigoManejador).ejecutar(correo);
    }

    @Test
    void deberiaValidarCodigo() {
        ValidarCodigoRecuperacionClaveComando comando = mock(ValidarCodigoRecuperacionClaveComando.class);
        when(validarCodigoRecuperacionClaveManejador.ejecutar(comando)).thenReturn(new ComandoRespuesta<>(true));

        ComandoRespuesta<Boolean> resultado = controlador.validarCodigo(comando);

        assertTrue(resultado.getValor());
        verify(validarCodigoRecuperacionClaveManejador).ejecutar(comando);
    }

    @Test
    void deberiaRecuperarClave() {
        RecuperarClaveComando comando = mock(RecuperarClaveComando.class);
        UUID id = UUID.randomUUID();
        when(recuperarClaveManejador.ejecutar(comando)).thenReturn(new ComandoRespuesta<>(id));

        ComandoRespuesta<UUID> resultado = controlador.recuperarClave(comando);

        assertEquals(id, resultado.getValor());
        verify(recuperarClaveManejador).ejecutar(comando);
    }
}
