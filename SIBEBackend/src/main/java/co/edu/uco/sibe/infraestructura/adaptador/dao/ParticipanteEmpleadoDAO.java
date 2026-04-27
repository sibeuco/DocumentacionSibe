package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.ParticipanteEmpleadoEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ParticipanteEmpleadoDAO extends JpaRepository<ParticipanteEmpleadoEntidad, UUID> { }