package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.ParticipanteInternoEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ParticipanteInternoDAO extends JpaRepository<ParticipanteInternoEntidad, UUID> { }