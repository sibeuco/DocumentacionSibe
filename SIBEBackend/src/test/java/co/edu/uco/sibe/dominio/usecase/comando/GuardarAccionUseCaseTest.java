package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.Accion;
import co.edu.uco.sibe.dominio.puerto.comando.AccionRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.AccionRepositorioConsulta;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorDuplicadoExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.DETALLE_EXISTENTE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuardarAccionUseCaseTest {

    @Mock
    private AccionRepositorioComando accionRepositorioComando;
    @Mock
    private AccionRepositorioConsulta accionRepositorioConsulta;

    private GuardarAccionUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GuardarAccionUseCase(accionRepositorioComando, accionRepositorioConsulta);
    }

    @Test
    void deberiaGuardarAccionExitosamente() {
        UUID identificador = UUID.randomUUID();
        Accion accion = Accion.construir(UUID.randomUUID(), "Detalle de la accion de prueba", "Objetivo de la accion de prueba");

        when(accionRepositorioConsulta.consultarPorDetalle(accion.getDetalle())).thenReturn(null);
        when(accionRepositorioComando.guardar(accion)).thenReturn(identificador);

        UUID resultado = useCase.ejecutar(accion);

        assertEquals(identificador, resultado);
    }

    @Test
    void deberiaLanzarValorDuplicadoExcepcionCuandoDetalleExiste() {
        Accion accion = Accion.construir(UUID.randomUUID(), "Detalle de accion ya existente", "Objetivo de la accion existente");

        when(accionRepositorioConsulta.consultarPorDetalle(accion.getDetalle()))
                .thenReturn(Accion.construir());

        ValorDuplicadoExcepcion excepcion = assertThrows(ValorDuplicadoExcepcion.class,
                () -> useCase.ejecutar(accion));

        assertEquals(DETALLE_EXISTENTE, excepcion.getMessage());
        verify(accionRepositorioComando, never()).guardar(any());
    }
}
