package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.puerto.consulta.ActividadRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarCentrosCostosEmpleadosEnEjecucionesFinalizadasUseCaseTest {

    @Mock
    private ActividadRepositorioConsulta actividadRepositorioConsulta;

    private ConsultarCentrosCostosEmpleadosEnEjecucionesFinalizadasUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ConsultarCentrosCostosEmpleadosEnEjecucionesFinalizadasUseCase(actividadRepositorioConsulta);
    }

    @Test
    void deberiaRetornarListaDeCentrosCostosExitosamente() {
        List<String> centrosCostos = List.of("CC001", "CC002");
        when(actividadRepositorioConsulta.consultarCentrosCostosEmpleadosEnEjecucionesFinalizadas()).thenReturn(centrosCostos);

        List<String> resultado = useCase.ejecutar();

        assertEquals(centrosCostos, resultado);
        verify(actividadRepositorioConsulta).consultarCentrosCostosEmpleadosEnEjecucionesFinalizadas();
    }
}
