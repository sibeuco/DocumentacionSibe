package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.Indicador;
import co.edu.uco.sibe.dominio.transversal.excepcion.LongitudExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.PatronExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorObligatorioExcepcion;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class IndicadorReglaTest {

    private final IndicadorRegla regla = IndicadorRegla.obtenerInstancia();

    @Test
    void deberiaValidarCamposConDatosValidos() {
        Indicador modelo = Indicador.construir(UUID.randomUUID(), "Indicador de gestion valido", null, null, null, new ArrayList<>());
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
        Indicador modelo = Indicador.construir(UUID.randomUUID(), null, null, null, null, new ArrayList<>());
        assertThrows(ValorObligatorioExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreCorto() {
        Indicador modelo = Indicador.construir(UUID.randomUUID(), "Cort", null, null, null, new ArrayList<>());
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreExcedeMaximo() {
        Indicador modelo = Indicador.construir(UUID.randomUUID(), "a".repeat(101), null, null, null, new ArrayList<>());
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreInvalido() {
        Indicador modelo = Indicador.construir(UUID.randomUUID(), "Indicador {invalido} <script>", null, null, null, new ArrayList<>());
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }
}
