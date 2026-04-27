package co.edu.uco.sibe.infraestructura.adaptador.repositorio.comando;

import co.edu.uco.sibe.dominio.modelo.Empleado;
import co.edu.uco.sibe.infraestructura.adaptador.dao.CentroCostosDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.CiudadResidenciaDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.EmpleadoDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.RelacionLaboralDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.*;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.EmpleadoMapeador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpleadoRepositorioComandoImplementacionTest {

    @Mock private EmpleadoDAO empleadoDAO;
    @Mock private CiudadResidenciaDAO ciudadResidenciaDAO;
    @Mock private RelacionLaboralDAO relacionLaboralDAO;
    @Mock private CentroCostosDAO centroCostosDAO;
    @Mock private EmpleadoMapeador empleadoMapeador;

    private EmpleadoRepositorioComandoImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new EmpleadoRepositorioComandoImplementacion(
                empleadoDAO, ciudadResidenciaDAO, relacionLaboralDAO, centroCostosDAO, empleadoMapeador);
    }

    @Test
    void deberiaGuardarEmpleadoConDependenciasNuevas() {
        Empleado empleado = mock(Empleado.class);
        EmpleadoEntidad entidad = mock(EmpleadoEntidad.class);
        UUID id = UUID.randomUUID();
        UUID ciudadId = UUID.randomUUID();
        UUID relacionId = UUID.randomUUID();
        UUID centroId = UUID.randomUUID();

        InternoCiudadResidenciaEntidad ciudadRes = mock(InternoCiudadResidenciaEntidad.class);
        CiudadResidenciaEntidad ciudad = mock(CiudadResidenciaEntidad.class);
        EmpleadoRelacionLaboralEntidad relLab = mock(EmpleadoRelacionLaboralEntidad.class);
        RelacionLaboralEntidad relacion = mock(RelacionLaboralEntidad.class);
        EmpleadoCentroCostosEntidad centCost = mock(EmpleadoCentroCostosEntidad.class);
        CentroCostosEntidad centro = mock(CentroCostosEntidad.class);

        when(empleadoMapeador.construirEntidad(empleado)).thenReturn(entidad);
        when(entidad.getCiudadResidencia()).thenReturn(ciudadRes);
        when(ciudadRes.getCiudadResidencia()).thenReturn(ciudad);
        when(ciudad.getIdentificador()).thenReturn(ciudadId);
        when(entidad.getRelacionLaboral()).thenReturn(relLab);
        when(relLab.getRelacionLaboral()).thenReturn(relacion);
        when(relacion.getIdentificador()).thenReturn(relacionId);
        when(entidad.getCentroCostos()).thenReturn(centCost);
        when(centCost.getCentroCostos()).thenReturn(centro);
        when(centro.getIdentificador()).thenReturn(centroId);

        when(ciudadResidenciaDAO.findById(ciudadId)).thenReturn(Optional.empty());
        when(relacionLaboralDAO.findById(relacionId)).thenReturn(Optional.empty());
        when(centroCostosDAO.findById(centroId)).thenReturn(Optional.empty());
        when(empleadoDAO.save(entidad)).thenReturn(entidad);
        when(entidad.getIdentificador()).thenReturn(id);

        UUID resultado = repositorio.guardar(empleado);

        assertEquals(id, resultado);
        verify(ciudadResidenciaDAO).save(ciudad);
        verify(relacionLaboralDAO).save(relacion);
        verify(centroCostosDAO).save(centro);
    }

    @Test
    void deberiaGuardarEmpleadoSinGuardarDependenciasExistentes() {
        Empleado empleado = mock(Empleado.class);
        EmpleadoEntidad entidad = mock(EmpleadoEntidad.class);
        UUID id = UUID.randomUUID();
        UUID ciudadId = UUID.randomUUID();
        UUID relacionId = UUID.randomUUID();
        UUID centroId = UUID.randomUUID();

        InternoCiudadResidenciaEntidad ciudadRes = mock(InternoCiudadResidenciaEntidad.class);
        CiudadResidenciaEntidad ciudad = mock(CiudadResidenciaEntidad.class);
        EmpleadoRelacionLaboralEntidad relLab = mock(EmpleadoRelacionLaboralEntidad.class);
        RelacionLaboralEntidad relacion = mock(RelacionLaboralEntidad.class);
        EmpleadoCentroCostosEntidad centCost = mock(EmpleadoCentroCostosEntidad.class);
        CentroCostosEntidad centro = mock(CentroCostosEntidad.class);

        when(empleadoMapeador.construirEntidad(empleado)).thenReturn(entidad);
        when(entidad.getCiudadResidencia()).thenReturn(ciudadRes);
        when(ciudadRes.getCiudadResidencia()).thenReturn(ciudad);
        when(ciudad.getIdentificador()).thenReturn(ciudadId);
        when(entidad.getRelacionLaboral()).thenReturn(relLab);
        when(relLab.getRelacionLaboral()).thenReturn(relacion);
        when(relacion.getIdentificador()).thenReturn(relacionId);
        when(entidad.getCentroCostos()).thenReturn(centCost);
        when(centCost.getCentroCostos()).thenReturn(centro);
        when(centro.getIdentificador()).thenReturn(centroId);

        when(ciudadResidenciaDAO.findById(ciudadId)).thenReturn(Optional.of(ciudad));
        when(relacionLaboralDAO.findById(relacionId)).thenReturn(Optional.of(relacion));
        when(centroCostosDAO.findById(centroId)).thenReturn(Optional.of(centro));
        when(empleadoDAO.save(entidad)).thenReturn(entidad);
        when(entidad.getIdentificador()).thenReturn(id);

        UUID resultado = repositorio.guardar(empleado);

        assertEquals(id, resultado);
        verify(ciudadResidenciaDAO, never()).save(any());
        verify(relacionLaboralDAO, never()).save(any());
        verify(centroCostosDAO, never()).save(any());
    }

    @Test
    void deberiaModificarEmpleado() {
        Empleado empleado = mock(Empleado.class);
        UUID id = UUID.randomUUID();
        EmpleadoEntidad entidad = mock(EmpleadoEntidad.class);

        when(empleadoDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(empleadoDAO.save(entidad)).thenReturn(entidad);
        when(entidad.getIdentificador()).thenReturn(id);

        UUID resultado = repositorio.modificar(empleado, id);

        assertEquals(id, resultado);
        verify(empleadoMapeador).modificarEntidad(entidad, empleado);
    }
}
