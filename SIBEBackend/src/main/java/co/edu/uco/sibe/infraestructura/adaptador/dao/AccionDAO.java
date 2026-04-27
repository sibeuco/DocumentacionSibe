package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.AccionEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AccionDAO extends JpaRepository<AccionEntidad, UUID> {
    AccionEntidad findByDetalle(String detalle);
}