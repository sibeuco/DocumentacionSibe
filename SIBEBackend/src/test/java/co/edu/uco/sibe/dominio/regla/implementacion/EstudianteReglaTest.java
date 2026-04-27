package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.Estudiante;
import co.edu.uco.sibe.dominio.transversal.excepcion.LongitudExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.PatronExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorObligatorioExcepcion;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EstudianteReglaTest {

    private final EstudianteRegla regla = EstudianteRegla.obtenerInstancia();

    private Estudiante construirEstudianteValido() {
        return Estudiante.construir(
                UUID.randomUUID(),
                "Juan Carlos Perez",
                "1234567890",
                null,
                "CARNET001",
                "M",
                LocalDate.of(2000, 1, 1),
                "Colombiana",
                "Soltero",
                "juan.personal@correo.com",
                "juan.institucional@correo.edu.co",
                "Ingenieria de Sistemas",
                "Facultad de Ingenieria",
                2020,
                "10MO ",
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
        assertDoesNotThrow(() -> regla.validarCampos(construirEstudianteValido()));
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
    void deberiaFallarConNombreCompletoVacio() {
        Estudiante modelo = Estudiante.construir(UUID.randomUUID(), null, "1234567890", null, "CARNET001", "M", LocalDate.of(2000, 1, 1), "Colombiana", "Soltero", "juan.personal@correo.com", "juan.institucional@correo.edu.co", "Ingenieria de Sistemas", "Facultad de Ingenieria", 2020, "10MO ", 60, 3.8f, "Activo", "Presencial", 30, "Transporte publico");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreCompletoCorto() {
        Estudiante modelo = Estudiante.construir(UUID.randomUUID(), "Juan", "1234567890", null, "CARNET001", "M", LocalDate.of(2000, 1, 1), "Colombiana", "Soltero", "juan.personal@correo.com", "juan.institucional@correo.edu.co", "Ingenieria de Sistemas", "Facultad de Ingenieria", 2020, "10MO ", 60, 3.8f, "Activo", "Presencial", 30, "Transporte publico");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreCompletoExcedeMaximo() {
        Estudiante modelo = Estudiante.construir(UUID.randomUUID(), "a".repeat(101), "1234567890", null, "CARNET001", "M", LocalDate.of(2000, 1, 1), "Colombiana", "Soltero", "juan.personal@correo.com", "juan.institucional@correo.edu.co", "Ingenieria de Sistemas", "Facultad de Ingenieria", 2020, "10MO ", 60, 3.8f, "Activo", "Presencial", 30, "Transporte publico");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreCompletoInvalido() {
        Estudiante modelo = Estudiante.construir(UUID.randomUUID(), "Juan123!@#", "1234567890", null, "CARNET001", "M", LocalDate.of(2000, 1, 1), "Colombiana", "Soltero", "juan.personal@correo.com", "juan.institucional@correo.edu.co", "Ingenieria de Sistemas", "Facultad de Ingenieria", 2020, "10MO ", 60, 3.8f, "Activo", "Presencial", 30, "Transporte publico");
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNumeroIdentificacionVacio() {
        Estudiante modelo = Estudiante.construir(UUID.randomUUID(), "Juan Carlos Perez", null, null, "CARNET001", "M", LocalDate.of(2000, 1, 1), "Colombiana", "Soltero", "juan.personal@correo.com", "juan.institucional@correo.edu.co", "Ingenieria de Sistemas", "Facultad de Ingenieria", 2020, "10MO ", 60, 3.8f, "Activo", "Presencial", 30, "Transporte publico");
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConIdCarnetVacio() {
        Estudiante modelo = Estudiante.construir(UUID.randomUUID(), "Juan Carlos Perez", "1234567890", null, null, "M", LocalDate.of(2000, 1, 1), "Colombiana", "Soltero", "juan.personal@correo.com", "juan.institucional@correo.edu.co", "Ingenieria de Sistemas", "Facultad de Ingenieria", 2020, "10MO ", 60, 3.8f, "Activo", "Presencial", 30, "Transporte publico");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConSexoVacio() {
        Estudiante modelo = Estudiante.construir(UUID.randomUUID(), "Juan Carlos Perez", "1234567890", null, "CARNET001", null, LocalDate.of(2000, 1, 1), "Colombiana", "Soltero", "juan.personal@correo.com", "juan.institucional@correo.edu.co", "Ingenieria de Sistemas", "Facultad de Ingenieria", 2020, "10MO ", 60, 3.8f, "Activo", "Presencial", 30, "Transporte publico");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNacionalidadVacia() {
        Estudiante modelo = Estudiante.construir(UUID.randomUUID(), "Juan Carlos Perez", "1234567890", null, "CARNET001", "M", LocalDate.of(2000, 1, 1), null, "Soltero", "juan.personal@correo.com", "juan.institucional@correo.edu.co", "Ingenieria de Sistemas", "Facultad de Ingenieria", 2020, "10MO ", 60, 3.8f, "Activo", "Presencial", 30, "Transporte publico");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNacionalidadCorta() {
        Estudiante modelo = Estudiante.construir(UUID.randomUUID(), "Juan Carlos Perez", "1234567890", null, "CARNET001", "M", LocalDate.of(2000, 1, 1), "Col", "Soltero", "juan.personal@correo.com", "juan.institucional@correo.edu.co", "Ingenieria de Sistemas", "Facultad de Ingenieria", 2020, "10MO ", 60, 3.8f, "Activo", "Presencial", 30, "Transporte publico");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConEstadoCivilVacio() {
        Estudiante modelo = Estudiante.construir(UUID.randomUUID(), "Juan Carlos Perez", "1234567890", null, "CARNET001", "M", LocalDate.of(2000, 1, 1), "Colombiana", null, "juan.personal@correo.com", "juan.institucional@correo.edu.co", "Ingenieria de Sistemas", "Facultad de Ingenieria", 2020, "10MO ", 60, 3.8f, "Activo", "Presencial", 30, "Transporte publico");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConCorreoPersonalVacio() {
        Estudiante modelo = Estudiante.construir(UUID.randomUUID(), "Juan Carlos Perez", "1234567890", null, "CARNET001", "M", LocalDate.of(2000, 1, 1), "Colombiana", "Soltero", null, "juan.institucional@correo.edu.co", "Ingenieria de Sistemas", "Facultad de Ingenieria", 2020, "10MO ", 60, 3.8f, "Activo", "Presencial", 30, "Transporte publico");
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConCorreoPersonalInvalido() {
        Estudiante modelo = Estudiante.construir(UUID.randomUUID(), "Juan Carlos Perez", "1234567890", null, "CARNET001", "M", LocalDate.of(2000, 1, 1), "Colombiana", "Soltero", "correo-invalido-sin-arroba", "juan.institucional@correo.edu.co", "Ingenieria de Sistemas", "Facultad de Ingenieria", 2020, "10MO ", 60, 3.8f, "Activo", "Presencial", 30, "Transporte publico");
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConCorreoInstitucionalVacio() {
        Estudiante modelo = Estudiante.construir(UUID.randomUUID(), "Juan Carlos Perez", "1234567890", null, "CARNET001", "M", LocalDate.of(2000, 1, 1), "Colombiana", "Soltero", "juan.personal@correo.com", null, "Ingenieria de Sistemas", "Facultad de Ingenieria", 2020, "10MO ", 60, 3.8f, "Activo", "Presencial", 30, "Transporte publico");
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConProgramaAcademicoVacio() {
        Estudiante modelo = Estudiante.construir(UUID.randomUUID(), "Juan Carlos Perez", "1234567890", null, "CARNET001", "M", LocalDate.of(2000, 1, 1), "Colombiana", "Soltero", "juan.personal@correo.com", "juan.institucional@correo.edu.co", null, "Facultad de Ingenieria", 2020, "10MO ", 60, 3.8f, "Activo", "Presencial", 30, "Transporte publico");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConFacultadVacia() {
        Estudiante modelo = Estudiante.construir(UUID.randomUUID(), "Juan Carlos Perez", "1234567890", null, "CARNET001", "M", LocalDate.of(2000, 1, 1), "Colombiana", "Soltero", "juan.personal@correo.com", "juan.institucional@correo.edu.co", "Ingenieria de Sistemas", null, 2020, "10MO ", 60, 3.8f, "Activo", "Presencial", 30, "Transporte publico");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConAnnoIngresoInvalido() {
        Estudiante modelo = Estudiante.construir(UUID.randomUUID(), "Juan Carlos Perez", "1234567890", null, "CARNET001", "M", LocalDate.of(2000, 1, 1), "Colombiana", "Soltero", "juan.personal@correo.com", "juan.institucional@correo.edu.co", "Ingenieria de Sistemas", "Facultad de Ingenieria", 1999, "10MO ", 60, 3.8f, "Activo", "Presencial", 30, "Transporte publico");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConSemestreActualVacio() {
        Estudiante modelo = Estudiante.construir(UUID.randomUUID(), "Juan Carlos Perez", "1234567890", null, "CARNET001", "M", LocalDate.of(2000, 1, 1), "Colombiana", "Soltero", "juan.personal@correo.com", "juan.institucional@correo.edu.co", "Ingenieria de Sistemas", "Facultad de Ingenieria", 2020, null, 60, 3.8f, "Activo", "Presencial", 30, "Transporte publico");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConEstadoAcademicoVacio() {
        Estudiante modelo = Estudiante.construir(UUID.randomUUID(), "Juan Carlos Perez", "1234567890", null, "CARNET001", "M", LocalDate.of(2000, 1, 1), "Colombiana", "Soltero", "juan.personal@correo.com", "juan.institucional@correo.edu.co", "Ingenieria de Sistemas", "Facultad de Ingenieria", 2020, "10MO ", 60, 3.8f, null, "Presencial", 30, "Transporte publico");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConModalidadEstudioVacia() {
        Estudiante modelo = Estudiante.construir(UUID.randomUUID(), "Juan Carlos Perez", "1234567890", null, "CARNET001", "M", LocalDate.of(2000, 1, 1), "Colombiana", "Soltero", "juan.personal@correo.com", "juan.institucional@correo.edu.co", "Ingenieria de Sistemas", "Facultad de Ingenieria", 2020, "10MO ", 60, 3.8f, "Activo", null, 30, "Transporte publico");
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }
}
