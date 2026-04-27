package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.TemporalidadDTO;
import co.edu.uco.sibe.dominio.modelo.Temporalidad;
import co.edu.uco.sibe.infraestructura.adaptador.dao.IndicadorTemporalidadDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.IndicadorTemporalidadEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.TemporalidadEntidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IndicadorTemporalidadMapeadorTest {

    @Mock
    private TemporalidadMapeador temporalidadMapeador;

    @Mock
    private IndicadorTemporalidadDAO indicadorTemporalidadDAO;

    private IndicadorTemporalidadMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new IndicadorTemporalidadMapeador(temporalidadMapeador, indicadorTemporalidadDAO);
    }

    @Test
    void deberiaConstruirEntidadDesdeTemporalidad() {
        UUID id = UUID.randomUUID();
        Temporalidad temporalidad = Temporalidad.construir(id, "Mensual");
        TemporalidadEntidad tempEntidad = new TemporalidadEntidad(id, "Mensual");

        when(temporalidadMapeador.construirEntidad(temporalidad)).thenReturn(tempEntidad);
        when(indicadorTemporalidadDAO.findById(any(UUID.class))).thenReturn(Optional.empty());

        IndicadorTemporalidadEntidad entidad = mapeador.construirEntidad(temporalidad);

        assertNotNull(entidad);
        assertEquals(tempEntidad, entidad.getTemporalidad());
    }

    @Test
    void deberiaConstruirModeloDesdeEntidad() {
        UUID id = UUID.randomUUID();
        TemporalidadEntidad tempEntidad = new TemporalidadEntidad(id, "Semestral");
        IndicadorTemporalidadEntidad entidad = new IndicadorTemporalidadEntidad(UUID.randomUUID(), tempEntidad);
        Temporalidad temporalidad = Temporalidad.construir(id, "Semestral");

        when(temporalidadMapeador.construirModelo(tempEntidad)).thenReturn(temporalidad);

        Temporalidad resultado = mapeador.construirModelo(entidad);

        assertEquals(id, resultado.getIdentificador());
        assertEquals("Semestral", resultado.getNombre());
    }

    @Test
    void deberiaActualizarEntidad() {
        UUID id = UUID.randomUUID();
        TemporalidadEntidad tempEntidad = new TemporalidadEntidad(id, "Original");
        IndicadorTemporalidadEntidad entidad = new IndicadorTemporalidadEntidad(UUID.randomUUID(), tempEntidad);
        Temporalidad nuevaTemp = Temporalidad.construir(UUID.randomUUID(), "Anual");
        TemporalidadEntidad nuevaEntidad = new TemporalidadEntidad(UUID.randomUUID(), "Anual");

        when(temporalidadMapeador.construirEntidad(nuevaTemp)).thenReturn(nuevaEntidad);

        mapeador.actualizarEntidad(entidad, nuevaTemp);

        assertEquals(nuevaEntidad, entidad.getTemporalidad());
    }

    @Test
    void deberiaConstruirDTODesdeEntidad() {
        UUID id = UUID.randomUUID();
        TemporalidadEntidad tempEntidad = new TemporalidadEntidad(id, "Trimestral");
        IndicadorTemporalidadEntidad entidad = new IndicadorTemporalidadEntidad(UUID.randomUUID(), tempEntidad);
        TemporalidadDTO tempDTO = new TemporalidadDTO(id.toString(), "Trimestral");

        when(temporalidadMapeador.construirDTO(tempEntidad)).thenReturn(tempDTO);

        TemporalidadDTO resultado = mapeador.construirDTO(entidad);

        assertEquals(id.toString(), resultado.getIdentificador());
        assertEquals("Trimestral", resultado.getNombre());
    }
}
