package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.ProyectoDTO;
import co.edu.uco.sibe.dominio.modelo.Accion;
import co.edu.uco.sibe.dominio.modelo.Proyecto;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.AccionEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.ProyectoAccionEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.ProyectoEntidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProyectoMapeadorTest {

    @Mock
    private ProyectoAccionMapeador proyectoAccionMapeador;

    private ProyectoMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new ProyectoMapeador(proyectoAccionMapeador);
    }

    @Test
    void deberiaConstruirEntidadDesdeModelo() {
        UUID id = UUID.randomUUID();
        Accion accion = Accion.construir(UUID.randomUUID(), "Detalle", "Objetivo");
        Proyecto proyecto = Proyecto.construir(id, "PPTO-001", "Proyecto Test", "Objetivo Test", List.of(accion));

        ProyectoAccionEntidad pae = new ProyectoAccionEntidad(UUID.randomUUID(), new AccionEntidad(accion.getIdentificador(), "Detalle", "Objetivo"));
        when(proyectoAccionMapeador.construirEntidades(anyList())).thenReturn(List.of(pae));

        ProyectoEntidad entidad = mapeador.construirEntidad(proyecto);

        assertEquals(id, entidad.getIdentificador());
        assertEquals("PPTO-001", entidad.getNumeroProyecto());
        assertEquals("Proyecto Test", entidad.getNombre());
        assertEquals("Objetivo Test", entidad.getObjetivo());
        assertEquals(1, entidad.getAcciones().size());
    }

    @Test
    void deberiaConstruirModeloDesdeEntidad() {
        UUID id = UUID.randomUUID();
        ProyectoAccionEntidad pae = new ProyectoAccionEntidad(UUID.randomUUID(), new AccionEntidad(UUID.randomUUID(), "D", "O"));
        ProyectoEntidad entidad = new ProyectoEntidad(id, "PPTO-002", "Nombre", "Objetivo", List.of(pae));

        Accion accion = Accion.construir(UUID.randomUUID(), "D", "O");
        when(proyectoAccionMapeador.construirModelos(anyList())).thenReturn(List.of(accion));

        Proyecto proyecto = mapeador.construriModelo(entidad);

        assertEquals(id, proyecto.getIdentificador());
        assertEquals("PPTO-002", proyecto.getNumeroProyecto());
        assertEquals("Nombre", proyecto.getNombre());
        assertEquals("Objetivo", proyecto.getObjetivo());
        assertEquals(1, proyecto.getAcciones().size());
    }

    @Test
    void deberiaConstruirDTODesdeEntidad() {
        UUID id = UUID.randomUUID();
        ProyectoEntidad entidad = new ProyectoEntidad(id, "PPTO-003", "Nombre DTO", "Objetivo DTO", List.of());

        ProyectoDTO dto = mapeador.construirDTO(entidad);

        assertEquals(id.toString(), dto.getIdentificador());
        assertEquals("PPTO-003", dto.getNumeroProyecto());
        assertEquals("Nombre DTO", dto.getNombre());
        assertEquals("Objetivo DTO", dto.getObjetivo());
    }

    @Test
    void deberiaConstruirListaDTOs() {
        ProyectoEntidad e1 = new ProyectoEntidad(UUID.randomUUID(), "P1", "N1", "O1", List.of());
        ProyectoEntidad e2 = new ProyectoEntidad(UUID.randomUUID(), "P2", "N2", "O2", List.of());

        List<ProyectoDTO> dtos = mapeador.construirDTOs(List.of(e1, e2));

        assertEquals(2, dtos.size());
    }

    @Test
    void deberiaActualizarEntidad() {
        ProyectoEntidad entidad = new ProyectoEntidad(UUID.randomUUID(), "PPTO-004", "Original", "Original", new ArrayList<>());
        Accion accion = Accion.construir(UUID.randomUUID(), "Nuevo", "Nuevo");
        Proyecto proyecto = Proyecto.construir(UUID.randomUUID(), "PPTO-005", "Nombre Nuevo", "Objetivo Nuevo", List.of(accion));

        mapeador.actualizarEntidad(entidad, proyecto);

        assertEquals("PPTO-005", entidad.getNumeroProyecto());
        assertEquals("Nombre Nuevo", entidad.getNombre());
        assertEquals("Objetivo Nuevo", entidad.getObjetivo());
        verify(proyectoAccionMapeador).actualizarTodos(anyList(), anyList());
    }

    @Test
    void deberiaConstruirDTOsPaginado() {
        ProyectoEntidad e1 = new ProyectoEntidad(UUID.randomUUID(), "P1", "N1", "O1", List.of());
        ProyectoEntidad e2 = new ProyectoEntidad(UUID.randomUUID(), "P2", "N2", "O2", List.of());
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<ProyectoEntidad> paginaEntidades = new PageImpl<>(List.of(e1, e2), pageRequest, 2);

        Page<ProyectoDTO> resultado = mapeador.construirDTOsPaginado(paginaEntidades);

        assertEquals(2, resultado.getContent().size());
        assertEquals(2, resultado.getTotalElements());
    }
}
