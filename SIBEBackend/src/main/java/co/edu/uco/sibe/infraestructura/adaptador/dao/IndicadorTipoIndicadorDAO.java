package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.IndicadorTipoIndicadorEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface IndicadorTipoIndicadorDAO extends JpaRepository<IndicadorTipoIndicadorEntidad, UUID> { }