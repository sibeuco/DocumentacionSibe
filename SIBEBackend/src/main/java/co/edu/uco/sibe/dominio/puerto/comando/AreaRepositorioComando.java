package co.edu.uco.sibe.dominio.puerto.comando;

import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.modelo.Area;
import java.util.UUID;

public interface AreaRepositorioComando {
    UUID guardar(Area area);

    UUID guardarActividad(Actividad actividad, UUID area);

    void eliminarActividad(Actividad actividad, Area area);
}