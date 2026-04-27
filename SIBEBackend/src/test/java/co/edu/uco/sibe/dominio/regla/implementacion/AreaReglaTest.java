package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.Area;
import co.edu.uco.sibe.dominio.transversal.excepcion.LongitudExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.PatronExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorObligatorioExcepcion;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AreaReglaTest {

    private final AreaRegla regla = AreaRegla.obtenerInstancia();

    @Test
    void deberiaValidarCamposConDatosValidos() {
        Area modelo = Area.construir(UUID.randomUUID(), "Area de Extension Valida", new ArrayList<>(), new ArrayList<>());
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
        Area modelo = Area.construir(UUID.randomUUID(), null, new ArrayList<>(), new ArrayList<>());
        assertThrows(ValorObligatorioExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreCorto() {
        Area modelo = Area.construir(UUID.randomUUID(), "Corto", new ArrayList<>(), new ArrayList<>());
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreExcedeMaximo() {
        Area modelo = Area.construir(UUID.randomUUID(), "a".repeat(71), new ArrayList<>(), new ArrayList<>());
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreInvalido() {
        Area modelo = Area.construir(UUID.randomUUID(), "Area Inv@lida!", new ArrayList<>(), new ArrayList<>());
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }
}
