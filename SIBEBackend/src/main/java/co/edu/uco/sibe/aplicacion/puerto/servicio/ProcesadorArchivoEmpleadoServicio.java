package co.edu.uco.sibe.aplicacion.puerto.servicio;

import co.edu.uco.sibe.aplicacion.comando.DatosEmpleadoComando;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface ProcesadorArchivoEmpleadoServicio {
    List<DatosEmpleadoComando> procesar(MultipartFile archivo);
}