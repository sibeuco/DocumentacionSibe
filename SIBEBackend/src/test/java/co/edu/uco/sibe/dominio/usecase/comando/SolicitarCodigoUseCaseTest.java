package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.Usuario;
import co.edu.uco.sibe.dominio.puerto.comando.PersonaRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.PersonaRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.servicio.EncriptarClaveServicio;
import co.edu.uco.sibe.dominio.puerto.servicio.EnviarCorreoElectronicoService;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SolicitarCodigoUseCaseTest {

    @Mock
    private PersonaRepositorioConsulta personaRepositorioConsulta;
    @Mock
    private PersonaRepositorioComando personaRepositorioComando;
    @Mock
    private EncriptarClaveServicio encriptarClaveServicio;
    @Mock
    private EnviarCorreoElectronicoService enviarCorreoElectronicoService;

    private SolicitarCodigoUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new SolicitarCodigoUseCase(personaRepositorioConsulta, personaRepositorioComando,
                encriptarClaveServicio, enviarCorreoElectronicoService);
    }

    @Test
    void deberiaSolicitarCodigoExitosamente() {
        String correo = "test@test.com";
        UUID identificador = UUID.randomUUID();

        when(personaRepositorioConsulta.consultarUsuarioPorCorreo(correo)).thenReturn(mock(Usuario.class));
        when(encriptarClaveServicio.ejecutar(anyString())).thenReturn("codigoCifrado");
        when(personaRepositorioComando.crearPeticionRecuperacionClave(eq("codigoCifrado"), eq(correo), any()))
                .thenReturn(identificador);

        UUID resultado = useCase.ejecutar(correo);

        assertEquals(identificador, resultado);
        verify(enviarCorreoElectronicoService).enviarCorreo(eq(correo), anyString(), anyString());
    }

    @Test
    void deberiaLanzarExcepcionCuandoUsuarioNoExiste() {
        String correo = "noexiste@test.com";

        when(personaRepositorioConsulta.consultarUsuarioPorCorreo(correo)).thenReturn(null);

        assertThrows(ValorInvalidoExcepcion.class, () -> useCase.ejecutar(correo));

        verify(enviarCorreoElectronicoService, never()).enviarCorreo(any(), any(), any());
    }
}
