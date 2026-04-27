package co.edu.uco.sibe.infraestructura.adaptador.entidad;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ParticipanteEstudianteEntidadTest {

    @Test
    void deberiaCrearEntidadConConstructorVacio() {
        ParticipanteEstudianteEntidad entidad = new ParticipanteEstudianteEntidad();

        assertNotNull(entidad);
    }

    @Test
    void deberiaEstablecerYObtenerPropiedades() {
        ParticipanteEstudianteEntidad entidad = new ParticipanteEstudianteEntidad();

        entidad.setEstadoCivil("Soltero");
        entidad.setProgramaAcademico("Ingeniería de Sistemas");
        entidad.setFacultad("Ingeniería");
        entidad.setAnnoIngreso(2022);
        entidad.setSemestreActual("8");
        entidad.setCreditosAprobados(120);
        entidad.setPromedioGeneral(4.2f);
        entidad.setEstadoAcademico("Activo");
        entidad.setModalidadEstudio("Presencial");
        entidad.setTiempoLlegadaUniversidad(30);
        entidad.setMedioTransporte("Bus");

        assertEquals("Soltero", entidad.getEstadoCivil());
        assertEquals("Ingeniería de Sistemas", entidad.getProgramaAcademico());
        assertEquals("Ingeniería", entidad.getFacultad());
        assertEquals(2022, entidad.getAnnoIngreso());
        assertEquals("8", entidad.getSemestreActual());
        assertEquals(120, entidad.getCreditosAprobados());
        assertEquals(4.2f, entidad.getPromedioGeneral());
        assertEquals("Activo", entidad.getEstadoAcademico());
        assertEquals("Presencial", entidad.getModalidadEstudio());
        assertEquals(30, entidad.getTiempoLlegadaUniversidad());
        assertEquals("Bus", entidad.getMedioTransporte());
    }

    @Test
    void deberiaCrearEntidadConConstructorCompleto() {
        UUID id = UUID.randomUUID();
        MiembroEntidad miembro = new MiembroEntidad();
        InternoCiudadResidenciaEntidad ciudadResidencia = new InternoCiudadResidenciaEntidad();

        ParticipanteEstudianteEntidad entidad = new ParticipanteEstudianteEntidad(
                id, miembro, ciudadResidencia, "CARNET1", "M",
                "Soltero", "Ingeniería", "Ingeniería", 2022,
                "8", 120, 4.2f, "Activo", "Presencial", 30, "Bus"
        );

        assertEquals("Soltero", entidad.getEstadoCivil());
        assertEquals("Ingeniería", entidad.getProgramaAcademico());
        assertEquals(2022, entidad.getAnnoIngreso());
    }
}
