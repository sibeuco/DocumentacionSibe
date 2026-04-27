package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.TipoIdentificacionEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TipoIdentificacionDAO extends JpaRepository<TipoIdentificacionEntidad, UUID> {
    TipoIdentificacionEntidad findBySigla(String sigla);

    TipoIdentificacionEntidad findByDescripcion(String descripcion);
}