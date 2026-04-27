package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.Empleado;
import co.edu.uco.sibe.dominio.transversal.excepcion.LongitudExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.PatronExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorObligatorioExcepcion;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EmpleadoReglaTest {

    private final EmpleadoRegla regla = EmpleadoRegla.obtenerInstancia();

    @Test
    void deberiaValidarCamposConDatosValidos() {
        Empleado modelo = Empleado.construir(UUID.randomUUID(), "Juan Carlos Perez", "1234567890", null, "CARNET001", "M", null, null);
        assertDoesNotThrow(() -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaValidarIdentificadorValido() {
        assertDoesNotThrow(() -> regla.validarIdentificador(UUID.randomUUID()));
    }

    @Test
    void deberiaFallarConIdentificadorNulo() {
        assertThrows(IllegalArgumentException.class, () -> regla.validarIdentificador(null));
    }

    @Test
    void deberiaFallarConNombreCompletoVacio() {
        Empleado modelo = Empleado.construir(UUID.randomUUID(), null, "1234567890", null, "CARNET001", "M", null, null);
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreCompletoCorto() {
        Empleado modelo = Empleado.construir(UUID.randomUUID(), "Juan", "1234567890", null, "CARNET001", "M", null, null);
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreCompletoExcedeMaximo() {
        Empleado modelo = Empleado.construir(UUID.randomUUID(), "a".repeat(101), "1234567890", null, "CARNET001", "M", null, null);
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreCompletoInvalido() {
        Empleado modelo = Empleado.construir(UUID.randomUUID(), "Juan123!@#", "1234567890", null, "CARNET001", "M", null, null);
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNumeroIdentificacionVacio() {
        Empleado modelo = Empleado.construir(UUID.randomUUID(), "Juan Carlos Perez", null, null, "CARNET001", "M", null, null);
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNumeroIdentificacionCorto() {
        Empleado modelo = Empleado.construir(UUID.randomUUID(), "Juan Carlos Perez", "123", null, "CARNET001", "M", null, null);
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNumeroIdentificacionExcedeMaximo() {
        Empleado modelo = Empleado.construir(UUID.randomUUID(), "Juan Carlos Perez", "1".repeat(13), null, "CARNET001", "M", null, null);
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNumeroIdentificacionInvalido() {
        Empleado modelo = Empleado.construir(UUID.randomUUID(), "Juan Carlos Perez", "ABC!@#DEF", null, "CARNET001", "M", null, null);
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConIdCarnetVacio() {
        Empleado modelo = Empleado.construir(UUID.randomUUID(), "Juan Carlos Perez", "1234567890", null, null, "M", null, null);
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConIdCarnetExcedeMaximo() {
        Empleado modelo = Empleado.construir(UUID.randomUUID(), "Juan Carlos Perez", "1234567890", null, "a".repeat(21), "M", null, null);
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConIdCarnetInvalido() {
        Empleado modelo = Empleado.construir(UUID.randomUUID(), "Juan Carlos Perez", "1234567890", null, "CARNET{inv}", "M", null, null);
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConSexoVacio() {
        Empleado modelo = Empleado.construir(UUID.randomUUID(), "Juan Carlos Perez", "1234567890", null, "CARNET001", null, null, null);
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConSexoExcedeMaximo() {
        Empleado modelo = Empleado.construir(UUID.randomUUID(), "Juan Carlos Perez", "1234567890", null, "CARNET001", "MF", null, null);
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }
}
