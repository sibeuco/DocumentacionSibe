package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.IdentificacionTipoIdentificacionEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface IdentificacionTipoIdentificacionDAO extends JpaRepository<IdentificacionTipoIdentificacionEntidad, UUID> { }