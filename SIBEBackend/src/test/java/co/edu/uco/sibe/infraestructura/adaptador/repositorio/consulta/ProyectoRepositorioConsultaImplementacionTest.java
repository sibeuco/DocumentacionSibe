package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.dto.ProyectoDTO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.ProyectoDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.ProyectoEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.ProyectoMapeador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import co.edu.uco.sibe.dominio.modelo.Proyecto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProyectoRepositorioConsultaImplementacionTest {

    @Mock
    private ProyectoDAO proyectoDAO;

    @Mock
    private ProyectoMapeador proyectoMapeador;

    private ProyectoRepositorioConsultaImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new ProyectoRepositorioConsultaImplementacion(proyectoDAO, proyectoMapeador);
    }

    @Test
    void deberiaRetornarDTOs() {
        List<ProyectoEntidad> entidades = List.of(new ProyectoEntidad());
        ProyectoDTO dto = new ProyectoDTO();

        when(proyectoDAO.findAllConAcciones()).thenReturn(entidades);
        when(proyectoMapeador.construirDTOs(entidades)).thenReturn(List.of(dto));

        List<ProyectoDTO> resultado = repositorio.consultarDTOs();

        assertEquals(1, resultado.size());
    }

    @Test
    void deberiaRetornarDTOsPaginado() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "nombre"));
        Page<ProyectoEntidad> paginaEntidades = new PageImpl<>(List.of(new ProyectoEntidad()), pageRequest, 1);
        Page<ProyectoDTO> paginaDTOs = new PageImpl<>(List.of(new ProyectoDTO()), pageRequest, 1);
        when(proyectoDAO.findAllConAcciones(pageRequest)).thenReturn(paginaEntidades);
        when(proyectoMapeador.construirDTOsPaginado(paginaEntidades)).thenReturn(paginaDTOs);

        Page<ProyectoDTO> resultado = repositorio.consultarDTOsPaginado(0, 10);

        assertEquals(1, resultado.getContent().size());
        assertEquals(1, resultado.getTotalElements());
    }

    @Test
    void deberiaRetornarProyectoCuandoExisteIdentificador() {
        UUID id = UUID.randomUUID();
        ProyectoEntidad entidad = new ProyectoEntidad();
        Proyecto modelo = mock(Proyecto.class);

        when(proyectoDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(proyectoMapeador.construriModelo(entidad)).thenReturn(modelo);

        Proyecto resultado = repositorio.consultarPorIdentificador(id);

        assertNotNull(resultado);
        assertEquals(modelo, resultado);
    }

    @Test
    void deberiaRetornarNuloCuandoNoExisteIdentificador() {
        UUID id = UUID.randomUUID();

        when(proyectoDAO.findById(id)).thenReturn(Optional.empty());

        assertNull(repositorio.consultarPorIdentificador(id));
    }

    @Test
    void deberiaRetornarProyectoCuandoExisteNumeroProyecto() {
        String numero = "PRY001";
        ProyectoEntidad entidad = new ProyectoEntidad();
        Proyecto modelo = mock(Proyecto.class);

        when(proyectoDAO.findByNumeroProyecto(numero)).thenReturn(entidad);
        when(proyectoMapeador.construriModelo(entidad)).thenReturn(modelo);

        Proyecto resultado = repositorio.consultarPorNumeroProyecto(numero);

        assertNotNull(resultado);
        assertEquals(modelo, resultado);
    }

    @Test
    void deberiaRetornarNuloCuandoNoExisteNumeroProyecto() {
        when(proyectoDAO.findByNumeroProyecto("INVALIDO")).thenReturn(null);

        assertNull(repositorio.consultarPorNumeroProyecto("INVALIDO"));
    }

    @Test
    void deberiaConsultarDTOsPaginado() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "nombre"));
        Page<ProyectoEntidad> paginaEntidades = new PageImpl<>(List.of(new ProyectoEntidad()), pageRequest, 1);
        Page<ProyectoDTO> paginaDTOs = new PageImpl<>(List.of(new ProyectoDTO()), pageRequest, 1);
        when(proyectoDAO.findAllConAcciones(pageRequest)).thenReturn(paginaEntidades);
        when(proyectoMapeador.construirDTOsPaginado(paginaEntidades)).thenReturn(paginaDTOs);

        Page<ProyectoDTO> resultado = repositorio.consultarDTOsPaginado(0, 10);

        assertEquals(1, resultado.getContent().size());
        assertEquals(1, resultado.getTotalElements());
    }
}
