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
class ConsultarAnnosEjecucionesFinalizadasUseCaseTest {

    @Mock
    private ActividadRepositorioConsulta actividadRepositorioConsulta;

    private ConsultarAnnosEjecucionesFinalizadasUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ConsultarAnnosEjecucionesFinalizadasUseCase(actividadRepositorioConsulta);
    }

    @Test
    void deberiaRetornarListaDeAnnosExitosamente() {
        List<String> annos = List.of("2023", "2024");
        when(actividadRepositorioConsulta.consultarAnnosEjecucionesFinalizadas()).thenReturn(annos);

        List<String> resultado = useCase.ejecutar();

        assertEquals(annos, resultado);
        verify(actividadRepositorioConsulta).consultarAnnosEjecucionesFinalizadas();
    }
}
