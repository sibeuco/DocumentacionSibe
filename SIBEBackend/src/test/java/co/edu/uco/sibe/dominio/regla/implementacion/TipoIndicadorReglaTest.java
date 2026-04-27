package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.TipoIndicador;
import co.edu.uco.sibe.dominio.transversal.excepcion.LongitudExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.PatronExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorObligatorioExcepcion;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TipoIndicadorReglaTest {

    private final TipoIndicadorRegla regla = TipoIndicadorRegla.obtenerInstancia();

    @Test
    void deberiaValidarCamposConDatosValidos() {
        TipoIndicador modelo = TipoIndicador.construir(UUID.randomUUID(), "Cuantitativa", "Impacto");
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
    void deberiaFallarConNaturalezaVacia() {
        TipoIndicador modelo = TipoIndicador.construir(UUID.randomUUID(), null, "Impacto");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNaturalezaCorta() {
        TipoIndicador modelo = TipoIndicador.construir(UUID.randomUUID(), "Ab", "Impacto");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNaturalezaExcedeMaximo() {
        TipoIndicador modelo = TipoIndicador.construir(UUID.randomUUID(), "a".repeat(21), "Impacto");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNaturalezaInvalida() {
        TipoIndicador modelo = TipoIndicador.construir(UUID.randomUUID(), "Inv@l!da", "Impacto");
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConTipologiaVacia() {
        TipoIndicador modelo = TipoIndicador.construir(UUID.randomUUID(), "Cuantitativa", null);
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConTipologiaCorta() {
        TipoIndicador modelo = TipoIndicador.construir(UUID.randomUUID(), "Cuantitativa", "Ab");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConTipologiaExcedeMaximo() {
        TipoIndicador modelo = TipoIndicador.construir(UUID.randomUUID(), "Cuantitativa", "a".repeat(16));
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConTipologiaInvalida() {
        TipoIndicador modelo = TipoIndicador.construir(UUID.randomUUID(), "Cuantitativa", "Inv@l!");
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }
}
