package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.dto.UsuarioDTO;
import co.edu.uco.sibe.dominio.modelo.Persona;
import co.edu.uco.sibe.dominio.puerto.consulta.PersonaRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarUsuarioPorIdentificadorUseCaseTest {

    @Mock
    private PersonaRepositorioConsulta personaRepositorioConsulta;

    @Mock
    private AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    private ConsultarUsuarioPorIdentificadorUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ConsultarUsuarioPorIdentificadorUseCase(personaRepositorioConsulta, autorizacionServicio);
    }

    @Test
    void deberiaRetornarUsuarioPorIdentificadorExitosamente() {
        UUID id = UUID.randomUUID();
        Persona persona = mock(Persona.class);
        UsuarioDTO dto = mock(UsuarioDTO.class);
        doNothing().when(autorizacionServicio).validarAccesoAUsuario(id);
        when(personaRepositorioConsulta.consultarPersonaPorIdentificador(id)).thenReturn(persona);
        when(personaRepositorioConsulta.consultarUsuarioPorIdentificadorDTO(id)).thenReturn(dto);

        UsuarioDTO resultado = useCase.ejecutar(id);

        assertEquals(dto, resultado);
        verify(autorizacionServicio).validarAccesoAUsuario(id);
    }

    @Test
    void deberiaLanzarExcepcionCuandoAutorizacionFalla() {
        UUID id = UUID.randomUUID();
        doThrow(new ValorInvalidoExcepcion("Sin acceso")).when(autorizacionServicio).validarAccesoAUsuario(id);

        assertThrows(ValorInvalidoExcepcion.class, () -> useCase.ejecutar(id));
    }

    @Test
    void deberiaLanzarExcepcionCuandoPersonaNoExistePorIdentificador() {
        UUID id = UUID.randomUUID();
        doNothing().when(autorizacionServicio).validarAccesoAUsuario(id);
        when(personaRepositorioConsulta.consultarPersonaPorIdentificador(id)).thenReturn(null);

        assertThrows(ValorInvalidoExcepcion.class, () -> useCase.ejecutar(id));
    }
}
