package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.PeticionRecuperacionClaveEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PeticionRecuperacionClaveDAO extends JpaRepository<PeticionRecuperacionClaveEntidad, UUID> {
    PeticionRecuperacionClaveEntidad findByCorreo(String correo);
}