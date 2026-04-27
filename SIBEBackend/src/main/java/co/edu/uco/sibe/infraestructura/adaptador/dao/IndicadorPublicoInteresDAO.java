package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.IndicadorPublicoInteresEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface IndicadorPublicoInteresDAO extends JpaRepository<IndicadorPublicoInteresEntidad, UUID> { }