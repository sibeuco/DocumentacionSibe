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
class ConsultarNivelesFormacionEstudiantesEnEjecucionesFinalizadasUseCaseTest {

    @Mock
    private ActividadRepositorioConsulta actividadRepositorioConsulta;

    private ConsultarNivelesFormacionEstudiantesEnEjecucionesFinalizadasUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ConsultarNivelesFormacionEstudiantesEnEjecucionesFinalizadasUseCase(actividadRepositorioConsulta);
    }

    @Test
    void deberiaRetornarListaDeNivelesFormacionExitosamente() {
        List<String> nivelesFormacion = List.of("Pregrado", "Posgrado");
        when(actividadRepositorioConsulta.consultarNivelesFormacionEstudiantesEnEjecucionesFinalizadas()).thenReturn(nivelesFormacion);

        List<String> resultado = useCase.ejecutar();

        assertEquals(nivelesFormacion, resultado);
        verify(actividadRepositorioConsulta).consultarNivelesFormacionEstudiantesEnEjecucionesFinalizadas();
    }
}
