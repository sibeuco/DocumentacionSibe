package co.edu.uco.sibe.dominio.puerto.comando;

import co.edu.uco.sibe.dominio.modelo.RegistroAsistencia;
import java.util.UUID;

public interface RegistroAsistenciaRepositorioComando {
    UUID guardar(RegistroAsistencia registroAsistencia);
}