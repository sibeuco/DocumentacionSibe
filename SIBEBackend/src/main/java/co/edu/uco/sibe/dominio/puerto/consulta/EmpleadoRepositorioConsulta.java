package co.edu.uco.sibe.dominio.puerto.consulta;

import co.edu.uco.sibe.dominio.modelo.Empleado;
import java.util.UUID;

public interface EmpleadoRepositorioConsulta {
    Empleado consultarPorIdentificador(UUID identificador);
    Empleado consultarPorIdentificacion(String identificacion);
}