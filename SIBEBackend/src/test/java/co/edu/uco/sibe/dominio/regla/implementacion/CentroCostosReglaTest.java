package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.CentroCostos;
import co.edu.uco.sibe.dominio.transversal.excepcion.LongitudExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.PatronExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorObligatorioExcepcion;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CentroCostosReglaTest {

    private final CentroCostosRegla regla = CentroCostosRegla.obtenerInstancia();

    @Test
    void deberiaValidarCamposConDatosValidos() {
        CentroCostos modelo = CentroCostos.construir(UUID.randomUUID(), "CC01", "Centro de costos principal");
        assertDoesNotThrow(() -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaValidarCamposConDescripcionAlfanumerica() {
        CentroCostos modelo = CentroCostos.construir(UUID.randomUUID(), "CC01", "INGLES COMFAMA JEC 2024-2025");
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
        CentroCostos modelo = CentroCostos.construir(UUID.randomUUID(), null, "Centro de costos principal");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConCodigoCorto() {
        CentroCostos modelo = CentroCostos.construir(UUID.randomUUID(), "AB", "Centro de costos principal");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConCodigoExcedeMaximo() {
        CentroCostos modelo = CentroCostos.construir(UUID.randomUUID(), "a".repeat(7), "Centro de costos principal");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConCodigoInvalido() {
        CentroCostos modelo = CentroCostos.construir(UUID.randomUUID(), "CC{}", "Centro de costos principal");
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConDescripcionVacia() {
        CentroCostos modelo = CentroCostos.construir(UUID.randomUUID(), "CC01", null);
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConDescripcionCorta() {
        CentroCostos modelo = CentroCostos.construir(UUID.randomUUID(), "CC01", "Ab");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConDescripcionExcedeMaximo() {
        CentroCostos modelo = CentroCostos.construir(UUID.randomUUID(), "CC01", "a".repeat(101));
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConDescripcionInvalida() {
        CentroCostos modelo = CentroCostos.construir(UUID.randomUUID(), "CC01", "Descripcion {invalida}");
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }
}
