package co.edu.uco.sibe.dominio.puerto.comando;

import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.modelo.Direccion;
import java.util.UUID;

public interface DireccionRepositorioComando {
    UUID guardar(Direccion direccion);

    UUID guardarActividad(Actividad actividad, UUID direccion);

    void eliminarActividad(Actividad actividad, Direccion direccion);
}