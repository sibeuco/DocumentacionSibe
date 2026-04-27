package co.edu.uco.sibe.dominio.modelo;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ExternoTest {

    @Test
    void deberiaConstruirConParametros() {
        UUID id = UUID.randomUUID();
        Externo externo = Externo.construir(id, "Juan Perez", "123456");

        assertEquals(id, externo.getIdentificador());
        assertEquals("Juan Perez", externo.getNombreCompleto());
        assertEquals("123456", externo.getNumeroIdentificacion());
    }

    @Test
    void deberiaConstruirConValoresPorDefecto() {
        Externo externo = Externo.construir();

        assertNotNull(externo.getIdentificador());
        assertNotNull(externo.getNombreCompleto());
        assertNotNull(externo.getNumeroIdentificacion());
    }
}
