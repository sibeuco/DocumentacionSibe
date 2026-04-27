package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.modelo.CiudadResidencia;
import co.edu.uco.sibe.infraestructura.adaptador.dao.CiudadResidenciaDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.InternoCiudadResidenciaDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.CiudadResidenciaEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.InternoCiudadResidenciaEntidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InternoCiudadResidenciaMapeadorTest {

    @Mock
    private CiudadResidenciaMapeador ciudadResidenciaMapeador;

    @Mock
    private InternoCiudadResidenciaDAO internoCiudadResidenciaDAO;

    @Mock
    private CiudadResidenciaDAO ciudadResidenciaDAO;

    private InternoCiudadResidenciaMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new InternoCiudadResidenciaMapeador(ciudadResidenciaMapeador, internoCiudadResidenciaDAO, ciudadResidenciaDAO);
    }

    @Test
    void deberiaConstruirEntidadCuandoCiudadExiste() {
        UUID id = UUID.randomUUID();
        CiudadResidencia ciudad = CiudadResidencia.construir(id, "Medellín");
        CiudadResidenciaEntidad ciudadEntidad = new CiudadResidenciaEntidad(id, "Medellín");

        when(ciudadResidenciaDAO.findFirstByDescripcion("Medellín")).thenReturn(ciudadEntidad);
        when(internoCiudadResidenciaDAO.findById(any(UUID.class))).thenReturn(Optional.empty());

        InternoCiudadResidenciaEntidad entidad = mapeador.construirEntidad(ciudad);

        assertNotNull(entidad);
        assertEquals(ciudadEntidad, entidad.getCiudadResidencia());
    }

    @Test
    void deberiaConstruirEntidadCuandoCiudadNoExiste() {
        UUID id = UUID.randomUUID();
        CiudadResidencia ciudad = CiudadResidencia.construir(id, "Bogotá");
        CiudadResidenciaEntidad ciudadEntidad = new CiudadResidenciaEntidad(id, "Bogotá");

        when(ciudadResidenciaDAO.findFirstByDescripcion("Bogotá")).thenReturn(null);
        when(ciudadResidenciaMapeador.construirEntidad(ciudad)).thenReturn(ciudadEntidad);
        when(ciudadResidenciaDAO.save(ciudadEntidad)).thenReturn(ciudadEntidad);
        when(internoCiudadResidenciaDAO.findById(any(UUID.class))).thenReturn(Optional.empty());

        InternoCiudadResidenciaEntidad entidad = mapeador.construirEntidad(ciudad);

        assertNotNull(entidad);
    }

    @Test
    void deberiaConstruirModeloDesdeEntidad() {
        UUID id = UUID.randomUUID();
        CiudadResidenciaEntidad ciudadEntidad = new CiudadResidenciaEntidad(id, "Cali");
        InternoCiudadResidenciaEntidad entidad = new InternoCiudadResidenciaEntidad(UUID.randomUUID(), ciudadEntidad);
        CiudadResidencia ciudad = CiudadResidencia.construir(id, "Cali");

        when(ciudadResidenciaMapeador.construirModelo(ciudadEntidad)).thenReturn(ciudad);

        CiudadResidencia resultado = mapeador.construirModelo(entidad);

        assertEquals(id, resultado.getIdentificador());
        assertEquals("Cali", resultado.getDescripcion());
    }

    @Test
    void deberiaModificarEntidad() {
        UUID id = UUID.randomUUID();
        CiudadResidenciaEntidad ciudadVieja = new CiudadResidenciaEntidad(id, "Vieja");
        InternoCiudadResidenciaEntidad puente = new InternoCiudadResidenciaEntidad(UUID.randomUUID(), ciudadVieja);
        CiudadResidencia ciudad = CiudadResidencia.construir(UUID.randomUUID(), "Nueva");
        CiudadResidenciaEntidad ciudadNueva = new CiudadResidenciaEntidad(UUID.randomUUID(), "Nueva");

        when(ciudadResidenciaDAO.findFirstByDescripcion("Nueva")).thenReturn(ciudadNueva);

        mapeador.modificarEntidad(puente, ciudad);

        assertEquals(ciudadNueva, puente.getCiudadResidencia());
    }
}
