package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.Identificacion;
import co.edu.uco.sibe.dominio.transversal.excepcion.LongitudExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.PatronExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorObligatorioExcepcion;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class IdentificacionReglaTest {

    private final IdentificacionRegla regla = IdentificacionRegla.obtenerInstancia();

    @Test
    void deberiaValidarCamposConDatosValidos() {
        Identificacion modelo = Identificacion.construir(UUID.randomUUID(), "1234567890", null);
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
    void deberiaFallarConNumeroIdentificacionNulo() {
        Identificacion modelo = Identificacion.construir(UUID.randomUUID(), null, null);
        assertThrows(ValorObligatorioExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNumeroIdentificacionCorto() {
        Identificacion modelo = Identificacion.construir(UUID.randomUUID(), "123", null);
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNumeroIdentificacionExcedeMaximo() {
        Identificacion modelo = Identificacion.construir(UUID.randomUUID(), "1".repeat(13), null);
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNumeroIdentificacionInvalido() {
        Identificacion modelo = Identificacion.construir(UUID.randomUUID(), "ABC!@#DEF", null);
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }
}
