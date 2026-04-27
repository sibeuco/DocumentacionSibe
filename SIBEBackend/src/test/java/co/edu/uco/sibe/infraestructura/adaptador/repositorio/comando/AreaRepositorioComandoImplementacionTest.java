package co.edu.uco.sibe.infraestructura.adaptador.repositorio.comando;

import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.modelo.Area;
import co.edu.uco.sibe.infraestructura.adaptador.dao.ActividadDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.AreaDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.ActividadEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.AreaEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.AreaMapeador;
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
class AreaRepositorioComandoImplementacionTest {

    @Mock private AreaDAO areaDAO;
    @Mock private AreaMapeador areaMapeador;
    @Mock private ActividadDAO actividadDAO;

    private AreaRepositorioComandoImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new AreaRepositorioComandoImplementacion(areaDAO, areaMapeador, actividadDAO);
    }

    @Test
    void deberiaGuardarArea() {
        Area area = mock(Area.class);
        AreaEntidad entidad = mock(AreaEntidad.class);
        UUID id = UUID.randomUUID();
        when(areaMapeador.construirEntidad(area)).thenReturn(entidad);
        when(areaDAO.save(entidad)).thenReturn(entidad);
        when(entidad.getIdentificador()).thenReturn(id);

        UUID resultado = repositorio.guardar(area);

        assertEquals(id, resultado);
    }

    @Test
    void deberiaGuardarActividadCuandoAmbosExisten() {
        UUID areaId = UUID.randomUUID();
        UUID actividadId = UUID.randomUUID();
        Actividad actividad = mock(Actividad.class);
        when(actividad.getIdentificador()).thenReturn(actividadId);

        AreaEntidad areaEntidad = mock(AreaEntidad.class);
        ActividadEntidad actividadEntidad = mock(ActividadEntidad.class);
        when(areaDAO.findById(areaId)).thenReturn(Optional.of(areaEntidad));
        when(actividadDAO.findById(actividadId)).thenReturn(Optional.of(actividadEntidad));
        when(areaEntidad.getActividades()).thenReturn(new ArrayList<>());
        when(areaDAO.save(areaEntidad)).thenReturn(areaEntidad);
        when(areaEntidad.getIdentificador()).thenReturn(areaId);

        UUID resultado = repositorio.guardarActividad(actividad, areaId);

        assertEquals(areaId, resultado);
        verify(areaDAO).save(areaEntidad);
    }

    @Test
    void deberiaRetornarIdOriginalCuandoAreaNoExisteAlGuardarActividad() {
        UUID areaId = UUID.randomUUID();
        UUID actividadId = UUID.randomUUID();
        Actividad actividad = mock(Actividad.class);
        when(actividad.getIdentificador()).thenReturn(actividadId);
        when(areaDAO.findById(areaId)).thenReturn(Optional.empty());
        when(actividadDAO.findById(actividadId)).thenReturn(Optional.of(mock(ActividadEntidad.class)));

        UUID resultado = repositorio.guardarActividad(actividad, areaId);

        assertEquals(areaId, resultado);
        verify(areaDAO, never()).save(any());
    }

    @Test
    void deberiaEliminarActividadCuandoAreaExiste() {
        UUID actividadId = UUID.randomUUID();
        UUID areaId = UUID.randomUUID();
        Actividad actividad = mock(Actividad.class);
        Area area = mock(Area.class);
        when(actividad.getIdentificador()).thenReturn(actividadId);
        when(area.getIdentificador()).thenReturn(areaId);

        ActividadEntidad actividadEntidad = mock(ActividadEntidad.class);
        when(actividadEntidad.getIdentificador()).thenReturn(actividadId);
        AreaEntidad areaEntidad = mock(AreaEntidad.class);
        when(areaDAO.findById(areaId)).thenReturn(Optional.of(areaEntidad));
        when(areaEntidad.getActividades()).thenReturn(new ArrayList<>(java.util.List.of(actividadEntidad)));

        repositorio.eliminarActividad(actividad, area);

        verify(areaDAO).save(areaEntidad);
    }

    @Test
    void noDeberiaHacerNadaCuandoAreaNoExisteAlEliminarActividad() {
        Actividad actividad = mock(Actividad.class);
        Area area = mock(Area.class);
        UUID areaId = UUID.randomUUID();
        when(area.getIdentificador()).thenReturn(areaId);
        when(areaDAO.findById(areaId)).thenReturn(Optional.empty());

        repositorio.eliminarActividad(actividad, area);

        verify(areaDAO, never()).save(any());
    }
}
