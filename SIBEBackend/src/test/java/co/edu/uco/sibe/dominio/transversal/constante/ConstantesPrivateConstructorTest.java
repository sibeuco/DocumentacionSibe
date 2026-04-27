package co.edu.uco.sibe.dominio.transversal.constante;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class ConstantesPrivateConstructorTest {

    @Test
    void datoConstanteNoDebeInstanciarse() throws Exception {
        var c = DatoConstante.class.getDeclaredConstructor();
        c.setAccessible(true);
        assertThrows(InvocationTargetException.class, c::newInstance);
    }

    @Test
    void seguridadConstanteNoDebeInstanciarse() throws Exception {
        var c = SeguridadConstante.class.getDeclaredConstructor();
        c.setAccessible(true);
        assertThrows(InvocationTargetException.class, c::newInstance);
    }

    @Test
    void mensajesErrorConstanteNoDebeInstanciarse() throws Exception {
        var c = MensajesErrorConstante.class.getDeclaredConstructor();
        c.setAccessible(true);
        assertThrows(InvocationTargetException.class, c::newInstance);
    }

    @Test
    void mensajesSistemaConstanteNoDebeInstanciarse() throws Exception {
        var c = MensajesSistemaConstante.class.getDeclaredConstructor();
        c.setAccessible(true);
        assertThrows(InvocationTargetException.class, c::newInstance);
    }

    @Test
    void archivoConstanteNoDebeInstanciarse() throws Exception {
        var c = ArchivoConstante.class.getDeclaredConstructor();
        c.setAccessible(true);
        assertThrows(InvocationTargetException.class, c::newInstance);
    }

    @Test
    void mensajesValidacionConstanteNoDebeInstanciarse() throws Exception {
        var c = MensajesValidacionConstante.class.getDeclaredConstructor();
        c.setAccessible(true);
        assertThrows(InvocationTargetException.class, c::newInstance);
    }

    @Test
    void apiEndpointConstanteNoDebeInstanciarse() throws Exception {
        var c = ApiEndpointConstante.class.getDeclaredConstructor();
        c.setAccessible(true);
        assertThrows(InvocationTargetException.class, c::newInstance);
    }

    @Test
    void textoConstanteNoDebeInstanciarse() throws Exception {
        var c = TextoConstante.class.getDeclaredConstructor();
        c.setAccessible(true);
        assertThrows(InvocationTargetException.class, c::newInstance);
    }

    @Test
    void persistenciaConstanteNoDebeInstanciarse() throws Exception {
        var c = PersistenciaConstante.class.getDeclaredConstructor();
        c.setAccessible(true);
        assertThrows(InvocationTargetException.class, c::newInstance);
    }

    @Test
    void correoConstanteNoDebeInstanciarse() throws Exception {
        var c = CorreoConstante.class.getDeclaredConstructor();
        c.setAccessible(true);
        assertThrows(InvocationTargetException.class, c::newInstance);
    }

    @Test
    void numeroConstanteNoDebeInstanciarse() throws Exception {
        var c = NumeroConstante.class.getDeclaredConstructor();
        c.setAccessible(true);
        assertThrows(InvocationTargetException.class, c::newInstance);
    }

    @Test
    void indicadorConstanteNoDebeInstanciarse() throws Exception {
        var c = IndicadorConstante.class.getDeclaredConstructor();
        c.setAccessible(true);
        assertThrows(InvocationTargetException.class, c::newInstance);
    }

    @Test
    void esIndicadorGlobalDeberiaRetornarFalseCuandoNombreEsNulo() {
        assertFalse(IndicadorConstante.esIndicadorGlobal(null));
    }

    @Test
    void esIndicadorGlobalDeberiaRetornarTrueCuandoEsParticipacion() {
        assertTrue(IndicadorConstante.esIndicadorGlobal("Participación"));
    }
}
