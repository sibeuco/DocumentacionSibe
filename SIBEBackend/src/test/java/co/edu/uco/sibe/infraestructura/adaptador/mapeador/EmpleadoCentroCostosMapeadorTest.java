package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.modelo.CentroCostos;
import co.edu.uco.sibe.infraestructura.adaptador.dao.CentroCostosDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.EmpleadoCentroCostosDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.CentroCostosEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.EmpleadoCentroCostosEntidad;
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
class EmpleadoCentroCostosMapeadorTest {

    @Mock
    private CentroCostosMapeador centroCostosMapeador;

    @Mock
    private EmpleadoCentroCostosDAO empleadoCentroCostosDAO;

    @Mock
    private CentroCostosDAO centroCostosDAO;

    private EmpleadoCentroCostosMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new EmpleadoCentroCostosMapeador(centroCostosMapeador, empleadoCentroCostosDAO, centroCostosDAO);
    }

    @Test
    void deberiaConstruirEntidadCuandoCentroCostosExiste() {
        UUID id = UUID.randomUUID();
        CentroCostos centroCostos = CentroCostos.construir(id, "CC-001", "Centro existente");
        CentroCostosEntidad ccEntidad = new CentroCostosEntidad(id, "CC-001", "Centro existente");

        when(centroCostosDAO.findFirstByCodigo("CC-001")).thenReturn(ccEntidad);
        when(empleadoCentroCostosDAO.findById(any(UUID.class))).thenReturn(Optional.empty());

        EmpleadoCentroCostosEntidad entidad = mapeador.construirEntidad(centroCostos);

        assertNotNull(entidad);
        assertEquals(ccEntidad, entidad.getCentroCostos());
    }

    @Test
    void deberiaConstruirEntidadCuandoCentroCostosNoExiste() {
        UUID id = UUID.randomUUID();
        CentroCostos centroCostos = CentroCostos.construir(id, "CC-NEW", "Nuevo centro");
        CentroCostosEntidad ccEntidad = new CentroCostosEntidad(id, "CC-NEW", "Nuevo centro");

        when(centroCostosDAO.findFirstByCodigo("CC-NEW")).thenReturn(null);
        when(centroCostosMapeador.construirEntidad(centroCostos)).thenReturn(ccEntidad);
        when(centroCostosDAO.save(ccEntidad)).thenReturn(ccEntidad);
        when(empleadoCentroCostosDAO.findById(any(UUID.class))).thenReturn(Optional.empty());

        EmpleadoCentroCostosEntidad entidad = mapeador.construirEntidad(centroCostos);

        assertNotNull(entidad);
    }

    @Test
    void deberiaConstruirModeloDesdeEntidad() {
        UUID id = UUID.randomUUID();
        CentroCostosEntidad ccEntidad = new CentroCostosEntidad(id, "CC-001", "Centro");
        EmpleadoCentroCostosEntidad entidad = new EmpleadoCentroCostosEntidad(UUID.randomUUID(), ccEntidad);
        CentroCostos centroCostos = CentroCostos.construir(id, "CC-001", "Centro");

        when(centroCostosMapeador.construirModelo(ccEntidad)).thenReturn(centroCostos);

        CentroCostos resultado = mapeador.construirModelo(entidad);

        assertEquals(id, resultado.getIdentificador());
    }

    @Test
    void deberiaModificarEntidad() {
        UUID id = UUID.randomUUID();
        CentroCostosEntidad ccVieja = new CentroCostosEntidad(id, "CC-OLD", "Viejo");
        EmpleadoCentroCostosEntidad puente = new EmpleadoCentroCostosEntidad(UUID.randomUUID(), ccVieja);
        CentroCostos centroCostos = CentroCostos.construir(UUID.randomUUID(), "CC-NEW", "Nuevo");
        CentroCostosEntidad ccNueva = new CentroCostosEntidad(UUID.randomUUID(), "CC-NEW", "Nuevo");

        when(centroCostosDAO.findFirstByCodigo("CC-NEW")).thenReturn(ccNueva);

        mapeador.modificarEntidad(puente, centroCostos);

        assertEquals(ccNueva, puente.getCentroCostos());
    }
}
