package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.Persona;
import co.edu.uco.sibe.dominio.transversal.excepcion.LongitudExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.PatronExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorObligatorioExcepcion;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PersonaReglaTest {

    private final PersonaRegla regla = PersonaRegla.obtenerInstancia();

    @Test
    void deberiaValidarCamposConDatosValidos() {
        Persona modelo = Persona.construir(UUID.randomUUID(), "Juan", "Perez", "juan.perez@correo.com", null);
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
    void deberiaFallarConNombresVacio() {
        Persona modelo = Persona.construir(UUID.randomUUID(), null, "Perez", "juan.perez@correo.com", null);
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombresExcedeMaximo() {
        Persona modelo = Persona.construir(UUID.randomUUID(), "a".repeat(51), "Perez", "juan.perez@correo.com", null);
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombresInvalido() {
        Persona modelo = Persona.construir(UUID.randomUUID(), "Juan123!", "Perez", "juan.perez@correo.com", null);
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConApellidosVacio() {
        Persona modelo = Persona.construir(UUID.randomUUID(), "Juan", null, "juan.perez@correo.com", null);
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConApellidosExcedeMaximo() {
        Persona modelo = Persona.construir(UUID.randomUUID(), "Juan", "a".repeat(51), "juan.perez@correo.com", null);
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConApellidosInvalido() {
        Persona modelo = Persona.construir(UUID.randomUUID(), "Juan", "Perez123!", "juan.perez@correo.com", null);
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConCorreoVacio() {
        Persona modelo = Persona.construir(UUID.randomUUID(), "Juan", "Perez", null, null);
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConCorreoCorto() {
        Persona modelo = Persona.construir(UUID.randomUUID(), "Juan", "Perez", "a@b.c", null);
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConCorreoExcedeMaximo() {
        Persona modelo = Persona.construir(UUID.randomUUID(), "Juan", "Perez", "a".repeat(90) + "@correo.com", null);
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConCorreoInvalido() {
        Persona modelo = Persona.construir(UUID.randomUUID(), "Juan", "Perez", "correo-invalido-sin-arroba", null);
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }
}
