package co.edu.uco.sibe.dominio.modelo;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ParticipanteEstudianteTest {

    @Test
    void deberiaConstruirConParametros() {
        UUID id = UUID.randomUUID();
        Miembro miembro = Externo.construir(UUID.randomUUID(), "Ana Martinez", "345678");
        CiudadResidencia ciudad = CiudadResidencia.construir(UUID.randomUUID(), "Cali");

        ParticipanteEstudiante participante = ParticipanteEstudiante.construir(
                id, miembro, ciudad, "CAR789", "F",
                "Soltera", "Ingenieria de Sistemas", "Ingenieria",
                2021, "Sexto", 80, 4.2f,
                "Activo", "Presencial", 30, "Bus"
        );

        assertEquals(id, participante.getIdentificador());
        assertEquals(miembro, participante.getMiembro());
        assertEquals(ciudad, participante.getCiudadResidencia());
        assertEquals("CAR789", participante.getIdCarnet());
        assertEquals("F", participante.getSexo());
        assertEquals("Soltera", participante.getEstadoCivil());
        assertEquals("Ingenieria de Sistemas", participante.getProgramaAcademico());
        assertEquals("Ingenieria", participante.getFacultad());
        assertEquals(2021, participante.getAnnoIngreso());
        assertEquals("Sexto", participante.getSemestreActual());
        assertEquals(80, participante.getCreditosAprobados());
        assertEquals(4.2f, participante.getPromedioGeneral(), 0.01f);
        assertEquals("Activo", participante.getEstadoAcademico());
        assertEquals("Presencial", participante.getModalidadEstudio());
        assertEquals(30, participante.getTiempoLlegadaUniversidad());
        assertEquals("Bus", participante.getMedioTransporte());
    }

    @Test
    void deberiaConstruirConValoresPorDefecto() {
        ParticipanteEstudiante participante = ParticipanteEstudiante.construir();

        assertNotNull(participante.getIdentificador());
        assertNotNull(participante.getMiembro());
        assertNotNull(participante.getCiudadResidencia());
    }
}
