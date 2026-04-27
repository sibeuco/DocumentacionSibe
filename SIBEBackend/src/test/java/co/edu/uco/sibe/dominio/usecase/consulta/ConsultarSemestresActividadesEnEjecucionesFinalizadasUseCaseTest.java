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
class ConsultarSemestresActividadesEnEjecucionesFinalizadasUseCaseTest {

    @Mock
    private ActividadRepositorioConsulta actividadRepositorioConsulta;

    private ConsultarSemestresActividadesEnEjecucionesFinalizadasUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ConsultarSemestresActividadesEnEjecucionesFinalizadasUseCase(actividadRepositorioConsulta);
    }

    @Test
    void deberiaRetornarListaDeSemestresExitosamente() {
        List<String> semestres = List.of("2024-1", "2024-2");
        when(actividadRepositorioConsulta.consultarSemestresActividadesEnEjecucionesFinalizadas()).thenReturn(semestres);

        List<String> resultado = useCase.ejecutar();

        assertEquals(semestres, resultado);
        verify(actividadRepositorioConsulta).consultarSemestresActividadesEnEjecucionesFinalizadas();
    }
}
