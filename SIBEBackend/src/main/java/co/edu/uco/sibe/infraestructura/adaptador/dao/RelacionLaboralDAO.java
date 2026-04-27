package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.RelacionLaboralEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface RelacionLaboralDAO extends JpaRepository<RelacionLaboralEntidad, UUID> {
    RelacionLaboralEntidad findFirstByCodigo(String codigo);
}