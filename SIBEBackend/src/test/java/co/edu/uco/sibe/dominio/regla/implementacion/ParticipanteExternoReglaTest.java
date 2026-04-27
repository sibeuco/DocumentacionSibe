package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.ParticipanteExterno;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ParticipanteExternoReglaTest {

    private final ParticipanteExternoRegla regla = ParticipanteExternoRegla.obtenerInstancia();

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
        ParticipanteExterno modelo = ParticipanteExterno.construir();
        assertThrows(IllegalStateException.class, () -> regla.validarCampos(modelo));
    }
}
