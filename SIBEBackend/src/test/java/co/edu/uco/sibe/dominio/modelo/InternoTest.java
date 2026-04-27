package co.edu.uco.sibe.dominio.modelo;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InternoTest {

    @Test
    void deberiaConstruirInternoConValoresPorDefecto() {
        Interno interno = Interno.construir();

        assertNotNull(interno);
        assertNotNull(interno.getIdentificador());
        assertEquals("", interno.getNombreCompleto());
        assertEquals("", interno.getNumeroIdentificacion());
        assertNotNull(interno.getCiudadResidencia());
        assertEquals("", interno.getIdCarnet());
        assertEquals("", interno.getSexo());
    }

    @Test
    void deberiaConstruirInternoConValoresEspecificados() {
        UUID id = UUID.randomUUID();
        CiudadResidencia ciudad = CiudadResidencia.construir(UUID.randomUUID(), "Medellin");

        Interno interno = Interno.construir(id, "Juan Perez", "123456", ciudad, "CAR001", "M");

        assertEquals(id, interno.getIdentificador());
        assertEquals("Juan Perez", interno.getNombreCompleto());
        assertEquals("123456", interno.getNumeroIdentificacion());
        assertEquals(ciudad, interno.getCiudadResidencia());
        assertEquals("CAR001", interno.getIdCarnet());
        assertEquals("M", interno.getSexo());
    }

    @Test
    void deberiaConstruirInternoConValoresNulosUsandoDefectos() {
        Interno interno = Interno.construir(UUID.randomUUID(), null, null, null, null, null);

        assertNotNull(interno);
        assertEquals("", interno.getNombreCompleto());
        assertEquals("", interno.getNumeroIdentificacion());
        assertNotNull(interno.getCiudadResidencia());
        assertEquals("", interno.getIdCarnet());
        assertEquals("", interno.getSexo());
    }
}
