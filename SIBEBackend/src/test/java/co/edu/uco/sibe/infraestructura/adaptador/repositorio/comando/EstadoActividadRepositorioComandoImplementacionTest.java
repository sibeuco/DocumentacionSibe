package co.edu.uco.sibe.infraestructura.adaptador.repositorio.comando;

import co.edu.uco.sibe.dominio.modelo.EstadoActividad;
import co.edu.uco.sibe.infraestructura.adaptador.dao.EstadoActividadDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.EstadoActividadEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.EstadoActividadMapeador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstadoActividadRepositorioComandoImplementacionTest {

    @Mock private EstadoActividadDAO estadoActividadDAO;
    @Mock private EstadoActividadMapeador estadoActividadMapeador;

    private EstadoActividadRepositorioComandoImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new EstadoActividadRepositorioComandoImplementacion(estadoActividadDAO, estadoActividadMapeador);
    }

    @Test
    void deberiaGuardarEstadoActividad() {
        UUID idEsperado = UUID.randomUUID();
        EstadoActividad estadoActividad = mock(EstadoActividad.class);
        EstadoActividadEntidad entidad = mock(EstadoActividadEntidad.class);

        when(estadoActividadMapeador.construirEntidad(estadoActividad)).thenReturn(entidad);
        when(estadoActividadDAO.save(entidad)).thenReturn(entidad);
        when(entidad.getIdentificador()).thenReturn(idEsperado);

        UUID resultado = repositorio.guardar(estadoActividad);

        assertEquals(idEsperado, resultado);
    }
}
