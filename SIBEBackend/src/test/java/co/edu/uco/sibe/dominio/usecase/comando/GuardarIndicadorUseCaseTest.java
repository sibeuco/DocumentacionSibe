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

import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.NOMBRE_INDICADOR_EXISTENTE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuardarIndicadorUseCaseTest {

    @Mock
    private IndicadorRepositorioComando indicadorRepositorioComando;
    @Mock
    private IndicadorRepositorioConsulta indicadorRepositorioConsulta;

    private GuardarIndicadorUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GuardarIndicadorUseCase(indicadorRepositorioComando, indicadorRepositorioConsulta);
    }

    @Test
    void deberiaGuardarIndicadorExitosamente() {
        UUID identificador = UUID.randomUUID();
        Indicador indicador = Indicador.construir(UUID.randomUUID(), "Indicador nombre de prueba", TipoIndicador.construir(), Temporalidad.construir(), Proyecto.construir(), new ArrayList<>());

        when(indicadorRepositorioConsulta.consultarPorNombre(indicador.getNombre())).thenReturn(null);
        when(indicadorRepositorioComando.guardar(indicador)).thenReturn(identificador);

        UUID resultado = useCase.ejecutar(indicador);

        assertEquals(identificador, resultado);
    }

    @Test
    void deberiaLanzarValorDuplicadoExcepcionCuandoNombreExiste() {
        Indicador indicador = Indicador.construir(UUID.randomUUID(), "Indicador ya existente", TipoIndicador.construir(), Temporalidad.construir(), Proyecto.construir(), new ArrayList<>());

        when(indicadorRepositorioConsulta.consultarPorNombre(indicador.getNombre()))
                .thenReturn(Indicador.construir());

        ValorDuplicadoExcepcion excepcion = assertThrows(ValorDuplicadoExcepcion.class,
                () -> useCase.ejecutar(indicador));

        assertEquals(NOMBRE_INDICADOR_EXISTENTE, excepcion.getMessage());
        verify(indicadorRepositorioComando, never()).guardar(any());
    }
}
