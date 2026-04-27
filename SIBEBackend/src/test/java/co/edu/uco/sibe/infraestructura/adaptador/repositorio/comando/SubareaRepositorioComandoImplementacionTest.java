package co.edu.uco.sibe.infraestructura.adaptador.repositorio.comando;

import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.modelo.Subarea;
import co.edu.uco.sibe.infraestructura.adaptador.dao.ActividadDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.SubareaDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.ActividadEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.SubareaEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.SubareaMapeador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubareaRepositorioComandoImplementacionTest {

    @Mock private SubareaDAO subareaDAO;
    @Mock private SubareaMapeador subareaMapeador;
    @Mock private ActividadDAO actividadDAO;

    private SubareaRepositorioComandoImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new SubareaRepositorioComandoImplementacion(subareaDAO, subareaMapeador, actividadDAO);
    }

    @Test
    void deberiaGuardarSubarea() {
        Subarea subarea = mock(Subarea.class);
        SubareaEntidad entidad = mock(SubareaEntidad.class);
        UUID id = UUID.randomUUID();
        when(subareaMapeador.construirEntidad(subarea)).thenReturn(entidad);
        when(subareaDAO.save(entidad)).thenReturn(entidad);
        when(entidad.getIdentificador()).thenReturn(id);

        UUID resultado = repositorio.guardar(subarea);

        assertEquals(id, resultado);
    }

    @Test
    void deberiaGuardarActividadCuandoAmbosExisten() {
        UUID subareaId = UUID.randomUUID();
        UUID actividadId = UUID.randomUUID();
        Actividad actividad = mock(Actividad.class);
        when(actividad.getIdentificador()).thenReturn(actividadId);

        SubareaEntidad subareaEntidad = mock(SubareaEntidad.class);
        ActividadEntidad actividadEntidad = mock(ActividadEntidad.class);
        when(subareaDAO.findById(subareaId)).thenReturn(Optional.of(subareaEntidad));
        when(actividadDAO.findById(actividadId)).thenReturn(Optional.of(actividadEntidad));
        when(subareaEntidad.getActividades()).thenReturn(new ArrayList<>());
        when(subareaDAO.save(subareaEntidad)).thenReturn(subareaEntidad);
        when(subareaEntidad.getIdentificador()).thenReturn(subareaId);

        UUID resultado = repositorio.guardarActividad(actividad, subareaId);

        assertEquals(subareaId, resultado);
        verify(subareaDAO).save(subareaEntidad);
    }

    @Test
    void deberiaRetornarIdOriginalCuandoSubareaNoExisteAlGuardarActividad() {
        UUID subareaId = UUID.randomUUID();
        UUID actividadId = UUID.randomUUID();
        Actividad actividad = mock(Actividad.class);
        when(actividad.getIdentificador()).thenReturn(actividadId);
        when(subareaDAO.findById(subareaId)).thenReturn(Optional.empty());
        when(actividadDAO.findById(actividadId)).thenReturn(Optional.of(mock(ActividadEntidad.class)));

        UUID resultado = repositorio.guardarActividad(actividad, subareaId);

        assertEquals(subareaId, resultado);
        verify(subareaDAO, never()).save(any());
    }

    @Test
    void deberiaEliminarActividadCuandoSubareaExiste() {
        UUID actividadId = UUID.randomUUID();
        UUID subareaId = UUID.randomUUID();
        Actividad actividad = mock(Actividad.class);
        Subarea subarea = mock(Subarea.class);
        when(actividad.getIdentificador()).thenReturn(actividadId);
        when(subarea.getIdentificador()).thenReturn(subareaId);

        ActividadEntidad actividadEntidad = mock(ActividadEntidad.class);
        when(actividadEntidad.getIdentificador()).thenReturn(actividadId);
        SubareaEntidad subareaEntidad = mock(SubareaEntidad.class);
        when(subareaDAO.findById(subareaId)).thenReturn(Optional.of(subareaEntidad));
        when(subareaEntidad.getActividades()).thenReturn(new ArrayList<>(java.util.List.of(actividadEntidad)));

        repositorio.eliminarActividad(actividad, subarea);

        verify(subareaDAO).save(subareaEntidad);
    }

    @Test
    void noDeberiaHacerNadaCuandoSubareaNoExisteAlEliminarActividad() {
        Actividad actividad = mock(Actividad.class);
        Subarea subarea = mock(Subarea.class);
        UUID subareaId = UUID.randomUUID();
        when(subarea.getIdentificador()).thenReturn(subareaId);
        when(subareaDAO.findById(subareaId)).thenReturn(Optional.empty());

        repositorio.eliminarActividad(actividad, subarea);

        verify(subareaDAO, never()).save(any());
    }
}
