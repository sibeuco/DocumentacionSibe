package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.Temporalidad;
import co.edu.uco.sibe.dominio.transversal.excepcion.LongitudExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.PatronExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorObligatorioExcepcion;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TemporalidadReglaTest {

    private final TemporalidadRegla regla = TemporalidadRegla.obtenerInstancia();

    @Test
    void deberiaValidarCamposConDatosValidos() {
        Temporalidad modelo = Temporalidad.construir(UUID.randomUUID(), "Mensual");
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
    void deberiaFallarConNombreNulo() {
        Temporalidad modelo = Temporalidad.construir(UUID.randomUUID(), null);
        assertThrows(ValorObligatorioExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreCorto() {
        Temporalidad modelo = Temporalidad.construir(UUID.randomUUID(), "Ab");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreExcedeMaximo() {
        Temporalidad modelo = Temporalidad.construir(UUID.randomUUID(), "a".repeat(31));
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreInvalido() {
        Temporalidad modelo = Temporalidad.construir(UUID.randomUUID(), "Inv@l!");
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }
}
