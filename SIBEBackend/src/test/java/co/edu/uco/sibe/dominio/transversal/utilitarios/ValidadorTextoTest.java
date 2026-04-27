package co.edu.uco.sibe.dominio.transversal.utilitarios;

import co.edu.uco.sibe.dominio.transversal.excepcion.PatronExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorObligatorioExcepcion;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class ValidadorTextoTest {

    @Test
    void deberiaLanzarExcepcionAlInstanciarClaseUtilitaria() throws Exception {
        var constructor = ValidadorTexto.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        assertThrows(InvocationTargetException.class, constructor::newInstance);
    }

    @Test
    void deberiaObtenerTipoProgramaPostgrado() {
        assertEquals("Postgrado", ValidadorTexto.obtenerTipoPrograma("Maestría en Ingeniería"));
    }

    @Test
    void deberiaObtenerTipoProgramaPregrado() {
        assertEquals("Pregrado", ValidadorTexto.obtenerTipoPrograma("Ingeniería de Sistemas"));
    }

    @Test
    void deberiaObtenerTipoProgramaPregradoCuandoEsNulo() {
        assertEquals("Pregrado", ValidadorTexto.obtenerTipoPrograma(null));
    }

    @Test
    void deberiaObtenerTipoProgramaPregradoCuandoEsVacio() {
        assertEquals("Pregrado", ValidadorTexto.obtenerTipoPrograma(""));
    }

    @Test
    void deberiaValidarObligatorioExitosamente() {
        assertDoesNotThrow(() -> ValidadorTexto.validarObligatorio("valor", "mensaje"));
    }

    @Test
    void deberiaLanzarExcepcionCuandoTextoObligatorioEsVacio() {
        assertThrows(ValorObligatorioExcepcion.class,
                () -> ValidadorTexto.validarObligatorio("", "Campo requerido"));
    }

    @Test
    void deberiaIdentificarCadenaVaciaComoTal() {
        assertTrue(ValidadorTexto.estaCadenaVacia(""));
        assertTrue(ValidadorTexto.estaCadenaVacia("   "));
        assertTrue(ValidadorTexto.estaCadenaVacia(null));
        assertFalse(ValidadorTexto.estaCadenaVacia("texto"));
    }

    @Test
    void deberiaIdentificarCadenaNula() {
        assertTrue(ValidadorTexto.esNula(null));
        assertFalse(ValidadorTexto.esNula("algo"));
    }

    @Test
    void deberiaObtenerTextoPorDefecto() {
        assertEquals("", ValidadorTexto.obtenerTextoPorDefecto(null));
        assertEquals("valor", ValidadorTexto.obtenerTextoPorDefecto("valor"));
    }
}
