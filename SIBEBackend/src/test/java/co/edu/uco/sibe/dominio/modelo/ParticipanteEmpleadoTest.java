package co.edu.uco.sibe.dominio.modelo;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ParticipanteEmpleadoTest {

    @Test
    void deberiaConstruirConParametros() {
        UUID id = UUID.randomUUID();
        Miembro miembro = Externo.construir(UUID.randomUUID(), "Carlos Garcia", "789012");
        CiudadResidencia ciudad = CiudadResidencia.construir(UUID.randomUUID(), "Bogota");
        RelacionLaboral relacion = RelacionLaboral.construir(UUID.randomUUID(), "PLT", "Planta");
        CentroCostos centro = CentroCostos.construir(UUID.randomUUID(), "CC01", "Centro Principal");

        ParticipanteEmpleado participante = ParticipanteEmpleado.construir(id, miembro, ciudad, "CAR456", "F", relacion, centro);

        assertEquals(id, participante.getIdentificador());
        assertEquals(miembro, participante.getMiembro());
        assertEquals(ciudad, participante.getCiudadResidencia());
        assertEquals("CAR456", participante.getIdCarnet());
        assertEquals("F", participante.getSexo());
        assertEquals(relacion, participante.getRelacionLaboral());
        assertEquals(centro, participante.getCentroCostos());
    }

    @Test
    void deberiaConstruirConValoresPorDefecto() {
        ParticipanteEmpleado participante = ParticipanteEmpleado.construir();

        assertNotNull(participante.getIdentificador());
        assertNotNull(participante.getMiembro());
        assertNotNull(participante.getCiudadResidencia());
        assertNotNull(participante.getIdCarnet());
        assertNotNull(participante.getSexo());
        assertNotNull(participante.getRelacionLaboral());
        assertNotNull(participante.getCentroCostos());
    }
}
