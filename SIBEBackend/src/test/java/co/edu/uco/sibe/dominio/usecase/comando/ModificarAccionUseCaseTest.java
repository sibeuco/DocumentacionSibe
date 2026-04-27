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
class ModificarAccionUseCaseTest {

    @Mock
    private AccionRepositorioComando accionRepositorioComando;
    @Mock
    private AccionRepositorioConsulta accionRepositorioConsulta;

    private ModificarAccionUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ModificarAccionUseCase(accionRepositorioComando, accionRepositorioConsulta);
    }

    @Test
    void deberiaModificarAccionExitosamente() {
        UUID identificador = UUID.randomUUID();
        Accion accion = Accion.construir(UUID.randomUUID(), "Detalle modificado test accion", "Objetivo modificado test accion");

        when(accionRepositorioConsulta.consultarPorDetalle(accion.getDetalle())).thenReturn(null);
        when(accionRepositorioComando.modificar(accion, identificador)).thenReturn(identificador);

        UUID resultado = useCase.ejecutar(accion, identificador);

        assertEquals(identificador, resultado);
    }

    @Test
    void deberiaLanzarExcepcionCuandoDetalleYaExisteEnOtraAccion() {
        UUID identificador = UUID.randomUUID();
        UUID otroIdentificador = UUID.randomUUID();
        Accion accion = Accion.construir(UUID.randomUUID(), "Detalle duplicado accion test", "Objetivo duplicado accion test");
        Accion accionExistente = mock(Accion.class);

        when(accionExistente.getIdentificador()).thenReturn(otroIdentificador);

        when(accionRepositorioConsulta.consultarPorDetalle(accion.getDetalle())).thenReturn(accionExistente);

        ValorDuplicadoExcepcion excepcion = assertThrows(ValorDuplicadoExcepcion.class,
                () -> useCase.ejecutar(accion, identificador));

        assertEquals(DETALLE_EXISTENTE, excepcion.getMessage());
    }

    @Test
    void deberiaPermitirMismoDetalleSiEsLaMismaAccion() {
        UUID identificador = UUID.randomUUID();
        Accion accion = Accion.construir(UUID.randomUUID(), "Mismo detalle de la accion", "Mismo objetivo de la accion");
        Accion accionExistente = mock(Accion.class);

        when(accionExistente.getIdentificador()).thenReturn(identificador);

        when(accionRepositorioConsulta.consultarPorDetalle(accion.getDetalle())).thenReturn(accionExistente);
        when(accionRepositorioComando.modificar(accion, identificador)).thenReturn(identificador);

        UUID resultado = useCase.ejecutar(accion, identificador);

        assertEquals(identificador, resultado);
    }
}
