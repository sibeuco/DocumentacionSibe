package co.edu.uco.sibe.dominio.puerto.consulta;

import co.edu.uco.sibe.dominio.dto.TemporalidadDTO;
import co.edu.uco.sibe.dominio.modelo.Temporalidad;
import java.util.List;
import java.util.UUID;

public interface TemporalidadRepositorioConsulta {
    List<TemporalidadDTO> consultarDTOs();

    Temporalidad consultarPorIdentificador(UUID identificador);

    boolean hayDatos();

    Temporalidad consultarPorNombre(String nombre);
}