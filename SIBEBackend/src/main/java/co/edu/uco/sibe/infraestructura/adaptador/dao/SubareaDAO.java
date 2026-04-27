package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.SubareaEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SubareaDAO extends JpaRepository<SubareaEntidad, UUID> {
    SubareaEntidad findByNombre(String nombre);

    SubareaEntidad findByActividades_Identificador(UUID actividadId);
}