package co.edu.uco.sibe.dominio.puerto.consulta;

import co.edu.uco.sibe.dominio.dto.PublicoInteresDTO;
import co.edu.uco.sibe.dominio.modelo.PublicoInteres;
import java.util.List;
import java.util.UUID;

public interface PublicoInteresRepositorioConsulta {
    List<PublicoInteresDTO> consultarDTOs();

    PublicoInteres consultarPorIdentificador(UUID identificador);

    PublicoInteres consultarPorNombre(String nombre);

    boolean hayDatos();

    List<PublicoInteres> consultarTodosPorIdentificadores(List<UUID> identificadores);
}