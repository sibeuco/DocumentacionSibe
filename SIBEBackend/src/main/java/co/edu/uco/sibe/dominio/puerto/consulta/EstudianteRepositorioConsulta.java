package co.edu.uco.sibe.dominio.puerto.consulta;

import co.edu.uco.sibe.dominio.modelo.Estudiante;
import java.util.UUID;

public interface EstudianteRepositorioConsulta {
    Estudiante consultarPorIdentificador(UUID identificador);
    Estudiante consultarPorIdentificacion(String identificacion);
}