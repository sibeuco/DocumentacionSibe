package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.TipoUsuarioEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TipoUsuarioDAO extends JpaRepository<TipoUsuarioEntidad, UUID> {
    TipoUsuarioEntidad findFirstByCodigo(String codigo);

    TipoUsuarioEntidad findByNombre(String nombre);
}