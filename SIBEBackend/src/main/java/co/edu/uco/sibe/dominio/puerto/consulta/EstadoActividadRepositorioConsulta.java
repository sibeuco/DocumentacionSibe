package co.edu.uco.sibe.dominio.puerto.consulta;

import co.edu.uco.sibe.dominio.modelo.EstadoActividad;
import java.util.UUID;

public interface EstadoActividadRepositorioConsulta {
    EstadoActividad consultarPorIdentificador(UUID identificador);

    boolean hayDatos();

    EstadoActividad consultarPorNombre(String nombre);
}