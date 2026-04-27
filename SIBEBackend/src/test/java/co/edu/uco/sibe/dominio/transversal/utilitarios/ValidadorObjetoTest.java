package co.edu.uco.sibe.dominio.transversal.utilitarios;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class ValidadorObjetoTest {

    @Test
    void deberiaLanzarExcepcionAlInstanciarClaseUtilitaria() throws Exception {
        var constructor = ValidadorObjeto.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        assertThrows(InvocationTargetException.class, constructor::newInstance);
    }

    @Test
    void deberiaValidarObjetoObligatorio() {
        assertDoesNotThrow(() -> ValidadorObjeto.validarObligatorio("objeto", "mensaje"));
    }

    @Test
    void deberiaLanzarExcepcionCuandoObjetoObligatorioEsNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> ValidadorObjeto.validarObligatorio(null, "El objeto es requerido"));
    }

    @Test
    void deberiaValidarColeccionNoVacia() {
        assertDoesNotThrow(() -> ValidadorObjeto.validarColeccionNoVacia(List.of("a"), "mensaje"));
    }

    @Test
    void deberiaLanzarExcepcionCuandoColeccionEsNula() {
        assertThrows(IllegalArgumentException.class,
                () -> ValidadorObjeto.validarColeccionNoVacia(null, "Coleccion requerida"));
    }

    @Test
    void deberiaLanzarExcepcionCuandoColeccionEstaVacia() {
        assertThrows(IllegalArgumentException.class,
                () -> ValidadorObjeto.validarColeccionNoVacia(new ArrayList<>(), "Coleccion vacia"));
    }

    @Test
    void deberiaRetornarTrueCuandoObjetoEsNulo() {
        assertTrue(ValidadorObjeto.esNulo(null));
    }

    @Test
    void deberiaRetornarFalseCuandoObjetoNoEsNulo() {
        assertFalse(ValidadorObjeto.esNulo("algo"));
    }

    @Test
    void deberiaRetornarObjetoPorDefectoCuandoEsNulo() {
        assertEquals("defecto", ValidadorObjeto.obtenerObjetoPorDefecto(null, "defecto"));
    }

    @Test
    void deberiaRetornarObjetoOriginalCuandoNoEsNulo() {
        assertEquals("original", ValidadorObjeto.obtenerObjetoPorDefecto("original", "defecto"));
    }
}
