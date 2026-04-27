package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.ProyectoAccionEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ProyectoAccionDAO extends JpaRepository<ProyectoAccionEntidad, UUID> { }