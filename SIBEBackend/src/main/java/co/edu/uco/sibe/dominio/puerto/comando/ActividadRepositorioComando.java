package co.edu.uco.sibe.dominio.puerto.comando;

import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.modelo.EjecucionActividad;
import java.util.List;
import java.util.UUID;

public interface ActividadRepositorioComando {
    UUID guardar(Actividad actividad);

    UUID guardarEjecucion(EjecucionActividad ejecucionActividad);

    UUID modificar(Actividad actividad);

    void modificarEjecuciones(List<EjecucionActividad> ejecucionesActividad);

    void modificarEjecucion(EjecucionActividad ejecucionActividad);
}