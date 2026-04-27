package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.RegistroAsistenciaEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface RegistroAsistenciaDAO extends JpaRepository<RegistroAsistenciaEntidad, UUID> {
    List<RegistroAsistenciaEntidad> findAllByEjecucionActividadIdentificador(UUID ejecucionActividadId);
}