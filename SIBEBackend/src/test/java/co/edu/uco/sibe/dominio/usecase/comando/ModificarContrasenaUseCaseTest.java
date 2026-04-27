package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.Persona;
import co.edu.uco.sibe.dominio.puerto.comando.PersonaRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.PersonaRepositorioConsulta;
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
class ModificarContrasenaUseCaseTest {

    @Mock
    private PersonaRepositorioComando personaRepositorioComando;
    @Mock
    private PersonaRepositorioConsulta personaRepositorioConsulta;

    private ModificarContrasenaUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ModificarContrasenaUseCase(personaRepositorioComando, personaRepositorioConsulta);
    }

    @Test
    void deberiaModificarContrasenaExitosamente() {
        UUID identificador = UUID.randomUUID();
        String nuevaContrasena = "NuevaClave123!";

        when(personaRepositorioConsulta.consultarPersonaPorIdentificador(identificador))
                .thenReturn(mock(Persona.class));
        when(personaRepositorioComando.modificarClave(nuevaContrasena, identificador))
                .thenReturn(identificador);

        UUID resultado = useCase.ejecutar(nuevaContrasena, identificador);

        assertEquals(identificador, resultado);
    }

    @Test
    void deberiaLanzarExcepcionCuandoPersonaNoExiste() {
        UUID identificador = UUID.randomUUID();

        when(personaRepositorioConsulta.consultarPersonaPorIdentificador(identificador)).thenReturn(null);

        assertThrows(ValorInvalidoExcepcion.class,
                () -> useCase.ejecutar("clave", identificador));

        verify(personaRepositorioComando, never()).modificarClave(any(), any());
    }
}
