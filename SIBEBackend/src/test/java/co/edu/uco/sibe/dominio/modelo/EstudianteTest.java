package co.edu.uco.sibe.dominio.modelo;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EstudianteTest {

    @Test
    void deberiaConstruirEstudianteConValoresPorDefecto() {
        Estudiante estudiante = Estudiante.construir();

        assertNotNull(estudiante);
        assertNotNull(estudiante.getIdentificador());
        assertEquals("", estudiante.getNombreCompleto());
        assertNotNull(estudiante.getFechaNacimiento());
        assertEquals("", estudiante.getProgramaAcademico());
    }

    @Test
    void deberiaConstruirEstudianteConNulos() {
        Estudiante estudiante = Estudiante.construir(UUID.randomUUID(), null, null, null, null, null,
                null, null, null, null, null, null, null, 0, null, 0, 0f, null, null, 0, null);

        assertNotNull(estudiante);
        assertEquals("", estudiante.getNombreCompleto());
        assertNotNull(estudiante.getCiudadResidencia());
        assertNotNull(estudiante.getFechaNacimiento());
    }
}
