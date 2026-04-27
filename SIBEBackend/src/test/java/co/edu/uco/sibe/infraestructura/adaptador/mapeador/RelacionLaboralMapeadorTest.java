package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.RelacionLaboralDTO;
import co.edu.uco.sibe.dominio.modelo.RelacionLaboral;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.RelacionLaboralEntidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RelacionLaboralMapeadorTest {

    private RelacionLaboralMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new RelacionLaboralMapeador();
    }

    @Test
    void deberiaConstruirModeloDesdeEntidad() {
        UUID id = UUID.randomUUID();
        RelacionLaboralEntidad entidad = new RelacionLaboralEntidad(id, "RL-001", "Planta");

        RelacionLaboral modelo = mapeador.construirModelo(entidad);

        assertEquals(id, modelo.getIdentificador());
        assertEquals("RL-001", modelo.getCodigo());
        assertEquals("Planta", modelo.getDescripcion());
    }

    @Test
    void deberiaConstruirEntidadDesdeModelo() {
        UUID id = UUID.randomUUID();
        RelacionLaboral modelo = RelacionLaboral.construir(id, "RL-002", "Contratista");

        RelacionLaboralEntidad entidad = mapeador.construirEntidad(modelo);

        assertEquals(id, entidad.getIdentificador());
        assertEquals("RL-002", entidad.getCodigo());
        assertEquals("Contratista", entidad.getDescripcion());
    }

    @Test
    void deberiaConstruirDTODesdeEntidad() {
        UUID id = UUID.randomUUID();
        RelacionLaboralEntidad entidad = new RelacionLaboralEntidad(id, "RL-003", "Temporal");

        RelacionLaboralDTO dto = mapeador.construirDTO(entidad);

        assertEquals(id.toString(), dto.getIdentificador());
        assertEquals("RL-003", dto.getCodigo());
        assertEquals("Temporal", dto.getDescripcion());
    }
}
