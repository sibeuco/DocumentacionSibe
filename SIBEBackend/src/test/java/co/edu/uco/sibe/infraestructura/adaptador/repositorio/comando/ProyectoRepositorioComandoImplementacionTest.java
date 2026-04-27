package co.edu.uco.sibe.infraestructura.adaptador.repositorio.comando;

import co.edu.uco.sibe.dominio.modelo.Proyecto;
import co.edu.uco.sibe.infraestructura.adaptador.dao.ProyectoDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.ProyectoEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.ProyectoMapeador;
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
class ProyectoRepositorioComandoImplementacionTest {

    @Mock private ProyectoDAO proyectoDAO;
    @Mock private ProyectoMapeador proyectoMapeador;

    private ProyectoRepositorioComandoImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new ProyectoRepositorioComandoImplementacion(proyectoDAO, proyectoMapeador);
    }

    @Test
    void deberiaGuardarProyecto() {
        UUID idEsperado = UUID.randomUUID();
        Proyecto proyecto = mock(Proyecto.class);
        ProyectoEntidad entidad = mock(ProyectoEntidad.class);

        when(proyectoMapeador.construirEntidad(proyecto)).thenReturn(entidad);
        when(proyectoDAO.save(entidad)).thenReturn(entidad);
        when(entidad.getIdentificador()).thenReturn(idEsperado);

        UUID resultado = repositorio.guardar(proyecto);

        assertEquals(idEsperado, resultado);
    }

    @Test
    void deberiaModificarProyecto() {
        UUID id = UUID.randomUUID();
        UUID idEsperado = UUID.randomUUID();
        Proyecto proyecto = mock(Proyecto.class);
        ProyectoEntidad entidad = mock(ProyectoEntidad.class);

        when(proyectoDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(proyectoDAO.save(entidad)).thenReturn(entidad);
        when(entidad.getIdentificador()).thenReturn(idEsperado);

        UUID resultado = repositorio.modificar(proyecto, id);

        assertEquals(idEsperado, resultado);
        verify(proyectoMapeador).actualizarEntidad(entidad, proyecto);
    }
}
