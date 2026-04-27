package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.puerto.comando.PersonaRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.PersonaRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import co.edu.uco.sibe.dominio.transversal.excepcion.AuthorizationException;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import co.edu.uco.sibe.dominio.modelo.Persona;
import co.edu.uco.sibe.dominio.modelo.TipoUsuario;
import co.edu.uco.sibe.dominio.modelo.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.ADMINISTRADOR_DIRECCION;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EliminarUsuarioUseCaseTest {

    @Mock
    private PersonaRepositorioComando personaRepositorioComando;
    @Mock
    private PersonaRepositorioConsulta personaRepositorioConsulta;
    @Mock
    private AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    private EliminarUsuarioUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new EliminarUsuarioUseCase(personaRepositorioComando, personaRepositorioConsulta,
                autorizacionServicio);
    }

    @Test
    void deberiaEliminarUsuarioExitosamente() {
        UUID identificador = UUID.randomUUID();
        Persona persona = mock(Persona.class);
        Usuario usuario = mock(Usuario.class);
        TipoUsuario tipoUsuario = mock(TipoUsuario.class);

        doNothing().when(autorizacionServicio).validarAccesoAUsuario(identificador);
        when(personaRepositorioConsulta.consultarPersonaPorIdentificador(identificador)).thenReturn(persona);
        when(persona.getCorreo()).thenReturn("test@test.com");
        when(personaRepositorioConsulta.consultarUsuarioPorCorreo("test@test.com")).thenReturn(usuario);
        when(usuario.getTipoUsuario()).thenReturn(tipoUsuario);
        when(tipoUsuario.getCodigo()).thenReturn("COLABORADOR");

        UUID resultado = useCase.ejecutar(identificador);

        assertEquals(identificador, resultado);
        verify(personaRepositorioComando).eliminarUsuario(identificador);
    }

    @Test
    void deberiaEliminarAdminDireccionCuandoHayMasDeDosActivos() {
        UUID identificador = UUID.randomUUID();
        Persona persona = mock(Persona.class);
        Usuario usuario = mock(Usuario.class);
        TipoUsuario tipoUsuario = mock(TipoUsuario.class);

        doNothing().when(autorizacionServicio).validarAccesoAUsuario(identificador);
        when(personaRepositorioConsulta.consultarPersonaPorIdentificador(identificador)).thenReturn(persona);
        when(persona.getCorreo()).thenReturn("admin@test.com");
        when(personaRepositorioConsulta.consultarUsuarioPorCorreo("admin@test.com")).thenReturn(usuario);
        when(usuario.getTipoUsuario()).thenReturn(tipoUsuario);
        when(tipoUsuario.getCodigo()).thenReturn(ADMINISTRADOR_DIRECCION);
        when(personaRepositorioConsulta.contarUsuariosActivosPorTipoUsuario(ADMINISTRADOR_DIRECCION)).thenReturn(3L);

        UUID resultado = useCase.ejecutar(identificador);

        assertEquals(identificador, resultado);
        verify(personaRepositorioComando).eliminarUsuario(identificador);
    }

    @Test
    void deberiaBloquearEliminacionCuandoQuedanExactamenteDosAdminsDireccion() {
        UUID identificador = UUID.randomUUID();
        Persona persona = mock(Persona.class);
        Usuario usuario = mock(Usuario.class);
        TipoUsuario tipoUsuario = mock(TipoUsuario.class);

        doNothing().when(autorizacionServicio).validarAccesoAUsuario(identificador);
        when(personaRepositorioConsulta.consultarPersonaPorIdentificador(identificador)).thenReturn(persona);
        when(persona.getCorreo()).thenReturn("admin@test.com");
        when(personaRepositorioConsulta.consultarUsuarioPorCorreo("admin@test.com")).thenReturn(usuario);
        when(usuario.getTipoUsuario()).thenReturn(tipoUsuario);
        when(tipoUsuario.getCodigo()).thenReturn(ADMINISTRADOR_DIRECCION);
        when(personaRepositorioConsulta.contarUsuariosActivosPorTipoUsuario(ADMINISTRADOR_DIRECCION)).thenReturn(2L);

        assertThrows(ValorInvalidoExcepcion.class, () -> useCase.ejecutar(identificador));
        verify(personaRepositorioComando, never()).eliminarUsuario(any());
    }

    @Test
    void deberiaLanzarAuthorizationExceptionCuandoNoTieneAcceso() {
        UUID identificador = UUID.randomUUID();

        doThrow(new AuthorizationException("No tiene acceso")).when(autorizacionServicio)
                .validarAccesoAUsuario(identificador);

        assertThrows(AuthorizationException.class, () -> useCase.ejecutar(identificador));

        verify(personaRepositorioComando, never()).eliminarUsuario(any());
    }

    @Test
    void deberiaLanzarValorInvalidoExcepcionCuandoUsuarioNoExiste() {
        UUID identificador = UUID.randomUUID();

        doNothing().when(autorizacionServicio).validarAccesoAUsuario(identificador);
        when(personaRepositorioConsulta.consultarPersonaPorIdentificador(identificador)).thenReturn(null);

        assertThrows(ValorInvalidoExcepcion.class, () -> useCase.ejecutar(identificador));

        verify(personaRepositorioComando, never()).eliminarUsuario(any());
    }
}
