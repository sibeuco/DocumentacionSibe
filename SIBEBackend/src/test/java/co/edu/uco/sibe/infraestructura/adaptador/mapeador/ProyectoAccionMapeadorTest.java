package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.modelo.Accion;
import co.edu.uco.sibe.infraestructura.adaptador.dao.ProyectoAccionDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.AccionEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.ProyectoAccionEntidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProyectoAccionMapeadorTest {

    @Mock
    private AccionMapeador accionMapeador;

    @Mock
    private ProyectoAccionDAO proyectoAccionDAO;

    private ProyectoAccionMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new ProyectoAccionMapeador(accionMapeador, proyectoAccionDAO);
    }

    @Test
    void deberiaConstruirEntidadDesdeAccion() {
        UUID id = UUID.randomUUID();
        Accion accion = Accion.construir(id, "Detalle", "Objetivo");
        AccionEntidad accionEntidad = new AccionEntidad(id, "Detalle", "Objetivo");

        when(accionMapeador.construirEntidad(accion)).thenReturn(accionEntidad);
        when(proyectoAccionDAO.findById(any(UUID.class))).thenReturn(Optional.empty());

        ProyectoAccionEntidad entidad = mapeador.construirEntidad(accion);

        assertNotNull(entidad);
        assertEquals(accionEntidad, entidad.getAccion());
    }

    @Test
    void deberiaConstruirModeloDesdeEntidad() {
        UUID id = UUID.randomUUID();
        AccionEntidad accionEntidad = new AccionEntidad(id, "Detalle", "Objetivo");
        ProyectoAccionEntidad entidad = new ProyectoAccionEntidad(UUID.randomUUID(), accionEntidad);
        Accion accion = Accion.construir(id, "Detalle", "Objetivo");

        when(accionMapeador.construriModelo(accionEntidad)).thenReturn(accion);

        Accion resultado = mapeador.construirModelo(entidad);

        assertEquals(id, resultado.getIdentificador());
    }

    @Test
    void deberiaConstruirListaEntidades() {
        Accion a1 = Accion.construir(UUID.randomUUID(), "D1", "O1");
        AccionEntidad ae1 = new AccionEntidad(UUID.randomUUID(), "D1", "O1");

        when(accionMapeador.construirEntidad(any(Accion.class))).thenReturn(ae1);
        when(proyectoAccionDAO.findById(any(UUID.class))).thenReturn(Optional.empty());

        List<ProyectoAccionEntidad> entidades = mapeador.construirEntidades(List.of(a1));

        assertEquals(1, entidades.size());
    }

    @Test
    void deberiaConstruirListaModelos() {
        UUID id = UUID.randomUUID();
        AccionEntidad ae = new AccionEntidad(id, "D1", "O1");
        ProyectoAccionEntidad pae = new ProyectoAccionEntidad(UUID.randomUUID(), ae);
        Accion accion = Accion.construir(id, "D1", "O1");

        when(accionMapeador.construriModelo(ae)).thenReturn(accion);

        List<Accion> modelos = mapeador.construirModelos(List.of(pae));

        assertEquals(1, modelos.size());
    }

    @Test
    void deberiaActualizarTodosAgregandoNuevos() {
        UUID idExistente = UUID.randomUUID();
        UUID idNuevo = UUID.randomUUID();
        AccionEntidad aeExistente = new AccionEntidad(idExistente, "Existente", "Obj");
        ProyectoAccionEntidad paeExistente = new ProyectoAccionEntidad(UUID.randomUUID(), aeExistente);
        List<ProyectoAccionEntidad> entidades = new ArrayList<>(List.of(paeExistente));

        Accion accionExistente = Accion.construir(idExistente, "Existente", "Obj");
        Accion accionNueva = Accion.construir(idNuevo, "Nueva", "Obj nuevo");
        AccionEntidad aeNueva = new AccionEntidad(idNuevo, "Nueva", "Obj nuevo");

        when(accionMapeador.construirEntidad(accionNueva)).thenReturn(aeNueva);
        when(proyectoAccionDAO.findById(any(UUID.class))).thenReturn(Optional.empty());

        mapeador.actualizarTodos(entidades, List.of(accionExistente, accionNueva));

        assertEquals(2, entidades.size());
    }
}
