package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.Subarea;
import co.edu.uco.sibe.dominio.transversal.excepcion.LongitudExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.PatronExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorObligatorioExcepcion;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SubareaReglaTest {

    private final SubareaRegla regla = SubareaRegla.obtenerInstancia();

    @Test
    void deberiaValidarCamposConDatosValidos() {
        Subarea modelo = Subarea.construir(UUID.randomUUID(), "Subarea Valida", new ArrayList<>());
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
    void deberiaFallarConNombreVacio() {
        Subarea modelo = Subarea.construir(UUID.randomUUID(), null, new ArrayList<>());
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreCorto() {
        Subarea modelo = Subarea.construir(UUID.randomUUID(), "Corto", new ArrayList<>());
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreExcedeMaximo() {
        Subarea modelo = Subarea.construir(UUID.randomUUID(), "a".repeat(71), new ArrayList<>());
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreInvalido() {
        Subarea modelo = Subarea.construir(UUID.randomUUID(), "Inv@lido!Sub", new ArrayList<>());
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }
}
