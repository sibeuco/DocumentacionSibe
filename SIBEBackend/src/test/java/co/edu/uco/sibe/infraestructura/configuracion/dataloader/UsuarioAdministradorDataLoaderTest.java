package co.edu.uco.sibe.infraestructura.configuracion.dataloader;

import co.edu.uco.sibe.aplicacion.comando.fabrica.PersonaFabrica;
import co.edu.uco.sibe.aplicacion.comando.fabrica.UsuarioFabrica;
import co.edu.uco.sibe.aplicacion.consulta.ConsultarTipoIdentificacionPorSiglaManejador;
import co.edu.uco.sibe.aplicacion.consulta.ConsultarTipoUsuarioPorCodigoManejador;
import co.edu.uco.sibe.aplicacion.consulta.HayDatosUsuarioManejador;
import co.edu.uco.sibe.dominio.modelo.Persona;
import co.edu.uco.sibe.dominio.modelo.TipoIdentificacion;
import co.edu.uco.sibe.dominio.modelo.TipoUsuario;
import co.edu.uco.sibe.dominio.modelo.Usuario;
import co.edu.uco.sibe.dominio.puerto.comando.PersonaRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.servicio.EncriptarClaveServicio;
import co.edu.uco.sibe.dominio.service.VincularUsuarioConAreaService;
import co.edu.uco.sibe.infraestructura.adaptador.dao.DireccionDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.DireccionEntidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioAdministradorDataLoaderTest {

    @Mock private HayDatosUsuarioManejador hayDatosUsuarioManejador;
    @Mock private ConsultarTipoUsuarioPorCodigoManejador consultarTipoUsuarioPorCodigoManejador;
    @Mock private ConsultarTipoIdentificacionPorSiglaManejador consultarTipoIdentificacionPorSiglaManejador;
    @Mock private DireccionDAO direccionDAO;
    @Mock private UsuarioFabrica usuarioFabrica;
    @Mock private PersonaFabrica personaFabrica;
    @Mock private PersonaRepositorioComando personaRepositorioComando;
    @Mock private EncriptarClaveServicio encriptarClaveServicio;
    @Mock private VincularUsuarioConAreaService vincularUsuarioConAreaService;

    private UsuarioAdministradorDataLoader dataLoader;

    @BeforeEach
    void setUp() {
        dataLoader = new UsuarioAdministradorDataLoader(
                hayDatosUsuarioManejador,
                consultarTipoUsuarioPorCodigoManejador,
                consultarTipoIdentificacionPorSiglaManejador,
                direccionDAO,
                usuarioFabrica,
                personaFabrica,
                personaRepositorioComando,
                encriptarClaveServicio,
                vincularUsuarioConAreaService
        );
    }

    @Test
    void deberiaCargarDatosCuandoNoExistenUsuarios() throws Exception {
        TipoUsuario tipoUsuario = mock(TipoUsuario.class);
        TipoIdentificacion tipoIdentificacion = mock(TipoIdentificacion.class);
        DireccionEntidad direccionEntidad = new DireccionEntidad();
        direccionEntidad.setIdentificador(UUID.randomUUID());
        Usuario usuario = mock(Usuario.class);
        Persona persona = mock(Persona.class);

        when(hayDatosUsuarioManejador.ejecutar()).thenReturn(false);
        when(consultarTipoUsuarioPorCodigoManejador.ejecutar(any())).thenReturn(tipoUsuario);
        when(consultarTipoIdentificacionPorSiglaManejador.ejecutar(any())).thenReturn(tipoIdentificacion);
        when(direccionDAO.findByNombre(any())).thenReturn(direccionEntidad);
        when(tipoUsuario.getIdentificador()).thenReturn(UUID.randomUUID());
        when(tipoIdentificacion.getIdentificador()).thenReturn(UUID.randomUUID());
        when(usuarioFabrica.construir(any())).thenReturn(usuario);
        when(personaFabrica.construir(any())).thenReturn(persona);
        when(usuario.getClave()).thenReturn("clave123");
        when(encriptarClaveServicio.ejecutar(any())).thenReturn("encrypted");
        when(personaRepositorioComando.agregarNuevoUsuario(any(), any(), any())).thenReturn(UUID.randomUUID());

        dataLoader.run();

        verify(personaRepositorioComando).agregarNuevoUsuario(any(), any(), any());
        verify(vincularUsuarioConAreaService).ejecutar(any(), any(), any());
    }

    @Test
    void noDeberiaCargarDatosCuandoYaExistenUsuarios() throws Exception {
        when(hayDatosUsuarioManejador.ejecutar()).thenReturn(true);

        dataLoader.run();

        verify(personaRepositorioComando, never()).agregarNuevoUsuario(any(), any(), any());
    }
}
