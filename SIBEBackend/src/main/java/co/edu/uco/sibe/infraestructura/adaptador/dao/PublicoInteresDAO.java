package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.PublicoInteresEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PublicoInteresDAO extends JpaRepository<PublicoInteresEntidad, UUID> {
    PublicoInteresEntidad findByNombre(String nombre);
}