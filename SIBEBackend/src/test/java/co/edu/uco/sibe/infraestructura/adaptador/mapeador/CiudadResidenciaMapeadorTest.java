package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.modelo.CiudadResidencia;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.CiudadResidenciaEntidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CiudadResidenciaMapeadorTest {

    private CiudadResidenciaMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new CiudadResidenciaMapeador();
    }

    @Test
    void deberiaConstruirModeloDesdeEntidad() {
        UUID id = UUID.randomUUID();
        CiudadResidenciaEntidad entidad = new CiudadResidenciaEntidad(id, "Medellín");

        CiudadResidencia modelo = mapeador.construirModelo(entidad);

        assertEquals(id, modelo.getIdentificador());
        assertEquals("Medellín", modelo.getDescripcion());
    }

    @Test
    void deberiaConstruirEntidadDesdeModelo() {
        UUID id = UUID.randomUUID();
        CiudadResidencia modelo = CiudadResidencia.construir(id, "Bogotá");

        CiudadResidenciaEntidad entidad = mapeador.construirEntidad(modelo);

        assertEquals(id, entidad.getIdentificador());
        assertEquals("Bogotá", entidad.getDescripcion());
    }
}
