package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.Accion;
import co.edu.uco.sibe.dominio.transversal.excepcion.LongitudExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.PatronExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorObligatorioExcepcion;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccionReglaTest {

    private final AccionRegla regla = AccionRegla.obtenerInstancia();

    @Test
    void deberiaValidarCamposConDatosValidos() {
        Accion modelo = Accion.construir(UUID.randomUUID(), "Detalle de la accion valida", "Objetivo de la accion valida");
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
    void deberiaFallarConDetalleNulo() {
        Accion modelo = Accion.construir(UUID.randomUUID(), null, "Objetivo de la accion valida");
        assertThrows(ValorObligatorioExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConDetalleCorto() {
        Accion modelo = Accion.construir(UUID.randomUUID(), "Corto", "Objetivo de la accion valida");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConDetalleExcedeMaximo() {
        Accion modelo = Accion.construir(UUID.randomUUID(), "a".repeat(501), "Objetivo de la accion valida");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConDetalleInvalido() {
        Accion modelo = Accion.construir(UUID.randomUUID(), "Detalle {invalido} <script>", "Objetivo de la accion valida");
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConObjetivoNulo() {
        Accion modelo = Accion.construir(UUID.randomUUID(), "Detalle de la accion valida", null);
        assertThrows(ValorObligatorioExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConObjetivoCorto() {
        Accion modelo = Accion.construir(UUID.randomUUID(), "Detalle de la accion valida", "Corto");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConObjetivoExcedeMaximo() {
        Accion modelo = Accion.construir(UUID.randomUUID(), "Detalle de la accion valida", "a".repeat(501));
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConObjetivoInvalido() {
        Accion modelo = Accion.construir(UUID.randomUUID(), "Detalle de la accion valida", "Objetivo {invalido} <script>");
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }
}
