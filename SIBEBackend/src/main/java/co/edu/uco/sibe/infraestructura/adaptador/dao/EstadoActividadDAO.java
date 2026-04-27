package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.EstadoActividadEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EstadoActividadDAO extends JpaRepository<EstadoActividadEntidad, UUID> {
    EstadoActividadEntidad findByNombre(String nombre);
}