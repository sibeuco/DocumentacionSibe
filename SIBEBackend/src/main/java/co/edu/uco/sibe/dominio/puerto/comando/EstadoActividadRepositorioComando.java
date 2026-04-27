package co.edu.uco.sibe.dominio.puerto.comando;

import co.edu.uco.sibe.dominio.modelo.EstadoActividad;
import java.util.UUID;

public interface EstadoActividadRepositorioComando {
    UUID guardar(EstadoActividad estadoActividad);
}