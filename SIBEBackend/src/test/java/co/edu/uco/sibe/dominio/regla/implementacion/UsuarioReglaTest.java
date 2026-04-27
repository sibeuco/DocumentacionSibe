package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.Usuario;
import co.edu.uco.sibe.dominio.transversal.excepcion.LongitudExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.PatronExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorObligatorioExcepcion;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioReglaTest {

    private final UsuarioRegla regla = UsuarioRegla.obtenerInstancia();

    @Test
    void deberiaValidarCamposConDatosValidos() {
        Usuario modelo = Usuario.construir(UUID.randomUUID(), "usuario@correo.com", "Clave123!", null, true);
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
    void deberiaFallarConCorreoVacio() {
        Usuario modelo = Usuario.construir(UUID.randomUUID(), null, "Clave123!", null, true);
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConCorreoCorto() {
        Usuario modelo = Usuario.construir(UUID.randomUUID(), "a@b.c", "Clave123!", null, true);
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConCorreoExcedeMaximo() {
        Usuario modelo = Usuario.construir(UUID.randomUUID(), "a".repeat(90) + "@correo.com", "Clave123!", null, true);
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConCorreoInvalido() {
        Usuario modelo = Usuario.construir(UUID.randomUUID(), "correo-invalido-sin-arroba", "Clave123!", null, true);
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaValidarClaveValida() {
        assertDoesNotThrow(() -> regla.validarClave("Clave123!"));
    }

    @Test
    void deberiaFallarConClaveNula() {
        assertThrows(IllegalArgumentException.class, () -> regla.validarClave(null));
    }

    @Test
    void deberiaFallarConClaveCorta() {
        var excepcion = assertThrows(PatronExcepcion.class, () -> regla.validarClave("Cl1!"));
        assertEquals("La contraseña debe tener al menos 8 caracteres.", excepcion.getMessage());
    }

    @Test
    void deberiaFallarConClaveExcedeMaximo() {
        assertThrows(LongitudExcepcion.class, () -> regla.validarClave("Abc1234567890123456789"));
    }

    @Test
    void deberiaFallarConClaveSinMayuscula() {
        var excepcion = assertThrows(PatronExcepcion.class, () -> regla.validarClave("clave123!"));
        assertEquals("La contraseña debe incluir al menos una letra mayúscula.", excepcion.getMessage());
    }

    @Test
    void deberiaFallarConClaveSinMinuscula() {
        var excepcion = assertThrows(PatronExcepcion.class, () -> regla.validarClave("CLAVE123!"));
        assertEquals("La contraseña debe incluir al menos una letra minúscula.", excepcion.getMessage());
    }

    @Test
    void deberiaFallarConClaveSinNumero() {
        var excepcion = assertThrows(PatronExcepcion.class, () -> regla.validarClave("ClaveSinNumero!"));
        assertEquals("La contraseña debe incluir al menos un número.", excepcion.getMessage());
    }

    @Test
    void deberiaFallarConClaveInvalida() {
        var excepcion = assertThrows(PatronExcepcion.class, () -> regla.validarClave("clavesimple"));
        assertEquals("La contraseña debe incluir al menos una letra mayúscula.", excepcion.getMessage());
    }
}
