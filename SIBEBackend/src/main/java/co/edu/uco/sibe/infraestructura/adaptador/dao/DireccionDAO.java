package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.DireccionEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface DireccionDAO extends JpaRepository<DireccionEntidad, UUID> {
    DireccionEntidad findByNombre(String nombre);

    DireccionEntidad findByActividades_Identificador(UUID actividadId);

    DireccionEntidad findByAreas_Identificador(UUID areaId);
}