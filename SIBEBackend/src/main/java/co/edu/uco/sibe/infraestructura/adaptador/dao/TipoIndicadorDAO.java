package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.TipoIndicadorEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TipoIndicadorDAO extends JpaRepository<TipoIndicadorEntidad, UUID> {
    TipoIndicadorEntidad findByNaturaleza(String naturaleza);

    TipoIndicadorEntidad findByTipologia(String tipologia);
}