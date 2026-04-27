package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.EstudianteEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.UUID;

public interface EstudianteDAO extends JpaRepository<EstudianteEntidad, UUID> {
    EstudianteEntidad findFirstByNumeroIdentificacion(String numeroIdentificacion);

    EstudianteEntidad findFirstByIdCarnet(String idCarnet);

    @Modifying
    @Query("UPDATE MiembroEntidad m SET m.estaActivo = false WHERE m.identificador IN (SELECT e.identificador FROM EstudianteEntidad e) AND m.estaActivo = true")
    void desactivarTodos();
}