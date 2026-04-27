package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.aplicacion.comando.DatosEmpleadoComando;
import co.edu.uco.sibe.dominio.modelo.CentroCostos;
import co.edu.uco.sibe.dominio.modelo.CiudadResidencia;
import co.edu.uco.sibe.dominio.modelo.Empleado;
import co.edu.uco.sibe.dominio.modelo.RelacionLaboral;
import co.edu.uco.sibe.dominio.puerto.consulta.EmpleadoRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmpleadoFabricaTest {

    @Mock
    private CiudadResidenciaFabrica ciudadResidenciaFabrica;
    @Mock
    private RelacionLaboralFabrica relacionLaboralFabrica;
    @Mock
    private CentroCostosFabrica centroCostosFabrica;
    @Mock
    private EmpleadoRepositorioConsulta empleadoRepositorioConsulta;

    private EmpleadoFabrica fabrica;

    @BeforeEach
    void setUp() {
        fabrica = new EmpleadoFabrica(ciudadResidenciaFabrica, relacionLaboralFabrica, centroCostosFabrica, empleadoRepositorioConsulta);
    }

    @Test
    void deberiaConstruirEmpleadoDesdeComando() {
        CiudadResidencia ciudad = CiudadResidencia.construir(UUID.randomUUID(), "Medellin");
        RelacionLaboral relacion = RelacionLaboral.construir(UUID.randomUUID(), "PLT", "Planta");
        CentroCostos centro = CentroCostos.construir(UUID.randomUUID(), "CC01", "Centro Principal");

        when(empleadoRepositorioConsulta.consultarPorIdentificador(any())).thenReturn(null);
        when(ciudadResidenciaFabrica.construir("Medellin")).thenReturn(ciudad);
        when(relacionLaboralFabrica.construir("PLT", "Planta")).thenReturn(relacion);
        when(centroCostosFabrica.construir("CC01", "Centro Principal")).thenReturn(centro);

        DatosEmpleadoComando comando = new DatosEmpleadoComando("Carlos Garcia", "M", "123456", "CAR001", "PLT", "Planta", "CC01", "Centro Principal", "05001", "Medellin");

        Empleado resultado = fabrica.construir(comando);

        assertNotNull(resultado.getIdentificador());
        assertEquals("Carlos Garcia", resultado.getNombreCompleto());
        assertEquals("123456", resultado.getNumeroIdentificacion());
        assertEquals(ciudad, resultado.getCiudadResidencia());
        assertEquals(relacion, resultado.getRelacionLaboral());
        assertEquals(centro, resultado.getCentroCostos());
    }
}
