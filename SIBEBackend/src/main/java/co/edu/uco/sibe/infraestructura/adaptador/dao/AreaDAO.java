package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.AreaEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AreaDAO extends JpaRepository<AreaEntidad, UUID> {
    AreaEntidad findByNombre(String nombre);

    AreaEntidad findByActividades_Identificador(UUID actividadId);

    AreaEntidad findBySubareas_Identificador(UUID subareaId);
}