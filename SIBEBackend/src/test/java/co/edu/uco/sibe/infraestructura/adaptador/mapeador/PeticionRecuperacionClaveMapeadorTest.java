package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.PeticionRecuperacionClaveDTO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.PeticionRecuperacionClaveDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.PeticionRecuperacionClaveEntidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PeticionRecuperacionClaveMapeadorTest {

    @Mock
    private PeticionRecuperacionClaveDAO peticionRecuperacionClaveDAO;

    private PeticionRecuperacionClaveMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new PeticionRecuperacionClaveMapeador(peticionRecuperacionClaveDAO);
    }

    @Test
    void deberiaConstruirEntidad() {
        LocalDateTime fecha = LocalDateTime.now();
        when(peticionRecuperacionClaveDAO.findById(any(UUID.class))).thenReturn(Optional.empty());

        PeticionRecuperacionClaveEntidad entidad = mapeador.construirEntidad("CODIGO123", "test@email.com", fecha);

        assertNotNull(entidad);
        assertEquals("CODIGO123", entidad.getCodigo());
        assertEquals("test@email.com", entidad.getCorreo());
        assertEquals(fecha, entidad.getFecha());
    }

    @Test
    void deberiaActualizarEntidad() {
        UUID id = UUID.randomUUID();
        LocalDateTime fechaOriginal = LocalDateTime.now().minusDays(1);
        LocalDateTime fechaNueva = LocalDateTime.now();
        PeticionRecuperacionClaveEntidad entidad = new PeticionRecuperacionClaveEntidad(id, "VIEJO", "test@email.com", fechaOriginal);

        mapeador.actualizarEntidad(entidad, "NUEVO", fechaNueva);

        assertEquals("NUEVO", entidad.getCodigo());
        assertEquals(fechaNueva, entidad.getFecha());
    }

    @Test
    void deberiaConstruirDTODesdeEntidad() {
        UUID id = UUID.randomUUID();
        LocalDateTime fecha = LocalDateTime.now();
        PeticionRecuperacionClaveEntidad entidad = new PeticionRecuperacionClaveEntidad(id, "CODE", "dto@email.com", fecha);

        PeticionRecuperacionClaveDTO dto = mapeador.construirDTO(entidad);

        assertEquals(id.toString(), dto.getIdentificador());
        assertEquals("dto@email.com", dto.getCorreo());
        assertEquals("CODE", dto.getCodigo());
        assertEquals(fecha.toString(), dto.getFecha());
    }
}
