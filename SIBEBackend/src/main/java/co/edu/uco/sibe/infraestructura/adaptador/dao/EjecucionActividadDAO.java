package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.EjecucionActividadEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.ParticipanteEntidad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.PersistenciaConstante.*;

public interface EjecucionActividadDAO extends JpaRepository<EjecucionActividadEntidad, UUID> {
    List<EjecucionActividadEntidad> findByActividadIdentificador(UUID actividadId);

    Page<EjecucionActividadEntidad> findByActividadIdentificador(UUID actividadId, Pageable pageable);

    @Query(value = "SELECT e FROM EjecucionActividadEntidad e " +
            "JOIN e.estadoActividad ea JOIN ea.estadoActividad estado " +
            "WHERE e.actividad.identificador = :actividadId " +
            "ORDER BY e.fechaProgramada ASC, " +
            "CASE estado.nombre WHEN 'Pendiente' THEN 1 WHEN 'En curso' THEN 2 WHEN 'Finalizada' THEN 3 ELSE 4 END ASC",
            countQuery = "SELECT COUNT(e) FROM EjecucionActividadEntidad e WHERE e.actividad.identificador = :actividadId")
    Page<EjecucionActividadEntidad> findByActividadIdentificadorOrdenado(@Param("actividadId") UUID actividadId, Pageable pageable);

    @Query(value = "SELECT DISTINCT p FROM RegistroAsistenciaEntidad ra JOIN ra.participante p LEFT JOIN FETCH p.miembro WHERE ra.ejecucionActividad.identificador = :ejecucionActividadId ORDER BY p.miembro.nombreCompleto ASC",
            countQuery = "SELECT COUNT(DISTINCT p) FROM RegistroAsistenciaEntidad ra JOIN ra.participante p WHERE ra.ejecucionActividad.identificador = :ejecucionActividadId")
    Page<ParticipanteEntidad> findParticipantesPaginadosByEjecucionActividadId(
            @Param("ejecucionActividadId") UUID ejecucionActividadId, Pageable pageable);

    @Query(CONSULTAR_PARTICIPANTES_POR_EJECUCION_ACTIVIDAD)
    List<ParticipanteEntidad> findParticipantesByEjecucionActividadId(
            @Param(EJECUCION_ACTIVIDAD_PARAMETRO) UUID ejecucionActividadId
    );

    @Query(CONSULTAR_FECHAS_REALIZACION_POR_ESTADO)
    List<LocalDate> findFechasRealizacionByEstadoNombre(@Param(ESTADO_PARAMETRO) String nombreEstado);

    @Query(CONSULTAR_INDICADORES_EJECUCIONES_FINALIZADAS)
    List<String> findNombresIndicadoresByEstadoEjecucion(@Param(ESTADO_PARAMETRO) String estado);

    @Query(CONSULTAR_SEMESTRES_ACTIVIDADES_FINALIZADAS)
    List<String> findSemestresActividadesByEstadoEjecucion(@Param(ESTADO_PARAMETRO) String estado);
}