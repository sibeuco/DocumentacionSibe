package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.transversal.excepcion.LongitudExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.PatronExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorObligatorioExcepcion;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ActividadReglaTest {

    private final ActividadRegla regla = ActividadRegla.obtenerInstancia();

    @Test
    void deberiaValidarCamposConDatosValidos() {
        Actividad modelo = Actividad.construir(
                UUID.randomUUID(),
                "Actividad de extension universitaria",
                "Objetivo de la actividad de extension universitaria",
                "202501",
                "https://ejemplo.com/insumos/actividad",
                LocalDate.now(),
                null,
                UUID.randomUUID(),
                UUID.randomUUID()
        );
        assertDoesNotThrow(() -> regla.validarCampos(modelo));
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
    void deberiaFallarConNombreVacio() {
        Actividad modelo = Actividad.construir(UUID.randomUUID(), null, "Objetivo de la actividad valida", "202501", "https://ejemplo.com/insumos", LocalDate.now(), null, UUID.randomUUID(), UUID.randomUUID());
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreCorto() {
        Actividad modelo = Actividad.construir(UUID.randomUUID(), "Corto", "Objetivo de la actividad valida", "202501", "https://ejemplo.com/insumos", LocalDate.now(), null, UUID.randomUUID(), UUID.randomUUID());
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreExcedeMaximo() {
        Actividad modelo = Actividad.construir(UUID.randomUUID(), "a".repeat(201), "Objetivo de la actividad valida", "202501", "https://ejemplo.com/insumos", LocalDate.now(), null, UUID.randomUUID(), UUID.randomUUID());
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreInvalido() {
        Actividad modelo = Actividad.construir(UUID.randomUUID(), "Actividad {invalida} <script>", "Objetivo de la actividad valida", "202501", "https://ejemplo.com/insumos", LocalDate.now(), null, UUID.randomUUID(), UUID.randomUUID());
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConObjetivoVacio() {
        Actividad modelo = Actividad.construir(UUID.randomUUID(), "Actividad de extension universitaria", null, "202501", "https://ejemplo.com/insumos", LocalDate.now(), null, UUID.randomUUID(), UUID.randomUUID());
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConObjetivoCorto() {
        Actividad modelo = Actividad.construir(UUID.randomUUID(), "Actividad de extension universitaria", "Corto", "202501", "https://ejemplo.com/insumos", LocalDate.now(), null, UUID.randomUUID(), UUID.randomUUID());
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConObjetivoExcedeMaximo() {
        Actividad modelo = Actividad.construir(UUID.randomUUID(), "Actividad de extension universitaria", "a".repeat(501), "202501", "https://ejemplo.com/insumos", LocalDate.now(), null, UUID.randomUUID(), UUID.randomUUID());
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConSemestreVacio() {
        Actividad modelo = Actividad.construir(UUID.randomUUID(), "Actividad de extension universitaria", "Objetivo de la actividad de extension universitaria", null, "https://ejemplo.com/insumos", LocalDate.now(), null, UUID.randomUUID(), UUID.randomUUID());
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConSemestreInvalido() {
        Actividad modelo = Actividad.construir(UUID.randomUUID(), "Actividad de extension universitaria", "Objetivo de la actividad de extension universitaria", "INVAL!", "https://ejemplo.com/insumos", LocalDate.now(), null, UUID.randomUUID(), UUID.randomUUID());
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConRutaInsumosVacia() {
        Actividad modelo = Actividad.construir(UUID.randomUUID(), "Actividad de extension universitaria", "Objetivo de la actividad de extension universitaria", "202501", null, LocalDate.now(), null, UUID.randomUUID(), UUID.randomUUID());
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConRutaInsumosCorta() {
        Actividad modelo = Actividad.construir(UUID.randomUUID(), "Actividad de extension universitaria", "Objetivo de la actividad de extension universitaria", "202501", "corta", LocalDate.now(), null, UUID.randomUUID(), UUID.randomUUID());
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConRutaInsumosExcedeMaximo() {
        Actividad modelo = Actividad.construir(UUID.randomUUID(), "Actividad de extension universitaria", "Objetivo de la actividad de extension universitaria", "202501", "a".repeat(3001), LocalDate.now(), null, UUID.randomUUID(), UUID.randomUUID());
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }
}
