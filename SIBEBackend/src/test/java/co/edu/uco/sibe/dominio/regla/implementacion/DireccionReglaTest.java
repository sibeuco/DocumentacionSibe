package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.Direccion;
import co.edu.uco.sibe.dominio.transversal.excepcion.LongitudExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.PatronExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorObligatorioExcepcion;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DireccionReglaTest {

    private final DireccionRegla regla = DireccionRegla.obtenerInstancia();

    @Test
    void deberiaValidarCamposConDatosValidos() {
        Direccion modelo = Direccion.construir(UUID.randomUUID(), "Direccion de Extension Valida", new ArrayList<>(), new ArrayList<>());
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
        Direccion modelo = Direccion.construir(UUID.randomUUID(), null, new ArrayList<>(), new ArrayList<>());
        assertThrows(ValorObligatorioExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreCorto() {
        Direccion modelo = Direccion.construir(UUID.randomUUID(), "Corto", new ArrayList<>(), new ArrayList<>());
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreExcedeMaximo() {
        Direccion modelo = Direccion.construir(UUID.randomUUID(), "a".repeat(71), new ArrayList<>(), new ArrayList<>());
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreInvalido() {
        Direccion modelo = Direccion.construir(UUID.randomUUID(), "Direccion {invalida}!", new ArrayList<>(), new ArrayList<>());
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }
}
