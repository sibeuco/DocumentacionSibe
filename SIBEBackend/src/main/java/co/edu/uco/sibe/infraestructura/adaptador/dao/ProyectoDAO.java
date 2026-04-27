package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.ProyectoEntidad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;

public interface ProyectoDAO extends JpaRepository<ProyectoEntidad, UUID> {
    ProyectoEntidad findByNumeroProyecto(String numeroProyecto);

    @EntityGraph(attributePaths = {"acciones", "acciones.accion"})
    @Query("SELECT p FROM ProyectoEntidad p")
    List<ProyectoEntidad> findAllConAcciones();

    @EntityGraph(attributePaths = {"acciones", "acciones.accion"})
    @Query(value = "SELECT p FROM ProyectoEntidad p",
           countQuery = "SELECT COUNT(p) FROM ProyectoEntidad p")
    Page<ProyectoEntidad> findAllConAcciones(Pageable pageable);
}