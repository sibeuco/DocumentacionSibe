package co.edu.uco.sibe.infraestructura.adaptador.servicio;

import co.edu.uco.sibe.aplicacion.comando.DatosEmpleadoComando;
import co.edu.uco.sibe.aplicacion.puerto.servicio.ProcesadorArchivoEmpleadoServicio;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.FilaExcelMapeador;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Service
public class ProcesadorArchivoEmpleadoServicioImplementacion implements ProcesadorArchivoEmpleadoServicio {
    private final ProcesadorExcelMotor motor;
    private final FilaExcelMapeador<DatosEmpleadoComando> mapeadorEmpleado;

    public ProcesadorArchivoEmpleadoServicioImplementacion(
            ProcesadorExcelMotor motor,
            @Qualifier("mapeadorEmpleado") FilaExcelMapeador<DatosEmpleadoComando> mapeadorEmpleado) {
        this.motor = motor;
        this.mapeadorEmpleado = mapeadorEmpleado;
    }

    @Override
    public List<DatosEmpleadoComando> procesar(MultipartFile archivo) {
        return motor.procesarArchivo(archivo, mapeadorEmpleado);
    }
}