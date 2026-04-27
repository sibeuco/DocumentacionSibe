package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.IndicadorProyectoEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface IndicadorProyectoDAO extends JpaRepository<IndicadorProyectoEntidad, UUID> { }