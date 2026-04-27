package co.edu.uco.sibe.dominio.modelo;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PublicoInteresTest {

    @Test
    void deberiaConstruirConParametros() {
        UUID id = UUID.randomUUID();
        PublicoInteres publicoInteres = PublicoInteres.construir(id, "Estudiantes");

        assertEquals(id, publicoInteres.getIdentificador());
        assertEquals("Estudiantes", publicoInteres.getNombre());
    }

    @Test
    void deberiaConstruirConValoresPorDefecto() {
        PublicoInteres publicoInteres = PublicoInteres.construir();

        assertNotNull(publicoInteres.getIdentificador());
        assertNotNull(publicoInteres.getNombre());
    }
}
