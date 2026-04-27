package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.UsuarioOrganizacion;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioOrganizacionReglaTest {

    private final UsuarioOrganizacionRegla regla = UsuarioOrganizacionRegla.obtenerInstancia();

    @Test
    void deberiaValidarIdentificadorValido() {
        assertDoesNotThrow(() -> regla.validarIdentificador(UUID.randomUUID()));
    }

    @Test
    void deberiaFallarConIdentificadorNulo() {
        assertThrows(IllegalArgumentException.class, () -> regla.validarIdentificador(null));
    }

    @Test
    void deberiaLanzarExcepcionAlValidarCampos() {
        UsuarioOrganizacion modelo = UsuarioOrganizacion.construir();
        assertThrows(IllegalStateException.class, () -> regla.validarCampos(modelo));
    }
}
