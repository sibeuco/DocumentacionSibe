package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.modelo.EstadoActividad;
import co.edu.uco.sibe.infraestructura.adaptador.dao.EjecucionActividadEstadoActividadDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.EjecucionActividadEstadoActividadEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.EstadoActividadEntidad;
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
class EjecucionActividadEstadoActividadMapeadorTest {

    @Mock
    private EstadoActividadMapeador estadoActividadMapeador;

    @Mock
    private EjecucionActividadEstadoActividadDAO dao;

    private EjecucionActividadEstadoActividadMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new EjecucionActividadEstadoActividadMapeador(estadoActividadMapeador, dao);
    }

    @Test
    void deberiaConstruirEntidadDesdeEstadoActividad() {
        UUID id = UUID.randomUUID();
        EstadoActividad estado = EstadoActividad.construir(id, "Pendiente");
        EstadoActividadEntidad estadoEntidad = new EstadoActividadEntidad(id, "Pendiente");

        when(estadoActividadMapeador.construirEntidad(estado)).thenReturn(estadoEntidad);
        when(dao.findById(any(UUID.class))).thenReturn(Optional.empty());

        EjecucionActividadEstadoActividadEntidad entidad = mapeador.construirEntidad(estado);

        assertNotNull(entidad);
        assertEquals(estadoEntidad, entidad.getEstadoActividad());
    }

    @Test
    void deberiaConstruirModeloDesdeEntidad() {
        UUID id = UUID.randomUUID();
        EstadoActividadEntidad estadoEntidad = new EstadoActividadEntidad(id, "Completada");
        EjecucionActividadEstadoActividadEntidad entidad = new EjecucionActividadEstadoActividadEntidad(UUID.randomUUID(), estadoEntidad);
        EstadoActividad estado = EstadoActividad.construir(id, "Completada");

        when(estadoActividadMapeador.construirModelo(estadoEntidad)).thenReturn(estado);

        EstadoActividad resultado = mapeador.construirModelo(entidad);

        assertEquals(id, resultado.getIdentificador());
        assertEquals("Completada", resultado.getNombre());
    }

    @Test
    void deberiaActualizarEntidad() {
        UUID id = UUID.randomUUID();
        EstadoActividadEntidad estadoEntidad = new EstadoActividadEntidad(id, "Original");
        EjecucionActividadEstadoActividadEntidad entidad = new EjecucionActividadEstadoActividadEntidad(UUID.randomUUID(), estadoEntidad);
        EstadoActividad nuevoEstado = EstadoActividad.construir(UUID.randomUUID(), "Nuevo");
        EstadoActividadEntidad nuevaEntidad = new EstadoActividadEntidad(UUID.randomUUID(), "Nuevo");

        when(estadoActividadMapeador.construirEntidad(nuevoEstado)).thenReturn(nuevaEntidad);

        mapeador.actualizarEntidad(entidad, nuevoEstado);

        assertEquals(nuevaEntidad, entidad.getEstadoActividad());
    }
}
