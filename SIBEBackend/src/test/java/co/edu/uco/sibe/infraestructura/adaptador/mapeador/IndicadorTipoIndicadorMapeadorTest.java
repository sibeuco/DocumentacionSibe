package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.TipoIndicadorDTO;
import co.edu.uco.sibe.dominio.modelo.TipoIndicador;
import co.edu.uco.sibe.infraestructura.adaptador.dao.IndicadorTipoIndicadorDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.IndicadorTipoIndicadorEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.TipoIndicadorEntidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IndicadorTipoIndicadorMapeadorTest {

    @Mock
    private TipoIndicadorMapeador tipoIndicadorMapeador;

    @Mock
    private IndicadorTipoIndicadorDAO indicadorTipoIndicadorDAO;

    private IndicadorTipoIndicadorMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new IndicadorTipoIndicadorMapeador(tipoIndicadorMapeador, indicadorTipoIndicadorDAO);
    }

    @Test
    void deberiaConstruirEntidadDesdeTipoIndicador() {
        UUID id = UUID.randomUUID();
        TipoIndicador tipoIndicador = TipoIndicador.construir(id, "Eficacia", "Resultado");
        TipoIndicadorEntidad tipoEntidad = new TipoIndicadorEntidad(id, "Eficacia", "Resultado");

        when(tipoIndicadorMapeador.construirEntidad(tipoIndicador)).thenReturn(tipoEntidad);
        when(indicadorTipoIndicadorDAO.findById(any(UUID.class))).thenReturn(Optional.empty());

        IndicadorTipoIndicadorEntidad entidad = mapeador.construirEntidad(tipoIndicador);

        assertNotNull(entidad);
        assertEquals(tipoEntidad, entidad.getTipoIndicador());
    }

    @Test
    void deberiaConstruirModeloDesdeEntidad() {
        UUID id = UUID.randomUUID();
        TipoIndicadorEntidad tipoEntidad = new TipoIndicadorEntidad(id, "Eficacia", "Resultado");
        IndicadorTipoIndicadorEntidad entidad = new IndicadorTipoIndicadorEntidad(UUID.randomUUID(), tipoEntidad);
        TipoIndicador tipoIndicador = TipoIndicador.construir(id, "Eficacia", "Resultado");

        when(tipoIndicadorMapeador.construirModelo(tipoEntidad)).thenReturn(tipoIndicador);

        TipoIndicador resultado = mapeador.construirModelo(entidad);

        assertEquals(id, resultado.getIdentificador());
        assertEquals("Eficacia", resultado.getNaturaleza());
    }

    @Test
    void deberiaActualizarEntidad() {
        UUID id = UUID.randomUUID();
        TipoIndicadorEntidad tipoEntidad = new TipoIndicadorEntidad(id, "Original", "Original");
        IndicadorTipoIndicadorEntidad entidad = new IndicadorTipoIndicadorEntidad(UUID.randomUUID(), tipoEntidad);
        TipoIndicador nuevoTipo = TipoIndicador.construir(UUID.randomUUID(), "Nuevo", "NuevoR");
        TipoIndicadorEntidad nuevaEntidad = new TipoIndicadorEntidad(UUID.randomUUID(), "Nuevo", "NuevoR");

        when(tipoIndicadorMapeador.construirEntidad(nuevoTipo)).thenReturn(nuevaEntidad);

        mapeador.actualizarEntidad(entidad, nuevoTipo);

        assertEquals(nuevaEntidad, entidad.getTipoIndicador());
    }

    @Test
    void deberiaConstruirDTODesdeEntidad() {
        UUID id = UUID.randomUUID();
        TipoIndicadorEntidad tipoEntidad = new TipoIndicadorEntidad(id, "Eficacia", "Resultado");
        IndicadorTipoIndicadorEntidad entidad = new IndicadorTipoIndicadorEntidad(UUID.randomUUID(), tipoEntidad);
        TipoIndicadorDTO tipoDTO = new TipoIndicadorDTO(id.toString(), "Eficacia", "Resultado");

        when(tipoIndicadorMapeador.construirDTO(tipoEntidad)).thenReturn(tipoDTO);

        TipoIndicadorDTO resultado = mapeador.construirDTO(entidad);

        assertEquals(id.toString(), resultado.getIdentificador());
        assertEquals("Eficacia", resultado.getNaturalezaIndicador());
    }
}
