package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.puerto.comando.PersonaRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.PersonaRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.servicio.EncriptarClaveServicio;
import co.edu.uco.sibe.dominio.transversal.excepcion.TiempoVencidoExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValidarCodigoRecuperacionClaveUseCaseTest {

    @Mock
    private PersonaRepositorioConsulta personaRepositorioConsulta;
    @Mock
    private PersonaRepositorioComando personaRepositorioComando;
    @Mock
    private EncriptarClaveServicio encriptarClaveServicio;

    private ValidarCodigoRecuperacionClaveUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ValidarCodigoRecuperacionClaveUseCase(personaRepositorioConsulta,
                personaRepositorioComando, encriptarClaveServicio);
    }

    @Test
    void deberiaValidarCodigoExitosamente() {
        String correo = "test@test.com";
        String codigo = "abc123";

        when(personaRepositorioConsulta.consultarCodigoConCorreo(correo)).thenReturn("codigoCifrado");
        when(encriptarClaveServicio.existe(codigo, "codigoCifrado")).thenReturn(true);
        when(personaRepositorioConsulta.consultarFechaPeticionRecuperacionClaveConCorreo(correo))
                .thenReturn(LocalDateTime.now().minusMinutes(2));

        Boolean resultado = useCase.ejecutar(correo, codigo);

        assertTrue(resultado);
        verify(personaRepositorioComando).eliminarPeticionRecuperacionClaveConCorreo(correo);
    }

    @Test
    void deberiaLanzarExcepcionCuandoCodigoEsIncorrecto() {
        String correo = "test@test.com";
        String codigo = "incorrecto";

        when(personaRepositorioConsulta.consultarCodigoConCorreo(correo)).thenReturn("codigoCifrado");
        when(encriptarClaveServicio.existe(codigo, "codigoCifrado")).thenReturn(false);

        assertThrows(ValorInvalidoExcepcion.class,
                () -> useCase.ejecutar(correo, codigo));
    }

    @Test
    void deberiaLanzarTiempoVencidoExcepcionCuandoCodigoExpiro() {
        String correo = "test@test.com";
        String codigo = "abc123";

        when(personaRepositorioConsulta.consultarCodigoConCorreo(correo)).thenReturn("codigoCifrado");
        when(encriptarClaveServicio.existe(codigo, "codigoCifrado")).thenReturn(true);
        when(personaRepositorioConsulta.consultarFechaPeticionRecuperacionClaveConCorreo(correo))
                .thenReturn(LocalDateTime.now().minusMinutes(10));

        assertThrows(TiempoVencidoExcepcion.class,
                () -> useCase.ejecutar(correo, codigo));
    }

    @Test
    void deberiaValidarCodigoConExactamente5Minutos() {
        String correo = "test@test.com";
        String codigo = "abc123";

        when(personaRepositorioConsulta.consultarCodigoConCorreo(correo)).thenReturn("codigoCifrado");
        when(encriptarClaveServicio.existe(codigo, "codigoCifrado")).thenReturn(true);
        when(personaRepositorioConsulta.consultarFechaPeticionRecuperacionClaveConCorreo(correo))
                .thenReturn(LocalDateTime.now().minusMinutes(5));

        Boolean resultado = useCase.ejecutar(correo, codigo);

        assertTrue(resultado);
    }
}
