package co.edu.uco.sibe.dominio.transversal.utilitarios;

import co.edu.uco.sibe.dominio.transversal.excepcion.LongitudExcepcion;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class ValidadorNumeroTest {

    @Test
    void deberiaLanzarExcepcionAlInstanciarClaseUtilitaria() throws Exception {
        var constructor = ValidadorNumero.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        assertThrows(InvocationTargetException.class, constructor::newInstance);
    }

    @Test
    void deberiaValidarNumeroEntreRangoExitosamente() {
        assertDoesNotThrow(() -> ValidadorNumero.validarNumeroEntre(5, 1, 10, "Error"));
    }

    @Test
    void deberiaLanzarExcepcionCuandoNumeroFueraDeRango() {
        assertThrows(LongitudExcepcion.class, () -> ValidadorNumero.validarNumeroEntre(15, 1, 10, "Error rango"));
    }

    @Test
    void deberiaLanzarExcepcionCuandoNumeroEsNulo() {
        assertThrows(LongitudExcepcion.class, () -> ValidadorNumero.validarNumeroEntre(null, 1, 10, "Error nulo"));
    }

    @Test
    void deberiaRetornarCeroCuandoNumeroEsNulo() {
        Integer resultado = ValidadorNumero.obtenerNumeroPorDefecto(null);
        assertNotNull(resultado);
    }
}
