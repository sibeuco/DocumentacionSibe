package co.edu.uco.sibe.dominio.puerto.consulta;

import co.edu.uco.sibe.dominio.dto.AccionDTO;
import co.edu.uco.sibe.dominio.modelo.Accion;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.UUID;

public interface AccionRepositorioConsulta {
    List<AccionDTO> consultarDTOs();

    Page<AccionDTO> consultarDTOsPaginado(int pagina, int tamano);

    List<Accion> consultarTodosPorIdentificadores(List<UUID> identificadores);

    Accion consultarPorIdentificador(UUID identificador);

    List<Accion> consultarTodos();

    Accion consultarPorDetalle(String detalle);
}