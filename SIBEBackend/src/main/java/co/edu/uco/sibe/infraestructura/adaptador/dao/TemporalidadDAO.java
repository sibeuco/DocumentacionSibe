package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.TemporalidadEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TemporalidadDAO extends JpaRepository<TemporalidadEntidad, UUID> {
    TemporalidadEntidad findByNombre(String nombre);
}