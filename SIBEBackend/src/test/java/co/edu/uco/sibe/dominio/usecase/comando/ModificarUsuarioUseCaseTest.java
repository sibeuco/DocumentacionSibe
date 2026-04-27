package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.enums.TipoArea;
import co.edu.uco.sibe.dominio.modelo.Identificacion;
import co.edu.uco.sibe.dominio.modelo.Persona;
import co.edu.uco.sibe.dominio.modelo.TipoIdentificacion;
import co.edu.uco.sibe.dominio.modelo.TipoUsuario;
import co.edu.uco.sibe.dominio.modelo.Usuario;
import co.edu.uco.sibe.dominio.puerto.comando.PersonaRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.PersonaRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import co.edu.uco.sibe.dominio.service.ModificarVinculacionUsuarioConAreaService;
import co.edu.uco.sibe.dominio.transversal.excepcion.AuthorizationException;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorDuplicadoExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModificarUsuarioUseCaseTest {

    @Mock
    private PersonaRepositorioComando personaRepositorioComando;
    @Mock
    private PersonaRepositorioConsulta personaRepositorioConsulta;
    @Mock
    private ModificarVinculacionUsuarioConAreaService modificarVinculacionService;
    @Mock
    private AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    private ModificarUsuarioUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ModificarUsuarioUseCase(personaRepositorioComando, personaRepositorioConsulta,
                modificarVinculacionService, autorizacionServicio);
    }

    @Test
    void deberiaModificarUsuarioExitosamente() {
        UUID identificador = UUID.randomUUID();
        UUID areaId = UUID.randomUUID();
        TipoIdentificacion tipoId = TipoIdentificacion.construir(UUID.randomUUID(), "CC", "Cedula Ciudadania");
        Identificacion identificacion = Identificacion.construir(UUID.randomUUID(), "12345678", tipoId);
        TipoUsuario tipoUsuario = TipoUsuario.construir(UUID.randomUUID(), "ADM", "Admin", true, true, true, true);
        Usuario usuario = Usuario.construir(UUID.randomUUID(), "test@correo.com", "Clave123!", tipoUsuario, true);
        Persona persona = Persona.construir(identificador, "Juan Carlos", "Lopez Garcia", "test@correo.com", identificacion);

        doNothing().when(autorizacionServicio).validarAccesoAUsuario(identificador);
        when(personaRepositorioConsulta.consultarPersonaPorIdentificador(identificador)).thenReturn(Persona.construir());
        when(personaRepositorioConsulta.consultarPersonaPorDocumento(identificacion.getNumeroIdentificacion())).thenReturn(null);
        when(personaRepositorioConsulta.consultarPersonaPorCorreo(persona.getCorreo())).thenReturn(null);

        UUID resultado = useCase.ejecutar(usuario, persona, areaId, TipoArea.AREA, identificador);

        assertEquals(identificador, resultado);
        verify(personaRepositorioComando).modificarUsuario(usuario, persona);
        verify(modificarVinculacionService).ejecutar(identificador, areaId, TipoArea.AREA);
    }

    @Test
    void deberiaLanzarAuthorizationExceptionCuandoNoTieneAcceso() {
        UUID identificador = UUID.randomUUID();
        UUID areaId = UUID.randomUUID();

        doThrow(new AuthorizationException("No tiene acceso")).when(autorizacionServicio)
                .validarAccesoAUsuario(identificador);

        assertThrows(AuthorizationException.class,
                () -> useCase.ejecutar(mock(Usuario.class), mock(Persona.class), areaId, TipoArea.AREA, identificador));

        verify(personaRepositorioComando, never()).modificarUsuario(any(), any());
    }

    @Test
    void deberiaLanzarValorInvalidoExcepcionCuandoPersonaNoExiste() {
        UUID identificador = UUID.randomUUID();
        UUID areaId = UUID.randomUUID();

        doNothing().when(autorizacionServicio).validarAccesoAUsuario(identificador);
        when(personaRepositorioConsulta.consultarPersonaPorIdentificador(identificador)).thenReturn(null);

        assertThrows(ValorInvalidoExcepcion.class,
                () -> useCase.ejecutar(mock(Usuario.class), mock(Persona.class), areaId, TipoArea.AREA, identificador));
    }

    @Test
    void deberiaLanzarValorDuplicadoExcepcionCuandoDocumentoYaPertenaceAOtro() {
        UUID identificador = UUID.randomUUID();
        UUID otroIdentificador = UUID.randomUUID();
        UUID areaId = UUID.randomUUID();
        TipoIdentificacion tipoId = TipoIdentificacion.construir(UUID.randomUUID(), "CC", "Cedula Ciudadania");
        Identificacion identificacion = Identificacion.construir(UUID.randomUUID(), "12345678", tipoId);
        TipoUsuario tipoUsuario = TipoUsuario.construir(UUID.randomUUID(), "ADM", "Admin", true, true, true, true);
        Usuario usuario = Usuario.construir(UUID.randomUUID(), "test@correo.com", "Clave123!", tipoUsuario, true);
        Persona persona = Persona.construir(identificador, "Juan Carlos", "Lopez Garcia", "test@correo.com", identificacion);
        Persona personaExistente = mock(Persona.class);

        when(personaExistente.getIdentificador()).thenReturn(otroIdentificador);

        doNothing().when(autorizacionServicio).validarAccesoAUsuario(identificador);
        when(personaRepositorioConsulta.consultarPersonaPorIdentificador(identificador)).thenReturn(Persona.construir());
        when(personaRepositorioConsulta.consultarPersonaPorDocumento(identificacion.getNumeroIdentificacion())).thenReturn(personaExistente);

        ValorDuplicadoExcepcion excepcion = assertThrows(ValorDuplicadoExcepcion.class,
                () -> useCase.ejecutar(usuario, persona, areaId, TipoArea.AREA, identificador));

        assertEquals(DOCUMENTO_EXISTENTE, excepcion.getMessage());
    }

    @Test
    void deberiaLanzarValorDuplicadoExcepcionCuandoCorreoYaPertenaceAOtro() {
        UUID identificador = UUID.randomUUID();
        UUID otroIdentificador = UUID.randomUUID();
        UUID areaId = UUID.randomUUID();
        TipoIdentificacion tipoId = TipoIdentificacion.construir(UUID.randomUUID(), "CC", "Cedula Ciudadania");
        Identificacion identificacion = Identificacion.construir(UUID.randomUUID(), "12345678", tipoId);
        TipoUsuario tipoUsuario = TipoUsuario.construir(UUID.randomUUID(), "ADM", "Admin", true, true, true, true);
        Usuario usuario = Usuario.construir(UUID.randomUUID(), "test@correo.com", "Clave123!", tipoUsuario, true);
        Persona persona = Persona.construir(identificador, "Juan Carlos", "Lopez Garcia", "test@correo.com", identificacion);
        Persona personaExistenteCorreo = mock(Persona.class);

        when(personaExistenteCorreo.getIdentificador()).thenReturn(otroIdentificador);

        doNothing().when(autorizacionServicio).validarAccesoAUsuario(identificador);
        when(personaRepositorioConsulta.consultarPersonaPorIdentificador(identificador)).thenReturn(Persona.construir());
        when(personaRepositorioConsulta.consultarPersonaPorDocumento(identificacion.getNumeroIdentificacion())).thenReturn(null);
        when(personaRepositorioConsulta.consultarPersonaPorCorreo(persona.getCorreo())).thenReturn(personaExistenteCorreo);

        ValorDuplicadoExcepcion excepcion = assertThrows(ValorDuplicadoExcepcion.class,
                () -> useCase.ejecutar(usuario, persona, areaId, TipoArea.AREA, identificador));

        assertEquals(CORREO_EXISTENTE, excepcion.getMessage());
    }

    @Test
    void deberiaPermitirDocumentoSiPertenaceAlMismoUsuario() {
        UUID identificador = UUID.randomUUID();
        UUID areaId = UUID.randomUUID();
        TipoIdentificacion tipoId = TipoIdentificacion.construir(UUID.randomUUID(), "CC", "Cedula Ciudadania");
        Identificacion identificacion = Identificacion.construir(UUID.randomUUID(), "12345678", tipoId);
        TipoUsuario tipoUsuario = TipoUsuario.construir(UUID.randomUUID(), "ADM", "Admin", true, true, true, true);
        Usuario usuario = Usuario.construir(UUID.randomUUID(), "test@correo.com", "Clave123!", tipoUsuario, true);
        Persona persona = Persona.construir(identificador, "Juan Carlos", "Lopez Garcia", "test@correo.com", identificacion);
        Persona mismaPersona = mock(Persona.class);

        when(mismaPersona.getIdentificador()).thenReturn(identificador);

        doNothing().when(autorizacionServicio).validarAccesoAUsuario(identificador);
        when(personaRepositorioConsulta.consultarPersonaPorIdentificador(identificador)).thenReturn(Persona.construir());
        when(personaRepositorioConsulta.consultarPersonaPorDocumento(identificacion.getNumeroIdentificacion())).thenReturn(mismaPersona);
        when(personaRepositorioConsulta.consultarPersonaPorCorreo(persona.getCorreo())).thenReturn(null);

        UUID resultado = useCase.ejecutar(usuario, persona, areaId, TipoArea.AREA, identificador);

        assertEquals(identificador, resultado);
        verify(personaRepositorioComando).modificarUsuario(usuario, persona);
    }
}
