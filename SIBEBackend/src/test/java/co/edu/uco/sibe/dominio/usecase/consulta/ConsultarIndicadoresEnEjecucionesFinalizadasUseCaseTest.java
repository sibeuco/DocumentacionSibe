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
class ConsultarIndicadoresEnEjecucionesFinalizadasUseCaseTest {

    @Mock
    private ActividadRepositorioConsulta actividadRepositorioConsulta;

    private ConsultarIndicadoresEnEjecucionesFinalizadasUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ConsultarIndicadoresEnEjecucionesFinalizadasUseCase(actividadRepositorioConsulta);
    }

    @Test
    void deberiaRetornarListaDeIndicadoresExitosamente() {
        List<String> indicadores = List.of("IND001", "IND002");
        when(actividadRepositorioConsulta.consultarIndicadoresEnEjecucionesFinalizadas()).thenReturn(indicadores);

        List<String> resultado = useCase.ejecutar();

        assertEquals(indicadores, resultado);
        verify(actividadRepositorioConsulta).consultarIndicadoresEnEjecucionesFinalizadas();
    }
}
