package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.EmpleadoEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.UUID;

public interface EmpleadoDAO extends JpaRepository<EmpleadoEntidad, UUID> {
    EmpleadoEntidad findFirstByNumeroIdentificacion(String numeroIdentificacion);

    EmpleadoEntidad findFirstByIdCarnet(String idCarnet);

    @Modifying
    @Query("UPDATE MiembroEntidad m SET m.estaActivo = false WHERE m.identificador IN (SELECT e.identificador FROM EmpleadoEntidad e) AND m.estaActivo = true")
    void desactivarTodos();
}