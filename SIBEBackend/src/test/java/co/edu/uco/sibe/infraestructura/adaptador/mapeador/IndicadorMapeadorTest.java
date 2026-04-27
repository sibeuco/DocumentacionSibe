package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.IndicadorDTO;
import co.edu.uco.sibe.dominio.dto.ProyectoDTO;
import co.edu.uco.sibe.dominio.dto.PublicoInteresDTO;
import co.edu.uco.sibe.dominio.dto.TemporalidadDTO;
import co.edu.uco.sibe.dominio.dto.TipoIndicadorDTO;
import co.edu.uco.sibe.dominio.modelo.*;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IndicadorMapeadorTest {

    @Mock
    private IndicadorTipoIndicadorMapeador indicadorTipoIndicadorMapeador;

    @Mock
    private IndicadorTemporalidadMapeador indicadorTemporalidadMapeador;

    @Mock
    private IndicadorProyectoMapeador indicadorProyectoMapeador;

    @Mock
    private IndicadorPublicoInteresMapeador indicadorPublicoInteresMapeador;

    private IndicadorMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new IndicadorMapeador(indicadorTipoIndicadorMapeador, indicadorTemporalidadMapeador, indicadorProyectoMapeador, indicadorPublicoInteresMapeador);
    }

    @Test
    void deberiaConstruirEntidadDesdeModelo() {
        UUID id = UUID.randomUUID();
        TipoIndicador tipoIndicador = TipoIndicador.construir(UUID.randomUUID(), "Eficacia", "Resultado");
        Temporalidad temporalidad = Temporalidad.construir(UUID.randomUUID(), "Mensual");
        Proyecto proyecto = Proyecto.construir(UUID.randomUUID(), "P-001", "Proyecto", "Obj", List.of());
        Indicador indicador = Indicador.construir(id, "Indicador Test", tipoIndicador, temporalidad, proyecto, List.of());

        IndicadorTipoIndicadorEntidad tipoEntidad = new IndicadorTipoIndicadorEntidad(UUID.randomUUID(), new TipoIndicadorEntidad(UUID.randomUUID(), "Eficacia", "Resultado"));
        IndicadorTemporalidadEntidad tempEntidad = new IndicadorTemporalidadEntidad(UUID.randomUUID(), new TemporalidadEntidad(UUID.randomUUID(), "Mensual"));
        IndicadorProyectoEntidad proyEntidad = new IndicadorProyectoEntidad(UUID.randomUUID(), new ProyectoEntidad(UUID.randomUUID(), "P-001", "Proyecto", "Obj", List.of()));

        when(indicadorTipoIndicadorMapeador.construirEntidad(tipoIndicador)).thenReturn(tipoEntidad);
        when(indicadorTemporalidadMapeador.construirEntidad(temporalidad)).thenReturn(tempEntidad);
        when(indicadorProyectoMapeador.construirEntidad(proyecto)).thenReturn(proyEntidad);
        when(indicadorPublicoInteresMapeador.construirEntidades(anyList())).thenReturn(List.of());

        IndicadorEntidad entidad = mapeador.construirEntidad(indicador);

        assertEquals(id, entidad.getIdentificador());
        assertEquals("Indicador Test", entidad.getNombre());
    }

    @Test
    void deberiaConstruirModeloDesdeEntidad() {
        UUID id = UUID.randomUUID();
        TipoIndicadorEntidad tipoEntidad = new TipoIndicadorEntidad(UUID.randomUUID(), "Eficacia", "Resultado");
        TemporalidadEntidad tempEntidad = new TemporalidadEntidad(UUID.randomUUID(), "Mensual");
        ProyectoEntidad proyEntidad = new ProyectoEntidad(UUID.randomUUID(), "P-001", "Proyecto", "Obj", List.of());
        IndicadorEntidad entidad = new IndicadorEntidad(id, "Indicador",
                new IndicadorTipoIndicadorEntidad(UUID.randomUUID(), tipoEntidad),
                new IndicadorTemporalidadEntidad(UUID.randomUUID(), tempEntidad),
                new IndicadorProyectoEntidad(UUID.randomUUID(), proyEntidad),
                List.of());

        TipoIndicador tipoModelo = TipoIndicador.construir(UUID.randomUUID(), "Eficacia", "Resultado");
        Temporalidad tempModelo = Temporalidad.construir(UUID.randomUUID(), "Mensual");
        Proyecto proyModelo = Proyecto.construir(UUID.randomUUID(), "P-001", "Proyecto", "Obj", List.of());

        when(indicadorTipoIndicadorMapeador.construirModelo(any())).thenReturn(tipoModelo);
        when(indicadorTemporalidadMapeador.construirModelo(any())).thenReturn(tempModelo);
        when(indicadorProyectoMapeador.construirModelo(any())).thenReturn(proyModelo);
        when(indicadorPublicoInteresMapeador.construirModelos(anyList())).thenReturn(List.of());

        Indicador modelo = mapeador.construriModelo(entidad);

        assertEquals(id, modelo.getIdentificador());
        assertEquals("Indicador", modelo.getNombre());
    }

    @Test
    void deberiaActualizarEntidad() {
        UUID id = UUID.randomUUID();
        IndicadorEntidad entidad = new IndicadorEntidad(id, "Original",
                new IndicadorTipoIndicadorEntidad(UUID.randomUUID(), new TipoIndicadorEntidad(UUID.randomUUID(), "T", "R")),
                new IndicadorTemporalidadEntidad(UUID.randomUUID(), new TemporalidadEntidad(UUID.randomUUID(), "M")),
                new IndicadorProyectoEntidad(UUID.randomUUID(), new ProyectoEntidad(UUID.randomUUID(), "P", "N", "O", List.of())),
                List.of());

        TipoIndicador tipoIndicador = TipoIndicador.construir(UUID.randomUUID(), "Nuevo Tipo", "NuevoR");
        Temporalidad temporalidad = Temporalidad.construir(UUID.randomUUID(), "Anual");
        Proyecto proyecto = Proyecto.construir(UUID.randomUUID(), "P-002", "Nuevo", "Obj", List.of());
        Indicador indicador = Indicador.construir(UUID.randomUUID(), "Nuevo Indicador", tipoIndicador, temporalidad, proyecto, List.of());

        mapeador.actualizarEntidad(entidad, indicador);

        assertEquals("Nuevo Indicador", entidad.getNombre());
        verify(indicadorTipoIndicadorMapeador).actualizarEntidad(any(), eq(tipoIndicador));
        verify(indicadorTemporalidadMapeador).actualizarEntidad(any(), eq(temporalidad));
        verify(indicadorProyectoMapeador).actualizarEntidad(any(), eq(proyecto));
    }

    @Test
    void deberiaConstruirDTODesdeEntidad() {
        UUID id = UUID.randomUUID();
        IndicadorEntidad entidad = new IndicadorEntidad(id, "Indicador DTO",
                new IndicadorTipoIndicadorEntidad(UUID.randomUUID(), new TipoIndicadorEntidad(UUID.randomUUID(), "T", "R")),
                new IndicadorTemporalidadEntidad(UUID.randomUUID(), new TemporalidadEntidad(UUID.randomUUID(), "M")),
                new IndicadorProyectoEntidad(UUID.randomUUID(), new ProyectoEntidad(UUID.randomUUID(), "P", "N", "O", List.of())),
                List.of());

        when(indicadorTipoIndicadorMapeador.construirDTO(any())).thenReturn(new TipoIndicadorDTO(UUID.randomUUID().toString(), "T", "R"));
        when(indicadorTemporalidadMapeador.construirDTO(any())).thenReturn(new TemporalidadDTO(UUID.randomUUID().toString(), "M"));
        when(indicadorProyectoMapeador.construirDTO(any())).thenReturn(new ProyectoDTO(UUID.randomUUID().toString(), "P", "N", "O", List.of()));
        when(indicadorPublicoInteresMapeador.construirDTOs(anyList())).thenReturn(List.of());

        IndicadorDTO dto = mapeador.construirDTO(entidad);

        assertEquals(id.toString(), dto.getIdentificador());
        assertEquals("Indicador DTO", dto.getNombre());
    }

    @Test
    void deberiaConstruirListaDTOs() {
        UUID id = UUID.randomUUID();
        IndicadorEntidad entidad = new IndicadorEntidad(id, "I1",
                new IndicadorTipoIndicadorEntidad(UUID.randomUUID(), new TipoIndicadorEntidad(UUID.randomUUID(), "T", "R")),
                new IndicadorTemporalidadEntidad(UUID.randomUUID(), new TemporalidadEntidad(UUID.randomUUID(), "M")),
                new IndicadorProyectoEntidad(UUID.randomUUID(), new ProyectoEntidad(UUID.randomUUID(), "P", "N", "O", List.of())),
                List.of());

        when(indicadorTipoIndicadorMapeador.construirDTO(any())).thenReturn(new TipoIndicadorDTO(UUID.randomUUID().toString(), "T", "R"));
        when(indicadorTemporalidadMapeador.construirDTO(any())).thenReturn(new TemporalidadDTO(UUID.randomUUID().toString(), "M"));
        when(indicadorProyectoMapeador.construirDTO(any())).thenReturn(new ProyectoDTO(UUID.randomUUID().toString(), "P", "N", "O", List.of()));
        when(indicadorPublicoInteresMapeador.construirDTOs(anyList())).thenReturn(List.of());

        List<IndicadorDTO> dtos = mapeador.construirDTOs(List.of(entidad));

        assertEquals(1, dtos.size());
    }
}
