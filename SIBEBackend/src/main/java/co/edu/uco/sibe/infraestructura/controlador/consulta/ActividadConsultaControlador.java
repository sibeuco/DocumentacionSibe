package co.edu.uco.sibe.infraestructura.controlador.consulta;

import co.edu.uco.sibe.aplicacion.consulta.*;
import co.edu.uco.sibe.dominio.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static co.edu.uco.sibe.dominio.transversal.constante.ApiEndpointConstante.*;
import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.textoAUUID;

@RestController
@AllArgsConstructor
@RequestMapping(ACTIVIDADES)
public class ActividadConsultaControlador {
    private final ConsultarActividadesPorAreaManejador consultarActividadesPorAreaManejador;
    private final ConsultarActividadesPorDireccionManejador consultarActividadesPorDireccionManejador;
    private final ConsultarActividadesPorSubareaManejador consultarActividadesPorSubareaManejador;
    private final ConsultarEjecucionesPorActividadManejador consultarEjecucionesPorActividadManejador;
    private final ConsultarParticipantesPorEjecucionActividadManejador consultarParticipantesPorEjecucionActividadManejador;
    private final ConsultarMesesEjecucionesFinalizadasManejador consultarMesesEjecucionesFinalizadasManejador;
    private final ConsultarAnnosEjecucionesFinalizadasManejador consultarAnnosEjecucionesFinalizadasManejador;
    private final ConsultarSemestresActividadesEnEjecucionesFinalizadasManejador consultarSemestresActividadesEnEjecucionesFinalizadasManejador;
    private final ConsultarCentrosCostosEmpleadosEnEjecucionesFinalizadasManejador consultarCentrosCostosEmpleadosEnEjecucionesFinalizadasManejador;
    private final ConsultarTiposParticipantesEnEjecucionesFinalizadasManejador consultarTiposParticipantesEnEjecucionesFinalizadasManejador;
    private final ConsultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadasManejador consultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadasManejador;
    private final ConsultarNivelesFormacionEstudiantesEnEjecucionesFinalizadasManejador consultarNivelesFormacionEstudiantesEnEjecucionesFinalizadasManejador;
    private final ConsultarIndicadoresEnEjecucionesFinalizadasManejador consultarIndicadoresEnEjecucionesFinalizadasManejador;
    private final ContarParticipantesTotalesManejador contarParticipantesTotalesManejador;
    private final ContarAsistenciasTotalesManejador contarAsistenciasTotalesManejador;
    private final ContarEjecucionesTotalesManejador contarEjecucionesTotalesManejador;
    private final ConsultarEstadisticasParticipantesPorEstructuraManejador consultarEstadisticasParticipantesPorEstructuraManejador;
    private final ConsultarEstadisticasParticipantesPorMesManejador consultarEstadisticasParticipantesPorMesManejador;
    private final ContarPoblacionTotalManejador contarPoblacionTotalManejador;

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping(ACTIVIDADES_AREA)
    public List<ActividadDTO> consultarPorArea(@PathVariable String identificador) {
        return this.consultarActividadesPorAreaManejador.ejecutar(identificador);
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping("/area/{identificador}/paginado")
    public Page<ActividadDTO> consultarPorAreaPaginado(
            @PathVariable String identificador,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamano) {
        return this.consultarActividadesPorAreaManejador.ejecutar(identificador, pagina, tamano);
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping(ACTIVIDADES_DIRECCION)
    public List<ActividadDTO> consultarPorDireccion(@PathVariable String identificador) {
        return this.consultarActividadesPorDireccionManejador.ejecutar(identificador);
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping("/direccion/{identificador}/paginado")
    public Page<ActividadDTO> consultarPorDireccionPaginado(
            @PathVariable String identificador,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamano) {
        return this.consultarActividadesPorDireccionManejador.ejecutar(identificador, pagina, tamano);
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping(ACTIVIDADES_SUBAREA)
    public List<ActividadDTO> consultarPorSubarea(@PathVariable String identificador) {
        return this.consultarActividadesPorSubareaManejador.ejecutar(identificador);
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping("/subarea/{identificador}/paginado")
    public Page<ActividadDTO> consultarPorSubareaPaginado(
            @PathVariable String identificador,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamano) {
        return this.consultarActividadesPorSubareaManejador.ejecutar(identificador, pagina, tamano);
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping(ACTIVIDADES_EJECUCIONES)
    public List<EjecucionActividadDTO> consultarEjecuciones(@PathVariable String identificador) {
        return this.consultarEjecucionesPorActividadManejador.ejecutar(identificador);
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping("/ejecuciones/{identificador}/paginado")
    public Page<EjecucionActividadDTO> consultarEjecucionesPaginado(
            @PathVariable String identificador,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamano) {
        return this.consultarEjecucionesPorActividadManejador.ejecutar(identificador, pagina, tamano);
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping(PARTICIPANTES_EJECUCION_ACTIVIDAD)
    public List<ParticipanteDTO> consultarParticipantesPorEjecucionActividad(@PathVariable String identificador) {
        return this.consultarParticipantesPorEjecucionActividadManejador.ejecutar(textoAUUID(identificador));
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping("/ejecuciones/ejecucion/participantes/{identificador}/paginado")
    public Page<ParticipanteDTO> consultarParticipantesPaginado(
            @PathVariable String identificador,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamano) {
        return this.consultarParticipantesPorEjecucionActividadManejador.ejecutar(textoAUUID(identificador), pagina, tamano);
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping(MESES_EJECUCIONES_FINALIZADAS)
    public List<String> consultarMesesEjecucionesFinalizadas() {
        return this.consultarMesesEjecucionesFinalizadasManejador.ejecutar();
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping(ANNOS_EJECUCIONES_FINALIZADAS)
    public List<String> consultarAnnosEjecucionesFinalizadas() {
        return this.consultarAnnosEjecucionesFinalizadasManejador.ejecutar();
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping(SEMESTRES_ESTUDIANTES_EJECUCIONES_FINALIZADAS)
    public List<String> consultarSemestresActividadesEnEjecucionesFinalizadas() {
        return this.consultarSemestresActividadesEnEjecucionesFinalizadasManejador.ejecutar();
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping(CENTROS_COSTOS_EMPLEADOS_EJECUCIONES_FINALIZADAS)
    public List<String> consultarCentrosCostosEmpleadosEnEjecucionesFinalizadas() {
        return this.consultarCentrosCostosEmpleadosEnEjecucionesFinalizadasManejador.ejecutar();
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping(TIPOS_PARTICIPANTES_EJECUCIONES_FINALIZADAS)
    public List<String> consultarTiposParticipantesEnEjecucionesFinalizadas() {
        return this.consultarTiposParticipantesEnEjecucionesFinalizadasManejador.ejecutar();
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping(PROGRAMAS_ACADEMICOS_ESTUDIANTES_EJECUCIONES_FINALIZADAS)
    public List<String> consultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadas() {
        return this.consultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadasManejador.ejecutar();
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping(NIVELES_FORMACION_ESTUDIANTES_EJECUCIONES_FINALIZADAS)
    public List<String> consultarNivelesFormacionEstudiantesEnEjecucionesFinalizadas() {
        return this.consultarNivelesFormacionEstudiantesEnEjecucionesFinalizadasManejador.ejecutar();
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping(INDICADORES_EJECUCIONES_FINALIZADAS)
    public List<String> consultarIndicadoresEnEjecucionesFinalizadas() {
        return this.consultarIndicadoresEnEjecucionesFinalizadasManejador.ejecutar();
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @PostMapping(CONTAR_PARTICIPANTES_EJECUCIONES_FINALIZADAS)
    public Long contarParticipantesTotales(@RequestBody FiltroEstadisticaDTO filtro) {
        return this.contarParticipantesTotalesManejador.ejecutar(filtro);
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @PostMapping(CONTAR_ASISTENCIAS_EJECUCIONES_FINALIZADAS)
    public Long contarAsistenciasTotales(@RequestBody FiltroEstadisticaDTO filtro) {
        return this.contarAsistenciasTotalesManejador.ejecutar(filtro);
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @PostMapping(CONTAR_EJECUCIONES_FINALIZADAS)
    public Long contarEjecucionesTotales(@RequestBody FiltroEstadisticaDTO filtro) {
        return this.contarEjecucionesTotalesManejador.ejecutar(filtro);
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @PostMapping(ESTADISTICAS_PARTICIPANTES_POR_ESTRUCTURA)
    public List<EstadisticaDTO> consultarEstadisticasParticipantesPorEstructura(@RequestBody FiltroEstadisticaDTO filtro) {
        return this.consultarEstadisticasParticipantesPorEstructuraManejador.ejecutar(filtro);
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @PostMapping(ESTADISTICAS_PARTICIPANTES_POR_MES)
    public List<EstadisticaMesDTO> consultarEstadisticasParticipantesPorMes(@RequestBody FiltroEstadisticaDTO filtro) {
        return this.consultarEstadisticasParticipantesPorMesManejador.ejecutar(filtro);
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @PostMapping(CONTAR_POBLACION_TOTAL)
    public Long contarPoblacionTotal(@RequestBody FiltroEstadisticaDTO filtro) {
        return this.contarPoblacionTotalManejador.ejecutar(filtro);
    }
}