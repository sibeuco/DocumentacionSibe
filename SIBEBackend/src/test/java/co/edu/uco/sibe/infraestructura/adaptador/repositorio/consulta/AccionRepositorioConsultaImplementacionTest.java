package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.dto.AccionDTO;
import co.edu.uco.sibe.dominio.modelo.Accion;
import co.edu.uco.sibe.infraestructura.adaptador.dao.AccionDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.AccionEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.AccionMapeador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccionRepositorioConsultaImplementacionTest {

    @Mock
    private AccionDAO accionDAO;

    @Mock
    private AccionMapeador accionMapeador;

    private AccionRepositorioConsultaImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new AccionRepositorioConsultaImplementacion(accionDAO, accionMapeador);
    }

    @Test
    void deberiaConsultarDTOs() {
        List<AccionEntidad> entidades = List.of(new AccionEntidad());
        when(accionDAO.findAll()).thenReturn(entidades);
        when(accionMapeador.construirDTOs(entidades)).thenReturn(List.of(new AccionDTO()));

        List<AccionDTO> resultado = repositorio.consultarDTOs();

        assertEquals(1, resultado.size());
    }

    @Test
    void deberiaConsultarDTOsPaginado() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "detalle"));
        Page<AccionEntidad> paginaEntidades = new PageImpl<>(List.of(new AccionEntidad()), pageRequest, 1);
        Page<AccionDTO> paginaDTOs = new PageImpl<>(List.of(new AccionDTO()), pageRequest, 1);
        when(accionDAO.findAll(pageRequest)).thenReturn(paginaEntidades);
        when(accionMapeador.construirDTOsPaginado(paginaEntidades)).thenReturn(paginaDTOs);

        Page<AccionDTO> resultado = repositorio.consultarDTOsPaginado(0, 10);

        assertEquals(1, resultado.getContent().size());
        assertEquals(1, resultado.getTotalElements());
    }

    @Test
    void deberiaConsultarTodosPorIdentificadores() {
        List<UUID> ids = List.of(UUID.randomUUID());
        List<AccionEntidad> entidades = List.of(new AccionEntidad());
        when(accionDAO.findAllById(ids)).thenReturn(entidades);
        when(accionMapeador.construirModelos(entidades)).thenReturn(List.of(mock(Accion.class)));

        List<Accion> resultado = repositorio.consultarTodosPorIdentificadores(ids);

        assertEquals(1, resultado.size());
    }

    @Test
    void deberiaConsultarPorIdentificadorExistente() {
        UUID id = UUID.randomUUID();
        AccionEntidad entidad = new AccionEntidad();
        Accion modelo = mock(Accion.class);
        when(accionDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(accionMapeador.construriModelo(entidad)).thenReturn(modelo);

        Accion resultado = repositorio.consultarPorIdentificador(id);

        assertNotNull(resultado);
    }

    @Test
    void deberiaRetornarNullCuandoIdentificadorNoExiste() {
        UUID id = UUID.randomUUID();
        when(accionDAO.findById(id)).thenReturn(Optional.empty());

        Accion resultado = repositorio.consultarPorIdentificador(id);

        assertNull(resultado);
    }

    @Test
    void deberiaConsultarTodos() {
        List<AccionEntidad> entidades = List.of(new AccionEntidad());
        when(accionDAO.findAll()).thenReturn(entidades);
        when(accionMapeador.construirModelos(entidades)).thenReturn(List.of(mock(Accion.class)));

        List<Accion> resultado = repositorio.consultarTodos();

        assertEquals(1, resultado.size());
    }

    @Test
    void deberiaConsultarPorDetalleExistente() {
        AccionEntidad entidad = new AccionEntidad();
        Accion modelo = mock(Accion.class);
        when(accionDAO.findByDetalle("detalle")).thenReturn(entidad);
        when(accionMapeador.construriModelo(entidad)).thenReturn(modelo);

        Accion resultado = repositorio.consultarPorDetalle("detalle");

        assertNotNull(resultado);
    }

    @Test
    void deberiaRetornarNullCuandoDetalleNoExiste() {
        when(accionDAO.findByDetalle("noexiste")).thenReturn(null);

        Accion resultado = repositorio.consultarPorDetalle("noexiste");

        assertNull(resultado);
    }
}
