package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.TipoIndicadorDTO;
import co.edu.uco.sibe.dominio.modelo.TipoIndicador;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.TipoIndicadorEntidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TipoIndicadorMapeadorTest {

    private TipoIndicadorMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new TipoIndicadorMapeador();
    }

    @Test
    void deberiaConstruirModelo() {
        UUID id = UUID.randomUUID();
        TipoIndicadorEntidad entidad = new TipoIndicadorEntidad(id, "Eficacia", "Resultado");
        TipoIndicador modelo = mapeador.construirModelo(entidad);

        assertEquals(id, modelo.getIdentificador());
        assertEquals("Eficacia", modelo.getNaturaleza());
    }

    @Test
    void deberiaConstruirEntidad() {
        UUID id = UUID.randomUUID();
        TipoIndicador modelo = TipoIndicador.construir(id, "Eficacia", "Resultado");
        TipoIndicadorEntidad entidad = mapeador.construirEntidad(modelo);

        assertEquals(id, entidad.getIdentificador());
    }

    @Test
    void deberiaConstruirDTOs() {
        TipoIndicadorEntidad e1 = new TipoIndicadorEntidad(UUID.randomUUID(), "Eficacia", "Resultado");
        List<TipoIndicadorDTO> dtos = mapeador.construirDTOs(List.of(e1));
        assertEquals(1, dtos.size());
    }
}
