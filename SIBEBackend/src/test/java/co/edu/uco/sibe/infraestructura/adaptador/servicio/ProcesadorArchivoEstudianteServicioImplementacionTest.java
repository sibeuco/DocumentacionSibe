package co.edu.uco.sibe.infraestructura.adaptador.servicio;

import co.edu.uco.sibe.aplicacion.comando.DatosEstudianteComando;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.FilaExcelMapeador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcesadorArchivoEstudianteServicioImplementacionTest {

    @Mock private ProcesadorExcelMotor motor;
    @Mock private FilaExcelMapeador<DatosEstudianteComando> mapeadorEstudiante;
    @Mock private MultipartFile archivo;

    private ProcesadorArchivoEstudianteServicioImplementacion servicio;

    @BeforeEach
    void setUp() {
        servicio = new ProcesadorArchivoEstudianteServicioImplementacion(motor, mapeadorEstudiante);
    }

    @Test
    void deberiaProcesarArchivo() {
        DatosEstudianteComando comando = mock(DatosEstudianteComando.class);
        when(motor.procesarArchivo(archivo, mapeadorEstudiante)).thenReturn(List.of(comando));

        List<DatosEstudianteComando> resultado = servicio.procesar(archivo);

        assertEquals(1, resultado.size());
        verify(motor).procesarArchivo(archivo, mapeadorEstudiante);
    }
}
