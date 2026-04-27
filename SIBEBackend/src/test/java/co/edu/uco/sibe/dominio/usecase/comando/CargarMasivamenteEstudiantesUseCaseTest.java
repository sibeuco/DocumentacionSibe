package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.CiudadResidencia;
import co.edu.uco.sibe.dominio.modelo.Estudiante;
import co.edu.uco.sibe.dominio.puerto.comando.EstudianteRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.comando.MiembroRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.EstudianteRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CargarMasivamenteEstudiantesUseCaseTest {

    @Mock
    private EstudianteRepositorioComando estudianteRepositorioComando;
    @Mock
    private EstudianteRepositorioConsulta estudianteRepositorioConsulta;
    @Mock
    private MiembroRepositorioComando miembroRepositorioComando;

    private CargarMasivamenteEstudiantesUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new CargarMasivamenteEstudiantesUseCase(estudianteRepositorioComando, estudianteRepositorioConsulta, miembroRepositorioComando);
    }

    private Estudiante crearEstudianteValido() {
        CiudadResidencia ciudad = CiudadResidencia.construir(UUID.randomUUID(), "Medellin");
        return Estudiante.construir(
                UUID.randomUUID(), "Maria Fernanda Gonzalez", "98765432", ciudad,
                "CARNET002", "F", LocalDate.of(2000, 1, 15), "Colombiana", "Soltera",
                "maria@correo.com", "maria@universidad.edu.co", "Ingenieria de sistemas",
                "Ingenieria", 2023, "20231", 120, 4.2f, "Activa", "Presencial", 30, "Autobus"
        );
    }

    @Test
    void deberiaGuardarNuevoEstudianteCuandoNoExiste() {
        UUID identificador = UUID.randomUUID();
        Estudiante estudiante = crearEstudianteValido();

        when(estudianteRepositorioConsulta.consultarPorIdentificacion(estudiante.getNumeroIdentificacion())).thenReturn(null);
        when(estudianteRepositorioComando.guardar(estudiante)).thenReturn(identificador);

        UUID resultado = useCase.ejecutar(estudiante);

        assertEquals(identificador, resultado);
        verify(estudianteRepositorioComando).guardar(estudiante);
        verify(estudianteRepositorioComando, never()).modificar(any(), any());
    }

    @Test
    void deberiaModificarEstudianteCuandoYaExiste() {
        UUID identificadorExistente = UUID.randomUUID();
        Estudiante estudiante = crearEstudianteValido();
        Estudiante estudianteExistente = mock(Estudiante.class);
        when(estudianteExistente.getIdentificador()).thenReturn(identificadorExistente);

        when(estudianteRepositorioConsulta.consultarPorIdentificacion(estudiante.getNumeroIdentificacion())).thenReturn(estudianteExistente);
        when(estudianteRepositorioComando.modificar(estudiante, identificadorExistente)).thenReturn(identificadorExistente);

        UUID resultado = useCase.ejecutar(estudiante);

        assertEquals(identificadorExistente, resultado);
        verify(estudianteRepositorioComando).modificar(estudiante, identificadorExistente);
        verify(estudianteRepositorioComando, never()).guardar(any());
    }
}
