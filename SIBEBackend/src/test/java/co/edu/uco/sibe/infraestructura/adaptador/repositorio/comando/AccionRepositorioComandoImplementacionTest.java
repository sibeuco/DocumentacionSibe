package co.edu.uco.sibe.infraestructura.adaptador.repositorio.comando;

import co.edu.uco.sibe.dominio.modelo.Accion;
import co.edu.uco.sibe.infraestructura.adaptador.dao.AccionDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.AccionEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.AccionMapeador;
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
class AccionRepositorioComandoImplementacionTest {

    @Mock private AccionDAO accionDAO;
    @Mock private AccionMapeador accionMapeador;

    private AccionRepositorioComandoImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new AccionRepositorioComandoImplementacion(accionDAO, accionMapeador);
    }

    @Test
    void deberiaGuardarAccion() {
        UUID idEsperado = UUID.randomUUID();
        Accion accion = mock(Accion.class);
        AccionEntidad entidad = mock(AccionEntidad.class);

        when(accionMapeador.construirEntidad(accion)).thenReturn(entidad);
        when(accionDAO.save(entidad)).thenReturn(entidad);
        when(entidad.getIdentificador()).thenReturn(idEsperado);

        UUID resultado = repositorio.guardar(accion);

        assertEquals(idEsperado, resultado);
    }

    @Test
    void deberiaModificarAccion() {
        UUID id = UUID.randomUUID();
        UUID idEsperado = UUID.randomUUID();
        Accion accion = mock(Accion.class);
        AccionEntidad entidad = mock(AccionEntidad.class);

        when(accionDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(accionDAO.save(entidad)).thenReturn(entidad);
        when(entidad.getIdentificador()).thenReturn(idEsperado);

        UUID resultado = repositorio.modificar(accion, id);

        assertEquals(idEsperado, resultado);
        verify(accionMapeador).actualizarEntidad(entidad, accion);
    }
}
