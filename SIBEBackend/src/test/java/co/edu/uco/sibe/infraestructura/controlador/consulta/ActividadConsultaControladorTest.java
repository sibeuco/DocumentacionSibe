package co.edu.uco.sibe.infraestructura.controlador.consulta;

import co.edu.uco.sibe.aplicacion.consulta.*;
import co.edu.uco.sibe.dominio.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActividadConsultaControladorTest {

    @Mock private ConsultarActividadesPorAreaManejador consultarActividadesPorAreaManejador;
    @Mock private ConsultarActividadesPorDireccionManejador consultarActividadesPorDireccionManejador;
    @Mock private ConsultarActividadesPorSubareaManejador consultarActividadesPorSubareaManejador;
    @Mock private ConsultarEjecucionesPorActividadManejador consultarEjecucionesPorActividadManejador;
    @Mock private ConsultarParticipantesPorEjecucionActividadManejador consultarParticipantesPorEjecucionActividadManejador;
    @Mock private ConsultarMesesEjecucionesFinalizadasManejador consultarMesesEjecucionesFinalizadasManejador;
    @Mock private ConsultarAnnosEjecucionesFinalizadasManejador consultarAnnosEjecucionesFinalizadasManejador;
    @Mock private ConsultarSemestresActividadesEnEjecucionesFinalizadasManejador consultarSemestresActividadesEnEjecucionesFinalizadasManejador;
    @Mock private ConsultarCentrosCostosEmpleadosEnEjecucionesFinalizadasManejador consultarCentrosCostosEmpleadosEnEjecucionesFinalizadasManejador;
    @Mock private ConsultarTiposParticipantesEnEjecucionesFinalizadasManejador consultarTiposParticipantesEnEjecucionesFinalizadasManejador;
    @Mock private ConsultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadasManejador consultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadasManejador;
    @Mock private ConsultarNivelesFormacionEstudiantesEnEjecucionesFinalizadasManejador consultarNivelesFormacionEstudiantesEnEjecucionesFinalizadasManejador;
    @Mock private ConsultarIndicadoresEnEjecucionesFinalizadasManejador consultarIndicadoresEnEjecucionesFinalizadasManejador;
    @Mock private ContarParticipantesTotalesManejador contarParticipantesTotalesManejador;
    @Mock private ContarAsistenciasTotalesManejador contarAsistenciasTotalesManejador;
    @Mock private ContarEjecucionesTotalesManejador contarEjecucionesTotalesManejador;
    @Mock private ConsultarEstadisticasParticipantesPorEstructuraManejador consultarEstadisticasParticipantesPorEstructuraManejador;
    @Mock private ConsultarEstadisticasParticipantesPorMesManejador consultarEstadisticasParticipantesPorMesManejador;
    @Mock private ContarPoblacionTotalManejador contarPoblacionTotalManejador;

    private ActividadConsultaControlador controlador;

    @BeforeEach
    void setUp() {
        controlador = new ActividadConsultaControlador(
                consultarActividadesPorAreaManejador,
                consultarActividadesPorDireccionManejador,
                consultarActividadesPorSubareaManejador,
                consultarEjecucionesPorActividadManejador,
                consultarParticipantesPorEjecucionActividadManejador,
                consultarMesesEjecucionesFinalizadasManejador,
                consultarAnnosEjecucionesFinalizadasManejador,
                consultarSemestresActividadesEnEjecucionesFinalizadasManejador,
                consultarCentrosCostosEmpleadosEnEjecucionesFinalizadasManejador,
                consultarTiposParticipantesEnEjecucionesFinalizadasManejador,
                consultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadasManejador,
                consultarNivelesFormacionEstudiantesEnEjecucionesFinalizadasManejador,
                consultarIndicadoresEnEjecucionesFinalizadasManejador,
                contarParticipantesTotalesManejador,
                contarAsistenciasTotalesManejador,
                contarEjecucionesTotalesManejador,
                consultarEstadisticasParticipantesPorEstructuraManejador,
                consultarEstadisticasParticipantesPorMesManejador,
                contarPoblacionTotalManejador
        );
    }

    @Test
    void deberiaConsultarPorArea() {
        String identificador = "area-1";
        List<ActividadDTO> actividades = List.of(new ActividadDTO());
        when(consultarActividadesPorAreaManejador.ejecutar(identificador)).thenReturn(actividades);

        List<ActividadDTO> resultado = controlador.consultarPorArea(identificador);

        assertEquals(1, resultado.size());
        verify(consultarActividadesPorAreaManejador).ejecutar(identificador);
    }

    @Test
    void deberiaConsultarPorDireccion() {
        String identificador = "dir-1";
        List<ActividadDTO> actividades = List.of(new ActividadDTO());
        when(consultarActividadesPorDireccionManejador.ejecutar(identificador)).thenReturn(actividades);

        List<ActividadDTO> resultado = controlador.consultarPorDireccion(identificador);

        assertEquals(1, resultado.size());
        verify(consultarActividadesPorDireccionManejador).ejecutar(identificador);
    }

    @Test
    void deberiaConsultarPorSubarea() {
        String identificador = "sub-1";
        List<ActividadDTO> actividades = List.of(new ActividadDTO());
        when(consultarActividadesPorSubareaManejador.ejecutar(identificador)).thenReturn(actividades);

        List<ActividadDTO> resultado = controlador.consultarPorSubarea(identificador);

        assertEquals(1, resultado.size());
        verify(consultarActividadesPorSubareaManejador).ejecutar(identificador);
    }

    @Test
    void deberiaConsultarEjecuciones() {
        String identificador = "act-1";
        List<EjecucionActividadDTO> ejecuciones = List.of(new EjecucionActividadDTO());
        when(consultarEjecucionesPorActividadManejador.ejecutar(identificador)).thenReturn(ejecuciones);

        List<EjecucionActividadDTO> resultado = controlador.consultarEjecuciones(identificador);

        assertEquals(1, resultado.size());
        verify(consultarEjecucionesPorActividadManejador).ejecutar(identificador);
    }

    @Test
    void deberiaConsultarParticipantesPorEjecucionActividad() {
        UUID id = UUID.randomUUID();
        List<ParticipanteDTO> participantes = List.of(new ParticipanteDTO());
        when(consultarParticipantesPorEjecucionActividadManejador.ejecutar(id)).thenReturn(participantes);

        List<ParticipanteDTO> resultado = controlador.consultarParticipantesPorEjecucionActividad(id.toString());

        assertEquals(1, resultado.size());
        verify(consultarParticipantesPorEjecucionActividadManejador).ejecutar(id);
    }

    @Test
    void deberiaConsultarParticipantesPaginado() {
        UUID id = UUID.randomUUID();
        Page<ParticipanteDTO> pagina = new PageImpl<>(List.of(new ParticipanteDTO()));
        when(consultarParticipantesPorEjecucionActividadManejador.ejecutar(id, 0, 10)).thenReturn(pagina);

        Page<ParticipanteDTO> resultado = controlador.consultarParticipantesPaginado(id.toString(), 0, 10);

        assertEquals(1, resultado.getContent().size());
        verify(consultarParticipantesPorEjecucionActividadManejador).ejecutar(id, 0, 10);
    }

    @Test
    void deberiaConsultarMesesEjecucionesFinalizadas() {
        List<String> meses = List.of("Enero", "Febrero");
        when(consultarMesesEjecucionesFinalizadasManejador.ejecutar()).thenReturn(meses);

        List<String> resultado = controlador.consultarMesesEjecucionesFinalizadas();

        assertEquals(2, resultado.size());
        verify(consultarMesesEjecucionesFinalizadasManejador).ejecutar();
    }

    @Test
    void deberiaConsultarAnnosEjecucionesFinalizadas() {
        List<String> annos = List.of("2025", "2024");
        when(consultarAnnosEjecucionesFinalizadasManejador.ejecutar()).thenReturn(annos);

        List<String> resultado = controlador.consultarAnnosEjecucionesFinalizadas();

        assertEquals(2, resultado.size());
        verify(consultarAnnosEjecucionesFinalizadasManejador).ejecutar();
    }

    @Test
    void deberiaConsultarSemestresEnEjecucionesFinalizadas() {
        List<String> semestres = List.of("2025-1", "2024-2");
        when(consultarSemestresActividadesEnEjecucionesFinalizadasManejador.ejecutar()).thenReturn(semestres);

        List<String> resultado = controlador.consultarSemestresActividadesEnEjecucionesFinalizadas();

        assertEquals(2, resultado.size());
        verify(consultarSemestresActividadesEnEjecucionesFinalizadasManejador).ejecutar();
    }

    @Test
    void deberiaConsultarCentrosCostosEmpleadosEnEjecucionesFinalizadas() {
        List<String> centros = List.of("CC-01");
        when(consultarCentrosCostosEmpleadosEnEjecucionesFinalizadasManejador.ejecutar()).thenReturn(centros);

        List<String> resultado = controlador.consultarCentrosCostosEmpleadosEnEjecucionesFinalizadas();

        assertEquals(1, resultado.size());
        verify(consultarCentrosCostosEmpleadosEnEjecucionesFinalizadasManejador).ejecutar();
    }

    @Test
    void deberiaConsultarTiposParticipantesEnEjecucionesFinalizadas() {
        List<String> tipos = List.of("ESTUDIANTE", "EMPLEADO");
        when(consultarTiposParticipantesEnEjecucionesFinalizadasManejador.ejecutar()).thenReturn(tipos);

        List<String> resultado = controlador.consultarTiposParticipantesEnEjecucionesFinalizadas();

        assertEquals(2, resultado.size());
        verify(consultarTiposParticipantesEnEjecucionesFinalizadasManejador).ejecutar();
    }

    @Test
    void deberiaConsultarProgramasAcademicosEstudiantes() {
        List<String> programas = List.of("Ingenieria");
        when(consultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadasManejador.ejecutar()).thenReturn(programas);

        List<String> resultado = controlador.consultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadas();

        assertEquals(1, resultado.size());
        verify(consultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadasManejador).ejecutar();
    }

    @Test
    void deberiaConsultarNivelesFormacionEstudiantes() {
        List<String> niveles = List.of("PREGRADO");
        when(consultarNivelesFormacionEstudiantesEnEjecucionesFinalizadasManejador.ejecutar()).thenReturn(niveles);

        List<String> resultado = controlador.consultarNivelesFormacionEstudiantesEnEjecucionesFinalizadas();

        assertEquals(1, resultado.size());
        verify(consultarNivelesFormacionEstudiantesEnEjecucionesFinalizadasManejador).ejecutar();
    }

    @Test
    void deberiaConsultarIndicadoresEnEjecucionesFinalizadas() {
        List<String> indicadores = List.of("IND-01");
        when(consultarIndicadoresEnEjecucionesFinalizadasManejador.ejecutar()).thenReturn(indicadores);

        List<String> resultado = controlador.consultarIndicadoresEnEjecucionesFinalizadas();

        assertEquals(1, resultado.size());
        verify(consultarIndicadoresEnEjecucionesFinalizadasManejador).ejecutar();
    }

    @Test
    void deberiaContarParticipantesTotales() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        when(contarParticipantesTotalesManejador.ejecutar(filtro)).thenReturn(100L);

        Long resultado = controlador.contarParticipantesTotales(filtro);

        assertEquals(100L, resultado);
        verify(contarParticipantesTotalesManejador).ejecutar(filtro);
    }

    @Test
    void deberiaContarAsistenciasTotales() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        when(contarAsistenciasTotalesManejador.ejecutar(filtro)).thenReturn(200L);

        Long resultado = controlador.contarAsistenciasTotales(filtro);

        assertEquals(200L, resultado);
        verify(contarAsistenciasTotalesManejador).ejecutar(filtro);
    }

    @Test
    void deberiaContarEjecucionesTotales() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        when(contarEjecucionesTotalesManejador.ejecutar(filtro)).thenReturn(50L);

        Long resultado = controlador.contarEjecucionesTotales(filtro);

        assertEquals(50L, resultado);
        verify(contarEjecucionesTotalesManejador).ejecutar(filtro);
    }

    @Test
    void deberiaConsultarEstadisticasParticipantesPorEstructura() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        List<EstadisticaDTO> estadisticas = List.of(new EstadisticaDTO());
        when(consultarEstadisticasParticipantesPorEstructuraManejador.ejecutar(filtro)).thenReturn(estadisticas);

        List<EstadisticaDTO> resultado = controlador.consultarEstadisticasParticipantesPorEstructura(filtro);

        assertEquals(1, resultado.size());
        verify(consultarEstadisticasParticipantesPorEstructuraManejador).ejecutar(filtro);
    }

    @Test
    void deberiaConsultarEstadisticasParticipantesPorMes() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        List<EstadisticaMesDTO> estadisticas = List.of(new EstadisticaMesDTO());
        when(consultarEstadisticasParticipantesPorMesManejador.ejecutar(filtro)).thenReturn(estadisticas);

        List<EstadisticaMesDTO> resultado = controlador.consultarEstadisticasParticipantesPorMes(filtro);

        assertEquals(1, resultado.size());
        verify(consultarEstadisticasParticipantesPorMesManejador).ejecutar(filtro);
    }

    @Test
    void deberiaContarPoblacionTotal() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTO();
        when(contarPoblacionTotalManejador.ejecutar(filtro)).thenReturn(5000L);

        Long resultado = controlador.contarPoblacionTotal(filtro);

        assertEquals(5000L, resultado);
        verify(contarPoblacionTotalManejador).ejecutar(filtro);
    }

    @Test
    void deberiaConsultarPorAreaPaginado() {
        String identificador = "area-1";
        Page<ActividadDTO> pagina = new PageImpl<>(List.of(new ActividadDTO()));
        when(consultarActividadesPorAreaManejador.ejecutar(identificador, 0, 10)).thenReturn(pagina);

        Page<ActividadDTO> resultado = controlador.consultarPorAreaPaginado(identificador, 0, 10);

        assertEquals(1, resultado.getTotalElements());
        verify(consultarActividadesPorAreaManejador).ejecutar(identificador, 0, 10);
    }

    @Test
    void deberiaConsultarPorDireccionPaginado() {
        String identificador = "dir-1";
        Page<ActividadDTO> pagina = new PageImpl<>(List.of(new ActividadDTO()));
        when(consultarActividadesPorDireccionManejador.ejecutar(identificador, 0, 10)).thenReturn(pagina);

        Page<ActividadDTO> resultado = controlador.consultarPorDireccionPaginado(identificador, 0, 10);

        assertEquals(1, resultado.getTotalElements());
        verify(consultarActividadesPorDireccionManejador).ejecutar(identificador, 0, 10);
    }

    @Test
    void deberiaConsultarPorSubareaPaginado() {
        String identificador = "sub-1";
        Page<ActividadDTO> pagina = new PageImpl<>(List.of(new ActividadDTO()));
        when(consultarActividadesPorSubareaManejador.ejecutar(identificador, 0, 10)).thenReturn(pagina);

        Page<ActividadDTO> resultado = controlador.consultarPorSubareaPaginado(identificador, 0, 10);

        assertEquals(1, resultado.getTotalElements());
        verify(consultarActividadesPorSubareaManejador).ejecutar(identificador, 0, 10);
    }

    @Test
    void deberiaConsultarEjecucionesPaginado() {
        String identificador = "act-1";
        Page<EjecucionActividadDTO> pagina = new PageImpl<>(List.of(new EjecucionActividadDTO()));
        when(consultarEjecucionesPorActividadManejador.ejecutar(identificador, 0, 10)).thenReturn(pagina);

        Page<EjecucionActividadDTO> resultado = controlador.consultarEjecucionesPaginado(identificador, 0, 10);

        assertEquals(1, resultado.getTotalElements());
        verify(consultarEjecucionesPorActividadManejador).ejecutar(identificador, 0, 10);
    }
}
