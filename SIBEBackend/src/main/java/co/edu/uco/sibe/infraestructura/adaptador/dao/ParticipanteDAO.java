package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.ParticipanteEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.PersistenciaConstante.*;

public interface ParticipanteDAO extends JpaRepository<ParticipanteEntidad, UUID> {
    @Query(CONSULTAR_PARTICIPANTE_POR_NUMERO_IDENTIFICACION)
    ParticipanteEntidad findByMiembroNumeroIdentificacion(
            @Param(DOCUMENTO_PARAMETRO) String documento
    );

    @Query(CONSULTAR_PARTICIPANTE_POR_DOCUMENTO_Y_SEMESTRE)
    Optional<ParticipanteEntidad> findByDocumentoAndSemestre(
            @Param(DOCUMENTO_PARAMETRO) String documento,
            @Param(SEMESTRE_PARAMETRO) String semestre
    );

    @Query(CONSULTAR_CENTROS_COSTOS_EMPLEADOS_FINALIZADOS)
    List<String> findCentrosCostosEmpleadosByEstadoEjecucion(@Param(ESTADO_PARAMETRO) String estado);

    @Query(CONSULTAR_TIPOS_PARTICIPANTES_FINALIZADOS)
    List<String> findTiposParticipantesByEstadoEjecucion(@Param(ESTADO_PARAMETRO) String estado);

    @Query(CONSULTAR_PROGRAMAS_ESTUDIANTES_FINALIZADOS)
    List<String> findProgramasAcademicosEstudiantesByEstadoEjecucion(@Param(ESTADO_PARAMETRO) String estado);
}