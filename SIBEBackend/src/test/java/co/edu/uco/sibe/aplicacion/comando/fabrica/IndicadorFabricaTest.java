package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.aplicacion.comando.IndicadorComando;
import co.edu.uco.sibe.dominio.modelo.*;
import co.edu.uco.sibe.dominio.puerto.consulta.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IndicadorFabricaTest {

    @Mock
    private IndicadorRepositorioConsulta indicadorRepositorioConsulta;
    @Mock
    private TipoIndicadorRepositorioConsulta tipoIndicadorRepositorioConsulta;
    @Mock
    private TemporalidadRepositorioConsulta temporalidadRepositorioConsulta;
    @Mock
    private ProyectoRepositorioConsulta proyectoRepositorioConsulta;
    @Mock
    private PublicoInteresRepositorioConsulta publicoInteresRepositorioConsulta;
    @Mock
    private PublicoInteresFabrica publicoInteresFabrica;

    private IndicadorFabrica fabrica;

    @BeforeEach
    void setUp() {
        fabrica = new IndicadorFabrica(indicadorRepositorioConsulta, tipoIndicadorRepositorioConsulta, temporalidadRepositorioConsulta, proyectoRepositorioConsulta, publicoInteresRepositorioConsulta, publicoInteresFabrica);
    }

    @Test
    void deberiaConstruirIndicadorDesdeComando() {
        UUID tipoIndicadorId = UUID.randomUUID();
        UUID temporalidadId = UUID.randomUUID();
        UUID proyectoId = UUID.randomUUID();
        UUID publicoInteresId = UUID.randomUUID();

        TipoIndicador tipoIndicador = TipoIndicador.construir(tipoIndicadorId, "Cuantitativo", "Resultado");
        Temporalidad temporalidad = Temporalidad.construir(temporalidadId, "Semestral");
        Proyecto proyecto = Proyecto.construir();
        List<PublicoInteres> publicosInteres = new ArrayList<>();

        when(indicadorRepositorioConsulta.consultarPorIdentificador(any())).thenReturn(null);
        when(tipoIndicadorRepositorioConsulta.consultarPorIdentificador(any())).thenReturn(tipoIndicador);
        when(temporalidadRepositorioConsulta.consultarPorIdentificador(any())).thenReturn(temporalidad);
        when(proyectoRepositorioConsulta.consultarPorIdentificador(any())).thenReturn(proyecto);
        when(publicoInteresRepositorioConsulta.consultarTodosPorIdentificadores(any())).thenReturn(publicosInteres);

        IndicadorComando comando = new IndicadorComando(
                "Indicador Test",
                tipoIndicadorId.toString(),
                temporalidadId.toString(),
                proyectoId.toString(),
                List.of(publicoInteresId.toString())
        );

        Indicador resultado = fabrica.construir(comando);

        assertNotNull(resultado.getIdentificador());
        assertEquals("Indicador Test", resultado.getNombre());
        assertEquals(tipoIndicador, resultado.getTipoIndicador());
        assertEquals(temporalidad, resultado.getTemporalidad());
        assertEquals(proyecto, resultado.getProyecto());
    }

    @Test
    void deberiaConstruirActualizarIndicador() {
        UUID id = UUID.randomUUID();
        UUID tipoIndicadorId = UUID.randomUUID();
        UUID temporalidadId = UUID.randomUUID();
        UUID proyectoId = UUID.randomUUID();
        UUID publicoInteresId = UUID.randomUUID();

        TipoIndicador tipoIndicador = TipoIndicador.construir(tipoIndicadorId, "Cualitativo", "Gestion");
        Temporalidad temporalidad = Temporalidad.construir(temporalidadId, "Anual");
        Proyecto proyecto = Proyecto.construir();
        List<PublicoInteres> publicosInteres = new ArrayList<>();

        when(tipoIndicadorRepositorioConsulta.consultarPorIdentificador(any())).thenReturn(tipoIndicador);
        when(temporalidadRepositorioConsulta.consultarPorIdentificador(any())).thenReturn(temporalidad);
        when(proyectoRepositorioConsulta.consultarPorIdentificador(any())).thenReturn(proyecto);
        when(publicoInteresRepositorioConsulta.consultarTodosPorIdentificadores(any())).thenReturn(publicosInteres);

        IndicadorComando comando = new IndicadorComando(
                "Indicador Modificado",
                tipoIndicadorId.toString(),
                temporalidadId.toString(),
                proyectoId.toString(),
                List.of(publicoInteresId.toString())
        );

        Indicador resultado = fabrica.construirActualizar(comando, id);

        assertEquals(id, resultado.getIdentificador());
        assertEquals("Indicador Modificado", resultado.getNombre());
        assertEquals(tipoIndicador, resultado.getTipoIndicador());
    }
}
