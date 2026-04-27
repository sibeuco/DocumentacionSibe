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
class ConsultarTiposParticipantesEnEjecucionesFinalizadasUseCaseTest {

    @Mock
    private ActividadRepositorioConsulta actividadRepositorioConsulta;

    private ConsultarTiposParticipantesEnEjecucionesFinalizadasUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ConsultarTiposParticipantesEnEjecucionesFinalizadasUseCase(actividadRepositorioConsulta);
    }

    @Test
    void deberiaRetornarListaDeTiposParticipantesExitosamente() {
        List<String> tiposParticipantes = List.of("Estudiante", "Docente");
        when(actividadRepositorioConsulta.consultarTiposParticipantesEnEjecucionesFinalizadas()).thenReturn(tiposParticipantes);

        List<String> resultado = useCase.ejecutar();

        assertEquals(tiposParticipantes, resultado);
        verify(actividadRepositorioConsulta).consultarTiposParticipantesEnEjecucionesFinalizadas();
    }
}
