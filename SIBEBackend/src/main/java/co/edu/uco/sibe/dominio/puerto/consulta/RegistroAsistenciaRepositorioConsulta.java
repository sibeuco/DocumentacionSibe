package co.edu.uco.sibe.dominio.puerto.consulta;

import co.edu.uco.sibe.dominio.modelo.RegistroAsistencia;
import java.util.UUID;

public interface RegistroAsistenciaRepositorioConsulta {
    RegistroAsistencia consultarPorIdentificador(UUID identificador);
}