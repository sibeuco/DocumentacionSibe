package co.edu.uco.sibe.dominio.modelo;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ParticipanteExternoTest {

    @Test
    void deberiaConstruirConParametros() {
        UUID id = UUID.randomUUID();
        Miembro miembro = Externo.construir(UUID.randomUUID(), "Juan Perez", "123456");
        ParticipanteExterno participante = ParticipanteExterno.construir(id, miembro);

        assertEquals(id, participante.getIdentificador());
        assertEquals(miembro, participante.getMiembro());
    }

    @Test
    void deberiaConstruirConValoresPorDefecto() {
        ParticipanteExterno participante = ParticipanteExterno.construir();

        assertNotNull(participante.getIdentificador());
        assertNotNull(participante.getMiembro());
    }
}
