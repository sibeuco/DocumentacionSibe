package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.dto.AreaDTO;
import co.edu.uco.sibe.dominio.dto.AreaDetalladaDTO;
import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.modelo.Area;
import co.edu.uco.sibe.infraestructura.adaptador.dao.AreaDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.DireccionDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.AreaEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.AreaDetalladaMapeador;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.AreaMapeador;
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
class AreaRepositorioConsultaImplementacionTest {

    @Mock private AreaDAO areaDAO;
    @Mock private DireccionDAO direccionDAO;
    @Mock private AreaMapeador areaMapeador;
    @Mock private AreaDetalladaMapeador areaDetalladaMapeador;

    private AreaRepositorioConsultaImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new AreaRepositorioConsultaImplementacion(areaDAO, direccionDAO, areaMapeador, areaDetalladaMapeador);
    }

    @Test
    void deberiaConsultarDTOs() {
        List<AreaEntidad> entidades = List.of(new AreaEntidad());
        when(areaDAO.findAll()).thenReturn(entidades);
        when(areaMapeador.construirDTOs(entidades)).thenReturn(List.of(new AreaDTO()));

        List<AreaDTO> resultado = repositorio.consultarDTOs();

        assertEquals(1, resultado.size());
    }

    @Test
    void deberiaConsultarTodos() {
        List<AreaEntidad> entidades = List.of(new AreaEntidad());
        when(areaDAO.findAll()).thenReturn(entidades);
        when(areaMapeador.construirModelos(entidades)).thenReturn(List.of(mock(Area.class)));

        List<Area> resultado = repositorio.consultarTodos();

        assertEquals(1, resultado.size());
    }

    @Test
    void deberiaConsultarPorIdentificadorExistente() {
        UUID id = UUID.randomUUID();
        AreaEntidad entidad = new AreaEntidad();
        Area modelo = mock(Area.class);
        when(areaDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(areaMapeador.construirModelo(entidad)).thenReturn(modelo);

        Area resultado = repositorio.consultarPorIdentificador(id);

        assertNotNull(resultado);
    }

    @Test
    void deberiaRetornarNullCuandoIdentificadorNoExisteArea() {
        UUID id = UUID.randomUUID();
        when(areaDAO.findById(id)).thenReturn(Optional.empty());

        Area resultado = repositorio.consultarPorIdentificador(id);

        assertNull(resultado);
    }

    @Test
    void deberiaVerificarHayDatos() {
        when(areaDAO.count()).thenReturn(5L);

        assertTrue(repositorio.hayDatos());
    }

    @Test
    void deberiaVerificarNoHayDatos() {
        when(areaDAO.count()).thenReturn(0L);

        assertFalse(repositorio.hayDatos());
    }

    @Test
    void deberiaConsultarPorNombreExistente() {
        AreaEntidad entidad = new AreaEntidad();
        Area modelo = mock(Area.class);
        when(areaDAO.findByNombre("Test")).thenReturn(entidad);
        when(areaMapeador.construirModelo(entidad)).thenReturn(modelo);

        Area resultado = repositorio.consultarPorNombre("Test");

        assertNotNull(resultado);
    }

    @Test
    void deberiaRetornarNullCuandoNombreNoExisteArea() {
        when(areaDAO.findByNombre("NoExiste")).thenReturn(null);

        Area resultado = repositorio.consultarPorNombre("NoExiste");

        assertNull(resultado);
    }

    @Test
    void deberiaConsultarPorActividad() {
        Actividad actividad = mock(Actividad.class);
        UUID actId = UUID.randomUUID();
        when(actividad.getIdentificador()).thenReturn(actId);
        AreaEntidad entidad = new AreaEntidad();
        Area modelo = mock(Area.class);
        when(areaDAO.findByActividades_Identificador(actId)).thenReturn(entidad);
        when(areaMapeador.construirModelo(entidad)).thenReturn(modelo);

        Area resultado = repositorio.consultarPorActividad(actividad);

        assertNotNull(resultado);
    }

    @Test
    void deberiaRetornarNullCuandoActividadNoTieneArea() {
        Actividad actividad = mock(Actividad.class);
        UUID actId = UUID.randomUUID();
        when(actividad.getIdentificador()).thenReturn(actId);
        when(areaDAO.findByActividades_Identificador(actId)).thenReturn(null);

        Area resultado = repositorio.consultarPorActividad(actividad);

        assertNull(resultado);
    }

    @Test
    void deberiaConsultarDetallePorIdentificadorExistente() {
        UUID id = UUID.randomUUID();
        AreaEntidad entidad = new AreaEntidad();
        AreaDetalladaDTO dto = new AreaDetalladaDTO();
        when(areaDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(areaDetalladaMapeador.construirDTO(entidad)).thenReturn(dto);

        AreaDetalladaDTO resultado = repositorio.consultarDetallePorIdentificador(id);

        assertNotNull(resultado);
    }

    @Test
    void deberiaRetornarNullDetalleNoExiste() {
        UUID id = UUID.randomUUID();
        when(areaDAO.findById(id)).thenReturn(Optional.empty());

        AreaDetalladaDTO resultado = repositorio.consultarDetallePorIdentificador(id);

        assertNull(resultado);
    }

    @Test
    void deberiaConsultarPorNombreDTO() {
        AreaEntidad entidad = new AreaEntidad();
        AreaDTO dto = new AreaDTO();
        when(areaDAO.findByNombre("Test")).thenReturn(entidad);
        when(areaMapeador.construirDTO(entidad)).thenReturn(dto);

        AreaDTO resultado = repositorio.consultarPorNombreDTO("Test");

        assertNotNull(resultado);
    }

    @Test
    void deberiaRetornarNullCuandoNombreDTONoExiste() {
        when(areaDAO.findByNombre("NoExiste")).thenReturn(null);

        AreaDTO resultado = repositorio.consultarPorNombreDTO("NoExiste");

        assertNull(resultado);
    }

    @Test
    void deberiaConsultarPorSubarea() {
        UUID subareaId = UUID.randomUUID();
        AreaEntidad entidad = new AreaEntidad();
        Area modelo = mock(Area.class);
        when(areaDAO.findBySubareas_Identificador(subareaId)).thenReturn(entidad);
        when(areaMapeador.construirModelo(entidad)).thenReturn(modelo);

        Area resultado = repositorio.consultarPorSubarea(subareaId);

        assertNotNull(resultado);
    }

    @Test
    void deberiaRetornarNullCuandoSubareaNoTieneArea() {
        UUID subareaId = UUID.randomUUID();
        when(areaDAO.findBySubareas_Identificador(subareaId)).thenReturn(null);

        Area resultado = repositorio.consultarPorSubarea(subareaId);

        assertNull(resultado);
    }
}
