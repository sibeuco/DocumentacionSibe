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
class ConsultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadasUseCaseTest {

    @Mock
    private ActividadRepositorioConsulta actividadRepositorioConsulta;

    private ConsultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadasUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ConsultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadasUseCase(actividadRepositorioConsulta);
    }

    @Test
    void deberiaRetornarListaDeProgramasAcademicosExitosamente() {
        List<String> programasAcademicos = List.of("Ingenieria de Sistemas", "Derecho");
        when(actividadRepositorioConsulta.consultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadas()).thenReturn(programasAcademicos);

        List<String> resultado = useCase.ejecutar();

        assertEquals(programasAcademicos, resultado);
        verify(actividadRepositorioConsulta).consultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadas();
    }
}
