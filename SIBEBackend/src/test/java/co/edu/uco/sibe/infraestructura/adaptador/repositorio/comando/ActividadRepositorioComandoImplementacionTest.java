package co.edu.uco.sibe.infraestructura.adaptador.repositorio.comando;

import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.modelo.EjecucionActividad;
import co.edu.uco.sibe.infraestructura.adaptador.dao.ActividadDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.EjecucionActividadDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.ActividadEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.EjecucionActividadEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.ActividadMapeador;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.EjecucionActividadMapeador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActividadRepositorioComandoImplementacionTest {

    @Mock private ActividadDAO actividadDAO;
    @Mock private ActividadMapeador actividadMapeador;
    @Mock private EjecucionActividadMapeador ejecucionActividadMapeador;
    @Mock private EjecucionActividadDAO ejecucionActividadDAO;

    private ActividadRepositorioComandoImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new ActividadRepositorioComandoImplementacion(
                actividadDAO, actividadMapeador, ejecucionActividadMapeador, ejecucionActividadDAO
        );
    }

    @Test
    void deberiaGuardarActividad() {
        UUID id = UUID.randomUUID();
        Actividad actividad = mock(Actividad.class);
        ActividadEntidad entidad = new ActividadEntidad();
        entidad.setIdentificador(id);

        when(actividadMapeador.construirEntidad(actividad)).thenReturn(entidad);
        when(actividadDAO.save(entidad)).thenReturn(entidad);

        UUID resultado = repositorio.guardar(actividad);

        assertEquals(id, resultado);
    }

    @Test
    void deberiaGuardarEjecucion() {
        UUID id = UUID.randomUUID();
        EjecucionActividad ejecucion = mock(EjecucionActividad.class);
        EjecucionActividadEntidad entidad = new EjecucionActividadEntidad();
        entidad.setIdentificador(id);

        when(ejecucionActividadMapeador.construirEntidad(ejecucion)).thenReturn(entidad);
        when(ejecucionActividadDAO.save(entidad)).thenReturn(entidad);

        UUID resultado = repositorio.guardarEjecucion(ejecucion);

        assertEquals(id, resultado);
    }

    @Test
    void deberiaModificarActividad() {
        UUID id = UUID.randomUUID();
        Actividad actividad = mock(Actividad.class);
        when(actividad.getIdentificador()).thenReturn(id);
        ActividadEntidad entidad = new ActividadEntidad();
        entidad.setIdentificador(id);

        when(actividadDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(actividadDAO.save(entidad)).thenReturn(entidad);

        UUID resultado = repositorio.modificar(actividad);

        assertEquals(id, resultado);
        verify(actividadMapeador).actualizarEntidad(entidad, actividad);
    }

    @Test
    void deberiaModificarEjecuciones() {
        UUID actId = UUID.randomUUID();
        Actividad actividad = mock(Actividad.class);
        when(actividad.getIdentificador()).thenReturn(actId);

        EjecucionActividad ejecucion = mock(EjecucionActividad.class);
        when(ejecucion.getActividad()).thenReturn(actividad);
        List<EjecucionActividad> ejecuciones = List.of(ejecucion);

        repositorio.modificarEjecuciones(ejecuciones);

        verify(ejecucionActividadMapeador).actualizarTodos(actId, ejecuciones);
    }

    @Test
    void noDeberiaModificarEjecucionesVacias() {
        repositorio.modificarEjecuciones(List.of());

        verifyNoInteractions(ejecucionActividadMapeador);
    }

    @Test
    void deberiaModificarEjecucion() {
        UUID id = UUID.randomUUID();
        EjecucionActividad ejecucion = mock(EjecucionActividad.class);
        when(ejecucion.getIdentificador()).thenReturn(id);
        EjecucionActividadEntidad entidad = new EjecucionActividadEntidad();

        when(ejecucionActividadDAO.findById(id)).thenReturn(Optional.of(entidad));

        repositorio.modificarEjecucion(ejecucion);

        verify(ejecucionActividadMapeador).actualizarEntidad(entidad, ejecucion);
        verify(ejecucionActividadDAO).save(entidad);
    }

    @Test
    void noDeberiaModificarEjecucionInexistente() {
        UUID id = UUID.randomUUID();
        EjecucionActividad ejecucion = mock(EjecucionActividad.class);
        when(ejecucion.getIdentificador()).thenReturn(id);

        when(ejecucionActividadDAO.findById(id)).thenReturn(Optional.empty());

        repositorio.modificarEjecucion(ejecucion);

        verifyNoInteractions(ejecucionActividadMapeador);
        verify(ejecucionActividadDAO, never()).save(any());
    }
}
