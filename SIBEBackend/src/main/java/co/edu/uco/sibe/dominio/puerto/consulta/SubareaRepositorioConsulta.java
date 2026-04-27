package co.edu.uco.sibe.dominio.puerto.consulta;

import co.edu.uco.sibe.dominio.dto.SubareaDTO;
import co.edu.uco.sibe.dominio.dto.SubareaDetalladaDTO;
import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.modelo.Subarea;
import java.util.List;
import java.util.UUID;

public interface SubareaRepositorioConsulta {
    List<SubareaDTO> consultarDTOs();

    List<Subarea> consultarTodos();

    Subarea consultarPorIdentificador(UUID identificador);

    boolean hayDatos();

    Subarea consultarPorNombre(String nombre);

    Subarea consultarPorActividad(Actividad actividad);

    SubareaDetalladaDTO consultarDetallePorIdentificador(UUID identificador);

    SubareaDTO consultarPorNombreDTO(String nombre);
}