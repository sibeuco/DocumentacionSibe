package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.Miembro;
import co.edu.uco.sibe.dominio.modelo.ParticipanteEmpleado;
import co.edu.uco.sibe.dominio.transversal.excepcion.LongitudExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.PatronExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorObligatorioExcepcion;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ParticipanteEmpleadoReglaTest {

    private final ParticipanteEmpleadoRegla regla = ParticipanteEmpleadoRegla.obtenerInstancia();

    @Test
    void deberiaValidarCamposConDatosValidos() {
        ParticipanteEmpleado modelo = ParticipanteEmpleado.construir(UUID.randomUUID(), Miembro.construir(), null, "CARNET001", "M", null, null);
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
    void deberiaFallarConIdCarnetNulo() {
        ParticipanteEmpleado modelo = ParticipanteEmpleado.construir(UUID.randomUUID(), Miembro.construir(), null, null, "M", null, null);
        assertThrows(ValorObligatorioExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConIdCarnetExcedeMaximo() {
        ParticipanteEmpleado modelo = ParticipanteEmpleado.construir(UUID.randomUUID(), Miembro.construir(), null, "a".repeat(21), "M", null, null);
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConIdCarnetInvalido() {
        ParticipanteEmpleado modelo = ParticipanteEmpleado.construir(UUID.randomUUID(), Miembro.construir(), null, "CARNET{inv}", "M", null, null);
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConSexoNulo() {
        ParticipanteEmpleado modelo = ParticipanteEmpleado.construir(UUID.randomUUID(), Miembro.construir(), null, "CARNET001", null, null, null);
        assertThrows(ValorObligatorioExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConSexoExcedeMaximo() {
        ParticipanteEmpleado modelo = ParticipanteEmpleado.construir(UUID.randomUUID(), Miembro.construir(), null, "CARNET001", "MF", null, null);
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }
}
