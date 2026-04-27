package co.edu.uco.sibe.infraestructura.adaptador.servicio;

import co.edu.uco.sibe.aplicacion.comando.DatosEstudianteComando;
import co.edu.uco.sibe.aplicacion.puerto.servicio.ProcesadorArchivoEstudianteServicio;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.FilaExcelMapeador;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Service
public class ProcesadorArchivoEstudianteServicioImplementacion implements ProcesadorArchivoEstudianteServicio {
    private final ProcesadorExcelMotor motor;
    private final FilaExcelMapeador<DatosEstudianteComando> mapeadorEstudiante;

    public ProcesadorArchivoEstudianteServicioImplementacion(
            ProcesadorExcelMotor motor,
            @Qualifier("mapeadorEstudiante") FilaExcelMapeador<DatosEstudianteComando> mapeadorEstudiante) {
        this.motor = motor;
        this.mapeadorEstudiante = mapeadorEstudiante;
    }

    @Override
    public List<DatosEstudianteComando> procesar(MultipartFile archivo) {
        return motor.procesarArchivo(archivo, mapeadorEstudiante);
    }
}