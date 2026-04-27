package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.dto.SubareaDTO;
import co.edu.uco.sibe.dominio.dto.SubareaDetalladaDTO;
import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.modelo.Subarea;
import co.edu.uco.sibe.infraestructura.adaptador.dao.AreaDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.DireccionDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.SubareaDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.SubareaEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.SubareaDetalladaMapeador;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.SubareaMapeador;
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
class SubareaRepositorioConsultaImplementacionTest {

    @Mock private SubareaDAO subareaDAO;
    @Mock private AreaDAO areaDAO;
    @Mock private DireccionDAO direccionDAO;
    @Mock private SubareaMapeador subareaMapeador;
    @Mock private SubareaDetalladaMapeador subareaDetalladaMapeador;

    private SubareaRepositorioConsultaImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new SubareaRepositorioConsultaImplementacion(subareaDAO, areaDAO, direccionDAO, subareaMapeador, subareaDetalladaMapeador);
    }

    @Test
    void deberiaConsultarDTOs() {
        List<SubareaEntidad> entidades = List.of(new SubareaEntidad());
        when(subareaDAO.findAll()).thenReturn(entidades);
        when(subareaMapeador.construirDTOs(entidades)).thenReturn(List.of(new SubareaDTO()));

        List<SubareaDTO> resultado = repositorio.consultarDTOs();

        assertEquals(1, resultado.size());
    }

    @Test
    void deberiaConsultarTodos() {
        List<SubareaEntidad> entidades = List.of(new SubareaEntidad());
        when(subareaDAO.findAll()).thenReturn(entidades);
        when(subareaMapeador.construirModelos(entidades)).thenReturn(List.of(mock(Subarea.class)));

        List<Subarea> resultado = repositorio.consultarTodos();

        assertEquals(1, resultado.size());
    }

    @Test
    void deberiaConsultarPorIdentificador() {
        UUID id = UUID.randomUUID();
        SubareaEntidad entidad = new SubareaEntidad();
        when(subareaDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(subareaMapeador.construirModelo(entidad)).thenReturn(mock(Subarea.class));

        Subarea resultado = repositorio.consultarPorIdentificador(id);

        assertNotNull(resultado);
    }

    @Test
    void deberiaRetornarNullCuandoIdentificadorNoExiste() {
        UUID id = UUID.randomUUID();
        when(subareaDAO.findById(id)).thenReturn(Optional.empty());

        assertNull(repositorio.consultarPorIdentificador(id));
    }

    @Test
    void deberiaVerificarHayDatos() {
        when(subareaDAO.count()).thenReturn(3L);
        assertTrue(repositorio.hayDatos());
    }

    @Test
    void deberiaVerificarNoHayDatos() {
        when(subareaDAO.count()).thenReturn(0L);
        assertFalse(repositorio.hayDatos());
    }

    @Test
    void deberiaConsultarPorNombre() {
        SubareaEntidad entidad = new SubareaEntidad();
        when(subareaDAO.findByNombre("Test")).thenReturn(entidad);
        when(subareaMapeador.construirModelo(entidad)).thenReturn(mock(Subarea.class));

        assertNotNull(repositorio.consultarPorNombre("Test"));
    }

    @Test
    void deberiaRetornarNullCuandoNombreNoExiste() {
        when(subareaDAO.findByNombre("NoExiste")).thenReturn(null);
        assertNull(repositorio.consultarPorNombre("NoExiste"));
    }

    @Test
    void deberiaConsultarPorActividad() {
        Actividad actividad = mock(Actividad.class);
        UUID actId = UUID.randomUUID();
        when(actividad.getIdentificador()).thenReturn(actId);
        SubareaEntidad entidad = new SubareaEntidad();
        when(subareaDAO.findByActividades_Identificador(actId)).thenReturn(entidad);
        when(subareaMapeador.construirModelo(entidad)).thenReturn(mock(Subarea.class));

        assertNotNull(repositorio.consultarPorActividad(actividad));
    }

    @Test
    void deberiaRetornarNullCuandoActividadNoTieneSubarea() {
        Actividad actividad = mock(Actividad.class);
        UUID actId = UUID.randomUUID();
        when(actividad.getIdentificador()).thenReturn(actId);
        when(subareaDAO.findByActividades_Identificador(actId)).thenReturn(null);

        assertNull(repositorio.consultarPorActividad(actividad));
    }

    @Test
    void deberiaConsultarDetallePorIdentificador() {
        UUID id = UUID.randomUUID();
        SubareaEntidad entidad = new SubareaEntidad();
        when(subareaDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(subareaDetalladaMapeador.construirDTO(entidad)).thenReturn(new SubareaDetalladaDTO());

        assertNotNull(repositorio.consultarDetallePorIdentificador(id));
    }

    @Test
    void deberiaRetornarNullDetalleNoExiste() {
        UUID id = UUID.randomUUID();
        when(subareaDAO.findById(id)).thenReturn(Optional.empty());

        assertNull(repositorio.consultarDetallePorIdentificador(id));
    }

    @Test
    void deberiaConsultarPorNombreDTO() {
        SubareaEntidad entidad = new SubareaEntidad();
        when(subareaDAO.findByNombre("Test")).thenReturn(entidad);
        when(subareaMapeador.construirDTO(entidad)).thenReturn(new SubareaDTO());

        assertNotNull(repositorio.consultarPorNombreDTO("Test"));
    }

    @Test
    void deberiaRetornarNullCuandoNombreDTONoExiste() {
        when(subareaDAO.findByNombre("NoExiste")).thenReturn(null);
        assertNull(repositorio.consultarPorNombreDTO("NoExiste"));
    }
}
