package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.Area;
import co.edu.uco.sibe.dominio.puerto.comando.AreaRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.AreaRepositorioConsulta;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorDuplicadoExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.UUID;

import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.NOMBRE_AREA_EXISTENTE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuardarAreaUseCaseTest {

    @Mock
    private AreaRepositorioComando areaRepositorioComando;
    @Mock
    private AreaRepositorioConsulta areaRepositorioConsulta;

    private GuardarAreaUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GuardarAreaUseCase(areaRepositorioComando, areaRepositorioConsulta);
    }

    @Test
    void deberiaGuardarAreaExitosamente() {
        UUID identificador = UUID.randomUUID();
        Area area = Area.construir(UUID.randomUUID(), "Bienestar Universitario", new ArrayList<>(), new ArrayList<>());

        when(areaRepositorioConsulta.consultarPorNombre("Bienestar Universitario")).thenReturn(null);
        when(areaRepositorioComando.guardar(area)).thenReturn(identificador);

        UUID resultado = useCase.ejecutar(area);

        assertEquals(identificador, resultado);
        verify(areaRepositorioComando).guardar(area);
    }

    @Test
    void deberiaLanzarValorDuplicadoExcepcionCuandoNombreExiste() {
        Area area = Area.construir(UUID.randomUUID(), "Bienestar Universitario", new ArrayList<>(), new ArrayList<>());

        when(areaRepositorioConsulta.consultarPorNombre("Bienestar Universitario"))
                .thenReturn(Area.construir());

        ValorDuplicadoExcepcion excepcion = assertThrows(ValorDuplicadoExcepcion.class,
                () -> useCase.ejecutar(area));

        assertEquals(NOMBRE_AREA_EXISTENTE, excepcion.getMessage());
        verify(areaRepositorioComando, never()).guardar(any());
    }
}
