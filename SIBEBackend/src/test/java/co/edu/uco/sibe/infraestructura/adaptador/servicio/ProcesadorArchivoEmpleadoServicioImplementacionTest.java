package co.edu.uco.sibe.infraestructura.adaptador.servicio;

import co.edu.uco.sibe.aplicacion.comando.DatosEmpleadoComando;
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
class ProcesadorArchivoEmpleadoServicioImplementacionTest {

    @Mock private ProcesadorExcelMotor motor;
    @Mock private FilaExcelMapeador<DatosEmpleadoComando> mapeadorEmpleado;
    @Mock private MultipartFile archivo;

    private ProcesadorArchivoEmpleadoServicioImplementacion servicio;

    @BeforeEach
    void setUp() {
        servicio = new ProcesadorArchivoEmpleadoServicioImplementacion(motor, mapeadorEmpleado);
    }

    @Test
    void deberiaProcesarArchivo() {
        DatosEmpleadoComando comando = mock(DatosEmpleadoComando.class);
        when(motor.procesarArchivo(archivo, mapeadorEmpleado)).thenReturn(List.of(comando));

        List<DatosEmpleadoComando> resultado = servicio.procesar(archivo);

        assertEquals(1, resultado.size());
        verify(motor).procesarArchivo(archivo, mapeadorEmpleado);
    }
}
