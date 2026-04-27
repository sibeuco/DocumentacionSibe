package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.InternoEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface InternoDAO extends JpaRepository<InternoEntidad, UUID> {
    Optional<InternoEntidad> findFirstByIdCarnet(String idCarnet);

    Optional<InternoEntidad> findFirstByIdCarnetAndEstaActivoTrue(String idCarnet);
}