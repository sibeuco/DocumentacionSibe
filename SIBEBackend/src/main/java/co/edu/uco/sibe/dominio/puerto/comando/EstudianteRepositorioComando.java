package co.edu.uco.sibe.dominio.puerto.comando;

import co.edu.uco.sibe.dominio.modelo.Estudiante;
import java.util.UUID;

public interface EstudianteRepositorioComando {
    UUID guardar(Estudiante estudiante);

    UUID modificar(Estudiante estudiante, UUID identificador);

    void desactivarTodos();
}