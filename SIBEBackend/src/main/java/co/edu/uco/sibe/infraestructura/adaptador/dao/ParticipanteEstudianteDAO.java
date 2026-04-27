package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.ParticipanteEstudianteEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ParticipanteEstudianteDAO extends JpaRepository<ParticipanteEstudianteEntidad, UUID> { }