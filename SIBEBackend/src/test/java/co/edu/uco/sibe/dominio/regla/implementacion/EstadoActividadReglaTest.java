package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.EstadoActividad;
import co.edu.uco.sibe.dominio.transversal.excepcion.LongitudExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.PatronExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorObligatorioExcepcion;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EstadoActividadReglaTest {

    private final EstadoActividadRegla regla = EstadoActividadRegla.obtenerInstancia();

    @Test
    void deberiaValidarCamposConDatosValidos() {
        EstadoActividad modelo = EstadoActividad.construir(UUID.randomUUID(), "Activo");
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
        EstadoActividad modelo = EstadoActividad.construir(UUID.randomUUID(), null);
        assertThrows(ValorObligatorioExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreCorto() {
        EstadoActividad modelo = EstadoActividad.construir(UUID.randomUUID(), "Ab");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreExcedeMaximo() {
        EstadoActividad modelo = EstadoActividad.construir(UUID.randomUUID(), "a".repeat(16));
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreInvalido() {
        EstadoActividad modelo = EstadoActividad.construir(UUID.randomUUID(), "Act!@#");
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }
}
