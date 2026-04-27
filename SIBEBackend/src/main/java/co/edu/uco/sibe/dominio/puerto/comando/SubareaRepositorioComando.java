package co.edu.uco.sibe.dominio.puerto.comando;

import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.modelo.Subarea;
import java.util.UUID;

public interface SubareaRepositorioComando {
    UUID guardar(Subarea subarea);

    UUID guardarActividad(Actividad actividad, UUID subarea);

    void eliminarActividad(Actividad actividad, Subarea subarea);
}