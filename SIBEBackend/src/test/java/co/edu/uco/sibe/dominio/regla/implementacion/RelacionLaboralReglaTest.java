package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.RelacionLaboral;
import co.edu.uco.sibe.dominio.transversal.excepcion.LongitudExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.PatronExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorObligatorioExcepcion;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RelacionLaboralReglaTest {

    private final RelacionLaboralRegla regla = RelacionLaboralRegla.obtenerInstancia();

    @Test
    void deberiaValidarCamposConDatosValidos() {
        RelacionLaboral modelo = RelacionLaboral.construir(UUID.randomUUID(), "RL01", "Planta");
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
    void deberiaFallarConCodigoVacio() {
        RelacionLaboral modelo = RelacionLaboral.construir(UUID.randomUUID(), null, "Planta");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConCodigoCorto() {
        RelacionLaboral modelo = RelacionLaboral.construir(UUID.randomUUID(), "A", "Planta");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConCodigoExcedeMaximo() {
        RelacionLaboral modelo = RelacionLaboral.construir(UUID.randomUUID(), "a".repeat(5), "Planta");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConCodigoInvalido() {
        RelacionLaboral modelo = RelacionLaboral.construir(UUID.randomUUID(), "R{}", "Planta");
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConDescripcionVacia() {
        RelacionLaboral modelo = RelacionLaboral.construir(UUID.randomUUID(), "RL01", null);
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConDescripcionCorta() {
        RelacionLaboral modelo = RelacionLaboral.construir(UUID.randomUUID(), "RL01", "Ab");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConDescripcionExcedeMaximo() {
        RelacionLaboral modelo = RelacionLaboral.construir(UUID.randomUUID(), "RL01", "a".repeat(21));
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConDescripcionInvalida() {
        RelacionLaboral modelo = RelacionLaboral.construir(UUID.randomUUID(), "RL01", "Desc {invalida}");
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }
}
