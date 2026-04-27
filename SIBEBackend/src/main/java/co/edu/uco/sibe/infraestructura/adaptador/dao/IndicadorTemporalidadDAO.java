package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.IndicadorTemporalidadEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface IndicadorTemporalidadDAO extends JpaRepository<IndicadorTemporalidadEntidad, UUID> { }