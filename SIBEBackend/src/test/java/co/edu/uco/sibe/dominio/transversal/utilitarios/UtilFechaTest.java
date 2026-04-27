package co.edu.uco.sibe.dominio.transversal.utilitarios;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class UtilFechaTest {

    @Test
    void deberiaLanzarExcepcionAlInstanciarClaseUtilitaria() throws Exception {
        var constructor = UtilFecha.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        assertThrows(InvocationTargetException.class, constructor::newInstance);
    }

    @Test
    void deberiaRetornarFechaActual() {
        LocalDate fecha = UtilFecha.obtenerFechaActual();
        assertNotNull(fecha);
        assertEquals(LocalDate.now(), fecha);
    }

    @Test
    void deberiaRetornarFechaDefecto() {
        LocalDate fecha = UtilFecha.obtenerFechaDefecto();
        assertEquals(LocalDate.of(1900, 1, 1), fecha);
    }

    @Test
    void deberiaRetornarHoraDefecto() {
        LocalTime hora = UtilFecha.obtenerHoraDefecto();
        assertEquals(LocalTime.of(0, 0, 0), hora);
    }

    @Test
    void deberiaRetornarFechaPorDefectoCuandoEsNula() {
        LocalDate resultado = UtilFecha.obtenerValorFechaPorDefecto(null);
        assertEquals(LocalDate.of(1900, 1, 1), resultado);
    }

    @Test
    void deberiaRetornarFechaOriginalCuandoNoEsNula() {
        LocalDate fecha = LocalDate.of(2026, 3, 24);
        assertEquals(fecha, UtilFecha.obtenerValorFechaPorDefecto(fecha));
    }

    @Test
    void deberiaRetornarHoraPorDefectoCuandoEsNula() {
        LocalTime resultado = UtilFecha.obtenerValorHoraPorDefecto(null);
        assertEquals(LocalTime.of(0, 0, 0), resultado);
    }

    @Test
    void deberiaFormatearTextoAFecha() {
        LocalDate resultado = UtilFecha.formatearTextoAFecha("2026-03-24");
        assertEquals(LocalDate.of(2026, 3, 24), resultado);
    }
}
