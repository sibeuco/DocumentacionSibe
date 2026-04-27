package co.edu.uco.sibe.infraestructura.adaptador.repositorio.comando;

import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.modelo.Direccion;
import co.edu.uco.sibe.infraestructura.adaptador.dao.ActividadDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.DireccionDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.ActividadEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.DireccionEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.DireccionMapeador;
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
class DireccionRepositorioComandoImplementacionTest {

    @Mock private DireccionDAO direccionDAO;
    @Mock private DireccionMapeador direccionMapeador;
    @Mock private ActividadDAO actividadDAO;

    private DireccionRepositorioComandoImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new DireccionRepositorioComandoImplementacion(direccionDAO, direccionMapeador, actividadDAO);
    }

    @Test
    void deberiaGuardarDireccion() {
        Direccion direccion = mock(Direccion.class);
        DireccionEntidad entidad = mock(DireccionEntidad.class);
        UUID id = UUID.randomUUID();
        when(direccionMapeador.construirEntidad(direccion)).thenReturn(entidad);
        when(direccionDAO.save(entidad)).thenReturn(entidad);
        when(entidad.getIdentificador()).thenReturn(id);

        UUID resultado = repositorio.guardar(direccion);

        assertEquals(id, resultado);
    }

    @Test
    void deberiaGuardarActividadCuandoAmbosExisten() {
        UUID direccionId = UUID.randomUUID();
        UUID actividadId = UUID.randomUUID();
        Actividad actividad = mock(Actividad.class);
        when(actividad.getIdentificador()).thenReturn(actividadId);

        DireccionEntidad direccionEntidad = mock(DireccionEntidad.class);
        ActividadEntidad actividadEntidad = mock(ActividadEntidad.class);
        when(direccionDAO.findById(direccionId)).thenReturn(Optional.of(direccionEntidad));
        when(actividadDAO.findById(actividadId)).thenReturn(Optional.of(actividadEntidad));
        when(direccionEntidad.getActividades()).thenReturn(new ArrayList<>());
        when(direccionDAO.save(direccionEntidad)).thenReturn(direccionEntidad);
        when(direccionEntidad.getIdentificador()).thenReturn(direccionId);

        UUID resultado = repositorio.guardarActividad(actividad, direccionId);

        assertEquals(direccionId, resultado);
        verify(direccionDAO).save(direccionEntidad);
    }

    @Test
    void deberiaRetornarIdOriginalCuandoDireccionNoExisteAlGuardarActividad() {
        UUID direccionId = UUID.randomUUID();
        UUID actividadId = UUID.randomUUID();
        Actividad actividad = mock(Actividad.class);
        when(actividad.getIdentificador()).thenReturn(actividadId);
        when(direccionDAO.findById(direccionId)).thenReturn(Optional.empty());
        when(actividadDAO.findById(actividadId)).thenReturn(Optional.of(mock(ActividadEntidad.class)));

        UUID resultado = repositorio.guardarActividad(actividad, direccionId);

        assertEquals(direccionId, resultado);
        verify(direccionDAO, never()).save(any());
    }

    @Test
    void deberiaEliminarActividadCuandoDireccionExiste() {
        UUID actividadId = UUID.randomUUID();
        UUID direccionId = UUID.randomUUID();
        Actividad actividad = mock(Actividad.class);
        Direccion direccion = mock(Direccion.class);
        when(actividad.getIdentificador()).thenReturn(actividadId);
        when(direccion.getIdentificador()).thenReturn(direccionId);

        ActividadEntidad actividadEntidad = mock(ActividadEntidad.class);
        when(actividadEntidad.getIdentificador()).thenReturn(actividadId);
        DireccionEntidad direccionEntidad = mock(DireccionEntidad.class);
        when(direccionDAO.findById(direccionId)).thenReturn(Optional.of(direccionEntidad));
        when(direccionEntidad.getActividades()).thenReturn(new ArrayList<>(java.util.List.of(actividadEntidad)));

        repositorio.eliminarActividad(actividad, direccion);

        verify(direccionDAO).save(direccionEntidad);
    }

    @Test
    void noDeberiaHacerNadaCuandoDireccionNoExisteAlEliminarActividad() {
        Actividad actividad = mock(Actividad.class);
        Direccion direccion = mock(Direccion.class);
        UUID direccionId = UUID.randomUUID();
        when(direccion.getIdentificador()).thenReturn(direccionId);
        when(direccionDAO.findById(direccionId)).thenReturn(Optional.empty());

        repositorio.eliminarActividad(actividad, direccion);

        verify(direccionDAO, never()).save(any());
    }
}
