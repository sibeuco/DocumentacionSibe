package co.edu.uco.sibe.aplicacion.puerto.servicio;

import co.edu.uco.sibe.aplicacion.comando.DatosEstudianteComando;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface ProcesadorArchivoEstudianteServicio {
    List<DatosEstudianteComando> procesar(MultipartFile archivo);
}