package co.edu.uco.sibe.dominio.puerto.comando;

import co.edu.uco.sibe.dominio.modelo.Empleado;
import java.util.UUID;

public interface EmpleadoRepositorioComando {
    UUID guardar(Empleado empleado);

    UUID modificar(Empleado empleado, UUID identificador);

    void desactivarTodos();
}