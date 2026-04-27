package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.enums.TipoArea;
import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.modelo.EjecucionActividad;
import co.edu.uco.sibe.dominio.modelo.EstadoActividad;
import co.edu.uco.sibe.dominio.puerto.comando.ActividadRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.ActividadRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import co.edu.uco.sibe.dominio.service.ModificarVinculacionActividadConAreaService;
import co.edu.uco.sibe.dominio.testdatabuilder.ActividadTestDataBuilder;
import co.edu.uco.sibe.dominio.testdatabuilder.EjecucionActividadTestDataBuilder;
import co.edu.uco.sibe.dominio.testdatabuilder.EstadoActividadTestDataBuilder;
import co.edu.uco.sibe.dominio.transversal.excepcion.AuthorizationException;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorDuplicadoExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import co.edu.uco.sibe.dominio.dto.EjecucionActividadDTO;
import co.edu.uco.sibe.dominio.dto.EstadoActividadDTO;

import static co.edu.uco.sibe.dominio.transversal.constante.DatoConstante.*;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.ACTIVIDAD_CON_EJECUCION_EN_CURSO;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.ACTIVIDAD_EXISTENTE_DURANTE_SEMESTRE_ACTUAL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModificarActividadUseCaseTest {

    @Mock
    private ActividadRepositorioComando actividadRepositorioComando;

    @Mock
    private ActividadRepositorioConsulta actividadRepositorioConsulta;

    @Mock
    private ModificarVinculacionActividadConAreaService modificarVinculacionActividadConAreaService;

    @Mock
    private AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    private ModificarActividadUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ModificarActividadUseCase(actividadRepositorioComando, actividadRepositorioConsulta,
                modificarVinculacionActividadConAreaService, autorizacionServicio);
    }

    @Test
    void deberiaModificarActividadConEjecucionesFinalizadasConFechasPasadas() {
        UUID identificador = UUID.randomUUID();
        UUID areaId = UUID.randomUUID();
        Actividad actividad = new ActividadTestDataBuilder().conIdentificador(identificador).construir();

        EstadoActividad estadoFinalizada = new EstadoActividadTestDataBuilder().conNombre(FINALIZADA).construir();
        EjecucionActividad ejecucionFinalizada = new EjecucionActividadTestDataBuilder()
                .conFechaProgramada(LocalDate.of(2026, 1, 16))
                .conEstado(estadoFinalizada)
                .construir();

        EstadoActividad estadoPendiente = new EstadoActividadTestDataBuilder().conNombre(PENDIENTE).construir();
        EjecucionActividad ejecucionNueva = new EjecucionActividadTestDataBuilder()
                .conFechaProgramada(LocalDate.now().plusDays(7))
                .conEstado(estadoPendiente)
                .construir();

        doNothing().when(autorizacionServicio).validarAccesoAActividad(identificador);
        when(actividadRepositorioConsulta.consultarPorIdentificador(identificador)).thenReturn(actividad);
        when(actividadRepositorioConsulta.consultarFechasProgramadasPorActividad(actividad))
                .thenReturn(List.of());
        when(actividadRepositorioConsulta.existeActividadConNombreEnSemestreYArea(
                actividad.getNombre(), actividad.getSemestre(), areaId, TipoArea.AREA, identificador))
                .thenReturn(false);

        UUID resultado = useCase.ejecutar(actividad, List.of(ejecucionFinalizada, ejecucionNueva),
                areaId, TipoArea.AREA, identificador);

        assertEquals(identificador, resultado);
        verify(actividadRepositorioComando).modificarEjecuciones(List.of(ejecucionFinalizada, ejecucionNueva));
    }

    @Test
    void deberiaLanzarExcepcionCuandoEjecucionPendienteTieneFechaPasada() {
        UUID identificador = UUID.randomUUID();
        UUID areaId = UUID.randomUUID();
        Actividad actividad = new ActividadTestDataBuilder().conIdentificador(identificador).construir();

        EstadoActividad estadoPendiente = new EstadoActividadTestDataBuilder().conNombre(PENDIENTE).construir();
        EjecucionActividad ejecucionPendientePasada = new EjecucionActividadTestDataBuilder()
                .conFechaProgramada(LocalDate.of(2026, 1, 16))
                .conEstado(estadoPendiente)
                .construir();

        doNothing().when(autorizacionServicio).validarAccesoAActividad(identificador);
        when(actividadRepositorioConsulta.consultarPorIdentificador(identificador)).thenReturn(actividad);
        when(actividadRepositorioConsulta.consultarFechasProgramadasPorActividad(actividad))
                .thenReturn(List.of());
        when(actividadRepositorioConsulta.existeActividadConNombreEnSemestreYArea(
                actividad.getNombre(), actividad.getSemestre(), areaId, TipoArea.AREA, identificador))
                .thenReturn(false);

        assertThrows(ValorInvalidoExcepcion.class,
                () -> useCase.ejecutar(actividad, List.of(ejecucionPendientePasada),
                        areaId, TipoArea.AREA, identificador));
    }

    @Test
    void deberiaPermitirAgregarNuevasFechasFuturasCuandoExistenFinalizadas() {
        UUID identificador = UUID.randomUUID();
        UUID areaId = UUID.randomUUID();
        Actividad actividad = new ActividadTestDataBuilder().conIdentificador(identificador).construir();

        EstadoActividad estadoFinalizada = new EstadoActividadTestDataBuilder().conNombre(FINALIZADA).construir();
        EjecucionActividad finalizada1 = new EjecucionActividadTestDataBuilder()
                .conFechaProgramada(LocalDate.of(2026, 1, 16))
                .conEstado(estadoFinalizada).construir();
        EjecucionActividad finalizada2 = new EjecucionActividadTestDataBuilder()
                .conFechaProgramada(LocalDate.of(2026, 2, 18))
                .conEstado(estadoFinalizada).construir();

        EstadoActividad estadoPendiente = new EstadoActividadTestDataBuilder().conNombre(PENDIENTE).construir();
        EjecucionActividad nuevaFutura = new EjecucionActividadTestDataBuilder()
                .conFechaProgramada(LocalDate.now().plusDays(7))
                .conEstado(estadoPendiente).construir();

        doNothing().when(autorizacionServicio).validarAccesoAActividad(identificador);
        when(actividadRepositorioConsulta.consultarPorIdentificador(identificador)).thenReturn(actividad);
        when(actividadRepositorioConsulta.consultarFechasProgramadasPorActividad(actividad))
                .thenReturn(List.of());
        when(actividadRepositorioConsulta.existeActividadConNombreEnSemestreYArea(
                actividad.getNombre(), actividad.getSemestre(), areaId, TipoArea.AREA, identificador))
                .thenReturn(false);

        UUID resultado = useCase.ejecutar(actividad,
                List.of(finalizada1, finalizada2, nuevaFutura), areaId, TipoArea.AREA, identificador);

        assertEquals(identificador, resultado);
        verify(actividadRepositorioComando).modificarEjecuciones(List.of(finalizada1, finalizada2, nuevaFutura));
    }

    @Test
    void deberiaValidarSemestreParaTodasLasEjecucionesSinImportarEstado() {
        UUID identificador = UUID.randomUUID();
        UUID areaId = UUID.randomUUID();
        Actividad actividad = new ActividadTestDataBuilder()
                .conIdentificador(identificador)
                .conSemestre("202601")
                .construir();

        EstadoActividad estadoFinalizada = new EstadoActividadTestDataBuilder().conNombre(FINALIZADA).construir();
        EjecucionActividad ejecucionSegundoSemestre = new EjecucionActividadTestDataBuilder()
                .conFechaProgramada(LocalDate.of(2026, 9, 15))
                .conEstado(estadoFinalizada)
                .construir();

        doNothing().when(autorizacionServicio).validarAccesoAActividad(identificador);
        when(actividadRepositorioConsulta.consultarPorIdentificador(identificador)).thenReturn(actividad);
        when(actividadRepositorioConsulta.consultarFechasProgramadasPorActividad(actividad))
                .thenReturn(List.of());
        when(actividadRepositorioConsulta.existeActividadConNombreEnSemestreYArea(
                actividad.getNombre(), actividad.getSemestre(), areaId, TipoArea.AREA, identificador))
                .thenReturn(false);

        assertThrows(ValorInvalidoExcepcion.class,
                () -> useCase.ejecutar(actividad, List.of(ejecucionSegundoSemestre),
                        areaId, TipoArea.AREA, identificador));
    }

    @Test
    void deberiaLanzarValorDuplicadoExcepcionCuandoNombreExisteEnMismaArea() {
        UUID identificador = UUID.randomUUID();
        UUID areaId = UUID.randomUUID();
        Actividad actividad = new ActividadTestDataBuilder().conIdentificador(identificador).construir();
        EjecucionActividad ejecucion = new EjecucionActividadTestDataBuilder().construir();

        doNothing().when(autorizacionServicio).validarAccesoAActividad(identificador);
        when(actividadRepositorioConsulta.consultarPorIdentificador(identificador)).thenReturn(actividad);
        when(actividadRepositorioConsulta.consultarFechasProgramadasPorActividad(actividad))
                .thenReturn(List.of());
        when(actividadRepositorioConsulta.existeActividadConNombreEnSemestreYArea(
                actividad.getNombre(), actividad.getSemestre(), areaId, TipoArea.AREA, identificador))
                .thenReturn(true);

        ValorDuplicadoExcepcion excepcion = assertThrows(ValorDuplicadoExcepcion.class,
                () -> useCase.ejecutar(actividad, List.of(ejecucion), areaId, TipoArea.AREA, identificador));

        assertEquals(ACTIVIDAD_EXISTENTE_DURANTE_SEMESTRE_ACTUAL, excepcion.getMessage());
        verify(actividadRepositorioComando, never()).modificar(any());
    }

    @Test
    void deberiaLanzarAuthorizationExceptionCuandoNoTieneAccesoAActividad() {
        UUID identificador = UUID.randomUUID();
        UUID areaId = UUID.randomUUID();
        Actividad actividad = new ActividadTestDataBuilder().construir();
        EjecucionActividad ejecucion = new EjecucionActividadTestDataBuilder().construir();

        doThrow(new AuthorizationException("No tiene acceso")).when(autorizacionServicio)
                .validarAccesoAActividad(identificador);

        assertThrows(AuthorizationException.class,
                () -> useCase.ejecutar(actividad, List.of(ejecucion), areaId, TipoArea.AREA, identificador));

        verify(autorizacionServicio).validarAccesoAActividad(identificador);
        verify(actividadRepositorioConsulta, never()).consultarPorIdentificador(any());
    }

    @Test
    void deberiaValidarAccesoADireccionCuandoTipoAreaEsDireccion() {
        UUID identificador = UUID.randomUUID();
        UUID direccionId = UUID.randomUUID();
        Actividad actividad = new ActividadTestDataBuilder().conIdentificador(identificador).construir();
        EstadoActividad estadoPendiente = new EstadoActividadTestDataBuilder().conNombre(PENDIENTE).construir();
        EjecucionActividad ejecucion = new EjecucionActividadTestDataBuilder()
                .conFechaProgramada(LocalDate.now().plusDays(7)).conEstado(estadoPendiente).construir();

        doNothing().when(autorizacionServicio).validarAccesoAActividad(identificador);
        doNothing().when(autorizacionServicio).validarAccesoADireccion(direccionId);
        when(actividadRepositorioConsulta.consultarPorIdentificador(identificador)).thenReturn(actividad);
        when(actividadRepositorioConsulta.consultarFechasProgramadasPorActividad(actividad))
                .thenReturn(List.of());
        when(actividadRepositorioConsulta.existeActividadConNombreEnSemestreYArea(
                actividad.getNombre(), actividad.getSemestre(), direccionId, TipoArea.DIRECCION, identificador))
                .thenReturn(false);

        UUID resultado = useCase.ejecutar(actividad, List.of(ejecucion), direccionId, TipoArea.DIRECCION, identificador);
        assertEquals(identificador, resultado);
        verify(autorizacionServicio).validarAccesoADireccion(direccionId);
    }

    @Test
    void deberiaValidarAccesoASubareaCuandoTipoAreaEsSubarea() {
        UUID identificador = UUID.randomUUID();
        UUID subareaId = UUID.randomUUID();
        Actividad actividad = new ActividadTestDataBuilder().conIdentificador(identificador).construir();
        EstadoActividad estadoPendiente = new EstadoActividadTestDataBuilder().conNombre(PENDIENTE).construir();
        EjecucionActividad ejecucion = new EjecucionActividadTestDataBuilder()
                .conFechaProgramada(LocalDate.now().plusDays(7)).conEstado(estadoPendiente).construir();

        doNothing().when(autorizacionServicio).validarAccesoAActividad(identificador);
        doNothing().when(autorizacionServicio).validarAccesoASubarea(subareaId);
        when(actividadRepositorioConsulta.consultarPorIdentificador(identificador)).thenReturn(actividad);
        when(actividadRepositorioConsulta.consultarFechasProgramadasPorActividad(actividad))
                .thenReturn(List.of());
        when(actividadRepositorioConsulta.existeActividadConNombreEnSemestreYArea(
                actividad.getNombre(), actividad.getSemestre(), subareaId, TipoArea.SUBAREA, identificador))
                .thenReturn(false);

        UUID resultado = useCase.ejecutar(actividad, List.of(ejecucion), subareaId, TipoArea.SUBAREA, identificador);
        assertEquals(identificador, resultado);
        verify(autorizacionServicio).validarAccesoASubarea(subareaId);
    }

    @Test
    void deberiaLanzarExcepcionCuandoFechaNoPertenecenAlSegundoSemestre() {
        UUID identificador = UUID.randomUUID();
        UUID areaId = UUID.randomUUID();
        Actividad actividad = new ActividadTestDataBuilder()
                .conIdentificador(identificador)
                .conSemestre("202602")
                .construir();
        EstadoActividad estadoFinalizada = new EstadoActividadTestDataBuilder().conNombre(FINALIZADA).construir();
        EjecucionActividad ejecucion = new EjecucionActividadTestDataBuilder()
                .conFechaProgramada(LocalDate.of(2026, 3, 15))
                .conEstado(estadoFinalizada)
                .construir();

        doNothing().when(autorizacionServicio).validarAccesoAActividad(identificador);
        when(actividadRepositorioConsulta.consultarPorIdentificador(identificador)).thenReturn(actividad);
        when(actividadRepositorioConsulta.consultarFechasProgramadasPorActividad(actividad))
                .thenReturn(List.of());
        when(actividadRepositorioConsulta.existeActividadConNombreEnSemestreYArea(
                actividad.getNombre(), actividad.getSemestre(), areaId, TipoArea.AREA, identificador))
                .thenReturn(false);

        assertThrows(ValorInvalidoExcepcion.class,
                () -> useCase.ejecutar(actividad, List.of(ejecucion), areaId, TipoArea.AREA, identificador));
    }

    @Test
    void deberiaLanzarExcepcionCuandoActividadNoExisteConIdentificador() {
        UUID identificador = UUID.randomUUID();
        UUID areaId = UUID.randomUUID();
        Actividad actividad = new ActividadTestDataBuilder().conIdentificador(identificador).construir();
        EjecucionActividad ejecucion = new EjecucionActividadTestDataBuilder().construir();

        doNothing().when(autorizacionServicio).validarAccesoAActividad(identificador);
        when(actividadRepositorioConsulta.consultarPorIdentificador(identificador)).thenReturn(null);

        assertThrows(ValorDuplicadoExcepcion.class,
                () -> useCase.ejecutar(actividad, List.of(ejecucion), areaId, TipoArea.AREA, identificador));
    }

    @Test
    void deberiaLanzarExcepcionCuandoActividadTieneEjecucionEnCurso() {
        UUID identificador = UUID.randomUUID();
        UUID areaId = UUID.randomUUID();
        Actividad actividad = new ActividadTestDataBuilder().conIdentificador(identificador).construir();

        EstadoActividad estadoEnCurso = new EstadoActividadTestDataBuilder().conNombre(EN_CURSO).construir();
        EjecucionActividad ejecucion = new EjecucionActividadTestDataBuilder()
                .conFechaProgramada(LocalDate.now().plusDays(7))
                .conEstado(estadoEnCurso)
                .construir();

        EjecucionActividadDTO ejecucionDTO = new EjecucionActividadDTO();
        ejecucionDTO.setIdentificador(UUID.randomUUID().toString());
        ejecucionDTO.setFechaProgramada(LocalDate.now().plusDays(7).toString());
        ejecucionDTO.setEstadoActividad(new EstadoActividadDTO(UUID.randomUUID().toString(), EN_CURSO));

        doNothing().when(autorizacionServicio).validarAccesoAActividad(identificador);
        when(actividadRepositorioConsulta.consultarPorIdentificador(identificador)).thenReturn(actividad);
        when(actividadRepositorioConsulta.consultarFechasProgramadasPorActividad(actividad))
                .thenReturn(List.of(ejecucionDTO));

        ValorInvalidoExcepcion excepcion = assertThrows(ValorInvalidoExcepcion.class,
                () -> useCase.ejecutar(actividad, List.of(ejecucion), areaId, TipoArea.AREA, identificador));

        assertEquals(ACTIVIDAD_CON_EJECUCION_EN_CURSO, excepcion.getMessage());
        verify(actividadRepositorioComando, never()).modificar(any());
        verify(actividadRepositorioComando, never()).modificarEjecuciones(any());
    }
}
