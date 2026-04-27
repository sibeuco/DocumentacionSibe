package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.enums.TipoArea;
import co.edu.uco.sibe.dominio.modelo.Identificacion;
import co.edu.uco.sibe.dominio.modelo.Persona;
import co.edu.uco.sibe.dominio.modelo.TipoIdentificacion;
import co.edu.uco.sibe.dominio.modelo.TipoUsuario;
import co.edu.uco.sibe.dominio.modelo.Usuario;
import co.edu.uco.sibe.dominio.puerto.comando.PersonaRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.PersonaRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.servicio.EncriptarClaveServicio;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import co.edu.uco.sibe.dominio.service.ModificarVinculacionUsuarioConAreaService;
import co.edu.uco.sibe.dominio.service.VincularUsuarioConAreaService;
import co.edu.uco.sibe.dominio.transversal.excepcion.AuthorizationException;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorDuplicadoExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.CORREO_EXISTENTE;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.DOCUMENTO_EXISTENTE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuardarUsuarioUseCaseTest {

    @Mock
    private PersonaRepositorioComando personaRepositorioComando;
    @Mock
    private PersonaRepositorioConsulta personaRepositorioConsulta;
    @Mock
    private EncriptarClaveServicio encriptarClaveServicio;
    @Mock
    private VincularUsuarioConAreaService vincularUsuarioConAreaService;
    @Mock
    private AutorizacionContextoOrganizacionalServicio autorizacionServicio;
    @Mock
    private ModificarVinculacionUsuarioConAreaService modificarVinculacionUsuarioConAreaService;

    private GuardarUsuarioUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GuardarUsuarioUseCase(personaRepositorioComando, personaRepositorioConsulta,
                encriptarClaveServicio, vincularUsuarioConAreaService, autorizacionServicio,
                modificarVinculacionUsuarioConAreaService);
    }

    @Test
    void deberiaGuardarUsuarioExitosamente() {
        UUID areaId = UUID.randomUUID();
        UUID identificadorGuardado = UUID.randomUUID();
        TipoIdentificacion tipoId = TipoIdentificacion.construir(UUID.randomUUID(), "CC", "Cedula Ciudadania");
        Identificacion identificacion = Identificacion.construir(UUID.randomUUID(), "12345678", tipoId);
        TipoUsuario tipoUsuario = TipoUsuario.construir(UUID.randomUUID(), "ADM", "Admin", true, true, true, true);
        Usuario usuario = Usuario.construir(UUID.randomUUID(), "test@correo.com", "Clave123!", tipoUsuario, true);
        Persona persona = Persona.construir(UUID.randomUUID(), "Juan Carlos", "Lopez Garcia", "test@correo.com", identificacion);

        doNothing().when(autorizacionServicio).validarAccesoAArea(areaId);
        when(personaRepositorioConsulta.consultarUsuarioPorCorreo(usuario.getCorreo())).thenReturn(null);
        when(personaRepositorioConsulta.consultarPersonaPorDocumento(identificacion.getNumeroIdentificacion())).thenReturn(null);
        when(encriptarClaveServicio.ejecutar(usuario.getClave())).thenReturn("claveEncriptada");
        when(personaRepositorioConsulta.consultarUsuarioInactivoPorCorreo(usuario.getCorreo())).thenReturn(null);
        when(personaRepositorioComando.agregarNuevoUsuario(usuario, persona, "claveEncriptada"))
                .thenReturn(identificadorGuardado);

        UUID resultado = useCase.ejecutar(usuario, persona, areaId, TipoArea.AREA);

        assertEquals(identificadorGuardado, resultado);
        verify(vincularUsuarioConAreaService).ejecutar(identificadorGuardado, areaId, TipoArea.AREA);
    }

    @Test
    void deberiaLanzarAuthorizationExceptionCuandoNoTieneAcceso() {
        UUID areaId = UUID.randomUUID();
        Usuario usuario = mock(Usuario.class);
        Persona persona = mock(Persona.class);

        doThrow(new AuthorizationException("No tiene acceso")).when(autorizacionServicio)
                .validarAccesoAArea(areaId);

        assertThrows(AuthorizationException.class,
                () -> useCase.ejecutar(usuario, persona, areaId, TipoArea.AREA));

        verify(personaRepositorioComando, never()).agregarNuevoUsuario(any(), any(), any());
    }

    @Test
    void deberiaLanzarValorDuplicadoExcepcionCuandoCorreoExiste() {
        UUID areaId = UUID.randomUUID();
        TipoIdentificacion tipoId = TipoIdentificacion.construir(UUID.randomUUID(), "CC", "Cedula Ciudadania");
        Identificacion identificacion = Identificacion.construir(UUID.randomUUID(), "12345678", tipoId);
        TipoUsuario tipoUsuario = TipoUsuario.construir(UUID.randomUUID(), "ADM", "Admin", true, true, true, true);
        Usuario usuario = Usuario.construir(UUID.randomUUID(), "existente@test.com", "Clave123!", tipoUsuario, true);
        Persona persona = Persona.construir(UUID.randomUUID(), "Juan Carlos", "Lopez Garcia", "existente@test.com", identificacion);

        doNothing().when(autorizacionServicio).validarAccesoAArea(areaId);
        when(personaRepositorioConsulta.consultarUsuarioPorCorreo(usuario.getCorreo()))
                .thenReturn(Usuario.construir());

        ValorDuplicadoExcepcion excepcion = assertThrows(ValorDuplicadoExcepcion.class,
                () -> useCase.ejecutar(usuario, persona, areaId, TipoArea.AREA));

        assertEquals(CORREO_EXISTENTE, excepcion.getMessage());
    }

    @Test
    void deberiaLanzarValorDuplicadoExcepcionCuandoDocumentoExiste() {
        UUID areaId = UUID.randomUUID();
        TipoIdentificacion tipoId = TipoIdentificacion.construir(UUID.randomUUID(), "CC", "Cedula Ciudadania");
        Identificacion identificacion = Identificacion.construir(UUID.randomUUID(), "12345678", tipoId);
        TipoUsuario tipoUsuario = TipoUsuario.construir(UUID.randomUUID(), "ADM", "Admin", true, true, true, true);
        Usuario usuario = Usuario.construir(UUID.randomUUID(), "nuevo@correo.com", "Clave123!", tipoUsuario, true);
        Persona persona = Persona.construir(UUID.randomUUID(), "Juan Carlos", "Lopez Garcia", "nuevo@correo.com", identificacion);

        doNothing().when(autorizacionServicio).validarAccesoAArea(areaId);
        when(personaRepositorioConsulta.consultarUsuarioPorCorreo(usuario.getCorreo())).thenReturn(null);
        when(personaRepositorioConsulta.consultarPersonaPorDocumento(identificacion.getNumeroIdentificacion()))
                .thenReturn(Persona.construir());

        ValorDuplicadoExcepcion excepcion = assertThrows(ValorDuplicadoExcepcion.class,
                () -> useCase.ejecutar(usuario, persona, areaId, TipoArea.AREA));

        assertEquals(DOCUMENTO_EXISTENTE, excepcion.getMessage());
    }

    @Test
    void deberiaReactivarUsuarioInactivoConMismoCorreo() {
        UUID areaId = UUID.randomUUID();
        UUID personaIdentificador = UUID.randomUUID();
        TipoIdentificacion tipoId = TipoIdentificacion.construir(UUID.randomUUID(), "CC", "Cedula Ciudadania");
        Identificacion identificacion = Identificacion.construir(UUID.randomUUID(), "12345678", tipoId);
        TipoUsuario tipoUsuario = TipoUsuario.construir(UUID.randomUUID(), "ADM", "Admin", true, true, true, true);
        Usuario usuario = Usuario.construir(UUID.randomUUID(), "reactivar@correo.com", "Clave123!", tipoUsuario, true);
        Persona persona = Persona.construir(UUID.randomUUID(), "Juan Carlos", "Lopez Garcia", "reactivar@correo.com", identificacion);
        Usuario usuarioInactivo = Usuario.construir(UUID.randomUUID(), "reactivar@correo.com", "claveVieja", tipoUsuario, false);

        doNothing().when(autorizacionServicio).validarAccesoAArea(areaId);
        when(personaRepositorioConsulta.consultarUsuarioPorCorreo(usuario.getCorreo())).thenReturn(null);
        when(personaRepositorioConsulta.consultarPersonaPorDocumento(identificacion.getNumeroIdentificacion())).thenReturn(null);
        when(encriptarClaveServicio.ejecutar(usuario.getClave())).thenReturn("claveEncriptada");
        when(personaRepositorioConsulta.consultarUsuarioInactivoPorCorreo(usuario.getCorreo())).thenReturn(usuarioInactivo);
        when(personaRepositorioComando.reactivarUsuario("reactivar@correo.com", usuario, persona, "claveEncriptada"))
                .thenReturn(personaIdentificador);

        UUID resultado = useCase.ejecutar(usuario, persona, areaId, TipoArea.AREA);

        assertEquals(personaIdentificador, resultado);
        verify(modificarVinculacionUsuarioConAreaService).ejecutar(personaIdentificador, areaId, TipoArea.AREA);
        verify(personaRepositorioComando, never()).agregarNuevoUsuario(any(), any(), any());
        verify(vincularUsuarioConAreaService, never()).ejecutar(any(), any(), any());
    }
}
