package co.edu.uco.sibe.dominio.transversal.constante;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IndicadorConstanteTest {

    @Test
    void deberiaRetornarTrueCuandoEsParticipacion() {
        assertTrue(IndicadorConstante.esIndicadorGlobal("Participación"));
    }

    @Test
    void deberiaRetornarTrueCuandoEsCobertura() {
        assertTrue(IndicadorConstante.esIndicadorGlobal("Cobertura"));
    }

    @Test
    void deberiaRetornarTrueCuandoEsParticipacionConDiferenteCasing() {
        assertTrue(IndicadorConstante.esIndicadorGlobal("participación"));
        assertTrue(IndicadorConstante.esIndicadorGlobal("PARTICIPACIÓN"));
        assertTrue(IndicadorConstante.esIndicadorGlobal("COBERTURA"));
        assertTrue(IndicadorConstante.esIndicadorGlobal("cobertura"));
    }

    @Test
    void deberiaRetornarTrueCuandoTieneEspaciosAlrededor() {
        assertTrue(IndicadorConstante.esIndicadorGlobal("  Participación  "));
        assertTrue(IndicadorConstante.esIndicadorGlobal(" Cobertura "));
    }

    @Test
    void deberiaRetornarFalseCuandoEsIndicadorRegular() {
        assertFalse(IndicadorConstante.esIndicadorGlobal("Satisfacción grupos de Interés"));
        assertFalse(IndicadorConstante.esIndicadorGlobal("Reducción de quejas y reclamos"));
    }

    @Test
    void deberiaRetornarFalseCuandoEsNull() {
        assertFalse(IndicadorConstante.esIndicadorGlobal(null));
    }

    @Test
    void deberiaRetornarFalseCuandoEsCadenaVacia() {
        assertFalse(IndicadorConstante.esIndicadorGlobal(""));
        assertFalse(IndicadorConstante.esIndicadorGlobal("   "));
    }

    @Test
    void deberiaContenerDosIndicadoresGlobalesCuandoSeConsultaElSet() {
        assertEquals(2, IndicadorConstante.INDICADORES_GLOBALES.size());
        assertTrue(IndicadorConstante.INDICADORES_GLOBALES.contains("Participación"));
        assertTrue(IndicadorConstante.INDICADORES_GLOBALES.contains("Cobertura"));
    }
}
