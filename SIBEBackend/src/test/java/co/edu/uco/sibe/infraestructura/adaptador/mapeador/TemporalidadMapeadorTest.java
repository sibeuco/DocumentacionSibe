package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.TemporalidadDTO;
import co.edu.uco.sibe.dominio.modelo.Temporalidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.TemporalidadEntidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TemporalidadMapeadorTest {

    private TemporalidadMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new TemporalidadMapeador();
    }

    @Test
    void deberiaConstruirModelo() {
        UUID id = UUID.randomUUID();
        TemporalidadEntidad entidad = new TemporalidadEntidad(id, "Semestral");
        Temporalidad modelo = mapeador.construirModelo(entidad);

        assertEquals(id, modelo.getIdentificador());
        assertEquals("Semestral", modelo.getNombre());
    }

    @Test
    void deberiaConstruirEntidad() {
        UUID id = UUID.randomUUID();
        Temporalidad modelo = Temporalidad.construir(id, "Semestral");
        TemporalidadEntidad entidad = mapeador.construirEntidad(modelo);

        assertEquals(id, entidad.getIdentificador());
    }

    @Test
    void deberiaConstruirDTOs() {
        TemporalidadEntidad e1 = new TemporalidadEntidad(UUID.randomUUID(), "Semestral");
        List<TemporalidadDTO> dtos = mapeador.construirDTOs(List.of(e1));
        assertEquals(1, dtos.size());
    }
}
