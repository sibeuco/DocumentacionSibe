package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.PublicoInteres;
import co.edu.uco.sibe.dominio.transversal.excepcion.LongitudExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.PatronExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorObligatorioExcepcion;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PublicoInteresReglaTest {

    private final PublicoInteresRegla regla = PublicoInteresRegla.obtenerInstancia();

    @Test
    void deberiaValidarCamposConDatosValidos() {
        PublicoInteres modelo = PublicoInteres.construir(UUID.randomUUID(), "Estudiantes");
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
        PublicoInteres modelo = PublicoInteres.construir(UUID.randomUUID(), null);
        assertThrows(ValorObligatorioExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreCorto() {
        PublicoInteres modelo = PublicoInteres.construir(UUID.randomUUID(), "Ab");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreExcedeMaximo() {
        PublicoInteres modelo = PublicoInteres.construir(UUID.randomUUID(), "a".repeat(51));
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreInvalido() {
        PublicoInteres modelo = PublicoInteres.construir(UUID.randomUUID(), "Inv@lido!");
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }
}
