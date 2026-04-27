package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.fabrica.EstudianteFabrica;
import co.edu.uco.sibe.aplicacion.puerto.servicio.ProcesadorArchivoEstudianteServicio;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.dominio.modelo.Estudiante;
import co.edu.uco.sibe.dominio.puerto.comando.EstudianteRepositorioComando;
import co.edu.uco.sibe.dominio.usecase.comando.CargarMasivamenteEstudiantesUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CargarMasivamenteEstudiantesManejadorTest {

    @Mock private ProcesadorArchivoEstudianteServicio procesadorArchivoEstudianteServicio;
    @Mock private EstudianteFabrica estudianteFabrica;
    @Mock private CargarMasivamenteEstudiantesUseCase cargarMasivamenteEstudiantesUseCase;
    @Mock private EstudianteRepositorioComando estudianteRepositorioComando;

    private CargarMasivamenteEstudiantesManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new CargarMasivamenteEstudiantesManejador(procesadorArchivoEstudianteServicio, estudianteFabrica, cargarMasivamenteEstudiantesUseCase, estudianteRepositorioComando);
    }

    @Test
    void deberiaDesactivarTodosAntesDeCargar() {
        MultipartFile archivo = mock(MultipartFile.class);
        var estudianteComando = mock(co.edu.uco.sibe.aplicacion.comando.DatosEstudianteComando.class);
        Estudiante estudiante = mock(Estudiante.class);
        UUID idEsperado = UUID.randomUUID();

        when(procesadorArchivoEstudianteServicio.procesar(archivo)).thenReturn(List.of(estudianteComando));
        when(estudianteFabrica.construir(estudianteComando)).thenReturn(estudiante);
        when(cargarMasivamenteEstudiantesUseCase.ejecutar(estudiante)).thenReturn(idEsperado);

        manejador.ejecutar(archivo);

        InOrder inOrder = inOrder(estudianteRepositorioComando, cargarMasivamenteEstudiantesUseCase);
        inOrder.verify(estudianteRepositorioComando).desactivarTodos();
        inOrder.verify(cargarMasivamenteEstudiantesUseCase).ejecutar(estudiante);
    }

    @Test
    void deberiaEjecutarCargarMasivamenteEstudiantes() {
        MultipartFile archivo = mock(MultipartFile.class);
        var estudianteComando = mock(co.edu.uco.sibe.aplicacion.comando.DatosEstudianteComando.class);
        Estudiante estudiante = mock(Estudiante.class);
        UUID idEsperado = UUID.randomUUID();

        when(procesadorArchivoEstudianteServicio.procesar(archivo)).thenReturn(List.of(estudianteComando));
        when(estudianteFabrica.construir(estudianteComando)).thenReturn(estudiante);
        when(cargarMasivamenteEstudiantesUseCase.ejecutar(estudiante)).thenReturn(idEsperado);

        ComandoRespuesta<List<UUID>> resultado = manejador.ejecutar(archivo);

        assertEquals(1, resultado.getValor().size());
        assertEquals(idEsperado, resultado.getValor().get(0));
        verify(procesadorArchivoEstudianteServicio).procesar(archivo);
        verify(estudianteFabrica).construir(estudianteComando);
        verify(cargarMasivamenteEstudiantesUseCase).ejecutar(estudiante);
    }
}
