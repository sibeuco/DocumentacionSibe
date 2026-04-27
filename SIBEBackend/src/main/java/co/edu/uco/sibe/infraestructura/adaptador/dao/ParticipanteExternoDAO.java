package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.ParticipanteExternoEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ParticipanteExternoDAO extends JpaRepository<ParticipanteExternoEntidad, UUID> { }