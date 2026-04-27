package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.aplicacion.comando.UsuarioComando;
import co.edu.uco.sibe.aplicacion.comando.UsuarioModificacionComando;
import co.edu.uco.sibe.dominio.modelo.Persona;
import co.edu.uco.sibe.dominio.modelo.TipoUsuario;
import co.edu.uco.sibe.dominio.modelo.Usuario;
import co.edu.uco.sibe.dominio.puerto.consulta.PersonaRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.TipoUsuarioRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioFabricaTest {

    @Mock
    private TipoUsuarioRepositorioConsulta tipoUsuarioRepositorioConsulta;
    @Mock
    private PersonaRepositorioConsulta personaRepositorioConsulta;

    private UsuarioFabrica fabrica;

    @BeforeEach
    void setUp() {
        fabrica = new UsuarioFabrica(tipoUsuarioRepositorioConsulta, personaRepositorioConsulta);
    }

    @Test
    void deberiaConstruirUsuarioDesdeComando() {
        UUID tipoUUID = UUID.randomUUID();
        TipoUsuario tipoUsuario = TipoUsuario.construir(tipoUUID, "ADM", "Administrador del Sistema", true, true, true, true);
        when(personaRepositorioConsulta.consultarUsuarioPorIdentificador(any())).thenReturn(null);
        when(tipoUsuarioRepositorioConsulta.consultarPorIdentificador(tipoUUID)).thenReturn(tipoUsuario);

        UsuarioComando comando = new UsuarioComando(tipoUUID.toString(), "12345678", "Juan", "Perez", "test@correo.com", "Clave123!", tipoUUID.toString(), null);

        Usuario resultado = fabrica.construir(comando);

        assertNotNull(resultado.getIdentificador());
        assertEquals("test@correo.com", resultado.getCorreo());
        assertEquals("Clave123!", resultado.getClave());
        assertEquals(tipoUsuario, resultado.getTipoUsuario());
        assertTrue(resultado.isEstaActivo());
    }

    @Test
    void deberiaConstruirUsuarioParaActualizacion() {
        UUID tipoUUID = UUID.randomUUID();
        UUID identificadorPersona = UUID.randomUUID();
        TipoUsuario tipoUsuario = TipoUsuario.construir(tipoUUID, "ADM", "Administrador", true, true, true, true);
        Persona persona = Persona.construir(identificadorPersona, "Juan", "Perez", "original@correo.com", null);
        Usuario usuarioExistente = Usuario.construir(UUID.randomUUID(), "original@correo.com", "ClaveOrig1", tipoUsuario, true);

        when(tipoUsuarioRepositorioConsulta.consultarPorIdentificador(tipoUUID)).thenReturn(tipoUsuario);
        when(personaRepositorioConsulta.consultarPersonaPorIdentificador(identificadorPersona)).thenReturn(persona);
        when(personaRepositorioConsulta.consultarUsuarioPorCorreo("original@correo.com")).thenReturn(usuarioExistente);

        UsuarioModificacionComando comando = new UsuarioModificacionComando(
                tipoUUID.toString(), "12345678", "Juan", "Perez", "nuevo@correo.com", tipoUUID.toString(), null);

        Usuario resultado = fabrica.construirActualizar(comando, identificadorPersona);

        assertNotNull(resultado);
        assertEquals("nuevo@correo.com", resultado.getCorreo());
        assertEquals(tipoUsuario, resultado.getTipoUsuario());
    }
}
