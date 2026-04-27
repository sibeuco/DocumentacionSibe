package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.ActividadEntidad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.UUID;

public interface ActividadDAO extends JpaRepository<ActividadEntidad, UUID> {
    ActividadEntidad findByNombreAndSemestre(String nombre, String semestre);

    @Query(value = "SELECT a FROM AreaEntidad ar JOIN ar.actividades a WHERE ar.identificador = :areaId ORDER BY a.fechaCreacion DESC, a.nombre ASC",
           countQuery = "SELECT COUNT(a) FROM AreaEntidad ar JOIN ar.actividades a WHERE ar.identificador = :areaId")
    Page<ActividadEntidad> findByAreaIdentificador(@Param("areaId") UUID areaId, Pageable pageable);

    @Query(value = "SELECT a FROM DireccionEntidad dir JOIN dir.actividades a WHERE dir.identificador = :dirId ORDER BY a.fechaCreacion DESC, a.nombre ASC",
           countQuery = "SELECT COUNT(a) FROM DireccionEntidad dir JOIN dir.actividades a WHERE dir.identificador = :dirId")
    Page<ActividadEntidad> findByDireccionIdentificador(@Param("dirId") UUID dirId, Pageable pageable);

    @Query(value = "SELECT a FROM SubareaEntidad sub JOIN sub.actividades a WHERE sub.identificador = :subId ORDER BY a.fechaCreacion DESC, a.nombre ASC",
           countQuery = "SELECT COUNT(a) FROM SubareaEntidad sub JOIN sub.actividades a WHERE sub.identificador = :subId")
    Page<ActividadEntidad> findBySubareaIdentificador(@Param("subId") UUID subId, Pageable pageable);
}