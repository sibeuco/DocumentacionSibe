package co.edu.uco.sibe.dominio.puerto.comando;

import co.edu.uco.sibe.dominio.modelo.Proyecto;
import java.util.UUID;

public interface ProyectoRepositorioComando {
    UUID guardar(Proyecto proyecto);

    UUID modificar(Proyecto proyecto, UUID identificador);
}