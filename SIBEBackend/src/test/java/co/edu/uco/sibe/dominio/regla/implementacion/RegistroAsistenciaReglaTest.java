package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.RegistroAsistencia;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RegistroAsistenciaReglaTest {

    private final RegistroAsistenciaRegla regla = RegistroAsistenciaRegla.obtenerInstancia();

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
        RegistroAsistencia modelo = RegistroAsistencia.construir();
        assertThrows(IllegalStateException.class, () -> regla.validarCampos(modelo));
    }
}
