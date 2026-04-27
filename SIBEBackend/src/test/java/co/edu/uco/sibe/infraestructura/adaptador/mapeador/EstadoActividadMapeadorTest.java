package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.EstadoActividadDTO;
import co.edu.uco.sibe.dominio.modelo.EstadoActividad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.EstadoActividadEntidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EstadoActividadMapeadorTest {

    private EstadoActividadMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new EstadoActividadMapeador();
    }

    @Test
    void deberiaConstruirEntidadDesdeModelo() {
        UUID id = UUID.randomUUID();
        EstadoActividad modelo = EstadoActividad.construir(id, "Pendiente");

        EstadoActividadEntidad entidad = mapeador.construirEntidad(modelo);

        assertEquals(id, entidad.getIdentificador());
        assertEquals("Pendiente", entidad.getNombre());
    }

    @Test
    void deberiaConstruirModeloDesdeEntidad() {
        UUID id = UUID.randomUUID();
        EstadoActividadEntidad entidad = new EstadoActividadEntidad(id, "Completada");

        EstadoActividad modelo = mapeador.construirModelo(entidad);

        assertEquals(id, modelo.getIdentificador());
        assertEquals("Completada", modelo.getNombre());
    }

    @Test
    void deberiaConstruirDTODesdeEntidad() {
        UUID id = UUID.randomUUID();
        EstadoActividadEntidad entidad = new EstadoActividadEntidad(id, "En progreso");

        EstadoActividadDTO dto = mapeador.construirDTO(entidad);

        assertEquals(id.toString(), dto.getIdentificador());
        assertEquals("En progreso", dto.getNombre());
    }
}
