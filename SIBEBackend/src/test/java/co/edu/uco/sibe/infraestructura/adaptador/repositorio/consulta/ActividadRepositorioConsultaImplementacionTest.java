package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.dto.*;
import co.edu.uco.sibe.dominio.modelo.*;
import co.edu.uco.sibe.infraestructura.adaptador.dao.*;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.*;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActividadRepositorioConsultaImplementacionTest {

    @Mock private EntityManager entityManager;
    @Mock private ActividadDAO actividadDAO;
    @Mock private ActividadMapeador actividadMapeador;
    @Mock private EjecucionActividadDAO ejecucionActividadDAO;
    @Mock private ParticipanteDAO participanteDAO;
    @Mock private EjecucionActividadMapeador ejecucionActividadMapeador;
    @Mock private SubareaMapeador subareaMapeador;
    @Mock private AreaMapeador areaMapeador;
    @Mock private DireccionMapeador direccionMapeador;
    @Mock private ParticipanteDetalladoMapeador participanteDetalladoMapeador;
    @Mock private DireccionDAO direccionDAO;
    @Mock private AreaDAO areaDAO;
    @Mock private SubareaDAO subareaDAO;

    private ActividadRepositorioConsultaImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new ActividadRepositorioConsultaImplementacion(
                entityManager, actividadDAO, actividadMapeador, ejecucionActividadDAO,
                participanteDAO, ejecucionActividadMapeador, subareaMapeador, areaMapeador,
                direccionMapeador, participanteDetalladoMapeador, direccionDAO, areaDAO, subareaDAO
        );
    }

    @Test
    void deberiaConsultarPorIdentificadorExistente() {
        UUID id = UUID.randomUUID();
        ActividadEntidad entidad = new ActividadEntidad();
        Actividad modelo = mock(Actividad.class);
        when(actividadDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(actividadMapeador.construirModelo(entidad)).thenReturn(modelo);

        Actividad resultado = repositorio.consultarPorIdentificador(id);

        assertNotNull(resultado);
        verify(actividadMapeador).construirModelo(entidad);
    }

    @Test
    void deberiaRetornarNullCuandoIdentificadorNoExiste() {
        UUID id = UUID.randomUUID();
        when(actividadDAO.findById(id)).thenReturn(Optional.empty());

        Actividad resultado = repositorio.consultarPorIdentificador(id);

        assertNull(resultado);
    }

    @Test
    void deberiaConsultarPorNombreYSemestreExistente() {
        ActividadEntidad entidad = new ActividadEntidad();
        Actividad modelo = mock(Actividad.class);
        when(actividadDAO.findByNombreAndSemestre("Act1", "2025-1")).thenReturn(entidad);
        when(actividadMapeador.construirModelo(entidad)).thenReturn(modelo);

        Actividad resultado = repositorio.consultarPorNombreYSemestre("Act1", "2025-1");

        assertNotNull(resultado);
    }

    @Test
    void deberiaRetornarNullCuandoNombreYSemestreNoExiste() {
        when(actividadDAO.findByNombreAndSemestre("Act1", "2025-1")).thenReturn(null);

        Actividad resultado = repositorio.consultarPorNombreYSemestre("Act1", "2025-1");

        assertNull(resultado);
    }

    @Test
    void deberiaConsultarPorSubarea() {
        Subarea subarea = mock(Subarea.class);
        SubareaEntidad entidad = mock(SubareaEntidad.class);
        List<ActividadEntidad> actividades = List.of(new ActividadEntidad());
        when(subareaMapeador.construirEntidad(subarea)).thenReturn(entidad);
        when(entidad.getActividades()).thenReturn(actividades);
        when(actividadMapeador.construirDTOs(actividades)).thenReturn(List.of(new ActividadDTO()));

        List<ActividadDTO> resultado = repositorio.consultarPorSubarea(subarea);

        assertEquals(1, resultado.size());
    }

    @Test
    void deberiaConsultarPorArea() {
        Area area = mock(Area.class);
        AreaEntidad entidad = mock(AreaEntidad.class);
        List<ActividadEntidad> actividades = List.of(new ActividadEntidad());
        when(areaMapeador.construirEntidad(area)).thenReturn(entidad);
        when(entidad.getActividades()).thenReturn(actividades);
        when(actividadMapeador.construirDTOs(actividades)).thenReturn(List.of(new ActividadDTO()));

        List<ActividadDTO> resultado = repositorio.consultarPorArea(area);

        assertEquals(1, resultado.size());
    }

    @Test
    void deberiaConsultarPorDireccion() {
        Direccion direccion = mock(Direccion.class);
        DireccionEntidad entidad = mock(DireccionEntidad.class);
        List<ActividadEntidad> actividades = List.of(new ActividadEntidad());
        when(direccionMapeador.construirEntidad(direccion)).thenReturn(entidad);
        when(entidad.getActividades()).thenReturn(actividades);
        when(actividadMapeador.construirDTOs(actividades)).thenReturn(List.of(new ActividadDTO()));

        List<ActividadDTO> resultado = repositorio.consultarPorDireccion(direccion);

        assertEquals(1, resultado.size());
    }

    @Test
    void deberiaConsultarFechasProgramadasPorActividad() {
        Actividad actividad = mock(Actividad.class);
        UUID actId = UUID.randomUUID();
        when(actividad.getIdentificador()).thenReturn(actId);
        List<EjecucionActividadEntidad> entidades = List.of(new EjecucionActividadEntidad());
        when(ejecucionActividadDAO.findByActividadIdentificador(actId)).thenReturn(entidades);
        when(ejecucionActividadMapeador.construirDTOs(entidades)).thenReturn(List.of(new EjecucionActividadDTO()));

        List<EjecucionActividadDTO> resultado = repositorio.consultarFechasProgramadasPorActividad(actividad);

        assertEquals(1, resultado.size());
    }

    @Test
    void deberiaConsultarEjecucionActividadPorIdentificadorExistente() {
        UUID id = UUID.randomUUID();
        EjecucionActividadEntidad entidad = new EjecucionActividadEntidad();
        EjecucionActividad modelo = mock(EjecucionActividad.class);
        when(ejecucionActividadDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(ejecucionActividadMapeador.construirModelo(entidad)).thenReturn(modelo);

        EjecucionActividad resultado = repositorio.consultarEjecucionActividadPorIdentificador(id);

        assertNotNull(resultado);
    }

    @Test
    void deberiaRetornarNullCuandoEjecucionNoExiste() {
        UUID id = UUID.randomUUID();
        when(ejecucionActividadDAO.findById(id)).thenReturn(Optional.empty());

        EjecucionActividad resultado = repositorio.consultarEjecucionActividadPorIdentificador(id);

        assertNull(resultado);
    }

    @Test
    void deberiaConsultarParticipantesPorEjecucionActividad() {
        UUID ejId = UUID.randomUUID();
        List<ParticipanteEntidad> participantes = List.of(new ParticipanteEntidad());
        when(ejecucionActividadDAO.findParticipantesByEjecucionActividadId(ejId)).thenReturn(participantes);
        when(participanteDetalladoMapeador.construirDTOs(participantes)).thenReturn(List.of(new ParticipanteDTO()));

        List<ParticipanteDTO> resultado = repositorio.consultarParticipantesPorEjecucionActividad(ejId);

        assertEquals(1, resultado.size());
    }

    @Test
    void deberiaConsultarAnnosEjecucionesFinalizadas() {
        List<LocalDate> fechas = List.of(LocalDate.of(2025, 3, 15), LocalDate.of(2024, 6, 10), LocalDate.of(2025, 7, 1));
        when(ejecucionActividadDAO.findFechasRealizacionByEstadoNombre("Finalizada")).thenReturn(fechas);

        List<String> resultado = repositorio.consultarAnnosEjecucionesFinalizadas();

        assertFalse(resultado.isEmpty());
        assertTrue(resultado.contains("2025"));
        assertTrue(resultado.contains("2024"));
    }

    @Test
    void deberiaConsultarMesesEjecucionesFinalizadas() {
        List<LocalDate> fechas = List.of(LocalDate.of(2025, 1, 15), LocalDate.of(2025, 3, 10));
        when(ejecucionActividadDAO.findFechasRealizacionByEstadoNombre("Finalizada")).thenReturn(fechas);

        List<String> resultado = repositorio.consultarMesesEjecucionesFinalizadas();

        assertFalse(resultado.isEmpty());
    }

    @Test
    void deberiaConsultarSemestresActividadesEnEjecucionesFinalizadas() {
        when(ejecucionActividadDAO.findSemestresActividadesByEstadoEjecucion("Finalizada")).thenReturn(List.of("2025-1"));

        List<String> resultado = repositorio.consultarSemestresActividadesEnEjecucionesFinalizadas();

        assertEquals(1, resultado.size());
    }

    @Test
    void deberiaConsultarCentrosCostosEmpleadosEnEjecucionesFinalizadas() {
        when(participanteDAO.findCentrosCostosEmpleadosByEstadoEjecucion("Finalizada")).thenReturn(List.of("CC-01"));

        List<String> resultado = repositorio.consultarCentrosCostosEmpleadosEnEjecucionesFinalizadas();

        assertEquals(1, resultado.size());
    }

    @Test
    void deberiaConsultarTiposParticipantesEnEjecucionesFinalizadas() {
        when(participanteDAO.findTiposParticipantesByEstadoEjecucion("Finalizada")).thenReturn(List.of("ESTUDIANTE"));

        List<String> resultado = repositorio.consultarTiposParticipantesEnEjecucionesFinalizadas();

        assertEquals(1, resultado.size());
    }

    @Test
    void deberiaConsultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadas() {
        when(participanteDAO.findProgramasAcademicosEstudiantesByEstadoEjecucion("Finalizada")).thenReturn(List.of("Ingenieria"));

        List<String> resultado = repositorio.consultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadas();

        assertEquals(1, resultado.size());
    }

    @Test
    void deberiaConsultarNivelesFormacionEstudiantesEnEjecucionesFinalizadas() {
        when(participanteDAO.findProgramasAcademicosEstudiantesByEstadoEjecucion("Finalizada"))
                .thenReturn(List.of("INGENIERIA DE SISTEMAS", "MAESTRIA EN COMPUTACION"));

        List<String> resultado = repositorio.consultarNivelesFormacionEstudiantesEnEjecucionesFinalizadas();

        assertFalse(resultado.isEmpty());
    }

    @Test
    void deberiaConsultarIndicadoresEnEjecucionesFinalizadas() {
        when(ejecucionActividadDAO.findNombresIndicadoresByEstadoEjecucion("Finalizada")).thenReturn(List.of("IND-01"));

        List<String> resultado = repositorio.consultarIndicadoresEnEjecucionesFinalizadas();

        assertEquals(1, resultado.size());
    }

    @Test
    void deberiaContarParticipantesTotales() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(42L);

        Long resultado = repositorio.contarParticipantesTotales(filtro);

        assertEquals(42L, resultado);
    }

    @Test
    void deberiaContarAsistenciasTotales() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(100L);

        Long resultado = repositorio.contarAsistenciasTotales(filtro);

        assertEquals(100L, resultado);
    }

    @Test
    void deberiaContarEjecucionesTotales() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(10L);

        Long resultado = repositorio.contarEjecucionesTotales(filtro);

        assertEquals(10L, resultado);
    }

    @Test
    void deberiaConsultarEstadisticasParticipantesPorEstructura() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        when(direccionDAO.findAll()).thenReturn(List.of());
        when(areaDAO.findAll()).thenReturn(List.of());
        when(subareaDAO.findAll()).thenReturn(List.of());

        List<EstadisticaDTO> resultado = repositorio.consultarEstadisticasParticipantesPorEstructura(filtro);

        assertNotNull(resultado);
    }

    @Test
    void deberiaConsultarEstadisticasParticipantesPorEstructuraConFiltroArea() {
        UUID areaId = UUID.randomUUID();
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        filtro.setTipoArea("AREA");
        filtro.setIdArea(areaId);

        AreaEntidad areaEntidad = new AreaEntidad();
        areaEntidad.setIdentificador(areaId);
        areaEntidad.setNombre("Area Test");
        areaEntidad.setSubareas(List.of());
        when(areaDAO.findById(areaId)).thenReturn(Optional.of(areaEntidad));

        when(direccionDAO.findAll()).thenReturn(List.of());
        when(areaDAO.findAll()).thenReturn(List.of(areaEntidad));
        when(subareaDAO.findAll()).thenReturn(List.of());

        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(0L);

        List<EstadisticaDTO> resultado = repositorio.consultarEstadisticasParticipantesPorEstructura(filtro);

        assertNotNull(resultado);
    }

    @Test
    void deberiaConsultarEstadisticasParticipantesPorMes() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(0L);

        List<EstadisticaMesDTO> resultado = repositorio.consultarEstadisticasParticipantesPorMes(filtro);

        assertNotNull(resultado);
        assertEquals(12, resultado.size()); // 12 months
    }

    @Test
    void deberiaExisteActividadConNombreEnSemestreYAreaSubarea() {
        UUID areaId = UUID.randomUUID();
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(1L);

        boolean resultado = repositorio.existeActividadConNombreEnSemestreYArea(
                "Act1", "2025-1", areaId, co.edu.uco.sibe.dominio.enums.TipoArea.SUBAREA, null);

        assertTrue(resultado);
    }

    @Test
    void deberiaExisteActividadConIdentificadorExcluir() {
        UUID areaId = UUID.randomUUID();
        UUID idExcluir = UUID.randomUUID();
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(0L);

        boolean resultado = repositorio.existeActividadConNombreEnSemestreYArea(
                "Act1", "2025-1", areaId, co.edu.uco.sibe.dominio.enums.TipoArea.AREA, idExcluir);

        assertFalse(resultado);
    }

    @Test
    void deberiaContarPoblacionTotalConFiltroVacio() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(500L);

        Long resultado = repositorio.contarPoblacionTotal(filtro);

        assertEquals(500L, resultado);
    }

    @Test
    void deberiaExisteActividadConNombreEnSemestreYAreaDireccion() {
        UUID areaId = UUID.randomUUID();
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(0L);

        boolean resultado = repositorio.existeActividadConNombreEnSemestreYArea(
                "Act1", "2025-1", areaId, co.edu.uco.sibe.dominio.enums.TipoArea.DIRECCION, null);

        assertFalse(resultado);
    }

    @Test
    void deberiaContarPoblacionTotalEstudianteConPrograma() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        filtro.setTipoParticipante("ESTUDIANTE");
        filtro.setProgramaAcademico("Ingenieria de Sistemas");
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(30L);

        Long resultado = repositorio.contarPoblacionTotal(filtro);

        assertEquals(30L, resultado);
        verify(query).setParameter(eq("programa"), eq("Ingenieria de Sistemas"));
    }

    @Test
    void deberiaContarPoblacionTotalEstudianteConTipoProgramaPostgrado() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        filtro.setTipoParticipante("ESTUDIANTE");
        filtro.setTipoProgramaAcademico("POSTGRADO");
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(15L);

        Long resultado = repositorio.contarPoblacionTotal(filtro);

        assertEquals(15L, resultado);
    }

    @Test
    void deberiaContarPoblacionTotalEstudianteConTipoProgramaPregrado() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        filtro.setTipoParticipante("ESTUDIANTE");
        filtro.setTipoProgramaAcademico("PREGRADO");
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(20L);

        Long resultado = repositorio.contarPoblacionTotal(filtro);

        assertEquals(20L, resultado);
    }

    @Test
    void deberiaContarPoblacionTotalEstudianteConProgramaYTipoPostgrado() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        filtro.setTipoParticipante("ESTUDIANTE");
        filtro.setProgramaAcademico("MAESTRIA EN COMPUTACION");
        filtro.setTipoProgramaAcademico("POSTGRADO");
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(5L);

        Long resultado = repositorio.contarPoblacionTotal(filtro);

        assertEquals(5L, resultado);
        verify(query).setParameter(eq("programa"), eq("MAESTRIA EN COMPUTACION"));
    }

    @Test
    void deberiaContarPoblacionTotalEmpleadoConCentroCostos() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        filtro.setTipoParticipante("EMPLEADO");
        filtro.setCentroCostos("CC-01");
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(25L);

        Long resultado = repositorio.contarPoblacionTotal(filtro);

        assertEquals(25L, resultado);
        verify(query).setParameter(eq("centroCostos"), eq("CC-01"));
    }

    @Test
    void deberiaContarPoblacionTotalEmpleadoSinCentroCostos() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        filtro.setTipoParticipante("EMPLEADO");
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(50L);

        Long resultado = repositorio.contarPoblacionTotal(filtro);

        assertEquals(50L, resultado);
    }

    @Test
    void deberiaContarPoblacionTotalExterno() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        filtro.setTipoParticipante("EXTERNO");
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(10L);

        Long resultado = repositorio.contarPoblacionTotal(filtro);

        assertEquals(10L, resultado);
    }

    @Test
    void deberiaContarPoblacionTotalSinTipoConFiltroProgramaAcademico() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        filtro.setProgramaAcademico("Ingenieria de Sistemas");
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(40L);

        Long resultado = repositorio.contarPoblacionTotal(filtro);

        assertEquals(40L, resultado);
        verify(query).setParameter(eq("programa"), eq("Ingenieria de Sistemas"));
    }

    @Test
    void deberiaContarPoblacionTotalSinTipoConFiltroProgramaYTipoPregrado() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        filtro.setProgramaAcademico("Ingenieria");
        filtro.setTipoProgramaAcademico("PREGRADO");
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(35L);

        Long resultado = repositorio.contarPoblacionTotal(filtro);

        assertEquals(35L, resultado);
    }

    @Test
    void deberiaContarPoblacionTotalSinTipoConFiltroCentroCostos() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        filtro.setCentroCostos("CC-02");
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(8L);

        Long resultado = repositorio.contarPoblacionTotal(filtro);

        assertEquals(8L, resultado);
        verify(query).setParameter(eq("centroCostos"), eq("CC-02"));
    }

    @Test
    void deberiaContarPoblacionTotalSinTipoConTipoProgramaPostgradoSinPrograma() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        filtro.setTipoProgramaAcademico("POSTGRADO");
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(12L);

        Long resultado = repositorio.contarPoblacionTotal(filtro);

        assertEquals(12L, resultado);
    }

    @Test
    void deberiaContarParticipantesConFiltroAnnoYMes() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        filtro.setAnno(2025);
        filtro.setMes("Enero");
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(20L);

        Long resultado = repositorio.contarParticipantesTotales(filtro);

        assertEquals(20L, resultado);
    }

    @Test
    void deberiaContarParticipantesConFiltroIndicador() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        filtro.setIndicador("IND-01");
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(5L);

        Long resultado = repositorio.contarParticipantesTotales(filtro);

        assertEquals(5L, resultado);
    }

    @Test
    void deberiaContarParticipantesConFiltroTipoParticipanteEstudiante() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        filtro.setTipoParticipante("ESTUDIANTE");
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(30L);

        Long resultado = repositorio.contarParticipantesTotales(filtro);

        assertEquals(30L, resultado);
    }

    @Test
    void deberiaContarParticipantesConFiltroTipoParticipanteEmpleado() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        filtro.setTipoParticipante("EMPLEADO");
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(15L);

        Long resultado = repositorio.contarParticipantesTotales(filtro);

        assertEquals(15L, resultado);
    }

    @Test
    void deberiaContarParticipantesConFiltroTipoParticipanteExterno() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        filtro.setTipoParticipante("EXTERNO");
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(7L);

        Long resultado = repositorio.contarParticipantesTotales(filtro);

        assertEquals(7L, resultado);
    }

    @Test
    void deberiaContarParticipantesConFiltroSemestre() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        filtro.setSemestre("2025-1");
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(18L);

        Long resultado = repositorio.contarParticipantesTotales(filtro);

        assertEquals(18L, resultado);
    }

    @Test
    void deberiaContarParticipantesConFiltroProgramaAcademico() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        filtro.setProgramaAcademico("Ingenieria");
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(22L);

        Long resultado = repositorio.contarParticipantesTotales(filtro);

        assertEquals(22L, resultado);
    }

    @Test
    void deberiaContarParticipantesConFiltroTipoProgramaPostgrado() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        filtro.setTipoProgramaAcademico("POSTGRADO");
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(6L);

        Long resultado = repositorio.contarParticipantesTotales(filtro);

        assertEquals(6L, resultado);
    }

    @Test
    void deberiaContarParticipantesConFiltroTipoProgramaPregrado() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        filtro.setTipoProgramaAcademico("PREGRADO");
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(14L);

        Long resultado = repositorio.contarParticipantesTotales(filtro);

        assertEquals(14L, resultado);
    }

    @Test
    void deberiaContarParticipantesConFiltroCentroCostos() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        filtro.setCentroCostos("CC-01");
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(9L);

        Long resultado = repositorio.contarParticipantesTotales(filtro);

        assertEquals(9L, resultado);
    }

    @Test
    void deberiaContarParticipantesConFiltroAreaTipoArea() {
        UUID areaId = UUID.randomUUID();
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        filtro.setIdArea(areaId);
        filtro.setTipoArea("AREA");
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(11L);

        Long resultado = repositorio.contarParticipantesTotales(filtro);

        assertEquals(11L, resultado);
    }

    @Test
    void deberiaContarParticipantesConFiltroSubarea() {
        UUID subareaId = UUID.randomUUID();
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        filtro.setIdArea(subareaId);
        filtro.setTipoArea("SUBAREA");
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(3L);

        Long resultado = repositorio.contarParticipantesTotales(filtro);

        assertEquals(3L, resultado);
    }

    @Test
    void deberiaContarParticipantesConFiltroDireccion() {
        UUID dirId = UUID.randomUUID();
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        filtro.setIdArea(dirId);
        filtro.setTipoArea("DIRECCION");
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(4L);

        Long resultado = repositorio.contarParticipantesTotales(filtro);

        assertEquals(4L, resultado);
    }

    @Test
    void deberiaConsultarEstadisticasParticipantesPorEstructuraConFiltroDireccion() {
        UUID dirId = UUID.randomUUID();
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        filtro.setTipoArea("DIRECCION");
        filtro.setIdArea(dirId);

        DireccionEntidad dirEntidad = new DireccionEntidad();
        dirEntidad.setIdentificador(dirId);
        dirEntidad.setNombre("Direccion Test");
        AreaEntidad areaEntidad = new AreaEntidad();
        areaEntidad.setIdentificador(UUID.randomUUID());
        areaEntidad.setNombre("Area Hija");
        areaEntidad.setSubareas(List.of());
        dirEntidad.setAreas(List.of(areaEntidad));
        when(direccionDAO.findById(dirId)).thenReturn(Optional.of(dirEntidad));

        when(direccionDAO.findAll()).thenReturn(List.of(dirEntidad));
        when(areaDAO.findAll()).thenReturn(List.of(areaEntidad));
        when(subareaDAO.findAll()).thenReturn(List.of());

        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(0L);

        List<EstadisticaDTO> resultado = repositorio.consultarEstadisticasParticipantesPorEstructura(filtro);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
    }

    @Test
    void deberiaConsultarEstadisticasParticipantesPorEstructuraConFiltroSubarea() {
        UUID subareaId = UUID.randomUUID();
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        filtro.setTipoArea("SUBAREA");
        filtro.setIdArea(subareaId);

        SubareaEntidad subareaEntidad = new SubareaEntidad();
        subareaEntidad.setIdentificador(subareaId);
        subareaEntidad.setNombre("Subarea Test");

        when(direccionDAO.findAll()).thenReturn(List.of());
        when(areaDAO.findAll()).thenReturn(List.of());
        when(subareaDAO.findAll()).thenReturn(List.of(subareaEntidad));

        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(0L);

        List<EstadisticaDTO> resultado = repositorio.consultarEstadisticasParticipantesPorEstructura(filtro);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    void deberiaConsultarEstadisticasParticipantesPorEstructuraConDireccionYSubareas() {
        UUID dirId = UUID.randomUUID();
        UUID areaId = UUID.randomUUID();
        UUID subareaId = UUID.randomUUID();
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        filtro.setTipoArea("DIRECCION");
        filtro.setIdArea(dirId);

        SubareaEntidad subareaEntidad = new SubareaEntidad();
        subareaEntidad.setIdentificador(subareaId);
        subareaEntidad.setNombre("Subarea Hija");

        AreaEntidad areaEntidad = new AreaEntidad();
        areaEntidad.setIdentificador(areaId);
        areaEntidad.setNombre("Area Hija");
        areaEntidad.setSubareas(List.of(subareaEntidad));

        DireccionEntidad dirEntidad = new DireccionEntidad();
        dirEntidad.setIdentificador(dirId);
        dirEntidad.setNombre("Direccion");
        dirEntidad.setAreas(List.of(areaEntidad));
        when(direccionDAO.findById(dirId)).thenReturn(Optional.of(dirEntidad));

        when(direccionDAO.findAll()).thenReturn(List.of(dirEntidad));
        when(areaDAO.findAll()).thenReturn(List.of(areaEntidad));
        when(subareaDAO.findAll()).thenReturn(List.of(subareaEntidad));

        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(2L);

        List<EstadisticaDTO> resultado = repositorio.consultarEstadisticasParticipantesPorEstructura(filtro);

        assertNotNull(resultado);
        assertEquals(3, resultado.size());
    }

    @Test
    void deberiaConsultarEstadisticasParticipantesPorMesConFiltroMesEspecifico() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        filtro.setMes("Enero");
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(5L);

        List<EstadisticaMesDTO> resultado = repositorio.consultarEstadisticasParticipantesPorMes(filtro);

        assertNotNull(resultado);
        assertEquals(12, resultado.size());
    }

    @Test
    void deberiaContarParticipantesConMesInvalido() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        filtro.setMes("MesInvalido");
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(0L);

        Long resultado = repositorio.contarParticipantesTotales(filtro);

        assertNotNull(resultado);
    }

    @Test
    void deberiaContarParticipantesConFiltroCompleto() {
        UUID areaId = UUID.randomUUID();
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        filtro.setAnno(2025);
        filtro.setMes("Marzo");
        filtro.setSemestre("2025-1");
        filtro.setProgramaAcademico("Ingenieria");
        filtro.setTipoProgramaAcademico("PREGRADO");
        filtro.setCentroCostos("CC-01");
        filtro.setTipoParticipante("ESTUDIANTE");
        filtro.setIndicador("IND-01");
        filtro.setTipoArea("AREA");
        filtro.setIdArea(areaId);
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(1L);

        Long resultado = repositorio.contarAsistenciasTotales(filtro);

        assertEquals(1L, resultado);
    }

    @Test
    void deberiaConsultarEstadisticasParticipantesPorEstructuraConAreaNoPermitida() {
        UUID areaId = UUID.randomUUID();
        UUID otraAreaId = UUID.randomUUID();
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        filtro.setTipoArea("AREA");
        filtro.setIdArea(areaId);

        AreaEntidad areaPermitida = new AreaEntidad();
        areaPermitida.setIdentificador(areaId);
        areaPermitida.setNombre("Area Permitida");
        areaPermitida.setSubareas(List.of());
        when(areaDAO.findById(areaId)).thenReturn(Optional.of(areaPermitida));

        AreaEntidad areaNoPermitida = new AreaEntidad();
        areaNoPermitida.setIdentificador(otraAreaId);
        areaNoPermitida.setNombre("Area No Permitida");

        when(direccionDAO.findAll()).thenReturn(List.of());
        when(areaDAO.findAll()).thenReturn(List.of(areaPermitida, areaNoPermitida));
        when(subareaDAO.findAll()).thenReturn(List.of());

        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(0L);

        List<EstadisticaDTO> resultado = repositorio.consultarEstadisticasParticipantesPorEstructura(filtro);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(0L, resultado.get(1).getCantidadParticipantes());
    }
}
