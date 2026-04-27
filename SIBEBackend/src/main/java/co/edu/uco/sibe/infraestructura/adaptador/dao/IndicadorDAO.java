package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.IndicadorEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface IndicadorDAO extends JpaRepository<IndicadorEntidad, UUID> {
    IndicadorEntidad findByNombre(String nombre);
}