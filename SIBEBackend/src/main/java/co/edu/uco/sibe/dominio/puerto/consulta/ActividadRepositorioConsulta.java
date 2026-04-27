package co.edu.uco.sibe.dominio.puerto.consulta;

import co.edu.uco.sibe.dominio.dto.*;
import co.edu.uco.sibe.dominio.enums.TipoArea;
import co.edu.uco.sibe.dominio.modelo.*;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.UUID;

public interface ActividadRepositorioConsulta {
    Actividad consultarPorIdentificador(UUID identificador);

    Actividad consultarPorNombreYSemestre(String nombre, String semestre);

    boolean existeActividadConNombreEnSemestreYArea(String nombre, String semestre, UUID areaId, TipoArea tipoArea, UUID identificadorExcluir);

    List<ActividadDTO> consultarPorSubarea(Subarea subarea);

    List<ActividadDTO> consultarPorArea(Area area);

    List<ActividadDTO> consultarPorDireccion(Direccion direccion);

    Page<ActividadDTO> consultarPorAreaPaginado(UUID areaId, int pagina, int tamano);

    Page<ActividadDTO> consultarPorDireccionPaginado(UUID direccionId, int pagina, int tamano);

    Page<ActividadDTO> consultarPorSubareaPaginado(UUID subareaId, int pagina, int tamano);

    List<EjecucionActividadDTO> consultarFechasProgramadasPorActividad(Actividad actividad);

    Page<EjecucionActividadDTO> consultarFechasProgramadasPorActividadPaginado(UUID actividadId, int pagina, int tamano);

    EjecucionActividad consultarEjecucionActividadPorIdentificador(UUID identificador);

    List<ParticipanteDTO> consultarParticipantesPorEjecucionActividad(UUID ejecucionActividad);

    Page<ParticipanteDTO> consultarParticipantesPorEjecucionActividadPaginado(UUID ejecucionActividad, int pagina, int tamano);

    List<String> consultarMesesEjecucionesFinalizadas();

    List<String> consultarAnnosEjecucionesFinalizadas();

    List<String> consultarSemestresActividadesEnEjecucionesFinalizadas();

    List<String> consultarCentrosCostosEmpleadosEnEjecucionesFinalizadas();

    List<String> consultarTiposParticipantesEnEjecucionesFinalizadas();

    List<String> consultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadas();

    List<String> consultarNivelesFormacionEstudiantesEnEjecucionesFinalizadas();

    List<String> consultarIndicadoresEnEjecucionesFinalizadas();

    Long contarParticipantesTotales(FiltroEstadisticaDTO filtro);

    Long contarAsistenciasTotales(FiltroEstadisticaDTO filtro);

    Long contarEjecucionesTotales(FiltroEstadisticaDTO filtro);

    List<EstadisticaDTO> consultarEstadisticasParticipantesPorEstructura(FiltroEstadisticaDTO filtro);

    List<EstadisticaMesDTO> consultarEstadisticasParticipantesPorMes(FiltroEstadisticaDTO filtro);

    Long contarPoblacionTotal(FiltroEstadisticaDTO filtro);
}