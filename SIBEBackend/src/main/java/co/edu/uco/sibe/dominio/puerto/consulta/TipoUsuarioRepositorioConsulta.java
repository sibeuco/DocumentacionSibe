package co.edu.uco.sibe.dominio.puerto.consulta;

import co.edu.uco.sibe.dominio.dto.TipoUsuarioDTO;
import co.edu.uco.sibe.dominio.modelo.TipoUsuario;
import java.util.List;
import java.util.UUID;

public interface TipoUsuarioRepositorioConsulta {
    List<TipoUsuarioDTO> consultarDTOs();

    TipoUsuario consultarPorIdentificador(UUID identificador);

    boolean hayDatos();

    TipoUsuario consultarPorCodigo(String codigo);

    TipoUsuario consultarPorNombre(String nombre);
}