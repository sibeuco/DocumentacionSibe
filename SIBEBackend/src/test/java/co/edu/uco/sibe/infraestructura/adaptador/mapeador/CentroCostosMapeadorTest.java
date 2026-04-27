package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.CentroCostosDTO;
import co.edu.uco.sibe.dominio.modelo.CentroCostos;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.CentroCostosEntidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CentroCostosMapeadorTest {

    private CentroCostosMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new CentroCostosMapeador();
    }

    @Test
    void deberiaConstruirModeloDesdeEntidad() {
        UUID id = UUID.randomUUID();
        CentroCostosEntidad entidad = new CentroCostosEntidad(id, "CC-001", "Centro de costos test");

        CentroCostos modelo = mapeador.construirModelo(entidad);

        assertEquals(id, modelo.getIdentificador());
        assertEquals("CC-001", modelo.getCodigo());
        assertEquals("Centro de costos test", modelo.getDescripcion());
    }

    @Test
    void deberiaConstruirEntidadDesdeModelo() {
        UUID id = UUID.randomUUID();
        CentroCostos modelo = CentroCostos.construir(id, "CC-002", "Otro centro");

        CentroCostosEntidad entidad = mapeador.construirEntidad(modelo);

        assertEquals(id, entidad.getIdentificador());
        assertEquals("CC-002", entidad.getCodigo());
        assertEquals("Otro centro", entidad.getDescripcion());
    }

    @Test
    void deberiaConstruirDTODesdeEntidad() {
        UUID id = UUID.randomUUID();
        CentroCostosEntidad entidad = new CentroCostosEntidad(id, "CC-003", "DTO centro");

        CentroCostosDTO dto = mapeador.construirDTO(entidad);

        assertEquals(id.toString(), dto.getIdentificador());
        assertEquals("CC-003", dto.getCodigo());
        assertEquals("DTO centro", dto.getDescripcion());
    }
}
