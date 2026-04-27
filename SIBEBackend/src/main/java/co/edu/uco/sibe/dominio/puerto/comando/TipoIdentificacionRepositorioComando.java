package co.edu.uco.sibe.dominio.puerto.comando;

import co.edu.uco.sibe.dominio.modelo.TipoIdentificacion;
import java.util.UUID;

public interface TipoIdentificacionRepositorioComando {
    UUID guardar(TipoIdentificacion tipoIdentificacion);
}