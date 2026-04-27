package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.aplicacion.comando.UsuarioComando;
import co.edu.uco.sibe.aplicacion.comando.UsuarioModificacionComando;
import co.edu.uco.sibe.dominio.modelo.Identificacion;
import co.edu.uco.sibe.dominio.modelo.Persona;
import co.edu.uco.sibe.dominio.puerto.consulta.PersonaRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonaFabricaTest {

    @Mock
    private IdentificacionFabrica identificacionFabrica;
    @Mock
    private PersonaRepositorioConsulta personaRepositorioConsulta;

    private PersonaFabrica fabrica;

    @BeforeEach
    void setUp() {
        fabrica = new PersonaFabrica(identificacionFabrica, personaRepositorioConsulta);
    }

    @Test
    void deberiaConstruirPersonaDesdeComando() {
        UUID tipoIdentificacionId = UUID.randomUUID();
        Identificacion identificacion = Identificacion.construir(UUID.randomUUID(), "1234567890", null);

        when(personaRepositorioConsulta.consultarPersonaPorIdentificador(any())).thenReturn(null);
        when(identificacionFabrica.construir(eq("1234567890"), eq(tipoIdentificacionId.toString()))).thenReturn(identificacion);

        UsuarioComando comando = new UsuarioComando(
                tipoIdentificacionId.toString(),
                "1234567890",
                "Juan",
                "Perez",
                "juan.perez@correo.com",
                "clave123",
                UUID.randomUUID().toString(),
                null
        );

        Persona resultado = fabrica.construir(comando);

        assertNotNull(resultado.getIdentificador());
        assertEquals("Juan", resultado.getNombres());
        assertEquals("Perez", resultado.getApellidos());
        assertEquals("juan.perez@correo.com", resultado.getCorreo());
        assertEquals(identificacion, resultado.getIdentificacion());
    }

    @Test
    void deberiaConstruirPersonaParaActualizacion() {
        UUID identificadorPersona = UUID.randomUUID();
        UUID identificacionId = UUID.randomUUID();
        UUID tipoIdentificacionId = UUID.randomUUID();
        Identificacion identificacionExistente = Identificacion.construir(identificacionId, "1234567890", null);
        Persona personaExistente = Persona.construir(identificadorPersona, "Juan", "Perez", "correo@test.com", identificacionExistente);
        Identificacion identificacionActualizada = Identificacion.construir(identificacionId, "9876543210", null);

        when(personaRepositorioConsulta.consultarPersonaPorIdentificador(identificadorPersona)).thenReturn(personaExistente);
        when(identificacionFabrica.construirActualizar(eq("9876543210"), eq(tipoIdentificacionId.toString()), eq(identificacionId))).thenReturn(identificacionActualizada);

        UsuarioModificacionComando comando = new UsuarioModificacionComando(
                tipoIdentificacionId.toString(), "9876543210", "Pedro", "Garcia", "pedro@correo.com", UUID.randomUUID().toString(), null);

        Persona resultado = fabrica.construirActualizar(comando, identificadorPersona);

        assertNotNull(resultado);
        assertEquals("Pedro", resultado.getNombres());
        assertEquals("Garcia", resultado.getApellidos());
        assertEquals(identificacionActualizada, resultado.getIdentificacion());
    }
}
