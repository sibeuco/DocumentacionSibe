package co.edu.uco.sibe.dominio.puerto.consulta;

import co.edu.uco.sibe.dominio.dto.MiembroDTO;
import co.edu.uco.sibe.dominio.modelo.Miembro;
import java.util.UUID;

public interface MiembroRepositorioConsulta {
    Miembro consultarPorIdentificador(UUID identificador);

    Miembro consultarPorIdentificacion(String identificacion);

    MiembroDTO consultarPorIdentificacionDTO(String identificacion);

    MiembroDTO consultarPorIdCarnetDTO(String idCarnet);
}