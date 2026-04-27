package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.AccionDTO;
import co.edu.uco.sibe.dominio.modelo.Accion;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.AccionEntidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccionMapeadorTest {

    private AccionMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new AccionMapeador();
    }

    @Test
    void deberiaConstruirEntidadDesdeModelo() {
        UUID id = UUID.randomUUID();
        Accion accion = Accion.construir(id, "Detalle test", "Objetivo test");
        AccionEntidad entidad = mapeador.construirEntidad(accion);

        assertEquals(id, entidad.getIdentificador());
        assertEquals("Detalle test", entidad.getDetalle());
        assertEquals("Objetivo test", entidad.getObjetivo());
    }

    @Test
    void deberiaConstruirModeloDesdeEntidad() {
        UUID id = UUID.randomUUID();
        AccionEntidad entidad = new AccionEntidad(id, "Detalle", "Objetivo");
        Accion accion = mapeador.construriModelo(entidad);

        assertEquals(id, accion.getIdentificador());
        assertEquals("Detalle", accion.getDetalle());
        assertEquals("Objetivo", accion.getObjetivo());
    }

    @Test
    void deberiaConstruirDTODesdeEntidad() {
        UUID id = UUID.randomUUID();
        AccionEntidad entidad = new AccionEntidad(id, "Detalle", "Objetivo");
        AccionDTO dto = mapeador.construirDTO(entidad);

        assertEquals(id.toString(), dto.getIdentificador());
        assertEquals("Detalle", dto.getDetalle());
        assertEquals("Objetivo", dto.getObjetivo());
    }

    @Test
    void deberiaConstruirListaDTOs() {
        AccionEntidad e1 = new AccionEntidad(UUID.randomUUID(), "D1", "O1");
        AccionEntidad e2 = new AccionEntidad(UUID.randomUUID(), "D2", "O2");

        List<AccionDTO> dtos = mapeador.construirDTOs(List.of(e1, e2));

        assertEquals(2, dtos.size());
    }

    @Test
    void deberiaConstruirListaModelos() {
        AccionEntidad e1 = new AccionEntidad(UUID.randomUUID(), "D1", "O1");

        List<Accion> modelos = mapeador.construirModelos(List.of(e1));

        assertEquals(1, modelos.size());
    }

    @Test
    void deberiaActualizarEntidad() {
        AccionEntidad entidad = new AccionEntidad(UUID.randomUUID(), "Original", "Original");
        Accion accion = Accion.construir(UUID.randomUUID(), "Nuevo Detalle", "Nuevo Objetivo");

        mapeador.actualizarEntidad(entidad, accion);

        assertEquals("Nuevo Detalle", entidad.getDetalle());
        assertEquals("Nuevo Objetivo", entidad.getObjetivo());
    }
}
