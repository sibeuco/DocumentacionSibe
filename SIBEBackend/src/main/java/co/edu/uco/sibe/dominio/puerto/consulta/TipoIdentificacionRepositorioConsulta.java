package co.edu.uco.sibe.dominio.puerto.consulta;

import co.edu.uco.sibe.dominio.dto.TipoIdentificacionDTO;
import co.edu.uco.sibe.dominio.modelo.TipoIdentificacion;
import java.util.List;
import java.util.UUID;

public interface TipoIdentificacionRepositorioConsulta {
    List<TipoIdentificacionDTO> consultarDTOs();

    TipoIdentificacion consultarPorIdentificador(UUID identificador);

    boolean hayDatos();

    TipoIdentificacion consultarPorSigla(String sigla);

    TipoIdentificacion consultarPorDescripcion(String descripcion);
}