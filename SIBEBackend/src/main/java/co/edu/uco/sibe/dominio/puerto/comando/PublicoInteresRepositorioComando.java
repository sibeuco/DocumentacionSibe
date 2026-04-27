package co.edu.uco.sibe.dominio.puerto.comando;

import co.edu.uco.sibe.dominio.modelo.PublicoInteres;
import java.util.UUID;

public interface PublicoInteresRepositorioComando {
    UUID guardar(PublicoInteres publicoInteres);
}