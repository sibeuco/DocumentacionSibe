package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.TipoIndicador;
import co.edu.uco.sibe.dominio.puerto.comando.TipoIndicadorRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.TipoIndicadorRepositorioConsulta;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorDuplicadoExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.NATURALEZA_TIPO_INDICADOR_EXISTENTE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuardarTipoIndicadorUseCaseTest {

    @Mock
    private TipoIndicadorRepositorioComando tipoIndicadorRepositorioComando;
    @Mock
    private TipoIndicadorRepositorioConsulta tipoIndicadorRepositorioConsulta;

    private GuardarTipoIndicadorUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GuardarTipoIndicadorUseCase(tipoIndicadorRepositorioComando, tipoIndicadorRepositorioConsulta);
    }

    @Test
    void deberiaGuardarTipoIndicadorExitosamente() {
        UUID identificador = UUID.randomUUID();
        TipoIndicador tipoIndicador = TipoIndicador.construir(UUID.randomUUID(), "Eficacia", "Resultado");

        when(tipoIndicadorRepositorioConsulta.consultarPorNaturaleza("Eficacia")).thenReturn(null);
        when(tipoIndicadorRepositorioComando.guardar(tipoIndicador)).thenReturn(identificador);

        UUID resultado = useCase.ejecutar(tipoIndicador);

        assertEquals(identificador, resultado);
        verify(tipoIndicadorRepositorioComando).guardar(tipoIndicador);
    }

    @Test
    void deberiaLanzarValorDuplicadoExcepcionCuandoNaturalezaExiste() {
        TipoIndicador tipoIndicador = TipoIndicador.construir(UUID.randomUUID(), "Eficacia", "Resultado");

        when(tipoIndicadorRepositorioConsulta.consultarPorNaturaleza("Eficacia"))
                .thenReturn(TipoIndicador.construir());

        ValorDuplicadoExcepcion excepcion = assertThrows(ValorDuplicadoExcepcion.class,
                () -> useCase.ejecutar(tipoIndicador));

        assertEquals(NATURALEZA_TIPO_INDICADOR_EXISTENTE, excepcion.getMessage());
        verify(tipoIndicadorRepositorioComando, never()).guardar(any());
    }
}
