package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.PersonaDTO;
import co.edu.uco.sibe.dominio.dto.IdentificacionDTO;
import co.edu.uco.sibe.dominio.modelo.Identificacion;
import co.edu.uco.sibe.dominio.modelo.Persona;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.IdentificacionEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.PersonaEntidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonaMapeadorTest {

    @Mock
    private IdentificacionMapeador identificacionMapeador;

    private PersonaMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new PersonaMapeador(identificacionMapeador);
    }

    @Test
    void deberiaConstruirDTODesdeEntidad() {
        UUID id = UUID.randomUUID();
        IdentificacionEntidad idEntidad = mock(IdentificacionEntidad.class);
        PersonaEntidad entidad = new PersonaEntidad(id, "Juan", "Pérez", "juan@email.com", idEntidad);
        IdentificacionDTO idDTO = mock(IdentificacionDTO.class);

        when(identificacionMapeador.construirDTO(idEntidad)).thenReturn(idDTO);

        PersonaDTO dto = mapeador.construirDTO(entidad);

        assertEquals(id.toString(), dto.getIdentificador());
        assertEquals("Juan", dto.getNombres());
        assertEquals("Pérez", dto.getApellidos());
        assertEquals("juan@email.com", dto.getCorreo());
    }

    @Test
    void deberiaConstruirEntidadDesdeModelo() {
        UUID id = UUID.randomUUID();
        Identificacion identificacion = mock(Identificacion.class);
        Persona persona = Persona.construir(id, "María", "López", "maria@email.com", identificacion);
        IdentificacionEntidad idEntidad = mock(IdentificacionEntidad.class);

        when(identificacionMapeador.construirEntidad(identificacion)).thenReturn(idEntidad);

        PersonaEntidad entidad = mapeador.construirEntidad(persona);

        assertEquals(id, entidad.getIdentificador());
        assertEquals("María", entidad.getNombres());
        assertEquals("López", entidad.getApellidos());
        assertEquals("maria@email.com", entidad.getCorreo());
    }

    @Test
    void deberiaConstruirModeloDesdeEntidad() {
        UUID id = UUID.randomUUID();
        IdentificacionEntidad idEntidad = mock(IdentificacionEntidad.class);
        PersonaEntidad entidad = new PersonaEntidad(id, "Carlos", "Gómez", "carlos@email.com", idEntidad);
        Identificacion identificacion = mock(Identificacion.class);

        when(identificacionMapeador.construirModelo(idEntidad)).thenReturn(identificacion);

        Persona modelo = mapeador.construirModelo(entidad);

        assertEquals(id, modelo.getIdentificador());
        assertEquals("Carlos", modelo.getNombres());
        assertEquals("Gómez", modelo.getApellidos());
    }

    @Test
    void deberiaModificarEntidad() {
        UUID id = UUID.randomUUID();
        IdentificacionEntidad idEntidad = mock(IdentificacionEntidad.class);
        PersonaEntidad entidad = new PersonaEntidad(id, "Original", "Original", "original@email.com", idEntidad);
        Identificacion identificacion = mock(Identificacion.class);
        Persona persona = Persona.construir(UUID.randomUUID(), "Nuevo", "Apellido", "nuevo@email.com", identificacion);

        mapeador.modificarEntidad(entidad, persona);

        assertEquals("Nuevo", entidad.getNombres());
        assertEquals("Apellido", entidad.getApellidos());
        assertEquals("nuevo@email.com", entidad.getCorreo());
        verify(identificacionMapeador).modificarEntidad(eq(idEntidad), eq(identificacion));
    }
}
