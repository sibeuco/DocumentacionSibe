package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.CiudadResidenciaEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CiudadResidenciaDAO extends JpaRepository<CiudadResidenciaEntidad, UUID> {
    CiudadResidenciaEntidad findFirstByDescripcion(String descripcion);
}