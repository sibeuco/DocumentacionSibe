package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.enums.TipoArea;
import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.modelo.EjecucionActividad;
import co.edu.uco.sibe.dominio.puerto.comando.ActividadRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.ActividadRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import co.edu.uco.sibe.dominio.service.VincularActividadConAreaService;
import co.edu.uco.sibe.dominio.testdatabuilder.ActividadTestDataBuilder;
import co.edu.uco.sibe.dominio.testdatabuilder.EjecucionActividadTestDataBuilder;
import co.edu.uco.sibe.dominio.transversal.excepcion.AuthorizationException;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorDuplicadoExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.ACTIVIDAD_EXISTENTE_DURANTE_SEMESTRE_ACTUAL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuardarActividadUseCaseTest {

    @Mock
    private ActividadRepositorioComando actividadRepositorioComando;

    @Mock
    private ActividadRepositorioConsulta actividadRepositorioConsulta;

    @Mock
    private VincularActividadConAreaService vincularActividadConAreaService;

    @Mock
    private AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    private GuardarActividadUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GuardarActividadUseCase(actividadRepositorioComando, actividadRepositorioConsulta,
                vincularActividadConAreaService, autorizacionServicio);
    }

    @Test
    void deberiaGuardarActividadCuandoNombreNoExisteEnMismaAreaYSemestre() {
        UUID areaId = UUID.randomUUID();
        UUID identificadorGuardado = UUID.randomUUID();
        Actividad actividad = new ActividadTestDataBuilder().conNombre("Taller de Lectura").construir();
        EjecucionActividad ejecucion = new EjecucionActividadTestDataBuilder().construir();

        doNothing().when(autorizacionServicio).validarAccesoAArea(areaId);
        when(actividadRepositorioConsulta.existeActividadConNombreEnSemestreYArea(
                actividad.getNombre(), actividad.getSemestre(), areaId, TipoArea.AREA, null))
                .thenReturn(false);
        when(actividadRepositorioComando.guardar(actividad)).thenReturn(identificadorGuardado);

        UUID resultado = useCase.ejecutar(actividad, List.of(ejecucion), areaId, TipoArea.AREA);

        assertEquals(identificadorGuardado, resultado);
        verify(actividadRepositorioConsulta).existeActividadConNombreEnSemestreYArea(
                actividad.getNombre(), actividad.getSemestre(), areaId, TipoArea.AREA, null);
    }

    @Test
    void deberiaLanzarValorDuplicadoExcepcionCuandoNombreExisteEnMismaAreaYSemestre() {
        UUID areaId = UUID.randomUUID();
        Actividad actividad = new ActividadTestDataBuilder().conNombre("Taller de Lectura").construir();
        EjecucionActividad ejecucion = new EjecucionActividadTestDataBuilder().construir();

        doNothing().when(autorizacionServicio).validarAccesoAArea(areaId);
        when(actividadRepositorioConsulta.existeActividadConNombreEnSemestreYArea(
                actividad.getNombre(), actividad.getSemestre(), areaId, TipoArea.AREA, null))
                .thenReturn(true);

        ValorDuplicadoExcepcion excepcion = assertThrows(ValorDuplicadoExcepcion.class,
                () -> useCase.ejecutar(actividad, List.of(ejecucion), areaId, TipoArea.AREA));

        assertEquals(ACTIVIDAD_EXISTENTE_DURANTE_SEMESTRE_ACTUAL, excepcion.getMessage());
        verify(actividadRepositorioComando, never()).guardar(any());
    }

    @Test
    void deberiaPermitirNombreDuplicadoEnDiferenteArea() {
        UUID areaBienestar = UUID.randomUUID();
        UUID identificadorGuardado = UUID.randomUUID();
        Actividad actividad = new ActividadTestDataBuilder().conNombre("Taller de Lectura").construir();
        EjecucionActividad ejecucion = new EjecucionActividadTestDataBuilder().construir();

        doNothing().when(autorizacionServicio).validarAccesoAArea(areaBienestar);
        when(actividadRepositorioConsulta.existeActividadConNombreEnSemestreYArea(
                actividad.getNombre(), actividad.getSemestre(), areaBienestar, TipoArea.AREA, null))
                .thenReturn(false);
        when(actividadRepositorioComando.guardar(actividad)).thenReturn(identificadorGuardado);

        UUID resultado = useCase.ejecutar(actividad, List.of(ejecucion), areaBienestar, TipoArea.AREA);

        assertEquals(identificadorGuardado, resultado);
    }

    @Test
    void deberiaLanzarAuthorizationExceptionCuandoNoTieneAcceso() {
        UUID areaId = UUID.randomUUID();
        Actividad actividad = new ActividadTestDataBuilder().construir();
        EjecucionActividad ejecucion = new EjecucionActividadTestDataBuilder().construir();

        doThrow(new AuthorizationException("No tiene acceso")).when(autorizacionServicio).validarAccesoAArea(areaId);

        assertThrows(AuthorizationException.class,
                () -> useCase.ejecutar(actividad, List.of(ejecucion), areaId, TipoArea.AREA));

        verify(autorizacionServicio).validarAccesoAArea(areaId);
        verify(actividadRepositorioConsulta, never()).existeActividadConNombreEnSemestreYArea(any(), any(), any(), any(), any());
    }

    @Test
    void deberiaValidarAccesoADireccionCuandoTipoAreaEsDireccion() {
        UUID direccionId = UUID.randomUUID();
        UUID identificadorGuardado = UUID.randomUUID();
        Actividad actividad = new ActividadTestDataBuilder().conNombre("Taller de Lectura Comprensiva").construir();
        EjecucionActividad ejecucion = new EjecucionActividadTestDataBuilder().construir();

        doNothing().when(autorizacionServicio).validarAccesoADireccion(direccionId);
        when(actividadRepositorioConsulta.existeActividadConNombreEnSemestreYArea(
                actividad.getNombre(), actividad.getSemestre(), direccionId, TipoArea.DIRECCION, null))
                .thenReturn(false);
        when(actividadRepositorioComando.guardar(actividad)).thenReturn(identificadorGuardado);

        UUID resultado = useCase.ejecutar(actividad, List.of(ejecucion), direccionId, TipoArea.DIRECCION);

        assertEquals(identificadorGuardado, resultado);
        verify(autorizacionServicio).validarAccesoADireccion(direccionId);
    }

    @Test
    void deberiaValidarAccesoASubareaCuandoTipoAreaEsSubarea() {
        UUID subareaId = UUID.randomUUID();
        UUID identificadorGuardado = UUID.randomUUID();
        Actividad actividad = new ActividadTestDataBuilder().conNombre("Taller de Lectura Comprensiva").construir();
        EjecucionActividad ejecucion = new EjecucionActividadTestDataBuilder().construir();

        doNothing().when(autorizacionServicio).validarAccesoASubarea(subareaId);
        when(actividadRepositorioConsulta.existeActividadConNombreEnSemestreYArea(
                actividad.getNombre(), actividad.getSemestre(), subareaId, TipoArea.SUBAREA, null))
                .thenReturn(false);
        when(actividadRepositorioComando.guardar(actividad)).thenReturn(identificadorGuardado);

        UUID resultado = useCase.ejecutar(actividad, List.of(ejecucion), subareaId, TipoArea.SUBAREA);

        assertEquals(identificadorGuardado, resultado);
        verify(autorizacionServicio).validarAccesoASubarea(subareaId);
    }

    @Test
    void deberiaLanzarExcepcionCuandoFechaProgramadaEsAnteriorAFechaActual() {
        UUID areaId = UUID.randomUUID();
        Actividad actividad = new ActividadTestDataBuilder().conNombre("Taller de Lectura Comprensiva").construir();
        EjecucionActividad ejecucionPasada = new EjecucionActividadTestDataBuilder()
                .conFechaProgramada(java.time.LocalDate.of(2020, 1, 1))
                .construir();

        doNothing().when(autorizacionServicio).validarAccesoAArea(areaId);
        when(actividadRepositorioConsulta.existeActividadConNombreEnSemestreYArea(
                actividad.getNombre(), actividad.getSemestre(), areaId, TipoArea.AREA, null))
                .thenReturn(false);
        when(actividadRepositorioComando.guardar(actividad)).thenReturn(UUID.randomUUID());

        assertThrows(co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion.class,
                () -> useCase.ejecutar(actividad, List.of(ejecucionPasada), areaId, TipoArea.AREA));
    }

    @Test
    void deberiaLanzarExcepcionCuandoFechaNoPertenecenAlSegundoSemestre() {
        UUID areaId = UUID.randomUUID();
        Actividad actividad = new ActividadTestDataBuilder()
                .conNombre("Taller Segundo Semestre")
                .conSemestre("202602")
                .construir();
        EjecucionActividad ejecucion = new EjecucionActividadTestDataBuilder()
                .conFechaProgramada(java.time.LocalDate.of(2026, 4, 15))
                .construir();

        doNothing().when(autorizacionServicio).validarAccesoAArea(areaId);
        when(actividadRepositorioConsulta.existeActividadConNombreEnSemestreYArea(
                actividad.getNombre(), actividad.getSemestre(), areaId, TipoArea.AREA, null))
                .thenReturn(false);
        when(actividadRepositorioComando.guardar(actividad)).thenReturn(UUID.randomUUID());

        assertThrows(co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion.class,
                () -> useCase.ejecutar(actividad, List.of(ejecucion), areaId, TipoArea.AREA));
    }

    @Test
    void deberiaLanzarExcepcionCuandoFechaNoPertenecenAlPrimerSemestre() {
        UUID areaId = UUID.randomUUID();
        Actividad actividad = new ActividadTestDataBuilder()
                .conNombre("Taller Primer Semestre")
                .conSemestre("202601")
                .construir();
        EjecucionActividad ejecucion = new EjecucionActividadTestDataBuilder()
                .conFechaProgramada(java.time.LocalDate.of(2026, 9, 15))
                .construir();

        doNothing().when(autorizacionServicio).validarAccesoAArea(areaId);
        when(actividadRepositorioConsulta.existeActividadConNombreEnSemestreYArea(
                actividad.getNombre(), actividad.getSemestre(), areaId, TipoArea.AREA, null))
                .thenReturn(false);
        when(actividadRepositorioComando.guardar(actividad)).thenReturn(UUID.randomUUID());

        assertThrows(co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion.class,
                () -> useCase.ejecutar(actividad, List.of(ejecucion), areaId, TipoArea.AREA));
    }
}
