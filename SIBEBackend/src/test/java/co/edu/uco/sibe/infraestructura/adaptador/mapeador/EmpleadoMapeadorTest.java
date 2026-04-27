package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.modelo.Empleado;
import co.edu.uco.sibe.dominio.modelo.CentroCostos;
import co.edu.uco.sibe.dominio.modelo.CiudadResidencia;
import co.edu.uco.sibe.dominio.modelo.RelacionLaboral;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpleadoMapeadorTest {

    @Mock
    private InternoCiudadResidenciaMapeador internoCiudadResidenciaMapeador;

    @Mock
    private EmpleadoRelacionLaboralMapeador empleadoRelacionLaboralMapeador;

    @Mock
    private EmpleadoCentroCostosMapeador empleadoCentroCostosMapeador;

    private EmpleadoMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new EmpleadoMapeador(internoCiudadResidenciaMapeador, empleadoRelacionLaboralMapeador, empleadoCentroCostosMapeador);
    }

    @Test
    void deberiaConstruirModeloDesdeEntidad() {
        UUID id = UUID.randomUUID();
        CiudadResidenciaEntidad ciudadEntidad = new CiudadResidenciaEntidad(UUID.randomUUID(), "Medellín");
        InternoCiudadResidenciaEntidad internoCiudadEntidad = new InternoCiudadResidenciaEntidad(UUID.randomUUID(), ciudadEntidad);
        RelacionLaboralEntidad rlEntidad = new RelacionLaboralEntidad(UUID.randomUUID(), "RL-001", "Planta");
        EmpleadoRelacionLaboralEntidad erlEntidad = new EmpleadoRelacionLaboralEntidad(UUID.randomUUID(), rlEntidad);
        CentroCostosEntidad ccEntidad = new CentroCostosEntidad(UUID.randomUUID(), "CC-001", "Centro");
        EmpleadoCentroCostosEntidad eccEntidad = new EmpleadoCentroCostosEntidad(UUID.randomUUID(), ccEntidad);

        EmpleadoEntidad entidad = new EmpleadoEntidad(id, "Juan Pérez", "123456", internoCiudadEntidad, "CARNET-001", "M", erlEntidad, eccEntidad);

        CiudadResidencia ciudadModelo = CiudadResidencia.construir(UUID.randomUUID(), "Medellín");
        RelacionLaboral rlModelo = RelacionLaboral.construir(UUID.randomUUID(), "RL-001", "Planta");
        CentroCostos ccModelo = CentroCostos.construir(UUID.randomUUID(), "CC-001", "Centro");

        when(internoCiudadResidenciaMapeador.construirModelo(internoCiudadEntidad)).thenReturn(ciudadModelo);
        when(empleadoRelacionLaboralMapeador.construirModelo(erlEntidad)).thenReturn(rlModelo);
        when(empleadoCentroCostosMapeador.construirModelo(eccEntidad)).thenReturn(ccModelo);

        Empleado modelo = mapeador.construirModelo(entidad);

        assertEquals(id, modelo.getIdentificador());
        assertEquals("Juan Pérez", modelo.getNombreCompleto());
        assertEquals("123456", modelo.getNumeroIdentificacion());
    }

    @Test
    void deberiaConstruirEntidadDesdeModelo() {
        UUID id = UUID.randomUUID();
        CiudadResidencia ciudad = CiudadResidencia.construir(UUID.randomUUID(), "Bogotá");
        RelacionLaboral rl = RelacionLaboral.construir(UUID.randomUUID(), "RL-002", "Contratista");
        CentroCostos cc = CentroCostos.construir(UUID.randomUUID(), "CC-002", "Otro centro");
        Empleado empleado = Empleado.construir(id, "María López", "789012", ciudad, "CARNET-002", "F", rl, cc);

        InternoCiudadResidenciaEntidad internoCiudadEntidad = mock(InternoCiudadResidenciaEntidad.class);
        EmpleadoRelacionLaboralEntidad erlEntidad = mock(EmpleadoRelacionLaboralEntidad.class);
        EmpleadoCentroCostosEntidad eccEntidad = mock(EmpleadoCentroCostosEntidad.class);

        when(internoCiudadResidenciaMapeador.construirEntidad(ciudad)).thenReturn(internoCiudadEntidad);
        when(empleadoRelacionLaboralMapeador.construirEntidad(rl)).thenReturn(erlEntidad);
        when(empleadoCentroCostosMapeador.construirEntidad(cc)).thenReturn(eccEntidad);

        EmpleadoEntidad entidad = mapeador.construirEntidad(empleado);

        assertEquals(id, entidad.getIdentificador());
        assertEquals("María López", entidad.getNombreCompleto());
    }

    @Test
    void deberiaModificarEntidad() {
        UUID id = UUID.randomUUID();
        CiudadResidenciaEntidad ciudadEntidad = new CiudadResidenciaEntidad(UUID.randomUUID(), "Medellín");
        InternoCiudadResidenciaEntidad internoCiudadEntidad = new InternoCiudadResidenciaEntidad(UUID.randomUUID(), ciudadEntidad);
        RelacionLaboralEntidad rlEntidad = new RelacionLaboralEntidad(UUID.randomUUID(), "RL-001", "Planta");
        EmpleadoRelacionLaboralEntidad erlEntidad = new EmpleadoRelacionLaboralEntidad(UUID.randomUUID(), rlEntidad);
        CentroCostosEntidad ccEntidad = new CentroCostosEntidad(UUID.randomUUID(), "CC-001", "Centro");
        EmpleadoCentroCostosEntidad eccEntidad = new EmpleadoCentroCostosEntidad(UUID.randomUUID(), ccEntidad);
        EmpleadoEntidad entidad = new EmpleadoEntidad(id, "Original", "000", internoCiudadEntidad, "C-OLD", "M", erlEntidad, eccEntidad);

        CiudadResidencia nuevaCiudad = CiudadResidencia.construir(UUID.randomUUID(), "Cali");
        RelacionLaboral nuevaRL = RelacionLaboral.construir(UUID.randomUUID(), "RL-NEW", "Nueva");
        CentroCostos nuevoCC = CentroCostos.construir(UUID.randomUUID(), "CC-NEW", "Nuevo");
        Empleado empleado = Empleado.construir(UUID.randomUUID(), "Nuevo Nombre", "999", nuevaCiudad, "C-NEW", "F", nuevaRL, nuevoCC);

        mapeador.modificarEntidad(entidad, empleado);

        assertEquals("Nuevo Nombre", entidad.getNombreCompleto());
        assertEquals("999", entidad.getNumeroIdentificacion());
        assertEquals("C-NEW", entidad.getIdCarnet());
        assertEquals("F", entidad.getSexo());
    }
}
