package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.CiudadResidencia;
import co.edu.uco.sibe.dominio.transversal.excepcion.LongitudExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.PatronExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorObligatorioExcepcion;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CiudadResidenciaReglaTest {

    private final CiudadResidenciaRegla regla = CiudadResidenciaRegla.obtenerInstancia();

    @Test
    void deberiaValidarCamposConDatosValidos() {
        CiudadResidencia modelo = CiudadResidencia.construir(UUID.randomUUID(), "Medellin");
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
    void deberiaFallarConDescripcionNula() {
        CiudadResidencia modelo = CiudadResidencia.construir(UUID.randomUUID(), null);
        assertThrows(ValorObligatorioExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConDescripcionCorta() {
        CiudadResidencia modelo = CiudadResidencia.construir(UUID.randomUUID(), "Ab");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConDescripcionExcedeMaximo() {
        CiudadResidencia modelo = CiudadResidencia.construir(UUID.randomUUID(), "a".repeat(31));
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConDescripcionInvalida() {
        CiudadResidencia modelo = CiudadResidencia.construir(UUID.randomUUID(), "Ciudad {invalida}");
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }
}
