package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.EjecucionActividadEstadoActividadEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EjecucionActividadEstadoActividadDAO extends JpaRepository<EjecucionActividadEstadoActividadEntidad, UUID> { }