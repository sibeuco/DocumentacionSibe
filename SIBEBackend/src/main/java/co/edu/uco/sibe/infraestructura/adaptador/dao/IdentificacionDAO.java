package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.IdentificacionEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface IdentificacionDAO extends JpaRepository<IdentificacionEntidad, UUID> {
    IdentificacionEntidad findFirstByNumeroIdentificacion(String numeroIdentificacion);
}