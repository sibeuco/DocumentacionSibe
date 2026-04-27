package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.Proyecto;
import co.edu.uco.sibe.dominio.transversal.excepcion.LongitudExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.PatronExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorObligatorioExcepcion;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProyectoReglaTest {

    private final ProyectoRegla regla = ProyectoRegla.obtenerInstancia();

    @Test
    void deberiaValidarCamposConDatosValidos() {
        Proyecto modelo = Proyecto.construir(UUID.randomUUID(), "PRY001", "Proyecto de Extension Valido", "Objetivo del proyecto de extension valido", new ArrayList<>());
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
        Proyecto modelo = Proyecto.construir(UUID.randomUUID(), "PRY001", null, "Objetivo del proyecto valido", new ArrayList<>());
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreCorto() {
        Proyecto modelo = Proyecto.construir(UUID.randomUUID(), "PRY001", "Corto", "Objetivo del proyecto valido", new ArrayList<>());
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreExcedeMaximo() {
        Proyecto modelo = Proyecto.construir(UUID.randomUUID(), "PRY001", "a".repeat(101), "Objetivo del proyecto valido", new ArrayList<>());
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConNombreInvalido() {
        Proyecto modelo = Proyecto.construir(UUID.randomUUID(), "PRY001", "Proyecto {invalido}", "Objetivo del proyecto valido", new ArrayList<>());
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConObjetivoVacio() {
        Proyecto modelo = Proyecto.construir(UUID.randomUUID(), "PRY001", "Proyecto de Extension Valido", null, new ArrayList<>());
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConObjetivoCorto() {
        Proyecto modelo = Proyecto.construir(UUID.randomUUID(), "PRY001", "Proyecto de Extension Valido", "Corto", new ArrayList<>());
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConObjetivoExcedeMaximo() {
        Proyecto modelo = Proyecto.construir(UUID.randomUUID(), "PRY001", "Proyecto de Extension Valido", "a".repeat(501), new ArrayList<>());
        assertThrows(LongitudExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaFallarConObjetivoInvalido() {
        Proyecto modelo = Proyecto.construir(UUID.randomUUID(), "PRY001", "Proyecto de Extension Valido", "Objetivo {invalido} <script>", new ArrayList<>());
        assertThrows(PatronExcepcion.class, () -> regla.validarCampos(modelo));
    }

    @Test
    void deberiaValidarNumeroProyectoValido() {
        assertDoesNotThrow(() -> regla.validarNumeroProyecto("PRY001"));
    }

    @Test
    void deberiaFallarConNumeroProyectoNulo() {
        assertThrows(IllegalArgumentException.class, () -> regla.validarNumeroProyecto(null));
    }

    @Test
    void deberiaFallarConNumeroProyectoExcedeMaximo() {
        assertThrows(LongitudExcepcion.class, () -> regla.validarNumeroProyecto("a".repeat(13)));
    }

    @Test
    void deberiaFallarConNumeroProyectoInvalido() {
        assertThrows(PatronExcepcion.class, () -> regla.validarNumeroProyecto("PRY{invalido}"));
    }
}
