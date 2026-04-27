package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.dto.DireccionDTO;
import co.edu.uco.sibe.dominio.dto.DireccionDetalladaDTO;
import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.modelo.Direccion;
import co.edu.uco.sibe.infraestructura.adaptador.dao.DireccionDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.DireccionEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.DireccionDetalladaMapeador;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.DireccionMapeador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DireccionRepositorioConsultaImplementacionTest {

    @Mock private DireccionDAO direccionDAO;
    @Mock private DireccionMapeador direccionMapeador;
    @Mock private DireccionDetalladaMapeador direccionDetalladaMapeador;

    private DireccionRepositorioConsultaImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new DireccionRepositorioConsultaImplementacion(direccionDAO, direccionMapeador, direccionDetalladaMapeador);
    }

    @Test
    void deberiaConsultarDTOs() {
        List<DireccionEntidad> entidades = List.of(new DireccionEntidad());
        when(direccionDAO.findAll()).thenReturn(entidades);
        when(direccionMapeador.construirDTOs(entidades)).thenReturn(List.of(new DireccionDTO()));

        assertEquals(1, repositorio.consultarDTOs().size());
    }

    @Test
    void deberiaConsultarPorIdentificador() {
        UUID id = UUID.randomUUID();
        DireccionEntidad entidad = new DireccionEntidad();
        when(direccionDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(direccionMapeador.construirModelo(entidad)).thenReturn(mock(Direccion.class));

        assertNotNull(repositorio.consultarPorIdentificador(id));
    }

    @Test
    void deberiaRetornarNullCuandoIdentificadorNoExiste() {
        UUID id = UUID.randomUUID();
        when(direccionDAO.findById(id)).thenReturn(Optional.empty());

        assertNull(repositorio.consultarPorIdentificador(id));
    }

    @Test
    void deberiaVerificarHayDatos() {
        when(direccionDAO.count()).thenReturn(3L);
        assertTrue(repositorio.hayDatos());
    }

    @Test
    void deberiaVerificarNoHayDatos() {
        when(direccionDAO.count()).thenReturn(0L);
        assertFalse(repositorio.hayDatos());
    }

    @Test
    void deberiaConsultarPorNombre() {
        DireccionEntidad entidad = new DireccionEntidad();
        when(direccionDAO.findByNombre("Test")).thenReturn(entidad);
        when(direccionMapeador.construirModelo(entidad)).thenReturn(mock(Direccion.class));

        assertNotNull(repositorio.consultarPorNombre("Test"));
    }

    @Test
    void deberiaRetornarNullCuandoNombreNoExiste() {
        when(direccionDAO.findByNombre("NoExiste")).thenReturn(null);
        assertNull(repositorio.consultarPorNombre("NoExiste"));
    }

    @Test
    void deberiaConsultarPorActividad() {
        Actividad actividad = mock(Actividad.class);
        UUID actId = UUID.randomUUID();
        when(actividad.getIdentificador()).thenReturn(actId);
        DireccionEntidad entidad = new DireccionEntidad();
        when(direccionDAO.findByActividades_Identificador(actId)).thenReturn(entidad);
        when(direccionMapeador.construirModelo(entidad)).thenReturn(mock(Direccion.class));

        assertNotNull(repositorio.consultarPorActividad(actividad));
    }

    @Test
    void deberiaRetornarNullCuandoActividadNoTieneDireccion() {
        Actividad actividad = mock(Actividad.class);
        UUID actId = UUID.randomUUID();
        when(actividad.getIdentificador()).thenReturn(actId);
        when(direccionDAO.findByActividades_Identificador(actId)).thenReturn(null);

        assertNull(repositorio.consultarPorActividad(actividad));
    }

    @Test
    void deberiaConsultarDetallePorIdentificador() {
        UUID id = UUID.randomUUID();
        DireccionEntidad entidad = new DireccionEntidad();
        when(direccionDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(direccionDetalladaMapeador.construirDTO(entidad)).thenReturn(new DireccionDetalladaDTO());

        assertNotNull(repositorio.consultarDetallePorIdentificador(id));
    }

    @Test
    void deberiaRetornarNullDetalleNoExiste() {
        UUID id = UUID.randomUUID();
        when(direccionDAO.findById(id)).thenReturn(Optional.empty());

        assertNull(repositorio.consultarDetallePorIdentificador(id));
    }

    @Test
    void deberiaConsultarPorNombreDTO() {
        DireccionEntidad entidad = new DireccionEntidad();
        when(direccionDAO.findByNombre("Test")).thenReturn(entidad);
        when(direccionMapeador.construirDTO(entidad)).thenReturn(new DireccionDTO());

        assertNotNull(repositorio.consultarPorNombreDTO("Test"));
    }

    @Test
    void deberiaRetornarNullCuandoNombreDTONoExiste() {
        when(direccionDAO.findByNombre("NoExiste")).thenReturn(null);
        assertNull(repositorio.consultarPorNombreDTO("NoExiste"));
    }
}
