package co.edu.uco.sibe.dominio.modelo;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EmpleadoTest {

    @Test
    void deberiaConstruirEmpleadoConValoresPorDefecto() {
        Empleado empleado = Empleado.construir();

        assertNotNull(empleado);
        assertNotNull(empleado.getIdentificador());
        assertEquals("", empleado.getNombreCompleto());
        assertNotNull(empleado.getRelacionLaboral());
        assertNotNull(empleado.getCentroCostos());
    }

    @Test
    void deberiaConstruirEmpleadoConValoresNulosUsandoDefectos() {
        Empleado empleado = Empleado.construir(UUID.randomUUID(), null, null, null, null, null, null, null);

        assertNotNull(empleado);
        assertEquals("", empleado.getNombreCompleto());
        assertNotNull(empleado.getCiudadResidencia());
        assertNotNull(empleado.getRelacionLaboral());
        assertNotNull(empleado.getCentroCostos());
    }
}
