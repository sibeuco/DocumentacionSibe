package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.Indicador;
import co.edu.uco.sibe.dominio.modelo.Proyecto;
import co.edu.uco.sibe.dominio.modelo.Temporalidad;
import co.edu.uco.sibe.dominio.modelo.TipoIndicador;
import co.edu.uco.sibe.dominio.puerto.comando.IndicadorRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.IndicadorRepositorioConsulta;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorDuplicadoExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.UUID;

import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.INDICADOR_NO_EXISTENTE;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.NOMBRE_INDICADOR_EXISTENTE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModificarIndicadorUseCaseTest {

    @Mock
    private IndicadorRepositorioComando indicadorRepositorioComando;
    @Mock
    private IndicadorRepositorioConsulta indicadorRepositorioConsulta;

    private ModificarIndicadorUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ModificarIndicadorUseCase(indicadorRepositorioComando, indicadorRepositorioConsulta);
    }

    @Test
    void deberiaModificarIndicadorExitosamente() {
        UUID identificador = UUID.randomUUID();
        Indicador indicador = Indicador.construir(UUID.randomUUID(), "Indicador modificado test", TipoIndicador.construir(), Temporalidad.construir(), Proyecto.construir(), new ArrayList<>());

        when(indicadorRepositorioConsulta.consultarPorIdentificador(identificador)).thenReturn(Indicador.construir());
        when(indicadorRepositorioConsulta.consultarPorNombre(indicador.getNombre())).thenReturn(null);
        when(indicadorRepositorioComando.guardar(indicador)).thenReturn(identificador);

        UUID resultado = useCase.ejecutar(indicador, identificador);

        assertEquals(identificador, resultado);
    }

    @Test
    void deberiaLanzarExcepcionCuandoIndicadorNoExiste() {
        UUID identificador = UUID.randomUUID();
        Indicador indicador = Indicador.construir(UUID.randomUUID(), "Indicador no existente test", TipoIndicador.construir(), Temporalidad.construir(), Proyecto.construir(), new ArrayList<>());

        when(indicadorRepositorioConsulta.consultarPorIdentificador(identificador)).thenReturn(null);

        ValorDuplicadoExcepcion excepcion = assertThrows(ValorDuplicadoExcepcion.class,
                () -> useCase.ejecutar(indicador, identificador));

        assertEquals(INDICADOR_NO_EXISTENTE, excepcion.getMessage());
    }

    @Test
    void deberiaLanzarExcepcionCuandoNombreYaExisteEnOtroIndicador() {
        UUID identificador = UUID.randomUUID();
        UUID otroIdentificador = UUID.randomUUID();
        Indicador indicador = Indicador.construir(UUID.randomUUID(), "Nombre duplicado indicador", TipoIndicador.construir(), Temporalidad.construir(), Proyecto.construir(), new ArrayList<>());
        Indicador indicadorExistente = mock(Indicador.class);

        when(indicadorExistente.getIdentificador()).thenReturn(otroIdentificador);

        when(indicadorRepositorioConsulta.consultarPorIdentificador(identificador)).thenReturn(Indicador.construir());
        when(indicadorRepositorioConsulta.consultarPorNombre(indicador.getNombre())).thenReturn(indicadorExistente);

        ValorDuplicadoExcepcion excepcion = assertThrows(ValorDuplicadoExcepcion.class,
                () -> useCase.ejecutar(indicador, identificador));

        assertEquals(NOMBRE_INDICADOR_EXISTENTE, excepcion.getMessage());
    }

    @Test
    void deberiaPermitirMismoNombreSiEsElMismoIndicador() {
        UUID identificador = UUID.randomUUID();
        Indicador indicador = Indicador.construir(UUID.randomUUID(), "Mismo nombre indicador test", TipoIndicador.construir(), Temporalidad.construir(), Proyecto.construir(), new ArrayList<>());
        Indicador indicadorExistente = mock(Indicador.class);

        when(indicadorExistente.getIdentificador()).thenReturn(identificador);

        when(indicadorRepositorioConsulta.consultarPorIdentificador(identificador)).thenReturn(Indicador.construir());
        when(indicadorRepositorioConsulta.consultarPorNombre(indicador.getNombre())).thenReturn(indicadorExistente);
        when(indicadorRepositorioComando.guardar(indicador)).thenReturn(identificador);

        UUID resultado = useCase.ejecutar(indicador, identificador);

        assertEquals(identificador, resultado);
    }
}
