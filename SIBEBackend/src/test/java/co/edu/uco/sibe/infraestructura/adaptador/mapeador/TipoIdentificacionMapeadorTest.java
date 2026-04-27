package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.TipoIdentificacionDTO;
import co.edu.uco.sibe.dominio.modelo.TipoIdentificacion;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.TipoIdentificacionEntidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TipoIdentificacionMapeadorTest {

    private TipoIdentificacionMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new TipoIdentificacionMapeador();
    }

    @Test
    void deberiaConstruirModelo() {
        UUID id = UUID.randomUUID();
        TipoIdentificacionEntidad entidad = new TipoIdentificacionEntidad(id, "CC", "Cedula");
        TipoIdentificacion modelo = mapeador.construirModelo(entidad);

        assertEquals(id, modelo.getIdentificador());
        assertEquals("CC", modelo.getSigla());
        assertEquals("Cedula", modelo.getDescripcion());
    }

    @Test
    void deberiaConstruirEntidad() {
        UUID id = UUID.randomUUID();
        TipoIdentificacion modelo = TipoIdentificacion.construir(id, "CC", "Cedula");
        TipoIdentificacionEntidad entidad = mapeador.construirEntidad(modelo);

        assertEquals(id, entidad.getIdentificador());
    }

    @Test
    void deberiaConstruirDTO() {
        UUID id = UUID.randomUUID();
        TipoIdentificacionEntidad entidad = new TipoIdentificacionEntidad(id, "CC", "Cedula");
        TipoIdentificacionDTO dto = mapeador.construirDTO(entidad);

        assertEquals(id.toString(), dto.getIdentificador());
    }

    @Test
    void deberiaConstruirDTOs() {
        TipoIdentificacionEntidad e1 = new TipoIdentificacionEntidad(UUID.randomUUID(), "CC", "Cedula");
        List<TipoIdentificacionDTO> dtos = mapeador.construirDTOs(List.of(e1));
        assertEquals(1, dtos.size());
    }

    @Test
    void deberiaModificarEntidad() {
        UUID id = UUID.randomUUID();
        TipoIdentificacion modelo = TipoIdentificacion.construir(id, "TI", "Tarjeta Identidad");
        co.edu.uco.sibe.infraestructura.adaptador.entidad.IdentificacionTipoIdentificacionEntidad entidad =
                new co.edu.uco.sibe.infraestructura.adaptador.entidad.IdentificacionTipoIdentificacionEntidad();

        mapeador.modificarEntidad(entidad, modelo);

        assertNotNull(entidad.getTipoIdentificacion());
        assertEquals(id, entidad.getTipoIdentificacion().getIdentificador());
        assertEquals("TI", entidad.getTipoIdentificacion().getSigla());
    }
}
