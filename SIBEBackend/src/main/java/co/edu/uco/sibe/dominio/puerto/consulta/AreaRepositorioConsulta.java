package co.edu.uco.sibe.dominio.puerto.consulta;

import co.edu.uco.sibe.dominio.dto.AreaDTO;
import co.edu.uco.sibe.dominio.dto.AreaDetalladaDTO;
import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.modelo.Area;
import java.util.List;
import java.util.UUID;

public interface AreaRepositorioConsulta {
    List<AreaDTO> consultarDTOs();

    List<Area> consultarTodos();

    Area consultarPorIdentificador(UUID identificador);

    boolean hayDatos();

    Area consultarPorNombre(String nombre);

    Area consultarPorActividad(Actividad actividad);

    Area consultarPorSubarea(UUID subareaId);

    AreaDetalladaDTO consultarDetallePorIdentificador(UUID identificador);

    AreaDTO consultarPorNombreDTO(String nombre);
}