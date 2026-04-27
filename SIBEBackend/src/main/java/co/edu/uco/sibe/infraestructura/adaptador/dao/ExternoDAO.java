package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.ExternoEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ExternoDAO extends JpaRepository<ExternoEntidad, UUID> {
    ExternoEntidad findFirstByNumeroIdentificacion(String numeroIdentificacion);
}