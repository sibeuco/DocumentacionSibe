package co.edu.uco.sibe.dominio.puerto.comando;

import co.edu.uco.sibe.dominio.modelo.TipoUsuario;
import java.util.UUID;

public interface TipoUsuarioRepositorioComando {
    UUID guardar(TipoUsuario tipoUsuario);
}