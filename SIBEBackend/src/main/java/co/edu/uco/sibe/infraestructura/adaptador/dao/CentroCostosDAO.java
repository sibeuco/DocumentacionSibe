package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.CentroCostosEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CentroCostosDAO extends JpaRepository<CentroCostosEntidad, UUID> {
    CentroCostosEntidad findFirstByCodigo(String codigo);
}