package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.TipoIdentificacion;
import co.edu.uco.sibe.dominio.transversal.excepcion.LongitudExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.PatronExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorObligatorioExcepcion;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TipoIdentificacionReglaTest {

    private final TipoIdentificacionRegla regla = TipoIdentificacionRegla.obtenerInstancia();

    @Test
    void deberiaValidarCamposConDatosValidos() {
        TipoIdentificacion modelo = TipoIdentificacion.construir(UUID.randomUUID(), "CC", "Cedula de Ciudadania");
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
    void deberiaFallarConSiglaNula() {
        TipoIdentificacion modelo = TipoIdentificacion.construir(UUID.randomUUID(), null, "Descripcion valida");
        assertThrows(ValorObligatorioExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConSiglaExcedeMaximo() {
        TipoIdentificacion modelo = TipoIdentificacion.construir(UUID.randomUUID(), "a".repeat(7), "Descripcion valida");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConSiglaInvalida() {
        TipoIdentificacion modelo = TipoIdentificacion.construir(UUID.randomUUID(), "C@!", "Descripcion valida");
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConDescripcionNula() {
        TipoIdentificacion modelo = TipoIdentificacion.construir(UUID.randomUUID(), "CC", null);
        assertThrows(ValorObligatorioExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConDescripcionExcedeMaximo() {
        TipoIdentificacion modelo = TipoIdentificacion.construir(UUID.randomUUID(), "CC", "a".repeat(31));
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConDescripcionInvalida() {
        TipoIdentificacion modelo = TipoIdentificacion.construir(UUID.randomUUID(), "CC", "Desc!@#$%^&*Invalida");
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }
}
