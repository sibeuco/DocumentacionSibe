package co.edu.uco.sibe.infraestructura.controlador.comando;

import co.edu.uco.sibe.aplicacion.comando.manejador.CargarMasivamenteEmpleadosManejador;
import co.edu.uco.sibe.aplicacion.comando.manejador.CargarMasivamenteEstudiantesManejador;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CargaMasivaControladorTest {

    @Mock private CargarMasivamenteEmpleadosManejador cargarMasivamenteEmpleadosManejador;
    @Mock private CargarMasivamenteEstudiantesManejador cargarMasivamenteEstudiantesManejador;

    private CargaMasivaControlador controlador;

    @BeforeEach
    void setUp() {
        controlador = new CargaMasivaControlador(cargarMasivamenteEmpleadosManejador, cargarMasivamenteEstudiantesManejador);
    }

    @Test
    void deberiaCargarMasivamenteEmpleados() {
        MultipartFile archivo = mock(MultipartFile.class);
        UUID id = UUID.randomUUID();
        when(cargarMasivamenteEmpleadosManejador.ejecutar(archivo)).thenReturn(new ComandoRespuesta<>(List.of(id)));

        ComandoRespuesta<List<UUID>> resultado = controlador.cargarMasivamenteEmpleados(archivo);

        assertEquals(1, resultado.getValor().size());
        verify(cargarMasivamenteEmpleadosManejador).ejecutar(archivo);
    }

    @Test
    void deberiaCargarMasivamenteEstudiantes() {
        MultipartFile archivo = mock(MultipartFile.class);
        UUID id = UUID.randomUUID();
        when(cargarMasivamenteEstudiantesManejador.ejecutar(archivo)).thenReturn(new ComandoRespuesta<>(List.of(id)));

        ComandoRespuesta<List<UUID>> resultado = controlador.cargarMasivamenteEstudiantes(archivo);

        assertEquals(1, resultado.getValor().size());
        verify(cargarMasivamenteEstudiantesManejador).ejecutar(archivo);
    }
}
