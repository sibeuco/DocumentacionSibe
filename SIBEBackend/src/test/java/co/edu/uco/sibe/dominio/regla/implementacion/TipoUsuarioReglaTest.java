package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.TipoUsuario;
import co.edu.uco.sibe.dominio.transversal.excepcion.LongitudExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorObligatorioExcepcion;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TipoUsuarioReglaTest {

    private final TipoUsuarioRegla regla = TipoUsuarioRegla.obtenerInstancia();

    @Test
    void deberiaValidarCamposConDatosValidos() {
        TipoUsuario modelo = TipoUsuario.construir(UUID.randomUUID(), "ADM", "Administrador General", true, true, true, true);
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
        TipoUsuario modelo = TipoUsuario.construir(UUID.randomUUID(), "ADM", null, true, true, true, true);
        assertThrows(ValorObligatorioExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreCorto() {
        TipoUsuario modelo = TipoUsuario.construir(UUID.randomUUID(), "ADM", "Corto", true, true, true, true);
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreExcedeMaximo() {
        TipoUsuario modelo = TipoUsuario.construir(UUID.randomUUID(), "ADM", "a".repeat(31), true, true, true, true);
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }
}
