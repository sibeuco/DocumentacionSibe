package co.edu.uco.sibe.infraestructura.configuracion.dataloader.fabrica;

import co.edu.uco.sibe.aplicacion.comando.TipoIndicadorComando;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DatosTipoIndicadorFabricaTest {

    @Test
    void deberiaCrearCincoTiposIndicador() {
        List<TipoIndicadorComando> tipos = DatosTipoIndicadorFabrica.crearTiposIndicador();

        assertEquals(5, tipos.size());
    }

    @Test
    void deberiaCrearTipoIndicadorEficiencia() {
        TipoIndicadorComando tipo = DatosTipoIndicadorFabrica.crearTipoIndicadorEficiencia();

        assertEquals("Eficiencia", tipo.getNaturaleza());
        assertEquals("Gestión", tipo.getTipologia());
    }

    @Test
    void deberiaCrearTipoIndicadorCapacidadInstalada() {
        TipoIndicadorComando tipo = DatosTipoIndicadorFabrica.crearTipoIndicadorCapacidadInstalada();

        assertEquals("Capacidad instalada", tipo.getNaturaleza());
    }

    @Test
    void deberiaCrearTipoIndicadorEficacia() {
        TipoIndicadorComando tipo = DatosTipoIndicadorFabrica.crearTipoIndicadorEficacia();

        assertNotNull(tipo.getNaturaleza());
        assertNotNull(tipo.getTipologia());
    }

    @Test
    void deberiaCrearTipoIndicadorEfectividad() {
        TipoIndicadorComando tipo = DatosTipoIndicadorFabrica.crearTipoIndicadorEfectividad();

        assertNotNull(tipo.getNaturaleza());
        assertNotNull(tipo.getTipologia());
    }

    @Test
    void deberiaCrearTipoIndicadorValor() {
        TipoIndicadorComando tipo = DatosTipoIndicadorFabrica.crearTipoIndicadorValor();

        assertNotNull(tipo.getNaturaleza());
        assertNotNull(tipo.getTipologia());
    }
}
