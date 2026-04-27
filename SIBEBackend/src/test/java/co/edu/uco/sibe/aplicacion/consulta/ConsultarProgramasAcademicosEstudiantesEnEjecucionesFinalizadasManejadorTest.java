package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadasUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadasManejadorTest {

    @Mock private ConsultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadasUseCase useCase;

    private ConsultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadasManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadasManejador(useCase);
    }

    @Test
    void deberiaConsultarProgramas() {
        List<String> esperado = List.of("Ingenieria", "Medicina");
        when(useCase.ejecutar()).thenReturn(esperado);

        List<String> resultado = manejador.ejecutar();

        assertEquals(esperado, resultado);
        verify(useCase).ejecutar();
    }
}
