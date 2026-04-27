package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.ProyectoDTO;
import co.edu.uco.sibe.dominio.modelo.Proyecto;
import co.edu.uco.sibe.infraestructura.adaptador.dao.IndicadorProyectoDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.IndicadorProyectoEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.ProyectoEntidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IndicadorProyectoMapeadorTest {

    @Mock
    private ProyectoMapeador proyectoMapeador;

    @Mock
    private IndicadorProyectoDAO indicadorProyectoDAO;

    private IndicadorProyectoMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new IndicadorProyectoMapeador(proyectoMapeador, indicadorProyectoDAO);
    }

    @Test
    void deberiaConstruirEntidadDesdeProyecto() {
        UUID id = UUID.randomUUID();
        Proyecto proyecto = Proyecto.construir(id, "P-001", "Proyecto", "Objetivo", List.of());
        ProyectoEntidad proyectoEntidad = new ProyectoEntidad(id, "P-001", "Proyecto", "Objetivo", List.of());

        when(proyectoMapeador.construirEntidad(proyecto)).thenReturn(proyectoEntidad);
        when(indicadorProyectoDAO.findById(any(UUID.class))).thenReturn(Optional.empty());

        IndicadorProyectoEntidad entidad = mapeador.construirEntidad(proyecto);

        assertNotNull(entidad);
        assertEquals(proyectoEntidad, entidad.getProyecto());
    }

    @Test
    void deberiaConstruirModeloDesdeEntidad() {
        UUID id = UUID.randomUUID();
        ProyectoEntidad proyectoEntidad = new ProyectoEntidad(id, "P-001", "Nombre", "Obj", List.of());
        IndicadorProyectoEntidad entidad = new IndicadorProyectoEntidad(UUID.randomUUID(), proyectoEntidad);
        Proyecto proyecto = Proyecto.construir(id, "P-001", "Nombre", "Obj", List.of());

        when(proyectoMapeador.construriModelo(proyectoEntidad)).thenReturn(proyecto);

        Proyecto resultado = mapeador.construirModelo(entidad);

        assertEquals(id, resultado.getIdentificador());
    }

    @Test
    void deberiaActualizarEntidad() {
        ProyectoEntidad proyectoEntidad = new ProyectoEntidad(UUID.randomUUID(), "P-001", "Original", "Obj", List.of());
        IndicadorProyectoEntidad entidad = new IndicadorProyectoEntidad(UUID.randomUUID(), proyectoEntidad);
        Proyecto nuevoProyecto = Proyecto.construir(UUID.randomUUID(), "P-002", "Nuevo", "Nuevo obj", List.of());
        ProyectoEntidad nuevaEntidad = new ProyectoEntidad(UUID.randomUUID(), "P-002", "Nuevo", "Nuevo obj", List.of());

        when(proyectoMapeador.construirEntidad(nuevoProyecto)).thenReturn(nuevaEntidad);

        mapeador.actualizarEntidad(entidad, nuevoProyecto);

        assertEquals(nuevaEntidad, entidad.getProyecto());
    }

    @Test
    void deberiaConstruirDTODesdeEntidad() {
        UUID id = UUID.randomUUID();
        ProyectoEntidad proyectoEntidad = new ProyectoEntidad(id, "P-003", "Nombre DTO", "Obj DTO", List.of());
        IndicadorProyectoEntidad entidad = new IndicadorProyectoEntidad(UUID.randomUUID(), proyectoEntidad);
        ProyectoDTO proyectoDTO = new ProyectoDTO(id.toString(), "P-003", "Nombre DTO", "Obj DTO", List.of());

        when(proyectoMapeador.construirDTO(proyectoEntidad)).thenReturn(proyectoDTO);

        ProyectoDTO resultado = mapeador.construirDTO(entidad);

        assertEquals(id.toString(), resultado.getIdentificador());
    }
}
