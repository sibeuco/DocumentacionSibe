package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.MiembroEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.UUID;

public interface MiembroDAO extends JpaRepository<MiembroEntidad, UUID> {
    Optional<MiembroEntidad> findFirstByNumeroIdentificacion(String numeroIdentificacion);

    Optional<MiembroEntidad> findFirstByNumeroIdentificacionAndEstaActivoTrue(String numeroIdentificacion);

    @Modifying
    @Query("UPDATE MiembroEntidad m SET m.estaActivo = false WHERE m.numeroIdentificacion = :identificacion AND m.estaActivo = true")
    void desactivarPorNumeroIdentificacion(@Param("identificacion") String identificacion);
}