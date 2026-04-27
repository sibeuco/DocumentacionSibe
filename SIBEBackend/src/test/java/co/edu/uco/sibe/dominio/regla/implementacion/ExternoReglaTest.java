package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.Externo;
import co.edu.uco.sibe.dominio.transversal.excepcion.LongitudExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.PatronExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorObligatorioExcepcion;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ExternoReglaTest {

    private final ExternoRegla regla = ExternoRegla.obtenerInstancia();

    @Test
    void deberiaValidarCamposConDatosValidos() {
        Externo modelo = Externo.construir(UUID.randomUUID(), "Juan Carlos Perez", "1234567890");
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
    void deberiaFallarConNombreCompletoVacio() {
        Externo modelo = Externo.construir(UUID.randomUUID(), null, "1234567890");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreCompletoCorto() {
        Externo modelo = Externo.construir(UUID.randomUUID(), "Juan", "1234567890");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreCompletoExcedeMaximo() {
        Externo modelo = Externo.construir(UUID.randomUUID(), "a".repeat(101), "1234567890");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreCompletoInvalido() {
        Externo modelo = Externo.construir(UUID.randomUUID(), "Juan123!@#", "1234567890");
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNumeroIdentificacionVacio() {
        Externo modelo = Externo.construir(UUID.randomUUID(), "Juan Carlos Perez", null);
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNumeroIdentificacionCorto() {
        Externo modelo = Externo.construir(UUID.randomUUID(), "Juan Carlos Perez", "123");
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNumeroIdentificacionExcedeMaximo() {
        Externo modelo = Externo.construir(UUID.randomUUID(), "Juan Carlos Perez", "1".repeat(13));
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNumeroIdentificacionInvalido() {
        Externo modelo = Externo.construir(UUID.randomUUID(), "Juan Carlos Perez", "ABC!@#DEF");
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }
}
