package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.modelo.EstadoActividad;
import co.edu.uco.sibe.infraestructura.adaptador.dao.EstadoActividadDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.EstadoActividadEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.EstadoActividadMapeador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstadoActividadRepositorioConsultaImplementacionTest {

    @Mock private EstadoActividadDAO estadoActividadDAO;
    @Mock private EstadoActividadMapeador estadoActividadMapeador;

    private EstadoActividadRepositorioConsultaImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new EstadoActividadRepositorioConsultaImplementacion(estadoActividadDAO, estadoActividadMapeador);
    }

    @Test
    void deberiaRetornarEstadoActividadCuandoExisteIdentificador() {
        UUID id = UUID.randomUUID();
        EstadoActividadEntidad entidad = new EstadoActividadEntidad();
        EstadoActividad modelo = mock(EstadoActividad.class);

        when(estadoActividadDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(estadoActividadMapeador.construirModelo(entidad)).thenReturn(modelo);

        EstadoActividad resultado = repositorio.consultarPorIdentificador(id);

        assertNotNull(resultado);
        assertEquals(modelo, resultado);
    }

    @Test
    void deberiaRetornarNuloCuandoNoExisteIdentificador() {
        UUID id = UUID.randomUUID();

        when(estadoActividadDAO.findById(id)).thenReturn(Optional.empty());

        assertNull(repositorio.consultarPorIdentificador(id));
    }

    @Test
    void deberiaRetornarTrueCuandoHayDatos() {
        when(estadoActividadDAO.count()).thenReturn(5L);

        assertTrue(repositorio.hayDatos());
    }

    @Test
    void deberiaRetornarFalseCuandoNoHayDatos() {
        when(estadoActividadDAO.count()).thenReturn(0L);

        assertFalse(repositorio.hayDatos());
    }

    @Test
    void deberiaRetornarEstadoActividadCuandoExisteNombre() {
        String nombre = "Activo";
        EstadoActividadEntidad entidad = new EstadoActividadEntidad();
        EstadoActividad modelo = mock(EstadoActividad.class);

        when(estadoActividadDAO.findByNombre(nombre)).thenReturn(entidad);
        when(estadoActividadMapeador.construirModelo(entidad)).thenReturn(modelo);

        EstadoActividad resultado = repositorio.consultarPorNombre(nombre);

        assertNotNull(resultado);
        assertEquals(modelo, resultado);
    }

    @Test
    void deberiaRetornarNuloCuandoNoExisteNombre() {
        when(estadoActividadDAO.findByNombre("Inexistente")).thenReturn(null);

        assertNull(repositorio.consultarPorNombre("Inexistente"));
    }
}
