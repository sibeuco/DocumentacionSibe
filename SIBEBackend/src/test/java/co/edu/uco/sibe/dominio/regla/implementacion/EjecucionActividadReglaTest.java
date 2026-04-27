package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.EjecucionActividad;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EjecucionActividadReglaTest {

    private final EjecucionActividadRegla regla = EjecucionActividadRegla.obtenerInstancia();

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
        EjecucionActividad modelo = EjecucionActividad.construir();
        assertThrows(IllegalStateException.class, () -> regla.validarCampos(modelo));
    }
}
