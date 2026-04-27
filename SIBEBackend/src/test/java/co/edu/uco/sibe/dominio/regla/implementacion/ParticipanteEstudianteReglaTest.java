package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.Miembro;
import co.edu.uco.sibe.dominio.modelo.ParticipanteEstudiante;
import co.edu.uco.sibe.dominio.transversal.excepcion.LongitudExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.PatronExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorObligatorioExcepcion;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ParticipanteEstudianteReglaTest {

    private final ParticipanteEstudianteRegla regla = ParticipanteEstudianteRegla.obtenerInstancia();

    private ParticipanteEstudiante construirValido() {
        return ParticipanteEstudiante.construir(
                UUID.randomUUID(),
                Miembro.construir(),
                null,
                "CARNET001",
                "M",
                "Soltero",
                "Ingenieria de Sistemas",
                "Facultad de Ingenieria",
                2020,
                "Decim",
                60,
                3.8f,
                "Activo",
                "Presencial",
                30,
                "Transporte publico"
        );
    }

    @Test
    void deberiaValidarCamposConDatosValidos() {
        assertDoesNotThrow(() -> regla.validarCampos(construirValido()));
    }

    @Test
    void deberiaValidarIdentificadorValido() {
        assertDoesNotThrow(() -> regla.validarIdentificador(UUID.randomUUID()));
    }

    @Test
    void deberiaFallarConIdentificadorNulo() {
        assertThrows(IllegalArgumentException.class, () -> regla.validarIdentificador(null));
    }

    @Test
    void deberiaFallarConIdCarnetVacio() {
        ParticipanteEstudiante modelo = ParticipanteEstudiante.construir(UUID.randomUUID(), Miembro.construir(), null, null, "M", "Soltero", "Ingenieria de Sistemas", "Facultad de Ingenieria", 2020, "Decim", 60, 3.8f, "Activo", "Presencial", 30, "Transporte publico");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConIdCarnetExcedeMaximo() {
        ParticipanteEstudiante modelo = ParticipanteEstudiante.construir(UUID.randomUUID(), Miembro.construir(), null, "a".repeat(21), "M", "Soltero", "Ingenieria de Sistemas", "Facultad de Ingenieria", 2020, "10MO ", 60, 3.8f, "Activo", "Presencial", 30, "Transporte publico");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConSexoVacio() {
        ParticipanteEstudiante modelo = ParticipanteEstudiante.construir(UUID.randomUUID(), Miembro.construir(), null, "CARNET001", null, "Soltero", "Ingenieria de Sistemas", "Facultad de Ingenieria", 2020, "Decim", 60, 3.8f, "Activo", "Presencial", 30, "Transporte publico");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConEstadoCivilVacio() {
        ParticipanteEstudiante modelo = ParticipanteEstudiante.construir(UUID.randomUUID(), Miembro.construir(), null, "CARNET001", "M", null, "Ingenieria de Sistemas", "Facultad de Ingenieria", 2020, "Decim", 60, 3.8f, "Activo", "Presencial", 30, "Transporte publico");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConEstadoCivilCorto() {
        ParticipanteEstudiante modelo = ParticipanteEstudiante.construir(UUID.randomUUID(), Miembro.construir(), null, "CARNET001", "M", "Sol", "Ingenieria de Sistemas", "Facultad de Ingenieria", 2020, "10MO ", 60, 3.8f, "Activo", "Presencial", 30, "Transporte publico");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConProgramaAcademicoVacio() {
        ParticipanteEstudiante modelo = ParticipanteEstudiante.construir(UUID.randomUUID(), Miembro.construir(), null, "CARNET001", "M", "Soltero", null, "Facultad de Ingenieria", 2020, "Decim", 60, 3.8f, "Activo", "Presencial", 30, "Transporte publico");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConFacultadVacia() {
        ParticipanteEstudiante modelo = ParticipanteEstudiante.construir(UUID.randomUUID(), Miembro.construir(), null, "CARNET001", "M", "Soltero", "Ingenieria de Sistemas", null, 2020, "Decim", 60, 3.8f, "Activo", "Presencial", 30, "Transporte publico");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConAnnoIngresoInvalido() {
        ParticipanteEstudiante modelo = ParticipanteEstudiante.construir(UUID.randomUUID(), Miembro.construir(), null, "CARNET001", "M", "Soltero", "Ingenieria de Sistemas", "Facultad de Ingenieria", 1999, "Decim", 60, 3.8f, "Activo", "Presencial", 30, "Transporte publico");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConSemestreActualVacio() {
        ParticipanteEstudiante modelo = ParticipanteEstudiante.construir(UUID.randomUUID(), Miembro.construir(), null, "CARNET001", "M", "Soltero", "Ingenieria de Sistemas", "Facultad de Ingenieria", 2020, null, 60, 3.8f, "Activo", "Presencial", 30, "Transporte publico");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConEstadoAcademicoVacio() {
        ParticipanteEstudiante modelo = ParticipanteEstudiante.construir(UUID.randomUUID(), Miembro.construir(), null, "CARNET001", "M", "Soltero", "Ingenieria de Sistemas", "Facultad de Ingenieria", 2020, "Decim", 60, 3.8f, null, "Presencial", 30, "Transporte publico");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConModalidadEstudioVacia() {
        ParticipanteEstudiante modelo = ParticipanteEstudiante.construir(UUID.randomUUID(), Miembro.construir(), null, "CARNET001", "M", "Soltero", "Ingenieria de Sistemas", "Facultad de Ingenieria", 2020, "Decim", 60, 3.8f, "Activo", null, 30, "Transporte publico");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConTiempoLlegadaUniversidadInvalido() {
        ParticipanteEstudiante modelo = ParticipanteEstudiante.construir(UUID.randomUUID(), Miembro.construir(), null, "CARNET001", "M", "Soltero", "Ingenieria de Sistemas", "Facultad de Ingenieria", 2020, "Decim", 60, 3.8f, "Activo", "Presencial", 0, "Transporte publico");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }
}
