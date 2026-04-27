package co.edu.uco.sibe.dominio.modelo;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ParticipanteInternoTest {

    @Test
    void deberiaConstruirConParametros() {
        UUID id = UUID.randomUUID();
        Miembro miembro = Externo.construir(UUID.randomUUID(), "Maria Lopez", "654321");
        CiudadResidencia ciudad = CiudadResidencia.construir(UUID.randomUUID(), "Medellin");

        ParticipanteInterno participante = ParticipanteInterno.construir(id, miembro, ciudad, "CAR123", "M");

        assertEquals(id, participante.getIdentificador());
        assertEquals(miembro, participante.getMiembro());
        assertEquals(ciudad, participante.getCiudadResidencia());
        assertEquals("CAR123", participante.getIdCarnet());
        assertEquals("M", participante.getSexo());
    }

    @Test
    void deberiaConstruirConValoresPorDefecto() {
        ParticipanteInterno participante = ParticipanteInterno.construir();

        assertNotNull(participante.getIdentificador());
        assertNotNull(participante.getMiembro());
        assertNotNull(participante.getCiudadResidencia());
        assertNotNull(participante.getIdCarnet());
        assertNotNull(participante.getSexo());
    }
}
